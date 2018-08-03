(ns coffee-machine.events
  (:require
   [re-frame.core :as re-frame]
   [coffee-machine.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::insert-coin
 (fn [current-db [_ value]]
   (update current-db :inserted-money conj value)))

(defn normalize [n]
  (.valueOf (js/Number. (.toFixed n 2))))

(defn- change
  ([money cost coins] (change
                       (normalize (- money cost)) cost
                       (into []
                             (comp (filter #(% 0)) (map #(% 1)))
                             coins)
                       {}))
  ([money cost coins already]
   (if (zero? money)
     {:coins already}
     (let [c (last coins)
           cn (quot (* money 100) (* c 100))
           nm (normalize (- money (* c cn)))
           na (assoc already c cn)]
       (if (zero? nm)
         {:coins na}
         (if (<= nm (first coins))
           (if (<= (count coins) 1)
             {:remainer? true
              :remainer nm
              :coins na}
             (recur money cost (pop coins) already))
           (if (<= (count coins) 1)
             {:remainer? true
              :remainer nm
              :coins na}
             (recur nm cost (pop coins) na))))))))

(re-frame/reg-event-db
 ::buy-coffee
 (fn [{:keys [coffees coins inserted-money] :as current-db} [_ i]]
   (let [c (change (reduce + inserted-money)
                   (:price (coffees i))
                   coins)
         r? (:remainer? c)
         r (:remainer c)]
     (assoc current-db
            :purchase {:change (:coins c)
                       :coffee (coffees i)}
            :inserted-money (if r? [r] [])))))

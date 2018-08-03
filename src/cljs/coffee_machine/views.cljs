(ns coffee-machine.views
  (:require
   [re-frame.core :as re-frame]
   [coffee-machine.subs :as subs]
   [coffee-machine.events :as events]))

(defn format-money [n]
  (str "R$" (.toFixed n 2)))

(defn coffees-list [coffees money-amount]
  (-> (map #(vector :li {:class (if (>= money-amount (:price %))
                                  "enabled" "disabled")}
                    (:name %) " - "
                    (format-money (:price %))) coffees)
      (conj :ul.coffees-list)
      vec))

(defn coin [m value enabled]
  [:input (merge m
                 {:type "button" 
                  :value (format-money value)
                  :on-click (if enabled
                              #(re-frame/dispatch [::events/insert-coin value])
                              #(js/alert "Estamos rejeitando esse tipo de moeda :) \n
                                   Agradecemos a compreensão"))})])

(defn main-panel []
  (let [coffees (re-frame/subscribe [::subs/coffees])
        inserted-money (re-frame/subscribe [::subs/inserted-money])
        money-amount (reduce + @inserted-money)]
    [:div
     [:h1 "Cafés disponíveis:" [:br]
      [coffees-list @coffees money-amount]
      [:br] [:br]
      (-> (for [[e c] [[false 1] [false 5] [true 10] [true 25] [true 50]]]
            [coin {:key c} (* 0.01 c) e]))]]))

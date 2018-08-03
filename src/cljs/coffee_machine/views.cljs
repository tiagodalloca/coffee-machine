(ns coffee-machine.views
  (:require
   [re-frame.core :as re-frame]
   [coffee-machine.subs :as subs]
   [coffee-machine.events :as events]))

(defn format-money [n]
  (str "R$" (.toFixed n 2)))

(defn coffees-list [coffees money-amount]
  (-> (map-indexed
       #(vector :li (let [enabled? (>= money-amount (:price %2))]
                      {:class (if enabled? "enabled" "disabled")
                       :on-click
                       (if enabled?
                         (fn [_] (re-frame/dispatch [::events/buy-coffee %1]))
                         (fn [_] (js/alert "Você precisa adicionar mais moedas para comprar esse café")))})
                (:name %2) " - "
                (format-money (:price %2))) coffees)
      (conj :ul.coffees-list)
      vec))

(defn coin [m value enabled]
  [:input (merge m
                 {:type "button" 
                  :value (format-money value)
                  :on-click (if enabled
                              #(re-frame/dispatch [::events/insert-coin value])
                              #(js/alert "Estamos rejeitando esse tipo de moeda :)\nAgradecemos a compreensão"))})])

(defn change-panel [{:keys [change coffee]}]
  [:div.change-panel
   [:h2 "Troco:"] 
   (clojure.string/join " + " (for [[c a] (filter (fn [[c a]] (not (zero? a))) change)]
                                (str (format-money c) "x" a)))
   " = " (format-money (reduce (fn [acc [c a]] (+ acc (* c a))) 0 change))])

(defn main-panel []
  (let [coins (re-frame/subscribe [::subs/coins])
        coffees (re-frame/subscribe [::subs/coffees])
        inserted-money (re-frame/subscribe [::subs/inserted-money])
        purchase (re-frame/subscribe [::subs/purchase])
        money-amount (reduce + @inserted-money)]
    [:div.machine
     [:div.title "~ La Machine à Café ~"]
     [:div {:style {:text-align "center"}}
      "Porque café é importante para nós e você"] [:br]

     [:h1 "Cafés disponíveis:"] 
     [coffees-list @coffees money-amount] 
     [:div.inserted-money "Inserido: " (format-money money-amount)]
     (-> (for [[e c] @coins]
           [coin {:key c} c e]))
     [:br]
     (when-let [p @purchase] [change-panel p])]))

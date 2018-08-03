(ns coffee-machine.views
  (:require
   [re-frame.core :as re-frame]
   [coffee-machine.subs :as subs]
   [goog.string :refer [format]]))

(defn coffees-list [coffees]
  (-> (map #(vector :li
                    (:name %) " - "
                    (format "R$%.2f" (:price %))) coffees)
      (conj :ul)
      vec))

(defn main-panel []
  (let [coffees (re-frame/subscribe [::subs/coffees])]
    [:div
     [:h1 "Cafés disponíveis:" [:br]
      [coffees-list @coffees]]]))

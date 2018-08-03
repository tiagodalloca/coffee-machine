(ns coffee-machine.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:color "#4a2323"}] 
  [:.title {:font-family "\"URW Chancery L\", cursive"
            :font-size "60px"
            :font-weight 400
            :color "black"
            :text-align "center"}] 
  [:h1 {
        ;; :text-align "center"
        :font-weight "500"}]
  [:.machine {:width "700px"
              ;; :border "1px solid gray"
              :margin "0 auto"}]
  [:ul.coffees-list {:margin-left 0
                     ;; :padding-left 0
                     }]  
  [:.coffees-list :li {:list-style-type "none"
                       :cursor "pointer"
                       :margin- 0
                       :font-size "30px"}]
  [:.coffees-list :.enabled {:color "#317e63"}]
  [:.coffees-list :.disabled {:color "gray"}]
  [:.inserted-money {:font-size "25px"}])

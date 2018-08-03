(ns coffee-machine.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:color "#4a2323"}] 
  [:.coffee-list :li {:cursor "pointer"}]
  [:.coffees-list :.enabled {:color "#317e63"}]
  [:.coffees-list :.disabled {:color "gray"}])

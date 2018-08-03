(ns coffee-machine.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::coffees
 (fn [db]
   (:coffees db)))

(re-frame/reg-sub
 ::inserted-money
 (fn [db]
   (:inserted-money db)))

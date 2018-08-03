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

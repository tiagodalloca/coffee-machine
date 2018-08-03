(ns coffee-machine.db)

(defn- gen-coffees [& args]
  (vec (for [coffee args]
         {:name (coffee 0) :price (coffee 1)})))

(def default-db
  {:coffees (gen-coffees ["Cappuccino" 3.5]
                         ["Mocha" 4.00]
                         ["Café com leite" 3.00])})

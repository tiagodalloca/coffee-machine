(ns coffee-machine.db)

(defn- gen-coffees [& args]
  (vec (for [coffee args]
         {:name (coffee 0) :price (coffee 1)})))

(def default-db
  {:coins [[false 0.01] [false 0.05] [true 0.10] [true 0.25] [true 0.50]]
   :coffees (gen-coffees ["Cappuccino" 3.5]
                         ["Mocha" 4.00]
                         ["Café com leite" 3.00])
   :inserted-money []})

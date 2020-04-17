(ns koans.15-destructuring
  (:require [koan-engine.core :refer :all]))

(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ":bar:foo" ((fn [[a b]] (str b a))
                 [:foo :bar]))

  "Whether in function definitions"
  (= (str "An Oxford comma list of apples, "
          "oranges, "
          "and pears.")
     ((fn [[a b c]] (str "An Oxford comma list of " a ", " b ", and " c "."))
      ["apples" "oranges" "pears"]))

  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Lambda Guru"
     (let [[first-name last-name & aliases]
           (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")]
       (str first-name " " last-name ""
            (reduce str (map (fn [s] (str " aka " s)) aliases)))))
  ; this version seems clearer, reducing the list using the "str" function
  ; it works using clojure.string/join function, instead of str, because that handles a list directly
  ; but it seems less clear
  ; here is the prior version of the above line:
  ;            (clojure.string/join (map (fn [s] (str " aka " s)) aliases)))))

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Stephen" "Hawking"] :named-parts {:first "Stephen" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
       {:original-parts (vector first-name last-name) :named-parts {:first first-name
                                                                    :last  last-name}}))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address, city :city, state :state} test-address]
       (str street-address ", " city ", " state)))
  ; or, (maybe?) more elegantly the last line could be:
  ;    (clojure.string/join ", " (list street-address city state))

  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address city state]} test-address]
       (str street-address ", " city ", " state)))

  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX"
     ((fn [[first-name last-name :as name-vector]  ; destructure the first and last names
           test-address-local]
        (let [{:keys [street-address city state]} test-address-local]  ; destructure the address parts using these keys
          (clojure.string/join ", "
                               (list
                                 (clojure.string/join " " name-vector) ; name joined by space only
                                 street-address
                                 city
                                 state))))
      ["Test" "Testerson"] test-address)))



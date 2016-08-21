(ns rpam.repository
  (:require [rpam.database :as db]
            [clj-time.core :as t]))

(def ads db/ads)

(defn find-ads
  ([channel id]
   (let [filters [#(= id (:id %))
                  #(.contains (:channels %) channel)]]
     (first (filter (fn [x] (every? #(% x) filters)) @db/ads))))

  ([channel country language]
   (let [filters [#(.contains (:channels %) channel)
                  #(= country (:country %))
                  #(= language (:language %))
                  #(> (get-in % [:views channel]) 0)
                  #(t/within?
                     (t/interval (:starttime %) (:endtime %))
                     (t/now))]]
    (filterv (fn [x] (every? #(% x) filters)) @db/ads))))

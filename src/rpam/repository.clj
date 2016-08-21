(ns rpam.repository
  (:require [rpam.database :as db]
            [clj-time.core :as t]))

(def ads db/ads)

(defn find-ads
  ([channel id]
   (let [filters [#(= id (:id %))
                  #(.contains (:channels %) channel)]]
     (first (filter (fn [x] (every? #(% x) filters)) @db/ads)))))

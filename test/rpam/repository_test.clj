(ns rpam.repository-test
  (:require [midje.sweet :refer :all]
            [rpam.database :as db]
            [rpam.repository :refer :all]
            [clj-time.core :as t]))

(facts "find-ads returns unformatted ads data"
  (fact "it returns nil if id not found"
    (find-ads :food 42) => nil)
  (fact "it returns specific unformatted ad for id and channel provided"
    (find-ads :food 1) => (nth @db/ads 1)))

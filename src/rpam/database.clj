(ns redpineapple.database
  (:require [clj-time.core :as t]))

(def ads
  (atom [
    {:id 0
     :url "http://example.com/0.jpg"
     :country :de
     :language :de
     :channels [:fashion :design]
     :starttime (t/date-time 2016 7 1)
     :endtime (t/date-time 2017 7 1)
     :views {:fashion 5000 :design 1000}}

    {:id 1
     :url "http://example.com/1.jpg"
     :country :de
     :language :de
     :channels [:cooking :food]
     :starttime (t/date-time 2016 6 1)
     :endtime (t/date-time 2016 9 1)
     :views {:cooking 3000 :food 1000}}

    {:id 2
     :url "http://example.com/2.jpg"
     :country :pt
     :language :pt
     :channels [:fashion]
     :starttime (t/date-time 2016 1 1)
     :endtime (t/date-time 2016 12 1)
     :views {:fashion 500}}

    {:id 3
     :url "http://example.com/3.jpg"
     :country :de
     :language :en
     :channels [:cooking :food]
     :starttime (t/date-time 2016 2 1)
     :endtime (t/date-time 2016 3 1)
     :views {:cooking 1000 :food 500}}

    {:id 4
     :url "http://example.com/4.jpg"
     :country :de
     :language :de
     :channels [:cooking :food :health]
     :starttime (t/date-time 2016 10 1)
     :endtime (t/date-time 2016 11 1)
     :views {:cooking 1000 :food 0 :health 3000}}

    {:id 5
     :url "http://example.com/5.jpg"
     :country :de
     :language :de
     :channels [:cooking :food]
     :starttime (t/date-time 2016 6 1)
     :endtime (t/date-time 2016 9 1)
     :views {:cooking 3000 :food 0}}

    {:id 6
     :url "http://example.com/6.jpg"
     :country :de
     :language :de
     :channels [:cooking :food :health]
     :starttime (t/date-time 2016 8 1)
     :endtime (t/date-time 2016 11 1)
     :views {:cooking 1000 :food 0 :health 3000}}]))

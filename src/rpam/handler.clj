(ns rpam.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [rpam.repository :as repo]))

(def channels (s/enum :fashion :cooking :design :health :food))

(defmulti format-result-data type)
(defmethod format-result-data clojure.lang.PersistentVector
  [results] (map format-result-data results))
(defmethod format-result-data clojure.lang.PersistentArrayMap
  [result] (select-keys result [:id :url]))
(defmethod format-result-data nil [_] ())

(defn prepare-response [results]
  (let [code (if (empty? results) "No Content" "OK")]
    {:code code :data (format-result-data results)}))

(defn update-channel-counter [ad channel]
  (let [index (some (fn [[i m]] (when (= (:id ad) (:id m)) i))
                (map-indexed vector @repo/ads))]
    (when index (swap! repo/ads #(update-in % [index :views channel] dec)))))


(def app
  (api
    {:swagger {:ui "/"
               :spec "/swagger.json"
               :data {:info {:title "Simple Ads APi"}
                      :tags [{:name "api", :description "ads api"}]}}}

      (GET "/ads/:channel/:id" []
        :path-params [channel :- channels, id :- Long]
        :summary "single specific ad"

        (ok (let [result (repo/find-ads channel id)]
              (do
                (update-channel-counter result channel)
                (prepare-response result)))))

      (GET "/ads/:channel/:country/:language" []
        :path-params [channel :- channels,
                      country :- (s/enum :de :pt),
                      language :- (s/enum :de :en :pt)]
        :summary "list of available ads"

        (ok (let [results (repo/find-ads channel country language)]
              (do
                (map #(update-channel-counter % channel) results)
                (prepare-response results)))))))

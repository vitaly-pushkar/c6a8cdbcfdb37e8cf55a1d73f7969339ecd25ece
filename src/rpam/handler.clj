(ns rpam.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [rpam.repository :as repo]))

(def channels (s/enum :fashion :cooking :design :health :food))

(defn format-result-data [result]
  (select-keys result [:id :url]))

(defn prepare-response [results]
  (let [code (if (empty? results) "No Content" "OK")]
    {:code code :data (format-result-data results)}))

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
              (prepare-response result))))))

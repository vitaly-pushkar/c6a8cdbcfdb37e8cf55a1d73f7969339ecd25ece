(ns rpam.handlers-test
  (:require [cheshire.core :as cheshire]
            [midje.sweet :refer :all]
            [rpam.handler :refer :all]
            [ring.mock.request :as mock]
            [clj-time.core :as t]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(facts "Retrieve single ad"

  (fact "GET /ads/:id/:channel returns expected ad"
    (let [response (app (-> (mock/request :get  "/ads/fashion/0")))
          body     (parse-body (:body response))]
      (:status response) => 200
      (:code body) => "OK"
      (:data body) => {:id 0, :url "http://example.com/0.jpg"}))

  (fact "GET /ads/:id for unexisting id returns empty array"
        (let [response (app (-> (mock/request :get "/ads/fashion/42")))
              body (parse-body (:body response))]
          (:status response) => 200
          (:code body) => "No Content"
          (:data body) => [] ))

  (fact "GET /ads/:id for existing id with wrong channel returns empty array"
        (let [response (app (-> (mock/request :get "/ads/design/1")))
              body (parse-body (:body response))]
          (:status response) => 200
          (:code body) => "No Content"
          (:data body) => [] ))

  (fact "GET /ads/:id for wrong id type returns 400 error"
        (let [response (app (-> (mock/request :get "/ads/fashion/hello")))
              body (parse-body (:body response))]
          (:status response) => 400)))

(facts "Retrieve list of available ads"

  (fact "GET /ads with params returns list of suitable ads"
    (let [response (app (-> (mock/request :get "/ads/fashion/de/de")))
          body (parse-body (:body response))]
      (:status response) => 200
      (:code body) => "OK"
      (:data body) => [{:id 0, :url "http://example.com/0.jpg"}]))

  (fact "GET /ads returns only viewable ads"
    (let [response (app (-> (mock/request :get "/ads/food/de/de")))
          body (parse-body (:body response))]
      (:status response) => 200
      (:code body) => "OK"
      (:data body) => [{:id 1, :url "http://example.com/1.jpg"}]))

  (fact "GET /ads returns only started and unexpired ads"
    (let [response (app (-> (mock/request :get "/ads/health/de/de")))
          body (parse-body (:body response))]
      (:status response) => 200
      (:code body) => "OK"
      (:data body) => [{:id 6, :url "http://example.com/6.jpg"}]))

  (fact "GET /ads return empty array if none found"
    (let [response (app (-> (mock/request :get "/ads/cooking/pt/pt")))
          body (parse-body (:body response))]
      (:status response) => 200
      (:code body) => "No Content"
      (:data body) => []))

  (fact "GET /ads with incorrect channel returns 400 error"
    (let [response (app (-> (mock/request :get "/ads/clojuring/pt/pt")))
          body (parse-body (:body response))]
      (:status response) => 400)))

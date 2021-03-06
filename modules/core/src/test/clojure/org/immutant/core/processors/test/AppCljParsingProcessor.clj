;; Copyright 2008-2011 Red Hat, Inc, and individual contributors.
;; 
;; This is free software; you can redistribute it and/or modify it
;; under the terms of the GNU Lesser General Public License as
;; published by the Free Software Foundation; either version 2.1 of
;; the License, or (at your option) any later version.
;; 
;; This software is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
;; Lesser General Public License for more details.
;; 
;; You should have received a copy of the GNU Lesser General Public
;; License along with this software; if not, write to the Free
;; Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
;; 02110-1301 USA, or see the FSF site: http://www.fsf.org.

(ns org.immutant.core.processors.test.AppCljParsingProcessor
  (:use clojure.test)
  (:use immutant.test.helpers)
  (:use immutant.test.as.helpers)
  (:import [org.immutant.core.processors AppCljParsingProcessor])
  (:import [org.immutant.core ClojureMetaData])
  (:require [clojure.java.io :as io]))

(use-fixtures :each
              (harness-with [(AppCljParsingProcessor.)]))

(deftest it-should-raise-with-no-root-specified
  (is (thrown? RuntimeException
               (.deployResourceAs *harness* (io/resource "simple-descriptor.clj") "app.clj" ))))

(deftest it-should-raise-with-an-invalid-root-specified
  (is (thrown? RuntimeException
               (.deployResourceAs *harness* (io/resource "invalid-root-descriptor.clj") "app.clj" ))))

(deftest it-should-raise-with-no-init-function-specified
  (is (thrown? RuntimeException
               (.deployResourceAs *harness* (io/resource "missing-init-function-descriptor.clj") "app.clj" ))))

(deftest it-should-create-metadata-when-given-a-valid-root
  (let [unit (.deployResourceAs *harness* (io/resource "valid-root-descriptor.clj") "app.clj" )]
    (is-not (nil? (.getAttachment unit ClojureMetaData/ATTACHMENT_KEY)))))

(deftest it-should-populate-the-metadata
  (let [unit (.deployResourceAs *harness* (io/resource "valid-root-descriptor.clj") "app.clj" )
        metadata (.getAttachment unit ClojureMetaData/ATTACHMENT_KEY)]
    (are [exp val-method] (= exp (val-method metadata))
         "vfs:/tmp/"         .getRootPath
         "my.namespace/init" .getInitFunction
         "app"               .getApplicationName)))


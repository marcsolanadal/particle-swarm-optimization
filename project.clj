(defproject particle-swarm-optimization "0.1.0"
  :description "Particle Swarm Optimization Algorithm"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ^:skip-aot particle-swarm-optimization.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

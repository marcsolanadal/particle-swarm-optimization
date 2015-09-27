(ns particle-swarm-optimization.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def algo-params {:num-particles 10
                  :max-epoch 50
                  :solution-space-max 100
                  :c1 2
                  :c2 2})

(def problem-params {:target 50
                     :max-inputs 3})

(defn calculate-fitness
  "Calculates the fitness of a given particle. The greater the value the further
  is the solucion to be correct"
  [present]
  (- (:target problem-params) (apply + present)))

(defn init-particle
  "Initializes the particle with a number of inputs and a max-value for the
  search space. It returns a list with the fitness of the particle followed
  by the present solution"
  [inputs max-num]
  (let [present (vec (repeatedly inputs #(rand-int max-num)))]
    (list [(calculate-fitness present) present])))

(init-particle 2 100)

(defn new-position
  ""
  [p gbest]
  (let [present (last (first p))
        pbest (last (sort-by first p))]

    ;;(+ present velocity)
    ;;(def velocity (+ (+ (* (:c1 algo-params) (rand)) (- pbest present))
    ;;                 (+ (* (:c2 algo-params) (rand)) (- gbest present))))

    (def velocity (+ (* (:c1 algo-params) (rand)) (- pbest present)))

    (println present pbest velocity)
))

(let [particles (repeatedly 2 #(init-particle 3 100))]

  (println "particles: " particles)

  (loop [epoch 0]
    (if (< epoch 2)
      (let [gbest (last (last (last (sort-by first particles))))]

        (println "epoch" epoch "gbest:" gbest)

        ;; For each particle we calculate the velocity and new position
        (doseq [p particles]
          (println p))

        (recur (inc epoch))))))



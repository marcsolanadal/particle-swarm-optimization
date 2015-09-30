(ns particle-swarm-optimization.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def algo-params {:num-particles 10
                  :max-epoch 50
                  :c1 2
                  :c2 2})

(def problem-params {:target 50
                     :max-inputs 3
                     :space-min -100
                     :space-max 100})

(defn calculate-fitness
  "Calculates the fitness of a given particle. The greater the value the further
  is the solucion to be correct"
  [solution]
  (- (:target problem-params) (apply + solution)))

(defn init-particle
  "Initializes the particle with a number of inputs and a max-value for the
  search space. It returns a list with the fitness of the particle followed
  by the present solution"
  [inputs max-num]
  (let [position (vec (repeatedly inputs #(rand-int max-num)))]
    (conj
      (vec (repeat 2 [(calculate-fitness position) position]))
      (vec (repeat inputs 0)))))

(defn update-position
  ""
  [[[current-fitness position] [best-fitness pbest] v] [_ gbest]]

  ;;(println "gbest:" gbest)
  ;;(println "pbest:" pbest)

  ;; Calculate the velocity differential and add it to the position
  ;; TODO: Something is wrong here
  (let [dv (mapv +
                 (mapv #(* (* (:c1 algo-params) (rand)) %)
                         (mapv - pbest position))
                 (mapv #(* (* (:c2 algo-params) (rand)) %)
                       (mapv - gbest position)))
        v (mapv + v dv)
        pos (mapv + position v)
        new-fitness (calculate-fitness pos)]

    ;;(println "dv:" dv)
    ;;(println "v:" v)
    ;;(println "pos:" pos)
    ;;(println "new-fitness:" new-fitness)
    ;;(println "best-fitness:" best-fitness)

    ;;(println "new particle:"
    ;;         [[new-fitness pos]
    ;;          (if (> new-fitness best-fitness)
    ;;            [new-fitness pos]
    ;;            [current-fitness position])
    ;;          v])

    ;; Creating new particle vector
    [[new-fitness pos]
     (if (> new-fitness best-fitness)
       [new-fitness pos]
       [current-fitness position])
     v]

    ))

(defn move-particles
  ""
  [particles gbest]
  ;;(println particles)
  (loop [n 0 new-particles []]
    ;;(println (nth particles n))
    ;;(println "iteration:" n "particles:" new-particles)
    (if (< n (count particles))
      (recur (inc n)
             (conj new-particles
                   (update-position (nth particles n) gbest)))
      new-particles
      )))


(loop [epoch 0
       particles (repeatedly 2 #(init-particle 2 20))]
  ;;(println "particles" particles)
  (let [[gbest] (last (sort-by first particles))]
    ;;(println "epoch:" epoch "gbest:" gbest)
    (if (< epoch 10)
      (recur (inc epoch) (move-particles particles gbest))
      (println gbest))))



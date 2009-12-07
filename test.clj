(import 'java.lang.reflect.Array)
(defn run-test []
  (let [size (int 2000000)
	tests (int 3)
	arr1 (int-array size)
	arr2 (int-array size)]
    (println "Rozmiar:" size)
    (dotimes [test tests]
      (print "Test" test "\n pusta-unchecked: ")
      (time (loop [i (int 0)]
	      (when (< i size)
		(recur (unchecked-inc i)))))
      (print " dotimes: ")
      (time (dotimes [i size]))
      (print " pelna ")
      (time (loop [i (int 0)]
	      (when (< i size)
		;(unchecked-subtract size i)
		;(aset-int arr1 1 1 )
		(Array/setInt arr1 i i)
		(Array/setInt arr2 i (unchecked-subtract size i))
		(recur (unchecked-inc i))))))))

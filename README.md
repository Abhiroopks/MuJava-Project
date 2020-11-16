# muJava Improved

Mutation system for Java programs, including OO mutation operators. Added multithreading functionality by Abhiroop Kodandapur Sanjeeva.

Visit http://cs.gmu.edu/~offutt/mujava for details.


## Progress
* 10/10/2020
	* Added multithreading for testing phase. This works by pushing a mutant to an idle processor and allowing it to run all test-cases against the assigned mutant. Added thread-safe data structures to record results.
* 10/18/2020
	* Added checkbox in TestRun GUI for parallel functionality.
	* Allow testrun threads to timeout in case of infinite loop mutants
* 10/24/2020
	* Added multithreading for generation phase. Pushes mutant ops to idle processors. Threads will also compile mutations in parallel.
* 11/01/2020
	* Threads in generation phase compile what they create. Completed basic CLI script for generation.
* 11/08/2020
	* Parallel version also keeps mutant source code / bytecode in memory for speedup.
	* Completed CLI script for both generation and testing.
* 11/15/2020
	* Wrote script for testing single class completely (Gen mutants + run tests in one go)
	* Include runnable JAR file for simple testing on linux machines


## Notes / TODO
* Test on larger classes(1000-10,000 LOC)
* Test with more test cases
* Fix GUI to use in-memory data

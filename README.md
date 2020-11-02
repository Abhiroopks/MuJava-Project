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

## Notes
* Currently, only the GUI was used to test correctness (shouldn't matter though)
* Only tested in Eclipse IDE so far
* Need framework to test performance
* Try to improve generation phase time?

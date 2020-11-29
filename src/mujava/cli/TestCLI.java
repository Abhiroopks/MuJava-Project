package mujava.cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.concurrent.Executors;

import mujava.MutantData;
import mujava.MutationSystem;
import mujava.cli.GenMutantsCLI;
import mujava.cli.RunMutantsCLI;

public class TestCLI {

	
	public static void main(String[] args) throws Exception {
		// invalid args
		if(args.length < 4 ||
				(!args[2].equals("t") && !args[2].equals("n"))
				){
			System.out.println("Invalid arguments.\n"
					+ "arg0: Name of class to test\n"
					+ "arg1: Name of JUnit Test File\n"
					+ "arg2: t (record time) or n (no time)\n"
					+ "arg3: parallel level : 0 meaning standard version is used");
			return;
		}
		
		// initialize some booleans for timing and parallel
		
		MutationSystem.timing = args[2].equals("t");
		// how many threads to use in multithreading
		MutationSystem.parallelLevel = Integer.parseInt(args[3]);
		// whether to use standard or new version (useful for testing)
		MutationSystem.isParallel = MutationSystem.parallelLevel > 0 ? true : false;

		if(MutationSystem.isParallel) {
			MutationSystem.executorService = Executors.newWorkStealingPool(MutationSystem.parallelLevel);
		}
		

		// Mutant Gen reports alot of errors when creating bad mutants. Redirect to file instead of console
		File errfile = new File("errfile.txt");
		FileOutputStream fileOutputStream=new FileOutputStream(errfile);
		PrintStream printStream=new PrintStream(fileOutputStream);		
		System.setErr(printStream);			
		
		
		if(MutationSystem.timing) {
			// Write timing information to file instead of console. Cleaner
			File timings = new File("timings.txt");
			FileWriter fileWriter = new FileWriter("timings.txt");
		    MutationSystem.writer = new PrintWriter(fileWriter);
		}
		
		long start = System.currentTimeMillis();
		// generate mutants and hold them in memory
		GenMutantsCLI gmcli = new GenMutantsCLI();
		gmcli.GenMutes(args[0],args[3]);

		
		
		// Run tests on mutants
		
		RunMutantsCLI rmcli = new RunMutantsCLI();
		rmcli.RunMutes(args[0], args[1], args[2]);
		
		if(MutationSystem.timing) {
			MutationSystem.recordTime("Total time: " + (System.currentTimeMillis() - start));
		}
		
		if(MutationSystem.timing) {
			MutationSystem.writer.close();
		}
		System.exit(0);
		
		
		
		
	}

}

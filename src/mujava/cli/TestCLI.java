package mujava.cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import mujava.MutantData;
import mujava.MutationSystem;
import mujava.cli.GenMutantsCLI;
import mujava.cli.RunMutantsCLI;

public class TestCLI {

	
	public static void main(String[] args) throws Exception {
		// invalid args
		if(args.length < 3) {
			System.out.println("Invalid arguments.\n"
					+ "arg0: Name of class to test\n"
					+ "arg1: Name of JUnit Test File\n"
					+ "arg2: parallel level : 0 meaning standard version is used\n"
					+ "-r: record time\n"
					+ "-t [timeout]: custom test timeout in seconds");
			return;
		}
		
		// initialize some booleans for timing and parallel
		List<String> arglist = (List<String>) Arrays.asList(args);
		
		MutationSystem.timing = arglist.contains("-r");
		// how many threads to use in multithreading
		MutationSystem.parallelLevel = Integer.parseInt(args[2]);
		if(MutationSystem.parallelLevel < 0) {System.out.println("Cannot use negative parallel level."); return;}
		// whether to use standard or new version (useful for testing)
		MutationSystem.isParallel = MutationSystem.parallelLevel > 0 ? true : false;

		if(MutationSystem.isParallel) {
			MutationSystem.executorService = Executors.newWorkStealingPool(MutationSystem.parallelLevel);
		}
		
		int timeout = 0;
		int timeoutindex = arglist.indexOf("-t");
		if(timeoutindex != -1) {
			try {
				timeout = 1000 * Integer.parseInt(args[timeoutindex + 1]);
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("must specify timeout if using -t");
				return;
			}
		}
		

		// Mutant Gen reports alot of errors when creating bad mutants. Redirect to file instead of console
		File errfile = new File("errfile" + args[0] + args[2] + ".txt");
		FileOutputStream fileOutputStream=new FileOutputStream(errfile);
		PrintStream printStream=new PrintStream(fileOutputStream);		
		System.setErr(printStream);			
		
		
		if(MutationSystem.timing) {
			// Write timing information to file instead of console. Cleaner
			File timings = new File("timings" + args[0] + args[2] + ".txt");
			FileWriter fileWriter = new FileWriter(timings);
		    MutationSystem.writer = new PrintWriter(fileWriter);
		}
		
		long start = System.currentTimeMillis();
		// generate mutants and hold them in memory
		GenMutantsCLI gmcli = new GenMutantsCLI();
		gmcli.GenMutes(args[0]);

		
		
		// Run tests on mutants
		

		
		RunMutantsCLI rmcli = new RunMutantsCLI();
		rmcli.RunMutes(args[0], args[1], "trad", timeout);
		
		if(MutationSystem.timing) {
			MutationSystem.recordTime("Total time: " + (System.currentTimeMillis() - start));
			MutationSystem.writer.close();

		}
		
		System.exit(0);
		
		
		
		
	}

}

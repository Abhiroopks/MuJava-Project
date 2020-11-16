package mujava.cli;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import mujava.MutantData;
import mujava.MutationSystem;
import mujava.cli.GenMutantsCLI;
import mujava.cli.RunMutantsCLI;

public class TestCLI {

	
	public static void main(String[] args) throws Exception {
		// invalid args
		if(args.length != 4 ||
				(!args[3].equals("p") && !args[3].equals("s")) ||
				(!args[2].equals("t") && !args[2].equals("n"))
				){
			System.out.println("Invalid arguments.\n"
					+ "arg0: Name of class to test\n"
					+ "arg1: Name of JUnit Test File\n"
					+ "arg2: t (record time) or n (no time)\n"
					+ "arg3: p(parallel) or s(sequential)");
			return;
		}
		
		// initialize some booleans for timing and parallel
		
		MutationSystem.timing = args[2].equals("t");
		MutationSystem.isParallel = args[3].equals("p");
		

		// Mutant Gen reports alot of errors when creating bad mutants. Redirect to file instead of console
		System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
                //DO NOTHING
            }
        }));		

		
		
				
		
		long start = System.currentTimeMillis();
		// generate mutants and hold them in memory
		GenMutantsCLI gmcli = new GenMutantsCLI();
		MutantData md = gmcli.GenMutes(args[0],args[3]);
		
		
		// Run tests on mutants
		
		RunMutantsCLI rmcli = new RunMutantsCLI();
		rmcli.RunMutes(args[0], args[1], args[2], md);
		
		if(MutationSystem.timing) {
			System.out.println("Total time: " + (System.currentTimeMillis() - start));
		}
		
		System.exit(0);
		
		
		
		
	}

}

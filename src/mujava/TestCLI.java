package mujava;

import mujava.cli.GenMutantsCLI;
import mujava.cli.RunMutantsCLI;

public class TestCLI {

	
	public static void main(String[] args) throws Exception {
		
		// invalid args
		if(args.length != 4 ||
				(!args[3].equals("-p") && !args[3].equals("-s")) ||
				(!args[2].equals("class") && !args[2].equals("trad") && !args[2].equals("both"))
				){
			System.out.println("Invalid arguments.\n"
					+ "arg0: Name of class to test\n"
					+ "arg1: Name of JUnit Test File\n"
					+ "arg2: class, trad, or both\n"
					+ "arg3: -p(parallel) or -s(sequential)");
			return;
		}
		
				
		
		long start = System.currentTimeMillis();
		// generate mutants and hold them in memory
		GenMutantsCLI gmcli = new GenMutantsCLI();
		MutantData md = gmcli.GenMutes(args[0],args[3],args[2]);
		
		
		// Run tests on mutants
		
		RunMutantsCLI rmcli = new RunMutantsCLI();
		rmcli.RunMutes(args[0], args[1], args[2], md);
		
		System.out.println("Total time: " + (System.currentTimeMillis() - start));
		
		
		System.exit(0);
		
		
		
		
	}

}

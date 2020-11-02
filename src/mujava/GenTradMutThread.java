package mujava;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

import com.sun.tools.javac.Main;

import mujava.cli.Util;
import mujava.op.util.Mutator;
import mujava.util.Debug;
import openjava.mop.FileEnvironment;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;


public class GenTradMutThread implements Callable<Void> {

	String traditionalOp;
	Mutator mutant_op;
	FileEnvironment file_env;
	ClassDeclaration cdecl;
	CompilationUnit comp_unit;
	

	//Constructor
	GenTradMutThread(String op, FileEnvironment fe, ClassDeclaration cd, CompilationUnit cu){
		
		this.traditionalOp = op;
		this.file_env = fe;
		this.cdecl = cd;
		this.comp_unit = cu;

		
	}
	
	// generates and compiles a traditional mutant
	@Override
	public Void call() throws Exception {
		
		// create Mutator object for this specific traditional mutant object
		try {
			
			// be sure to prepend the package name to classname
			Class myClass = Class.forName("mujava.op.basic." + this.traditionalOp);
	
			// classes of the parameters used in constructor
			Class[] types = {this.file_env.getClass(),this.cdecl.getClass(),this.comp_unit.getClass()};
			
			Constructor constructor = myClass.getConstructor(types);
	
			// parameters for constructor
			Object[] parameters = {this.file_env,this.cdecl,this.comp_unit};
			mutant_op = (Mutator) constructor.newInstance(parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// generate mutant
		this.comp_unit.accept(mutant_op);
		
		
		// Compile mutants
		 String[] pars = new String[3];
	     pars[0] = "-classpath";
	     pars[1] = MutationSystem.CLASS_PATH;
	     
	     for(String mut : mutant_op.mutfiles) {
	    	 try
	         {
	        // result = 0 : SUCCESS,   result = 1 : FALSE
	        //int result = Main.compile(pars,new PrintWriter(new FileOutputStream("temp")));
	        	 
	        	 /*
	        	  * 12/19/13 Lin modified:
	        	  * if not in debug mode, for not showing the compile result when some 
	        	  * mutants can't pass compiler
	        	  * if in debug mode, display
	        	  */
	    		 
	    		 	pars[2] = mut;
	        	 
	 				int result;
	 				if (!Util.debug)
	 					result = Main.compile(pars);
	 				else {
	 					File tempCompileResultFile = new File(
	 							MutationSystem.SYSTEM_HOME + "/compile_output");
	 					PrintWriter out = new PrintWriter(tempCompileResultFile);

	 					result = Main.compile(pars, out);
	 					tempCompileResultFile.delete();
	 				}
	            
	            if (result == 0)
	            {
	               Debug.print("+" + mut + "   ");
	               //counter++;
	            }
	            else
	            {
	               Debug.print("-" + mut + "   ");
	             // delete directory
	               File dir_name = new File(MutationSystem.MUTANT_PATH + "/" + mut);
	               File[] mutants = dir_name.listFiles();
	               boolean tr = false;
	               
	               for (int j=0; j<mutants.length; j++)
	               {
	            // [tricky solution] It can produce loop -_-;;
	                  while (!tr)
	                  {
	                     tr = mutants[j].delete();
	                  }
	                  tr = false;
	               }
	               
	               while (!tr)
	               {
	                  tr = dir_name.delete();
	               }
	            }
	         } 
	         catch (Exception e)
	         {
	            System.err.println(e);
	         }
	     }

        

		return null;
	}

}

package mujava;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.Vector;
import java.util.concurrent.Callable;

import com.sun.tools.javac.Main;

import mujava.op.util.Mutator;
import mujava.util.Debug;
import mujava.util.ExtensionFilter;
import mujava.util.MutantDirFilter;
import openjava.mop.FileEnvironment;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import mujava.cli.Util;
import mujava.op.basic.*;


public class CompMutantThread implements Callable<Void> {

	String target;
	File target_dir;

	//Constructor
	CompMutantThread(String mut_dir){
		this.target = mut_dir;
	}
	
	// compiles a traditional mutant
	@Override
	public Void call() throws Exception {
		target_dir = new File(target);
        String[] target_file = target_dir.list(new ExtensionFilter("java"));
        String fileName = target_file[0];

        Vector v = new Vector();
        for (int j=0; j<target_file.length; j++)
        {
           v.add(target + "/" + target_file[j]);
        }

        String[] pars = new String[v.size()+2];

        pars[0] = "-classpath";
        pars[1] = MutationSystem.CLASS_PATH;
        for (int j=0; j<v.size(); j++)
        {
           pars[2+j] = v.get(j).toString();
        }
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
       	 
				int result;
				if (Util.debug)
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
              Debug.print("+" + target + "   ");
              //counter++;
           }
           else
           {
              Debug.print("-" + target + "   ");
            // delete directory
              File dir_name = new File(MutationSystem.MUTANT_PATH + "/" + target);
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
        
		return null;
	}

}

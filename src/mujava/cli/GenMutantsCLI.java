package mujava.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;


import mujava.ClassMutantsGenerator;
import mujava.GenTradMutThread;
import mujava.MutantData;
import mujava.MutationSystem;
import mujava.OGTraditionalMutantsGenerator;
import mujava.OpenJavaException;
import mujava.TraditionalMutantsGenerator;

public class GenMutantsCLI {
	
	public MutantData GenMutes(String className, String parallel, String mutantTypes) throws Exception {
	      try {
		  MutationSystem.setJMutationStructure();
	      }
	      catch (NoClassDefFoundError e) {
		  System.err.println("[ERROR] Could not find one of the classes necessary to run muJava. Make sure that the .jar file for openjava is in your classpath.");
		  System.err.println();
		  e.printStackTrace();
		  return null;
	      }
	      MutationSystem.recordInheritanceRelation();		
	      
	      // simply mutate all files in src directory of target
//	      Vector<String> file_list = MutationSystem.getNewTargetFiles();
//	      if(file_list.size() == 0) {
//	          System.out.println("[ERROR] No files found to mutate in dir: " + MutationSystem.CLASS_PATH);
//	      }
	      
	      String file_name = className + ".java";
	      
	      // get all traditional mutation operators
	      String[] traditional_ops = MutationSystem.tm_operators;
	      
	      
	   // iterate over each class file selected
	      long start = System.currentTimeMillis();
	      	      
//	      for (int i=0; i<file_list.size(); i++)
//	      {
	      // file_name = ABSTRACT_PATH - MutationSystem.SRC_PATH
	      // For example: org/apache/bcel/Class.java
	      
         MutantData mutantData = null;

	      
         try
         {
            //System.out.println(i + " : " + file_name);
            // [1] Examine if the target class is interface or abstract class
            //     In that case, we can't apply mutation testing.

            // Generate class name from file_name
            String temp = file_name.substring(0,file_name.length()-".java".length());
            String class_name="";
            
            for (int j=0; j<temp.length(); j++)
            {
               if ( (temp.charAt(j) == '\\') || (temp.charAt(j) == '/') )
               {
                  class_name = class_name + ".";
               } 
               else
               {
                  class_name = class_name + temp.charAt(j);
               }
            }
           
            int class_type = MutationSystem.getClassType(class_name);
            
			if (class_type == MutationSystem.NORMAL)
			{   // do nothing?
			} 
			else if (class_type == MutationSystem.MAIN)
			{
               System.out.println(" -- "  + file_name + " class contains 'static void main()' method.");
               System.out.println("    Pleas note that mutants are not generated for the 'static void main()' method");
            }
			//Added on 1/19/2013, no mutants will be generated for a class having only one main method
			else if(class_type == MutationSystem.MAIN_ONLY){
				System.out.println("Class " + file_name + " has only the 'static void main()' method and no mutants will be generated.");
				return mutantData;
			}

			
            // [2] Apply mutation testing
            setMutationSystemPathFor(file_name);
            //File[] original_files = new File[1];
            //original_files[0] = new File(MutationSystem.SRC_PATH,file_name);
            
            File original_file = new File(MutationSystem.SRC_PATH, file_name);
            

           
            /*AllMutantsGenerator genEngine;
            genEngine = new AllMutantsGenerator(original_file,class_ops,traditional_ops);
            genEngine.makeMutants();
            genEngine.compileMutants();*/
            
            
            
            //do not generate class mutants if no class mutation operator is selected
            /*
            ClassMutantsGenerator cmGenEngine;
            if(class_ops != null){
	            cmGenEngine = new ClassMutantsGenerator(original_file,class_ops);   
	            long start = System.currentTimeMillis();
	            cmGenEngine.makeMutants();      
	            long end = System.currentTimeMillis();
	            System.out.println("Class Mutants Gen time:" + (end-start));
	            start = System.currentTimeMillis();
	            cmGenEngine.compileMutants();
	            end = System.currentTimeMillis();
	            System.out.println("Class Mutants Comp time:" + (end-start));
            }
            */
            
            
            
            //do not generate traditional mutants if no class traditional operator is selected
            if(traditional_ops != null){
	            OGTraditionalMutantsGenerator tmGenEngine;

	            
			  // use parallel executor
			  if(parallel.equals("-p")){
				  mutantData = new MutantData();
				  MutationSystem.isParallel = true;
		          System.out.println("Running Parallel");
		          // empty maps for source code and bytecode
				  tmGenEngine = new TraditionalMutantsGenerator(original_file,traditional_ops,mutantData);
				  tmGenEngine.makeMutants();
			  }
			  else {
				  MutationSystem.isParallel = false;
				  System.out.println("Running Seq");
			    tmGenEngine = new OGTraditionalMutantsGenerator(original_file,traditional_ops);
			    tmGenEngine.makeMutants();
			    tmGenEngine.compileMutants();
			  }

            }

         } catch (OpenJavaException oje)
         {
            System.out.println("[OJException] " + file_name + " " + oje.toString());
            //System.out.println("Can't generate mutants for " +file_name + " because OpenJava " + oje.getMessage());
            deleteDirectory();
         } catch(Exception exp)
         {
            System.out.println("[Exception] " + file_name + " " + exp.toString());
            exp.printStackTrace();
            //System.out.println("Can't generate mutants for " +file_name + " due to exception" + exp.getClass().getName());
            //exp.printStackTrace();
            deleteDirectory();
         } catch(Error er)
         {
            System.out.println("[Error] " + file_name + " " + er.toString());
            System.out.println("MutantsGenPanel: ");
            er.printStackTrace();

            //System.out.println("Can't generate mutants for " +file_name + " due to error" + er.getClass().getName());
            deleteDirectory();
         }
	      
	      
	      
          long end = System.currentTimeMillis();
          System.out.println("Mutants Gen Time:" + (end-start));
          
	      System.out.println("------------------------------------------------------------------");
	      System.out.println("All files are handled");
	      
	      // used by parallel executor if necessary
	      return mutantData;
		
	}

	public static void main(String[] args) throws Exception {
		
		if(args.length != 3 || (!args[2].equals("-p") && !args[2].equals("-s"))) {
			System.out.println("Invalid arguments.\n"
					+ "arg0: Name of class to test\n"
					+ "arg1: class, trad, or both\n"
					+ "arg2: -p or -s");
			return;
		}
				
		GenMutantsCLI gmcli = new GenMutantsCLI();
		gmcli.GenMutes(args[0],args[1],args[2]);
      }
	
	void setMutationSystemPathFor(String file_name)
	   {
	      try
	      {
	         String temp;
	         temp = file_name.substring(0, file_name.length()-".java".length());
	         temp = temp.replace('/', '.');
	         temp = temp.replace('\\', '.');
	         int separator_index = temp.lastIndexOf(".");
	         
	         if (separator_index >= 0)
	         {
	            MutationSystem.CLASS_NAME=temp.substring(separator_index+1, temp.length());
	         }
	         else
	         {
	            MutationSystem.CLASS_NAME = temp;
	         }

	         String mutant_dir_path = MutationSystem.MUTANT_HOME + "/" + temp;
	         File mutant_path = new File(mutant_dir_path);
	         mutant_path.mkdir();

	         String class_mutant_dir_path = mutant_dir_path + "/" + MutationSystem.CM_DIR_NAME;
	         File class_mutant_path = new File(class_mutant_dir_path);
	         class_mutant_path.mkdir();

	         String traditional_mutant_dir_path = mutant_dir_path + "/" + MutationSystem.TM_DIR_NAME;
	         File traditional_mutant_path = new File(traditional_mutant_dir_path);
	         traditional_mutant_path.mkdir();

	         String original_dir_path = mutant_dir_path + "/" + MutationSystem.ORIGINAL_DIR_NAME;
	         File original_path = new File(original_dir_path);
	         original_path.mkdir();

	         MutationSystem.CLASS_MUTANT_PATH = class_mutant_dir_path;
	         MutationSystem.TRADITIONAL_MUTANT_PATH = traditional_mutant_dir_path;
	         MutationSystem.ORIGINAL_PATH = original_dir_path;
	         MutationSystem.DIR_NAME = temp;
	      } catch(Exception e)
	      {
	         System.err.println(e);
	      }
	   }
	
	void deleteDirectory()
	   {
	      File originalDir = new File(MutationSystem.MUTANT_HOME + "/" + MutationSystem.DIR_NAME
	                                + "/" + MutationSystem.ORIGINAL_DIR_NAME);
	      while (originalDir.delete())  
	      {    // do nothing?
	      }

	      File cmDir = new File(MutationSystem.MUTANT_HOME + "/" + MutationSystem.DIR_NAME
	                                + "/" + MutationSystem.CM_DIR_NAME);
	      while (cmDir.delete()) 
	      {    // do nothing?
	      }

	      File tmDir = new File (MutationSystem.MUTANT_HOME + "/" + MutationSystem.DIR_NAME
	                                + "/" + MutationSystem.TM_DIR_NAME);
	      while (tmDir.delete())
	      {    // do nothing?
	      }

	      File myHomeDir = new File(MutationSystem.MUTANT_HOME + "/" + MutationSystem.DIR_NAME);
	      while (myHomeDir.delete())
	      {    // do nothing?
	      }
	   }
	      
	      
	      
		
		
		

	

}

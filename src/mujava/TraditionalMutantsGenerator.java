/**
 * Copyright (C) 2015  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
 
/**
 * <p>Generate traditional mutants according to selected 
 *         operator(s) from gui.GenMutantsMain. 
 *         The original version is loaded, mutated, and compiled. 
 *         Outputs (mutated source and class files) are in 
 *         the traditional-mutants folder. </p>
 *         
 * <p>Currently available traditional mutation operators:
 *         (1) AORB: Arithmetic Operator Replacement (Binary),    
 *         (2) AORU: Arithmetic Operator Replacement (Unary), 
 *         (3) AORS: Arithmetic Operator Replacement (Short-cut), 
 *         (4) AODU: Arithmetic Operator Deletion (Unary), 
 *         (5) AODS: Arithmetic Operator Deletion (Short-cut), 
 *         (6) AOIU: Arithmetic Operator Insertion (Unary), 
 *         (7) AOIS: Arithmetic Operator Insertion (Short-cut), 
 *         (8) ROR: Rational Operator Replacement, 
 *         (9) COR: Conditional Operator Replacement,  
 *        (10) COD: Conditional Operator Deletion, 
 *        (11) COI: Conditional Operator Insertion,  
 *        (12) SOR: Shift Operator Replacement, 
 *        (13) LOR: Logical Operator Replacement, 
 *        (14) LOI: Logical Operator Insertion, 
 *        (15) LOD: Logical Operator Deletion, 
 *        (16) ASRS: Assignment Operator Replacement (short-cut) 
 * </p>
 * @author Yu-Seung Ma
 * @version 1.0
 * 
 * Taking out aor_flag for not clear about the reason of using it.
 * Lin Deng, Aug 23
 * 
*/
package mujava;

import openjava.ptree.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import mujava.op.basic.*;
import mujava.op.util.*;
import mujava.util.Debug;


public class TraditionalMutantsGenerator extends OGTraditionalMutantsGenerator
{
   String[] traditionalOp;
   ExecutorService executorService = Executors.newWorkStealingPool();
   ConcurrentHashMap<String,String> mutantSource = null;
   ConcurrentHashMap<String,byte[]> mutantClass = null;


   public TraditionalMutantsGenerator(File f) 
   {
      super(f);
      traditionalOp = MutationSystem.tm_operators;
      this.mutantSource = MutationSystem.md.mutantSource;
      this.mutantClass = MutationSystem.md.mutantClass;
      
   }
   
   public TraditionalMutantsGenerator(File f, boolean debug) 
   {
      super (f, debug);
      traditionalOp = MutationSystem.tm_operators;
      this.mutantSource = MutationSystem.md.mutantSource;
      this.mutantClass = MutationSystem.md.mutantClass;
   }

   public TraditionalMutantsGenerator(File f, String[] tOP) 
   {
      super(f);
      traditionalOp = tOP;
      this.mutantSource = MutationSystem.md.mutantSource;
      this.mutantClass = MutationSystem.md.mutantClass;
   }

   /** 
    * Verify if the target Java source and class files exist, 
    * generate traditional mutants
    */
   void genMutants()
   {
      if (comp_unit == null)
      {
         System.err.println (original_file + " is skipped.");
      }
      
      ClassDeclarationList cdecls = comp_unit.getClassDeclarations();
      
      if (cdecls == null || cdecls.size() == 0) 
         return;

      if (traditionalOp != null && traditionalOp.length > 0)
      {
	     Debug.println("* Generating traditional mutants");
         MutationSystem.clearPreviousTraditionalMutants();

         MutationSystem.MUTANT_PATH = MutationSystem.TRADITIONAL_MUTANT_PATH;

         CodeChangeLog.openLogFile();

         genTraditionalMutants(cdecls);

         CodeChangeLog.closeLogFile();
      }
   }

   /**
    * Compile traditional mutants into bytecode 
    */
   
   
//   public void compileMutants()
//   {
//      if (traditionalOp != null && traditionalOp.length > 0)
//      {
//         try
//         {
//            Debug.println("* Compiling traditional mutants into bytecode");
//            String original_tm_path = MutationSystem.TRADITIONAL_MUTANT_PATH;
//            File f = new File(original_tm_path, "method_list");
//            FileReader r = new FileReader(f);
//            BufferedReader reader = new BufferedReader(r);
//            String str = reader.readLine();
//            
//            //ArrayList<CompMutantThread> compthreads = new ArrayList<CompMutantThread>();
//            ArrayList<Future<Void>> futures = new ArrayList<Future<Void>>();
//            
//            
//            // iterate over each method of this class
//            while (str != null){
//            	String mut_path = original_tm_path + "/" + str;
//            	//MutationSystem.MUTANT_PATH = original_tm_path + "/" + str;
//            	//super.compileMutants();
//            	File muts = new File(mut_path);
//            	// list of all mutants for this method
//     	       	String[] s = muts.list(new MutantDirFilter());
//     	       	
//     	       	//submit each task to the exec service immediately
//     	       	for(int i = 0; i < s.length; i++) {
//     	       		futures.add(executorService.submit(new FileGenThread(mut_path + "/" + s[i])));
//     	       	}
////     	       	for(String mut : s) {
////         	       	compthreads.add(new CompMutantThread(mut_path + "/" + mut));
////         	       	executorService.su
////     	       	}
//
//     	       	str = reader.readLine();
//     	       	
//            }
//            reader.close();
//            MutationSystem.MUTANT_PATH = original_tm_path;
//            
//            // wait for each to finish
//            for(Future<Void> fut : futures) {
//            	fut.get();
//            }
//            
////            // invoke all the tasks to thread pool and block until ALL finish
////            // shouldn't get stuck because each task has time limit
////            executorService.invokeAll(compthreads).forEach(t -> {
////       		try {
////       			t.get();
////       		} catch (InterruptedException | ExecutionException e) {
////       			e.printStackTrace();
////       		}
////       	});
// */
//            
//            
//         } catch (Exception e)
//         {
//        	e.printStackTrace();
//            System.err.println("Error at compileMutants() in TraditionalMutantsGenerator.java");
//         }
//      }
//   }

   /**
    * Apply selected traditional mutation operators: 
    *      AORB, AORS, AODU, AODS, AOIU, AOIS, ROR, COR, COD, COI,
    *      SOR, LOR, LOI, LOD, ASRS, SID, SWD, SFD, SSD 
    * @param cdecls
    */
   void genTraditionalMutants(ClassDeclarationList cdecls){
	   
      for (int j=0; j<cdecls.size(); ++j)
      {
         ClassDeclaration cdecl = cdecls.get(j);
         //take care of the case for generics
         String tempName = cdecl.getName();
         if(tempName.indexOf("<") != -1 && tempName.indexOf(">")!= -1)
        	 tempName = tempName.substring(0, tempName.indexOf("<")) + tempName.substring(tempName.lastIndexOf(">") + 1, tempName.length());

         if (tempName.equals(MutationSystem.CLASS_NAME))
         {
            try
            {
               mujava.op.util.Mutator mutant_op;
//               boolean AOR_FLAG = false;
     
               try
               {
                  //generate a list of methods from the original java class
            	  //System.out.println("MutationSystem.MUTANT_PATH: " + MutationSystem.MUTANT_PATH);
                  File f = new File(MutationSystem.MUTANT_PATH, "method_list");
                  FileOutputStream fout = new FileOutputStream(f);
                  PrintWriter out = new PrintWriter(fout);

                  mutant_op = new CreateDirForEachMethod(file_env, cdecl, comp_unit, out);

                  comp_unit.accept(mutant_op);
                  out.flush();  
                  out.close();
               } catch (Exception e)
               {
                  System.err.println("Error in writing method list");
                  return;
               }
               
               
	               // MAKE ALL TRAD MUTATIONS HERE
	               ArrayList<Future<Void>> futures = new ArrayList<Future<Void>>();

	               
	               // submit generation tasks to processors
	               // each thread should use a local map, then merge with global
	               
	               long start = System.currentTimeMillis();
	               
	               for(String mut : traditionalOp) {
	            	   futures.add(executorService.submit(new GenTradMutThread(mut,file_env,cdecl,comp_unit,mutantSource)));
	               }
	               // wait for all tasks to finish
	               for(Future<Void> fut : futures) {
	            	   fut.get();
	               }
	               
	               long end = System.currentTimeMillis();
	               
	               if(MutationSystem.timing) {
	            	   MutationSystem.recordTime("Gen time: " + (end-start));
	               }
	              	               
	               // compile
	               
	               
	               ArrayList<String> options = new ArrayList<String>(2);
	               options.add("-cp");
	               options.add(MutationSystem.CLASS_PATH);
	               
	               JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

	               futures.clear();
	               
	               start = System.currentTimeMillis();

	               // now compile each mutant and store in memory
	               for(Map.Entry<String,String> e : mutantSource.entrySet()) {
	            	   futures.add(executorService.submit(new CompMutThread(mutantSource, e, mutantClass, tempName,options,compiler)));
	               }
	               
	               // wait for all tasks to finish
	               for(Future<Void> fut : futures) {
	            	   fut.get();
	               }
	               
	               end = System.currentTimeMillis();
	               
	               if(MutationSystem.timing) {
	            	   MutationSystem.recordTime("Compile time: " + (end-start));
	               }
	               
	               
	               
	               //ensure if any compilations failed,
	               //threads removed the corresponding sourcecode as well
	               assert(mutantSource.size() == mutantClass.size());



            	}catch (InterruptedException | ExecutionException e) {
	            	System.err.println("Trad mutant gen thread execution interrupted exception.");
					e.printStackTrace();
            	}
         }
      }
   }
}
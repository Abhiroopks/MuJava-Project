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
 
 
package mujava;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import mujava.test.*;
import mujava.util.*;

import org.junit.*;
import org.junit.experimental.ParallelComputer;
import org.junit.internal.RealSystem;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runners.*;

/**
 * <p>Description: </p>
 * @author Yu-Seung Ma
 * @update by Nan Li May 2012 integration with JUnit
 * @version 1.0
  */

public class TestExecuter {
  Object lockObject = new Object();

  //int TIMEOUT = 3000;
  int TIMEOUT;
  final int MAX_TRY = 100;

  Class original_executer;
  Object original_obj;        // instancitation of the test set class
  volatile Object mutant_result;


  Class mutant_executer;      // test set class for a mutant
  volatile Object mutant_obj;          // test set object for a mutant

  Method[] testCases;
  volatile Method testcase;

  String whole_class_name;
  String testSet;
  boolean mutantRunning = true;
  
  //original test results
  Map<String, String> originalResults = new HashMap<String, String>();
  //results for mutants
  Map<String, String> mutantResults = null;
  //JUnit test cases
  List<String> junitTests = new ArrayList<String>();
  //result of a test case
  Result result = null;
  //results as to how many mutants are killed by each test  
  Map<String, String> finalTestResults = new HashMap<String, String>(1);
  //results as to how many tests can kill each single mutant  
  Map<String, String> finalMutantResults = new HashMap<String, String>(1);
  
  //ExecutorService executorService = Executors.newWorkStealingPool();
  
  // holds maps for source code and bytecode of each mutant
  MutantData mutantData;
  

  /**
   * @param targetClassName name of class to test
   */
  public TestExecuter(String targetClassName) {
	  
	// assume the md in MutationSystem has been populated by generator
	this.mutantData = MutationSystem.md;

    int index = targetClassName.lastIndexOf(".");
    if(index<0){
      MutationSystem.CLASS_NAME = targetClassName;
    }else{
      MutationSystem.CLASS_NAME = targetClassName.substring(index+1,targetClassName.length());
    }

    MutationSystem.DIR_NAME = targetClassName;
    MutationSystem.CLASS_MUTANT_PATH = MutationSystem.MUTANT_HOME+"/"+targetClassName
                                      +"/"+MutationSystem.CM_DIR_NAME;
    MutationSystem.TRADITIONAL_MUTANT_PATH = MutationSystem.MUTANT_HOME+"/"+targetClassName
                                      +"/"+MutationSystem.TM_DIR_NAME;
    MutationSystem.EXCEPTION_MUTANT_PATH = MutationSystem.MUTANT_HOME+"/"+targetClassName
                                      +"/"+MutationSystem.EM_DIR_NAME;

    whole_class_name = targetClassName;

  }

	public void setTimeOut(int msecs){
		TIMEOUT = msecs;
	}

	 public boolean readTestSet(String testSetName){
		    try{
		        testSet = testSetName;
		        // Class loader for the testset
		        //OriginalLoader myLoader = new OriginalLoader();
		        
		        // load the original class
		      	JMutationLoader mutantLoader = new JMutationLoader();
		        mutantLoader.loadOriginal(whole_class_name);
		        
		        
		        System.out.println(testSet);
		        original_executer = mutantLoader.loadTestClass(testSet);
		        
		        System.out.println("Test class loaded: " + original_executer.getName());
		                		
//				original_obj = original_executer.newInstance();
//				
//		        if(original_obj == null){
//		          System.out.println("Can't instantiace original object");
//		          return false;
//		        }

		        // read testcases from the test set class
		        testCases = original_executer.getDeclaredMethods();
		        if(testCases==null){
		          System.out.println(" No test case exist ");
		          return false;
		        }
		    }catch(Exception e){
		      System.err.println(e);
		      return false;
		    }
		    return true;

  }

  boolean sameResult(Object result1,Object result2){
    if( !(result1.toString().equals(result2.toString())) ) return false;
    return true;
  }

  public TestResultParallel runClassMutants()  throws NoMutantException,NoMutantDirException{
    MutationSystem.MUTANT_PATH = MutationSystem.CLASS_MUTANT_PATH;
    TestResultParallel test_result = new TestResultParallel();
    runMutants(test_result, "");
    return test_result;
  }

  public TestResultParallel runExceptionMutants()  throws NoMutantException,NoMutantDirException{
    MutationSystem.MUTANT_PATH = MutationSystem.EXCEPTION_MUTANT_PATH;
    TestResultParallel test_result = new TestResultParallel();
    runMutants(test_result, "");
    return test_result;
  }

  public TestResultParallel runTraditionalMutants(String methodSignature)  throws NoMutantException,NoMutantDirException{

    MutationSystem.MUTANT_PATH = MutationSystem.TRADITIONAL_MUTANT_PATH;
    String original_mutant_path = MutationSystem.MUTANT_PATH;

    TestResultParallel test_result = new TestResultParallel();
    
    long start = System.currentTimeMillis();


    if(methodSignature.equals("All method")){
        try{
          File f = new File(MutationSystem.TRADITIONAL_MUTANT_PATH, "method_list");
          FileReader r = new FileReader(f);
          BufferedReader reader = new BufferedReader(r);
          String readSignature = reader.readLine();
          while(readSignature != null){

            MutationSystem.MUTANT_PATH = original_mutant_path + "/" + readSignature;
            try{
              runMutants(test_result, readSignature);
            }catch(NoMutantException e){
            }
            readSignature = reader.readLine();
          }
          
          reader.close();
        }catch(Exception e){
          System.err.println("[WARNING] A problem occurred when running the traditional mutants:");
		  System.err.println();
		  e.printStackTrace();
      }
    }
    else{
      MutationSystem.MUTANT_PATH = original_mutant_path + "/" + methodSignature;
      runMutants(test_result, methodSignature);
    }
    
    long end = System.currentTimeMillis();
    
    if(MutationSystem.timing) {
    	MutationSystem.recordTime("Parallel test time: "+ (end-start));
    }
    
    double mutant_score = (double)test_result.killed_mutants.size() /
    		(test_result.live_mutants.size() + test_result.killed_mutants.size());
    System.out.printf("Mutation Score : %f\n", mutant_score);

    
    return test_result;
  }
 /**
  * get the result of the test under the mutanted program
  * @deprecated
  * @param mutant
  * @param testcase
  * @throws InterruptedException
  */
  
  /*
  void runMutants(Object mutant,Method testcase) throws InterruptedException{
    mutantRunning = true;
    try{
      // testcase execution
      mutant_result = testcase.invoke(mutant_obj,null);
    }catch(Exception e){
      // execption occurred -> abnormal execution
      mutant_result = e.getCause().getClass().getName()+" : "  +e.getCause().getMessage();
    }
    mutantRunning = false;
    synchronized(lockObject){
    lockObject.notify();
    }
    //throw new InterruptedException();
  }
*/
  
  synchronized void waitUntilAtLeast(long timeOut) throws InterruptedException{
    wait(timeOut);
  }
  
  /**
   * get the mutants for one method based on the method signature
   * @param methodSignature
   * @return
   * @throws NoMutantDirException
   * @throws NoMutantException
   */
  private List<String> getMutants (String methodSignature) throws NoMutantDirException, NoMutantException{
	  
	  ArrayList<String> out = new ArrayList<String>();
	  int dirlen = MutationSystem.MUTANT_PATH.length();
	  int filenamelen = whole_class_name.length() + 5; 
	  
	  //iterate through the map of bytecodes
	  for(String e : mutantData.mutantClass.keySet()) {
		  if(e.startsWith(MutationSystem.MUTANT_PATH)) {
			  out.add(e.substring(dirlen+1,e.length()-filenamelen-1));
		  }
		  
	  }
	  
	  	
//	      // Read mutants
//	     //System.out.println("mutant_path: " + MutationSystem.MUTANT_PATH);
//	     File f = new File(MutationSystem.MUTANT_PATH);
//	    
//	     if(!f.exists()){
//	        System.err.println(" There is no directory for the mutants of " + MutationSystem.CLASS_NAME);
//	        System.err.println(" Please generate mutants for " + MutationSystem.CLASS_NAME);
//	        throw new NoMutantDirException();
//	     }
//
//	      // mutantDirectories match the names of mutants
//	     String[] mutantDirectories = f.list(new MutantDirFilter());
//
//	     if(mutantDirectories == null || mutantDirectories.length == 0){
//	    	  if(!methodSignature.equals(""))
//	    		  System.err.println(" No mutants have been generated for the method " + methodSignature + " of the class" + MutationSystem.CLASS_NAME);
//	    	  else
//	    		  System.err.println(" No mutants have been generated for the class " + MutationSystem.CLASS_NAME);
//	       // System.err.println(" Please check if zero mutant is correct.");
//	       // throw new NoMutantException();
//	     }  
	  
	return out;	  
  }
  

  /**
   * compute the result of a test under the original program
   */
   public void computeOriginalTestResults(){
	  Debug.println("\n\n======================================== Generating Original Test Results ========================================");	  
	  try{   	  
    	  //initialize the original results to "pass"
    	  //later the results of the failed test cases will be updated
          for(int k = 0;k < testCases.length;k++){
          	Annotation[] annotations = testCases[k].getDeclaredAnnotations();
            	for(Annotation annotation : annotations)
            	{
            		//System.out.println("name: " + testCases[k].getName() + annotation.toString() + annotation.toString().indexOf("@org.junit.Test"));
            		if(annotation.toString().indexOf("@org.junit.Test") != -1){
                		//killed_mutants[k]= "";   // At first, no mutants are killed by each test case
                		originalResults.put(testCases[k].getName(), "pass");
                		junitTests.add(testCases[k].getName());
                		//finalTestResults.put(testCases[k].getName(), "");
                		continue;
                	}
            	}
          }
           	 
      	JUnitCore jCore = new JUnitCore();	
      	//result = jCore.runMain(new RealSystem(), "VMTEST1");
      	
      	long startTime = System.currentTimeMillis();
      	
    	result = jCore.run(ParallelComputer.methods(),original_executer);
    	
    	long endTime = System.currentTimeMillis();
    	
    	// user never specified timeout
    	if(this.TIMEOUT == 0) {
    		this.setTimeOut((int) (2*(endTime - startTime)));
    	}
    	
    	
    	
    	
    	//get the failure report and update the original result of the test with the failures
      	List<Failure> listOfFailure = result.getFailures();
  		for(Failure failure: listOfFailure){
  			String nameOfTest = failure.getTestHeader().substring(0, failure.getTestHeader().indexOf("("));
   			String testSourceName = testSet + "." + nameOfTest;
   			
   			//System.out.println("failure message: " + failure.getMessage() + failure.getMessage().equals(""));
   			String[] sb = failure.getTrace().split("\\n");
   			String lineNumber = "";
   			for(int i = 0; i < sb.length;i++){
   				if(sb[i].indexOf(testSourceName) != -1){
   					lineNumber = sb[i].substring(sb[i].indexOf(":") + 1, sb[i].indexOf(")"));   					
   				}
   			}
   			
   			//put the failure messages into the test results
   			if(failure.getMessage() == null)
   				originalResults.put(nameOfTest, nameOfTest + ": " + lineNumber + "; " + "fail");
   			else{
	   			if(failure.getMessage().equals(""))
	   				originalResults.put(nameOfTest, nameOfTest + ": " + lineNumber + "; " + "fail");
	   			else
	   				originalResults.put(nameOfTest, nameOfTest + ": " + lineNumber + "; " + failure.getMessage());
   			}
  			
  		}
  		//System.out.println(originalResults.toString());
   
      //  System.out.println(System.getProperty("user.dir"));
      //  System.out.println(System.getProperty("java.class.path"));
      //  System.out.println(System.getProperty("java.library.path"));

      
      } 
	  catch (NoClassDefFoundError e) {
	      System.err.println("Could not find one of the necessary classes for running tests. Make sure that .jar files for hamcrest and junit are in your classpath.");
	      e.printStackTrace();
	      return;
	  }
       catch(Exception e){
    	   System.out.println(e.getMessage());
    	   e.printStackTrace();
      	 
    //    original_results[k] = e.getCause().getClass().getName()+" : "  +e.getCause().getMessage();
   //     Debug.println("Result for " + testName + "  :  " +original_results[k] );
    //    Debug.println(" [warining] " + testName + " generate exception as a result " );
        
         // ----------------------------------
        
     }finally{
          //originalResultFileRead();
     }
      }

   private TestResultParallel runMutants(TestResultParallel test_result, String methodSignature) throws NoMutantException,NoMutantDirException{
    try{

      List<String> mutantDirectories = getMutants(methodSignature);
      
      int mutant_num = mutantDirectories.size();
      test_result.setMutants();
            
      for(int i = 0;i < mutant_num;i++){
          // set live mutnats
          test_result.mutants.add(mutantDirectories.get(i));
      }
      
      // result againg original class for each test case
     // Object[] original_results = new Object[testCases.length];
      // list of the names of killed mutants with each test case
      //String[] killed_mutants = new String[testCases.length];

      Debug.println("\n\n======================================== Executing Mutants ========================================");
            
      ArrayList<Future<Void>> futures = new ArrayList<Future<Void>>();
      
      String prefix = MutationSystem.MUTANT_PATH;
      String path;

      for(int i = 0; i < test_result.mutants.size(); i++){    	
    	String mutant_name = test_result.mutants.get(i).toString();
    	path = prefix + "/" + mutant_name + "/" + whole_class_name + ".java";
    	futures.add(MutationSystem.executorService.submit(
    			new TestThread(mutant_name,whole_class_name,testSet,
    			testCases,test_result,originalResults,TIMEOUT,mutantData.mutantClass.get(path))));
      }
      // END LOOP FOR MUTANTS

      for(Future<Void> fut : futures) {
    	  fut.get();
      }
      
      //executorService.shutdownNow();
      
      //System.out.println("\nMethod: " + methodSignature + "\nLive Mutants:\n" + test_result.live_mutants);
      
     // invoke all the tasks to thread pool and block until ALL finish
     // shouldn't get stuck because each task has time limit
//     executorService.invokeAll(testthreads).forEach(f -> {
//		try {
//			f.get();
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
//	});

      }catch(NoMutantException e1){
      throw e1;
    }catch(NoMutantDirException e2){
      throw e2;
    }
    /*catch(ClassNotFoundException e3){
      System.err.println("[Execution 1] " + e3);
      return null;
    }
    */catch(Exception e){
      System.err.println("[Exception 2]" + e);
      return null;
    }
    //System.out.println("test report: " + finalTestResults);
    //System.out.println("mutant report: " + finalMutantResults);
    return test_result;
  }

  void erase_killed_mutants(Vector v){
    System.out.println("Deleting directories of killed mutants");
    for(int i=0;i<v.size();i++){
      System.out.print(v.get(i).toString()+" ");
      erase_directory(v.get(i).toString());
    }
  }

  void erase_directory(String mutant_name){
    File mutant_dir = new File(MutationSystem.MUTANT_PATH+"/"+mutant_name);
    File[] f = mutant_dir.listFiles();
    boolean flag = false;
    for(int i=0;i<f.length;i++){
      while(!flag){
        flag = f[i].delete();
      }
      flag = false;
    }

    while(!flag){
      flag = mutant_dir.delete();
    }
  }
}

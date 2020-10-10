package mujava;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import mujava.test.JMutationLoader;
import mujava.test.TestResult;
import mujava.test.TestResultParallel;


// Runs a single mutant against all test cases and records the result (killed or live)
public class TestThread implements Callable<Void>{
	
	JMutationLoader mutantLoader;
	Class mutant_executer;
	Method[] testCases;
	String mutantName;
	TestResultParallel tr;
	Map<String, String> orig;
	


	//constructor
	TestThread(String mutantName, String className, String testName, Method[] testCases, 
			TestResultParallel tr,Map<String,String> orig) throws ClassNotFoundException, FileNotFoundException, IOException{
		mutantLoader = new JMutationLoader(mutantName);
		mutantLoader.loadMutant(className);
        mutant_executer = mutantLoader.loadTestClass(testName);
        this.testCases = testCases;
        this.mutantName = mutantName;
        this.tr = tr;
        this.orig = orig;
        
        
	}
	
	@Override
	public Void call() throws Exception {
		HashMap<String,String> mutantResults = new HashMap<String, String>();
		try{
	
			for(int k = 0;k < testCases.length;k++){
				Annotation[] annotations = testCases[k].getDeclaredAnnotations();
	            for(Annotation annotation : annotations){
            		//System.out.println("name: " + testCases[k].getName() + annotation.toString() + annotation.toString().indexOf("@org.junit.Test"));
            		if(annotation.toString().indexOf("@org.junit.Test") != -1){
                		//killed_mutants[k]= "";   // At first, no mutants are killed by each test case
            			mutantResults.put(testCases[k].getName(), "pass");
                		continue;
                	}
            	}
            }
		
		
			JUnitCore jCore = new JUnitCore();
			//jCore.addListener(new TextListener(System.out));
	     	Result result = jCore.run(mutant_executer);
	     	
	     	List<Failure> listOfFailure = result.getFailures();
	   		for(Failure failure: listOfFailure){
	   			String nameOfTest = failure.getTestHeader().substring(0, failure.getTestHeader().indexOf("("));
	   			String testSourceName = mutant_executer.getName() + "." + nameOfTest;
	   			
	   			//System.out.println(testSourceName);
	   			String[] sb = failure.getTrace().split("\\n");
	   			String lineNumber = "";
	   			for(int i = 0; i < sb.length;i++){
	   				//System.out.println("sb-trace: " + sb[i]);
	   				if(sb[i].indexOf(testSourceName) != -1){
	   					lineNumber = sb[i].substring(sb[i].indexOf(":") + 1, sb[i].indexOf(")"));
	   					
	   				}
	   			}
	   			//get the test name that has the error and save the failure info to the results for mutants
	   			if(failure.getMessage() == null)
	   				mutantResults.put(nameOfTest, nameOfTest + ": " + lineNumber + "; " + "fail");
	   			else if(failure.getMessage().equals(""))
	   				mutantResults.put(nameOfTest, nameOfTest + ": " + lineNumber + "; " + "fail");
	   			else
	   				mutantResults.put(nameOfTest, nameOfTest + ": " + lineNumber + "; " + failure.getMessage());
	   		}
	   	
	   		//System.out.println(mutantResults.toString());
	    }catch(Exception e){
	      e.printStackTrace();
	      //System.out.println("e.getMessage()");
	      //System.out.println(e.getMessage());
	    }
	
		//determine whether a mutant is killed or not. Need to compare to original results
	    //update the test report
		 boolean killed = false;
	      for(int k = 0;k < testCases.length;k++){
	    	  String name = testCases[k].getName();	 
	    	  // different mutant result and orig result (killed)
	    	  if(!mutantResults.get(name).equals(orig.get(name))){
	    		  tr.killed_mutants.add(mutantName);
	    		  killed = true;
	    		  break;
	    	  }
	      }
	      if(!killed) {
	    	  tr.live_mutants.add(mutantName);
	      }

		return null;
	}

}

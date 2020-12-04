package mujava;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import mujava.test.JMutationLoader;
import mujava.test.TestResultParallel;


// Runs a single mutant against all test cases and records the result (killed or live)
public class TestThread implements Callable<Void>{
	
	JMutationLoader mutantLoader;
	Class mutant_executer;
	Class mutant;
	Method[] testCases;
	String mutantName;
	TestResultParallel tr;
	Map<String, String> orig;
	int TIMEOUT;
	String className;
	String testName;
	byte[] bytecode;

	//constructor
	TestThread(String mutantName, String className, String testName, Method[] testCases, 
			TestResultParallel tr,Map<String,String> orig,int TIMEOUT,byte[] bytecode){

		this.className = className;
        this.testCases = testCases;
        this.mutantName = mutantName;
        this.testName = testName;
        this.tr = tr;
        this.orig = orig;
        this.TIMEOUT = TIMEOUT;
        this.bytecode = bytecode;

        
        
	}
	

	
	@Override
	public Void call() throws ClassNotFoundException, FileNotFoundException, IOException {
		mutantLoader = new JMutationLoader(mutantName);
		//mutant = mutantLoader.loadMutant(className);
		mutant = mutantLoader.loadMutantInMem(className, bytecode);
		mutant_executer = mutantLoader.loadTestClass(testName);

		
		HashMap<String,String> mutantResults = new HashMap<String, String>();
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
		
		ExecutorService exec = Executors.newSingleThreadExecutor(new ThreadFactory(){
			// kill threads once they go overtime (set daemon)
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
		        t.setDaemon(true);
		        return t;
			}
			
			
		});
		Future<Result> f = exec.submit(new JunitThread(jCore, mutant_executer));
		Result result = null;
		try {
			result = f.get(this.TIMEOUT,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// if timeout happens, then there was likely an infinite loop/
			// mark this mutant as killed and return immediately
			System.err.println("Timeout: " + mutantName);
			exec.shutdownNow();
			tr.killed_mutants.add(mutantName);
			
			return null;
		}
		
		

		//jCore.addListener(new TextListener(System.out))
		//Result result = jCore.run(mutant_executer);
		
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
	
		//determine whether a mutant is killed or not. Need to compare to original results
	    //update the test report
	      for(int k = 0;k < testCases.length;k++){
	    	  String name = testCases[k].getName();	 
	    	  // different mutant result and orig result (killed)
	    	  if(!mutantResults.get(name).equals(orig.get(name))){
	    		  //System.out.println("Mutant " + this.mutantName +  " result: " + mutantResults.get(name));
	    		  //System.out.println("Orig result: " + orig.get(name));
	    		  tr.killed_mutants.add(mutantName);
	    		  return null;
	    	  }
	      }
	    
	    // if no method killed this mutant, it is LIVE
	    tr.live_mutants.add(mutantName);
	      
		return null;

	}
	
	class JunitThread implements Callable<Result>{
		JUnitCore jCore;
		Class mutant_executor;
		//Class mutant;
		
		JunitThread(JUnitCore jCore, Class mutant_executor){
			this.jCore = jCore;
			this.mutant_executor = mutant_executor;
			//this.mutant = mutant;
		}

		@Override
		public Result call() throws Exception {
			return jCore.run(mutant_executor);

		}
		
		
	}

}

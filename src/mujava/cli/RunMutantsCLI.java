package mujava.cli;

import mujava.MutantData;
import mujava.MutationSystem;
import mujava.OGTestExecuter;
import mujava.TestExecuter;
import mujava.test.NoMutantDirException;
import mujava.test.NoMutantException;
import mujava.test.TestResult;
import mujava.test.TestResultParallel;

public class RunMutantsCLI {
	
	/**
	 * @param targetClassName name of class being tested
	 * @param testSetName name of JUnit Test file to be used
	 * @param mutantTypes Either traditional, class, or both
	 */
	public void RunMutes(String targetClassName, String testSetName, String mutantTypes, int timeout_secs) {
		   // just test all methods for now
		   String methodSignature = "All method";
		   
	      // OG TEST EXECUTOR
	      if(!MutationSystem.isParallel) {
	    	  
	      
		      if((targetClassName != null) && (testSetName != null))
		      {
		
		         OGTestExecuter test_engine = new OGTestExecuter(targetClassName);
		         test_engine.setTimeOut(timeout_secs);
		
		         // First, read (load) test suite class.
		         test_engine.readTestSet(testSetName);
		
		         //TestResult test_result = new TestResult();
		         TestResult test_result;
		         try
		         {
//		            if (mutantTypes.equals("class"))
//		            {
//		               cResultPanel.setVisible(true);
//		               tResultPanel.setVisible(false);
//		               test_engine.computeOriginalTestResults();
//		               test_result = test_engine.runClassMutants();
//		               OGshowResult(test_result, cResultTable, cKilledList, cLiveList);
//		            } 
		            if (mutantTypes.equals("trad"))
		            {
		               test_engine.computeOriginalTestResults();
		               test_result = test_engine.runTraditionalMutants(methodSignature);
		               //OGshowResult(test_result, tResultTable, tKilledList, tLiveList);
		            }
//		            else if (mutantTypes.equals("both"))
//		            {
//		               cResultPanel.setVisible(true);
//		               tResultPanel.setVisible(true);
//		               test_engine.computeOriginalTestResults();
//		               test_result = test_engine.runClassMutants();
//		               OGshowResult(test_result, cResultTable, cKilledList, cLiveList);
//		               test_result = test_engine.runTraditionalMutants(methodSignature);
//		               OGshowResult(test_result, tResultTable, tKilledList, tLiveList);
//		            }
		         } 
		         catch (NoMutantException e1)
		         {
		         } 
		         catch (NoMutantDirException e2)
		         {
		         }
		      } 
		      else
		      {
		         System.out.println(" [Error] Please check test target or test suite ");
		      }
	      }
	      // PARALLEL TEST EXECUTOR
	      else {
	      
		      if((targetClassName != null) && (testSetName != null))
		      {

		
		         TestExecuter test_engine = new TestExecuter(targetClassName);
		         test_engine.setTimeOut(timeout_secs);
		
		         // First, read (load) test suite class.
		         test_engine.readTestSet(testSetName);
		
		         //TestResult test_result = new TestResult();
		         TestResultParallel test_result;
		         try
		         {
//		            if (mutantTypes.equals("class"))
//		            {
//		               cResultPanel.setVisible(true);
//		               tResultPanel.setVisible(false);
//		               test_engine.computeOriginalTestResults();
//		               test_result = test_engine.runClassMutants();
//		               showResult(test_result, cResultTable, cKilledList, cLiveList);
//		            } 
		            if (mutantTypes.equals("trad"))
		            {
		               test_engine.computeOriginalTestResults();
		               test_result = test_engine.runTraditionalMutants(methodSignature);
		               //showResult(test_result, tResultTable, tKilledList, tLiveList);
		            }
//		            else if (mutantTypes.equals("both"))
//		            {
//		               cResultPanel.setVisible(true);
//		               tResultPanel.setVisible(true);
//		               test_engine.computeOriginalTestResults();
//		               test_result = test_engine.runClassMutants();
//		               showResult(test_result, cResultTable, cKilledList, cLiveList);
//		               test_result = test_engine.runTraditionalMutants(methodSignature);
//		               showResult(test_result, tResultTable, tKilledList, tLiveList);
//		            }
		         } 
		         catch (NoMutantException e1)
		         {
		         } 
		         catch (NoMutantDirException e2)
		         {
		         }
		      } 
		      else
		      {
		         System.out.println(" [Error] Please check test target or test suite ");
		      }
	      }
		
	}

}

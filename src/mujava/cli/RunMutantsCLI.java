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
	
	/*
	 * targetClassName: name of class being tested
	 * testSetName: name of JUnit Test file to be used
	 * mutantTypes: Either traditional, class, or both
	 * md: MutantData object for parallel (in-memory) executor. Null if using sequential executor
	 */
	public void RunMutes(String targetClassName, String testSetName, String mutantTypes, MutantData mutantData) {
		   int timeout_secs = 3000;
		   // just test all methods for now
		   String methodSignature = "All method";
		   
		   //TODO: Get arg for timeout limit
//			// check if the customized timeout is used
//		   // added by Lin, 05/23/2015
//			if (isCustomizedTimeout) {
//				try {
//					timeout_secs  = 1000*Integer.parseInt(timeoutTextField.getText());
//					// what if a negative or zero, set it to 3000
//					if (timeout_secs <= 0) {
//						timeout_secs = 3000;
//					}
//
//				} catch (NumberFormatException ex) {
//					// if not a number, set to be 3000
//					timeout_secs = 3000;
//				}
//			}
		   
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

		
		         TestExecuter test_engine = new TestExecuter(targetClassName, mutantData);
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

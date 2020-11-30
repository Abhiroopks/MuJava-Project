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


package mujava.test;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>Description: </p>
 * @author Yu-Seung Ma
 * @update by Nan Li May 2012
 * @update by Abhi K November 2020
 * @version 1.0
  */

public class TestResultParallel
{
	public Vector mutants = new Vector();
	
	public ConcurrentLinkedQueue<String> killed_mutants = new ConcurrentLinkedQueue<String>();
	public ConcurrentLinkedQueue<String> live_mutants = new ConcurrentLinkedQueue<String>();
  
  public void setMutants(){
	  mutants = new Vector();
	  
  }
}

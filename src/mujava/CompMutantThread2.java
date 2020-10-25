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


public class CompMutantThread2 implements Callable<Void> {

	
	String[] pars;

	//Constructor
	CompMutantThread2(String[] pars){
		
		this.pars = pars;
	}
	
	// compiles a traditional mutant
	@Override
	public Void call() throws Exception {
		
		Main.compile(pars);
		
		
		return null;
	}

}

package mujava;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.tools.javac.Main;

import mujava.cli.Util;
import mujava.op.util.Mutator;
import mujava.util.Debug;
import openjava.mop.FileEnvironment;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;


public class GenTradMutThread implements Callable<Void> {

	String traditionalOp;
	Mutator mutant_op;
	FileEnvironment file_env;
	ClassDeclaration cdecl;
	CompilationUnit comp_unit;
	ConcurrentHashMap<String, String> mutantSource;

	//Constructor
	GenTradMutThread(String op, FileEnvironment fe, ClassDeclaration cd, CompilationUnit cu, ConcurrentHashMap<String, String> mutantSource2){
		
		this.traditionalOp = op;
		this.file_env = fe;
		this.cdecl = cd;
		this.comp_unit = cu;
		this.mutantSource = mutantSource2;

		
	}
	
	// generates and compiles a traditional mutant
	@Override
	public Void call() throws Exception {
		

		
		// create Mutator object for this specific traditional mutant object
		try {
			
			// be sure to prepend the package name to classname
			Class myClass = Class.forName("mujava.op.basic." + this.traditionalOp);
	
			// classes of the parameters used in constructor
			Class[] types = {this.file_env.getClass(),this.cdecl.getClass(),this.comp_unit.getClass()};
			
			Constructor constructor = myClass.getConstructor(types);
	
			// parameters for constructor
			Object[] parameters = {this.file_env,this.cdecl,this.comp_unit};
			mutant_op = (Mutator) constructor.newInstance(parameters);
			
			// tell this Mutator where to store generated mutant sources (local)
			
			mutant_op.setSourceMap(new HashMap<String,String>());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// generate mutants using this operator
		this.comp_unit.accept(mutant_op);
		
		// now merge with global map
		mutantSource.putAll(mutant_op.myprintwriter.mutantSource);

        

		return null;
	}

}

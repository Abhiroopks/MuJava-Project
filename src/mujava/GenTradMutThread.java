package mujava;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import com.sun.tools.javac.Main;

import mujava.op.util.Mutator;
import openjava.mop.FileEnvironment;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import mujava.op.basic.*;


public class GenTradMutThread implements Callable<Void> {

	String traditionalOp;
	Mutator mutant_op;
	FileEnvironment file_env;
	ClassDeclaration cdecl;
	CompilationUnit comp_unit;
	

	//Constructor
	GenTradMutThread(String op, FileEnvironment fe, ClassDeclaration cd, CompilationUnit cu){
		
		this.traditionalOp = op;
		this.file_env = fe;
		this.cdecl = cd;
		this.comp_unit = cu;

		
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// generate mutant
		this.comp_unit.accept(mutant_op);
		
		// COMPILE MUTANTS
		
		String[] pars = new String[3];
        pars[0] = "-classpath";
        pars[1] = MutationSystem.CLASS_PATH;
        
        ArrayList<CompMutantThread2> compthreads = new ArrayList<CompMutantThread2>();
        
        
        for(String mut : this.mutant_op.mutfiles) {
        	pars[2] = mut;
        	compthreads.add(new CompMutantThread2(pars));
        }
        
        Executors.newWorkStealingPool().invokeAll(compthreads).forEach(t -> {
			try {
				t.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});;
        

		return null;
	}

}

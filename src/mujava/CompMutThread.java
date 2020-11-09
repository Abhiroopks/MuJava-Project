package mujava;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import com.sun.tools.javac.Main;

import mujava.cli.Util;
import mujava.op.util.ClassFileManager;
import mujava.op.util.Mutator;
import mujava.op.util.StringJavaFileObject;
import mujava.util.Debug;
import openjava.mop.FileEnvironment;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;


public class CompMutThread implements Callable<Void> {


	Map.Entry<String,String> entry = null;
	ConcurrentHashMap<String, byte[]> mutantClass;
	String name;
	JavaCompiler compiler;
	ArrayList<String> options;
	ConcurrentHashMap<String, String> mutantSource;
	

	//Constructor
	CompMutThread(ConcurrentHashMap<String, String> mutantSource, Map.Entry<String,String> entry, ConcurrentHashMap<String, byte[]> mutantClass,String name, ArrayList<String> options, JavaCompiler compiler){
		this.entry = entry;
		this.mutantClass = mutantClass;
		this.name = name;
		this.options = options;
		this.compiler = compiler;
		this.mutantSource = mutantSource;
		
	}
	
	// compiles a traditional mutant
	@Override
	public Void call() throws Exception {	
        ClassFileManager jfm = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
        
        ArrayList<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new StringJavaFileObject(name, entry.getValue()));
        
        boolean success = compiler.getTask(null, jfm, null, options, null, jfiles).call();
        
        if(success) {
     	   mutantClass.put(entry.getKey(), jfm.jclassObject.getBytes());
        }
        else {
     	   // remove from source map
        	mutantSource.remove(entry.getKey());
        	
        }
		
		
		
		
		return null;	
	}

}

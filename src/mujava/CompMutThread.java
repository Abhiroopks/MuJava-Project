package mujava;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import mujava.op.util.ClassFileManager;
import mujava.op.util.StringJavaFileObject;


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
        
        boolean success = compiler.getTask(new StringWriter(), jfm,null, options, null, jfiles).call();
        
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

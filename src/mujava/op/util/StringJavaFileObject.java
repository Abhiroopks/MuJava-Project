package mujava.op.util;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class StringJavaFileObject extends SimpleJavaFileObject {
	
	private String source;

	
	/*
	 * Constructor
	 * @param className
	 *		name of the class to be compiled
	 * @param source
	 * 		sourcecode in String		
	 */
	public StringJavaFileObject(String className, String source) {
	        super(URI.create("string:///" + className.replace('.', '/')
	            + Kind.SOURCE.extension), Kind.SOURCE);
	        this.source = source;
	    }

	    /**
	    * Returns the sourcecode to be compiled
	    * 
	    */
	    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
	        return source;
	    }

}

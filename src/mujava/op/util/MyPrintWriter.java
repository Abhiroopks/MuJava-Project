package mujava.op.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;


public class MyPrintWriter extends PrintWriter {
	
	public HashMap<String,String> mutantSource = null;
	public String currFile = null;
	
	// to be used by Mutator.getPrintWriter()
	public MyPrintWriter(StringWriter out) {
		super(out);
	}
	
    /**
     * Flushes the stream - saves the mutant source in map
     * @see #checkError()
     */
    public void flush() {
    	// store this mutant source code in the source map
    	
    	//System.out.println(this.out.toString());
    	
    	mutantSource.put(currFile,this.out.toString());
    	
    	// clear the buffer
    	StringWriter sw = (StringWriter) this.out;
    	sw.getBuffer().delete(0, sw.getBuffer().length());
    	
    	
    }
    
    /*
     * Does nothing - because MyPrintWriter can re-use a single StringWriter
     */
    public void close() {
    	
    	
    	
    	
    }
	
	
	
	

}

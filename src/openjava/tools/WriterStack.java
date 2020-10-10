// 
// Decompiled by Procyon v0.5.36
// 

package openjava.tools;

import java.io.BufferedWriter;
import java.util.EmptyStackException;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.Writer;
import java.io.PrintWriter;
import java.util.Stack;

public class WriterStack
{
    private Stack stack;
    private static PrintWriter defaultWriter;
    
    public WriterStack() {
        this.stack = null;
        (this.stack = new Stack()).push(WriterStack.defaultWriter);
    }
    
    public WriterStack(final PrintWriter item) {
        this.stack = null;
        (this.stack = new Stack()).push(item);
    }
    
    public WriterStack(final Writer out) {
        this.stack = null;
        (this.stack = new Stack()).push(new PrintWriter(out));
    }
    
    public WriterStack(final OutputStream out) {
        this.stack = null;
        (this.stack = new Stack()).push(new PrintWriter(new OutputStreamWriter(out)));
    }
    
    public PrintWriter peek() {
        try {
            return (PrintWriter) this.stack.peek();
        }
        catch (EmptyStackException x) {
            System.err.println(x);
            return WriterStack.defaultWriter;
        }
    }
    
    public PrintWriter pop() {
        try {
            return (PrintWriter) this.stack.pop();
        }
        catch (EmptyStackException x) {
            System.err.println(x);
            return WriterStack.defaultWriter;
        }
    }
    
    public void push(final PrintWriter item) {
        this.stack.push(item);
    }
    
    public void push(final Writer out) {
        this.stack.push(new PrintWriter(out));
    }
    
    public void push(final OutputStream out) {
        this.stack.push(new PrintWriter(new OutputStreamWriter(out)));
    }
    
    public boolean empty() {
        return this.stack.empty();
    }
    
    static {
        WriterStack.defaultWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    }
}

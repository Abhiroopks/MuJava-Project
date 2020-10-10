// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.io.PrintStream;
import java.io.PrintWriter;

public class MOPException extends Exception
{
    private Exception ex;
    
    public MOPException() {
        this.ex = null;
    }
    
    public MOPException(final Exception ex) {
        super(ex.getMessage());
        this.ex = null;
        this.ex = ex;
    }
    
    public MOPException(final String message) {
        super(message);
        this.ex = null;
    }
    
    @Override
    public void printStackTrace(final PrintWriter printWriter) {
        if (this.ex != null) {
            this.ex.printStackTrace(printWriter);
        }
        else {
            super.printStackTrace(printWriter);
        }
    }
    
    @Override
    public void printStackTrace(final PrintStream printStream) {
        if (this.ex != null) {
            this.ex.printStackTrace(printStream);
        }
        else {
            super.printStackTrace(printStream);
        }
    }
}

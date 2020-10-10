// 
// Decompiled by Procyon v0.5.36
// 

package openjava.tools;

import java.io.PrintStream;

public final class DebugOut
{
    private static int debugLevel;
    protected static PrintStream out;
    
    public static void setDebugLevel(final int debugLevel) {
        DebugOut.debugLevel = debugLevel;
    }
    
    public static void flush() {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.flush();
        }
    }
    
    public static void close() {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.close();
        }
    }
    
    public static boolean checkError() {
        return DebugOut.out.checkError();
    }
    
    public static void print(final boolean b) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(b);
        }
    }
    
    public static void print(final char c) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(c);
        }
    }
    
    public static void print(final int i) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(i);
        }
    }
    
    public static void print(final long l) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(l);
        }
    }
    
    public static void print(final float f) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(f);
        }
    }
    
    public static void print(final double d) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(d);
        }
    }
    
    public static void print(final char[] s) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(s);
        }
    }
    
    public static void print(final String s) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(s);
        }
    }
    
    public static void print(final Object obj) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.print(obj);
        }
    }
    
    public static void println() {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println();
        }
    }
    
    public static void println(final boolean x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    public static void println(final char x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    public static void println(final int x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    public static void println(final long x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    public static void println(final float x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    public static void println(final double x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    public static void println(final char[] x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    public static void println(final String x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    public static void println(final Object x) {
        if (DebugOut.debugLevel > 2) {
            DebugOut.out.println(x);
        }
    }
    
    static {
        DebugOut.debugLevel = 0;
        DebugOut.out = System.err;
    }
}

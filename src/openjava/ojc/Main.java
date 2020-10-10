// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ojc;

import java.io.PrintStream;

public class Main
{
    public static void main(final String[] array) {
        System.err.println("OpenJava Compiler Version 1.1 build 20031119");
        CommandArguments commandArguments;
        try {
            commandArguments = new CommandArguments(array);
        }
        catch (Exception ex) {
            showUsage();
            return;
        }
        new Compiler(commandArguments).run();
    }
    
    private static void showUsage() {
        final PrintStream err = System.err;
        err.println("Usage : ojc <options> <source files>");
        err.println("where <options> includes:");
        err.println("  -verbose                 Enable verbose output                  ");
        err.println("  -g=<number>              Specify debugging info level           ");
        err.println("  -d=<directory>           Specify where to place generated files ");
        err.println("  -compiler=<class>        Specify regular Java compiler          ");
        err.println("  --default-meta=<file>    Specify separated meta-binding configurations");
        err.println("  -calleroff               Turn off caller-side translations      ");
        err.println("  -C=<argument>            Pass the argument to Java compiler     ");
        err.println("  -J<argument>             Pass the argument to JVM               ");
    }
}

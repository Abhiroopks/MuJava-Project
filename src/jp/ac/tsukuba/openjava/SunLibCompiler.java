// 
// Decompiled by Procyon v0.5.36
// 

package jp.ac.tsukuba.openjava;

import java.io.OutputStream;
import java.lang.reflect.Method;
import openjava.ojc.JavaCompiler;

public class SunLibCompiler implements JavaCompiler
{
    Object sunJavac;
    Method compileMethod;
    
    public SunLibCompiler() {
        try {
            final Class<?> forName = Class.forName("sun.tools.javac.Main");
            this.sunJavac = forName.getConstructor(OutputStream.class, String.class).newInstance(System.err, "javac");
            this.compileMethod = forName.getMethod("compile", String[].class);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }
    
    public static void main(final String[] array) {
        new SunLibCompiler().compile(array);
    }
    
    @Override
    public void compile(final String[] array) {
        try {
            this.compileMethod.invoke(this.sunJavac, array);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }
}

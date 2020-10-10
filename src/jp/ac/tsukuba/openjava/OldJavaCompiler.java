// 
// Decompiled by Procyon v0.5.36
// 

package jp.ac.tsukuba.openjava;

import java.io.BufferedInputStream;
import openjava.ojc.JavaCompiler;

public class OldJavaCompiler implements JavaCompiler
{
    public static void main(final String[] array) {
        new OldJavaCompiler().compile(array);
    }
    
    @Override
    public void compile(final String[] array) {
        final Runtime runtime = Runtime.getRuntime();
        try {
            final Process exec = runtime.exec("javac " + strs2str(array));
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(exec.getErrorStream());
            final byte[] b = new byte[1024];
            for (int i = bufferedInputStream.read(b); i != -1; i = bufferedInputStream.read(b)) {
                System.err.write(b, 0, i);
            }
            exec.waitFor();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static String strs2str(final String[] array) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i]).append(" ");
        }
        return sb.toString();
    }
}

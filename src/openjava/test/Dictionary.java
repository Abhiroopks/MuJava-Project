// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class Dictionary
{
    protected static RandomAccessFile d;
    protected static final String filename = "lib/resource/dictionarywords";
    protected static final int entries = 431000;
    protected static final String endoffile = "End-Of-File";
    protected static InputStream in;
    protected static BufferedReader bf;
    
    protected static boolean open() {
        boolean b = false;
        try {
            Dictionary.in = Crosslexic.dictionaryResource.openStream();
            Dictionary.bf = new BufferedReader(new InputStreamReader(Dictionary.in));
            b = true;
        }
        catch (Exception ex) {
            Crosslexic.addtolist(Crosslexic.iconAlert, "ERROR: InputStreamException:\n" + ex.getMessage());
        }
        return b;
    }
    
    protected static String getNextWord() {
        String line;
        try {
            line = Dictionary.bf.readLine();
            if (line == null) {
                line = "End-Of-File";
            }
        }
        catch (IOException ex) {
            Crosslexic.addtolist(Crosslexic.iconAlert, "ERROR: IOException:\n" + ex.getMessage());
            line = "End-Of-File";
        }
        return line;
    }
    
    protected static void close() {
        try {
            if (Dictionary.bf != null) {
                Dictionary.bf.close();
            }
            if (Dictionary.in != null) {
                Dictionary.in.close();
            }
        }
        catch (IOException ex) {
            Crosslexic.addtolist(Crosslexic.iconAlert, "ERROR: IOException:\n" + ex.getMessage());
        }
    }
}

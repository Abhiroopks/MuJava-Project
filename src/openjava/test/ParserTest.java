// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import openjava.ptree.CompilationUnit;
import openjava.tools.parser.ParseException;
import openjava.mop.Environment;
import openjava.mop.OJSystem;
import java.io.FileNotFoundException;
import java.io.InputStream;
import openjava.tools.parser.Parser;
import java.io.FileInputStream;

public class ParserTest
{
    public static void main(final String[] args) {
        System.out.println(System.getProperty("user.dir"));
        final String file = "src/openjava/test/Flower.java";
        Parser parser = null;
        try {
            parser = new Parser(new FileInputStream(file));
        }
        catch (FileNotFoundException e3) {
            System.err.println("File " + file + " not found.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            OJSystem.initConstants();
            final CompilationUnit result = parser.CompilationUnit(OJSystem.env);
        }
        catch (ParseException e2) {
            e2.printStackTrace();
            System.out.println(" can't generate parse tree");
        }
        catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}

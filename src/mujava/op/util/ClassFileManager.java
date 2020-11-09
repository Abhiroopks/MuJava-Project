package mujava.op.util;


import java.io.IOException;
import java.security.SecureClassLoader;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.JavaFileObject.Kind;

public class ClassFileManager extends ForwardingJavaFileManager {
    /**
     * Instance of JavaClassObject that will store the
     * compiled bytecode of our class
     */
    public JavaClassObject jclassObject;

    /**
     * Will initialize the manager with the specified
     * standard java file manager
     *
     * @param standardManger
     */
    public ClassFileManager(StandardJavaFileManager
            standardManager) {
        super(standardManager);
    }


    /**
     * Gives the compiler an instance of the JavaClassObject
     * so that the compiler can write the byte code into it.
     */
    public JavaFileObject getJavaFileForOutput(Location location,
            String className, Kind kind, FileObject sibling)
    throws IOException {
        jclassObject = new JavaClassObject(className, kind);
        return jclassObject;
    }
}
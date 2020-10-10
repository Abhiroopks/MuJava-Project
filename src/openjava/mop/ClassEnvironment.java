// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.tools.DebugOut;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;

public class ClassEnvironment extends ClosedEnvironment
{
    private OJClass clazz;
    private String className;
    private Vector memberClasses;
    
    public ClassEnvironment(final Environment environment, final String s) {
        super(environment);
        this.clazz = null;
        this.className = null;
        this.memberClasses = new Vector();
        this.className = Environment.toSimpleName(s);
    }
    
    public ClassEnvironment(final Environment environment) {
        super(environment);
        this.clazz = null;
        this.className = null;
        this.memberClasses = new Vector();
        this.className = null;
    }
    
    public ClassEnvironment(final Environment environment, final OJClass ojClass) {
        super(environment);
        this.clazz = null;
        this.className = null;
        this.memberClasses = new Vector();
        this.className = Environment.toSimpleName(ojClass.getName());
        final OJClass[] declaredClasses = ojClass.getDeclaredClasses();
        for (int i = 0; i < declaredClasses.length; ++i) {
            this.memberClasses.addElement(declaredClasses[i].getSimpleName());
        }
    }
    
    public String getClassName() {
        if (this.className != null) {
            return this.className;
        }
        return "<unknown class>";
    }
    
    public Vector getMemberClasses() {
        return this.memberClasses;
    }
    
    @Override
    public String toString() {
        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.println("ClassEnvironment");
        printWriter.println("class : " + this.getClassName());
        printWriter.println("member classes : " + this.getMemberClasses());
        printWriter.print("parent env : " + this.parent);
        printWriter.flush();
        return out.toString();
    }
    
    public void recordClassName(final String s) {
        this.className = Environment.toSimpleName(s);
    }
    
    public void recordMemberClass(final String obj) {
        this.memberClasses.addElement(obj);
    }
    
    @Override
    public OJClass lookupClass(final String s) {
        return this.parent.lookupClass(s);
    }
    
    @Override
    public OJClass lookupBind(final String s) {
        final String currentClassName = this.currentClassName();
        final OJClass lookupClass = this.lookupClass(currentClassName);
        if (lookupClass == null) {
            System.err.println("unexpected error : unknown class " + currentClassName);
            return this.parent.lookupBind(s);
        }
        final OJField pickupField = pickupField(lookupClass, s);
        if (pickupField != null) {
            return pickupField.getType();
        }
        return this.parent.lookupBind(s);
    }
    
    public static OJField pickupField(final OJClass ojClass, final String s) {
        try {
            return ojClass.getField(s, ojClass);
        }
        catch (NoSuchMemberException ex) {
            return pickupField(ojClass.getDeclaredClasses(), s);
        }
    }
    
    public static OJField pickupField(final OJClass[] array, final String s) {
        for (int i = 0; i < array.length; ++i) {
            final OJField pickupField = pickupField(array[i], s);
            if (pickupField != null) {
                return pickupField;
            }
        }
        return null;
    }
    
    @Override
    public String toQualifiedName(final String s) {
        if (s == null) {
            return null;
        }
        if (s.endsWith("[]")) {
            return this.toQualifiedName(s.substring(0, s.length() - 2)) + "[]";
        }
        if (s.indexOf(".") != -1) {
            final String first = getFirst(s);
            final String qualifiedName = this.toQualifiedName(first);
            if (qualifiedName == null || qualifiedName.equals(first)) {
                return s;
            }
            return qualifiedName + "." + getRest(s);
        }
        else if (s.equals(this.getClassName())) {
            if (this.isMostOuter()) {
                final String package1 = this.getPackage();
                if (package1 == null || package1.equals("")) {
                    return s;
                }
                return package1 + "." + s;
            }
            else {
                if (this.parentName() != null) {
                    return this.parentName() + "." + s;
                }
                return s;
            }
        }
        else {
            if (this.memberClasses.indexOf(s) >= 0) {
                return this.currentClassName() + "." + s;
            }
            return this.parent.toQualifiedName(s);
        }
    }
    
    @Override
    public String toQualifiedNameForEnum(final String str) {
        Class<?> forName = null;
        String s = "";
        final boolean b = false;
        try {
            if (this.getPackage() != null && !this.getPackage().equals("")) {
                s = this.getPackage() + "." + this.getClassName();
            }
            else {
                s = this.getClassName();
            }
            forName = Class.forName(s);
        }
        catch (ClassNotFoundException ex) {
            System.err.println("No such class: " + s);
        }
        if (forName != null && forName.getClasses() != null) {
            final Class<?>[] classes = forName.getClasses();
            for (int length = classes.length, i = 0; i < length; ++i) {
                if (classes[i].getName().endsWith(str)) {
                    return s + "." + str;
                }
            }
        }
        if (!b) {
            try {
                Class.forName(s + "$" + str);
            }
            catch (ClassNotFoundException ex2) {
                System.err.println("No such class: " + s + "$" + str);
            }
            return s + "." + str;
        }
        if (this.memberClasses.indexOf(str) >= 0) {
            return this.currentClassName() + "." + str;
        }
        return str;
    }
    
    private static final String getFirst(final String s) {
        final int index = s.indexOf(".");
        if (index == -1) {
            return s;
        }
        return s.substring(0, index);
    }
    
    private static final String getRest(final String s) {
        final int index = s.indexOf(".");
        if (index == -1) {
            return s;
        }
        return s.substring(index + 1);
    }
    
    private String parentName() {
        if (this.parent == null || !(this.parent instanceof ClassEnvironment)) {
            return null;
        }
        return this.parent.currentClassName();
    }
    
    private boolean isMostOuter() {
        return this.parent instanceof FileEnvironment;
    }
    
    @Override
    public String currentClassName() {
        return this.toQualifiedName(this.getClassName());
    }
    
    @Override
    public void record(final String str, final OJClass ojClass) {
        DebugOut.println("ClassEnvironment#record() : " + str + " " + ojClass.getName());
        this.parent.record(str, ojClass);
    }
    
    @Override
    public Vector getImportedClasses() {
        return this.parent.getImportedClasses();
    }
    
    @Override
    public Vector getImportedPackages() {
        return this.parent.getImportedPackages();
    }
    
    @Override
    public Environment getParentEnvironment() {
        return this.parent;
    }
}

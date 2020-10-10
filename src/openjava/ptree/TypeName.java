// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.OJClass;
import java.util.Hashtable;

public class TypeName extends NonLeaf
{
    Hashtable suffixes;
    int dim;
    
    public TypeName(final String p3, final int dimension, final Hashtable suffixes) {
        this.set(p3);
        this.setDimension(dimension);
        this.suffixes = suffixes;
    }
    
    public TypeName(final String s, final int n) {
        this(s, n, null);
    }
    
    public TypeName(final String s, final Hashtable hashtable) {
        this(s, 0, hashtable);
    }
    
    public TypeName(final String s) {
        this(s, 0, null);
    }
    
    TypeName() {
    }
    
    public static TypeName forOJClass(OJClass componentType) {
        int n = 0;
        while (componentType.isArray()) {
            ++n;
            componentType = componentType.getComponentType();
        }
        return new TypeName(componentType.getName(), n);
    }
    
    @Override
    public ParseTree makeRecursiveCopy() {
        final TypeName typeName = (TypeName)super.makeRecursiveCopy();
        typeName.dim = this.dim;
        typeName.suffixes = this.suffixes;
        return typeName;
    }
    
    @Override
    public ParseTree makeCopy() {
        final TypeName typeName = (TypeName)super.makeCopy();
        typeName.dim = this.dim;
        typeName.suffixes = this.suffixes;
        return typeName;
    }
    
    public int getDimension() {
        return this.dim;
    }
    
    public void setDimension(final int dim) {
        this.dim = dim;
    }
    
    public void addDimension(final int n) {
        this.dim += n;
    }
    
    public void addDimension(final String s) {
        this.addDimension(s.length() / 2);
    }
    
    public String getName() {
        return (String)this.elementAt(0);
    }
    
    @Deprecated
    public void setTypeName(final String p) {
        this.setElementAt(p, 0);
    }
    
    public void setName(final String p) {
        this.setElementAt(p, 0);
    }
    
    public static String stringFromDimension(final int n) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            sb.append("[]");
        }
        return sb.toString();
    }
    
    public static int toDimension(final String s) {
        int n = 0;
        for (int n2 = s.length() - 1; 0 < n2; n2 -= 2) {
            if (s.lastIndexOf(93, n2) != n2 || s.lastIndexOf(91, n2) != n2 - 1) {
                return n;
            }
            ++n;
        }
        return n;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}

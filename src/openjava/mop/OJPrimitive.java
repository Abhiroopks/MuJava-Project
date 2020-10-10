// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

public abstract class OJPrimitive
{
    public static final OJClass VOID;
    public static final OJClass BYTE;
    public static final OJClass CHAR;
    public static final OJClass INT;
    public static final OJClass LONG;
    public static final OJClass FLOAT;
    public static final OJClass DOUBLE;
    public static final OJClass STRING;
    public static final OJClass OBJECT;
    
    static {
        VOID = OJClass.forClass(Void.TYPE);
        BYTE = OJClass.forClass(Byte.TYPE);
        CHAR = OJClass.forClass(Character.TYPE);
        INT = OJClass.forClass(Integer.TYPE);
        LONG = OJClass.forClass(Long.TYPE);
        FLOAT = OJClass.forClass(Float.TYPE);
        DOUBLE = OJClass.forClass(Double.TYPE);
        STRING = OJClass.forClass(String.class);
        OBJECT = OJClass.forClass(Object.class);
    }
}

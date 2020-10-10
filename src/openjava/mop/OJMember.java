// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

public interface OJMember
{
    public static final int PUBLIC = 0;
    public static final int DECLARED = 1;
    
    OJClass getDeclaringClass();
    
    String getName();
    
    OJModifier getModifiers();
    
    Signature signature();
    
    Environment getEnvironment();
}

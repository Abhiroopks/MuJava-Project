// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

public interface MemberDeclaration extends ParseTree
{
    public static final int FIELD = 48;
    public static final int METHOD = 49;
    public static final int CONSTRUCTOR = 50;
    public static final int STATICINIT = 32;
    public static final int TYPE = 40;
    public static final int STATICINITIALIZER = 32;
    
    boolean equals(final ParseTree p0);
}

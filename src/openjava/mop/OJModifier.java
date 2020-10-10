// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.lang.reflect.Modifier;
import openjava.ptree.ModifierList;

public class OJModifier
{
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int STATIC = 8;
    public static final int FINAL = 16;
    public static final int SYNCHRONIZED = 32;
    public static final int VOLATILE = 64;
    public static final int TRANSIENT = 128;
    public static final int NATIVE = 256;
    public static final int INTERFACE = 512;
    public static final int ABSTRACT = 1024;
    private int javaModifier;
    private String[] userModifiers;
    private static OJModifier _constantEmpty;
    private static final int ACCESS = 7;
    private static final int INHERIT = 1040;
    
    OJModifier(final int javaModifier) {
        this.javaModifier = 0;
        this.userModifiers = null;
        this.javaModifier = javaModifier;
        this.userModifiers = new String[0];
    }
    
    OJModifier(final int javaModifier, final String[] userModifiers) {
        this.javaModifier = 0;
        this.userModifiers = null;
        this.javaModifier = javaModifier;
        this.userModifiers = userModifiers;
    }
    
    public static final OJModifier constantEmpty() {
        if (OJModifier._constantEmpty == null) {
            OJModifier._constantEmpty = new OJModifier(0);
        }
        return OJModifier._constantEmpty;
    }
    
    public int toModifier() {
        return this.javaModifier;
    }
    
    public static OJModifier forModifier(final int n) {
        return new OJModifier(n);
    }
    
    public static OJModifier forParseTree(final ModifierList list) {
        final int regular = list.getRegular();
        final String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            array[i] = list.get(i);
        }
        return new OJModifier(regular, array);
    }
    
    public final boolean isPublic() {
        return (this.javaModifier & 0x1) != 0x0;
    }
    
    public final boolean isPrivate() {
        return (this.javaModifier & 0x2) != 0x0;
    }
    
    public final boolean isProtected() {
        return (this.javaModifier & 0x4) != 0x0;
    }
    
    public final boolean isStatic() {
        return (this.javaModifier & 0x8) != 0x0;
    }
    
    public final boolean isFinal() {
        return (this.javaModifier & 0x10) != 0x0;
    }
    
    public final boolean isSynchronized() {
        return (this.javaModifier & 0x20) != 0x0;
    }
    
    public final boolean isVolatile() {
        return (this.javaModifier & 0x40) != 0x0;
    }
    
    public final boolean isTransient() {
        return (this.javaModifier & 0x80) != 0x0;
    }
    
    public final boolean isNative() {
        return (this.javaModifier & 0x100) != 0x0;
    }
    
    public final boolean isInterface() {
        return (this.javaModifier & 0x200) != 0x0;
    }
    
    public final boolean isAbstract() {
        return (this.javaModifier & 0x400) != 0x0;
    }
    
    public final boolean has(final String anObject) {
        for (int i = 0; i < this.userModifiers.length; ++i) {
            if (this.userModifiers[i].equals(anObject)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return Modifier.toString(this.javaModifier);
    }
    
    public OJModifier add(final int n) {
        if ((n & 0x7) != 0x0) {
            return new OJModifier(this.removedModifier(7) | n);
        }
        return new OJModifier(this.toModifier() | n);
    }
    
    public OJModifier remove(final int n) {
        return new OJModifier(this.removedModifier(n));
    }
    
    private final int removedModifier(final int n) {
        return this.toModifier() - (this.toModifier() & n);
    }
    
    public OJModifier setPublic() {
        return new OJModifier(this.removedModifier(7) | 0x1);
    }
    
    public OJModifier setProtected() {
        return new OJModifier(this.removedModifier(7) | 0x4);
    }
    
    public OJModifier setPrivate() {
        return new OJModifier(this.removedModifier(7) | 0x2);
    }
    
    public OJModifier setPackaged() {
        return new OJModifier(this.removedModifier(7));
    }
    
    public OJModifier setAbstract() {
        return new OJModifier(this.removedModifier(1040) | 0x400);
    }
    
    public OJModifier setFinal() {
        return new OJModifier(this.removedModifier(1040) | 0x10);
    }
    
    static {
        OJModifier._constantEmpty = null;
    }
}

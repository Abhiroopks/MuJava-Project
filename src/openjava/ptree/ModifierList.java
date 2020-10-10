// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ModifierList extends List
{
    public static final int PUBLIC = 1;
    public static final int PROTECTED = 4;
    public static final int PRIVATE = 2;
    public static final int STATIC = 8;
    public static final int FINAL = 16;
    public static final int SYNCHRONIZED = 32;
    public static final int VOLATILE = 64;
    public static final int TRANSIENT = 128;
    public static final int NATIVE = 256;
    public static final int ABSTRACT = 1024;
    public static final int EMPTY = 0;
    private int mod;
    
    public ModifierList() {
        super(" ");
        this.mod = 0;
    }
    
    public ModifierList(final String s) {
        super(" ", s);
        this.mod = 0;
    }
    
    public ModifierList(final int mod) {
        this();
        this.mod = mod;
    }
    
    @Override
    public String toString() {
        return toString(this.getRegular());
    }
    
    public static String toString(final int n) {
        final StringBuffer sb = new StringBuffer();
        if ((n & 0x1) != 0x0) {
            sb.append("public ");
        }
        if ((n & 0x2) != 0x0) {
            sb.append("private ");
        }
        if ((n & 0x4) != 0x0) {
            sb.append("protected ");
        }
        if ((n & 0x400) != 0x0) {
            sb.append("abstract ");
        }
        if ((n & 0x8) != 0x0) {
            sb.append("static ");
        }
        if ((n & 0x10) != 0x0) {
            sb.append("final ");
        }
        if ((n & 0x80) != 0x0) {
            sb.append("transient ");
        }
        if ((n & 0x40) != 0x0) {
            sb.append("volatile ");
        }
        if ((n & 0x100) != 0x0) {
            sb.append("native ");
        }
        if ((n & 0x20) != 0x0) {
            sb.append("synchronized ");
        }
        final int length;
        if ((length = sb.length()) > 0) {
            return sb.toString().substring(0, length - 1);
        }
        return "";
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.getRegular() == 0;
    }
    
    public boolean isEmptyAsRegular() {
        return this.getRegular() == 0;
    }
    
    @Override
    public boolean contains(final String s) {
        return s != null && ((s.equals("public") && this.contains(1)) || (s.equals("private") && this.contains(2)) || (s.equals("protected") && this.contains(4)) || (s.equals("abstract") && this.contains(1024)) || (s.equals("static") && this.contains(8)) || (s.equals("final") && this.contains(16)) || (s.equals("transient") && this.contains(128)) || (s.equals("volatile") && this.contains(64)) || (s.equals("native") && this.contains(256)) || (s.equals("synchronized") && this.contains(32)) || super.contains(s));
    }
    
    public boolean contains(final int n) {
        return (this.mod & n) != 0x0;
    }
    
    public String get(final int n) {
        return (String)this.contents_elementAt(n);
    }
    
    public void set(final int n, final String s) {
        this.contents_setElementAt(s, n);
    }
    
    public void add(final int n) {
        this.mod |= n;
    }
    
    public void add(final String s) {
        this.contents_addElement(s);
    }
    
    public String remove(final int n) {
        final String s = (String)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return s;
    }
    
    public void insertElementAt(final String s, final int n) {
        this.contents_insertElementAt(s, n);
    }
    
    public void append(final ModifierList list) {
        this.mod |= list.getRegular();
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public int getRegular() {
        return this.mod;
    }
    
    public void setRegular(final int mod) {
        this.mod = mod;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}

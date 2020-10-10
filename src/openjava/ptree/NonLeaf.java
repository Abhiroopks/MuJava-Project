// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import openjava.tools.DebugOut;

public abstract class NonLeaf extends ParseTreeObject implements ParseTree
{
    private String comment;
    private Object[] contents;
    
    @Override
    protected final void replaceChildWith(final ParseTree dist, final ParseTree replacement) throws ParseTreeException {
        DebugOut.println("NonLeaf.replaceChildWith() " + dist + " with " + replacement);
        for (int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] == dist) {
                this.contents[i] = replacement;
                ((ParseTreeObject)replacement).setParent(this);
                return;
            }
        }
        throw new ParseTreeException("no replacing target " + dist + " for " + replacement + " in the source code : " + this.toString());
    }
    
    public NonLeaf() {
        this.comment = "";
        this.contents = null;
        this.contents = new Object[0];
    }
    
    @Override
    public ParseTree makeRecursiveCopy() {
        final NonLeaf result = (NonLeaf)this.makeCopy();
        final Object[] newc = new Object[this.contents.length];
        for (int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] instanceof ParseTree) {
                final ParseTree src = (ParseTree)this.contents[i];
                newc[i] = src.makeRecursiveCopy();
            }
            else if (this.contents[i] instanceof String[]) {
                final String[] srcary = (String[])this.contents[i];
                final String[] destary = new String[srcary.length];
                System.arraycopy(srcary, 0, destary, 0, srcary.length);
                newc[i] = destary;
            }
            else if (this.contents[i] instanceof TypeName[]) {
                final TypeName[] srcary2 = (TypeName[])this.contents[i];
                final TypeName[] destary2 = new TypeName[srcary2.length];
                for (int j = 0; j < srcary2.length; ++j) {
                    destary2[j] = (TypeName)srcary2[j].makeRecursiveCopy();
                }
                newc[i] = destary2;
            }
            else if (this.contents[i] instanceof VariableDeclarator[]) {
                final VariableDeclarator[] srcary3 = (VariableDeclarator[])this.contents[i];
                final VariableDeclarator[] destary3 = new VariableDeclarator[srcary3.length];
                for (int j = 0; j < srcary3.length; ++j) {
                    destary3[j] = (VariableDeclarator)srcary3[j].makeRecursiveCopy();
                }
                newc[i] = destary3;
            }
            else if (this.contents[i] instanceof Object[]) {
                System.err.println("makeRecursiveCopy() not supported in " + this.getClass());
                newc[i] = this.contents[i];
            }
            else {
                newc[i] = this.contents[i];
            }
        }
        result.set(newc);
        return result;
    }
    
    @Override
    public boolean equals(final ParseTree p) {
        if (p == null || this.getClass() != p.getClass()) {
            return false;
        }
        if (this == p) {
            return true;
        }
        final NonLeaf nlp = (NonLeaf)p;
        final int len = this.getLength();
        if (len != nlp.getLength()) {
            return false;
        }
        System.err.println("equals() not supported in " + this.getClass().getName());
        for (int i = 0; i < len; ++i) {}
        return true;
    }
    
    protected void set(final Object[] ptrees) {
        this.contents = ptrees;
        for (int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] instanceof ParseTreeObject) {
                ((ParseTreeObject)this.contents[i]).setParent(this);
            }
        }
    }
    
    protected void set(final Object p) {
        this.set(new Object[] { p });
    }
    
    protected void set(final Object p0, final Object p1) {
        this.set(new Object[] { p0, p1 });
    }
    
    protected void set(final Object p0, final Object p1, final Object p2) {
        this.set(new Object[] { p0, p1, p2 });
    }
    
    protected void set(final Object p0, final Object p1, final Object p2, final Object p3) {
        this.set(new Object[] { p0, p1, p2, p3 });
    }
    
    protected void set(final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        this.set(new Object[] { p0, p1, p2, p3, p4 });
    }
    
    protected void set(final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        this.set(new Object[] { p0, p1, p2, p3, p4, p5 });
    }
    
    protected void set(final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        this.set(new Object[] { p0, p1, p2, p3, p4, p5, p6 });
    }
    
    protected void set(final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        this.set(new Object[] { p0, p1, p2, p3, p4, p5, p6, p7 });
    }
    
    protected Object elementAt(final int i) {
        Object ret = null;
        try {
            ret = this.contents[i];
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
        }
        return ret;
    }
    
    protected void setElementAt(final Object p, final int i) {
        try {
            this.contents[i] = p;
            if (this.contents[i] instanceof ParseTreeObject) {
                ((ParseTreeObject)this.contents[i]).setParent(this);
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
        }
    }
    
    public Object[] getContents() {
        return this.contents;
    }
    
    protected int getLength() {
        return this.contents.length;
    }
    
    public void setComment(final String comment) {
        this.comment = comment;
    }
    
    public String getComment() {
        return this.comment;
    }
    
    @Override
    public void childrenAccept(final ParseTreeVisitor visitor) throws ParseTreeException {
        if (this.contents == null) {
            return;
        }
        for (int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] instanceof VariableDeclarator[]) {
                final VariableDeclarator[] array;
                final VariableDeclarator[] vds = array = (VariableDeclarator[])this.contents[i];
                for (final ParseTree ptree : array) {
                    final VariableDeclarator vd = (VariableDeclarator)ptree;
                    ptree.accept(visitor);
                }
            }
            else if (this.contents[i] instanceof ParseTree) {
                final ParseTree ptree2 = (ParseTree)this.contents[i];
                ptree2.accept(visitor);
            }
        }
    }
}

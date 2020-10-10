// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class Leaf extends ParseTreeObject implements ParseTree
{
    private String textString;
    private int tokenID;
    public int line;
    public int charBegin;
    
    @Override
    protected void replaceChildWith(final ParseTree parseTree, final ParseTree parseTree2) throws ParseTreeException {
        throw new ParseTreeException("no child");
    }
    
    public Leaf(final String s) {
        this(-1, s, -1, -1);
    }
    
    public Leaf(final int n, final String s) {
        this(n, s, -1, -1);
    }
    
    public Leaf(final int tokenID, final String textString, final int line, final int charBegin) {
        this.textString = null;
        this.tokenID = 0;
        this.line = -1;
        this.charBegin = -1;
        this.tokenID = tokenID;
        this.textString = textString;
        this.line = line;
        this.charBegin = charBegin;
    }
    
    @Override
    public String toString() {
        return this.textString;
    }
    
    @Override
    public ParseTree makeRecursiveCopy() {
        return (ParseTree)this.clone();
    }
    
    @Override
    public ParseTree makeCopy() {
        return (ParseTree)this.clone();
    }
    
    @Override
    public boolean equals(final ParseTree parseTree) {
        return parseTree != null && parseTree instanceof Leaf && (this == parseTree || this.toString().equals(parseTree.toString()));
    }
    
    public boolean equals(final String anObject) {
        return anObject != null && this.toString().equals(anObject);
    }
    
    public int getTokenID() {
        return this.tokenID;
    }
    
    @Override
    public void childrenAccept(final ParseTreeVisitor parseTreeVisitor) {
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}

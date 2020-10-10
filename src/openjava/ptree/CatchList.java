// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class CatchList extends List
{
    private static final String LNLN;
    
    public CatchList() {
        super(CatchList.LNLN);
    }
    
    public CatchList(final CatchBlock catchBlock) {
        super(CatchList.LNLN, catchBlock);
    }
    
    public CatchBlock get(final int n) {
        return (CatchBlock)this.contents_elementAt(n);
    }
    
    public void add(final CatchBlock catchBlock) {
        this.contents_addElement(catchBlock);
    }
    
    public void set(final int n, final CatchBlock catchBlock) {
        this.contents_setElementAt(catchBlock, n);
    }
    
    public CatchBlock remove(final int n) {
        final CatchBlock catchBlock = (CatchBlock)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return catchBlock;
    }
    
    public void insertElementAt(final CatchBlock catchBlock, final int n) {
        this.contents_insertElementAt(catchBlock, n);
    }
    
    public void addAll(final CatchList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public CatchList subList(final int n, final int n2) {
        final CatchList list = new CatchList();
        for (int i = n; i < n2; ++i) {
            list.add(this.get(i));
        }
        return list;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    static {
        LNLN = ParseTreeObject.LN + ParseTreeObject.LN;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ParseTree;
import openjava.ptree.ObjectList;

public final class DefaultListRule extends SeparatedListRule
{
    private ObjectList list;
    
    public DefaultListRule(final SyntaxRule syntaxRule, final int n, final boolean b) {
        super(syntaxRule, n, b);
        this.list = null;
    }
    
    public DefaultListRule(final SyntaxRule syntaxRule, final int n) {
        this(syntaxRule, n, false);
    }
    
    @Override
    protected void initList() {
        this.list = new ObjectList();
    }
    
    @Override
    protected void addListElement(final Object o) {
        this.list.add(o);
    }
    
    @Override
    protected ParseTree getList() {
        return this.list;
    }
}

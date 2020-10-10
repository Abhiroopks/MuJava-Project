// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class MemberInitializer extends NonLeaf implements MemberDeclaration
{
    private boolean _isStatic;
    
    public MemberInitializer(final StatementList list) {
        this(list, false);
    }
    
    public MemberInitializer(final StatementList p2, final boolean isStatic) {
        this._isStatic = false;
        this._isStatic = isStatic;
        this.set(p2);
    }
    
    public MemberInitializer() {
        this._isStatic = false;
    }
    
    public boolean isStatic() {
        return this._isStatic;
    }
    
    public StatementList getBody() {
        return (StatementList)this.elementAt(0);
    }
    
    public void setBody(final StatementList p) {
        this.setElementAt(p, 0);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class TypeParameter extends NonLeaf implements ParseTree
{
    public TypeParameter(final String p2, final String p3) {
        this.set(p2, p3);
    }
    
    TypeParameter() {
    }
    
    public String getName() {
        return (String)this.elementAt(0);
    }
    
    public void setName(final String p) {
        this.setElementAt(p, 0);
    }
    
    public String getTypeBound() {
        return (String)this.elementAt(1);
    }
    
    public void setTypeBounds(final String p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public String toString() {
        if (this.getTypeBound() == "") {
            return this.getName();
        }
        return this.getName() + " extends " + this.getTypeBound();
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}

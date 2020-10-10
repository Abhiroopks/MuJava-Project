// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class VariableDeclarator extends NonLeaf
{
    int dims;
    
    public VariableDeclarator(final String p3, final int dims, final VariableInitializer p4) {
        this.dims = 0;
        this.set(p3, p4);
        this.dims = dims;
    }
    
    public VariableDeclarator(final String s, final VariableInitializer variableInitializer) {
        this(s, 0, variableInitializer);
    }
    
    VariableDeclarator() {
        this.dims = 0;
    }
    
    public String getVariable() {
        return (String)this.elementAt(0);
    }
    
    public void setVariable(final String p) {
        this.setElementAt(p, 0);
    }
    
    public int getDimension() {
        return this.dims;
    }
    
    public String dimensionString() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.getDimension(); ++i) {
            sb.append("[]");
        }
        return sb.toString();
    }
    
    public VariableInitializer getInitializer() {
        return (VariableInitializer)this.elementAt(1);
    }
    
    public void setInitializer(final VariableInitializer p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}

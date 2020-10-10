// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class TypeParameterList extends List
{
    private static final String LNLN;
    
    public TypeParameterList() {
        super(TypeParameterList.LNLN);
    }
    
    public TypeParameterList(final TypeParameter typeParameter) {
        super(TypeParameterList.LNLN, typeParameter);
    }
    
    public TypeParameter get(final int n) {
        return (TypeParameter)this.contents_elementAt(n);
    }
    
    public void add(final TypeParameter typeParameter) {
        this.contents_addElement(typeParameter);
    }
    
    @Override
    public String toString() {
        if (this.contents_size() == 0) {
            return "";
        }
        String s = "<" + this.get(0).toString();
        for (int i = 1; i < this.contents_size(); ++i) {
            s = s + ", " + this.get(i).toString();
        }
        return s + ">";
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    static {
        LNLN = ParseTreeObject.LN + ParseTreeObject.LN;
    }
}

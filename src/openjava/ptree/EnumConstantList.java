// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class EnumConstantList extends List
{
    private static final String LNLN;
    
    public EnumConstantList() {
        super(EnumConstantList.LNLN);
    }
    
    public EnumConstantList(final EnumConstant enumConstant) {
        super(EnumConstantList.LNLN, enumConstant);
    }
    
    public EnumConstant get(final int n) {
        return (EnumConstant)this.contents_elementAt(n);
    }
    
    public void add(final EnumConstant enumConstant) {
        this.contents_addElement(enumConstant);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    static {
        LNLN = ParseTreeObject.LN + ParseTreeObject.LN;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ParameterList extends List
{
    private static final String LNLN;
    
    public ParameterList() {
        super(ParameterList.LNLN);
    }
    
    public ParameterList(final Parameter parameter) {
        super(ParameterList.LNLN, parameter);
    }
    
    public Parameter get(final int n) {
        return (Parameter)this.contents_elementAt(n);
    }
    
    public void add(final Parameter parameter) {
        this.contents_addElement(parameter);
    }
    
    public void set(final int n, final Parameter parameter) {
        this.contents_setElementAt(parameter, n);
    }
    
    public Parameter remove(final int n) {
        final Parameter parameter = (Parameter)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return parameter;
    }
    
    public void insertElementAt(final Parameter parameter, final int n) {
        this.contents_insertElementAt(parameter, n);
    }
    
    public void addAll(final ParameterList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public ParameterList subList(final int n, final int n2) {
        final ParameterList list = new ParameterList();
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

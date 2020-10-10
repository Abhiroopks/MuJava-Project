// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class CaseLabelList extends List
{
    private static final String LNLN;
    
    public CaseLabelList() {
        super(CaseLabelList.LNLN);
    }
    
    public CaseLabelList(final CaseLabel caseLabel) {
        super(CaseLabelList.LNLN, caseLabel);
    }
    
    public CaseLabel get(final int n) {
        return (CaseLabel)this.contents_elementAt(n);
    }
    
    public void add(final CaseLabel caseLabel) {
        this.contents_addElement(caseLabel);
    }
    
    public void set(final int n, final CaseLabel caseLabel) {
        this.contents_setElementAt(caseLabel, n);
    }
    
    public CaseLabel remove(final int n) {
        final CaseLabel caseLabel = (CaseLabel)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return caseLabel;
    }
    
    public void insertElementAt(final CaseLabel caseLabel, final int n) {
        this.contents_insertElementAt(caseLabel, n);
    }
    
    public void addAll(final CaseLabelList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public CaseLabelList subList(final int n, final int n2) {
        final CaseLabelList list = new CaseLabelList();
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

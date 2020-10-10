// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class CaseGroupList extends List
{
    private static final String LNLN;
    
    public CaseGroupList() {
        super(CaseGroupList.LNLN);
    }
    
    public CaseGroupList(final CaseGroup caseGroup) {
        super(CaseGroupList.LNLN, caseGroup);
    }
    
    public CaseGroup get(final int n) {
        return (CaseGroup)this.contents_elementAt(n);
    }
    
    public void add(final CaseGroup caseGroup) {
        this.contents_addElement(caseGroup);
    }
    
    public void set(final int n, final CaseGroup caseGroup) {
        this.contents_setElementAt(caseGroup, n);
    }
    
    public CaseGroup remove(final int n) {
        final CaseGroup caseGroup = (CaseGroup)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return caseGroup;
    }
    
    public void insertElementAt(final CaseGroup caseGroup, final int n) {
        this.contents_insertElementAt(caseGroup, n);
    }
    
    public void addAll(final CaseGroupList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public CaseGroupList subList(final int n, final int n2) {
        final CaseGroupList list = new CaseGroupList();
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

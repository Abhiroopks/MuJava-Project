// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class MemberDeclarationList extends List
{
    private static final String LNLN;
    
    public MemberDeclarationList() {
        super(MemberDeclarationList.LNLN);
    }
    
    public MemberDeclarationList(final MemberDeclaration memberDeclaration) {
        super(MemberDeclarationList.LNLN, memberDeclaration);
    }
    
    public MemberDeclaration get(final int n) {
        return (MemberDeclaration)this.contents_elementAt(n);
    }
    
    public void add(final MemberDeclaration memberDeclaration) {
        this.contents_addElement(memberDeclaration);
    }
    
    public void set(final int n, final MemberDeclaration memberDeclaration) {
        this.contents_setElementAt(memberDeclaration, n);
    }
    
    public MemberDeclaration remove(final int n) {
        final MemberDeclaration memberDeclaration = (MemberDeclaration)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return memberDeclaration;
    }
    
    public void insertElementAt(final MemberDeclaration memberDeclaration, final int n) {
        this.contents_insertElementAt(memberDeclaration, n);
    }
    
    public void addAll(final MemberDeclarationList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public MemberDeclarationList subList(final int n, final int n2) {
        final MemberDeclarationList list = new MemberDeclarationList();
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

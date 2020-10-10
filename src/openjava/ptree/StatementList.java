// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class StatementList extends List
{
    private static final String LNLN;
    
    public StatementList() {
        super(StatementList.LNLN);
    }
    
    public StatementList(final Statement statement) {
        super(StatementList.LNLN, statement);
    }
    
    public StatementList(final Statement statement, final Statement statement2) {
        this(statement);
        this.add(statement2);
    }
    
    public StatementList(final Statement statement, final Statement statement2, final Statement statement3) {
        this(statement, statement2);
        this.add(statement3);
    }
    
    public Statement get(final int n) {
        return (Statement)this.contents_elementAt(n);
    }
    
    public void add(final Statement statement) {
        this.contents_addElement(statement);
    }
    
    public void set(final int n, final Statement statement) {
        this.contents_setElementAt(statement, n);
    }
    
    public Statement remove(final int n) {
        final Statement statement = (Statement)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return statement;
    }
    
    public void insertElementAt(final Statement statement, final int n) {
        this.contents_insertElementAt(statement, n);
    }
    
    public void addAll(final StatementList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public StatementList subList(final int n, final int n2) {
        final StatementList list = new StatementList();
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

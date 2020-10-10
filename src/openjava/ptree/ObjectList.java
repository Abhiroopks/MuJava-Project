// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ObjectList extends List
{
    public ObjectList() {
        super(" ");
    }
    
    public ObjectList(final Object o) {
        super(" ", o);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OBJECTLIST {");
        if (!this.isEmpty()) {
            sb.append(this.get(0));
        }
        for (int i = 1; i < this.size(); ++i) {
            sb.append(",");
            sb.append(this.get(i));
        }
        sb.append("}");
        return sb.toString();
    }
    
    public Object get(final int n) {
        return this.contents_elementAt(n);
    }
    
    public void add(final Object o) {
        this.contents_addElement(o);
    }
    
    public void set(final int n, final Object o) {
        this.contents_setElementAt(o, n);
    }
    
    public Object remove(final int n) {
        final Object contents_element = this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return contents_element;
    }
    
    public void insertElementAt(final Object o, final int n) {
        this.contents_insertElementAt(o, n);
    }
    
    public void addAll(final ObjectList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public ObjectList subList(final int n, final int n2) {
        final ObjectList list = new ObjectList();
        for (int i = n; i < n2; ++i) {
            list.add(this.get(i));
        }
        return list;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}

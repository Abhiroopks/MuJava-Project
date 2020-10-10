// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import java.util.Enumeration;
import openjava.tools.DebugOut;
import java.util.Vector;

public abstract class List extends ParseTreeObject implements ParseTree
{
    private Vector contents;
    private String delimiter;
    
    @Override
    protected final void replaceChildWith(final ParseTree obj, final ParseTree obj2) throws ParseTreeException {
        DebugOut.println("List.replaceChildWith() " + obj + " with " + obj2);
        for (int i = 0; i < this.contents.size(); ++i) {
            if (this.contents_elementAt(i) == obj) {
                this.contents_setElementAt(obj2, i);
                return;
            }
        }
        throw new ParseTreeException("no replacing target");
    }
    
    protected void contents_addElement(final Object obj) {
        this.contents.addElement(obj);
        if (obj instanceof ParseTreeObject) {
            ((ParseTreeObject)obj).setParent(this);
        }
    }
    
    protected void contents_insertElementAt(final Object obj, final int index) {
        this.contents.insertElementAt(obj, index);
        if (obj instanceof ParseTreeObject) {
            ((ParseTreeObject)obj).setParent(this);
        }
    }
    
    protected void contents_setElementAt(final Object obj, final int index) {
        this.contents.setElementAt(obj, index);
        if (obj instanceof ParseTreeObject) {
            ((ParseTreeObject)obj).setParent(this);
        }
    }
    
    protected Object contents_elementAt(final int index) {
        return this.contents.elementAt(index);
    }
    
    protected void contents_removeElementAt(final int index) {
        this.contents.removeElementAt(index);
    }
    
    protected int contents_size() {
        return this.contents.size();
    }
    
    protected List() {
        this.contents = new Vector();
        this.delimiter = ParseTreeObject.LN;
        this.contents = new Vector();
    }
    
    protected List(final Object o) {
        this();
        this.contents_addElement(o);
    }
    
    protected List(final String delimiter) {
        this();
        this.delimiter = delimiter;
    }
    
    protected List(final String s, final Object o) {
        this(s);
        this.contents_addElement(o);
    }
    
    public Enumeration elements() {
        return this.contents.elements();
    }
    
    public int size() {
        return this.contents.size();
    }
    
    public boolean isEmpty() {
        return this.contents.isEmpty();
    }
    
    public void removeAll() {
        this.contents.removeAllElements();
    }
    
    public boolean contains(final String anObject) {
        final Enumeration<Object> elements = this.contents.elements();
        while (elements.hasMoreElements()) {
            final Object nextElement = elements.nextElement();
            if (nextElement != null && nextElement.toString().equals(anObject)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean equals(final ParseTree parseTree) {
        if (parseTree == null) {
            return false;
        }
        if (this == parseTree) {
            return true;
        }
        if (this.getClass() != parseTree.getClass()) {
            return false;
        }
        final List list = (List)parseTree;
        final int size = this.size();
        if (list.size() != size) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            final Object element = this.contents.elementAt(i);
            final Object element2 = list.contents.elementAt(i);
            if (element != null && element2 == null) {
                return false;
            }
            if (element == null && element2 != null) {
                return false;
            }
            if (element != null || element2 != null) {
                if (!element.equals(element2)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public ParseTree makeRecursiveCopy() {
        final List list = (List)this.clone();
        list.contents = new Vector();
        final Enumeration<ParseTree> elements = this.contents.elements();
        while (elements.hasMoreElements()) {
            ParseTree parseTree = elements.nextElement();
            if (parseTree instanceof ParseTree) {
                parseTree = parseTree.makeRecursiveCopy();
            }
            list.contents_addElement(parseTree);
        }
        return list;
    }
    
    @Override
    public void childrenAccept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        if (this.contents == null) {
            return;
        }
        for (int size = this.contents.size(), i = 0; i < size; ++i) {
            final Object element = this.contents.elementAt(i);
            if (element instanceof ParseTree) {
                ((ParseTree)element).accept(parseTreeVisitor);
            }
        }
    }
}

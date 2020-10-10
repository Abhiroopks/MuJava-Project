// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ArrayInitializer extends List implements VariableInitializer
{
    private boolean _isRemainderOmitted;
    
    public ArrayInitializer() {
        super(", ");
        this._isRemainderOmitted = false;
    }
    
    public ArrayInitializer(final VariableInitializer variableInitializer) {
        this();
        this.add(variableInitializer);
    }
    
    public ArrayInitializer(final ExpressionList list) {
        this();
        for (int size = list.size(), i = 0; i < size; ++i) {
            this.add(list.get(i));
        }
    }
    
    public void omitRemainder(final boolean isRemainderOmitted) {
        this._isRemainderOmitted = isRemainderOmitted;
    }
    
    public void omitRemainder() {
        this._isRemainderOmitted = true;
    }
    
    public boolean isRemainderOmitted() {
        return this._isRemainderOmitted;
    }
    
    public VariableInitializer get(final int n) {
        return (VariableInitializer)this.contents_elementAt(n);
    }
    
    public void add(final VariableInitializer variableInitializer) {
        this.contents_addElement(variableInitializer);
    }
    
    public void set(final int n, final VariableInitializer variableInitializer) {
        this.contents_setElementAt(variableInitializer, n);
    }
    
    public VariableInitializer remove(final int n) {
        final VariableInitializer variableInitializer = (VariableInitializer)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return variableInitializer;
    }
    
    public void insertElementAt(final VariableInitializer variableInitializer, final int n) {
        this.contents_insertElementAt(variableInitializer, n);
    }
    
    public void addAll(final ArrayInitializer arrayInitializer) {
        for (int i = 0; i < arrayInitializer.size(); ++i) {
            this.contents_addElement(arrayInitializer.get(i));
        }
    }
    
    public ArrayInitializer subList(final int n, final int n2) {
        final ArrayInitializer arrayInitializer = new ArrayInitializer();
        for (int i = n; i < n2; ++i) {
            arrayInitializer.add(this.get(i));
        }
        return arrayInitializer;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Node
{
    private Object obj;
    private List<Edge> outGoingEdges;
    
    Node(final Object obj) {
        this.obj = obj;
        this.outGoingEdges = new ArrayList<Edge>();
    }
    
    public Object getObject() {
        return this.obj;
    }
    
    @Override
    public String toString() {
        return this.obj.toString();
    }
    
    public boolean equals(final Node n) {
        return this.obj.equals(n.getObject());
    }
    
    void addOutGoing(final Edge e) {
        this.outGoingEdges.add(e);
    }
    
    void removeOutGoing(final Edge e) {
        this.outGoingEdges.remove(e);
    }
    
    public int sizeOfOutEdges() {
        return this.outGoingEdges.size();
    }
    
    public Iterator<Edge> getOutGoingIterator() {
        return this.outGoingEdges.iterator();
    }
    
    public List<Edge> getOutGoingEdges() {
        return this.outGoingEdges;
    }
}

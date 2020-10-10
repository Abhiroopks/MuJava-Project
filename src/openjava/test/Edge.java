// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

public class Edge
{
    Node src;
    Node dest;
    Object weight;
    int capacity;
    int flow;
    
    Edge(final Node s, final Node d) {
        this.src = s;
        this.dest = d;
        this.weight = null;
        this.capacity = 0;
        this.flow = 0;
        this.src.addOutGoing(this);
    }
    
    Edge(final Node s, final Node d, final Object weight) {
        this.src = s;
        this.dest = d;
        this.weight = weight;
        this.capacity = 0;
        this.flow = 0;
        this.src.addOutGoing(this);
    }
    
    Edge(final Node s, final Node d, final Object weight, final int capacity, final int flow) {
        this.src = s;
        this.dest = d;
        this.weight = weight;
        this.capacity = capacity;
        this.flow = flow;
        this.src.addOutGoing(this);
    }
    
    public Node getSrc() {
        return this.src;
    }
    
    public Node getDest() {
        return this.dest;
    }
    
    public Object getWeight() {
        return this.weight;
    }
    
    public int getCapacity() {
        return this.capacity;
    }
    
    public int getFlow() {
        return this.flow;
    }
    
    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }
    
    public void setFlow(final int flow) {
        this.flow = flow;
    }
    
    public boolean equals(final Edge e) {
        return this.src.equals(e.getSrc()) && this.dest.equals(e.getDest());
    }
    
    @Override
    public String toString() {
        return "(" + this.src.getObject() + ", " + this.dest.getObject() + ")";
    }
    
    public String toStringWithFlow() {
        return "(" + this.src.getObject() + ", " + this.dest.getObject() + ")" + "; flow: " + this.getFlow() + "; capacity: " + this.getCapacity();
    }
}

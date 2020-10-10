// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public abstract class GraphBase
{
    List<Edge> edges;
    List<Node> nodes;
    
    public GraphBase() {
        this.edges = new ArrayList<Edge>();
        this.nodes = new ArrayList<Node>();
    }
    
    public Iterator<Node> getNodeIterator() {
        return this.nodes.iterator();
    }
    
    public int sizeOfNodes() {
        return this.nodes.size();
    }
    
    public int sizeOfEdges() {
        return this.edges.size();
    }
    
    public Node findNode(final Object obj) {
        for (int i = 0; i < this.nodes.size(); ++i) {
            if (this.nodes.get(i).getObject().equals(obj)) {
                return this.nodes.get(i);
            }
        }
        return null;
    }
    
    public Edge findEdge(final Object objSrc, final Object objDest) {
        for (int i = 0; i < this.edges.size(); ++i) {
            if (this.edges.get(i).getSrc().getObject().equals(objSrc) && this.edges.get(i).getDest().getObject().equals(objDest)) {
                return this.edges.get(i);
            }
        }
        return null;
    }
    
    public Iterator<Edge> getEdgeIterator() {
        return this.edges.iterator();
    }
    
    public Node createNode(final Object obj) {
        for (int i = 0; i < this.nodes.size(); ++i) {
            if (this.nodes.get(i).getObject().equals(obj)) {
                return this.nodes.get(i);
            }
        }
        final Node node = new Node(obj);
        this.nodes.add(node);
        return node;
    }
    
    public Edge createEdge(final Node s, final Node d) {
        Iterator<Edge> outEdges = null;
        Node src = null;
        for (final Node node : this.nodes) {
            if (node.equals(s)) {
                src = node;
                outEdges = node.getOutGoingIterator();
                break;
            }
        }
        if (outEdges != null) {
            while (outEdges.hasNext()) {
                final Edge e = outEdges.next();
                if (d.equals(e.getDest())) {
                    return e;
                }
            }
        }
        Edge e = null;
        if (src != null) {
            e = new Edge(src, d);
        }
        else {
            e = new Edge(s, d);
        }
        this.edges.add(e);
        return e;
    }
    
    public Edge createEdge(final Node s, final Node d, final Object weight) {
        Iterator<Edge> outEdges = null;
        Node src = null;
        for (final Node node : this.nodes) {
            if (node.equals(s)) {
                src = node;
                outEdges = node.getOutGoingIterator();
                break;
            }
        }
        if (outEdges != null) {
            while (outEdges.hasNext()) {
                final Edge e = outEdges.next();
                if (d.equals(e.getDest())) {
                    return e;
                }
            }
        }
        Edge e = null;
        if (src != null) {
            e = new Edge(src, d, weight);
        }
        else {
            e = new Edge(s, d, weight);
        }
        this.edges.add(e);
        return e;
    }
    
    public Edge createEdge(final Node s, final Node d, final Object weight, final int capacity, final int flow) {
        Iterator<Edge> outEdges = null;
        Node src = null;
        for (final Node node : this.nodes) {
            if (node.equals(s)) {
                src = node;
                outEdges = node.getOutGoingIterator();
                break;
            }
        }
        if (outEdges != null) {
            while (outEdges.hasNext()) {
                final Edge e = outEdges.next();
                if (d.equals(e.getDest())) {
                    return e;
                }
            }
        }
        Edge e = null;
        if (src != null) {
            e = new Edge(src, d, weight, capacity, flow);
        }
        else {
            e = new Edge(s, d, weight, capacity, flow);
        }
        this.edges.add(e);
        return e;
    }
    
    public Edge createEdge(final Edge edge) {
        Iterator<Edge> outEdges = null;
        Node src = null;
        for (final Node node : this.nodes) {
            if (node.equals(edge.src)) {
                src = node;
                outEdges = node.getOutGoingIterator();
                break;
            }
        }
        if (outEdges != null) {
            while (outEdges.hasNext()) {
                final Edge e = outEdges.next();
                if (edge.dest.equals(e.getDest())) {
                    return e;
                }
            }
        }
        Edge e = null;
        if (src != null) {
            e = new Edge(src, edge.dest, edge.getWeight(), edge.getCapacity(), edge.getFlow());
        }
        else {
            e = new Edge(edge.src, edge.dest, edge.getWeight(), edge.getCapacity(), edge.getFlow());
        }
        this.edges.add(e);
        return e;
    }
    
    @Deprecated
    public void addEdge(final Edge e) {
        this.edges.add(e);
    }
    
    public abstract void validate() throws InvalidGraphException;
    
    public static List<Path> minimize(final List<Path> paths) {
        final List<Path> result = new ArrayList<Path>();
        for (int i = 0; i < paths.size(); ++i) {
            result.add(paths.get(i));
        }
        for (int i = 0; i < result.size(); ++i) {
            for (int j = i + 1; j < result.size(); ++j) {
                if (result.get(i).indexOf(result.get(j)) != -1) {
                    result.remove(j);
                    break;
                }
                if (result.get(j).indexOf(result.get(i)) != -1) {
                    result.remove(i);
                    --i;
                    break;
                }
            }
        }
        return result;
    }
}

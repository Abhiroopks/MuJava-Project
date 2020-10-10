// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Path
{
    List<Node> path;
    
    public Path() {
        this.path = new ArrayList<Node>();
    }
    
    public Path(final Edge e) {
        (this.path = new ArrayList<Node>()).add(e.getSrc());
        this.path.add(e.getDest());
    }
    
    public Path(final Node n) {
        (this.path = new ArrayList<Node>()).add(n);
    }
    
    public Path(final Path p) {
        this.path = new ArrayList<Node>();
        for (final Node node : p.path) {
            this.path.add(node);
        }
    }
    
    public Iterator<Node> getNodeIterator() {
        return this.path.iterator();
    }
    
    public List<Edge> getEdgeList() {
        final List<Edge> edges = new ArrayList<Edge>();
        if (this.size() > 1) {
            for (int i = 0; i < this.path.size() - 1; ++i) {
                edges.add(new Edge(this.path.get(i), this.path.get(i + 1)));
            }
        }
        return edges;
    }
    
    public Path immutableExtendedPath(final Path p) {
        final Iterator<Node> nodes = p.getNodeIterator();
        final Iterator<Node> nodes2 = this.path.iterator();
        final Path newPath = new Path();
        while (nodes2.hasNext()) {
            final Node node = nodes2.next();
            newPath.path.add(node);
        }
        while (nodes.hasNext()) {
            newPath.path.add(nodes.next());
        }
        return newPath;
    }
    
    public void extendPath(final Path p) {
        final Iterator<Node> nodes = p.getNodeIterator();
        while (nodes.hasNext()) {
            this.path.add(nodes.next());
        }
    }
    
    public void extendPath(final Node n) {
        this.path.add(n);
    }
    
    public boolean isCycle() {
        return this.path.get(0).equals(this.path.get(this.path.size() - 1));
    }
    
    public Object clone() {
        final Path p = new Path(this.path.get(0));
        for (int i = 1; i < this.path.size(); ++i) {
            p.extendPath(this.path.get(i));
        }
        return p;
    }
    
    public int size() {
        return this.path.size();
    }
    
    public Node get(final int index) {
        return this.path.get(index);
    }
    
    public void remove(final int index) {
        this.path.remove(index);
    }
    
    public void remove(final Node node) {
        this.path.remove(node);
    }
    
    public int indexOf(final Node n) {
        for (int i = 0; i < this.path.size(); ++i) {
            if (this.path.get(i).equals(n)) {
                return i;
            }
        }
        return -1;
    }
    
    public int nextIndexOf(final Node n, final int index) {
        if (index <= -1) {
            return -1;
        }
        if (index >= this.path.size()) {
            return -1;
        }
        for (int i = index + 1; i < this.path.size(); ++i) {
            if (this.path.get(i).equals(n) && i != index) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final Node n) {
        for (int i = this.path.size() - 1; i > -1; --i) {
            if (this.path.get(i).equals(n)) {
                return i;
            }
        }
        return -1;
    }
    
    public Node getEnd() {
        return this.path.get(this.path.size() - 1);
    }
    
    public boolean detour(final Path p2) {
        final Iterator<Node> it = p2.getNodeIterator();
        int pointer = 0;
        while (it.hasNext()) {
            boolean detour = false;
            final Node n = it.next();
            while (pointer < this.path.size()) {
                if (this.path.get(pointer).equals(n)) {
                    detour = true;
                    break;
                }
                ++pointer;
            }
            if (!detour) {
                return false;
            }
            ++pointer;
        }
        return true;
    }
    
    public boolean sidetrip(final Path p) {
        final List<Edge> edges = this.getEdgeList();
        final List<Edge> e = p.getEdgeList();
        int pointer = 0;
        for (int i = 0; i < e.size(); ++i) {
            boolean sidetrip = false;
            while (pointer < edges.size()) {
                if (edges.get(pointer).equals(e.get(i))) {
                    sidetrip = true;
                    break;
                }
                ++pointer;
            }
            if (!sidetrip) {
                return false;
            }
            ++pointer;
        }
        return true;
    }
    
    public Path subPath(final int i) {
        int counter = i;
        final Node newNode = this.path.get(counter);
        final Path subPath = new Path(newNode);
        ++counter;
        while (counter < this.path.size()) {
            subPath.extendPath(this.path.get(counter));
            ++counter;
        }
        return subPath;
    }
    
    public Path subPath(final int begin, final int end) {
        int counter = begin;
        final Node newNode = this.path.get(counter);
        final Path subPath = new Path(newNode);
        ++counter;
        while (counter < end) {
            subPath.extendPath(this.path.get(counter));
            ++counter;
        }
        return subPath;
    }
    
    public int indexOf(final Path p) {
        final int NOTFOUND = -1;
        int iSub = 0;
        int rtnIndex = -1;
        boolean isPat = false;
        for (int subjectLen = this.size(), patternLen = p.size(); !isPat && iSub + patternLen - 1 < subjectLen; ++iSub) {
            if (this.get(iSub).equals(p.get(0))) {
                rtnIndex = iSub;
                isPat = true;
                for (int iPat = 1; iPat < patternLen; ++iPat) {
                    if (!this.get(iSub + iPat).equals(p.get(iPat))) {
                        rtnIndex = -1;
                        isPat = false;
                        break;
                    }
                }
            }
        }
        return rtnIndex;
    }
    
    @Deprecated
    public boolean isSubpath(final Path s) {
        final int NOTFOUND = -1;
        int iSub = 0;
        int rtnIndex = -1;
        boolean isPat = false;
        for (int subjectLen = s.size(), patternLen = this.size(); !isPat && iSub + patternLen - 1 < subjectLen; ++iSub) {
            if (s.get(iSub).equals(this.get(0))) {
                rtnIndex = iSub;
                isPat = true;
                for (int iPat = 1; iPat < patternLen; ++iPat) {
                    if (!s.get(iSub + iPat).equals(this.get(iPat))) {
                        rtnIndex = -1;
                        isPat = false;
                        break;
                    }
                }
            }
        }
        return rtnIndex != -1;
    }
    
    public int hasOverlapWith(final Path p) {
        return 0;
    }
    
    @Override
    public String toString() {
        if (this.path.size() == 0) {
            return "[]";
        }
        String result = "[" + this.path.get(0);
        for (int i = 1; i < this.path.size() - 1; ++i) {
            result = result + "," + this.path.get(i).toString();
        }
        if (this.path.size() == 1) {
            return result + "]";
        }
        return result + "," + this.path.get(this.path.size() - 1) + "]";
    }
    
    public boolean equals(final Path anotherPath) {
        final Path temp = anotherPath;
        if (this.path.size() != temp.size()) {
            return false;
        }
        for (int i = 0; i < this.path.size(); ++i) {
            if (!this.path.get(i).equals(temp.get(i))) {
                return false;
            }
        }
        return true;
    }
}

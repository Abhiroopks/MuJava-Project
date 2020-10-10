// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph extends GraphBase
{
    List<Node> starts;
    List<Node> ends;
    String[] infeasiblePrimePathsString;
    String[] infeasibleEdgePairsString;
    
    public String[] getInfeasiblePrimePathsString() {
        return this.infeasiblePrimePathsString;
    }
    
    public void setInitialNode(final List<Node> starts) {
        this.starts = starts;
    }
    
    public List<Node> getInitialNode() {
        return this.starts;
    }
    
    public boolean isInitialNode(final Node n) {
        return this.starts != null && this.starts.size() != 0 && this.starts.contains(n);
    }
    
    public boolean isEndingNode(final Node n) {
        return this.ends != null && this.ends.size() != 0 && this.ends.contains(n);
    }
    
    public Iterator<Node> getEndingNodeIterator() {
        if (this.ends == null) {
            this.ends = new ArrayList<Node>();
        }
        return this.ends.iterator();
    }
    
    public Iterator<Node> getInitialNodeIterator() {
        if (this.starts == null) {
            this.starts = new ArrayList<Node>();
        }
        return this.starts.iterator();
    }
    
    @Override
    public int sizeOfEdges() {
        return this.edges.size();
    }
    
    @Override
    public int sizeOfNodes() {
        return this.nodes.size();
    }
    
    public int sizeOfEndingNode() {
        if (this.ends != null) {
            return this.ends.size();
        }
        return 0;
    }
    
    public int sizeOfInitialNode() {
        if (this.starts != null) {
            return this.starts.size();
        }
        return 0;
    }
    
    public void addEndingNode(final Node n) {
        if (this.ends == null) {
            this.ends = new ArrayList<Node>();
        }
        this.ends.add(n);
    }
    
    public void addInitialNode(final Node n) {
        if (this.starts == null) {
            this.starts = new ArrayList<Node>();
        }
        this.starts.add(n);
    }
    
    public List<Path> findTestPath() throws InvalidGraphException {
        final List<Path> paths = this.findSpanningTree();
        final List<Path> result = new ArrayList<Path>();
        for (int i = 0; i < paths.size(); ++i) {
            final Path p = paths.get(i);
            if (this.ends.contains(p.getEnd())) {
                result.add(p);
                paths.remove(i);
                --i;
            }
            else {
                for (int j = 0; j < this.ends.size(); ++j) {
                    final int index = p.indexOf(this.ends.get(j));
                    if (index != -1) {
                        final Path subpath = p.subPath(0, index + 1);
                        boolean exist = false;
                        for (int k = 0; k < result.size(); ++k) {
                            if (subpath.isSubpath(result.get(k))) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            result.add(subpath);
                        }
                    }
                }
            }
        }
        if (result.size() == 0) {
            throw new InvalidGraphException("No ending node.");
        }
        int oldsize = 0;
        do {
            oldsize = paths.size();
            for (int l = 0; l < result.size(); ++l) {
                for (int m = 0; m < paths.size(); ++m) {
                    final Path testPath = result.get(l);
                    final Path path = paths.get(m);
                    final int index2 = testPath.indexOf(path.getEnd());
                    if (index2 != -1) {
                        final Path subpath2 = testPath.subPath(index2 + 1);
                        path.extendPath(subpath2);
                        result.add(path);
                        paths.remove(m);
                        --m;
                    }
                }
            }
        } while (oldsize - paths.size() > 0);
        if (paths.size() != 0) {
            String pathStr = "";
            for (final Path p2 : paths) {
                pathStr = pathStr + " " + p2.toString();
            }
            throw new InvalidGraphException("some paths, " + pathStr + ", never reach an ending node.");
        }
        return result;
    }
    
    public List<Path> findSpanningTree() throws InvalidGraphException {
        this.validate();
        final List<Path> result = new ArrayList<Path>();
        for (int j = 0; j < this.starts.size(); ++j) {
            final List<Node> nodesCopy = new ArrayList<Node>();
            for (int i = 0; i < this.nodes.size(); ++i) {
                nodesCopy.add(this.nodes.get(i));
            }
            for (int i = this.starts.size() - 1; i >= 0; --i) {
                final int index = this.nodes.indexOf(this.starts.get(i));
                nodesCopy.remove(index);
            }
            final List<Path> paths = new ArrayList<Path>();
            paths.add(new Path(this.starts.get(j)));
            while (paths.size() != 0) {
                for (int k = 0; k < paths.size(); ++k) {
                    final Path path = paths.get(k);
                    final Node end = path.getEnd();
                    final Iterator<Edge> outEdges = end.getOutGoingIterator();
                    int count = 0;
                    while (outEdges.hasNext()) {
                        if (++count == end.sizeOfOutEdges()) {
                            path.extendPath(outEdges.next().getDest());
                        }
                        else {
                            final Path newPath = (Path)path.clone();
                            newPath.extendPath(outEdges.next().getDest());
                            paths.add(k + 1, newPath);
                            ++k;
                        }
                    }
                }
                for (int k = 0; k < paths.size(); ++k) {
                    final Path path = paths.get(k);
                    final Node end = path.getEnd();
                    if (nodesCopy.contains(end)) {
                        nodesCopy.remove(end);
                    }
                    else {
                        paths.remove(k);
                        --k;
                        result.add(path);
                    }
                }
            }
        }
        return result;
    }
    
    public List<Path> findPrimePathCoverage(final String infeasiblePrimePathsString) throws InvalidGraphException {
        final List<Path> primes = this.findPrimePaths1(infeasiblePrimePathsString);
        final long start = System.nanoTime();
        final List<Path> testPaths = this.findTestPath();
        for (int i = 0; i < primes.size(); ++i) {
            Path prime = primes.get(i);
            boolean extendHead = false;
            boolean extendTail = false;
            final Node head = prime.get(0);
            final Node tail = prime.getEnd();
            if (this.starts.indexOf(head) != -1) {
                extendHead = true;
            }
            if (this.ends.contains(tail)) {
                extendTail = true;
            }
            for (int j = 0; j < testPaths.size(); ++j) {
                final Path test = testPaths.get(j);
                if (!extendTail) {
                    final int index = test.lastIndexOf(tail);
                    if (index != -1) {
                        final Path sub = test.subPath(index + 1, test.size());
                        prime.extendPath(sub);
                        extendTail = true;
                    }
                }
                if (!extendHead) {
                    final int index = test.indexOf(head);
                    if (index != -1) {
                        final Path sub = test.subPath(0, index);
                        sub.extendPath(prime);
                        prime = sub;
                        extendHead = true;
                    }
                }
                if (extendHead && extendTail) {
                    primes.set(i, prime);
                    break;
                }
            }
            if (!(extendHead & extendTail)) {
                throw new InvalidGraphException("Can't find test path for " + prime.toString());
            }
        }
        for (int i = 0; i < primes.size(); ++i) {
            final Path prime = primes.get(i);
            for (int k = i + 1; k < primes.size(); ++k) {
                if (primes.get(k).isSubpath(prime)) {
                    primes.remove(k);
                    --k;
                }
            }
        }
        for (int i = 0; i < primes.size(); ++i) {
            final Path prime = primes.get(i);
        }
        for (int i = 0; i < primes.size(); ++i) {
            final List<Path> primePathsList = this.findPrimePaths();
            final List<Path> primePathsWithSidetripsList = this.findPrimePaths1(infeasiblePrimePathsString);
            final List<Path> selectedPrimePaths = new ArrayList<Path>();
            final List<Path> selectedPrimePathsWithSidetrips = new ArrayList<Path>();
            for (int z = 0; z < primePathsList.size(); ++z) {
                if (primePathsList.get(z).isSubpath(primes.get(i))) {
                    selectedPrimePaths.add(primePathsList.get(z));
                }
            }
            for (int z = 0; z < primePathsWithSidetripsList.size(); ++z) {
                if (primePathsWithSidetripsList.get(z).isSubpath(primes.get(i))) {
                    selectedPrimePathsWithSidetrips.add(primePathsWithSidetripsList.get(z));
                }
            }
            for (int z = 0; z < selectedPrimePaths.size(); ++z) {}
            for (int z = 0; z < selectedPrimePaths.size(); ++z) {}
            for (int l = 0; l < primes.size(); ++l) {
                boolean sign = false;
                for (int x = 0; x < selectedPrimePaths.size(); ++x) {
                    if (!selectedPrimePaths.get(x).isSubpath(primes.get(l))) {
                        sign = true;
                    }
                }
                for (int x = 0; x < selectedPrimePathsWithSidetrips.size(); ++x) {
                    if (!selectedPrimePathsWithSidetrips.get(x).isSubpath(primes.get(l))) {
                        sign = true;
                    }
                }
                if (!sign && !primes.get(i).equals(primes.get(l))) {
                    primes.remove(i);
                    --i;
                    break;
                }
            }
        }
        final long end = System.nanoTime();
        final long duration = end - start;
        return primes;
    }
    
    public List<Path> findPrimePathCoverageWithInfeasibleSubPath(final String infeasiblePrimePathsString, final List<Path> infeasibleSubpaths) throws InvalidGraphException {
        final List<Path> result = new ArrayList<Path>();
        final List<Path> testPathsForPrimePaths = this.findPrimePathCoverage(infeasiblePrimePathsString);
        final List<Path> tempTestPathsForPrimePaths = new ArrayList<Path>();
        final List<Path> tempPrimes = new ArrayList<Path>();
        final List<Path> subPaths = new ArrayList<Path>();
        if (infeasibleSubpaths != null) {
            for (int a = 0; a < infeasibleSubpaths.size(); ++a) {
                subPaths.add(infeasibleSubpaths.get(a));
            }
        }
        for (int i = 0; i < testPathsForPrimePaths.size(); ++i) {
            final Path testPath = testPathsForPrimePaths.get(i);
            for (int j = 0; j < subPaths.size(); ++j) {
                final Path infeasibleSubPath = subPaths.get(j);
                if (infeasibleSubPath.isSubpath(testPath)) {
                    testPathsForPrimePaths.remove(i);
                    --i;
                    final List<Path> primes = this.findPrimePaths1(infeasiblePrimePathsString);
                    for (int k = 0; k < primes.size(); ++k) {
                        boolean exist = false;
                        boolean exist2 = false;
                        for (int k2 = 0; k2 < tempPrimes.size(); ++k2) {
                            if (tempPrimes.get(k2).equals(primes.get(k))) {
                                exist = true;
                            }
                        }
                        for (int k3 = 0; k3 < subPaths.size(); ++k3) {
                            if (primes.get(k).isSubpath(subPaths.get(k3))) {
                                exist2 = true;
                                break;
                            }
                        }
                        if (primes.get(k).isSubpath(testPath) && !exist && !exist2) {
                            tempPrimes.add(primes.get(k));
                        }
                    }
                    for (int l = 0; l < tempPrimes.size(); ++l) {
                        final Path tempPrimes2 = tempPrimes.get(l);
                        for (int m = 0; m < testPathsForPrimePaths.size(); ++m) {
                            if (tempPrimes2.isSubpath(testPathsForPrimePaths.get(m)) && !testPathsForPrimePaths.get(m).equals(testPath)) {
                                tempPrimes.remove(l);
                                --l;
                                break;
                            }
                        }
                    }
                    for (int n = 0; n < tempPrimes.size(); ++n) {
                        final Path tempPrime = tempPrimes.get(n);
                        for (int x = 0; x < this.findTestPath().size(); ++x) {
                            final Path pathForHead = this.findTestPath().get(x);
                            Path subPathForHead = null;
                            final int index1 = pathForHead.lastIndexOf(tempPrime.get(0));
                            if (index1 != -1 && this.starts.indexOf(pathForHead.get(0)) != -1) {
                                subPathForHead = pathForHead.subPath(0, index1);
                                if (index1 == 0) {
                                    subPathForHead.extendPath(tempPrime.subPath(1, tempPrime.size()));
                                }
                                else {
                                    subPathForHead.extendPath(tempPrime);
                                }
                            }
                            for (int y = 0; y < testPathsForPrimePaths.size(); ++y) {
                                final Path pathForTail = testPathsForPrimePaths.get(y);
                                Path subPathForTail = null;
                                Path finalPath = null;
                                final Path finalPath2 = null;
                                if (subPathForHead != null) {
                                    finalPath = subPathForHead;
                                    if (this.ends.indexOf(finalPath.getEnd()) == -1) {
                                        final int index2 = pathForTail.indexOf(finalPath.getEnd());
                                        if (index2 != -1) {
                                            subPathForTail = pathForTail.subPath(index2 + 1, pathForTail.size());
                                            finalPath.extendPath(subPathForTail);
                                        }
                                    }
                                    boolean is = true;
                                    boolean existed = false;
                                    for (int z = 0; z < subPaths.size(); ++z) {
                                        if (subPaths.get(z).isSubpath(finalPath)) {
                                            is = false;
                                        }
                                    }
                                    for (int z2 = 0; z2 < testPathsForPrimePaths.size(); ++z2) {
                                        if (testPathsForPrimePaths.get(z2).equals(finalPath)) {
                                            existed = true;
                                            break;
                                        }
                                    }
                                    boolean existed2 = false;
                                    if (is && !existed && this.ends.indexOf(finalPath.getEnd()) != -1) {
                                        for (int z3 = 0; z3 < testPathsForPrimePaths.size(); ++z3) {
                                            if (tempPrime.isSubpath(finalPath) && tempPrime.isSubpath(testPathsForPrimePaths.get(z3))) {
                                                existed2 = true;
                                            }
                                        }
                                        if (!existed2) {
                                            testPathsForPrimePaths.add(finalPath);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return testPathsForPrimePaths;
    }
    
    public List<Path> findEdgePairCoverage(final String infeasibleEdgePairs) throws InvalidGraphException {
        final List<Path> testPaths = this.findTestPath();
        final List<Path> edgePairs = this.findEdgePairs(infeasibleEdgePairs);
        final List<Path> result = new ArrayList<Path>();
        int i = 0;
    Label_0023:
        while (i < edgePairs.size()) {
            Path edgePair = edgePairs.get(i);
            while (true) {
                for (int k = 0; k < result.size(); ++k) {
                    if (edgePair.isSubpath(result.get(k))) {
                        ++i;
                        continue Label_0023;
                    }
                }
                boolean extendHead = false;
                boolean extendTail = false;
                final Node head = edgePair.get(0);
                final Node tail = edgePair.getEnd();
                if (this.starts.indexOf(head) != -1) {
                    extendHead = true;
                }
                if (this.ends.contains(tail)) {
                    extendTail = true;
                }
                for (int j = 0; j < testPaths.size(); ++j) {
                    final Path testPath = testPaths.get(j);
                    if (!extendTail) {
                        final int index = testPath.lastIndexOf(tail);
                        if (index != -1) {
                            final Path sub = testPath.subPath(index + 1, testPath.size());
                            edgePair.extendPath(sub);
                            extendTail = true;
                        }
                    }
                    if (!extendHead) {
                        final int index = testPath.indexOf(head);
                        if (index != -1) {
                            final Path sub = testPath.subPath(0, index);
                            sub.extendPath(edgePair);
                            edgePair = sub;
                            extendHead = true;
                        }
                    }
                    if (extendHead && extendTail) {
                        result.add(edgePair);
                        break;
                    }
                }
                if (!(extendHead & extendTail)) {
                    throw new InvalidGraphException("Can't find test path for " + edgePair.toString());
                }
                continue;
            }
        }
        for (i = 0; i < result.size(); ++i) {
            final Path edgePair = result.get(i);
            for (int l = i + 1; l < result.size(); ++l) {
                if (result.get(l).isSubpath(edgePair)) {
                    result.remove(l);
                    --l;
                }
            }
        }
        for (i = 0; i < result.size(); ++i) {
            final List<Path> edgePairsList = this.findEdgePairs();
            final List<Path> edgePairsWithSidetripsList = this.findEdgePairs(infeasibleEdgePairs);
            final List<Path> selectedEdgePairs = new ArrayList<Path>();
            final List<Path> selectedEdgePairsWithSidetrips = new ArrayList<Path>();
            for (int z = 0; z < edgePairsList.size(); ++z) {
                if (edgePairsList.get(z).isSubpath(result.get(i))) {
                    selectedEdgePairs.add(edgePairsList.get(z));
                }
            }
            for (int z = 0; z < edgePairsWithSidetripsList.size(); ++z) {
                if (edgePairsWithSidetripsList.get(z).isSubpath(result.get(i))) {
                    selectedEdgePairsWithSidetrips.add(edgePairsWithSidetripsList.get(z));
                }
            }
            for (int m = 0; m < result.size(); ++m) {
                boolean sign = false;
                for (int x = 0; x < selectedEdgePairs.size(); ++x) {
                    if (!selectedEdgePairs.get(x).isSubpath(result.get(m))) {
                        sign = true;
                    }
                }
                for (int x = 0; x < selectedEdgePairsWithSidetrips.size(); ++x) {
                    if (!selectedEdgePairsWithSidetrips.get(x).isSubpath(result.get(m))) {
                        sign = true;
                    }
                }
                if (!sign && !result.get(i).equals(result.get(m))) {
                    result.remove(i);
                }
            }
        }
        return result;
    }
    
    public List<Path> findEdgeCoverage() throws InvalidGraphException {
        final List<Path> result = this.findTestPath();
        final List<Path> resultCopy = new ArrayList<Path>();
        for (int i = 0; i < result.size(); ++i) {
            resultCopy.add(result.get(i));
        }
        for (int i = result.size() - 1; i > -1; --i) {
            for (int j = 0; j < resultCopy.size(); ++j) {
                final Path p1 = result.get(i);
                final Path p2 = resultCopy.get(j);
                if (p1 != p2 && p1.sidetrip(p2)) {
                    resultCopy.remove(j);
                }
            }
        }
        final List<Path> resultCopy2 = new ArrayList<Path>();
        final List<Path> resultCopy3 = new ArrayList<Path>();
        for (int k = 0; k < resultCopy.size(); ++k) {
            resultCopy3.add(resultCopy.get(k));
        }
        final List<Edge> edgeCopy = new ArrayList<Edge>();
        for (int l = 0; l < this.edges.size(); ++l) {
            edgeCopy.add(this.edges.get(l));
        }
        for (int l = 0; l < resultCopy3.size(); ++l) {
            final Path path = resultCopy3.get(l);
            resultCopy2.add(path);
            for (int m = 0; m < path.getEdgeList().size(); ++m) {
                for (int z = 0; z < edgeCopy.size(); ++z) {
                    if (edgeCopy.get(z).equals(path.getEdgeList().get(m))) {
                        final boolean sign = edgeCopy.remove(edgeCopy.get(z));
                    }
                }
            }
            if (edgeCopy.size() == 0) {
                break;
            }
        }
        return resultCopy2;
    }
    
    public List<Path> findNodeCoverage() throws InvalidGraphException {
        final List<Path> result = this.findTestPath();
        final List<Path> resultCopy = new ArrayList<Path>();
        for (int i = 0; i < result.size(); ++i) {
            resultCopy.add(result.get(i));
        }
        for (int i = 0; i < result.size(); ++i) {
            for (int j = 0; j < resultCopy.size(); ++j) {
                final Path p1 = result.get(i);
                final Path p2 = resultCopy.get(j);
                if (p1 != p2 && p1.detour(p2)) {
                    resultCopy.remove(j);
                }
            }
        }
        for (int i = 0; i < resultCopy.size(); ++i) {
            final Path p3 = resultCopy.get(i);
            for (int k = 0; k < p3.size() - 1; ++k) {
                if (p3.get(k).equals(p3.get(k + 1))) {
                    p3.remove(k);
                }
            }
        }
        final List<Path> resultCopy2 = new ArrayList<Path>();
        for (int l = 0; l < resultCopy.size(); ++l) {
            resultCopy2.add(resultCopy.get(l));
        }
        for (int l = 0; l < resultCopy.size(); ++l) {
            for (int k = 0; k < resultCopy2.size(); ++k) {
                final Path p4 = resultCopy.get(l);
                final Path p5 = resultCopy2.get(k);
                if (p4 != p5 && p4.detour(p5)) {
                    resultCopy2.remove(k);
                }
            }
        }
        final List<Path> resultCopy3 = new ArrayList<Path>();
        final List<Path> resultCopy4 = new ArrayList<Path>();
        for (int m = 0; m < resultCopy2.size(); ++m) {
            resultCopy4.add(resultCopy2.get(m));
        }
        final List<Node> nodeCopy = new ArrayList<Node>();
        for (int i2 = 0; i2 < this.nodes.size(); ++i2) {
            nodeCopy.add(this.nodes.get(i2));
        }
        for (int i2 = 0; i2 < resultCopy4.size(); ++i2) {
            final Path path = resultCopy4.get(i2);
            resultCopy3.add(path);
            for (int j2 = 0; j2 < path.size(); ++j2) {
                for (int z = 0; z < nodeCopy.size(); ++z) {
                    final int index = nodeCopy.indexOf(path.get(j2));
                    if (index >= 0) {
                        nodeCopy.remove(index);
                    }
                }
            }
            if (nodeCopy.size() == 0) {
                break;
            }
        }
        return resultCopy3;
    }
    
    @Override
    public void validate() throws InvalidGraphException {
        if (this.starts == null || this.starts.size() == 0) {
            throw new InvalidGraphException("No initial nodes.");
        }
        if (this.ends == null || this.ends.size() == 0) {
            throw new InvalidGraphException("No ending nodes.");
        }
        final List<Node> linkedNodes = new ArrayList<Node>();
        final List<Node> nodesCopy = new ArrayList<Node>();
        for (int i = 0; i < this.nodes.size(); ++i) {
            nodesCopy.add(this.nodes.get(i));
        }
        for (int i = 0; i < this.starts.size(); ++i) {
            linkedNodes.add(this.starts.get(i));
        }
        for (int i = this.starts.size() - 1; i >= 0; --i) {
            final int index = this.nodes.indexOf(this.starts.get(i));
            if (index >= 0 && index < nodesCopy.size()) {
                nodesCopy.remove(index);
            }
        }
        for (int i = 0; i < linkedNodes.size(); ++i) {
            final Iterator<Edge> outEdge = linkedNodes.get(i).getOutGoingIterator();
            while (outEdge.hasNext()) {
                final Node des = outEdge.next().getDest();
                if (nodesCopy.contains(des)) {
                    nodesCopy.remove(des);
                    linkedNodes.add(des);
                }
            }
        }
        if (nodesCopy.size() != 0 && nodesCopy.size() != 1) {
            String nodeStr = "";
            for (final Node node : nodesCopy) {
                nodeStr = nodeStr + " " + node.toString();
            }
            throw new InvalidGraphException("The Nodes: " + nodeStr + " are not connected.");
        }
        if (nodesCopy.size() == 1) {
            String nodeStr = "";
            for (final Node node : nodesCopy) {
                nodeStr = nodeStr + " " + node.toString();
            }
            throw new InvalidGraphException("The Node: " + nodeStr + " is not connected.");
        }
    }
    
    public List<Path> findSimplePaths() {
        int lastStart = 0;
        final List<Path> simplePaths = new ArrayList<Path>();
        for (int i = 0; i < this.edges.size(); ++i) {
            final Edge e = this.edges.get(i);
            final Path p = new Path(e);
            simplePaths.add(p);
        }
        boolean newPaths = true;
        while (newPaths) {
            newPaths = false;
            final int curSize = simplePaths.size();
            for (int j = lastStart; j < curSize; ++j) {
                final Path p = simplePaths.get(j);
                if (!p.isCycle()) {
                    for (int k = 0; k < this.edges.size(); ++k) {
                        final Edge e = this.edges.get(k);
                        if (e.getSrc().equals(p.get(p.size() - 1)) && (p.indexOf(e.getDest()) == -1 || p.indexOf(e.getDest()) == 0)) {
                            newPaths = true;
                            final Path pnew = (Path)p.clone();
                            pnew.extendPath(e.getDest());
                            simplePaths.add(pnew);
                        }
                    }
                }
            }
            lastStart = curSize;
        }
        return simplePaths;
    }
    
    public List<Path> findNodes() {
        final List<Path> nodesPath = new ArrayList<Path>();
        for (int i = 0; i < this.nodes.size(); ++i) {
            final Path p = new Path(this.nodes.get(i));
            nodesPath.add(p);
        }
        return nodesPath;
    }
    
    public List<Path> findEdges() {
        final List<Path> edgesPath = new ArrayList<Path>();
        for (int i = 0; i < this.edges.size(); ++i) {
            final Path p = new Path(this.edges.get(i));
            edgesPath.add(p);
        }
        return edgesPath;
    }
    
    public List<Path> findEdgePairs() {
        final long start = System.nanoTime();
        final List<Path> edgesPath = new ArrayList<Path>();
        for (int i = 0; i < this.edges.size(); ++i) {
            if (this.edges.get(i).dest.sizeOfOutEdges() != 0) {
                final Iterator<Edge> ie = this.edges.get(i).dest.getOutGoingIterator();
                while (ie.hasNext()) {
                    final Path p = new Path(this.edges.get(i));
                    final Path p2 = new Path(ie.next().dest);
                    p.extendPath(p2);
                    edgesPath.add(p);
                }
            }
            else if (this.edges.get(i).dest.sizeOfOutEdges() == 0 && this.starts.indexOf(this.edges.get(i).src) != -1) {
                final Path p3 = new Path(this.edges.get(i));
                edgesPath.add(p3);
            }
        }
        return edgesPath;
    }
    
    public List<Path> findPrimePaths() {
        final List<Path> simplePaths = this.findSimplePaths();
        if (simplePaths.size() > 0) {
            this.quickSort(simplePaths, 1, simplePaths.size());
            final List<Path> primePaths = new ArrayList<Path>();
            primePaths.add(simplePaths.get(simplePaths.size() - 1));
            for (int i = simplePaths.size() - 2; i > -1; --i) {
                boolean isSubpath = false;
                for (int j = 0; j < primePaths.size(); ++j) {
                    if (simplePaths.get(i).isSubpath(primePaths.get(j))) {
                        isSubpath = true;
                        break;
                    }
                }
                if (!isSubpath) {
                    primePaths.add(simplePaths.get(i));
                }
            }
            return primePaths;
        }
        return simplePaths;
    }
    
    public List<Path> findPrimePaths1(final String infeasiblePrimePaths) {
        final List<Path> simplePaths = this.findSimplePaths();
        this.quickSort(simplePaths, 1, simplePaths.size());
        final List<Path> primePaths = new ArrayList<Path>();
        primePaths.add(simplePaths.get(simplePaths.size() - 1));
        for (int i = simplePaths.size() - 2; i > -1; --i) {
            boolean isSubpath = false;
            for (int j = 0; j < primePaths.size(); ++j) {
                if (simplePaths.get(i).isSubpath(primePaths.get(j))) {
                    isSubpath = true;
                    break;
                }
            }
            if (!isSubpath) {
                primePaths.add(simplePaths.get(i));
            }
        }
        final List<Path> primePathsCopy = new ArrayList<Path>();
        for (int k = 0; k < primePaths.size(); ++k) {
            primePathsCopy.add(primePaths.get(k));
        }
        if (!infeasiblePrimePaths.equals("") && !infeasiblePrimePaths.equals(" ") && !infeasiblePrimePaths.equals(null)) {
            this.infeasiblePrimePathsString = infeasiblePrimePaths.trim().split(",");
            for (int k = primePaths.size() - 1; k >= 0; --k) {
                for (int j = 0; j < this.infeasiblePrimePathsString.length; ++j) {
                    final Integer tempInt = new Integer(this.infeasiblePrimePathsString[j]) - 1;
                    if (tempInt == k) {
                        final Path tempPath = primePaths.get(k);
                        primePaths.remove(k);
                        primePathsCopy.remove(k);
                        for (int z = 0; z < tempPath.size(); ++z) {
                            for (int l = 0; l < primePathsCopy.size(); ++l) {
                                Path firstPartTempPath = null;
                                Path secondPartTempPath = null;
                                final Path tempPrimePath = primePathsCopy.get(l);
                                if (tempPath.get(z).equals(tempPrimePath.get(0)) && tempPath.get(z).equals(tempPrimePath.getEnd()) && !tempPath.equals(tempPrimePath)) {
                                    if (z == 0) {
                                        secondPartTempPath = tempPath.subPath(z + 1, tempPath.size());
                                        firstPartTempPath = tempPath.subPath(0, z);
                                        firstPartTempPath.extendPath(tempPrimePath.subPath(1, tempPrimePath.size()));
                                        firstPartTempPath.extendPath(secondPartTempPath);
                                    }
                                    else if (z > 0) {
                                        secondPartTempPath = tempPath.subPath(z, tempPath.size());
                                        firstPartTempPath = tempPath.subPath(0, z);
                                        firstPartTempPath.extendPath(tempPrimePath.subPath(0, tempPrimePath.size() - 1));
                                        firstPartTempPath.extendPath(secondPartTempPath);
                                    }
                                    if (firstPartTempPath.size() < 100) {
                                        primePaths.add(firstPartTempPath);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return primePaths;
    }
    
    public List<Path> findPrimePathsWithSidetrips(final String infeasiblePrimePaths) {
        final List<Path> simplePaths = this.findSimplePaths();
        this.quickSort(simplePaths, 1, simplePaths.size());
        final List<Path> primePaths = new ArrayList<Path>();
        primePaths.add(simplePaths.get(simplePaths.size() - 1));
        for (int i = simplePaths.size() - 2; i > -1; --i) {
            boolean isSubpath = false;
            for (int j = 0; j < primePaths.size(); ++j) {
                if (simplePaths.get(i).isSubpath(primePaths.get(j))) {
                    isSubpath = true;
                    break;
                }
            }
            if (!isSubpath) {
                primePaths.add(simplePaths.get(i));
            }
        }
        final List<Path> primePathsCopy = new ArrayList<Path>(primePaths.size());
        if (!infeasiblePrimePaths.equals("") && !infeasiblePrimePaths.equals(" ") && !infeasiblePrimePaths.equals(null)) {
            this.infeasiblePrimePathsString = infeasiblePrimePaths.trim().split(",");
            for (int k = primePaths.size() - 1; k >= 0; --k) {
                for (int j = 0; j < this.infeasiblePrimePathsString.length; ++j) {
                    final Integer tempInt = new Integer(this.infeasiblePrimePathsString[j]) - 1;
                    if (tempInt == k) {
                        final Path tempPath = primePaths.get(k);
                        primePaths.remove(k);
                        for (int z = 0; z < tempPath.size(); ++z) {
                            Path firstPartTempPath = null;
                            Path secondPartTempPath = null;
                            for (int l = 0; l < primePaths.size(); ++l) {
                                final Path tempPrimePath = primePaths.get(l);
                                if (tempPath.get(z).equals(tempPrimePath.get(0)) && tempPath.get(z).equals(tempPrimePath.getEnd()) && !tempPath.equals(tempPrimePath)) {
                                    secondPartTempPath = tempPath.subPath(z, tempPath.size());
                                    if (z == 0) {
                                        firstPartTempPath = tempPath.subPath(0, z);
                                        firstPartTempPath.extendPath(tempPrimePath.subPath(1, tempPrimePath.size()));
                                        firstPartTempPath.extendPath(secondPartTempPath);
                                    }
                                    else if (z > 0) {
                                        firstPartTempPath = tempPath.subPath(0, z);
                                        firstPartTempPath.extendPath(tempPrimePath.subPath(0, tempPrimePath.size() - 1));
                                        firstPartTempPath.extendPath(secondPartTempPath);
                                    }
                                    if (firstPartTempPath != null) {
                                        primePathsCopy.add(k, tempPath);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (infeasiblePrimePaths.equals("") || infeasiblePrimePaths.equals(" ")) {
            for (int k = 0; k < primePaths.size(); ++k) {
                final Path tempPath2 = primePaths.get(k);
                for (int z2 = 0; z2 < tempPath2.size(); ++z2) {
                    Path firstPartTempPath2 = null;
                    Path secondPartTempPath2 = null;
                    for (int m = 0; m < primePaths.size(); ++m) {
                        final Path tempPrimePath2 = primePaths.get(m);
                        if (tempPath2.get(z2).equals(tempPrimePath2.get(0)) && tempPath2.get(z2).equals(tempPrimePath2.getEnd()) && !tempPath2.equals(tempPrimePath2)) {
                            secondPartTempPath2 = tempPath2.subPath(z2, tempPath2.size());
                            if (z2 == 0) {
                                firstPartTempPath2 = tempPath2.subPath(0, z2);
                                firstPartTempPath2.extendPath(tempPrimePath2.subPath(1, tempPrimePath2.size()));
                                firstPartTempPath2.extendPath(secondPartTempPath2);
                            }
                            else if (z2 > 0) {
                                firstPartTempPath2 = tempPath2.subPath(0, z2);
                                firstPartTempPath2.extendPath(tempPrimePath2.subPath(0, tempPrimePath2.size() - 1));
                                firstPartTempPath2.extendPath(secondPartTempPath2);
                            }
                            primePathsCopy.add(k, firstPartTempPath2);
                        }
                    }
                }
            }
        }
        return primePathsCopy;
    }
    
    public List<Path> findEdgePairs(final String infeasibleEdgePairs) {
        final List<Path> edgesPath = new ArrayList<Path>();
        for (int i = 0; i < this.edges.size(); ++i) {
            if (this.edges.get(i).dest.sizeOfOutEdges() != 0) {
                final Iterator<Edge> ie = this.edges.get(i).dest.getOutGoingIterator();
                while (ie.hasNext()) {
                    final Path p = new Path(this.edges.get(i));
                    final Path p2 = new Path(ie.next().dest);
                    p.extendPath(p2);
                    edgesPath.add(p);
                }
            }
            else if (this.edges.get(i).dest.sizeOfOutEdges() == 0 && this.starts.indexOf(this.edges.get(i).src) != -1) {
                final Path p3 = new Path(this.edges.get(i));
                edgesPath.add(p3);
            }
        }
        final List<Path> edgePairsCopy = new ArrayList<Path>();
        for (int j = 0; j < edgesPath.size(); ++j) {
            edgePairsCopy.add(edgesPath.get(j));
        }
        if (!infeasibleEdgePairs.equals("") && !infeasibleEdgePairs.equals(" ") && !infeasibleEdgePairs.equals(null)) {
            this.infeasibleEdgePairsString = infeasibleEdgePairs.trim().split(",");
            for (int j = edgesPath.size() - 1; j >= 0; --j) {
                for (int k = 0; k < this.infeasibleEdgePairsString.length; ++k) {
                    final Integer tempInt = new Integer(this.infeasibleEdgePairsString[k]) - 1;
                    if (tempInt == j) {
                        final Path tempPath = edgesPath.get(j);
                        edgesPath.remove(j);
                        edgePairsCopy.remove(j);
                        for (int z = 0; z < tempPath.size(); ++z) {
                            for (int l = 0; l < edgePairsCopy.size(); ++l) {
                                Path firstPartTempPath = null;
                                Path secondPartTempPath = null;
                                final Path tempPrimePath = edgePairsCopy.get(l);
                                if (tempPath.get(z).equals(tempPrimePath.get(0)) && tempPath.get(z).equals(tempPrimePath.getEnd()) && !tempPath.equals(tempPrimePath)) {
                                    if (z == 0) {
                                        secondPartTempPath = tempPath.subPath(z + 1, tempPath.size());
                                        firstPartTempPath = tempPath.subPath(0, z);
                                        firstPartTempPath.extendPath(tempPrimePath.subPath(1, tempPrimePath.size()));
                                        firstPartTempPath.extendPath(secondPartTempPath);
                                    }
                                    else if (z > 0) {
                                        secondPartTempPath = tempPath.subPath(z, tempPath.size());
                                        firstPartTempPath = tempPath.subPath(0, z);
                                        firstPartTempPath.extendPath(tempPrimePath.subPath(0, tempPrimePath.size() - 1));
                                        firstPartTempPath.extendPath(secondPartTempPath);
                                    }
                                    if (firstPartTempPath.size() < 100) {
                                        edgesPath.add(firstPartTempPath);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return edgesPath;
    }
    
    public List<Path> findMinimumPrimePathCoverageViaSetCover(final List<Path> listOfPaths) {
        List<Path> primePathsList = new ArrayList<Path>();
        primePathsList = listOfPaths;
        final List<Path> overlappingPaths = new ArrayList<Path>();
        final Path minimumPath = new Path();
        final List<Path> finalSet = new ArrayList<Path>();
        final long start1 = System.nanoTime();
        for (final Path pathI : primePathsList) {
            for (final Path pathJ : primePathsList) {
                if (!pathI.equals(pathJ)) {
                    int index = 0;
                    while (index != -1) {
                        index = pathI.nextIndexOf(pathJ.get(0), index);
                        if (index != -1) {
                            boolean signal = true;
                            for (int i = index; i < pathI.size(); ++i) {
                                if (i - index >= pathJ.size()) {
                                    signal = false;
                                    break;
                                }
                                if (!pathI.get(i).equals(pathJ.get(i - index))) {
                                    signal = false;
                                    break;
                                }
                            }
                            if (!signal) {
                                continue;
                            }
                            Path tempPath = pathI.subPath(0, index);
                            tempPath = tempPath.immutableExtendedPath(pathJ);
                            overlappingPaths.add(tempPath);
                        }
                    }
                }
            }
        }
        final long end1 = System.nanoTime();
        final long duration1 = end1 - start1;
        final long start2 = System.nanoTime();
        for (final Path path : primePathsList) {
            finalSet.add(path);
        }
        for (final Path path : overlappingPaths) {
            finalSet.add(path);
        }
        int numberOfSetsSelected = 0;
        final int numberOfSets = finalSet.size();
        final List<Integer> numberOfSubs = new ArrayList<Integer>();
        for (int j = 0; j < finalSet.size(); ++j) {
            numberOfSubs.add(new Integer(0));
        }
        while (primePathsList.size() > 0) {
            double ratio = 100.0;
            int index2 = 0;
            for (int k = 0; k < finalSet.size(); ++k) {
                Integer num = 0;
                for (final Path primePath : primePathsList) {
                    if (finalSet.get(k).indexOf(primePath) != -1) {
                        ++num;
                    }
                }
                numberOfSubs.set(k, num);
                if (numberOfSubs.get(k) != 0 && finalSet.get(k).size() / numberOfSubs.get(k) < ratio) {
                    ratio = finalSet.get(k).size() / (double)numberOfSubs.get(k);
                    index2 = k;
                }
            }
            for (int k = 0; k < primePathsList.size(); ++k) {
                final int index3 = finalSet.get(index2).indexOf(primePathsList.get(k));
                if (index3 != -1) {
                    primePathsList.remove(k);
                    --k;
                }
            }
            minimumPath.extendPath(finalSet.get(index2));
            ++numberOfSetsSelected;
            finalSet.remove(index2);
            numberOfSubs.remove(index2);
        }
        final long end2 = System.nanoTime();
        final long duration2 = end2 - start2;
        overlappingPaths.removeAll(overlappingPaths);
        overlappingPaths.add(minimumPath);
        return overlappingPaths;
    }
    
    public List<Path> findMinimumPrimePathCoverageViaPrefixGraph(final List<Path> listOfPaths) {
        final List<Path> minimumPaths = new ArrayList<Path>();
        final Iterator<Edge> edgesIterator = this.edges.iterator();
        final List<Path> primePaths = listOfPaths;
        final List<Node> leftSide = new ArrayList<Node>();
        final List<Node> rightSide = new ArrayList<Node>();
        final Path minimumPath = new Path();
        while (edgesIterator.hasNext()) {
            final Edge edge = edgesIterator.next();
            boolean signForLeft = true;
            boolean signForRight = true;
            for (final Node node : leftSide) {
                if (edge.getSrc().equals(node)) {
                    signForLeft = false;
                }
            }
            if (signForLeft) {
                leftSide.add(edge.getSrc());
            }
            for (final Node node : rightSide) {
                if (edge.getDest().equals(node)) {
                    signForRight = false;
                }
            }
            if (signForRight) {
                rightSide.add(edge.getDest());
            }
        }
        final List<Edge> perfectMatching = new ArrayList<Edge>();
        Iterator<Edge> edgesLeft = null;
        for (int i = 0; i < leftSide.size(); ++i) {
            final Node node2 = leftSide.get(i);
            edgesLeft = node2.getOutGoingIterator();
            final int size = node2.sizeOfOutEdges();
            int counter = 0;
        Label_0633:
            while (edgesLeft.hasNext()) {
                final Edge edge2 = edgesLeft.next();
                ++counter;
                boolean sign = false;
                for (int j = 0; j < perfectMatching.size(); ++j) {
                    if (perfectMatching.get(j).getDest().equals(edge2.getDest())) {
                        sign = true;
                        break;
                    }
                }
                if (!sign) {
                    perfectMatching.add(edge2);
                    break;
                }
                if (size != counter) {
                    continue;
                }
                edgesLeft = node2.getOutGoingIterator();
                while (edgesLeft.hasNext()) {
                    if (i == perfectMatching.size() - 1) {
                        break;
                    }
                    final Edge edge3 = edgesLeft.next();
                    final Node nodeDest = edge3.getDest();
                    Node nodeSrc = null;
                    int position = 0;
                    for (int x = 0; x < perfectMatching.size(); ++x) {
                        if (perfectMatching.get(x).getDest().equals(nodeDest)) {
                            nodeSrc = perfectMatching.get(x).getSrc();
                            position = x;
                            break;
                        }
                    }
                    if (nodeSrc == null) {
                        continue;
                    }
                    final Iterator<Edge> edges = nodeSrc.getOutGoingIterator();
                    while (edges.hasNext()) {
                        final Edge edge4 = edges.next();
                        boolean sign2 = false;
                        for (int y = 0; y < perfectMatching.size(); ++y) {
                            if (edge4.getDest().equals(perfectMatching.get(y).getDest())) {
                                sign2 = true;
                                break;
                            }
                        }
                        if (!sign2) {
                            perfectMatching.set(position, edge4);
                            perfectMatching.add(edge3);
                            continue Label_0633;
                        }
                    }
                }
            }
        }
        List<Path> paths = null;
        Path path = new Path();
        paths = new ArrayList<Path>();
        boolean signForMatching = false;
        path.extendPath(perfectMatching.get(0).getSrc());
        path.extendPath(perfectMatching.get(0).getDest());
        perfectMatching.remove(0);
        while (perfectMatching.size() > 0) {
            if (path.size() == 0) {
                path.extendPath(perfectMatching.get(0).getSrc());
                path.extendPath(perfectMatching.get(0).getDest());
                perfectMatching.remove(0);
            }
            signForMatching = false;
            for (int y2 = 0; y2 < perfectMatching.size(); ++y2) {
                if (path.get(0).equals(perfectMatching.get(y2).getDest())) {
                    final Path p = new Path();
                    p.extendPath(perfectMatching.get(y2).getSrc());
                    p.extendPath(path);
                    path = new Path();
                    path = p;
                    perfectMatching.remove(y2);
                    if (perfectMatching.size() == 0) {
                        paths.add(path);
                        path = new Path();
                    }
                    --y2;
                    signForMatching = true;
                    break;
                }
                if (path.getEnd().equals(perfectMatching.get(y2).getSrc())) {
                    path.extendPath(perfectMatching.get(y2).getDest());
                    perfectMatching.remove(y2);
                    if (perfectMatching.size() == 0) {
                        paths.add(path);
                        path = new Path();
                    }
                    --y2;
                    signForMatching = true;
                    break;
                }
            }
            if (!signForMatching) {
                paths.add(path);
                path = new Path();
            }
        }
        int tempValue = 0;
        for (final Path path2 : paths) {
            path = new Path();
            final List<Node> nodes = path2.path;
            for (int m = 0; m < nodes.size() - 1; ++m) {
                final Node nSrc = nodes.get(m);
                final Node nDest = nodes.get(m + 1);
                final Edge e = new Edge(nSrc, nDest);
                boolean signForEdge = false;
                for (int l = 0; l < this.edges.size(); ++l) {
                    if (e.equals(this.edges.get(l))) {
                        tempValue += ((Path)this.edges.get(l).getWeight()).size();
                        path.extendPath((Path)this.edges.get(l).getWeight());
                        signForEdge = true;
                    }
                }
                if (m == nodes.size() - 2) {
                    tempValue += ((Path)nodes.get(m + 1).getObject()).size();
                    path.extendPath((Path)nodes.get(m + 1).getObject());
                }
            }
            minimumPath.extendPath(path);
        }
        for (final Path path2 : primePaths) {
            if (minimumPath.indexOf(path2) == -1) {
                minimumPath.extendPath(path2);
            }
        }
        minimumPaths.add(minimumPath);
        return minimumPaths;
    }
    
    public List<Path> findMinimumPrimePathCoverageViaPrefixGraphOptimized(final List<Path> listOfPaths) throws InvalidGraphException {
        final List<Path> minimumPaths = new ArrayList<Path>();
        final Iterator<Edge> edgesIterator = this.edges.iterator();
        final List<Path> primePaths = listOfPaths;
        final List<Path> finalPathsSet = new ArrayList<Path>();
        final List<Node> leftSide = new ArrayList<Node>();
        final List<Node> rightSide = new ArrayList<Node>();
        Path minimumPath = new Path();
        while (edgesIterator.hasNext()) {
            final Edge edge = edgesIterator.next();
            boolean signForLeft = true;
            boolean signForRight = true;
            for (final Node node : leftSide) {
                if (edge.getSrc().equals(node)) {
                    signForLeft = false;
                }
            }
            if (signForLeft) {
                leftSide.add(edge.getSrc());
            }
            for (final Node node : rightSide) {
                if (edge.getDest().equals(node)) {
                    signForRight = false;
                }
            }
            if (signForRight) {
                rightSide.add(edge.getDest());
            }
        }
        final long start1 = System.nanoTime();
        final List<Edge> perfectMatching = new ArrayList<Edge>();
        int counterOne = 0;
        int counterTwo = 0;
        Iterator<Edge> edgesLeft = null;
        for (int i = 0; i < leftSide.size() && (perfectMatching.size() < rightSide.size() || rightSide.size() >= leftSide.size()); ++i) {
            final Node node2 = leftSide.get(i);
            edgesLeft = node2.getOutGoingIterator();
            final int size = node2.sizeOfOutEdges();
            int counter = 0;
            while (edgesLeft.hasNext()) {
                final Edge edge2 = edgesLeft.next();
                ++counter;
                boolean sign = false;
                for (int j = 0; j < perfectMatching.size(); ++j) {
                    if (perfectMatching.get(j).getDest().equals(edge2.getDest())) {
                        sign = true;
                        break;
                    }
                }
                if (!sign) {
                    perfectMatching.add(edge2);
                    break;
                }
                if (size != counter) {
                    continue;
                }
                edgesLeft = node2.getOutGoingIterator();
                boolean signForSwitch = false;
            Label_0702:
                while (edgesLeft.hasNext()) {
                    if (i == perfectMatching.size() - 1) {
                        break;
                    }
                    final Edge edge3 = edgesLeft.next();
                    final Node nodeDest = edge3.getDest();
                    Node nodeSrc = null;
                    int position = 0;
                    ++counterOne;
                    for (int x = 0; x < perfectMatching.size(); ++x) {
                        if (perfectMatching.get(x).getDest().equals(nodeDest)) {
                            nodeSrc = perfectMatching.get(x).getSrc();
                            position = x;
                            break;
                        }
                    }
                    if (nodeSrc == null) {
                        continue;
                    }
                    final Iterator<Edge> edges = nodeSrc.getOutGoingIterator();
                    while (edges.hasNext()) {
                        final Edge edge4 = edges.next();
                        ++counterTwo;
                        boolean sign2 = false;
                        for (int y = 0; y < perfectMatching.size(); ++y) {
                            if (edge4.getDest().equals(perfectMatching.get(y).getDest())) {
                                sign2 = true;
                                break;
                            }
                        }
                        if (!sign2) {
                            perfectMatching.set(position, edge4);
                            perfectMatching.add(edge3);
                            signForSwitch = true;
                            break Label_0702;
                        }
                    }
                }
                if (!signForSwitch) {
                    break;
                }
            }
        }
        final int sizeOfPerfectMatching = perfectMatching.size();
        final List<Path> paths = new ArrayList<Path>();
        Path path = new Path();
        boolean signForMatching = false;
        if (perfectMatching.size() >= 1) {
            path.extendPath(perfectMatching.get(0).getSrc());
            path.extendPath(perfectMatching.get(0).getDest());
            perfectMatching.remove(0);
        }
        while (perfectMatching.size() > 0) {
            if (path.size() == 0) {
                path.extendPath(perfectMatching.get(0).getSrc());
                path.extendPath(perfectMatching.get(0).getDest());
                perfectMatching.remove(0);
            }
            signForMatching = false;
            for (int y2 = 0; y2 < perfectMatching.size(); ++y2) {
                if (path.get(0).equals(perfectMatching.get(y2).getDest())) {
                    final Path p = new Path();
                    p.extendPath(perfectMatching.get(y2).getSrc());
                    p.extendPath(path);
                    path = new Path();
                    path = p;
                    perfectMatching.remove(y2);
                    if (perfectMatching.size() == 0) {
                        paths.add(path);
                        path = new Path();
                    }
                    --y2;
                    signForMatching = true;
                    break;
                }
                if (path.getEnd().equals(perfectMatching.get(y2).getSrc())) {
                    path.extendPath(perfectMatching.get(y2).getDest());
                    perfectMatching.remove(y2);
                    if (perfectMatching.size() == 0) {
                        paths.add(path);
                        path = new Path();
                    }
                    --y2;
                    signForMatching = true;
                    break;
                }
            }
            if (!signForMatching) {
                paths.add(path);
                path = new Path();
            }
        }
        for (final Path path2 : paths) {
            path = new Path();
            final List<Node> nodes = path2.path;
            boolean sign3 = false;
            if (nodes.get(0).equals(nodes.get(nodes.size() - 1))) {
                sign3 = true;
            }
            for (int size2 = nodes.size(), m = 0; m < size2 - 1; ++m) {
                final Node nSrc = nodes.get(m);
                final Node nDest = nodes.get(m + 1);
                final Edge e = new Edge(nSrc, nDest);
                for (int l = 0; l < this.edges.size(); ++l) {
                    if (e.equals(this.edges.get(l))) {
                        path.extendPath((Path)this.edges.get(l).getWeight());
                    }
                }
                if (m == size2 - 2 && !sign3) {
                    path.extendPath((Path)nodes.get(m + 1).getObject());
                }
                if (m == size2 - 3 && sign3) {
                    path.extendPath((Path)nodes.get(m + 1).getObject());
                    break;
                }
            }
            minimumPath.extendPath(path);
            finalPathsSet.add(path);
        }
        final long end1 = System.nanoTime();
        final long duration1 = end1 - start1;
        final long start2 = System.nanoTime();
        minimumPath = new Path();
        for (final Path tempPath : primePaths) {
            finalPathsSet.add(tempPath);
        }
        final int numberOfFinalSets = finalPathsSet.size();
        int numberOfSetsSelected = 0;
        final List<Integer> numberOfSubs = new ArrayList<Integer>();
        for (int k = 0; k < finalPathsSet.size(); ++k) {
            numberOfSubs.add(new Integer(0));
        }
        while (primePaths.size() > 0) {
            double ratio = 100.0;
            int index = 0;
            for (int i2 = 0; i2 < finalPathsSet.size(); ++i2) {
                Integer num = 0;
                for (final Path primePath : primePaths) {
                    if (finalPathsSet.get(i2).indexOf(primePath) != -1) {
                        ++num;
                    }
                }
                numberOfSubs.set(i2, num);
                if (numberOfSubs.get(i2) != 0 && finalPathsSet.get(i2).size() / numberOfSubs.get(i2) < ratio) {
                    ratio = finalPathsSet.get(i2).size() / (double)numberOfSubs.get(i2);
                    index = i2;
                }
            }
            for (int i2 = 0; i2 < primePaths.size(); ++i2) {
                final int index2 = finalPathsSet.get(index).indexOf(primePaths.get(i2));
                if (index2 != -1) {
                    primePaths.remove(i2);
                    --i2;
                }
            }
            minimumPath.extendPath(finalPathsSet.get(index));
            ++numberOfSetsSelected;
            finalPathsSet.remove(index);
            numberOfSubs.remove(index);
        }
        final long end2 = System.nanoTime();
        final long duration2 = end2 - start2;
        minimumPaths.add(minimumPath);
        return minimumPaths;
    }
    
    public List<Path> splittedPathsFromSuperString(final Path superString, final List<Path> testPaths) {
        final long start = System.nanoTime();
        final List<Path> paths = new ArrayList<Path>();
        Path testPath = new Path(superString.get(0));
        for (int i = 1; i < superString.size(); ++i) {
            final Node node = superString.get(i);
            if (this.isInitialNode(node) && !this.isEdge(superString.get(i - 1), node)) {
                testPath = new Path(node);
            }
            else if (testPath == null) {
                testPath = new Path(node);
            }
            else {
                testPath.extendPath(node);
            }
            if (i == superString.size() - 1) {
                if (!this.isInitialNode(testPath.get(0))) {
                    int indexInt = 32255;
                    int index = 0;
                    for (int j = 0; j < testPaths.size(); ++j) {
                        if (testPaths.get(j).indexOf(testPath.get(0)) < indexInt && testPaths.get(j).indexOf(testPath.get(0)) >= 0) {
                            indexInt = testPaths.get(j).indexOf(testPath.get(0));
                            index = j;
                        }
                    }
                    final Path thePath = new Path(testPaths.get(index).get(0));
                    for (int k = 1; k < indexInt; ++k) {
                        thePath.extendPath(testPaths.get(index).get(k));
                    }
                    for (int k = 0; k < testPath.size(); ++k) {
                        thePath.extendPath(testPath.get(k));
                    }
                    testPath = new Path();
                    for (int k = 0; k < thePath.size(); ++k) {
                        testPath.extendPath(thePath.get(k));
                    }
                }
                if (!this.isEndingNode(testPath.getEnd())) {
                    int indexInt = 0;
                    int index = 0;
                    for (int j = 0; j < testPaths.size(); ++j) {
                        if (testPaths.get(j).lastIndexOf(testPath.getEnd()) > indexInt) {
                            indexInt = testPaths.get(j).lastIndexOf(testPath.getEnd());
                            index = j;
                        }
                    }
                    for (int j = indexInt + 1; j < testPaths.get(index).size(); ++j) {
                        testPath.extendPath(testPaths.get(index).get(j));
                    }
                }
                paths.add(testPath);
            }
            else if (!this.isEdge(node, superString.get(i + 1))) {
                if (!this.isInitialNode(testPath.get(0))) {
                    int indexInt = 32255;
                    int index = 0;
                    for (int j = 0; j < testPaths.size(); ++j) {
                        if (testPaths.get(j).indexOf(testPath.get(0)) < indexInt && testPaths.get(j).indexOf(testPath.get(0)) >= 0) {
                            indexInt = testPaths.get(j).indexOf(testPath.get(0));
                            index = j;
                        }
                    }
                    final Path thePath = new Path(testPaths.get(index).get(0));
                    for (int k = 1; k < indexInt; ++k) {
                        thePath.extendPath(testPaths.get(index).get(k));
                    }
                    for (int k = 0; k < testPath.size(); ++k) {
                        thePath.extendPath(testPath.get(k));
                    }
                    testPath = new Path();
                    for (int k = 0; k < thePath.size(); ++k) {
                        testPath.extendPath(thePath.get(k));
                    }
                }
                if (!this.isEndingNode(testPath.getEnd())) {
                    int indexInt = 0;
                    int index = 0;
                    for (int j = 0; j < testPaths.size(); ++j) {
                        if (testPaths.get(j).lastIndexOf(testPath.getEnd()) > indexInt) {
                            indexInt = testPaths.get(j).lastIndexOf(testPath.getEnd());
                            index = j;
                        }
                    }
                    final Path thePath = new Path(testPaths.get(index).get(indexInt + 1));
                    for (int k = indexInt + 2; k < testPaths.get(index).size(); ++k) {
                        thePath.extendPath(testPaths.get(index).get(k));
                    }
                    for (int k = 0; k < thePath.size(); ++k) {
                        testPath.extendPath(thePath.get(k));
                    }
                }
                paths.add(testPath);
                testPath = null;
            }
        }
        for (int i = 0; i < paths.size(); ++i) {
            final Path path = paths.get(i);
            for (int l = i + 1; l < paths.size(); ++l) {
                final Path anotherPath = paths.get(l);
                if (path.equals(anotherPath)) {
                    paths.remove(i);
                    --i;
                }
            }
        }
        final long end = System.nanoTime();
        final long duration = end - start;
        return paths;
    }
    
    public List<Path> fordFulkerson(final Node starting, final Node target) throws InvalidGraphException {
        final List<Path> augmentingPaths = new ArrayList<Path>();
        final Graph residualGraph = new Graph();
        for (final Node node : this.nodes) {
            residualGraph.createNode(node.getObject());
        }
        for (final Edge edge : this.edges) {
            Node leftNode = edge.src;
            Node rightNode = edge.dest;
            for (final Node node2 : residualGraph.nodes) {
                if (!node2.getObject().equals("S") && !node2.getObject().equals("T")) {
                    if (((Path)node2.getObject()).equals(leftNode.getObject())) {
                        leftNode = node2;
                    }
                    if (!((Path)node2.getObject()).equals(rightNode.getObject())) {
                        continue;
                    }
                    rightNode = node2;
                }
            }
            residualGraph.createEdge(leftNode, rightNode, null, edge.getCapacity(), edge.getFlow());
        }
        if (starting != null) {
            residualGraph.starts.add(starting);
        }
        else {
            for (final Node node : this.starts) {
                residualGraph.addInitialNode(residualGraph.createNode(node.getObject()));
            }
        }
        if (target != null) {
            residualGraph.ends.add(target);
        }
        else {
            for (final Node node : this.ends) {
                residualGraph.addEndingNode(residualGraph.createNode(node.getObject()));
            }
        }
        Path augmentingPath = null;
        int numberLeft = 0;
        int numberRight = 0;
        int numberForStop = 0;
        for (int i = 0; i < this.nodes.size(); ++i) {
            if (!this.ends.contains(this.nodes.get(i))) {
                if (this.nodes.get(i).toString().indexOf("L") != -1) {
                    ++numberLeft;
                }
                if (this.nodes.get(i).toString().indexOf("R") != -1) {
                    ++numberRight;
                }
            }
        }
        if (numberLeft < numberRight) {
            numberForStop = numberLeft;
        }
        else {
            numberForStop = numberRight;
        }
        while ((augmentingPath = residualGraph.nextAugmentingPath()) != null || augmentingPaths.size() < numberForStop) {
            augmentingPaths.add(augmentingPath);
            final List<Edge> edgesOfAugmentingPath = new ArrayList<Edge>();
            int j = 0;
            while (j < augmentingPath.size()) {
                final Edge edge2 = residualGraph.findEdge(augmentingPath.get(j).getObject(), augmentingPath.get(++j).getObject());
                edgesOfAugmentingPath.add(edge2);
                if (j == augmentingPath.size() - 1) {
                    break;
                }
            }
            int minimumFlow = 200000;
            for (final Edge edge3 : edgesOfAugmentingPath) {
                if (edge3.getCapacity() < minimumFlow) {
                    minimumFlow = edge3.getCapacity();
                }
            }
            for (final Edge edge3 : this.edges) {
                for (final Edge edge4 : edgesOfAugmentingPath) {
                    if (edge3.equals(edge4) && edge3.getCapacity() >= edge3.getFlow() + minimumFlow) {
                        edge3.setFlow(edge3.getFlow() + minimumFlow);
                    }
                }
            }
            for (final Edge edge3 : edgesOfAugmentingPath) {
                if (minimumFlow < edge3.getCapacity()) {
                    edge3.setCapacity(edge3.getCapacity() - minimumFlow);
                    if (residualGraph.findEdge(edge3.dest.getObject(), edge3.src.getObject()) == null) {
                        residualGraph.addEdge(new Edge(residualGraph.createNode(edge3.dest.getObject()), residualGraph.createNode(edge3.src.getObject()), null, minimumFlow, 0));
                    }
                    else {
                        residualGraph.findEdge(edge3.dest.getObject(), edge3.src.getObject()).setCapacity(residualGraph.findEdge(edge3.dest.getObject(), edge3.src.getObject()).getCapacity() + minimumFlow);
                    }
                }
                else {
                    if (minimumFlow != edge3.getCapacity()) {
                        continue;
                    }
                    residualGraph.edges.remove(edge3);
                    residualGraph.findNode(edge3.src.getObject()).removeOutGoing(edge3);
                    if (residualGraph.findEdge(edge3.dest.getObject(), edge3.src.getObject()) == null) {
                        residualGraph.addEdge(new Edge(residualGraph.createNode(edge3.dest.getObject()), residualGraph.createNode(edge3.src.getObject()), null, minimumFlow, 0));
                    }
                    else {
                        residualGraph.findEdge(edge3.dest.getObject(), edge3.src.getObject()).setCapacity(residualGraph.findEdge(edge3.dest.getObject(), edge3.src.getObject()).getCapacity() + minimumFlow);
                    }
                }
            }
        }
        return augmentingPaths;
    }
    
    public Path nextAugmentingPath() throws InvalidGraphException {
        final List<Path> result = new ArrayList<Path>();
        for (int j = 0; j < this.starts.size(); ++j) {
            final List<Node> nodesCopy = new ArrayList<Node>();
            for (int i = 0; i < this.nodes.size(); ++i) {
                nodesCopy.add(this.nodes.get(i));
            }
            for (int i = this.starts.size() - 1; i >= 0; --i) {
                final int index = this.nodes.indexOf(this.starts.get(i));
                nodesCopy.remove(index);
            }
            final List<Path> paths = new ArrayList<Path>();
            paths.add(new Path(this.starts.get(j)));
            while (paths.size() != 0) {
                for (int k = 0; k < paths.size(); ++k) {
                    final Path path = paths.get(k);
                    final Node end = path.getEnd();
                    final Iterator<Edge> outEdges = end.getOutGoingIterator();
                    int count = 0;
                    while (outEdges.hasNext()) {
                        if (++count == end.sizeOfOutEdges()) {
                            path.extendPath(outEdges.next().getDest());
                        }
                        else {
                            final Path newPath = (Path)path.clone();
                            newPath.extendPath(outEdges.next().getDest());
                            paths.add(k + 1, newPath);
                            ++k;
                        }
                    }
                }
                for (int k = 0; k < paths.size(); ++k) {
                    final Path path = paths.get(k);
                    final Node end = path.getEnd();
                    boolean sign = false;
                    for (final Node node : this.ends) {
                        if (node.equals(end)) {
                            sign = true;
                            break;
                        }
                    }
                    if (end.sizeOfOutEdges() <= 0 && !sign) {
                        paths.remove(k);
                        --k;
                    }
                    else if (sign) {
                        paths.remove(k);
                        --k;
                        result.add(path);
                        return path;
                    }
                }
            }
        }
        return null;
    }
    
    private void quickSort(final List<Path> paths, final int p, final int len) {
        int pivot = -1;
        if (p < len) {
            final Path x = paths.get(p);
            int i = p - 1;
            int j = len;
            while (true) {
                --j;
                while (paths.get(j).size() > x.size()) {
                    --j;
                }
                ++i;
                while (paths.get(i).size() < x.size()) {
                    ++i;
                }
                if (i >= j) {
                    break;
                }
                final Path temp = paths.get(i);
                paths.set(i, paths.get(j));
                paths.set(j, temp);
            }
            pivot = j;
            this.quickSort(paths, p, pivot);
            this.quickSort(paths, pivot + 1, len);
        }
    }
    
    public boolean isEdge(final Node start, final Node end) {
        final Iterator<Edge> ie = start.getOutGoingIterator();
        Edge e = null;
        while (ie.hasNext()) {
            e = ie.next();
            if (e.getDest().equals(end)) {
                return true;
            }
        }
        return false;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import java.util.StringTokenizer;
import openjava.ptree.util.ParseTreeVisitor;
import openjava.ptree.util.SourceCodeWriter;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class ParseTreeObject implements ParseTree, Cloneable
{
    protected static String LN;
    private ParseTreeObject parent;
    private int objectID;
    private static int idCount;
    
    public final ParseTreeObject getParent() {
        return this.parent;
    }
    
    protected final void setParent(final ParseTreeObject parent) {
        this.parent = parent;
    }
    
    @Override
    public final void replace(final ParseTree parseTree) throws ParseTreeException {
        final ParseTreeObject parent = this.getParent();
        if (parent == null) {
            throw new ParseTreeException("no parent");
        }
        parent.replaceChildWith(this, parseTree);
    }
    
    protected abstract void replaceChildWith(final ParseTree p0, final ParseTree p1) throws ParseTreeException;
    
    public ParseTreeObject() {
        this.objectID = -1;
        this.setObjectID();
    }
    
    @Override
    protected final Object clone() {
        try {
            final ParseTreeObject parseTreeObject = (ParseTreeObject)super.clone();
            parseTreeObject.setObjectID();
            return parseTreeObject;
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    @Override
    public ParseTree makeCopy() {
        return (ParseTree)this.clone();
    }
    
    @Override
    public abstract ParseTree makeRecursiveCopy();
    
    @Override
    public abstract boolean equals(final ParseTree p0);
    
    @Override
    public int hashCode() {
        return this.getObjectID();
    }
    
    @Override
    public String toString() {
        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        final SourceCodeWriter sourceCodeWriter = new SourceCodeWriter(printWriter);
        try {
            this.accept(sourceCodeWriter);
        }
        catch (ParseTreeException ex) {
            System.err.println("fail in toString()");
        }
        printWriter.close();
        return out.toString();
    }
    
    public static final boolean equal(final ParseTree parseTree, final ParseTree parseTree2) {
        return (parseTree == null && parseTree2 == null) || (parseTree != null && parseTree2 != null && (parseTree == parseTree2 || parseTree.equals(parseTree2)));
    }
    
    public static final int lastObjectID() {
        return ParseTreeObject.idCount;
    }
    
    private final synchronized void setObjectID() {
        ++ParseTreeObject.idCount;
        this.objectID = ParseTreeObject.idCount;
    }
    
    @Override
    public final int getObjectID() {
        return this.objectID;
    }
    
    @Override
    public String toFlattenString() {
        final StringTokenizer stringTokenizer = new StringTokenizer(this.toString(), "\\\"", true);
        final StringBuffer sb = new StringBuffer();
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals("\\") || nextToken.equals("\"")) {
                sb.append("\\");
            }
            sb.append(nextToken);
        }
        final StringTokenizer stringTokenizer2 = new StringTokenizer(sb.toString(), "\n\r", false);
        final StringBuffer sb2 = new StringBuffer();
        while (stringTokenizer2.hasMoreTokens()) {
            sb2.append(" " + stringTokenizer2.nextToken());
        }
        return sb2.toString();
    }
    
    @Override
    public abstract void accept(final ParseTreeVisitor p0) throws ParseTreeException;
    
    @Override
    public abstract void childrenAccept(final ParseTreeVisitor p0) throws ParseTreeException;
    
    static {
        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.println();
        printWriter.close();
        ParseTreeObject.LN = out.toString();
        ParseTreeObject.idCount = 0;
    }
}

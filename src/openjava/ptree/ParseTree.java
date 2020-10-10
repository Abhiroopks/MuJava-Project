// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public interface ParseTree
{
    void replace(final ParseTree p0) throws ParseTreeException;
    
    ParseTree makeRecursiveCopy();
    
    ParseTree makeCopy();
    
    boolean equals(final ParseTree p0);
    
    String toString();
    
    String toFlattenString();
    
    int getObjectID();
    
    void accept(final ParseTreeVisitor p0) throws ParseTreeException;
    
    void childrenAccept(final ParseTreeVisitor p0) throws ParseTreeException;
}

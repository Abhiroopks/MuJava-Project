// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ParseTree;

public interface SyntaxRule extends TokenID
{
    ParseTree consume(final TokenSource p0) throws SyntaxException;
    
    boolean lookahead(final TokenSource p0);
    
    SyntaxException getSyntaxException();
}

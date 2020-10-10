// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.tools.parser.Token;
import openjava.mop.Environment;

public interface TokenSource
{
    Environment getEnvironment();
    
    Token getNextToken();
    
    Token getToken(final int p0);
}

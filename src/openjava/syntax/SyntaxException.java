// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.tools.parser.Token;
import openjava.tools.parser.ParseException;

public class SyntaxException extends ParseException
{
    public SyntaxException(final ParseException ex) {
        super(ex.currentToken, ex.expectedTokenSequences, ex.tokenImage);
    }
    
    public SyntaxException(final Token token, final int[][] array, final String[] array2) {
        super(token, array, array2);
    }
    
    public SyntaxException() {
    }
    
    public SyntaxException(final String s) {
        super(s);
    }
}

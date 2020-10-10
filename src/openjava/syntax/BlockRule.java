// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.Block;
import openjava.ptree.ParseTree;
import openjava.mop.Environment;

public class BlockRule extends AbstractSyntaxRule
{
    private Environment env;
    
    public BlockRule(final Environment env) {
        this.env = env;
    }
    
    public BlockRule() {
        this(null);
    }
    
    @Override
    public ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        final Block consumeBlock = JavaSyntaxRules.consumeBlock(tokenSource, this.env);
        if (consumeBlock == null) {
            throw JavaSyntaxRules.getLastException();
        }
        return consumeBlock;
    }
}

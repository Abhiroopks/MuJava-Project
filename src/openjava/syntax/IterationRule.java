// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ObjectList;
import openjava.ptree.ParseTree;

public class IterationRule extends AbstractSyntaxRule
{
    private SyntaxRule elementRule;
    private boolean allowsEmpty;
    
    public IterationRule(final SyntaxRule elementRule, final boolean allowsEmpty) {
        this.elementRule = elementRule;
        this.allowsEmpty = allowsEmpty;
    }
    
    public IterationRule(final SyntaxRule syntaxRule) {
        this(syntaxRule, false);
    }
    
    @Override
    public ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        final ObjectList list = new ObjectList();
        if (!this.allowsEmpty) {
            list.add(this.elementRule.consume(tokenSource));
        }
        while (this.elementRule.lookahead(tokenSource)) {
            list.add(this.elementRule.consume(tokenSource));
        }
        return list;
    }
}

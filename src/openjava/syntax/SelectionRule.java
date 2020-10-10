// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ParseTree;

public class SelectionRule extends AbstractSyntaxRule
{
    protected SyntaxRule[] elementRules;
    
    public SelectionRule(final SyntaxRule[] elementRules) {
        this.elementRules = elementRules;
    }
    
    public SelectionRule(final SyntaxRule syntaxRule, final SyntaxRule syntaxRule2) {
        this(new SyntaxRule[] { syntaxRule, syntaxRule2 });
    }
    
    public SelectionRule(final SyntaxRule syntaxRule, final SyntaxRule syntaxRule2, final SyntaxRule syntaxRule3) {
        this(new SyntaxRule[] { syntaxRule, syntaxRule2, syntaxRule3 });
    }
    
    @Override
    public ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        for (int i = 0; i < this.elementRules.length; ++i) {
            if (this.elementRules[i].lookahead(tokenSource)) {
                return this.elementRules[i].consume(tokenSource);
            }
        }
        throw new SyntaxException("neither of selection");
    }
}

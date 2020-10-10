// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ObjectList;
import openjava.ptree.ParseTree;

public class CompositeRule extends AbstractSyntaxRule
{
    private SyntaxRule[] elementRules;
    
    public CompositeRule(final SyntaxRule[] elementRules) {
        this.elementRules = elementRules;
    }
    
    public CompositeRule(final SyntaxRule syntaxRule, final SyntaxRule syntaxRule2) {
        this(new SyntaxRule[] { syntaxRule, syntaxRule2 });
    }
    
    public CompositeRule(final SyntaxRule syntaxRule, final SyntaxRule syntaxRule2, final SyntaxRule syntaxRule3) {
        this(new SyntaxRule[] { syntaxRule, syntaxRule2, syntaxRule3 });
    }
    
    @Override
    public ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        final ObjectList list = new ObjectList();
        for (int i = 0; i < this.elementRules.length; ++i) {
            list.add(this.elementRules[i].consume(tokenSource));
        }
        return list;
    }
}

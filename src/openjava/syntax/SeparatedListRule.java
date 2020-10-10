// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ParseTree;

public abstract class SeparatedListRule extends AbstractSyntaxRule
{
    private SyntaxRule elementRule;
    private int separator;
    private boolean allowsEmpty;
    
    protected abstract void initList();
    
    protected abstract void addListElement(final Object p0);
    
    protected abstract ParseTree getList();
    
    public SeparatedListRule(final SyntaxRule elementRule, final int separator, final boolean allowsEmpty) {
        this.elementRule = elementRule;
        this.separator = separator;
        this.allowsEmpty = allowsEmpty;
    }
    
    public SeparatedListRule(final SyntaxRule syntaxRule, final int n) {
        this(syntaxRule, n, false);
    }
    
    @Override
    public final ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        this.initList();
        if (!this.allowsEmpty) {
            this.addListElement(this.elementRule.consume(tokenSource));
        }
        while (new CompositeRule(new TokenRule(this.separator), this.elementRule).lookahead(tokenSource)) {
            this.addListElement(this.consumeSepAndElem(tokenSource));
        }
        return this.getList();
    }
    
    private ParseTree consumeSepAndElem(final TokenSource tokenSource) throws SyntaxException {
        tokenSource.getNextToken();
        return this.elementRule.consume(tokenSource);
    }
}

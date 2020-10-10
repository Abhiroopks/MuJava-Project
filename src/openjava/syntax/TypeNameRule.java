// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.TypeName;
import openjava.ptree.ParseTree;

public class TypeNameRule extends AbstractSyntaxRule
{
    @Override
    public final ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        return this.consumeTypeName(tokenSource);
    }
    
    public TypeName consumeTypeName(final TokenSource tokenSource) throws SyntaxException {
        final TypeName consumeTypeName = JavaSyntaxRules.consumeTypeName(tokenSource);
        if (consumeTypeName == null) {
            throw JavaSyntaxRules.getLastException();
        }
        consumeTypeName.setName(tokenSource.getEnvironment().toQualifiedName(consumeTypeName.getName()));
        return consumeTypeName;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.TypeName;
import openjava.ptree.Block;
import openjava.ptree.Statement;
import openjava.tools.parser.Parser;
import openjava.ptree.Expression;
import openjava.tools.parser.Token;
import openjava.ptree.ParseTreeException;
import openjava.ptree.util.ParseTreeVisitor;
import openjava.ptree.ObjectList;
import openjava.ptree.util.MemberAccessCorrector;
import openjava.mop.Environment;
import openjava.ptree.ParseTree;
import openjava.tools.parser.ParseException;

public class JavaSyntaxRules implements TokenID
{
    private static ParseException lastException;
    
    private JavaSyntaxRules() {
    }
    
    public static SyntaxException getLastException() {
        return new SyntaxException(JavaSyntaxRules.lastException);
    }
    
    private static ParseTree correct(final ParseTree parseTree, final Environment environment) {
        final MemberAccessCorrector memberAccessCorrector = new MemberAccessCorrector(environment);
        final ObjectList list = new ObjectList(parseTree);
        try {
            parseTree.accept(memberAccessCorrector);
        }
        catch (ParseTreeException ex) {
            System.err.println(ex.getMessage());
        }
        return (ParseTree)list.get(0);
    }
    
    private static void adjustTokenSource(final TokenSource tokenSource, final Token token) {
        for (Token token2 = tokenSource.getToken(0); token2 != token; token2 = tokenSource.getNextToken()) {}
    }
    
    public static final Expression consumeExpression(final TokenSource tokenSource, Environment environment) {
        if (environment == null) {
            environment = tokenSource.getEnvironment();
        }
        final Parser parser = new Parser(new RestorableTokenSource(tokenSource));
        try {
            final Expression expression = parser.Expression(environment);
            adjustTokenSource(tokenSource, parser.getToken(0));
            return (Expression)correct(expression, environment);
        }
        catch (ParseException lastException) {
            JavaSyntaxRules.lastException = lastException;
            return null;
        }
    }
    
    public static final Expression consumeExpression(final TokenSource tokenSource) {
        return consumeExpression(tokenSource, null);
    }
    
    public static final Statement consumeStatement(final TokenSource tokenSource, Environment environment) {
        if (environment == null) {
            environment = tokenSource.getEnvironment();
        }
        final Parser parser = new Parser(new RestorableTokenSource(tokenSource));
        try {
            final Statement statement = parser.Statement(environment);
            adjustTokenSource(tokenSource, parser.getToken(0));
            return (Statement)correct(statement, environment);
        }
        catch (ParseException lastException) {
            JavaSyntaxRules.lastException = lastException;
            return null;
        }
    }
    
    public static final Statement consumeStatement(final TokenSource tokenSource) {
        return consumeStatement(tokenSource, tokenSource.getEnvironment());
    }
    
    public static final Block consumeBlock(final TokenSource tokenSource, Environment environment) {
        if (environment == null) {
            environment = tokenSource.getEnvironment();
        }
        final Parser parser = new Parser(new RestorableTokenSource(tokenSource));
        try {
            final Block block = parser.Block(environment);
            adjustTokenSource(tokenSource, parser.getToken(0));
            return (Block)correct(block, environment);
        }
        catch (ParseException lastException) {
            JavaSyntaxRules.lastException = lastException;
            return null;
        }
    }
    
    public static final Block consumeBlock(final TokenSource tokenSource) {
        return consumeBlock(tokenSource, tokenSource.getEnvironment());
    }
    
    public static final TypeName consumeTypeName(final TokenSource tokenSource) {
        final Environment environment = tokenSource.getEnvironment();
        final Parser parser = new Parser(new RestorableTokenSource(tokenSource));
        try {
            final TypeName type = parser.Type(environment);
            adjustTokenSource(tokenSource, parser.getToken(0));
            return (TypeName)correct(type, environment);
        }
        catch (ParseException lastException) {
            JavaSyntaxRules.lastException = lastException;
            return null;
        }
    }
    
    static {
        JavaSyntaxRules.lastException = null;
    }
}

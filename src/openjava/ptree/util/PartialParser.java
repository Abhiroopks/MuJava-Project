// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.ptree.MemberDeclarationList;
import openjava.ptree.MemberDeclaration;
import openjava.mop.ClosedEnvironment;
import java.io.Reader;
import openjava.tools.parser.Parser;
import java.io.StringReader;
import openjava.tools.DebugOut;
import openjava.mop.MOPException;
import openjava.ptree.Expression;
import openjava.ptree.Statement;
import openjava.ptree.StatementList;
import openjava.ptree.ParseTreeException;
import openjava.ptree.ObjectList;
import openjava.ptree.ParseTree;
import openjava.mop.Environment;

public final class PartialParser
{
    protected PartialParser() {
    }
    
    private static ParseTree initialize(final Environment environment, final ParseTree parseTree) throws ParseTreeException {
        final ObjectList list = new ObjectList(parseTree);
        parseTree.accept(new MemberAccessCorrector(environment));
        return (ParseTree)list.get(0);
    }
    
    public static final String replace(final String str, final Object[] array) throws MOPException {
        try {
            final StringBuffer sb = new StringBuffer();
            int n = 0;
            int beginIndex = 0;
            int index;
            while ((index = str.indexOf(35, beginIndex)) != -1) {
                sb.append(str.substring(beginIndex, index));
                if (str.regionMatches(index, "#STMTS", 0, 6)) {
                    sb.append(array[n++]);
                    beginIndex = index + 6;
                }
                else if (str.regionMatches(index, "#STMT", 0, 5)) {
                    sb.append(array[n++]);
                    beginIndex = index + 5;
                }
                else if (str.regionMatches(index, "#EXPR", 0, 5)) {
                    sb.append(array[n++]);
                    beginIndex = index + 5;
                }
                else if (str.regionMatches(index, "#s", 0, 2)) {
                    sb.append(array[n++].toString());
                    beginIndex = index + 2;
                }
                else if (str.regionMatches(index, "##", 0, 2)) {
                    sb.append('#');
                    beginIndex = index + 2;
                }
                else {
                    sb.append('#');
                    beginIndex = index + 1;
                }
            }
            sb.append(str.substring(beginIndex));
            return sb.toString();
        }
        catch (Exception ex) {
            throw new MOPException("PartialParser.replace() : illegal format for arguments : " + str);
        }
    }
    
    public static final String replace(final String s, final Object o) throws MOPException {
        return replace(s, new Object[] { o });
    }
    
    public static final String replace(final String s, final Object o, final Object o2) throws MOPException {
        return replace(s, new Object[] { o, o2 });
    }
    
    public static final String replace(final String s, final Object o, final Object o2, final Object o3) throws MOPException {
        return replace(s, new Object[] { o, o2, o3 });
    }
    
    public static final String replace(final String s, final Object o, final Object o2, final Object o3, final Object o4) throws MOPException {
        return replace(s, new Object[] { o, o2, o3, o4 });
    }
    
    public static final String replace(final String s, final Object o, final Object o2, final Object o3, final Object o4, final Object o5) throws MOPException {
        return replace(s, new Object[] { o, o2, o3, o4, o5 });
    }
    
    public static final String replace(final String s, final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) throws MOPException {
        return replace(s, new Object[] { o, o2, o3, o4, o5, o6 });
    }
    
    public static final String replace(final String s, final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7) throws MOPException {
        return replace(s, new Object[] { o, o2, o3, o4, o5, o6, o7 });
    }
    
    public static final String replace(final String s, final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) throws MOPException {
        return replace(s, new Object[] { o, o2, o3, o4, o5, o6, o7, o8 });
    }
    
    public static Expression makeExpression(final Environment env, final String str) throws MOPException {
        DebugOut.println("PP makeExpression() : " + str);
        final Parser parser = new Parser(new StringReader(str));
        Expression expression;
        try {
            expression = (Expression)initialize(env, parser.Expression(env));
        }
        catch (Exception x) {
            System.err.println("partial parsing failed for : " + str);
            System.err.println(x);
            System.err.println(env.toString());
            throw new MOPException(x);
        }
        return expression;
    }
    
    public static Statement makeStatement(final Environment env, final String str) throws MOPException {
        DebugOut.println("PP makeStatement() : " + str);
        final Parser parser = new Parser(new StringReader(str));
        Statement statement;
        try {
            statement = (Statement)initialize(env, parser.Statement(env));
        }
        catch (Exception x) {
            System.err.println("partial parsing failed for : " + str);
            System.err.println(x);
            System.err.println(env.toString());
            throw new MOPException(x);
        }
        return statement;
    }
    
    public static StatementList makeStatementList(Environment env, final String str) throws MOPException {
        DebugOut.println("PP makeStatementList() : " + str);
        final Parser parser = new Parser(new StringReader(str));
        env = new ClosedEnvironment(env);
        StatementList list;
        try {
            list = (StatementList)initialize(env, parser.BlockOrStatementListOpt(env));
        }
        catch (Exception x) {
            System.err.println("partial parsing failed for : " + str);
            System.err.println(x);
            System.err.println(env.toString());
            throw new MOPException(x);
        }
        return list;
    }
    
    public static MemberDeclaration makeMemberDeclaration(final String s) throws MOPException {
        return null;
    }
    
    public static MemberDeclarationList makeMemberDeclarationList(final String s) throws MOPException {
        return null;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.ptree.AssertStatement;
import openjava.ptree.TypeParameterList;
import openjava.ptree.TypeParameter;
import openjava.ptree.EnumConstantList;
import openjava.ptree.EnumConstant;
import openjava.ptree.EnumDeclaration;
import openjava.ptree.WhileStatement;
import openjava.ptree.VariableDeclarator;
import openjava.ptree.VariableDeclaration;
import openjava.ptree.Variable;
import openjava.ptree.UnaryExpression;
import openjava.ptree.TypeName;
import openjava.ptree.TryStatement;
import openjava.ptree.ThrowStatement;
import openjava.ptree.SynchronizedStatement;
import openjava.ptree.SwitchStatement;
import openjava.ptree.StatementList;
import openjava.ptree.SelfAccess;
import openjava.ptree.ReturnStatement;
import openjava.ptree.ParameterList;
import openjava.ptree.Parameter;
import openjava.ptree.ModifierList;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.MethodCall;
import openjava.ptree.MemberInitializer;
import openjava.ptree.MemberDeclarationList;
import openjava.ptree.Literal;
import openjava.ptree.LabeledStatement;
import openjava.ptree.InstanceofExpression;
import openjava.ptree.IfStatement;
import openjava.ptree.ForStatement;
import openjava.ptree.FieldDeclaration;
import openjava.ptree.FieldAccess;
import openjava.ptree.ExpressionStatement;
import openjava.ptree.ExpressionList;
import openjava.ptree.EmptyStatement;
import openjava.ptree.DoWhileStatement;
import openjava.ptree.ContinueStatement;
import openjava.ptree.ConstructorInvocation;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.ConditionalExpression;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ClassLiteral;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CatchList;
import openjava.ptree.CatchBlock;
import openjava.ptree.CastExpression;
import openjava.ptree.CaseLabelList;
import openjava.ptree.CaseLabel;
import openjava.ptree.CaseGroupList;
import openjava.ptree.CaseGroup;
import openjava.ptree.BreakStatement;
import openjava.ptree.Block;
import openjava.ptree.BinaryExpression;
import openjava.ptree.AssignmentExpression;
import openjava.ptree.ArrayInitializer;
import openjava.ptree.ArrayAllocationExpression;
import openjava.ptree.ArrayAccess;
import openjava.ptree.AllocationExpression;
import openjava.ptree.List;
import openjava.ptree.VariableInitializer;
import openjava.ptree.Expression;
import openjava.ptree.Statement;
import openjava.ptree.MemberDeclaration;
import openjava.ptree.Leaf;
import openjava.ptree.NonLeaf;
import openjava.ptree.ParseTreeObject;
import openjava.ptree.ParseTreeException;
import openjava.ptree.ParseTree;

public abstract class ParseTreeVisitor
{
    public void visit(final ParseTree parseTree) throws ParseTreeException {
        parseTree.accept(this);
    }
    
    public void visit(final ParseTreeObject parseTreeObject) throws ParseTreeException {
        parseTreeObject.accept(this);
    }
    
    public void visit(final NonLeaf nonLeaf) throws ParseTreeException {
        nonLeaf.accept(this);
    }
    
    public void visit(final Leaf leaf) throws ParseTreeException {
        leaf.accept(this);
    }
    
    public void visit(final MemberDeclaration memberDeclaration) throws ParseTreeException {
        memberDeclaration.accept(this);
    }
    
    public void visit(final Statement statement) throws ParseTreeException {
        statement.accept(this);
    }
    
    public void visit(final Expression expression) throws ParseTreeException {
        expression.accept(this);
    }
    
    public void visit(final VariableInitializer variableInitializer) throws ParseTreeException {
        variableInitializer.accept(this);
    }
    
    public void visit(final List list) throws ParseTreeException {
        list.accept(this);
    }
    
    public abstract void visit(final AllocationExpression p0) throws ParseTreeException;
    
    public abstract void visit(final ArrayAccess p0) throws ParseTreeException;
    
    public abstract void visit(final ArrayAllocationExpression p0) throws ParseTreeException;
    
    public abstract void visit(final ArrayInitializer p0) throws ParseTreeException;
    
    public abstract void visit(final AssignmentExpression p0) throws ParseTreeException;
    
    public abstract void visit(final BinaryExpression p0) throws ParseTreeException;
    
    public abstract void visit(final Block p0) throws ParseTreeException;
    
    public abstract void visit(final BreakStatement p0) throws ParseTreeException;
    
    public abstract void visit(final CaseGroup p0) throws ParseTreeException;
    
    public abstract void visit(final CaseGroupList p0) throws ParseTreeException;
    
    public abstract void visit(final CaseLabel p0) throws ParseTreeException;
    
    public abstract void visit(final CaseLabelList p0) throws ParseTreeException;
    
    public abstract void visit(final CastExpression p0) throws ParseTreeException;
    
    public abstract void visit(final CatchBlock p0) throws ParseTreeException;
    
    public abstract void visit(final CatchList p0) throws ParseTreeException;
    
    public abstract void visit(final ClassDeclaration p0) throws ParseTreeException;
    
    public abstract void visit(final ClassDeclarationList p0) throws ParseTreeException;
    
    public abstract void visit(final ClassLiteral p0) throws ParseTreeException;
    
    public abstract void visit(final CompilationUnit p0) throws ParseTreeException;
    
    public abstract void visit(final ConditionalExpression p0) throws ParseTreeException;
    
    public abstract void visit(final ConstructorDeclaration p0) throws ParseTreeException;
    
    public abstract void visit(final ConstructorInvocation p0) throws ParseTreeException;
    
    public abstract void visit(final ContinueStatement p0) throws ParseTreeException;
    
    public abstract void visit(final DoWhileStatement p0) throws ParseTreeException;
    
    public abstract void visit(final EmptyStatement p0) throws ParseTreeException;
    
    public abstract void visit(final ExpressionList p0) throws ParseTreeException;
    
    public abstract void visit(final ExpressionStatement p0) throws ParseTreeException;
    
    public abstract void visit(final FieldAccess p0) throws ParseTreeException;
    
    public abstract void visit(final FieldDeclaration p0) throws ParseTreeException;
    
    public abstract void visit(final ForStatement p0) throws ParseTreeException;
    
    public abstract void visit(final IfStatement p0) throws ParseTreeException;
    
    public abstract void visit(final InstanceofExpression p0) throws ParseTreeException;
    
    public abstract void visit(final LabeledStatement p0) throws ParseTreeException;
    
    public abstract void visit(final Literal p0) throws ParseTreeException;
    
    public abstract void visit(final MemberDeclarationList p0) throws ParseTreeException;
    
    public abstract void visit(final MemberInitializer p0) throws ParseTreeException;
    
    public abstract void visit(final MethodCall p0) throws ParseTreeException;
    
    public abstract void visit(final MethodDeclaration p0) throws ParseTreeException;
    
    public abstract void visit(final ModifierList p0) throws ParseTreeException;
    
    public abstract void visit(final Parameter p0) throws ParseTreeException;
    
    public abstract void visit(final ParameterList p0) throws ParseTreeException;
    
    public abstract void visit(final ReturnStatement p0) throws ParseTreeException;
    
    public abstract void visit(final SelfAccess p0) throws ParseTreeException;
    
    public abstract void visit(final StatementList p0) throws ParseTreeException;
    
    public abstract void visit(final SwitchStatement p0) throws ParseTreeException;
    
    public abstract void visit(final SynchronizedStatement p0) throws ParseTreeException;
    
    public abstract void visit(final ThrowStatement p0) throws ParseTreeException;
    
    public abstract void visit(final TryStatement p0) throws ParseTreeException;
    
    public abstract void visit(final TypeName p0) throws ParseTreeException;
    
    public abstract void visit(final UnaryExpression p0) throws ParseTreeException;
    
    public abstract void visit(final Variable p0) throws ParseTreeException;
    
    public abstract void visit(final VariableDeclaration p0) throws ParseTreeException;
    
    public abstract void visit(final VariableDeclarator p0) throws ParseTreeException;
    
    public abstract void visit(final WhileStatement p0) throws ParseTreeException;
    
    public abstract void visit(final EnumDeclaration p0) throws ParseTreeException;
    
    public abstract void visit(final EnumConstant p0) throws ParseTreeException;
    
    public abstract void visit(final EnumConstantList p0) throws ParseTreeException;
    
    public abstract void visit(final TypeParameter p0) throws ParseTreeException;
    
    public abstract void visit(final TypeParameterList p0) throws ParseTreeException;
    
    public abstract void visit(final AssertStatement p0) throws ParseTreeException;
}

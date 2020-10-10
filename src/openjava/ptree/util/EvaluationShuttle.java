// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.ptree.ParseTree;
import openjava.ptree.WhileStatement;
import openjava.ptree.VariableDeclarator;
import openjava.ptree.VariableDeclaration;
import openjava.ptree.Variable;
import openjava.ptree.UnaryExpression;
import openjava.ptree.TypeParameterList;
import openjava.ptree.TypeParameter;
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
import openjava.ptree.AssertStatement;
import openjava.ptree.IfStatement;
import openjava.ptree.ForStatement;
import openjava.ptree.FieldDeclaration;
import openjava.ptree.FieldAccess;
import openjava.ptree.ExpressionStatement;
import openjava.ptree.ExpressionList;
import openjava.ptree.EnumConstantList;
import openjava.ptree.EnumConstant;
import openjava.ptree.EnumDeclaration;
import openjava.ptree.EmptyStatement;
import openjava.ptree.DoWhileStatement;
import openjava.ptree.ContinueStatement;
import openjava.ptree.ConstructorInvocation;
import openjava.ptree.MemberDeclaration;
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
import openjava.ptree.Statement;
import openjava.ptree.Block;
import openjava.ptree.BinaryExpression;
import openjava.ptree.AssignmentExpression;
import openjava.ptree.VariableInitializer;
import openjava.ptree.ArrayInitializer;
import openjava.ptree.ArrayAllocationExpression;
import openjava.ptree.ArrayAccess;
import openjava.ptree.ParseTreeException;
import openjava.ptree.Expression;
import openjava.ptree.AllocationExpression;
import openjava.mop.Environment;

public abstract class EvaluationShuttle extends ParseTreeVisitor
{
    private Environment env;
    
    public EvaluationShuttle(final Environment env) {
        this.env = env;
    }
    
    protected Environment getEnvironment() {
        return this.env;
    }
    
    protected void setEnvironment(final Environment env) {
        this.env = env;
    }
    
    public Expression evaluateDown(final AllocationExpression allocationExpression) throws ParseTreeException {
        return allocationExpression;
    }
    
    public Expression evaluateDown(final ArrayAccess arrayAccess) throws ParseTreeException {
        return arrayAccess;
    }
    
    public Expression evaluateDown(final ArrayAllocationExpression arrayAllocationExpression) throws ParseTreeException {
        return arrayAllocationExpression;
    }
    
    public VariableInitializer evaluateDown(final ArrayInitializer arrayInitializer) throws ParseTreeException {
        return arrayInitializer;
    }
    
    public Expression evaluateDown(final AssignmentExpression assignmentExpression) throws ParseTreeException {
        return assignmentExpression;
    }
    
    public Expression evaluateDown(final BinaryExpression binaryExpression) throws ParseTreeException {
        return binaryExpression;
    }
    
    public Statement evaluateDown(final Block block) throws ParseTreeException {
        return block;
    }
    
    public Statement evaluateDown(final BreakStatement breakStatement) throws ParseTreeException {
        return breakStatement;
    }
    
    public CaseGroup evaluateDown(final CaseGroup caseGroup) throws ParseTreeException {
        return caseGroup;
    }
    
    public CaseGroupList evaluateDown(final CaseGroupList list) throws ParseTreeException {
        return list;
    }
    
    public CaseLabel evaluateDown(final CaseLabel caseLabel) throws ParseTreeException {
        return caseLabel;
    }
    
    public CaseLabelList evaluateDown(final CaseLabelList list) throws ParseTreeException {
        return list;
    }
    
    public Expression evaluateDown(final CastExpression castExpression) throws ParseTreeException {
        return castExpression;
    }
    
    public CatchBlock evaluateDown(final CatchBlock catchBlock) throws ParseTreeException {
        return catchBlock;
    }
    
    public CatchList evaluateDown(final CatchList list) throws ParseTreeException {
        return list;
    }
    
    public ClassDeclaration evaluateDown(final ClassDeclaration classDeclaration) throws ParseTreeException {
        return classDeclaration;
    }
    
    public ClassDeclarationList evaluateDown(final ClassDeclarationList list) throws ParseTreeException {
        return list;
    }
    
    public Expression evaluateDown(final ClassLiteral classLiteral) throws ParseTreeException {
        return classLiteral;
    }
    
    public CompilationUnit evaluateDown(final CompilationUnit compilationUnit) throws ParseTreeException {
        return compilationUnit;
    }
    
    public Expression evaluateDown(final ConditionalExpression conditionalExpression) throws ParseTreeException {
        return conditionalExpression;
    }
    
    public MemberDeclaration evaluateDown(final ConstructorDeclaration constructorDeclaration) throws ParseTreeException {
        return constructorDeclaration;
    }
    
    public ConstructorInvocation evaluateDown(final ConstructorInvocation constructorInvocation) throws ParseTreeException {
        return constructorInvocation;
    }
    
    public Statement evaluateDown(final ContinueStatement continueStatement) throws ParseTreeException {
        return continueStatement;
    }
    
    public Statement evaluateDown(final DoWhileStatement doWhileStatement) throws ParseTreeException {
        return doWhileStatement;
    }
    
    public Statement evaluateDown(final EmptyStatement emptyStatement) throws ParseTreeException {
        return emptyStatement;
    }
    
    public MemberDeclaration evaluateDown(final EnumDeclaration enumDeclaration) throws ParseTreeException {
        return enumDeclaration;
    }
    
    public EnumConstant evaluateDown(final EnumConstant enumConstant) throws ParseTreeException {
        return enumConstant;
    }
    
    public EnumConstantList evaluateDown(final EnumConstantList list) throws ParseTreeException {
        return list;
    }
    
    public ExpressionList evaluateDown(final ExpressionList list) throws ParseTreeException {
        return list;
    }
    
    public Statement evaluateDown(final ExpressionStatement expressionStatement) throws ParseTreeException {
        return expressionStatement;
    }
    
    public Expression evaluateDown(final FieldAccess fieldAccess) throws ParseTreeException {
        return fieldAccess;
    }
    
    public MemberDeclaration evaluateDown(final FieldDeclaration fieldDeclaration) throws ParseTreeException {
        return fieldDeclaration;
    }
    
    public Statement evaluateDown(final ForStatement forStatement) throws ParseTreeException {
        return forStatement;
    }
    
    public Statement evaluateDown(final IfStatement ifStatement) throws ParseTreeException {
        return ifStatement;
    }
    
    public Statement evaluateDown(final AssertStatement assertStatement) throws ParseTreeException {
        return assertStatement;
    }
    
    public Expression evaluateDown(final InstanceofExpression instanceofExpression) throws ParseTreeException {
        return instanceofExpression;
    }
    
    public Statement evaluateDown(final LabeledStatement labeledStatement) throws ParseTreeException {
        return labeledStatement;
    }
    
    public Expression evaluateDown(final Literal literal) throws ParseTreeException {
        return literal;
    }
    
    public MemberDeclarationList evaluateDown(final MemberDeclarationList list) throws ParseTreeException {
        return list;
    }
    
    public MemberDeclaration evaluateDown(final MemberInitializer memberInitializer) throws ParseTreeException {
        return memberInitializer;
    }
    
    public Expression evaluateDown(final MethodCall methodCall) throws ParseTreeException {
        return methodCall;
    }
    
    public MemberDeclaration evaluateDown(final MethodDeclaration methodDeclaration) throws ParseTreeException {
        return methodDeclaration;
    }
    
    public ModifierList evaluateDown(final ModifierList list) throws ParseTreeException {
        return list;
    }
    
    public Parameter evaluateDown(final Parameter parameter) throws ParseTreeException {
        return parameter;
    }
    
    public ParameterList evaluateDown(final ParameterList list) throws ParseTreeException {
        return list;
    }
    
    public Statement evaluateDown(final ReturnStatement returnStatement) throws ParseTreeException {
        return returnStatement;
    }
    
    public Expression evaluateDown(final SelfAccess selfAccess) throws ParseTreeException {
        return selfAccess;
    }
    
    public StatementList evaluateDown(final StatementList list) throws ParseTreeException {
        return list;
    }
    
    public Statement evaluateDown(final SwitchStatement switchStatement) throws ParseTreeException {
        return switchStatement;
    }
    
    public Statement evaluateDown(final SynchronizedStatement synchronizedStatement) throws ParseTreeException {
        return synchronizedStatement;
    }
    
    public Statement evaluateDown(final ThrowStatement throwStatement) throws ParseTreeException {
        return throwStatement;
    }
    
    public Statement evaluateDown(final TryStatement tryStatement) throws ParseTreeException {
        return tryStatement;
    }
    
    public TypeName evaluateDown(final TypeName typeName) throws ParseTreeException {
        return typeName;
    }
    
    public TypeParameter evaluateDown(final TypeParameter typeParameter) throws ParseTreeException {
        return typeParameter;
    }
    
    public TypeParameterList evaluateDown(final TypeParameterList list) throws ParseTreeException {
        return list;
    }
    
    public Expression evaluateDown(final UnaryExpression unaryExpression) throws ParseTreeException {
        return unaryExpression;
    }
    
    public Expression evaluateDown(final Variable variable) throws ParseTreeException {
        return variable;
    }
    
    public Statement evaluateDown(final VariableDeclaration variableDeclaration) throws ParseTreeException {
        return variableDeclaration;
    }
    
    public VariableDeclarator evaluateDown(final VariableDeclarator variableDeclarator) throws ParseTreeException {
        return variableDeclarator;
    }
    
    public Statement evaluateDown(final WhileStatement whileStatement) throws ParseTreeException {
        return whileStatement;
    }
    
    public Expression evaluateUp(final AllocationExpression allocationExpression) throws ParseTreeException {
        return allocationExpression;
    }
    
    public Expression evaluateUp(final ArrayAccess arrayAccess) throws ParseTreeException {
        return arrayAccess;
    }
    
    public Expression evaluateUp(final ArrayAllocationExpression arrayAllocationExpression) throws ParseTreeException {
        return arrayAllocationExpression;
    }
    
    public VariableInitializer evaluateUp(final ArrayInitializer arrayInitializer) throws ParseTreeException {
        return arrayInitializer;
    }
    
    public Expression evaluateUp(final AssignmentExpression assignmentExpression) throws ParseTreeException {
        return assignmentExpression;
    }
    
    public Expression evaluateUp(final BinaryExpression binaryExpression) throws ParseTreeException {
        return binaryExpression;
    }
    
    public Statement evaluateUp(final Block block) throws ParseTreeException {
        return block;
    }
    
    public Statement evaluateUp(final BreakStatement breakStatement) throws ParseTreeException {
        return breakStatement;
    }
    
    public CaseGroup evaluateUp(final CaseGroup caseGroup) throws ParseTreeException {
        return caseGroup;
    }
    
    public CaseGroupList evaluateUp(final CaseGroupList list) throws ParseTreeException {
        return list;
    }
    
    public CaseLabel evaluateUp(final CaseLabel caseLabel) throws ParseTreeException {
        return caseLabel;
    }
    
    public CaseLabelList evaluateUp(final CaseLabelList list) throws ParseTreeException {
        return list;
    }
    
    public Expression evaluateUp(final CastExpression castExpression) throws ParseTreeException {
        return castExpression;
    }
    
    public CatchBlock evaluateUp(final CatchBlock catchBlock) throws ParseTreeException {
        return catchBlock;
    }
    
    public CatchList evaluateUp(final CatchList list) throws ParseTreeException {
        return list;
    }
    
    public ClassDeclaration evaluateUp(final ClassDeclaration classDeclaration) throws ParseTreeException {
        return classDeclaration;
    }
    
    public ClassDeclarationList evaluateUp(final ClassDeclarationList list) throws ParseTreeException {
        return list;
    }
    
    public Expression evaluateUp(final ClassLiteral classLiteral) throws ParseTreeException {
        return classLiteral;
    }
    
    public CompilationUnit evaluateUp(final CompilationUnit compilationUnit) throws ParseTreeException {
        return compilationUnit;
    }
    
    public Expression evaluateUp(final ConditionalExpression conditionalExpression) throws ParseTreeException {
        return conditionalExpression;
    }
    
    public MemberDeclaration evaluateUp(final ConstructorDeclaration constructorDeclaration) throws ParseTreeException {
        return constructorDeclaration;
    }
    
    public ConstructorInvocation evaluateUp(final ConstructorInvocation constructorInvocation) throws ParseTreeException {
        return constructorInvocation;
    }
    
    public Statement evaluateUp(final ContinueStatement continueStatement) throws ParseTreeException {
        return continueStatement;
    }
    
    public Statement evaluateUp(final DoWhileStatement doWhileStatement) throws ParseTreeException {
        return doWhileStatement;
    }
    
    public Statement evaluateUp(final EmptyStatement emptyStatement) throws ParseTreeException {
        return emptyStatement;
    }
    
    public MemberDeclaration evaluateUp(final EnumDeclaration enumDeclaration) throws ParseTreeException {
        return enumDeclaration;
    }
    
    public EnumConstant evaluateUp(final EnumConstant enumConstant) throws ParseTreeException {
        return enumConstant;
    }
    
    public EnumConstantList evaluateUp(final EnumConstantList list) throws ParseTreeException {
        return list;
    }
    
    public ExpressionList evaluateUp(final ExpressionList list) throws ParseTreeException {
        return list;
    }
    
    public Statement evaluateUp(final ExpressionStatement expressionStatement) throws ParseTreeException {
        return expressionStatement;
    }
    
    public Expression evaluateUp(final FieldAccess fieldAccess) throws ParseTreeException {
        return fieldAccess;
    }
    
    public MemberDeclaration evaluateUp(final FieldDeclaration fieldDeclaration) throws ParseTreeException {
        return fieldDeclaration;
    }
    
    public Statement evaluateUp(final ForStatement forStatement) throws ParseTreeException {
        return forStatement;
    }
    
    public Statement evaluateUp(final IfStatement ifStatement) throws ParseTreeException {
        return ifStatement;
    }
    
    public Statement evaluateUp(final AssertStatement assertStatement) throws ParseTreeException {
        return assertStatement;
    }
    
    public Expression evaluateUp(final InstanceofExpression instanceofExpression) throws ParseTreeException {
        return instanceofExpression;
    }
    
    public Statement evaluateUp(final LabeledStatement labeledStatement) throws ParseTreeException {
        return labeledStatement;
    }
    
    public Expression evaluateUp(final Literal literal) throws ParseTreeException {
        return literal;
    }
    
    public MemberDeclarationList evaluateUp(final MemberDeclarationList list) throws ParseTreeException {
        return list;
    }
    
    public MemberDeclaration evaluateUp(final MemberInitializer memberInitializer) throws ParseTreeException {
        return memberInitializer;
    }
    
    public Expression evaluateUp(final MethodCall methodCall) throws ParseTreeException {
        return methodCall;
    }
    
    public MemberDeclaration evaluateUp(final MethodDeclaration methodDeclaration) throws ParseTreeException {
        return methodDeclaration;
    }
    
    public ModifierList evaluateUp(final ModifierList list) throws ParseTreeException {
        return list;
    }
    
    public Parameter evaluateUp(final Parameter parameter) throws ParseTreeException {
        return parameter;
    }
    
    public ParameterList evaluateUp(final ParameterList list) throws ParseTreeException {
        return list;
    }
    
    public Statement evaluateUp(final ReturnStatement returnStatement) throws ParseTreeException {
        return returnStatement;
    }
    
    public Expression evaluateUp(final SelfAccess selfAccess) throws ParseTreeException {
        return selfAccess;
    }
    
    public StatementList evaluateUp(final StatementList list) throws ParseTreeException {
        return list;
    }
    
    public Statement evaluateUp(final SwitchStatement switchStatement) throws ParseTreeException {
        return switchStatement;
    }
    
    public Statement evaluateUp(final SynchronizedStatement synchronizedStatement) throws ParseTreeException {
        return synchronizedStatement;
    }
    
    public Statement evaluateUp(final ThrowStatement throwStatement) throws ParseTreeException {
        return throwStatement;
    }
    
    public Statement evaluateUp(final TryStatement tryStatement) throws ParseTreeException {
        return tryStatement;
    }
    
    public TypeName evaluateUp(final TypeName typeName) throws ParseTreeException {
        return typeName;
    }
    
    public TypeParameter evaluateUp(final TypeParameter typeParameter) throws ParseTreeException {
        return typeParameter;
    }
    
    public TypeParameterList evaluateUp(final TypeParameterList list) throws ParseTreeException {
        return list;
    }
    
    public Expression evaluateUp(final UnaryExpression unaryExpression) throws ParseTreeException {
        return unaryExpression;
    }
    
    public Expression evaluateUp(final Variable variable) throws ParseTreeException {
        return variable;
    }
    
    public Statement evaluateUp(final VariableDeclaration variableDeclaration) throws ParseTreeException {
        return variableDeclaration;
    }
    
    public VariableDeclarator evaluateUp(final VariableDeclarator variableDeclarator) throws ParseTreeException {
        return variableDeclarator;
    }
    
    public Statement evaluateUp(final WhileStatement whileStatement) throws ParseTreeException {
        return whileStatement;
    }
    
    @Override
    public void visit(final AllocationExpression allocationExpression) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(allocationExpression);
        if (evaluateDown != allocationExpression) {
            allocationExpression.replace(evaluateDown);
            return;
        }
        allocationExpression.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(allocationExpression);
        if (evaluateUp != allocationExpression) {
            allocationExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ArrayAccess arrayAccess) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(arrayAccess);
        if (evaluateDown != arrayAccess) {
            arrayAccess.replace(evaluateDown);
            return;
        }
        arrayAccess.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(arrayAccess);
        if (evaluateUp != arrayAccess) {
            arrayAccess.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ArrayAllocationExpression arrayAllocationExpression) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(arrayAllocationExpression);
        if (evaluateDown != arrayAllocationExpression) {
            arrayAllocationExpression.replace(evaluateDown);
            return;
        }
        arrayAllocationExpression.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(arrayAllocationExpression);
        if (evaluateUp != arrayAllocationExpression) {
            arrayAllocationExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ArrayInitializer arrayInitializer) throws ParseTreeException {
        final VariableInitializer evaluateDown = this.evaluateDown(arrayInitializer);
        if (evaluateDown != arrayInitializer) {
            arrayInitializer.replace(evaluateDown);
            return;
        }
        arrayInitializer.childrenAccept(this);
        final VariableInitializer evaluateUp = this.evaluateUp(arrayInitializer);
        if (evaluateUp != arrayInitializer) {
            arrayInitializer.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final AssignmentExpression assignmentExpression) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(assignmentExpression);
        if (evaluateDown != assignmentExpression) {
            assignmentExpression.replace(evaluateDown);
            return;
        }
        assignmentExpression.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(assignmentExpression);
        if (evaluateUp != assignmentExpression) {
            assignmentExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final BinaryExpression binaryExpression) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(binaryExpression);
        if (evaluateDown != binaryExpression) {
            binaryExpression.replace(evaluateDown);
            return;
        }
        binaryExpression.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(binaryExpression);
        if (evaluateUp != binaryExpression) {
            binaryExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final Block block) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(block);
        if (evaluateDown != block) {
            block.replace(evaluateDown);
            return;
        }
        block.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(block);
        if (evaluateUp != block) {
            block.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final BreakStatement breakStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(breakStatement);
        if (evaluateDown != breakStatement) {
            breakStatement.replace(evaluateDown);
            return;
        }
        breakStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(breakStatement);
        if (evaluateUp != breakStatement) {
            breakStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final CaseGroup caseGroup) throws ParseTreeException {
        this.evaluateDown(caseGroup);
        caseGroup.childrenAccept(this);
        this.evaluateUp(caseGroup);
    }
    
    @Override
    public void visit(final CaseGroupList list) throws ParseTreeException {
        final CaseGroupList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final CaseGroupList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final CaseLabel caseLabel) throws ParseTreeException {
        final CaseLabel evaluateDown = this.evaluateDown(caseLabel);
        if (evaluateDown != caseLabel) {
            caseLabel.replace(evaluateDown);
            return;
        }
        caseLabel.childrenAccept(this);
        final CaseLabel evaluateUp = this.evaluateUp(caseLabel);
        if (evaluateUp != caseLabel) {
            caseLabel.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final CaseLabelList list) throws ParseTreeException {
        final CaseLabelList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final CaseLabelList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final CastExpression castExpression) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(castExpression);
        if (evaluateDown != castExpression) {
            castExpression.replace(evaluateDown);
            return;
        }
        castExpression.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(castExpression);
        if (evaluateUp != castExpression) {
            castExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final CatchBlock catchBlock) throws ParseTreeException {
        final CatchBlock evaluateDown = this.evaluateDown(catchBlock);
        if (evaluateDown != catchBlock) {
            catchBlock.replace(evaluateDown);
            return;
        }
        catchBlock.childrenAccept(this);
        final CatchBlock evaluateUp = this.evaluateUp(catchBlock);
        if (evaluateUp != catchBlock) {
            catchBlock.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final CatchList list) throws ParseTreeException {
        final CatchList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final CatchList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ClassDeclaration classDeclaration) throws ParseTreeException {
        final ClassDeclaration evaluateDown = this.evaluateDown(classDeclaration);
        if (evaluateDown != classDeclaration) {
            classDeclaration.replace(evaluateDown);
            return;
        }
        classDeclaration.childrenAccept(this);
        final ClassDeclaration evaluateUp = this.evaluateUp(classDeclaration);
        if (evaluateUp != classDeclaration) {
            classDeclaration.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ClassDeclarationList list) throws ParseTreeException {
        final ClassDeclarationList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final ClassDeclarationList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ClassLiteral classLiteral) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(classLiteral);
        if (evaluateDown != classLiteral) {
            classLiteral.replace(evaluateDown);
            return;
        }
        classLiteral.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(classLiteral);
        if (evaluateUp != classLiteral) {
            classLiteral.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final CompilationUnit compilationUnit) throws ParseTreeException {
        final CompilationUnit evaluateDown = this.evaluateDown(compilationUnit);
        if (evaluateDown != compilationUnit) {
            compilationUnit.replace(evaluateDown);
            return;
        }
        compilationUnit.childrenAccept(this);
        final CompilationUnit evaluateUp = this.evaluateUp(compilationUnit);
        if (evaluateUp != compilationUnit) {
            compilationUnit.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ConditionalExpression conditionalExpression) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(conditionalExpression);
        if (evaluateDown != conditionalExpression) {
            conditionalExpression.replace(evaluateDown);
            return;
        }
        conditionalExpression.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(conditionalExpression);
        if (evaluateUp != conditionalExpression) {
            conditionalExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ConstructorDeclaration constructorDeclaration) throws ParseTreeException {
        final MemberDeclaration evaluateDown = this.evaluateDown(constructorDeclaration);
        if (evaluateDown != constructorDeclaration) {
            constructorDeclaration.replace(evaluateDown);
            return;
        }
        constructorDeclaration.childrenAccept(this);
        final MemberDeclaration evaluateUp = this.evaluateUp(constructorDeclaration);
        if (evaluateUp != constructorDeclaration) {
            constructorDeclaration.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ConstructorInvocation constructorInvocation) throws ParseTreeException {
        final ConstructorInvocation evaluateDown = this.evaluateDown(constructorInvocation);
        if (evaluateDown != constructorInvocation) {
            constructorInvocation.replace(evaluateDown);
            return;
        }
        constructorInvocation.childrenAccept(this);
        final ConstructorInvocation evaluateUp = this.evaluateUp(constructorInvocation);
        if (evaluateUp != constructorInvocation) {
            constructorInvocation.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ContinueStatement continueStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(continueStatement);
        if (evaluateDown != continueStatement) {
            continueStatement.replace(evaluateDown);
            return;
        }
        continueStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(continueStatement);
        if (evaluateUp != continueStatement) {
            continueStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final DoWhileStatement doWhileStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(doWhileStatement);
        if (evaluateDown != doWhileStatement) {
            doWhileStatement.replace(evaluateDown);
            return;
        }
        doWhileStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(doWhileStatement);
        if (evaluateUp != doWhileStatement) {
            doWhileStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final EmptyStatement emptyStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(emptyStatement);
        if (evaluateDown != emptyStatement) {
            emptyStatement.replace(evaluateDown);
            return;
        }
        emptyStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(emptyStatement);
        if (evaluateUp != emptyStatement) {
            emptyStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final EnumDeclaration enumDeclaration) throws ParseTreeException {
        final MemberDeclaration evaluateDown = this.evaluateDown(enumDeclaration);
        if (evaluateDown != enumDeclaration) {
            enumDeclaration.replace(evaluateDown);
            return;
        }
        enumDeclaration.childrenAccept(this);
        final MemberDeclaration evaluateUp = this.evaluateUp(enumDeclaration);
        if (evaluateUp != enumDeclaration) {
            enumDeclaration.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final EnumConstant enumConstant) throws ParseTreeException {
        final EnumConstant evaluateDown = this.evaluateDown(enumConstant);
        if (evaluateDown != enumConstant) {
            enumConstant.replace(evaluateDown);
            return;
        }
        enumConstant.childrenAccept(this);
        final EnumConstant evaluateUp = this.evaluateUp(enumConstant);
        if (evaluateUp != enumConstant) {
            enumConstant.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final EnumConstantList list) throws ParseTreeException {
        final EnumConstantList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final EnumConstantList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ExpressionList list) throws ParseTreeException {
        final ExpressionList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final ExpressionList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ExpressionStatement expressionStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(expressionStatement);
        if (evaluateDown != expressionStatement) {
            expressionStatement.replace(evaluateDown);
            return;
        }
        expressionStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(expressionStatement);
        if (evaluateUp != expressionStatement) {
            expressionStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final FieldAccess fieldAccess) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(fieldAccess);
        if (evaluateDown != fieldAccess) {
            fieldAccess.replace(evaluateDown);
            return;
        }
        fieldAccess.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(fieldAccess);
        if (evaluateUp != fieldAccess) {
            fieldAccess.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final FieldDeclaration fieldDeclaration) throws ParseTreeException {
        final MemberDeclaration evaluateDown = this.evaluateDown(fieldDeclaration);
        if (evaluateDown != fieldDeclaration) {
            fieldDeclaration.replace(evaluateDown);
            return;
        }
        fieldDeclaration.childrenAccept(this);
        final MemberDeclaration evaluateUp = this.evaluateUp(fieldDeclaration);
        if (evaluateUp != fieldDeclaration) {
            fieldDeclaration.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ForStatement forStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(forStatement);
        if (evaluateDown != forStatement) {
            forStatement.replace(evaluateDown);
            return;
        }
        forStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(forStatement);
        if (evaluateUp != forStatement) {
            forStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final IfStatement ifStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(ifStatement);
        if (evaluateDown != ifStatement) {
            ifStatement.replace(evaluateDown);
            return;
        }
        ifStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(ifStatement);
        if (evaluateUp != ifStatement) {
            ifStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final AssertStatement assertStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(assertStatement);
        if (evaluateDown != assertStatement) {
            assertStatement.replace(evaluateDown);
            return;
        }
        assertStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(assertStatement);
        if (evaluateUp != assertStatement) {
            assertStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final InstanceofExpression instanceofExpression) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(instanceofExpression);
        if (evaluateDown != instanceofExpression) {
            instanceofExpression.replace(evaluateDown);
            return;
        }
        instanceofExpression.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(instanceofExpression);
        if (evaluateUp != instanceofExpression) {
            instanceofExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final LabeledStatement labeledStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(labeledStatement);
        if (evaluateDown != labeledStatement) {
            labeledStatement.replace(evaluateDown);
            return;
        }
        labeledStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(labeledStatement);
        if (evaluateUp != labeledStatement) {
            labeledStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final Literal literal) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(literal);
        if (evaluateDown != literal) {
            literal.replace(evaluateDown);
            return;
        }
        literal.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(literal);
        if (evaluateUp != literal) {
            literal.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final MemberDeclarationList list) throws ParseTreeException {
        final MemberDeclarationList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final MemberDeclarationList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final MemberInitializer memberInitializer) throws ParseTreeException {
        final MemberDeclaration evaluateDown = this.evaluateDown(memberInitializer);
        if (evaluateDown != memberInitializer) {
            memberInitializer.replace(evaluateDown);
            return;
        }
        memberInitializer.childrenAccept(this);
        final MemberDeclaration evaluateUp = this.evaluateUp(memberInitializer);
        if (evaluateUp != memberInitializer) {
            memberInitializer.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final MethodCall methodCall) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(methodCall);
        if (evaluateDown != methodCall) {
            methodCall.replace(evaluateDown);
            return;
        }
        methodCall.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(methodCall);
        if (evaluateUp != methodCall) {
            methodCall.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final MethodDeclaration methodDeclaration) throws ParseTreeException {
        final MemberDeclaration evaluateDown = this.evaluateDown(methodDeclaration);
        if (evaluateDown != methodDeclaration) {
            methodDeclaration.replace(evaluateDown);
            return;
        }
        methodDeclaration.childrenAccept(this);
        final MemberDeclaration evaluateUp = this.evaluateUp(methodDeclaration);
        if (evaluateUp != methodDeclaration) {
            methodDeclaration.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ModifierList list) throws ParseTreeException {
        final ModifierList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final ModifierList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final Parameter parameter) throws ParseTreeException {
        final Parameter evaluateDown = this.evaluateDown(parameter);
        if (evaluateDown != parameter) {
            parameter.replace(evaluateDown);
            return;
        }
        parameter.childrenAccept(this);
        final Parameter evaluateUp = this.evaluateUp(parameter);
        if (evaluateUp != parameter) {
            parameter.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ParameterList list) throws ParseTreeException {
        final ParameterList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final ParameterList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ReturnStatement returnStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(returnStatement);
        if (evaluateDown != returnStatement) {
            returnStatement.replace(evaluateDown);
            return;
        }
        returnStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(returnStatement);
        if (evaluateUp != returnStatement) {
            returnStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final SelfAccess selfAccess) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(selfAccess);
        if (evaluateDown != selfAccess) {
            selfAccess.replace(evaluateDown);
            return;
        }
        selfAccess.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(selfAccess);
        if (evaluateUp != selfAccess) {
            selfAccess.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final StatementList list) throws ParseTreeException {
        final StatementList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final StatementList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final SwitchStatement switchStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(switchStatement);
        if (evaluateDown != switchStatement) {
            switchStatement.replace(evaluateDown);
            return;
        }
        switchStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(switchStatement);
        if (evaluateUp != switchStatement) {
            switchStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final SynchronizedStatement synchronizedStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(synchronizedStatement);
        if (evaluateDown != synchronizedStatement) {
            synchronizedStatement.replace(evaluateDown);
            return;
        }
        synchronizedStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(synchronizedStatement);
        if (evaluateUp != synchronizedStatement) {
            synchronizedStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final ThrowStatement throwStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(throwStatement);
        if (evaluateDown != throwStatement) {
            throwStatement.replace(evaluateDown);
            return;
        }
        throwStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(throwStatement);
        if (evaluateUp != throwStatement) {
            throwStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final TryStatement tryStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(tryStatement);
        if (evaluateDown != tryStatement) {
            tryStatement.replace(evaluateDown);
            return;
        }
        tryStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(tryStatement);
        if (evaluateUp != tryStatement) {
            tryStatement.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final TypeName typeName) throws ParseTreeException {
        final TypeName evaluateDown = this.evaluateDown(typeName);
        if (evaluateDown != typeName) {
            typeName.replace(evaluateDown);
            return;
        }
        typeName.childrenAccept(this);
        final TypeName evaluateUp = this.evaluateUp(typeName);
        if (evaluateUp != typeName) {
            typeName.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final TypeParameter typeParameter) throws ParseTreeException {
        final TypeParameter evaluateDown = this.evaluateDown(typeParameter);
        if (evaluateDown != typeParameter) {
            typeParameter.replace(evaluateDown);
            return;
        }
        typeParameter.childrenAccept(this);
        final TypeParameter evaluateUp = this.evaluateUp(typeParameter);
        if (evaluateUp != typeParameter) {
            typeParameter.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final TypeParameterList list) throws ParseTreeException {
        final TypeParameterList evaluateDown = this.evaluateDown(list);
        if (evaluateDown != list) {
            list.replace(evaluateDown);
            return;
        }
        list.childrenAccept(this);
        final TypeParameterList evaluateUp = this.evaluateUp(list);
        if (evaluateUp != list) {
            list.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final UnaryExpression unaryExpression) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(unaryExpression);
        if (evaluateDown != unaryExpression) {
            unaryExpression.replace(evaluateDown);
            return;
        }
        unaryExpression.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(unaryExpression);
        if (evaluateUp != unaryExpression) {
            unaryExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final Variable variable) throws ParseTreeException {
        final Expression evaluateDown = this.evaluateDown(variable);
        if (evaluateDown != variable) {
            variable.replace(evaluateDown);
            return;
        }
        variable.childrenAccept(this);
        final Expression evaluateUp = this.evaluateUp(variable);
        if (evaluateUp != variable) {
            variable.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final VariableDeclaration variableDeclaration) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(variableDeclaration);
        if (evaluateDown != variableDeclaration) {
            variableDeclaration.replace(evaluateDown);
            return;
        }
        variableDeclaration.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(variableDeclaration);
        if (evaluateUp != variableDeclaration) {
            variableDeclaration.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final VariableDeclarator variableDeclarator) throws ParseTreeException {
        final VariableDeclarator evaluateDown = this.evaluateDown(variableDeclarator);
        if (evaluateDown != variableDeclarator) {
            variableDeclarator.replace(evaluateDown);
            return;
        }
        variableDeclarator.childrenAccept(this);
        final VariableDeclarator evaluateUp = this.evaluateUp(variableDeclarator);
        if (evaluateUp != variableDeclarator) {
            variableDeclarator.replace(evaluateUp);
        }
    }
    
    @Override
    public void visit(final WhileStatement whileStatement) throws ParseTreeException {
        final Statement evaluateDown = this.evaluateDown(whileStatement);
        if (evaluateDown != whileStatement) {
            whileStatement.replace(evaluateDown);
            return;
        }
        whileStatement.childrenAccept(this);
        final Statement evaluateUp = this.evaluateUp(whileStatement);
        if (evaluateUp != whileStatement) {
            whileStatement.replace(evaluateUp);
        }
    }
}

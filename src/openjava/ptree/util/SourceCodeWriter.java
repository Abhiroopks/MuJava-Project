// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import java.io.Writer;
import java.io.StringWriter;
import openjava.ptree.TypeParameter;
import java.util.Enumeration;
import openjava.ptree.WhileStatement;
import openjava.ptree.VariableDeclaration;
import openjava.ptree.TryStatement;
import openjava.ptree.ThrowStatement;
import openjava.ptree.SynchronizedStatement;
import openjava.ptree.SwitchStatement;
import openjava.ptree.SelfAccess;
import openjava.ptree.ReturnStatement;
import openjava.ptree.Parameter;
import openjava.ptree.TypeParameterList;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.MemberInitializer;
import openjava.ptree.Literal;
import openjava.ptree.LabeledStatement;
import openjava.ptree.AssertStatement;
import openjava.ptree.IfStatement;
import openjava.ptree.VariableDeclarator;
import openjava.ptree.ForStatement;
import openjava.ptree.VariableInitializer;
import openjava.ptree.FieldDeclaration;
import openjava.ptree.ExpressionStatement;
import openjava.ptree.EnumConstant;
import openjava.ptree.EnumDeclaration;
import openjava.ptree.EmptyStatement;
import openjava.ptree.DoWhileStatement;
import openjava.ptree.ContinueStatement;
import openjava.ptree.ConstructorInvocation;
import openjava.ptree.ParameterList;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ClassLiteral;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.EnumConstantList;
import openjava.ptree.TypeName;
import openjava.ptree.ModifierList;
import openjava.ptree.NonLeaf;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CatchList;
import openjava.ptree.CatchBlock;
import openjava.ptree.UnaryExpression;
import openjava.ptree.InstanceofExpression;
import openjava.ptree.ConditionalExpression;
import openjava.ptree.CastExpression;
import openjava.ptree.CaseLabelList;
import openjava.ptree.CaseLabel;
import openjava.ptree.CaseGroupList;
import openjava.ptree.CaseGroup;
import openjava.ptree.BreakStatement;
import openjava.ptree.StatementList;
import openjava.ptree.Block;
import openjava.ptree.BinaryExpression;
import openjava.ptree.AssignmentExpression;
import openjava.ptree.List;
import openjava.ptree.ArrayInitializer;
import openjava.ptree.ExpressionList;
import openjava.ptree.ArrayAllocationExpression;
import openjava.ptree.Variable;
import openjava.ptree.MethodCall;
import openjava.ptree.FieldAccess;
import openjava.ptree.Leaf;
import openjava.ptree.ArrayAccess;
import openjava.ptree.ParseTreeException;
import openjava.ptree.MemberDeclarationList;
import openjava.ptree.Expression;
import openjava.ptree.AllocationExpression;
import openjava.ptree.ParseTree;
import java.io.PrintWriter;

public class SourceCodeWriter extends ParseTreeVisitor
{
    protected PrintWriter out;
    public static String NEWLINE;
    private int debugLevel;
    private String tab;
    private int nest;
    
    public void setDebugLevel(final int debugLevel) {
        this.debugLevel = debugLevel;
    }
    
    public int getDebugLevel() {
        return this.debugLevel;
    }
    
    public void setTab(final String tab) {
        this.tab = tab;
    }
    
    public String getTab() {
        return this.tab;
    }
    
    public void setNest(final int nest) {
        this.nest = nest;
    }
    
    public int getNest() {
        return this.nest;
    }
    
    public void pushNest() {
        this.setNest(this.getNest() + 1);
    }
    
    public void popNest() {
        this.setNest(this.getNest() - 1);
    }
    
    private final void writeDebugL(final ParseTree parseTree) {
        if (this.getDebugLevel() > 0) {
            this.out.print("[");
            if (this.debugLevel > 1) {
                final String name = parseTree.getClass().getName();
                this.out.print(name.substring(name.lastIndexOf(46) + 1) + "#");
                if (this.debugLevel > 2) {
                    this.out.print(parseTree.getObjectID());
                }
                this.out.print(" ");
            }
        }
    }
    
    private final void writeDebugR() {
        if (this.getDebugLevel() > 0) {
            this.out.print("]");
        }
    }
    
    private final void writeDebugLR() {
        if (this.getDebugLevel() > 0) {
            this.out.print("[]");
        }
    }
    
    private final void writeDebugLln() {
        if (this.getDebugLevel() > 0) {
            this.out.println("[");
        }
    }
    
    private final void writeDebugRln() {
        if (this.getDebugLevel() > 0) {
            this.out.println("]");
        }
    }
    
    private final void writeDebugln() {
        if (this.getDebugLevel() > 0) {
            this.out.println();
        }
    }
    
    private final void writeDebug(final String s) {
        if (this.getDebugLevel() > 0) {
            this.out.print(s);
        }
    }
    
    private final void writeTab() {
        for (int i = 0; i < this.nest; ++i) {
            this.out.print(this.getTab());
        }
    }
    
    public SourceCodeWriter(final PrintWriter out) {
        this.debugLevel = 0;
        this.tab = "    ";
        this.nest = 0;
        this.out = out;
    }
    
    @Override
    public void visit(final AllocationExpression allocationExpression) throws ParseTreeException {
        this.writeDebugL(allocationExpression);
        final Expression encloser = allocationExpression.getEncloser();
        if (encloser != null) {
            encloser.accept(this);
            this.out.print(" . ");
        }
        this.out.print("new ");
        allocationExpression.getClassType().accept(this);
        this.writeArguments(allocationExpression.getArguments());
        final MemberDeclarationList classBody = allocationExpression.getClassBody();
        if (classBody != null) {
            this.out.println("{");
            this.pushNest();
            classBody.accept(this);
            this.popNest();
            this.writeTab();
            this.out.print("}");
        }
        this.writeDebugR();
    }
    
    @Override
    public void visit(final ArrayAccess arrayAccess) throws ParseTreeException {
        this.writeDebugL(arrayAccess);
        final Expression referenceExpr = arrayAccess.getReferenceExpr();
        if (referenceExpr instanceof Leaf || referenceExpr instanceof ArrayAccess || referenceExpr instanceof FieldAccess || referenceExpr instanceof MethodCall || referenceExpr instanceof Variable) {
            referenceExpr.accept(this);
        }
        else {
            this.writeParenthesis(referenceExpr);
        }
        final Expression indexExpr = arrayAccess.getIndexExpr();
        this.out.print("[");
        indexExpr.accept(this);
        this.out.print("]");
        this.writeDebugR();
    }
    
    @Override
    public void visit(final ArrayAllocationExpression arrayAllocationExpression) throws ParseTreeException {
        this.writeDebugL(arrayAllocationExpression);
        this.out.print("new ");
        arrayAllocationExpression.getTypeName().accept(this);
        final ExpressionList dimExprList = arrayAllocationExpression.getDimExprList();
        for (int i = 0; i < dimExprList.size(); ++i) {
            final Expression value = dimExprList.get(i);
            this.out.print("[");
            if (value != null) {
                value.accept(this);
            }
            this.out.print("]");
        }
        final ArrayInitializer initializer = arrayAllocationExpression.getInitializer();
        if (initializer != null) {
            initializer.accept(this);
        }
        this.writeDebugR();
    }
    
    @Override
    public void visit(final ArrayInitializer arrayInitializer) throws ParseTreeException {
        this.writeDebugL(arrayInitializer);
        this.out.print("{ ");
        this.writeListWithDelimiter(arrayInitializer, ", ");
        if (arrayInitializer.isRemainderOmitted()) {
            this.out.print(",");
        }
        this.out.print(" }");
        this.writeDebugR();
    }
    
    @Override
    public void visit(final AssignmentExpression assignmentExpression) throws ParseTreeException {
        this.writeDebugL(assignmentExpression);
        final Expression left = assignmentExpression.getLeft();
        if (left instanceof AssignmentExpression) {
            this.writeParenthesis(left);
        }
        else {
            left.accept(this);
        }
        this.out.print(" " + assignmentExpression.operatorString() + " ");
        assignmentExpression.getRight().accept(this);
        this.writeDebugR();
    }
    
    @Override
    public void visit(final BinaryExpression binaryExpression) throws ParseTreeException {
        this.writeDebugL(binaryExpression);
        final Expression left = binaryExpression.getLeft();
        if (isOperatorNeededLeftPar(binaryExpression.getOperator(), left)) {
            this.writeParenthesis(left);
        }
        else {
            left.accept(this);
        }
        this.out.print(" " + binaryExpression.operatorString() + " ");
        final Expression right = binaryExpression.getRight();
        if (isOperatorNeededRightPar(binaryExpression.getOperator(), right)) {
            this.writeParenthesis(right);
        }
        else {
            right.accept(this);
        }
        this.writeDebugR();
    }
    
    @Override
    public void visit(final Block block) throws ParseTreeException {
        final StatementList statements = block.getStatements();
        this.writeTab();
        this.writeDebugL(block);
        this.writeStatementsBlock(statements);
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final BreakStatement breakStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(breakStatement);
        this.out.print("break");
        final String label = breakStatement.getLabel();
        if (label != null) {
            this.out.print(" ");
            this.out.print(label);
        }
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final CaseGroup caseGroup) throws ParseTreeException {
        final ExpressionList labels = caseGroup.getLabels();
        for (int i = 0; i < labels.size(); ++i) {
            this.writeTab();
            final Expression value = labels.get(i);
            if (value == null) {
                this.out.print("default ");
            }
            else {
                this.out.print("case ");
                value.accept(this);
            }
            this.out.println(" :");
        }
        this.pushNest();
        caseGroup.getStatements().accept(this);
        this.popNest();
    }
    
    @Override
    public void visit(final CaseGroupList list) throws ParseTreeException {
        this.writeListWithSuffix(list, SourceCodeWriter.NEWLINE);
    }
    
    @Override
    public void visit(final CaseLabel caseLabel) throws ParseTreeException {
        final Expression expression = caseLabel.getExpression();
        if (expression != null) {
            this.out.print("case ");
            expression.accept(this);
        }
        else {
            this.out.print("default");
        }
        this.out.print(":");
    }
    
    @Override
    public void visit(final CaseLabelList list) throws ParseTreeException {
        this.writeListWithSuffix(list, SourceCodeWriter.NEWLINE);
    }
    
    @Override
    public void visit(final CastExpression castExpression) throws ParseTreeException {
        this.writeDebugL(castExpression);
        this.out.print("(");
        castExpression.getTypeSpecifier().accept(this);
        this.out.print(")");
        this.out.print(" ");
        final Expression expression = castExpression.getExpression();
        if (expression instanceof AssignmentExpression || expression instanceof ConditionalExpression || expression instanceof BinaryExpression || expression instanceof InstanceofExpression || expression instanceof UnaryExpression) {
            this.writeParenthesis(expression);
        }
        else {
            expression.accept(this);
        }
        this.writeDebugR();
    }
    
    @Override
    public void visit(final CatchBlock catchBlock) throws ParseTreeException {
        this.out.print(" catch ");
        this.out.print("( ");
        catchBlock.getParameter().accept(this);
        this.out.print(" ) ");
        this.writeStatementsBlock(catchBlock.getBody());
    }
    
    @Override
    public void visit(final CatchList list) throws ParseTreeException {
        this.writeList(list);
    }
    
    @Override
    public void visit(final ClassDeclaration classDeclaration) throws ParseTreeException {
        this.printComment(classDeclaration);
        this.writeTab();
        this.writeDebugL(classDeclaration);
        if (classDeclaration.isEnumeration()) {
            final ModifierList modifiers = classDeclaration.getModifiers();
            if (modifiers != null) {
                modifiers.accept(this);
                if (!modifiers.isEmptyAsRegular()) {
                    this.out.print(" ");
                }
            }
            this.out.print("enum ");
            this.out.print(classDeclaration.getName());
            final TypeName[] interfaces = classDeclaration.getInterfaces();
            if (interfaces != null) {
                if (interfaces.length != 0) {
                    this.out.print(" implements ");
                    interfaces[0].accept(this);
                    for (int i = 1; i < interfaces.length; ++i) {
                        this.out.print(", ");
                        interfaces[i].accept(this);
                    }
                }
                else {
                    this.writeDebug(" ");
                    this.writeDebugLR();
                }
            }
            this.out.println();
            final EnumConstantList enumConstants = classDeclaration.getEnumConstants();
            final MemberDeclarationList body = classDeclaration.getBody();
            this.out.print("{");
            if (enumConstants != null) {
                enumConstants.accept(this);
            }
            if (body != null) {
                this.out.print("; ");
                body.accept(this);
            }
            this.writeTab();
            this.out.print("}");
        }
        else {
            final ModifierList modifiers2 = classDeclaration.getModifiers();
            if (modifiers2 != null) {
                modifiers2.accept(this);
                if (!modifiers2.isEmptyAsRegular()) {
                    this.out.print(" ");
                }
            }
            if (classDeclaration.isInterface()) {
                this.out.print("interface ");
            }
            else {
                this.out.print("class ");
            }
            this.out.print(classDeclaration.getName());
            if (classDeclaration.getTypeParameters() != null) {
                this.out.println(classDeclaration.getTypeParameters().toString());
            }
            final TypeName[] baseclasses = classDeclaration.getBaseclasses();
            if (baseclasses.length != 0) {
                this.out.print(" extends ");
                baseclasses[0].accept(this);
                for (int j = 1; j < baseclasses.length; ++j) {
                    this.out.print(", ");
                    baseclasses[j].accept(this);
                }
            }
            else {
                this.writeDebug(" ");
                this.writeDebugLR();
            }
            final TypeName[] interfaces2 = classDeclaration.getInterfaces();
            if (interfaces2.length != 0) {
                this.out.print(" implements ");
                interfaces2[0].accept(this);
                for (int k = 1; k < interfaces2.length; ++k) {
                    this.out.print(", ");
                    interfaces2[k].accept(this);
                }
            }
            else {
                this.writeDebug(" ");
                this.writeDebugLR();
            }
            this.out.println();
            final MemberDeclarationList body2 = classDeclaration.getBody();
            this.writeTab();
            this.out.println("{");
            if (body2.isEmpty()) {
                body2.accept(this);
            }
            else {
                this.out.println();
                this.pushNest();
                body2.accept(this);
                this.popNest();
                this.out.println();
            }
            this.writeTab();
            this.out.print("}");
        }
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final ClassDeclarationList list) throws ParseTreeException {
        this.writeListWithDelimiter(list, SourceCodeWriter.NEWLINE + SourceCodeWriter.NEWLINE);
    }
    
    @Override
    public void visit(final ClassLiteral classLiteral) throws ParseTreeException {
        this.writeDebugL(classLiteral);
        classLiteral.getTypeName().accept(this);
        this.out.print(".class");
        this.writeDebugR();
    }
    
    @Override
    public void visit(final CompilationUnit compilationUnit) throws ParseTreeException {
        this.out.println("/*");
        this.out.println(" * This code was generated by ojc.");
        this.out.println(" */");
        this.printComment(compilationUnit);
        final String package1 = compilationUnit.getPackage();
        if (package1 != null) {
            this.writeDebugL(compilationUnit);
            this.out.print("package " + package1 + ";");
            this.writeDebugR();
            this.out.println();
            this.out.println();
            this.out.println();
        }
        final String[] declaredImports = compilationUnit.getDeclaredImports();
        if (declaredImports.length != 0) {
            for (int i = 0; i < declaredImports.length; ++i) {
                this.out.println("import " + declaredImports[i] + ";");
            }
            this.out.println();
            this.out.println();
        }
        compilationUnit.getClassDeclarations().accept(this);
    }
    
    @Override
    public void visit(final ConditionalExpression conditionalExpression) throws ParseTreeException {
        this.writeDebugL(conditionalExpression);
        final Expression condition = conditionalExpression.getCondition();
        if (condition instanceof AssignmentExpression || condition instanceof ConditionalExpression) {
            this.writeParenthesis(condition);
        }
        else {
            condition.accept(this);
        }
        this.out.print(" ? ");
        final Expression trueCase = conditionalExpression.getTrueCase();
        if (trueCase instanceof AssignmentExpression) {
            this.writeParenthesis(trueCase);
        }
        else {
            trueCase.accept(this);
        }
        this.out.print(" : ");
        final Expression falseCase = conditionalExpression.getFalseCase();
        if (falseCase instanceof AssignmentExpression) {
            this.writeParenthesis(falseCase);
        }
        else {
            falseCase.accept(this);
        }
        this.writeDebugR();
    }
    
    @Override
    public void visit(final ConstructorDeclaration constructorDeclaration) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(constructorDeclaration);
        final ModifierList modifiers = constructorDeclaration.getModifiers();
        if (modifiers != null) {
            modifiers.accept(this);
            if (!modifiers.isEmptyAsRegular()) {
                this.out.print(" ");
            }
        }
        this.out.print(constructorDeclaration.getName());
        final ParameterList parameters = constructorDeclaration.getParameters();
        this.out.print("(");
        if (parameters.size() != 0) {
            this.out.print(" ");
            parameters.accept(this);
            this.out.print(" ");
        }
        this.out.print(")");
        final TypeName[] throws1 = constructorDeclaration.getThrows();
        if (throws1.length != 0) {
            this.out.println();
            this.writeTab();
            this.writeTab();
            this.out.print("throws ");
            throws1[0].accept(this);
            for (int i = 1; i < throws1.length; ++i) {
                this.out.print(", ");
                throws1[i].accept(this);
            }
        }
        final ConstructorInvocation constructorInvocation = constructorDeclaration.getConstructorInvocation();
        final StatementList body = constructorDeclaration.getBody();
        if (body == null && constructorInvocation == null) {
            this.out.println(";");
        }
        else {
            this.out.println();
            this.writeTab();
            this.out.println("{");
            this.pushNest();
            if (constructorInvocation != null) {
                constructorInvocation.accept(this);
            }
            if (body != null) {
                body.accept(this);
            }
            this.popNest();
            this.writeTab();
            this.out.print("}");
        }
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final ConstructorInvocation constructorInvocation) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(constructorInvocation);
        if (constructorInvocation.isSelfInvocation()) {
            this.out.print("this");
        }
        else {
            final Expression enclosing = constructorInvocation.getEnclosing();
            if (enclosing != null) {
                enclosing.accept(this);
                this.out.print(" . ");
            }
            this.out.print("super");
        }
        this.writeArguments(constructorInvocation.getArguments());
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final ContinueStatement continueStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(continueStatement);
        this.out.print("continue");
        final String label = continueStatement.getLabel();
        if (label != null) {
            this.out.print(" " + label);
        }
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final DoWhileStatement doWhileStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(doWhileStatement);
        this.out.print("do ");
        final StatementList statements = doWhileStatement.getStatements();
        if (statements.isEmpty()) {
            this.out.print(" ; ");
        }
        else {
            this.writeStatementsBlock(statements);
        }
        this.out.print(" while ");
        this.out.print("(");
        doWhileStatement.getExpression().accept(this);
        this.out.print(")");
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final EmptyStatement emptyStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(emptyStatement);
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final EnumDeclaration enumDeclaration) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(enumDeclaration);
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final EnumConstant enumConstant) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(enumConstant);
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final EnumConstantList list) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(list);
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final ExpressionList list) throws ParseTreeException {
        this.writeListWithDelimiter(list, ", ");
    }
    
    @Override
    public void visit(final ExpressionStatement expressionStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(expressionStatement);
        expressionStatement.getExpression().accept(this);
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final FieldAccess fieldAccess) throws ParseTreeException {
        this.writeDebugL(fieldAccess);
        final Expression referenceExpr = fieldAccess.getReferenceExpr();
        final TypeName referenceType = fieldAccess.getReferenceType();
        if (referenceExpr != null) {
            if (referenceExpr instanceof Leaf || referenceExpr instanceof ArrayAccess || referenceExpr instanceof FieldAccess || referenceExpr instanceof MethodCall || referenceExpr instanceof Variable) {
                referenceExpr.accept(this);
            }
            else {
                this.out.print("(");
                referenceExpr.accept(this);
                this.out.print(")");
            }
            this.out.print(".");
        }
        else if (referenceType != null) {
            referenceType.accept(this);
            this.out.print(".");
        }
        this.out.print(fieldAccess.getName());
        this.writeDebugR();
    }
    
    @Override
    public void visit(final FieldDeclaration fieldDeclaration) throws ParseTreeException {
        this.printComment(fieldDeclaration);
        this.writeTab();
        this.writeDebugL(fieldDeclaration);
        final ModifierList modifiers = fieldDeclaration.getModifiers();
        if (modifiers != null) {
            modifiers.accept(this);
            if (!modifiers.isEmptyAsRegular()) {
                this.out.print(" ");
            }
        }
        fieldDeclaration.getTypeSpecifier().accept(this);
        this.out.print(" ");
        this.out.print(fieldDeclaration.getVariable());
        final VariableInitializer initializer = fieldDeclaration.getInitializer();
        if (initializer != null) {
            this.out.print(" = ");
            initializer.accept(this);
        }
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final ForStatement forStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(forStatement);
        this.out.print("for ");
        this.out.print("(");
        final ExpressionList init = forStatement.getInit();
        final TypeName initDeclType = forStatement.getInitDeclType();
        final VariableDeclarator[] initDecls = forStatement.getInitDecls();
        if (init != null && !init.isEmpty()) {
            init.get(0).accept(this);
            for (int i = 1; i < init.size(); ++i) {
                this.out.print(", ");
                init.get(i).accept(this);
            }
        }
        else if (initDeclType != null && initDecls != null && initDecls.length != 0) {
            initDeclType.accept(this);
            this.out.print(" ");
            initDecls[0].accept(this);
            for (int j = 1; j < initDecls.length; ++j) {
                this.out.print(", ");
                initDecls[j].accept(this);
            }
        }
        this.out.print(";");
        final Expression condition = forStatement.getCondition();
        if (condition != null) {
            this.out.print(" ");
            condition.accept(this);
        }
        this.out.print(";");
        final ExpressionList increment = forStatement.getIncrement();
        if (increment != null && !increment.isEmpty()) {
            this.out.print(" ");
            increment.get(0).accept(this);
            for (int k = 1; k < increment.size(); ++k) {
                this.out.print(", ");
                increment.get(k).accept(this);
            }
        }
        this.out.print(") ");
        final StatementList statements = forStatement.getStatements();
        if (statements.isEmpty()) {
            this.out.print(";");
        }
        else {
            this.writeStatementsBlock(statements);
        }
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final IfStatement ifStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(ifStatement);
        this.out.print("if ");
        this.out.print("(");
        ifStatement.getExpression().accept(this);
        this.out.print(") ");
        this.writeStatementsBlock(ifStatement.getStatements());
        final StatementList elseStatements = ifStatement.getElseStatements();
        if (!elseStatements.isEmpty()) {
            this.out.print(" else ");
            this.writeStatementsBlock(elseStatements);
        }
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final AssertStatement assertStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(assertStatement);
        this.out.print(" assert ");
        assertStatement.getExpression().accept(this);
        final Expression expression2 = assertStatement.getExpression2();
        if (expression2 != null) {
            this.out.print(" : ");
            expression2.accept(this);
        }
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final InstanceofExpression instanceofExpression) throws ParseTreeException {
        this.writeDebugL(instanceofExpression);
        final Expression expression = instanceofExpression.getExpression();
        if (expression instanceof AssignmentExpression || expression instanceof ConditionalExpression || expression instanceof BinaryExpression) {
            this.writeParenthesis(expression);
        }
        else {
            expression.accept(this);
        }
        this.out.print(" instanceof ");
        instanceofExpression.getTypeSpecifier().accept(this);
        this.writeDebugR();
    }
    
    @Override
    public void visit(final LabeledStatement labeledStatement) throws ParseTreeException {
        this.writeTab();
        this.out.print(labeledStatement.getLabel());
        this.out.println(" : ");
        labeledStatement.getStatement().accept(this);
    }
    
    @Override
    public void visit(final Literal literal) throws ParseTreeException {
        this.out.print(literal.toString());
    }
    
    @Override
    public void visit(final MemberDeclarationList list) throws ParseTreeException {
        this.writeListWithDelimiter(list, SourceCodeWriter.NEWLINE);
    }
    
    @Override
    public void visit(final MemberInitializer memberInitializer) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(memberInitializer);
        if (memberInitializer.isStatic()) {
            this.out.print("static ");
        }
        this.writeStatementsBlock(memberInitializer.getBody());
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final MethodCall methodCall) throws ParseTreeException {
        this.writeDebugL(methodCall);
        final Expression referenceExpr = methodCall.getReferenceExpr();
        final TypeName referenceType = methodCall.getReferenceType();
        if (referenceExpr != null) {
            if (referenceExpr instanceof Leaf || referenceExpr instanceof ArrayAccess || referenceExpr instanceof FieldAccess || referenceExpr instanceof MethodCall || referenceExpr instanceof Variable) {
                referenceExpr.accept(this);
            }
            else {
                this.writeParenthesis(referenceExpr);
            }
            this.out.print(".");
        }
        else if (referenceType != null) {
            referenceType.accept(this);
            this.out.print(".");
        }
        this.out.print(methodCall.getName());
        this.writeArguments(methodCall.getArguments());
        this.writeDebugR();
    }
    
    @Override
    public void visit(final MethodDeclaration methodDeclaration) throws ParseTreeException {
        this.printComment(methodDeclaration);
        this.writeTab();
        this.writeDebugL(methodDeclaration);
        final ModifierList modifiers = methodDeclaration.getModifiers();
        if (modifiers != null) {
            modifiers.accept(this);
            if (!modifiers.isEmptyAsRegular()) {
                this.out.print(" ");
            }
        }
        final TypeParameterList typeParameterList = methodDeclaration.getTypeParameterList();
        if (typeParameterList != null) {
            typeParameterList.accept(this);
        }
        this.out.print(" ");
        methodDeclaration.getReturnType().accept(this);
        this.out.print(" ");
        this.out.print(methodDeclaration.getName());
        final ParameterList parameters = methodDeclaration.getParameters();
        this.out.print("(");
        if (!parameters.isEmpty()) {
            this.out.print(" ");
            parameters.accept(this);
            this.out.print(" ");
        }
        else {
            parameters.accept(this);
        }
        this.out.print(")");
        final TypeName[] throws1 = methodDeclaration.getThrows();
        if (throws1.length != 0) {
            this.out.println();
            this.writeTab();
            this.writeTab();
            this.out.print("throws ");
            throws1[0].accept(this);
            for (int i = 1; i < throws1.length; ++i) {
                this.out.print(", ");
                throws1[i].accept(this);
            }
        }
        final StatementList body = methodDeclaration.getBody();
        if (body == null) {
            this.out.print(";");
        }
        else {
            this.out.println();
            this.writeTab();
            this.out.print("{");
            if (body.isEmpty()) {
                body.accept(this);
            }
            else {
                this.out.println();
                this.pushNest();
                body.accept(this);
                this.popNest();
                this.writeTab();
            }
            this.out.print("}");
        }
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final ModifierList list) throws ParseTreeException {
        this.writeDebugL(list);
        this.out.print(ModifierList.toString(list.getRegular()));
        this.writeDebugR();
    }
    
    @Override
    public void visit(final Parameter parameter) throws ParseTreeException {
        this.writeDebugL(parameter);
        final ModifierList modifiers = parameter.getModifiers();
        modifiers.accept(this);
        if (!modifiers.isEmptyAsRegular()) {
            this.out.print(" ");
        }
        parameter.getTypeSpecifier().accept(this);
        this.out.print(" ");
        this.out.print(parameter.getVariable());
        this.writeDebugR();
    }
    
    @Override
    public void visit(final ParameterList list) throws ParseTreeException {
        this.writeListWithDelimiter(list, ", ");
    }
    
    @Override
    public void visit(final ReturnStatement returnStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(returnStatement);
        this.out.print("return");
        final Expression expression = returnStatement.getExpression();
        if (expression != null) {
            this.out.print(" ");
            expression.accept(this);
        }
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final SelfAccess selfAccess) throws ParseTreeException {
        this.out.print(selfAccess.toString());
    }
    
    @Override
    public void visit(final StatementList list) throws ParseTreeException {
        this.writeList(list);
    }
    
    @Override
    public void visit(final SwitchStatement switchStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(switchStatement);
        this.out.print("switch ");
        this.out.print("(");
        switchStatement.getExpression().accept(this);
        this.out.print(")");
        this.out.println(" {");
        switchStatement.getCaseGroupList().accept(this);
        this.writeTab();
        this.out.print("}");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final SynchronizedStatement synchronizedStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(synchronizedStatement);
        this.out.print("synchronized ");
        this.out.print("(");
        synchronizedStatement.getExpression().accept(this);
        this.out.println(")");
        this.writeStatementsBlock(synchronizedStatement.getStatements());
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final ThrowStatement throwStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(throwStatement);
        this.out.print("throw ");
        throwStatement.getExpression().accept(this);
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final TryStatement tryStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(tryStatement);
        this.out.print("try ");
        this.writeStatementsBlock(tryStatement.getBody());
        final CatchList catchList = tryStatement.getCatchList();
        if (!catchList.isEmpty()) {
            catchList.accept(this);
        }
        final StatementList finallyBody = tryStatement.getFinallyBody();
        if (!finallyBody.isEmpty()) {
            this.out.println(" finally ");
            this.writeStatementsBlock(finallyBody);
        }
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final TypeName typeName) throws ParseTreeException {
        this.writeDebugL(typeName);
        this.out.print(typeName.getName().replace('$', '.'));
        this.out.print(TypeName.stringFromDimension(typeName.getDimension()));
        this.writeDebugR();
    }
    
    @Override
    public void visit(final UnaryExpression unaryExpression) throws ParseTreeException {
        this.writeDebugL(unaryExpression);
        if (unaryExpression.isPrefix()) {
            this.out.print(unaryExpression.operatorString());
        }
        final Expression expression = unaryExpression.getExpression();
        if (expression instanceof AssignmentExpression || expression instanceof ConditionalExpression || expression instanceof BinaryExpression || expression instanceof InstanceofExpression || expression instanceof CastExpression || expression instanceof UnaryExpression) {
            this.writeParenthesis(expression);
        }
        else {
            expression.accept(this);
        }
        if (unaryExpression.isPostfix()) {
            this.out.print(unaryExpression.operatorString());
        }
        this.writeDebugR();
    }
    
    @Override
    public void visit(final Variable variable) throws ParseTreeException {
        this.out.print(variable.toString());
    }
    
    @Override
    public void visit(final VariableDeclaration variableDeclaration) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(variableDeclaration);
        final ModifierList modifiers = variableDeclaration.getModifiers();
        modifiers.accept(this);
        if (!modifiers.isEmptyAsRegular()) {
            this.out.print(" ");
        }
        variableDeclaration.getTypeSpecifier().accept(this);
        this.out.print(" ");
        variableDeclaration.getVariableDeclarator().accept(this);
        this.out.print(";");
        this.writeDebugR();
        this.out.println();
    }
    
    @Override
    public void visit(final VariableDeclarator variableDeclarator) throws ParseTreeException {
        this.out.print(variableDeclarator.getVariable());
        for (int i = 0; i < variableDeclarator.getDimension(); ++i) {
            this.out.print("[]");
        }
        final VariableInitializer initializer = variableDeclarator.getInitializer();
        if (initializer != null) {
            this.out.print(" = ");
            initializer.accept(this);
        }
    }
    
    @Override
    public void visit(final WhileStatement whileStatement) throws ParseTreeException {
        this.writeTab();
        this.writeDebugL(whileStatement);
        this.out.print("while ");
        this.out.print("(");
        whileStatement.getExpression().accept(this);
        this.out.print(") ");
        final StatementList statements = whileStatement.getStatements();
        if (statements.isEmpty()) {
            this.out.print(" ;");
        }
        else {
            this.writeStatementsBlock(statements);
        }
        this.writeDebugR();
        this.out.println();
    }
    
    private final void writeArguments(final ExpressionList list) throws ParseTreeException {
        this.out.print("(");
        if (!list.isEmpty()) {
            this.out.print(" ");
            list.accept(this);
            this.out.print(" ");
        }
        else {
            list.accept(this);
        }
        this.out.print(")");
    }
    
    private final void writeAnonymous(final Object o) throws ParseTreeException {
        if (o == null) {
            this.writeDebug("#null");
        }
        else if (o instanceof ParseTree) {
            ((ParseTree)o).accept(this);
        }
        else {
            this.out.print(o.toString());
        }
    }
    
    private final void writeList(final List list) throws ParseTreeException {
        final Enumeration elements = list.elements();
        while (elements.hasMoreElements()) {
            this.writeAnonymous(elements.nextElement());
        }
    }
    
    private final void writeListWithDelimiter(final List list, final String s) throws ParseTreeException {
        final Enumeration elements = list.elements();
        if (!elements.hasMoreElements()) {
            return;
        }
        this.writeAnonymous(elements.nextElement());
        while (elements.hasMoreElements()) {
            this.out.print(s);
            this.writeAnonymous(elements.nextElement());
        }
    }
    
    private final void writeListWithSuffix(final List list, final String s) throws ParseTreeException {
        final Enumeration elements = list.elements();
        while (elements.hasMoreElements()) {
            this.writeAnonymous(elements.nextElement());
            this.out.print(s);
        }
    }
    
    private final void writeParenthesis(final Expression expression) throws ParseTreeException {
        this.out.print("(");
        expression.accept(this);
        this.out.print(")");
    }
    
    private final void writeStatementsBlock(final StatementList list) throws ParseTreeException {
        this.out.println("{");
        this.pushNest();
        list.accept(this);
        this.popNest();
        this.writeTab();
        this.out.print("}");
    }
    
    private static final boolean isOperatorNeededLeftPar(final int n, final Expression expression) {
        if (expression instanceof AssignmentExpression || expression instanceof ConditionalExpression) {
            return true;
        }
        final int operatorStrength = operatorStrength(n);
        if (expression instanceof InstanceofExpression) {
            return operatorStrength > operatorStrength(12);
        }
        return expression instanceof BinaryExpression && operatorStrength > operatorStrength(((BinaryExpression)expression).getOperator());
    }
    
    private static final boolean isOperatorNeededRightPar(final int n, final Expression expression) {
        if (expression instanceof AssignmentExpression || expression instanceof ConditionalExpression) {
            return true;
        }
        final int operatorStrength = operatorStrength(n);
        if (expression instanceof InstanceofExpression) {
            return operatorStrength >= operatorStrength(12);
        }
        return expression instanceof BinaryExpression && operatorStrength >= operatorStrength(((BinaryExpression)expression).getOperator());
    }
    
    private static final int operatorStrength(final int n) {
        switch (n) {
            case 0:
            case 1:
            case 2: {
                return 40;
            }
            case 3:
            case 4: {
                return 35;
            }
            case 5:
            case 6:
            case 7: {
                return 30;
            }
            case 8:
            case 9:
            case 10:
            case 11:
            case 12: {
                return 25;
            }
            case 13:
            case 14: {
                return 20;
            }
            case 15: {
                return 16;
            }
            case 16: {
                return 14;
            }
            case 17: {
                return 12;
            }
            case 18: {
                return 10;
            }
            case 19: {
                return 8;
            }
            default: {
                return 100;
            }
        }
    }
    
    private final void printComment(final NonLeaf nonLeaf) {
        final String comment = nonLeaf.getComment();
        if (comment != null) {
            this.writeTab();
            this.out.println(comment);
        }
    }
    
    @Override
    public void visit(final TypeParameterList list) throws ParseTreeException {
        this.writeTab();
        this.out.print("<");
        if (list != null) {
            this.writeListWithDelimiter(list, ",");
        }
        this.out.print(">");
    }
    
    @Override
    public void visit(final TypeParameter typeParameter) throws ParseTreeException {
        this.out.print(typeParameter.toString());
    }
    
    static {
        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.println();
        printWriter.close();
        SourceCodeWriter.NEWLINE = out.toString();
    }
}

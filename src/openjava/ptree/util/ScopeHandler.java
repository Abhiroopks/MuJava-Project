// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.mop.AnonymousClassEnvironment;
import openjava.ptree.Expression;
import openjava.ptree.AllocationExpression;
import openjava.ptree.SynchronizedStatement;
import openjava.ptree.TryStatement;
import openjava.ptree.ForStatement;
import openjava.ptree.DoWhileStatement;
import openjava.ptree.WhileStatement;
import openjava.ptree.AssertStatement;
import openjava.ptree.IfStatement;
import openjava.ptree.SwitchStatement;
import openjava.ptree.Statement;
import openjava.ptree.Block;
import openjava.ptree.MemberInitializer;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.MethodDeclaration;
import openjava.mop.OJClass;
import openjava.ptree.MemberDeclarationList;
import openjava.ptree.EnumDeclaration;
import openjava.mop.ClassEnvironment;
import openjava.ptree.MemberDeclaration;
import openjava.ptree.ParseTreeException;
import openjava.ptree.ClassDeclaration;
import openjava.mop.FileEnvironment;
import openjava.ptree.CompilationUnit;
import openjava.mop.ClosedEnvironment;
import openjava.mop.Environment;
import java.util.Stack;

public abstract class ScopeHandler extends EvaluationShuttle
{
    private Stack env_nest;
    
    public ScopeHandler(final Environment environment) {
        super(environment);
        this.env_nest = new Stack();
    }
    
    protected final void pushClosedEnvironment() {
        this.push(new ClosedEnvironment(this.getEnvironment()));
    }
    
    protected final void push(final Environment environment) {
        this.env_nest.push(this.getEnvironment());
        this.setEnvironment(environment);
    }
    
    protected final void pop() {
        this.setEnvironment((Environment) this.env_nest.pop());
    }
    
    @Override
    public CompilationUnit evaluateDown(final CompilationUnit compilationUnit) throws ParseTreeException {
        final ClassDeclaration publicClass = compilationUnit.getPublicClass();
        this.push(new FileEnvironment(this.getEnvironment(), compilationUnit, (publicClass != null) ? publicClass.getName() : "<no public class>"));
        return compilationUnit;
    }
    
    @Override
    public ClassDeclaration evaluateDown(final ClassDeclaration classDeclaration) throws ParseTreeException {
        if (this.getEnvironment() instanceof ClosedEnvironment) {
            this.recordLocalClass(classDeclaration);
        }
        final ClassEnvironment classEnvironment = new ClassEnvironment(this.getEnvironment(), classDeclaration.getName());
        final MemberDeclarationList body = classDeclaration.getBody();
        for (int i = 0; i < body.size(); ++i) {
            final MemberDeclaration value = body.get(i);
            if (value instanceof ClassDeclaration) {
                classEnvironment.recordMemberClass(((ClassDeclaration)value).getName());
            }
            else if (value instanceof EnumDeclaration) {
                classEnvironment.recordMemberClass(((EnumDeclaration)value).getName());
            }
        }
        this.push(classEnvironment);
        return classDeclaration;
    }
    
    @Override
    public MemberDeclaration evaluateDown(final EnumDeclaration enumDeclaration) throws ParseTreeException {
        if (this.getEnvironment() instanceof ClosedEnvironment) {
            this.recordLocalClass(enumDeclaration);
        }
        final ClassEnvironment classEnvironment = new ClassEnvironment(this.getEnvironment(), enumDeclaration.getName());
        final MemberDeclarationList classBodayDeclaration = enumDeclaration.getClassBodayDeclaration();
        if (classBodayDeclaration != null) {
            for (int i = 0; i < classBodayDeclaration.size(); ++i) {
                final MemberDeclaration value = classBodayDeclaration.get(i);
                if (value instanceof ClassDeclaration) {
                    classEnvironment.recordMemberClass(((ClassDeclaration)value).getName());
                }
                else if (value instanceof EnumDeclaration) {
                    classEnvironment.recordMemberClass(((EnumDeclaration)value).getName());
                }
            }
        }
        this.push(classEnvironment);
        return enumDeclaration;
    }
    
    private void recordLocalEnum(final EnumDeclaration enumDeclaration) {
        final String name = enumDeclaration.getName();
        final Environment environment = this.getEnvironment();
        if (environment.lookupClass(environment.toQualifiedName(name)) != null) {
            return;
        }
        try {
            environment.record(name, new OJClass(environment, environment.lookupClass(environment.currentClassName()), new ClassDeclaration(enumDeclaration)));
        }
        catch (Exception obj) {
            System.err.println("unknown error: " + obj);
        }
    }
    
    private void recordLocalClass(final MemberDeclaration memberDeclaration) {
        String s = "";
        if (memberDeclaration instanceof ClassDeclaration) {
            s = ((ClassDeclaration)memberDeclaration).getName();
        }
        else if (memberDeclaration instanceof EnumDeclaration) {
            s = ((EnumDeclaration)memberDeclaration).getName();
        }
        final Environment environment = this.getEnvironment();
        if (environment.lookupClass(environment.toQualifiedName(s)) != null) {
            return;
        }
        try {
            final OJClass lookupClass = environment.lookupClass(environment.currentClassName());
            OJClass ojClass = null;
            if (memberDeclaration instanceof ClassDeclaration) {
                ojClass = new OJClass(environment, lookupClass, (ClassDeclaration)memberDeclaration);
            }
            else if (memberDeclaration instanceof EnumDeclaration) {
                ojClass = new OJClass(environment, lookupClass, new ClassDeclaration(memberDeclaration));
            }
            environment.record(s, ojClass);
        }
        catch (Exception obj) {
            System.err.println("unknown error: " + obj);
        }
    }
    
    @Override
    public MemberDeclaration evaluateDown(final MethodDeclaration methodDeclaration) throws ParseTreeException {
        this.pushClosedEnvironment();
        return methodDeclaration;
    }
    
    @Override
    public MemberDeclaration evaluateDown(final ConstructorDeclaration constructorDeclaration) throws ParseTreeException {
        this.pushClosedEnvironment();
        return constructorDeclaration;
    }
    
    @Override
    public MemberDeclaration evaluateDown(final MemberInitializer memberInitializer) throws ParseTreeException {
        this.pushClosedEnvironment();
        return memberInitializer;
    }
    
    @Override
    public Statement evaluateDown(final Block block) throws ParseTreeException {
        this.pushClosedEnvironment();
        return block;
    }
    
    @Override
    public Statement evaluateDown(final SwitchStatement switchStatement) throws ParseTreeException {
        this.pushClosedEnvironment();
        return switchStatement;
    }
    
    @Override
    public Statement evaluateDown(final IfStatement ifStatement) throws ParseTreeException {
        this.pushClosedEnvironment();
        return ifStatement;
    }
    
    @Override
    public Statement evaluateDown(final AssertStatement assertStatement) throws ParseTreeException {
        this.pushClosedEnvironment();
        return assertStatement;
    }
    
    @Override
    public Statement evaluateDown(final WhileStatement whileStatement) throws ParseTreeException {
        this.pushClosedEnvironment();
        return whileStatement;
    }
    
    @Override
    public Statement evaluateDown(final DoWhileStatement doWhileStatement) throws ParseTreeException {
        this.pushClosedEnvironment();
        return doWhileStatement;
    }
    
    @Override
    public Statement evaluateDown(final ForStatement forStatement) throws ParseTreeException {
        this.pushClosedEnvironment();
        return forStatement;
    }
    
    @Override
    public Statement evaluateDown(final TryStatement tryStatement) throws ParseTreeException {
        this.pushClosedEnvironment();
        return tryStatement;
    }
    
    @Override
    public Statement evaluateDown(final SynchronizedStatement synchronizedStatement) throws ParseTreeException {
        this.pushClosedEnvironment();
        return synchronizedStatement;
    }
    
    @Override
    public Expression evaluateDown(final AllocationExpression allocationExpression) throws ParseTreeException {
        final MemberDeclarationList classBody = allocationExpression.getClassBody();
        if (classBody != null) {
            this.push(new AnonymousClassEnvironment(this.getEnvironment(), allocationExpression.getClassType().toString(), classBody));
        }
        else {
            this.pushClosedEnvironment();
        }
        return allocationExpression;
    }
    
    @Override
    public CompilationUnit evaluateUp(final CompilationUnit compilationUnit) throws ParseTreeException {
        this.pop();
        return compilationUnit;
    }
    
    @Override
    public ClassDeclaration evaluateUp(final ClassDeclaration classDeclaration) throws ParseTreeException {
        this.pop();
        return classDeclaration;
    }
    
    @Override
    public MemberDeclaration evaluateUp(final MethodDeclaration methodDeclaration) throws ParseTreeException {
        this.pop();
        return methodDeclaration;
    }
    
    @Override
    public MemberDeclaration evaluateUp(final ConstructorDeclaration constructorDeclaration) throws ParseTreeException {
        this.pop();
        return constructorDeclaration;
    }
    
    @Override
    public EnumDeclaration evaluateUp(final EnumDeclaration enumDeclaration) throws ParseTreeException {
        this.pop();
        return enumDeclaration;
    }
    
    @Override
    public MemberDeclaration evaluateUp(final MemberInitializer memberInitializer) throws ParseTreeException {
        this.pop();
        return memberInitializer;
    }
    
    @Override
    public Statement evaluateUp(final Block block) throws ParseTreeException {
        this.pop();
        return block;
    }
    
    @Override
    public Statement evaluateUp(final SwitchStatement switchStatement) throws ParseTreeException {
        this.pop();
        return switchStatement;
    }
    
    @Override
    public Statement evaluateUp(final IfStatement ifStatement) throws ParseTreeException {
        this.pop();
        return ifStatement;
    }
    
    @Override
    public Statement evaluateUp(final AssertStatement assertStatement) throws ParseTreeException {
        this.pop();
        return assertStatement;
    }
    
    @Override
    public Statement evaluateUp(final WhileStatement whileStatement) throws ParseTreeException {
        this.pop();
        return whileStatement;
    }
    
    @Override
    public Statement evaluateUp(final DoWhileStatement doWhileStatement) throws ParseTreeException {
        this.pop();
        return doWhileStatement;
    }
    
    @Override
    public Statement evaluateUp(final ForStatement forStatement) throws ParseTreeException {
        this.pop();
        return forStatement;
    }
    
    @Override
    public Statement evaluateUp(final TryStatement tryStatement) throws ParseTreeException {
        this.pop();
        return tryStatement;
    }
    
    @Override
    public Statement evaluateUp(final SynchronizedStatement synchronizedStatement) throws ParseTreeException {
        this.pop();
        return synchronizedStatement;
    }
    
    @Override
    public Expression evaluateUp(final AllocationExpression allocationExpression) throws ParseTreeException {
        this.pop();
        return allocationExpression;
    }
}

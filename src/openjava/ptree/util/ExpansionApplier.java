// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.ptree.Statement;
import openjava.ptree.VariableDeclaration;
import openjava.ptree.Variable;
import openjava.ptree.UnaryExpression;
import openjava.ptree.SelfAccess;
import openjava.ptree.MethodCall;
import openjava.ptree.Literal;
import openjava.ptree.InstanceofExpression;
import openjava.ptree.ConditionalExpression;
import openjava.ptree.ClassLiteral;
import openjava.ptree.CastExpression;
import openjava.ptree.BinaryExpression;
import openjava.ptree.ArrayAllocationExpression;
import openjava.ptree.AllocationExpression;
import openjava.ptree.ParseTree;
import openjava.ptree.FieldAccess;
import openjava.ptree.AssignmentExpression;
import openjava.ptree.TypeName;
import openjava.ptree.ArrayAccess;
import openjava.tools.DebugOut;
import openjava.ptree.ParseTreeException;
import openjava.mop.OJClass;
import openjava.ptree.Expression;
import openjava.mop.Environment;

public class ExpansionApplier extends VariableBinder
{
    public ExpansionApplier(final Environment environment) {
        super(environment);
    }
    
    private OJClass getType(final Expression expression) throws ParseTreeException {
        OJClass type;
        try {
            type = expression.getType(this.getEnvironment());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new ParseTreeException(ex);
        }
        DebugOut.println("type eval - " + expression + "\t: " + type);
        if (type == null) {
            System.err.println("cannot resolve the type of expression");
            System.err.println(expression.getClass() + " : " + expression);
            System.err.println(this.getEnvironment());
            if (expression instanceof ArrayAccess) {
                final Expression referenceExpr = ((ArrayAccess)expression).getReferenceExpr();
                OJClass type2 = null;
                Object componentType = null;
                try {
                    type2 = referenceExpr.getType(this.getEnvironment());
                    componentType = type2.getComponentType();
                }
                catch (Exception ex2) {}
                System.err.println(referenceExpr + " : " + type2 + " : " + componentType);
            }
        }
        return type;
    }
    
    private OJClass getSelfType() throws ParseTreeException {
        OJClass lookupClass;
        try {
            final Environment environment = this.getEnvironment();
            lookupClass = environment.lookupClass(environment.currentClassName());
        }
        catch (Exception ex) {
            throw new ParseTreeException(ex);
        }
        return lookupClass;
    }
    
    private OJClass getType(final TypeName obj) throws ParseTreeException {
        OJClass lookupClass;
        try {
            final Environment environment = this.getEnvironment();
            lookupClass = environment.lookupClass(environment.toQualifiedName(obj.toString()));
        }
        catch (Exception ex) {
            throw new ParseTreeException(ex);
        }
        DebugOut.println("type eval - class access : " + lookupClass);
        if (lookupClass == null) {
            System.err.println("unknown type for a type name : " + obj);
        }
        return lookupClass;
    }
    
    private OJClass computeRefType(final TypeName typeName, final Expression expression) throws ParseTreeException {
        if (typeName != null) {
            return this.getType(typeName);
        }
        if (expression != null) {
            return this.getType(expression);
        }
        return this.getSelfType();
    }
    
    @Override
    public void visit(final AssignmentExpression assignmentExpression) throws ParseTreeException {
        final Expression left = assignmentExpression.getLeft();
        if (!(left instanceof FieldAccess)) {
            super.visit(assignmentExpression);
            return;
        }
        final FieldAccess fieldAccess = (FieldAccess)left;
        final Expression referenceExpr = fieldAccess.getReferenceExpr();
        final TypeName referenceType = fieldAccess.getReferenceType();
        final Expression right = assignmentExpression.getRight();
        final Expression evaluateDown = this.evaluateDown(assignmentExpression);
        if (evaluateDown != assignmentExpression) {
            assignmentExpression.replace(evaluateDown);
            evaluateDown.accept(this);
            return;
        }
        if (referenceExpr != null) {
            referenceExpr.accept(this);
        }
        else if (referenceType != null) {
            referenceType.accept(this);
        }
        right.accept(this);
        final Expression evaluateUp = this.evaluateUp(assignmentExpression);
        if (evaluateUp != assignmentExpression) {
            assignmentExpression.replace(evaluateUp);
        }
    }
    
    @Override
    public Expression evaluateUp(final AllocationExpression allocationExpression) throws ParseTreeException {
        final OJClass type = this.getType(allocationExpression);
        final Expression expandAllocation = type.expandAllocation(this.getEnvironment(), allocationExpression);
        if (expandAllocation != allocationExpression) {
            return expandAllocation;
        }
        final Expression expandExpression = type.expandExpression(this.getEnvironment(), allocationExpression);
        if (expandExpression != allocationExpression) {
            return expandExpression;
        }
        return super.evaluateUp(allocationExpression);
    }
    
    @Override
    public Expression evaluateUp(final ArrayAccess arrayAccess) throws ParseTreeException {
        final OJClass type = this.getType(arrayAccess);
        final Expression expandArrayAccess = type.expandArrayAccess(this.getEnvironment(), arrayAccess);
        if (expandArrayAccess != arrayAccess) {
            return expandArrayAccess;
        }
        final Expression expandExpression = type.expandExpression(this.getEnvironment(), arrayAccess);
        if (expandExpression != arrayAccess) {
            return expandExpression;
        }
        return super.evaluateUp(arrayAccess);
    }
    
    @Override
    public Expression evaluateUp(final ArrayAllocationExpression arrayAllocationExpression) throws ParseTreeException {
        final OJClass type = this.getType(arrayAllocationExpression);
        final Expression expandArrayAllocation = type.expandArrayAllocation(this.getEnvironment(), arrayAllocationExpression);
        if (expandArrayAllocation != arrayAllocationExpression) {
            return expandArrayAllocation;
        }
        final Expression expandExpression = type.expandExpression(this.getEnvironment(), arrayAllocationExpression);
        if (expandExpression != arrayAllocationExpression) {
            return expandExpression;
        }
        return super.evaluateUp(arrayAllocationExpression);
    }
    
    @Override
    public Expression evaluateUp(AssignmentExpression assignmentExpression) throws ParseTreeException {
        final Expression left = assignmentExpression.getLeft();
        if (left instanceof FieldAccess) {
            final FieldAccess fieldAccess = (FieldAccess)left;
            final OJClass computeRefType = this.computeRefType(fieldAccess.getReferenceType(), fieldAccess.getReferenceExpr());
            if (computeRefType != this.getSelfType()) {
                final Expression expandFieldWrite = computeRefType.expandFieldWrite(this.getEnvironment(), assignmentExpression);
                if (!(expandFieldWrite instanceof AssignmentExpression)) {
                    return expandFieldWrite;
                }
                assignmentExpression = (AssignmentExpression)expandFieldWrite;
            }
        }
        OJClass ojClass = this.getType(assignmentExpression);
        if (ojClass != this.getSelfType()) {
            final Expression expandAssignmentExpression = ojClass.expandAssignmentExpression(this.getEnvironment(), assignmentExpression);
            if (!(expandAssignmentExpression instanceof AssignmentExpression)) {
                return expandAssignmentExpression;
            }
            assignmentExpression = (AssignmentExpression)expandAssignmentExpression;
            ojClass = this.getType(assignmentExpression);
        }
        if (ojClass != this.getSelfType()) {
            final Expression expandExpression = ojClass.expandExpression(this.getEnvironment(), assignmentExpression);
            if (!(expandExpression instanceof AssignmentExpression)) {
                return expandExpression;
            }
            assignmentExpression = (AssignmentExpression)expandExpression;
        }
        return super.evaluateUp(assignmentExpression);
    }
    
    @Override
    public Expression evaluateUp(final BinaryExpression binaryExpression) throws ParseTreeException {
        final Expression expandExpression = this.getType(binaryExpression).expandExpression(this.getEnvironment(), binaryExpression);
        if (expandExpression != binaryExpression) {
            return expandExpression;
        }
        return super.evaluateUp(binaryExpression);
    }
    
    @Override
    public Expression evaluateUp(final CastExpression castExpression) throws ParseTreeException {
        final OJClass type = this.getType(castExpression);
        final Expression expandCastedExpression = this.getType(castExpression.getExpression()).expandCastedExpression(this.getEnvironment(), castExpression);
        if (expandCastedExpression != castExpression) {
            return expandCastedExpression;
        }
        final Expression expandCastExpression = type.expandCastExpression(this.getEnvironment(), castExpression);
        if (expandCastExpression != castExpression) {
            return expandCastExpression;
        }
        final Expression expandExpression = type.expandExpression(this.getEnvironment(), castExpression);
        if (expandExpression != castExpression) {
            return expandExpression;
        }
        return super.evaluateUp(castExpression);
    }
    
    @Override
    public Expression evaluateUp(final ClassLiteral classLiteral) throws ParseTreeException {
        final Expression expandExpression = this.getType(classLiteral).expandExpression(this.getEnvironment(), classLiteral);
        if (expandExpression != classLiteral) {
            return expandExpression;
        }
        return super.evaluateUp(classLiteral);
    }
    
    @Override
    public Expression evaluateUp(final ConditionalExpression conditionalExpression) throws ParseTreeException {
        final Expression expandExpression = this.getType(conditionalExpression).expandExpression(this.getEnvironment(), conditionalExpression);
        if (expandExpression != conditionalExpression) {
            return expandExpression;
        }
        return super.evaluateUp(conditionalExpression);
    }
    
    @Override
    public Expression evaluateUp(FieldAccess fieldAccess) throws ParseTreeException {
        final OJClass computeRefType = this.computeRefType(fieldAccess.getReferenceType(), fieldAccess.getReferenceExpr());
        if (computeRefType != this.getSelfType()) {
            final Expression expandFieldRead = computeRefType.expandFieldRead(this.getEnvironment(), fieldAccess);
            if (expandFieldRead != fieldAccess) {
                return expandFieldRead;
            }
        }
        final Expression expandExpression = this.getType(fieldAccess).expandExpression(this.getEnvironment(), fieldAccess);
        if (!(expandExpression instanceof FieldAccess)) {
            return expandExpression;
        }
        fieldAccess = (FieldAccess)expandExpression;
        return super.evaluateUp(fieldAccess);
    }
    
    @Override
    public Expression evaluateUp(final InstanceofExpression instanceofExpression) throws ParseTreeException {
        final Expression expandExpression = this.getType(instanceofExpression).expandExpression(this.getEnvironment(), instanceofExpression);
        if (expandExpression != instanceofExpression) {
            return expandExpression;
        }
        return super.evaluateUp(instanceofExpression);
    }
    
    @Override
    public Expression evaluateUp(final Literal literal) throws ParseTreeException {
        final Expression expandExpression = this.getType(literal).expandExpression(this.getEnvironment(), literal);
        if (expandExpression != literal) {
            return expandExpression;
        }
        return super.evaluateUp(literal);
    }
    
    @Override
    public Expression evaluateUp(MethodCall methodCall) throws ParseTreeException {
        final OJClass computeRefType = this.computeRefType(methodCall.getReferenceType(), methodCall.getReferenceExpr());
        if (computeRefType != this.getSelfType()) {
            final Expression expandMethodCall = computeRefType.expandMethodCall(this.getEnvironment(), methodCall);
            if (expandMethodCall != methodCall) {
                return expandMethodCall;
            }
        }
        final Expression expandExpression = this.getType(methodCall).expandExpression(this.getEnvironment(), methodCall);
        if (!(expandExpression instanceof MethodCall)) {
            return expandExpression;
        }
        methodCall = (MethodCall)expandExpression;
        return super.evaluateUp(methodCall);
    }
    
    @Override
    public Expression evaluateUp(final SelfAccess selfAccess) throws ParseTreeException {
        final Expression expandExpression = this.getType(selfAccess).expandExpression(this.getEnvironment(), selfAccess);
        if (expandExpression != selfAccess) {
            return expandExpression;
        }
        return super.evaluateUp(selfAccess);
    }
    
    @Override
    public TypeName evaluateUp(final TypeName typeName) throws ParseTreeException {
        final TypeName expandTypeName = this.getType(typeName).expandTypeName(this.getEnvironment(), typeName);
        if (expandTypeName != typeName) {
            return expandTypeName;
        }
        return super.evaluateUp(typeName);
    }
    
    @Override
    public Expression evaluateUp(final UnaryExpression unaryExpression) throws ParseTreeException {
        final Expression expandExpression = this.getType(unaryExpression).expandExpression(this.getEnvironment(), unaryExpression);
        if (expandExpression != unaryExpression) {
            return expandExpression;
        }
        return super.evaluateUp(unaryExpression);
    }
    
    @Override
    public Expression evaluateUp(final Variable variable) throws ParseTreeException {
        final OJClass type = this.getType(variable);
        if (type == null) {
            return variable;
        }
        final Expression expandExpression = type.expandExpression(this.getEnvironment(), variable);
        if (expandExpression != variable) {
            return expandExpression;
        }
        return super.evaluateUp(variable);
    }
    
    @Override
    public Statement evaluateUp(final VariableDeclaration variableDeclaration) throws ParseTreeException {
        final Statement expandVariableDeclaration = this.getType(variableDeclaration.getTypeSpecifier()).expandVariableDeclaration(this.getEnvironment(), variableDeclaration);
        if (expandVariableDeclaration != variableDeclaration) {
            return expandVariableDeclaration;
        }
        return super.evaluateUp(variableDeclaration);
    }
}

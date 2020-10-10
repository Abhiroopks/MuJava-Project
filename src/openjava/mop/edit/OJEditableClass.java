// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop.edit;

import openjava.mop.OJMethod;
import openjava.mop.OJField;
import openjava.mop.OJConstructor;
import openjava.mop.CannotAlterException;
import openjava.mop.Environment;
import openjava.mop.FileEnvironment;
import openjava.mop.OJSystem;
import openjava.mop.OJClass;

public class OJEditableClass extends OJClass
{
    public OJEditableClass(final OJClass ojClass) throws CannotAlterException {
        super(new FileEnvironment(OJSystem.env, ojClass.getPackage(), ojClass.getSimpleName()), ojClass.getDeclaringClass(), ojClass.getSourceCode());
    }
    
    public OJClass addClass(final OJClass ojClass) throws CannotAlterException {
        return super.addClass(ojClass);
    }
    
    public OJConstructor addConstructor(final OJConstructor ojConstructor) throws CannotAlterException {
        return super.addConstructor(ojConstructor);
    }
    
    public OJField addField(final OJField ojField) throws CannotAlterException {
        return super.addField(ojField);
    }
    
    public void addInterface(final OJClass ojClass) throws CannotAlterException {
        super.addInterface(ojClass);
    }
    
    public OJMethod addMethod(final OJMethod ojMethod) throws CannotAlterException {
        return super.addMethod(ojMethod);
    }
}

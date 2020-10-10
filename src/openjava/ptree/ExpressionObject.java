// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.Environment;
import openjava.mop.OJClass;

public abstract class ExpressionObject extends NonLeaf implements Expression
{
    private OJClass cachedType;
    
    public ExpressionObject() {
        this.cachedType = null;
    }
    
    void soilCache() {
        this.cachedType = null;
        final ParseTreeObject parent = this.getParent();
        if (parent instanceof ExpressionObject) {
            ((ExpressionObject)parent).soilCache();
        }
    }
    
    public OJClass getCachedType(final Environment environment) throws Exception {
        if (this.cachedType == null) {
            this.cachedType = this.getType(environment);
        }
        return this.cachedType;
    }
    
    public abstract OJClass getType(final Environment p0, final boolean p1) throws Exception;
    
    @Override
    public abstract OJClass getType(final Environment p0) throws Exception;
    
    @Override
    protected void set(final Object[] ptrees) {
        this.soilCache();
        super.set(ptrees);
    }
}

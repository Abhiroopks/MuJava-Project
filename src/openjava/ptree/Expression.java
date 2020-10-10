// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.OJClass;
import openjava.mop.Environment;

public interface Expression extends ParseTree, VariableInitializer
{
    OJClass getType(final Environment p0) throws Exception;
}

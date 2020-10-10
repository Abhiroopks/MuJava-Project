// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ojc;

import openjava.ptree.ClassDeclaration;
import openjava.mop.OJSystem;
import openjava.ptree.ParseTree;
import openjava.mop.OJClass;
import openjava.mop.Environment;

public class TranslatorThread extends Thread
{
    private final Environment env;
    private final OJClass clazz;
    
    public TranslatorThread(final Environment env, final OJClass clazz) {
        this.env = env;
        this.clazz = clazz;
    }
    
    @Override
    public void run() {
        try {
            final ClassDeclaration sourceCode = this.clazz.getSourceCode();
            final ClassDeclaration translateDefinition = this.clazz.translateDefinition(this.env, sourceCode);
            if (translateDefinition != sourceCode) {
                sourceCode.replace(translateDefinition);
            }
        }
        catch (Exception obj) {
            System.err.println("fail to translate " + this.clazz.getName() + " : " + obj);
            obj.printStackTrace();
        }
        synchronized (this.clazz) {
            OJSystem.waited = null;
            this.clazz.notifyAll();
        }
    }
}

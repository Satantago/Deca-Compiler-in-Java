package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tree.DeclField;


import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;

public class ListDeclField extends TreeList<AbstractDeclField> {
    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private static final Logger LOG = Logger.getLogger(ListDeclField.class);


    /**
     * Implements non-terminal "list_decl_Field" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclField(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                LOG.debug("verify listdec: start");
                for (AbstractDeclField decField : getList()) {
                    decField.verifyDeclField(compiler, localEnv, currentClass);
                }
                LOG.debug("verify listdec: end");


    }

    public void codeGenListDeclField(DecacCompiler compiler) {
        for (AbstractDeclField i : getList()) {
            i.codeGenDeclField(compiler);
        }
    }
}

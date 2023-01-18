package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tree.DeclParam;


import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;




/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl32
 * @date 01/01/2023
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {
    private static final Logger LOG = Logger.getLogger(ListDeclParam.class);

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Implements non-terminal "list_decl_Param" of [SyntaxeContextuelle] in pass 3
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
    
    public Signature verifyListDeclParam(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                LOG.debug("verify listdec: start");
                Signature sig = new Signature();
                for (AbstractDeclParam decParam : getList()) {
                    sig.add(decParam.verifyDeclParam(compiler, currentClass.getparams(), currentClass));
                }
                LOG.debug("verify listdec: end");
                return sig;
    }

    public void codeGenListDeclParam(DecacCompiler compiler) {
        for (AbstractDeclParam i : getList()) {
            i.codeGenDeclParam(compiler);
        }
        compiler.getRegisterAllocator().initCmptInitParam();
    }
}
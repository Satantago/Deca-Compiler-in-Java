package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;


/**
 * MethodBody 
 * @author gl32
 * @date 01/01/2023
 */
public abstract class AbstractMethodBody extends Tree {

    protected abstract void codeGenMethodBody(DecacCompiler compiler);


    /**
     * Implements non-terminal "method_body" of [SyntaxeContextuelle] in pass 3 
     */
    protected abstract void verifyMethodBody(DecacCompiler compiler,EnvironmentExp localEnv ,ClassDefinition currentClass,Type t) throws ContextualError;
}
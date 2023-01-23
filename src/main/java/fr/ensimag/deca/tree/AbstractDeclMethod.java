package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DAddr;

import java.util.LinkedList;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

public abstract class AbstractDeclMethod extends Tree {
    /**
 * Methodiable declaration
 *
 * @author gl32
 * @date 01/01/2023
 */
    
    /**
     * Generate assembly code for the instruction.
     * 
     * @param compiler
     */
    protected abstract void codeGenDeclMethod(DecacCompiler compiler,String s,DAddr adresseClass);
    protected abstract void codeGenDeclMethodLabel(DecacCompiler compiler,String s);

    protected abstract LinkedList<String> ajoutMethodLabel(LinkedList<String>list,String s);
    

    /**
     * Implements non-terminal "decl_Method" of [SyntaxeContextuelle] in pass 2
     * @param compiler contains "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to the "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the synthetized attribute
     * @param currentClass 
     *          corresponds to the "class" attribute (null in the main bloc).
     */    
    protected abstract void verifyDeclMethod(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;
}
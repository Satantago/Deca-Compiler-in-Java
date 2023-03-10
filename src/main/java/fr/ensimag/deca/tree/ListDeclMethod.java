package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tree.DeclMethod;
import fr.ensimag.ima.pseudocode.DAddr;
import net.bytebuddy.asm.Advice.Return;

import org.apache.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang.Validate;




/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl32
 * @date 01/01/2023
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {
    private static final Logger LOG = Logger.getLogger(ListDeclMethod.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclMethod method : getList()) {
            method.decompile(s);
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Implements non-terminal "list_decl_Method*" of [SyntaxeContextuelle] in pass 3
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
    void verifyListDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                LOG.debug("verify listmeth: start");
                for (AbstractDeclMethod decMethod : getList()) {
                    decMethod.verifyDeclMethod(compiler, localEnv, currentClass);
                }
                LOG.debug("verify listmeth: end");


    }
    

    public void codeGenListDeclMethodLabel(DecacCompiler compiler,String s) {
        for (AbstractDeclMethod i : getList()) {
            i.codeGenDeclMethodLabel(compiler,s);
        }
    }



    public LinkedList<String>  ajoutMethode(LinkedList<String>  list,String s) {
        for (AbstractDeclMethod i : getList()) {
            list = i.ajoutMethodLabel(list,s);
        }
        return list;
     }

    public void codeGenListDeclMethod(DecacCompiler compiler,String s,DAddr adresseClass) {
        for (AbstractDeclMethod i : getList()) {
            i.codeGenDeclMethod(compiler, s,adresseClass);
        }
    }
}
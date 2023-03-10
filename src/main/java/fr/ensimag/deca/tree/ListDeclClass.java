package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");

        for (AbstractDeclClass classe : getList() ){
            classe.verifyClass(compiler);
        }

         LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {

        for (AbstractDeclClass classe : getList() ){
            classe.verifyClassMembers(compiler); 
      
        }
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
            
            for (AbstractDeclClass classe : getList() ){
                classe.verifyClassBody(compiler); 
            }
    }

    public void codGenListDeclClass(DecacCompiler compiler){
        for (AbstractDeclClass c : getList()) {
            c.codeGenDeclClass(compiler);
        }
    }

    // public void codGenListDeclClassMethode(DecacCompiler compiler){
    //     for (AbstractDeclClass c : getList()) {
    //         c.codeGenDeclClassMethode(compiler);
    //     }
    // }
    
    public void codGenListDeclClassInit(DecacCompiler compiler){
        for (AbstractDeclClass c : getList()) {
            c.codeGenDeclClassInit(compiler);
        }
    }

         
}

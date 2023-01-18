package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;


import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl32
 * @date 01/01/2023
 */
public class DeclClass extends AbstractDeclClass {

    AbstractIdentifier className;
    AbstractIdentifier superClassName;
    ListDeclMethod listMethod;
    ListDeclField listField;
    
    public DeclClass(AbstractIdentifier className, AbstractIdentifier superClassName,
           ListDeclMethod listMethod, ListDeclField listField) {
        Validate.notNull(className);
        Validate.notNull(listMethod);
        Validate.notNull(listField);
        this.className = className;
        this.superClassName = superClassName;
        this.listMethod = listMethod;
        this.listField = listField;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class { ... A FAIRE ... }");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        /*************************super*********************/
        if (!(this.superClassName.verifyType(compiler) instanceof ClassType)){
            throw new ContextualError(this.superClassName.getName() 
            + " is definetely not a class", this.superClassName.getLocation());
        }
        ClassType veriftyp = (ClassType) this.superClassName.verifyType(compiler);
        this.superClassName.setType(veriftyp);


        /*************************name*********************/
        SymbolTable.Symbol superSymb = this.superClassName.getName();
        ClassDefinition supeer = (ClassDefinition) compiler.environmentType.defOfType(superSymb);
        ClassType mytype = new ClassType(className.getName(), className.getLocation(), supeer);
        ClassDefinition classDef = new ClassDefinition(mytype, className.getLocation(), supeer);
        try{
            compiler.environmentType.declareClass(this.className.getName(), classDef);
        } catch (DoubleDefException e) {
            throw new ContextualError(this.className.getName() + " is already defined", this.className.getLocation());
        }
       
        //rejects names such int, float ....
        if (compiler.environmentType.defOfType(name) != null) {
            if (!(compiler.environmentType.defOfType(name) instanceof ClassDefinition)){
            throw new ContextualError("Invalid name", getLocation());
        }
        this.className.setDefinition(classDef);
        this.className.setType(mytype);
        this.verifyClassMembers(compiler);

    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
                 //get the environmentExp of the class 

                   
                //className.verifyType(compiler); // Verifications que la classe existe 
                listMethod.verifyListDeclMethod(compiler, className.getClassDefinition().getMembers() ,className.getClassDefinition()) ;  // Verifications des methodes
                listField.verifyListDeclField(compiler, className.getClassDefinition().getMembers(), className.getClassDefinition()); // Verifications des attributs


            
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {

         throw new UnsupportedOperationException("not yet implemented");

        
     }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // Marouane Modification 
        className.prettyPrint(s, prefix, false);
        superClassName.prettyPrint(s, prefix, false);
        listField.prettyPrint(s, prefix, true);
        listMethod.prettyPrint(s, prefix, false);
        // Marouane Modification fini


    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // Marouane Modification 
        className.iter(f); 
        superClassName.iter(f);
        listField.iter(f);
        listMethod.iter(f);
        // Marouane Modification fini



    }

}

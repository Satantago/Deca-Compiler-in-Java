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
        ClassDefinition classDef = new ClassDefinition(veriftyp, className.getLocation(), supeer   );

        try{
        compiler.environmentType.declareClass(this.className.getName(), classDef);


        } catch (DoubleDefException e) {
            throw new ContextualError("Intersection of EnvExps is not empty", this.className.getLocation());
        }
        this.className.verifyExpr(compiler, null, classDef);


        //ListdeclMethod
        this.listMethod.verifyListDeclMethod(compiler, null, classDef);


        //Listdecl
        this.listField.verifyListDeclField(compiler, null, classDef);
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    

    @Override
    protected void codeGenDeclClass(DecacCompiler compiler){
        superClassName.codeGenSuperClass(compiler);
        className.codeGenClass(compiler);
        listField.codeGenListDeclField(compiler);
        listMethod.codeGenListDeclMethod(compiler);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        className.prettyPrint(s, prefix, false);
        superClassName.prettyPrint(s, prefix, false);
        listField.prettyPrint(s, prefix, false);
        listMethod.prettyPrint(s, prefix, true);
   }

    @Override
    protected void iterChildren(TreeFunction f) {
        className.iter(f); 
        superClassName.iter(f);
        listField.iter(f);
        listMethod.iter(f); 
    }
}

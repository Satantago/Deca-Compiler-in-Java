package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
import fr.ensimag.ima.pseudocode.DAddr;



import java.io.PrintStream;
import java.util.Deque;
import java.util.LinkedList;

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
        s.print("class ");
        this.className.decompile(s);
        s.print(" extends ");
        this.superClassName.decompile(s);
        s.println("{");
        s.indent();
        listField.decompile(s);
        listMethod.decompile(s);
        s.unindent();
        s.println("}");
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
        try{
            compiler.environmentType.declareClass(this.className.getName(), mytype.getDefinition());
        } catch (DoubleDefException e) {
            throw new ContextualError(this.className.getName() + " is already defined", this.className.getLocation());
        }
        this.className.setDefinition(mytype.getDefinition());
        this.className.setType(mytype);
        this.verifyClassMembers(compiler);
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
                 //get the environmentExp of the class 

                   
        //className.verifyType(compiler); // Verifications que la classe existe 
      // Verifications des methodes
        listField.verifyListDeclField(compiler, className.getClassDefinition().getMembers(), className.getClassDefinition()); 
        listMethod.verifyListDeclMethod(compiler, className.getClassDefinition().getMembers() ,className.getClassDefinition());

    }

    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {

         throw new UnsupportedOperationException("not yet implemented");

        
     }


    

    @Override
    protected void codeGenDeclClass(DecacCompiler compiler){
        superClassName.codeGenSuperClass(compiler);
        className.codeGenClass(compiler,className);
        LinkedList<String> list =new LinkedList<>(superClassName.getClassDefinition().getList());
        list = listMethod.ajoutMethode(list,className.getName().getName());
        className.getClassDefinition().setList(list);
        for(int i=0;i<list.size();i++){
            compiler.addInstruction(new LOAD(new LabelOperand(new Label("code."+list.get(i))),Register.R0));  
            compiler.addInstruction(new STORE(Register.R0, compiler.getRegisterAllocator().newGBRegistre()));    
        }
        compiler.getRegisterAllocator().setListMethodClass(className.getClassDefinition().getList());
    }
    @Override
    protected void codeGenDeclClassMethode(DecacCompiler compiler){

    }
    @Override
    protected void codeGenDeclClassInit(DecacCompiler compiler){
        compiler.addLabel(new Label("init."+className.getName().getName()));
        if(superClassName.getName().getName()!="Object"){
            compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB ),Register.R0));
            compiler.addInstruction(new PUSH(Register.R0));
            compiler.addInstruction(new BSR(new Label("init."+superClassName.getName().getName())));
            compiler.addInstruction(new SUBSP(1));
        }
        listField.codeGenListDeclFieldInit(compiler,className.getName().getName());
        listMethod.codeGenListDeclMethod(compiler,className.getName().getName());
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

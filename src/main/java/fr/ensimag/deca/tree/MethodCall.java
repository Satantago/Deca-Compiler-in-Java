package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
import fr.ensimag.ima.pseudocode.instructions.WSTR;


import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.StringType;

public class MethodCall extends AbstractExpr{
    private AbstractExpr expr;
    private AbstractIdentifier ident;
    private ListExpr lstExpr;

    public MethodCall(AbstractExpr expr, AbstractIdentifier ident, ListExpr lstExpr) {
        Validate.notNull(expr);
        Validate.notNull(ident);
        Validate.notNull(lstExpr);
        this.expr = expr;
        this.ident = ident;
        this.lstExpr = lstExpr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

                for( AbstractExpr e : lstExpr.getList()){
                    e.verifyExpr(compiler, localEnv, currentClass);
                }
                Type type = expr.verifyExpr(compiler, localEnv, currentClass); 
                return type;
                 

               }


    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        //p12
        compiler.addInstruction(new ADDSP(new ImmediateInteger(1+lstExpr.size())));
        //compiler.addInstruction(new LOAD(,Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));

        compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterAllocator().popRegister()),new RegisterOffset(0, Register.SP)));

        // DAddr dr = expr.getExpDefinition().getOperand() // Code gen qui pemet d'appeler getExpe

         // For les parametre !!!! 

         for(int i=0;i<lstExpr.size();i++){
            //Ajouter codeGen adresse // popRegister=> on prend la derniere valeur du registre !!!!!!
            //compiler.addInstruction(new LOAD(Register.getR(lstExpr.getIndex(i).,Register.getR(compiler.getRegisterAllocator().popRegister()))));
            compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterAllocator().popRegister()),new RegisterOffset(-1-i, Register.SP)));
         }
       

       
        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP),Register.getR(compiler.getRegisterAllocator().popRegister())));
        compiler.addInstruction(new LOAD(new NullOperand(),(Register.getR(compiler.getRegisterAllocator().popRegister()))));
      
      //  compiler.addInstruction(new BEQ(new Label("dereferncement_null")));
        
        compiler.addInstruction(new LOAD(new RegisterOffset(0,Register.getR(compiler.getRegisterAllocator().popRegister())) ,(Register.getR(compiler.getRegisterAllocator().popRegister()))));
        compiler.addInstruction(new BSR(new RegisterOffset(2,Register.getR(compiler.getRegisterAllocator().popRegister())) )); // OFFSET !!!!!!
        compiler.addInstruction(new SUBSP(new ImmediateInteger(1+lstExpr.size())));
        
        
    
    
    
    }
    

    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("not yet implemented");
        expr.decompile(s);
        s.print('.');
        ident.decompile(s);
        s.print('(');
        lstExpr.decompile(s);
        s.print(')');


    }

    @Override
    protected void iterChildren(TreeFunction f) {
        
        expr.iter(f);
        ident.iter(f); 
        lstExpr.iter(f);

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        
        expr.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, false);
        lstExpr.prettyPrint(s, prefix, false);



    }
    
    @Override
    String prettyPrintNode() {
        return "MethodCall";
    }

}
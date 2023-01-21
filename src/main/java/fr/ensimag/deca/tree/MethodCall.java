package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WSTR;


import java.io.PrintStream;
import java.lang.reflect.Method;
import java.time.format.SignStyle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
        Type type = expr.verifyExpr(compiler, localEnv, currentClass); 
        ClassType typ2 = (ClassType) type;
        expr.setType(type);
        if (!type.isClass()){
            throw new ContextualError("A method is called by a non class type", this.expr.getLocation());
        }
        ident.verifyExpr(compiler, typ2.getDefinition().getMembers(), currentClass);
        MethodDefinition metodef;
        try{
            metodef = (MethodDefinition) ident.getMethodDefinition();
        }catch (ClassCastException e) {
            throw new ContextualError("Methodcall expects a method", this.ident.getLocation());
        }
        ident.setType(metodef.getType());
        Signature sig = metodef.getSignature();
        Signature sig2 = new Signature();
        for (AbstractExpr a : this.lstExpr.getList()){
            sig2.add(a.verifyExpr(compiler, localEnv, currentClass));
        }
        if (!(sig.equals(sig2))){
            throw new ContextualError("Wrong signature for method : " + this.ident.getName(), this.ident.getLocation());
        }
        this.setType(metodef.getType());
        return metodef.getType();

    }


    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        codeGenInst(compiler);
        compiler.addInstruction(new LOAD(Register.R0,Register.R1)); 
       if(ident.getDefinition().getType().isInt()){
        compiler.addInstruction(new WINT());
        }
        else if(ident.getDefinition().getType().isFloat()){
            compiler.addInstruction(new WFLOAT());
        }   
        
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        //p12
        int indice=0;
        compiler.addInstruction(new ADDSP(new ImmediateInteger(1+lstExpr.size())));
        expr.codeGenInst(compiler);
        compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterAllocator().popRegister()),new RegisterOffset(0, Register.SP)));

        

         // For les parametre !!!!

         for(int i=0;i<lstExpr.size();i++){
            lstExpr.getIndex(i).codeGen(compiler);
            compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterAllocator().popRegister()),new RegisterOffset(-1-i, Register.SP)));
            compiler.getRegisterAllocator().freeRegistre(compiler);
        }

        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP),Register.getR(compiler.getRegisterAllocator().popRegister())));
        compiler.addInstruction(new CMP(new NullOperand(),(Register.getR(compiler.getRegisterAllocator().popRegister()))));
        compiler.addInstruction(new BEQ(new Label("dereferencement_null")));
        
        compiler.addInstruction(new LOAD(new RegisterOffset(0,Register.getR(compiler.getRegisterAllocator().popRegister())) ,(Register.getR(compiler.getRegisterAllocator().popRegister()))));
        LinkedList<String> l = compiler.getRegisterAllocator().getListMethodClass();
        System.out.println( l);

        for(indice=0;indice<l.size();indice++){
            System.out.println( l.get(indice).split(".",2)[1]);
            if( l.get(indice).split(".",2)[1].equals(("."+ident.getName().getName())) ){
                break;
            }
        }
        compiler.addInstruction(new BSR(new RegisterOffset(indice+1,Register.getR(compiler.getRegisterAllocator().popRegister())) )); // OFFSET !!!!!!
        compiler.getRegisterAllocator().freeRegistre(compiler);
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
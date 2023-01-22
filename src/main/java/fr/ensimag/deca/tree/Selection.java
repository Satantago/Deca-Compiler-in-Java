package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

public class Selection extends AbstractLValue {

    private AbstractExpr expr;
    private AbstractIdentifier ident;

    public Selection(AbstractExpr expr, AbstractIdentifier ident) {
        Validate.notNull(expr);
        Validate.notNull(ident);
        this.expr = expr;
        this.ident = ident;
    }
    /* 
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        
        Type type = this.expr.verifyExpr(compiler, localEnv, currentClass);
        ClassType classtyp = (ClassType) type;
        this.expr.setType(classtyp);
        if (!type.isClass()) {
            throw new ContextualError("selection exp must be a class", this.expr.getLocation());
        }
        ClassDefinition classdef = (ClassDefinition) compiler.environmentType.defOfType(classtyp.getName());
        if (classdef== null){
            throw new ContextualError("selection expects a field", this.ident.getLocation());
        }
        Type identype = this.ident.verifyExpr(compiler, classtyp.getDefinition().getMembers(), currentClass);
        FieldDefinition fieldef = (FieldDefinition) ident.getFieldDefinition();
        this.ident.setType(identype);

        if (fieldef.getVisibility() == Visibility.PROTECTED) {
            if (currentClass == null) {
                throw new ContextualError("can't acces this field from main", this.ident.getLocation());
            }
            if(!(this.assign_compatible(compiler,classtyp, currentClass.getType()))){
                throw new ContextualError("we're not in a subtype of the class where the field is defined", this.ident.getLocation());
            }
            if (!(this.assign_compatible(compiler, currentClass.getType(), type))){
                throw new ContextualError("expression type is not a subtype of current class", this.ident.getLocation());
            }
        }
        return fieldef.getType();
    }
    */
    
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
 ClassDefinition currentClass) throws ContextualError {
    Type type;
    try{
    type = this.expr.verifyExpr(compiler, localEnv, currentClass);
    } catch (ContextualError e) {
        throw new ContextualError("selection exp must be a class", this.expr.getLocation());
    }
    if (!type.isClass()) {
        throw new ContextualError("selection exp must be a class", this.expr.getLocation());
    }

    ClassType classtyp = (ClassType) type;
        this.expr.setType(classtyp);

    ClassDefinition classdef = (ClassDefinition) compiler.environmentType.defOfType(classtyp.getName());
    FieldDefinition fieldef;
    try{
        Type identype = this.ident.verifyExpr(compiler, classtyp.getDefinition().getMembers(), currentClass);
        this.ident.setType(identype);
        fieldef = (FieldDefinition) ident.getFieldDefinition();
    } catch (DecacInternalError e){
        throw new ContextualError("type class expects a field or a method", this.ident.getLocation());
    } catch (ContextualError e){
         throw new ContextualError("type class expects a field or a method", this.ident.getLocation());
    }
    
    if (fieldef.getVisibility() == Visibility.PROTECTED) {
        if (currentClass == null) {
            throw new ContextualError("can't acces this field from main", this.ident.getLocation());
        }
        if(!(this.assign_compatible(compiler,classtyp, currentClass.getType()))){
            throw new ContextualError("expression type is not a subtype of current class", this.ident.getLocation());

        }
        if (!(this.assign_compatible(compiler, currentClass.getType(), classtyp))){
            throw new ContextualError("we're not in a subtype of the class where the field is defined", this.ident.getLocation());
        }
    }
    this.setType(fieldef.getType());
    return fieldef.getType();
 }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        expr.codeGenInst(compiler);
        compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterAllocator().popRegister()),new RegisterOffset(0, Register.SP)));
        compiler.addInstruction(new CMP(new NullOperand(),(Register.getR(compiler.getRegisterAllocator().popRegister()))));
        compiler.addInstruction(new BEQ(new Label("dereferencement_null")));
        compiler.addInstruction(new LOAD(new RegisterOffset(ident.getExpDefinition().getIndex(),Register.getR(compiler.getRegisterAllocator().popRegister())) ,(Register.getR(compiler.getRegisterAllocator().popRegister()))));
    }
    protected void codeGen(DecacCompiler compiler) {
        codeGenInst(compiler);  
    }
    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        codeGenInst(compiler);  
        ident.codeGenPrint(compiler);  
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print('.');
        ident.decompile(s);
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        ident.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, true);
    }
    
    @Override
    String prettyPrintNode() {
        return "Selection";
    }
}

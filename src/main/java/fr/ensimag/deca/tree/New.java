package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.NEW;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
// A faire   !!!!!!!!!!!!
public class New extends AbstractExpr {
    AbstractIdentifier newtype;
    
    public New(AbstractIdentifier type) {
        this.newtype = type;
    }
   

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type typ = this.newtype.verifyType(compiler);
        if (!(typ.isClass())){
            throw new ContextualError("new is used on classes", getLocation());
        }
        this.setType(typ);
        return typ;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }
    @Override
    public void codeGen(DecacCompiler compiler) {
        compiler.addInstruction(new NEW(newtype.getClassDefinition().getNumberOfFields()+1, Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));
        compiler.addInstruction(new BOV(new Label("tas_plein")));
        compiler.addInstruction(new LEA(newtype.getClassDefinition().getAdresse(),Register.R0));
        compiler.addInstruction(new STORE(Register.R0,new RegisterOffset(0, Register.getR(compiler.getRegisterAllocator().popRegister()))));
        compiler.addInstruction(new PUSH( Register.getR(compiler.getRegisterAllocator().popRegister())));
        compiler.addInstruction(new BSR(new Label("init."+newtype.getClassDefinition().getType().getName().getName())));
        compiler.addInstruction(new POP( Register.getR(compiler.getRegisterAllocator().popRegister())));
        //compiler.addInstruction(new STORE(Register.getR(2),new RegisterOffset(0, Register.getR(compiler.getRegisterAllocator().popRegister()))));
       
        //super.getLeftOperand().getType().getName()
        
        //getNumberOfFields()

    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        this.newtype.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.newtype.prettyPrint(s, prefix, true);
    }

    @Override
    String prettyPrintNode() {
        return "new";
    }
}
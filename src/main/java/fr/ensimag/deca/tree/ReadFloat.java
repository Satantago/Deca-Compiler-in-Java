package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.RFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;

import java.io.PrintStream;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class ReadFloat extends AbstractReadExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(compiler.environmentType.FLOAT);
        return this.getType();            
    }

    @Override
    protected void codeGen(DecacCompiler compiler) { 
       compiler.addInstruction(new RFLOAT()); 
       compiler.addInstruction(new LOAD(Register.R1,Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) { 
        codeGen(compiler);    
    }
    @Override
    protected void codeGenPrint(DecacCompiler compiler) { 
        compiler.addInstruction(new WFLOAT()); 
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("readFloat()");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

}

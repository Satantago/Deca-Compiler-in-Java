package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.RINT;
import fr.ensimag.ima.pseudocode.instructions.WINT;

import java.io.PrintStream;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class ReadInt extends AbstractReadExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        this.setType(compiler.environmentType.INT);
        return this.getType();
    }

    @Override
    protected void codeGen(DecacCompiler compiler) { 
       compiler.addInstruction(new RINT()); 
       compiler.addInstruction(new LOAD(Register.R1,Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) { 
        codeGen(compiler);    
    }
    @Override
    protected void codeGenPrint(DecacCompiler compiler) { 
        compiler.addInstruction(new WINT()); 
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("readInt()");
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

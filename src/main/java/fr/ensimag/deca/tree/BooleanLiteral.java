package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }



    protected void codeGenPrint(DecacCompiler compiler) {
    }
    protected void codeGen(DecacCompiler compiler) {
    if(value)
        compiler.addInstruction(new LOAD(1,Register.getR(compiler.getRegisterAllocator().newRegister())));

    else
            compiler.addInstruction(new LOAD(0,Register.getR(compiler.getRegisterAllocator().newRegister())));
}

        protected void codeGenIter(DecacCompiler compiler) {
        System.out.println("BRA D Z");
        Label l =  new Label("FinIF"+compiler.getCmptLabel());
        compiler.addInstruction(new LOAD(0,Register.R0));
        compiler.addInstruction(new CMP(Register.R0,Register.R0));
        if(value)
            compiler.addInstruction(new BNE(l));
        else
            compiler.addInstruction(new BEQ(l));
        compiler.addDqueLabel(l);
        compiler.incCmptLabel();
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        this.setType(compiler.environmentType.BOOLEAN);
        return new BooleanType(compiler.createSymbol("boolean"));

    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

}
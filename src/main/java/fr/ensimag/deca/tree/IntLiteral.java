package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;

import java.io.PrintStream;

/**
 * Integer literal
 *
 * @author gl32
 * @date 01/01/2023
 */
public class IntLiteral extends AbstractExpr {
    public int getValue() {
        return value;
    }

    private int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
         this.setType(compiler.environmentType.INT);
         return new IntType(compiler.createSymbol("int"));
    }

    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    } 
    @Override
    protected void codeGenPrint(DecacCompiler compiler) { 
        compiler.addInstruction(new LOAD(this.getValue(),Register.R1));
        compiler.addInstruction(new WINT()); 
    }
    protected void codeGenInitFields(DecacCompiler compiler) {
        compiler.addInstruction(new LOAD(this.getValue(),Register.R0));      
    }

    @Override
    protected void codeGen(DecacCompiler compiler) { 
        compiler.addInstruction(new LOAD(this.getValue(),Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));  
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(value));
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

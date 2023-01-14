package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

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

//burger frite_cheese  menu (allstar(BROWNIE/fuze tee) /  allstar(pancakes pepsi)  )  hot dog vg    2*frites   3*cookies    

    @Override
    protected void codeGenPrint(DecacCompiler compiler) { 
        System.out.println("intliteral Print");
        compiler.addInstruction(new LOAD(this.getValue(),Register.R1));
        compiler.addInstruction(new WINT()); 
    }
    @Override
    protected void codeGen(DecacCompiler compiler) { 
        System.out.println("intliteral gen");
       GPRegister reg =  Register.getR(compiler.getRegisterAllocator().newRegister()) ;
       compiler.addInstruction(new LOAD(this.getValue(),reg));  
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

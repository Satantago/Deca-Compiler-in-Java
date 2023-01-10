package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        compiler.addInstruction(new LOAD(new ImmediateInteger(),Register.R1));
        compiler.addInstruction(new LOAD(new ImmediateInteger(), Register.R0));
        compiler.addInstruction(new ADD(Register.R0, Register.R1 ));
        compiler.addInstruction(new WINT());
    }

 

    @Override
    protected String getOperatorName() {
        return "+";
    }
}

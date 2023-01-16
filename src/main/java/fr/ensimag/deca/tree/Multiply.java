package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    public void codeGenBinaryOp(DecacCompiler compiler,int lefReg,int rightReg ){
        compiler.addInstruction(new MUL(Register.getR(compiler.getRegisterAllocator().popRegister()),Register.getR(compiler.getRegisterAllocator().getLastButOne())));
        compiler.addInstruction(new BOV(new Label("opArith")));
        compiler.getRegisterAllocator().freeRegistre(compiler);
    } 


    @Override
    protected String getOperatorName() {
        return "*";
    }

}

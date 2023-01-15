package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;


/**
 * @author gl32
 * @date 01/01/2023
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenBinaryOp(DecacCompiler compiler,int lefReg,int rightReg ){
        compiler.addInstruction(new ADD(Register.getR(compiler.getRegisterAllocator().popRegister()),Register.getR(compiler.getRegisterAllocator().getLastButOne())));
        compiler.getRegisterAllocator().freeRegistre(compiler);

    } 

    @Override
    protected String getOperatorName() {
        return "+";
    }
}

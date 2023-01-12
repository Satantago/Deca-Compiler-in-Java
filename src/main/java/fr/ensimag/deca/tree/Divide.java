package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenBinaryOp(DecacCompiler compiler ,int lefReg,int rightReg ){
        System.out.println("/");
        compiler.addInstruction(new QUO(Register.getR(rightReg),Register.getR(lefReg)));
        compiler.getRegisterAllocator().triRegister(lefReg);
    } 

    @Override
    protected String getOperatorName() {
        return "/";
    }

}

package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenBinaryOp(DecacCompiler compiler,int lefReg,int rightReg ){
        System.out.println("-");
        compiler.addInstruction(new SUB(Register.getR(rightReg),Register.getR(lefReg)));
        compiler.getRegisterAllocator().triRegister(lefReg);
    } 


    @Override
    protected String getOperatorName() {
        return "-";
    }
    
}

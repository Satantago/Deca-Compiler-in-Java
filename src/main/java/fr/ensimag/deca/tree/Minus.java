package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenBinaryOp(DecacCompiler compiler,GPRegister lefReg,GPRegister rightReg ){
        System.out.println("plus");
        compiler.addInstruction(new SUB(lefReg,rightReg));
    } 


    @Override
    protected String getOperatorName() {
        return "-";
    }
    
}

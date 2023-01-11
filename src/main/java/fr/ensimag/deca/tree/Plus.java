package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;


/**
 * @author gl32
 * @date 01/01/2023
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenBinaryOp(DecacCompiler compiler,GPRegister lefReg,GPRegister rightReg ){
        System.out.println("plus");
        compiler.addInstruction(new ADD(lefReg,rightReg));
    } 

    @Override
    protected String getOperatorName() {
        return "+";
    }
}

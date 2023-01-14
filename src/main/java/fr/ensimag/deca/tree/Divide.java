package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;


// DIV PAR 0 
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
        if(super.getType().isFloat()){
            compiler.addInstruction(new DIV(Register.getR(rightReg),Register.getR(lefReg)));
        }
        else{
            compiler.addInstruction(new QUO(Register.getR(rightReg),Register.getR(lefReg)));
        }
        compiler.getRegisterAllocator().triRegister(lefReg);
    } 

    @Override
    protected String getOperatorName() {
        return "/";
    }

}

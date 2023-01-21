package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;
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
        
        compiler.addInstruction(new SUB(Register.getR(compiler.getRegisterAllocator().popRegister()),Register.getR(compiler.getRegisterAllocator().getLastButOne())));
        if(super.getType().isFloat()){
            compiler.addInstruction(new BOV(new Label("opArith")));
        }
        compiler.getRegisterAllocator().freeRegistre(compiler);
    } 


    @Override
    protected String getOperatorName() {
        return "-";
    }
    
}

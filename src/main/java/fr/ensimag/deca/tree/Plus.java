package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BOV;


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
        if(super.getType().isFloat()){
            compiler.addInstruction(new BOV(new Label("opArith")));
        }
        compiler.getRegisterAllocator().freeRegistre(compiler);

    } 

    @Override
    protected String getOperatorName() {
        return "+";
    }
}

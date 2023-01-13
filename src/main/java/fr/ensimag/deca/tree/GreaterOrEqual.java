package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Operator "x >= y"
 * 
 * @author gl32
 * @date 01/01/2023
 */
public class GreaterOrEqual extends AbstractOpIneq {

    public GreaterOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenBinaryOp(DecacCompiler compiler, int lefReg, int rightReg ) {
        System.out.println("== GREATER OR EQUAL");
        compiler.addInstruction(new LOAD(Register.getR(lefReg), Register.getR(compiler.getRegisterAllocator().newRegister())));
        compiler.addInstruction(new CMP(Register.getR(rightReg), Register.getR(compiler.getRegisterAllocator().popRegister())));
        Label l = new Label("FinIF" + compiler.getCmptLabel());
        compiler.addInstruction(new BLT(l));
        compiler.addDqueLabel(l);
        compiler.incCmptLabel();
    }


    @Override
    protected String getOperatorName() {
        return ">=";
    }

}

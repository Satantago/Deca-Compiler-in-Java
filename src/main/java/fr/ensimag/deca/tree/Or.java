package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
    @Override
    public void codeGenBinaryOpIter(DecacCompiler compiler, int lefReg, int rightReg ) {
        codeGenBinaryOp(compiler,lefReg,rightReg);
        compiler.addInstruction(new CMP(0, Register.getR(compiler.getRegisterAllocator().popRegister())));
        Label l = new Label("FinIF" + compiler.getCmptLabel());
        compiler.addInstruction(new BEQ(l));
        compiler.addDqueLabel(l);
        compiler.incCmptLabel();
    }

    public void codeGenBinaryOp(DecacCompiler compiler, int lefReg, int rightReg ) {
        Label l = new Label("BIN" + compiler.getCmptLabel());
        compiler.addInstruction(new LOAD(1, Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));
        compiler.addDqueLabel(l);
        compiler.incCmptLabel();
        compiler.addInstruction(new CMP(1,Register.getR(lefReg)));
        compiler.addInstruction(new BEQ(l));
        compiler.addInstruction(new CMP(1,Register.getR(rightReg)));
        compiler.addInstruction(new BEQ(l));
        compiler.addInstruction(new LOAD(0, Register.getR(compiler.getRegisterAllocator().popRegister())));
        compiler.addLabel(compiler.popDdqueLabel());
    }
    @Override
    protected String getOperatorName() {
        return "||";
    }


}

package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    protected void codeGen(DecacCompiler compiler) {
        super.getOperand().codeGen(compiler);
        Label l = new Label("Not_"+compiler.getCmptLabel()); 
        Label l2 = new Label("Not_1_"+compiler.getCmptLabel()); 
        compiler.addInstruction(new CMP(1,Register.getR(compiler.getRegisterAllocator().popRegister())));
        compiler.getRegisterAllocator().newRegister(compiler);
        compiler.addInstruction(new BNE(l));
        compiler.addInstruction(new LOAD(0,Register.getR(compiler.getRegisterAllocator().popRegister())));
        compiler.addInstruction(new BRA(l2));
        compiler.addLabel(l);
        compiler.addInstruction(new LOAD(1,Register.getR(compiler.getRegisterAllocator().popRegister())));
        compiler.addLabel(l2);
    }
    @Override
    protected void codeGenIter(DecacCompiler compiler) {
        codeGen(compiler);
        compiler.addInstruction(new CMP(0, Register.getR(compiler.getRegisterAllocator().popRegister())));
        Label l = new Label("FinIF" + compiler.getCmptLabel());
        compiler.addInstruction(new BEQ(l));
        compiler.addDqueLabel(l);
        compiler.incCmptLabel();
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        AbstractExpr operand = this.getOperand();
        Type type = operand.verifyExpr(compiler, localEnv, currentClass);
        if (type.isBoolean()) {
            this.setType(type);
            return compiler.environmentType.BOOLEAN;
        }
        throw new ContextualError("cannot apply the Not" +  
                                "Operator to this type", this.getLocation());
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }
}

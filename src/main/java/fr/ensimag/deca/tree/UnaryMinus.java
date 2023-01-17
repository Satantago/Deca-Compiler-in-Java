package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    protected void codeGen(DecacCompiler compiler) {
        super.getOperand().codeGen(compiler);
        compiler.addInstruction(new LOAD(0,Register.R1));
        if(super.getType().isFloat())
            compiler.addInstruction(new FLOAT(Register.R1,Register.R1));
        compiler.addInstruction(new SUB(Register.getR(compiler.getRegisterAllocator().popRegister()),Register.R1));
        compiler.addInstruction(new LOAD(Register.R1,Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) { 
        codeGen(compiler);
        compiler.addInstruction(new LOAD(Register.getR(compiler.getRegisterAllocator().popRegister()) ,Register.R1));
        compiler.getRegisterAllocator().freeRegistre(compiler);
        if(super.getType().isFloat())
            compiler.addInstruction(new WFLOAT());
        else if(super.getType().isInt())
            compiler.addInstruction(new WINT());

    }
    @Override
    protected void codeGenPrintX(DecacCompiler compiler) { 
        codeGen(compiler);
        compiler.addInstruction(new LOAD(Register.getR(compiler.getRegisterAllocator().popRegister()) ,Register.R1));
        compiler.getRegisterAllocator().freeRegistre(compiler);
        compiler.addInstruction(new WFLOATX());
    }


    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        AbstractExpr operand = this.getOperand();
        Type type = operand.verifyExpr(compiler, localEnv, currentClass);

        if (! (type.isInt() || type.isFloat())) {
            throw new ContextualError("Unary minus can only be applied to integers and floats", this.getLocation());
        }
        this.setType(type);
        return type;
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }
}
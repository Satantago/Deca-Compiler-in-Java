package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl32
 * @date 01/01/2023
 */
public class ConvFloat extends AbstractUnaryExpr {
    private AbstractExpr operand;
    public ConvFloat(AbstractExpr operand) {
        super(operand); 
        this.operand = operand;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(compiler.environmentType.FLOAT);
        return compiler.environmentType.FLOAT;
    }

    @Override
    protected void codeGen(DecacCompiler compiler) {
        this.operand.codeGen(compiler);
        compiler.addInstruction(new FLOAT(Register.getR(compiler.getRegisterAllocator().popRegister()), Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));
        compiler.getRegisterAllocator().freeRegistreLastButOne(compiler);
    }   

    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

}

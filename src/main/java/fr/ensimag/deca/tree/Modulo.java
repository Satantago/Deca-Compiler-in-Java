package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.REM;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        AbstractExpr leftOperand = this.getLeftOperand();
        AbstractExpr rightOperand = this.getRightOperand();

        Type leftType = leftOperand.verifyExpr(compiler, localEnv, currentClass);
        Type rightType = rightOperand.verifyExpr(compiler, localEnv, currentClass);

        if (!leftType.isInt() || !rightType.isInt()) {
            throw new ContextualError("can only be applied to ints", this.getLocation());
        }
        
        this.setType(compiler.environmentType.INT);
        return compiler.environmentType.INT;
    }

    public void codeGenBinaryOp(DecacCompiler compiler,int lefReg,int rightReg ){
        System.out.println("%");
        compiler.addInstruction(new REM(Register.getR(rightReg),Register.getR(lefReg)));
        compiler.getRegisterAllocator().triRegister(lefReg);
    } 

    @Override
    protected String getOperatorName() {
        return "%";
    }

}

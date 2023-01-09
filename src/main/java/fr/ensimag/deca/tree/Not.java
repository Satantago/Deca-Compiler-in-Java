package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
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

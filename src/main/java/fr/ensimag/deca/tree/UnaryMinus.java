package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
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
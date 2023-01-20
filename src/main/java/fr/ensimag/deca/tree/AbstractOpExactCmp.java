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
public abstract class AbstractOpExactCmp extends AbstractOpCmp {

    public AbstractOpExactCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
    ClassDefinition currentClass) throws ContextualError {

        AbstractExpr leftOperand = this.getLeftOperand();
        AbstractExpr rightOperand = this.getRightOperand();

        Type leftType = leftOperand.verifyExpr(compiler, localEnv, currentClass);
        Type rightType = rightOperand.verifyExpr(compiler, localEnv, currentClass);

        if (((leftType.isInt() || leftType.isFloat()) && (rightType.isInt() || rightType.isFloat()))
            || (leftType.isBoolean() && rightType.isBoolean()))
            // pour les classes || leftType)
         {
            this.setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;
        }

        //equals and not equals
        if (this instanceof Equals || this instanceof NotEquals ){
            if ((leftType.isClass() || leftType.isNull()) && (rightType.isClass() || rightType.isNull())){
                this.setType(compiler.environmentType.BOOLEAN);
                return compiler.environmentType.BOOLEAN;
            }
        }
        throw new ContextualError("Cannot be applied to this type" + leftType.toString() + " " +
                rightType.toString(), this.getLocation());
    }


}

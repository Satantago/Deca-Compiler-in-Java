package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;


/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl32
 * @date 01/01/2023
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        AbstractExpr leftOperand = this.getLeftOperand();
        AbstractExpr rightOperand = this.getRightOperand();

        // Verify operand of the operation
        Type leftType = leftOperand.verifyExpr(compiler, localEnv, currentClass);
        Type rightType = rightOperand.verifyExpr(compiler, localEnv, currentClass);
                
        Type returnType;        

        if (leftType.isInt()  && rightType.isInt()) {
            returnType = compiler.environmentType.INT;
        }
        else if (leftType.isInt() && rightType.isFloat()) {
            setLeftOperand(new ConvFloat(leftOperand));
            getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
            returnType = compiler.environmentType.FLOAT;
        }
        else if (leftType.isFloat() && rightType.isInt()) {  
            setRightOperand(new ConvFloat(rightOperand));
            getRightOperand().verifyExpr(compiler, localEnv, currentClass);
            returnType = compiler.environmentType.FLOAT;
        }
        else if (leftType.isFloat() && rightType.isFloat()) { 
            returnType = compiler.environmentType.FLOAT;
        }
        else {
            throw new ContextualError("Cannot apply an arithmetic" + 
                                    "operationto this type",
                                      this.getLocation()); 
        }
        this.setType(returnType);
        return returnType;
    }
}
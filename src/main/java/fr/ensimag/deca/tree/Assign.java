package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;


/**

 /**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        Type typleft = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        getRightOperand().verifyRValue(compiler, localEnv, currentClass, typleft);
        this.setType(typleft);
        return typleft;
    }

    @Override
    public void codeGenInst(DecacCompiler compiler){
        super.getRightOperand().codeGen(compiler);
        super.getLeftOperand().codeGenStore(compiler);
    }
    // protected void codeGenIter(DecacCompiler compiler) {
        
    // }
    @Override
    protected String getOperatorName() {
        return "=";
    }

}

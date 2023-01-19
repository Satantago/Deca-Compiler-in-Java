package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
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
       // System.out.println( super.getLeftOperand().getType().isClass());
        // if( super.getLeftOperand().getType().isClass()){   
        //     super.getRightOperand().codeGen(compiler); // codegenobje ajouter en identifier contenu page 12(new)
        // }
        // else
        super.getRightOperand().codeGen(compiler);
       
        super.getLeftOperand().codeGenStore(compiler);
    }
    protected void codeGenIter(DecacCompiler compiler) {
        codeGenInst(compiler);
        Label l = new Label("FinIF" + compiler.getCmptLabel());
        
       compiler.addInstruction(new CMP(Register.getR(compiler.getRegisterAllocator().popRegister()),Register.R0));
       compiler.addInstruction(new BEQ(l));
       compiler.addDqueLabel(l);
       compiler.incCmptLabel();
       compiler.getRegisterAllocator().freeRegistre(compiler);

    }
    @Override
    protected String getOperatorName() {
        return "=";
    }

}

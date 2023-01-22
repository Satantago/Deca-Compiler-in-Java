package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Return extends AbstractInst {
    private AbstractExpr returnExpression;

    public AbstractExpr getReturnExpression() {
        return this.returnExpression;
    }

    public Return(AbstractExpr returnExpression) {
        Validate.notNull(returnExpression);
        this.returnExpression = returnExpression;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        returnExpression.codeGen(compiler);
        compiler.addInstruction(new LOAD(Register.getR(compiler.getRegisterAllocator().popRegister()),Register.R0));   
    }

    protected void codeGenPrint(DecacCompiler compiler) {
        //TODO  
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType) throws ContextualError {
        if (returnType.isVoid()){
            throw new ContextualError("In order to return, returntype should be different than void", getLocation());
        }
        try{         
        this.returnExpression = returnExpression.verifyRValue(compiler, localEnv, currentClass, returnType);
        }
        catch(ContextualError e){
            throw e;
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        getReturnExpression().decompile(s);
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnExpression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnExpression.prettyPrint(s, prefix, true);
    }
}
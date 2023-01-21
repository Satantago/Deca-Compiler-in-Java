package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Null extends AbstractExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(compiler.environmentType.NULL);
        return compiler.environmentType.NULL;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("null");
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        //nada
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
     //nada
    }

    @Override
    String prettyPrintNode() {
        return "null";
    }
}

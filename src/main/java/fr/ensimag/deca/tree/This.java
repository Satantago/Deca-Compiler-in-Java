package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.context.ClassType;


import java.io.PrintStream;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class This extends AbstractExpr {

    private boolean bool;

    public This(boolean b) {
        this.bool = b;
    }
   

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        if (currentClass==null) {
            throw new ContextualError("Can't call 'this' inside main", getLocation());
        }
        Type typ = currentClass.getType();
        this.setType(typ);
        return typ;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("this");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
    }

    @Override
    String prettyPrintNode() {
        return "This";
    }
}
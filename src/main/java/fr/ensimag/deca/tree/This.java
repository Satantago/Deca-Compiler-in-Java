package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

import java.io.PrintStream;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
// A faire   !!!!!!!!!!!!
public class This extends AbstractExpr {

    private boolean bool;
    private Symbol sym;
    public This() {
        this.bool = false;
    }
    public This(boolean b) {
        this.bool = b;
    }

    public This(Symbol s) {
        this.sym = s;
    }
    public boolean getbool() {
        return bool;
    }

   

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        //bool.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        //bool.prettyPrint(s, prefix, true);
    }

    @Override
    String prettyPrintNode() {
        return "This";
    }
}
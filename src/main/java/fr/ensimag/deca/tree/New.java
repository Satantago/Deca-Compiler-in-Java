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
public class New extends AbstractExpr {
    AbstractIdentifier newtype;
    
    public New(AbstractIdentifier type) {
        this.newtype = type;
    }
   

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type typ = this.newtype.verifyType(compiler);
        if (!(typ.isClass())){
            throw new ContextualError("new is used on classes", getLocation());
        }
        this.setType(typ);
        return typ;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.newtype.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.newtype.prettyPrint(s, prefix, true);
    }

    @Override
    String prettyPrintNode() {
        return "new";
    }
}
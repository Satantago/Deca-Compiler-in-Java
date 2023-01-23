package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.StringType;

public class InstanceOf extends AbstractExpr{
    private AbstractExpr expr;
    private AbstractIdentifier ident;

    public InstanceOf(AbstractExpr expr, AbstractIdentifier ident) {
        Validate.notNull(expr);
        Validate.notNull(ident);
        this.expr = expr;
        this.ident = ident;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type T1 = this.expr.verifyExpr(compiler, localEnv, currentClass);
        Type T2 = this.ident.verifyExpr(compiler, localEnv, currentClass);
        if (!((T1.isNull() || T1.isClass()) && T2.isClass())){
            throw new ContextualError("Type error while using instanceof", this.expr.getLocation());
        }
        this.setType(compiler.environmentType.BOOLEAN);
        return compiler.environmentType.BOOLEAN;
     }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(" instance of ");
        ident.decompile(s);
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        ident.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, true);
    }
    
    @Override
    String prettyPrintNode() {
        return "instanceof";
    }
}
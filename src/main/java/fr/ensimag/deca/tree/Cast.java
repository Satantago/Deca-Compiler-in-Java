package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.AbstractIdentifier;
import fr.ensimag.deca.tree.TreeFunction;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Cast extends AbstractExpr {
    private AbstractIdentifier type;
    private AbstractExpr Expression;

    public AbstractExpr getReturnExpression() {
        return this.Expression;
    }

    public Cast(AbstractIdentifier ident, AbstractExpr returnExpression) {
        Validate.notNull(ident) ; 
        Validate.notNull(returnExpression);
        this.Expression = returnExpression;
        this.type = ident;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        Expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        Expression.prettyPrint(s, prefix, true);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
            Type T2 = this.type.verifyType(compiler);
            Type T1 = this.Expression.verifyExpr(compiler, localEnv, currentClass);
            if (T1.isVoid()){
                throw new ContextualError("cannot cast a void type", this.Expression.getLocation());
            }
            if (!(this.assign_compatible(compiler,T2, T1) || this.assign_compatible(compiler,T1, T2))){
                throw new ContextualError("Invalid cast", this.type.getLocation());
            }
            return T2;
    }
}
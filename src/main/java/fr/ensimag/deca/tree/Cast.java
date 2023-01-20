package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Cast extends AbstractExpr {
    private AbstractExpr returnExpression;
    private AbstractIdentifier ident ;

    public AbstractExpr getReturnExpression() {
        return this.returnExpression;
    }

    public Cast(AbstractIdentifier ident, AbstractExpr returnExpression) {
        Validate.notNull(returnExpression);
        Validate.notNull(ident) ; 
        this.returnExpression = returnExpression;
        this.ident = ident ;
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
        s.print('(');
        ident.decompile(s);
        s.print(')');
        s.print('(');
        returnExpression.decompile(s);
        s.print(')');

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

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
                throw new UnsupportedOperationException("not yet implemented") ; 
                }
}
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

public class MethodCall extends AbstractExpr{
    private AbstractExpr expr;
    private AbstractIdentifier ident;
    private ListExpr lstExpr;

    public MethodCall(AbstractExpr expr, AbstractIdentifier ident, ListExpr lstExpr) {
        Validate.notNull(expr);
        Validate.notNull(ident);
        Validate.notNull(lstExpr);
        this.expr = expr;
        this.ident = ident;
        this.lstExpr = lstExpr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

                for( AbstractExpr e : lstExpr.getList()){
                    e.verifyExpr(compiler, localEnv, currentClass);
                }
                Type type = expr.verifyExpr(compiler, localEnv, currentClass); 
                return type;
                 

               }


    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        //p14
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        
        expr.iter(f);
        ident.iter(f); 
        lstExpr.iter(f);

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        
        expr.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, false);
        lstExpr.prettyPrint(s, prefix, false);



    }
    
    @Override
    String prettyPrintNode() {
        return "MethodCall";
    }

}
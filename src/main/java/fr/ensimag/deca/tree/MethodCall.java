package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.time.format.SignStyle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        Type type = expr.verifyExpr(compiler, localEnv, currentClass); 
        expr.setType(type);
        if (!type.isClass()){
            throw new ContextualError("A method is called by a non class type", this.expr.getLocation());
        }
        ClassType typ2 = (ClassType) type;
        ident.verifyExpr(compiler, typ2.getDefinition().getMembers(), currentClass);
        MethodDefinition metodef;
        try{
            metodef = (MethodDefinition) ident.getMethodDefinition();
        }catch (ClassCastException e) {
            throw new ContextualError("Methodcall expects a method", this.ident.getLocation());
        }
        ident.setType(metodef.getType());
        Signature sig = metodef.getSignature();
        Signature sig2 = new Signature();
        for (AbstractExpr a : this.lstExpr.getList()){
            sig2.add(a.verifyExpr(compiler, localEnv, currentClass));
        }
        if (!(sig.equals(sig2))){
            throw new ContextualError("Wrong signature for method : " + this.ident.getName(), this.ident.getLocation());
        }
        this.setType(metodef.getType());
        return metodef.getType();

    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
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
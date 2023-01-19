package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

public class Selection extends AbstractLValue {

    private AbstractExpr expr;
    private AbstractIdentifier ident;

    public Selection(AbstractExpr expr, AbstractIdentifier ident) {
        Validate.notNull(expr);
        Validate.notNull(ident);
        this.expr = expr;
        this.ident = ident;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = this.expr.verifyExpr(compiler, localEnv, currentClass);
        ClassType classtyp = (ClassType) type;
        this.expr.setType(classtyp);
        if (!type.isClass()) {
            throw new ContextualError("selection exp must be a class", this.expr.getLocation());
        }
        if (compiler.environmentType.defOfType(classtyp.getName()) == null){
            throw new ContextualError("selection expects a field", this.ident.getLocation());
        }
        Type identype = this.ident.verifyExpr(compiler, currentClass.getMembers(), currentClass);
        FieldDefinition fieldef = (FieldDefinition) ident.getFieldDefinition();
        this.ident.setType(identype);

        if (fieldef.getVisibility() == Visibility.PROTECTED) {
            if (currentClass == null) {
                throw new ContextualError("can't acces this field from main", this.ident.getLocation());
            }
            if ((!this.subtype(compiler, type, currentClass.getType()))
            && !(this.subtype(compiler, currentClass.getType(), fieldef.getType()))){
                throw new ContextualError("can't acces this field 1", this.ident.getLocation());
            }
        }
        return fieldef.getType();
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
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, true);
    }
    
    @Override
    String prettyPrintNode() {
        return "Selection";
    }
}

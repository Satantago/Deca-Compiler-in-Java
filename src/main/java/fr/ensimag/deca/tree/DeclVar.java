package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // non terminal type
        Type veriftype = this.type.verifyType(compiler);
        assert(type != null);
        this.type.setType(veriftype);
        if (this.type.getType().isVoid()){
            throw new ContextualError("type different de void", this.type.getLocation());
        }
        //identifier ici variable
        VariableDefinition defvar = new VariableDefinition(veriftype, this.varName.getLocation());
        // forcé à utiliser l'exception
        try{
        localEnv.declare(this.varName.getName(), defvar);
        System.out.println(localEnv.get(this.varName.getName()));
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("type already defined", this.varName.getLocation());
        }
        this.varName.setDefinition(defvar);
        //inialisation non terminal
        this.initialization.verifyInitialization(compiler, veriftype, localEnv, currentClass);
        //verify exp
        this.varName.verifyExpr(compiler, localEnv, currentClass);


    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}

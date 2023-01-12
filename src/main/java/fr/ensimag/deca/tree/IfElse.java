package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *  if/else if/else statement.
 *
 * @author gl32
 * @date 09/01/2023
 */
public class IfElse extends AbstractInst   {
    
    private AbstractExpr condition; 
    private ListInst thenBranch;
    private IfElse arbe;

    public IfElse(AbstractExpr condition, ListInst thenBranch, IfElse arbre ) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        //Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.arbe= arbre;
    }

    public void setCondition(AbstractExpr condition){
        Validate.notNull(condition);
        this.condition = condition;
    }
    public AbstractExpr getCondition(){
        return this.condition ;
    }
    public void setThenBranch(ListInst thenBranch){
        Validate.notNull(thenBranch);
        this.thenBranch = thenBranch;
    }
    public ListInst getThenBranch(){
        return this.thenBranch ;
    }
    public void setArbe(IfElse arbe){
        this.arbe = arbe;
    }
    public IfElse getArbe(){
        return this.arbe ;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {

            this.condition.verifyCondition(compiler, localEnv, currentClass);
            this.thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
            if (this.arbe != null) { 
                this.arbe.verifyInst(compiler, localEnv, currentClass, returnType);
            }
    } 

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.condition.codeGen(compiler);
        this.thenBranch.codeGenListInst(compiler);
        if(this.arbe != null) this.arbe.codeGenInst(compiler);
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected
    /* Fait quoi cette fct */
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        if(this.arbe != null) arbe.iter(f);

       // arbe.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        if(this.arbe != null)   arbe.prettyPrint(s, prefix, true);
    }
}

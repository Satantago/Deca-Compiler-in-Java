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
 * Full if/else if/else statement.
 *
 * @author gl32
 * @date 01/01/2023
 */
public class IfElse extends AbstractInst   {
    
    private AbstractExpr condition; 
    private ListInst thenBranch;
    private IfElse arbe;

    public IfElse(AbstractExpr condition, ListInst thenBranch, IfElse arbe ) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        //Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        // while(this.arbe != null){
        //     this.arbe = IfElse(this.arbe.condition,this.arbe.thenBranch);
        // }    
        this.arbe= arbe;
    }


    // public Iterator<IfElse> iterator() {
    //     return list.iterator();
    // }
    
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
      //  System.out.println("ICI" + arbe == null  );
        return this.arbe ;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
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

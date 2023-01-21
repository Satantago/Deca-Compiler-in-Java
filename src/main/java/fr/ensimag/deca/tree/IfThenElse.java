package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *  if/else if/else statement.
 *
 * @author gl32
 * @date 09/01/2023
 */
public class IfThenElse extends AbstractInst   {

    private AbstractExpr condition;
    private ListInst thenBranch;
    private IfThenElse arbe;
    private boolean isElse;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, boolean isElse, IfThenElse arbre ) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        this.condition = condition;
        this.isElse = isElse;
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
    public void setArbe(IfThenElse arbe){
        this.arbe = arbe;
    }
    public IfThenElse getArbe(){
        return this.arbe ;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
                              ClassDefinition currentClass, Type returnType)
            throws ContextualError {
       
            
        this.getCondition().verifyCondition(compiler, localEnv, currentClass);
        this.getThenBranch().verifyListInst(compiler, localEnv, currentClass, returnType);
        if (this.getArbe() != null) {
            this.getArbe().verifyInst(compiler, localEnv, currentClass, returnType);
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.condition.codeGenIter(compiler);
        this.thenBranch.codeGenListInst(compiler);
        Label lFin =  new Label("Fin"+compiler.getcmptLabelFin());
        compiler.addInstruction(new BRA(lFin));
        compiler.addDqueLabelFin(lFin);
        compiler.inccmptLabelFin();
        compiler.addLabel(compiler.popDdqueLabel());
        if(this.arbe != null){
            this.arbe.codeGenInst(compiler);
        }
        if(! compiler.isEmtyDqueLabelFin()){
            compiler.addLabel(compiler.popDqueLabelFin());

        }
    }

    protected void codeGenCndt(DecacCompiler compiler) {
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("if");
        s.print("(");
        condition.decompile(s);
        s.print(")");
        s.println("{");
        s.indent();
        thenBranch.decompile(s);
        s.unindent();
        s.println("}");
        if (this.arbe != null) {
            if(this.arbe.isElse) {
                arbe.decompileelse(s);
            }
            else{
                arbe.decompileelseif(s);
            }
        }
    }
    public void decompileelseif(IndentPrintStream s) {
        s.print("else if");
        s.print("(");
        condition.decompile(s);
        s.print(")");
        s.println("{");
        s.indent();
        thenBranch.decompile(s);
        s.unindent();
        s.print("}");
        if (this.arbe != null) {
            if(this.arbe.isElse) {
                arbe.decompileelse(s);
            }
            else{

                arbe.decompileelseif(s);
            }
        }
}

    public void decompileelse(IndentPrintStream s) {
        s.print("else");
        s.println("{");
        s.indent();
        thenBranch.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        if(this.arbe != null) arbe.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        if(this.arbe != null)   arbe.prettyPrint(s, prefix, true);
    }
}
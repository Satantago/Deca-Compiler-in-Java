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
 *
 * @author gl32
 * @date 01/01/2023
 */
public class While extends AbstractInst {
    private AbstractExpr condition;
    private ListInst body;

    public AbstractExpr getCondition() {
        return condition;
    }

    public ListInst getBody() {
        return body;
    }

    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.condition = condition;
        this.body = body;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        // throw new UnsupportedOperationException("not yet implemented");
        //compiler.addLabel(new Label("START_WHILE"));

        Label lWhile =  new Label("START_WHILE"+compiler.getcmptLabelFin());
        compiler.inccmptLabelFin();
        compiler.addDqueLabelWhile(lWhile);


        compiler.addLabel(compiler.popDqueLabelWhile());

        System.out.println("while");

        this.condition.codeGenIter(compiler);

        this.body.codeGenListInst(compiler);

        // Label lWhile =  new Label("START_WHILE"+compiler.getcmptLabelFin());
        compiler.addInstruction(new BRA(lWhile));
        // compiler.addDqueLabelWhile(lWhile);
        // compiler.inccmptLabelFin();

        // compiler.addInstruction(new BRA(new Label("START_WHILE")));

        compiler.addLabel(compiler.popDdqueLabel());
        compiler.inccmptLabelFin();
    }

    // @Override
    // protected void codeGenInst(DecacCompiler compiler) {
    //     // throw new UnsupportedOperationException("not yet implemented");
    //     System.out.println("while");

    //     this.condition.codeGen(compiler);

    //     this.body.codeGenListInst(compiler);
    //     codeGenInst(compiler);

    //     compiler.addLabel(new Label("FinIF"+compiler.getCmptLabel()));

    // }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
                              ClassDefinition currentClass, Type returnType) {
        try {
            this.condition.verifyCondition(compiler, localEnv, currentClass);
            this.body.verifyListInst(compiler, localEnv, currentClass, returnType);
        }
        catch (ContextualError e) {
            // A FAIRE
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getBody().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

}
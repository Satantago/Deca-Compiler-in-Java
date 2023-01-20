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

/**
 * String literal
 *
 * @author gl32
 * @date 01/01/2023
 */
public class StringLiteral extends AbstractStringLiteral {

    @Override
    public String getValue() {
        return value;
    }

    private String value;

    public StringLiteral(String value) {
        Validate.notNull(value);
        this.value = value.substring(1,value.length()-1);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // expression is a String  we use environmentExp to check if the variable is declared in the environment 
        // return the type of the expression which is a String
        this.setType(compiler.environmentType.STRING);
        return new StringType(compiler.createSymbol("String"));
     }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        this.value = value.replace("\\\\" ,"\\" );
        this.value = value.replace("\\\"" ,"\"");
        compiler.addInstruction(new WSTR(new ImmediateString(value)));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print('"');
        s.print(value);//a verifier
        s.print('"');
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
    
    @Override
    String prettyPrintNode() {
        return "StringLiteral (" + value + ")";
    }

}

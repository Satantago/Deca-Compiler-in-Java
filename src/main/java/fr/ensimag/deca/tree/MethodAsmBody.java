package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.*;

/**
 * Main block of a Deca program.
 *
 * @author gl32
 * @date 01/01/2023
 */
//no abstract
public class MethodAsmBody extends AbstractMethodBody {
    private AbstractStringLiteral text;

    private static final Logger LOG = Logger.getLogger(MethodAsmBody.class);
        
    public MethodAsmBody(AbstractStringLiteral text) {
        Validate.notNull(text);
        this.text = text;
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler,EnvironmentExp localEnv ,ClassDefinition currentClass,Type t) throws ContextualError {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        text.codeGen(compiler);      
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("asm(");
        text.decompile(s);
        s.print(");");
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        text.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        text.prettyPrint(s, prefix, true);
    }
}
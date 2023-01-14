package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.tools.*;

/**
 * Main block of a Deca program.
 *
 * @author gl32
 * @date 01/01/2023
 */
public class MethodBody extends AbstractMethodBody {

    private ListDeclVar declVariables;
    private ListInst insts;

    public MethodBody(ListDeclVar declVariables, ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    private static final Logger LOG = Logger.getLogger(MethodBody.class);
        
    @Override
    protected void verifyMethodBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Not yet implemented");        
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
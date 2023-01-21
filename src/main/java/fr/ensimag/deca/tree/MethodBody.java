package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.RTS;

import static org.mockito.Mockito.never;

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
    protected void verifyMethodBody(DecacCompiler compiler,EnvironmentExp localEnv,ClassDefinition currentClass,Type t) throws ContextualError {
        declVariables.verifyListDeclVariable(compiler, localEnv, currentClass);
        insts.verifyListInst(compiler, localEnv , currentClass, t);
    }


    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        declVariables.codeGenListDeclVar(compiler);
        insts.codeGenListInst(compiler);
        compiler.addInstruction(new RTS());
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.indent();
        declVariables.decompile(s);
        s.println();
        insts.decompile(s);
        s.unindent();
        //throw new UnsupportedOperationException("Not yet implemented");
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
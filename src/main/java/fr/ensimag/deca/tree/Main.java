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
 * @author gl32
 * @date 01/01/2023
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    private ListDeclVar declVariables;
    private ListInst insts;
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify Main: start");
        // A FAIRE: Appeler méthodes "verify*" de ListDeclVarSet et ListInst.
        // Vous avez le droit de changer le profil fourni pour ces méthodes
        // (mais ce n'est à priori pas nécessaire).
        EnvironmentExp notre = new EnvironmentExp(null);
        declVariables.verifyListDeclVariable(compiler, notre, null);
        insts.verifyListInst(compiler, notre, null, null);
        LOG.debug("verify Main: end");
    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        // A FAIRE: traiter les déclarations de variables.

        double g = 0.0f, d = 1.0f;
        double fg = -1.0f; double fd = 0.718284f;
        double m, fm;
        while (d>=(g+0x1.0p-23)) {
            m = (g+d) / 2.0f;
            System.out.println("m : "+ m);

            fm = m * (m * (m * (m * (m * (m * ( m * (1.0/5040.0) + 1.0/720.0) + 1.0/120.0) + 1.0/24.0) + 1.0/6.0) + 1.0/2.0) + 1.0) -1;
            System.out.println(fm);
            
            if (fm > 0.0) {

                d = m;
                fd = fm;
            } else {
                g=m;
                fg = fm;
            }
        }
        System.out.println(g);
        System.out.println("main");
        compiler.addComment("Beginning of main instructions:");
        declVariables.codeGenListDeclVar(compiler);
        insts.codeGenListInst(compiler);
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
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

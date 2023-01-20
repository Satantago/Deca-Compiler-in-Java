package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");

        classes.verifyListClass(compiler);
        main.verifyMain(compiler);






     
 
        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {

        compiler.addInstruction(new LOAD(0,Register.R0));
        compiler.addComment("Table des Methodes");
        classes.codGenListDeclClass(compiler); // Cree la table des methodes
        compiler.addComment("Main program");
        main.codeGenMain(compiler); // PRog main
        compiler.addInstruction(new HALT());
        classes.codGenListDeclClassInit(compiler); //Init field + methode squellete


        Label pile = new Label("pile_pleine");
        compiler.addFirst(new ADDSP(new ImmediateInteger(compiler.getRegisterAllocator().getNbGB()-2)));
        compiler.addFirst(new BOV(pile));
        compiler.addFirst(new TSTO(new ImmediateInteger(compiler.getRegisterAllocator().getMaxSP())));
        compiler.addLabel(pile);
        compiler.addInstruction(new WSTR("Erreur : pile pleine"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        Label tas = new Label("tas_pleine");
        compiler.addLabel(tas);
        compiler.addInstruction(new WSTR("Erreur : tas pleine"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("opArith"));
        compiler.addInstruction(new WSTR("Erreur :Stack Overflow"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("code.object.equals"));
        compiler.addInstruction(new RTS());
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}

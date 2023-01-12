package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WNL;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class Println extends AbstractPrint {

    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printlnx)
     */
    public Println(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        System.out.println("pRINTLN Aavnt super");
        super.codeGenInst(compiler);
        super.codeGenPrint(compiler);
        System.out.println("pRINTLN apres super");
        compiler.addInstruction(new WNL());
        // compiler.addInstruction(new ADD(Register.GB,Register.R0));
        // compiler.addInstruction(new WINT());
    }   

    @Override
    String getSuffix() {
        return "ln";
    }
}

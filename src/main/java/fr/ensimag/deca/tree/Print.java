package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class Print extends AbstractPrint {
    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printx)
     */
    public Print(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        if(super.getPrintHex())
            super.codeGenPrintX(compiler);
        else 
            super.codeGenPrint(compiler);
    }   
    @Override
    String getSuffix() {
        return "";
    }
}

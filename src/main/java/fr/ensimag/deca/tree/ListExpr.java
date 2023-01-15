package fr.ensimag.deca.tree;


import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl32
 * @date 01/01/2023
 */
public class ListExpr extends TreeList<AbstractExpr> {

    @Override
    public void decompile(IndentPrintStream s) {
        String separat = "";
        for (AbstractExpr expr : getList()) {
            s.print(separat);
            expr.decompile(s);
            separat = ", ";
        }
    }
}

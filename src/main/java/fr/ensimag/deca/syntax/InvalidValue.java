package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Syntax error for big numbers
 */

public class InvalidValue extends DecaRecognitionException {

    private static final long serialVersionUID = -4864898038248848446L;

    public InvalidValue(DecaParser recognizer, ParserRuleContext ctx) {
        super(recognizer, ctx);
    }

    @Override
    public String getMessage() {
        return "infinite number";
    }
}

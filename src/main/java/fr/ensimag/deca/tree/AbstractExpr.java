package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl32
 * @date 01/01/2023
 */
public abstract class AbstractExpr extends AbstractInst {

    //public abstract AbstractIdentifier getAbstractIdentifier();
    /**
     * @return true if the expression does not correspond to any concrete token
     * in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    /**
     * Get the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }
    private Type type;

    @Override
    protected void checkDecoration() {
        if (getType() == null) {
            throw new DecacInternalError("Expression " + decompile() + " has no Type decoration");
        }
    }

    /**
     * Verify the expression for contextual error.
     *
     * implements non-terminals "expr" and "lvalue"
     *    of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return the Type of the expression
     *            (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
                                    EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /*
     * Verifies if type1 is subtype of type2
    */
    /**
     * @param compiler
     * @param type1
     * @param type2
     * @return
     */
    protected boolean assign_compatible(DecacCompiler compiler, Type type1, Type type2) {
        if (type1.isNull() && type2.isClass()){
            return true;
        }
        try {
            ClassType newtype1 = (ClassType) type1;
            ClassType newtype2 = (ClassType) type2;
            return newtype1.isSubClassOf(newtype2);
        }
        catch (ClassCastException e) {
        }
        boolean b = ((type2.isFloat()) && (type1.isInt()));
        if ((b || type1.sameType(type2))){
            return true;
        }
        return false;
        }

    /**
     * Verify the expression in right hand-side of (implicit) assignments
     *
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute
     * @return this with an additional ConvFloat if needed ...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,
                                     EnvironmentExp localEnv, ClassDefinition currentClass,
                                     Type expectedType)
            throws ContextualError {
        Type type2 = this.verifyExpr(compiler, localEnv, currentClass);
        if ((assign_compatible(compiler, type2, expectedType))){
            return this;
        }
        throw new ContextualError("incompatible types", this.getLocation());
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
                              ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.verifyExpr(compiler, localEnv, currentClass);
    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *            Environment in which the condition should be checked.
     * @param currentClass
     *            Definition of the class containing the expression, or null in
     *            the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
                         ClassDefinition currentClass) throws ContextualError {
        Type upType = this.verifyExpr(compiler, localEnv, currentClass);
        if (! upType.isBoolean()) {
            throw new ContextualError("Condition does not return a boolean", getLocation());
        }
        this.setType(upType);
    }

    /**
     * Generate code to print the expression
     *
     * @param compiler
     */
    protected void codeGenPrint(DecacCompiler compiler) {
    }
    /**
     * @param compiler
     */
    protected void codeGenPrintX(DecacCompiler compiler) {
         
    }
    /**
     * Methode permet de generer le code
     * @param compiler
     */
    protected void codeGen(DecacCompiler compiler) {

    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    }


    /**
     * Methode permet de generer l'instruction STORE, utilise pour les variables
     * @param compiler
     */
    protected void codeGenStore(DecacCompiler compiler) {}

    protected void codeGenInitFields(DecacCompiler compiler){
    }
    protected void codeGenSuperClass(DecacCompiler compiler,AbstractIdentifier className) {}
    protected void codeGenClass(DecacCompiler compiler,AbstractIdentifier className) {}
    protected void codeGenLabel(DecacCompiler compiler){}
    /**
     * Methode permet de generer le code dans les condition d'iterration de if/while
     * @param compiler
     */
    protected void codeGenIter(DecacCompiler compiler) {}


    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }
}

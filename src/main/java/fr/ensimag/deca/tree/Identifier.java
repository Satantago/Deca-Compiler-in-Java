package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.ima.pseudocode.Register;

/**
 * Deca Identifier
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Identifier extends AbstractIdentifier {

    private Symbol name;
    private Definition definition;

    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }


    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }
    private static final Logger LOG = Logger.getLogger(Initialization.class);

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        if (localEnv.get(name) == null) {
            throw new ContextualError(name +" is not in localEnv", getLocation());
        }
        this.setDefinition(localEnv.get(name));
        return localEnv.get(name).getType();
    }


    /**getDefinition
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     * @return the type corresponding to this identifier
     * (corresponds to the "type" attribute)
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        // condition (__, type) = env_types(name)
        LOG.debug("verify verftype non terminal: start");

        if (compiler.environmentType.defOfType(name) == null) {
            throw new ContextualError("non defined type", getLocation());
        }
        this.setDefinition(compiler.environmentType.defOfType(name));

        LOG.debug("verify verftype non terminal: sortie");
        return compiler.environmentType.defOfType(name).getType();
    }

    @Override
    protected void codeGenStore(DecacCompiler compiler) {
        System.out.println("ident inst");
        compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterAllocator().popRegister()),getExpDefinition().getOperand()));
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        System.out.println("ident inst");
        compiler.addInstruction(new LOAD(getExpDefinition().getOperand() ,Register.getR(compiler.getRegisterAllocator().newRegister())));
    }

    @Override
    protected void codeGenIter(DecacCompiler compiler) {
        System.out.println("ident inst");
        Label l = new Label("FinIF" + compiler.getCmptLabel());
        compiler.addInstruction(new LOAD(0,Register.R0));
        compiler.addInstruction(new LOAD(getExpDefinition().getOperand() ,Register.getR(compiler.getRegisterAllocator().newRegister())));
        compiler.addInstruction(new CMP(Register.getR(compiler.getRegisterAllocator().popRegister()),Register.R0));
        compiler.addInstruction(new BEQ(l));
        compiler.addDqueLabel(l);
        compiler.incCmptLabel();
    }

    @Override
    protected void codeGen(DecacCompiler compiler) {
        System.out.println("ident Gen");
        codeGenInst(compiler);
    }
    @Override // Neeed IT??????
    protected void codeGenPrint(DecacCompiler compiler) { // Ajouter le cas de int float & string !!!!
        System.out.println("IDent Print");
        compiler.addInstruction(new LOAD(getExpDefinition().getOperand() ,Register.R1));
        if(getDefinition().getType().isInt()){
            compiler.addInstruction(new WINT());
        }
        else if(getDefinition().getType().isFloat()){
            compiler.addInstruction(new WFLOAT());
        }    
    }
    @Override // Neeed IT??????
    protected void codeGenPrintX(DecacCompiler compiler) { // Ajouter le cas de int float & string !!!!
        System.out.println("IDent PrintX");
        compiler.addInstruction(new LOAD(getExpDefinition().getOperand() ,Register.R1));
        compiler.addInstruction(new WFLOATX());
    }



    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {

        s.print(name.toString());

    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

}

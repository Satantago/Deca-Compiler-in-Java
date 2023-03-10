package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

public class DeclParam extends AbstractDeclParam{
    final private AbstractIdentifier type;
    final private AbstractIdentifier paramName;

    public DeclParam(AbstractIdentifier type, AbstractIdentifier paramName) {
        Validate.notNull(type);
        Validate.notNull(paramName);
        this.type = type;
        this.paramName = paramName;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        paramName.decompile(s);
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenDeclParam(DecacCompiler compiler){
        paramName.getExpDefinition().setOperand(new RegisterOffset(compiler.getRegisterAllocator().getCmptInitParam(),Register.LB));
        compiler.getRegisterAllocator().decCmptInitParam();
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        paramName.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        paramName.prettyPrint(s, prefix, true);
    }

    @Override
    public Type verifyDeclParam(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type stype = this.type.verifyType(compiler);
        this.type.setType(stype);
        if (stype.isVoid()){
            throw new ContextualError("Can't use void as a Type", this.type.getLocation());
        }
        ParamDefinition paramDef = new ParamDefinition(stype, this.getLocation());
        try {
            localEnv.declare(this.paramName.getName(), paramDef);
        } catch (DoubleDefException e) {
            throw new ContextualError(paramName.getName() + " is already used ", paramName.getLocation());
        }
        paramName.setDefinition(paramDef);
        return stype;
    }
}
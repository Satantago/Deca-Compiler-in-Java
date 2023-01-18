package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUB;

public class DeclMethod extends AbstractDeclMethod{
    AbstractIdentifier returnType;
    AbstractIdentifier methodName;
    ListDeclParam listParametres;
    AbstractMethodBody body;

    public DeclMethod( AbstractIdentifier returnType, AbstractIdentifier methodName,
                ListDeclParam listParametres, AbstractMethodBody body) {
        this.returnType = returnType;
        this.methodName = methodName;
        this.listParametres = listParametres;
        this.body = body;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    protected void codeGenDeclMethod(DecacCompiler compiler,String s){
        //ICIC
        compiler.addLabel(new Label("code."+s+"."+methodName.getName().getName()));

        listParametres.codeGenListDeclParam(compiler);
        // set operand (-3)LB ...
         


         

        // returnType.codeGen(compiler);
        // methodName.codeGenLabel(compiler);
        // listParametres.codeGenListDeclParam(compiler);
        // body.codeGenMethodBody(compiler);
    }
    protected void codeGenDeclMethodLabel(DecacCompiler compiler,String s){
        compiler.addInstruction(new LOAD(new LabelOperand(new Label("code."+s+"."+methodName.getName().getName())),Register.R0));  
        compiler.addInstruction(new STORE(Register.R0, compiler.getRegisterAllocator().newGBRegistre()));
    }
    @Override
    protected
    void iterChildren(TreeFunction f) {
        returnType.iter(f);
        methodName.iter(f);
        listParametres.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        methodName.prettyPrint(s, prefix, false);
        listParametres.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

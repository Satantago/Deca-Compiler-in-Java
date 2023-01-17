package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.STORE;

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

    @Override
    protected void codeGenDeclMethod(DecacCompiler compiler){
        returnType.codeGen(compiler);
        methodName.codeGenLabel(compiler);
        listParametres.codeGenListDeclParam(compiler);
        body.codeGenMethodBody(compiler);
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

            Type type = returnType.verifyType(compiler); // ok 

            // ClassDefinition superClass = currentClass.getSuperClass();

            Signature signature = listParametres.verifyListDeclParam(compiler, localEnv, currentClass);


            listParametres.verifyListDeclParam(compiler, localEnv, currentClass);

            


            MethodDefinition method = new MethodDefinition(type, this.getLocation(), signature,currentClass.getNumberOfMethods());




            try {
                localEnv.declare(methodName.getName(), method);
            } catch (DoubleDefException e) {
                throw new ContextualError("Double definition of method " + methodName.getName() + " in class " + currentClass.getNature(), this.getLocation());
            }
          
    }
}




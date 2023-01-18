package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.STORE;

public class DeclMethod extends AbstractDeclMethod{
    public AbstractIdentifier returnType;
    public AbstractIdentifier methodName;
    public ListDeclParam listParametres;
    public AbstractMethodBody body;

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

    /*
     * Verifies if type1 is subtype of type2
     */
    boolean subtype(DecacCompiler compiler, Type type1, Type type2) {
        try {
            ClassType newtype1 = (ClassType) type1;
            ClassType newtype2 = (ClassType) type2;
            System.out.println(newtype1.isSubClassOf(newtype2));
            return newtype1.isSubClassOf(newtype2);
        }
        catch (ClassCastException e) {
        }
        boolean b = ((type2.isFloat()) && (type1.isInt()));
            if (!(b || type1.sameType(type2))){
                return false;
            }
            return true;
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

            Type type = returnType.verifyType(compiler);
            returnType.setType(type); // ok 

            // ClassDefinition superClass = currentClass.getSuperClass();

            Signature signature = listParametres.verifyListDeclParam(compiler, localEnv, currentClass);


            //listParametres.verifyListDeclParam(compiler, localEnv, currentClass);

            

            MethodDefinition methodef;

            /**********************CONDITION**********************/
            ExpDefinition expdef = localEnv.get(methodName.getName());
            
            if (expdef != null) {
                if (!(expdef.isMethod())) {
                    throw new ContextualError("A field or param is already defined by " + methodName.getName(), methodName.getLocation());
                }
                MethodDefinition expdef2 = (MethodDefinition) expdef;
                if (!signature.equals(expdef2.getSignature())){
                    throw new ContextualError("Signature of overriden method " + methodName.getName() + " doesn't match the method ", methodName.getLocation());
                }
                methodef = new MethodDefinition(type, this.getLocation(), signature,expdef2.getIndex());
                methodName.setDefinition(methodef);
                //subtype
                Type parentreturnType = expdef2.getType();
                if (!(this.subtype(compiler, type, parentreturnType))){
                    throw new ContextualError("returntype of the child is not a subtype of the parent's", methodName.getLocation());
                }
                
            }
            else {
            currentClass.incNumberOfMethods();
            methodef = new MethodDefinition(type, this.getLocation(), signature,currentClass.getNumberOfMethods());
            methodName.setDefinition(methodef);
            }


            try {
                localEnv.declare(methodName.getName(), methodef);
            } catch (DoubleDefException e) {
                throw new ContextualError("Double definition of method " + methodName.getName() + " in class " + currentClass.getNature(), this.getLocation());
            }
    }
}




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
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import net.bytebuddy.asm.Advice.OffsetMapping.ForThisReference;

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
        this.returnType.decompile(s);
        s.print(" ");
        this.methodName.decompile(s);
        s.print(" (");
        this.listParametres.decompile(s);
        s.print(") {");

        this.body.decompile(s);
        s.println("}");
        //throw new UnsupportedOperationException("not yet implemented");
    }

    protected void codeGenDeclMethod(DecacCompiler compiler,String s){
        compiler.addLabel(new Label("code."+s+"."+methodName.getName().getName()));
        listParametres.codeGenListDeclParam(compiler);
        body.codeGenMethodBody(compiler);
    }
    protected void codeGenDeclMethodLabel(DecacCompiler compiler,String s){
        compiler.addInstruction(new LOAD(new LabelOperand(new Label("code."+s+"."+methodName.getName().getName())),Register.R0));  
        compiler.addInstruction(new STORE(Register.R0, compiler.getRegisterAllocator().newGBRegistre()));
    }

   // list.add(new String[][]{{"j"," " }});

   public LinkedList<String> ajoutMethodLabel(LinkedList<String> list,String s) {
    boolean b = true;
    for(int i=0;i<list.size();i++) {
        if( list.get(i).split(".",2)[1].equals(("."+methodName.getName().getName())) ){
            list.remove(i);
            list.add(i, s+"."+methodName.getName().getName());
            b = false;
            break;
        }        
    }
    if(b)
        list.addLast(s+"."+methodName.getName().getName());
    return list;
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
    boolean assign_compatible(DecacCompiler compiler, Type type1, Type type2) {
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

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler,
        EnvironmentExp localEnv, ClassDefinition currentClass)
        throws ContextualError {
            EnvironmentExp ExpParamsVars = new EnvironmentExp(currentClass.getMembers());
            Signature signature = listParametres.verifyListDeclParam(compiler, ExpParamsVars, currentClass);



        Type type = returnType.verifyType(compiler);
        returnType.setType(type); // ok 

        // ClassDefinition superClass = currentClass.getSuperClass();



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
            if (!(this.assign_compatible(compiler, type, parentreturnType))){
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
        /**************bodyverification **************/
        body.verifyMethodBody(compiler, ExpParamsVars,currentClass, type);

    }
}





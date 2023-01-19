package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUB;

public class DeclField extends AbstractDeclField{
    final private Visibility visibility;
    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    final private AbstractInitialization initialization;

    public DeclField(Visibility visibility, AbstractIdentifier type, 
                    AbstractIdentifier fieldName, AbstractInitialization initialization) {
        Validate.notNull(visibility);
        Validate.notNull(type);
        Validate.notNull(fieldName);
        Validate.notNull(initialization);
        this.visibility = visibility;
        this.type = type;
        this.fieldName = fieldName;
        this.initialization = initialization;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenDeclField(DecacCompiler compiler){
            if(initialization.isInit()){
                initialization.codeGenInitFields(compiler);            
            }
            else{
                compiler.addInstruction(new LOAD(0,Register.R0));
            }
            compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB ),Register.R1));
            compiler.addInstruction(new STORE(Register.R0,new RegisterOffset(1+compiler.getRegisterAllocator().getCmptInitClass(),Register.R1)));
            System.out.println(1+compiler.getRegisterAllocator().getCmptInitClass());
            //compiler.addInstruction(new LOAD(new RegisterOffset(1+compiler.getRegisterAllocator().getCmptInitClass(),Register.R0),Register.R1));

            fieldName.getFieldDefinition().setOperand(new RegisterOffset(1+compiler.getRegisterAllocator().getCmptInitClass(),Register.R1));
            compiler.getRegisterAllocator().incCmptInitClass();
    }
    @Override
    protected void codeGenDeclFieldInit(DecacCompiler compiler,int indice){
      
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        fieldName.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s, prefix, false);

        initialization.prettyPrint(s, prefix, true);
    }

    @Override
    protected void verifyDeclField(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        /****************visibility********************/
        
        /******************type************************/
        Type veriftype = this.type.verifyType(compiler);
        assert(veriftype != null);
        this.type.setType(veriftype);
        if (veriftype.isVoid()){
            throw new ContextualError("type different de void", this.type.getLocation());
        }

        /******************Initialisation *************/
        EnvironmentExp classEnv = currentClass.getMembers();

        this.initialization.verifyInitialization(compiler,veriftype, classEnv, currentClass);



        /****************field ***********************/
        FieldDefinition fieldDef;
        ExpDefinition superfield = classEnv.get(fieldName.getName());
        if (!(superfield == null)){
            if (!(superfield.isField())){
                throw new ContextualError("A method or param is already defined by " + fieldName.getName(), this.fieldName.getLocation());
            }
        /*
            //If a param has the same name of the field in the current class
            else if (superfield.isParam() && classEnv.envExp.containsKey(fieldName.getName())){
                throw new ContextualError("A param is already defined by this name", this.fieldName.getLocation());
        }
        */
        }
        currentClass.incNumberOfFields();
        fieldDef = new FieldDefinition(veriftype, this.fieldName.getLocation(), this.visibility, currentClass, currentClass.getNumberOfFields());
        fieldName.setDefinition(fieldDef);
        try{
        classEnv.declare(fieldName.getName(), fieldDef);
        }
        catch (EnvironmentExp.DoubleDefException e){
            throw new ContextualError("field already exist", this.fieldName.getLocation());
        }
    
}

}

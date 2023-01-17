package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.deca.tree.Visibility;

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
        throw new UnsupportedOperationException("not yet implemented");
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
        System.out.println(this.type.getName());
        Type veriftype = this.type.verifyType(compiler);
        assert(veriftype != null);
        this.type.setType(veriftype);
        if (veriftype.isVoid()){
            throw new ContextualError("type different de void", this.type.getLocation());
        }
        /******************Initialisation *************/
        EnvironmentExp classEnv = currentClass.getMembers();
        this.initialization.verifyInitialization(compiler,veriftype, classEnv, currentClass);



        /****************field **********************/
        ExpDefinition superfield = classEnv.get(fieldName.getName());
        if (superfield == null){
            // Le champ Index est 1, car il n’y a pas de champ dans Object.
            currentClass.incNumberOfFields(); 

            FieldDefinition fieldDef = new FieldDefinition(veriftype, this.fieldName.getLocation(), this.visibility, currentClass,currentClass.getNumberOfFields());

            fieldName.setDefinition(fieldDef);
            
        try{
        classEnv.declare(fieldName.getName(), fieldDef);
        }
        catch (EnvironmentExp.DoubleDefException e){
            throw new ContextualError("field already exist", this.fieldName.getLocation());
    }
    }
    else { 
        throw new ContextualError("field already exist", this.fieldName.getLocation());
    }

}
}
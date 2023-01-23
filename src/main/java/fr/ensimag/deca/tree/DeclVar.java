package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
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
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * @author gl32
 * @date 01/01/2023
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        /*****************Type non terminal*********************/
        Type veriftype = this.type.verifyType(compiler);
        assert(type != null);
        this.type.setType(veriftype);
        if (veriftype.isVoid()){
            throw new ContextualError("Void can't be used as a Type", this.type.getLocation());
        }

        /*****************Inialization non terminal *************/
        // here for float a = a ;
        this.initialization.verifyInitialization(compiler, veriftype, localEnv, currentClass);
        
        /******************Identifier terminal ******************/
        VariableDefinition defvar = new VariableDefinition(veriftype, this.varName.getLocation());
        try{
        localEnv.declare(this.varName.getName(), defvar);
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("variable already defined", this.varName.getLocation());
        }
        this.varName.setDefinition(defvar);
        this.varName.verifyExpr(compiler, localEnv, currentClass);

    }

    
    @Override
    protected void codeGenDeclVar(DecacCompiler compiler){
        initialization.codeGenInitialization(compiler);
        DAddr GBAdresse = compiler.getRegisterAllocator().newGBRegistre();
        this.varName.getExpDefinition().setOperand(GBAdresse);
        if(initialization.isInit()){
            compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterAllocator().popRegister()),GBAdresse));
            compiler.getRegisterAllocator().freeRegistre(compiler);
        }
    }

    @Override
    protected void codeGenDeclVarMethod(DecacCompiler compiler){
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB),Register.getR(compiler.getRegisterAllocator().newRegister(compiler))));  
        initialization.codeGenInitialization(compiler);
        DAddr GBAdresse = compiler.getRegisterAllocator().newGBRegistre();
        this.varName.getExpDefinition().setOperand(GBAdresse);

        if(initialization.isInit()){
            compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterAllocator().popRegister()),GBAdresse));
            compiler.getRegisterAllocator().freeRegistre(compiler);
        }
    }


    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
        s.println(";");
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}

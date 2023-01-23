package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.FMA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WINT;

import fr.ensimag.deca.DecacCompiler;



/**
 * Binary expressions.
 *
 * @author gl32
 * @date 01/01/2023
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    public AbstractBinaryExpr(AbstractExpr leftOperand,
                              AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
                if ( (leftOperand.getType() != null && leftOperand.getType().isInt())  || this instanceof Divide || this instanceof Modulo || this instanceof Minus){
                    getLeftOperand().codeGen(compiler);
                    int lefReg = compiler.getRegisterAllocator().popRegister();
                    getRightOperand().codeGen(compiler);
                    int rightReg = compiler.getRegisterAllocator().popRegister();
                    codeGenBinaryOp(compiler,lefReg,rightReg);
                }
                else if(this instanceof Plus){
                    if (getLeftOperand() instanceof Multiply) {
                        compiler.getRegisterAllocator().incFMA();
                        getLeftOperand().codeGen(compiler);
                        compiler.getRegisterAllocator().decFMA();
                        getRightOperand().codeGen(compiler);
                        compiler.addInstruction(new LOAD(Register.getR(compiler.getRegisterAllocator().popRegister()), Register.R1));
                        compiler.getRegisterAllocator().freeRegistre(compiler);
                        compiler.addInstruction(new FMA(Register.getR(compiler.getRegisterAllocator().getLastButOne()),Register.getR(compiler.getRegisterAllocator().popRegister())));
                        compiler.getRegisterAllocator().freeRegistreLastButOne(compiler);
                    }
                    else if (getRightOperand() instanceof Multiply) {
                        compiler.getRegisterAllocator().incFMA();
                        getRightOperand().codeGen(compiler);
                        getLeftOperand().codeGen(compiler);
                        compiler.addInstruction(new LOAD(Register.getR(compiler.getRegisterAllocator().popRegister()), Register.R1));
                        compiler.getRegisterAllocator().freeRegistre(compiler);
                        compiler.getRegisterAllocator().decFMA();
                        compiler.addInstruction(new FMA(Register.getR(compiler.getRegisterAllocator().getLastButOne()),Register.getR(compiler.getRegisterAllocator().popRegister())));
                        compiler.getRegisterAllocator().triRegister(compiler.getRegisterAllocator().getLastButOne());
                        compiler.getRegisterAllocator().freeRegistre(compiler);

                    }
                    else {
                        getLeftOperand().codeGen(compiler);
                        int lefReg = compiler.getRegisterAllocator().popRegister();
                        getRightOperand().codeGen(compiler);
                        int rightReg = compiler.getRegisterAllocator().popRegister();
                        codeGenBinaryOp(compiler,lefReg,rightReg);
                    }
                }
                else { 
                    getLeftOperand().codeGen(compiler);
                    int lefReg = compiler.getRegisterAllocator().popRegister();
                    getRightOperand().codeGen(compiler);
                    int rightReg = compiler.getRegisterAllocator().popRegister();
                    if (compiler.getRegisterAllocator().getFMA() == 0){
                        codeGenBinaryOp(compiler,lefReg,rightReg);
                    }
                }
    }



    @Override
    protected void codeGen(DecacCompiler compiler) {
        codeGenInst(compiler);
    }

    @Override
    protected void codeGenIter(DecacCompiler compiler) {
        getLeftOperand().codeGen(compiler);
        int lefReg = compiler.getRegisterAllocator().popRegister();
        getRightOperand().codeGen(compiler);
        int rightReg = compiler.getRegisterAllocator().popRegister();
        codeGenBinaryOpIter(compiler,lefReg,rightReg);
    }
    @Override
    protected void codeGenPrint(DecacCompiler compiler) { 
        codeGen(compiler);
        compiler.addInstruction(new LOAD(Register.getR(compiler.getRegisterAllocator().popRegister()) ,Register.R1));
        compiler.getRegisterAllocator().freeRegistre(compiler);
        if(super.getType().isFloat())
            compiler.addInstruction(new WFLOAT());
        else if(super.getType().isInt())
            compiler.addInstruction(new WINT());
    }
    @Override
    protected void codeGenPrintX(DecacCompiler compiler) { 
        compiler.addInstruction(new LOAD(Register.getR(compiler.getRegisterAllocator().popRegister()) ,Register.R1));
        compiler.getRegisterAllocator().freeRegistre(compiler);
        compiler.addInstruction(new WFLOATX());
    }

    protected void codeGenInitFields(DecacCompiler compiler){
        codeGen(compiler);
    }
    /** 
     * @param compiler
     * @param lefReg
     * @param rightReg
     */
    public void codeGenBinaryOp(DecacCompiler compiler,int lefReg,int rightReg){
    }

    /**
     * @param compiler
     * @param lefReg
     * @param rightReg
     */
    public void codeGenBinaryOpIter(DecacCompiler compiler,int lefReg,int rightReg){
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getLeftOperand().decompile(s);
        s.print(" " + getOperatorName() + " ");
        getRightOperand().decompile(s);
    }

    abstract protected String getOperatorName();

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }

}
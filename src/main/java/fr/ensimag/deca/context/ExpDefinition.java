package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;

/**
 * Definition associated to identifier in expressions.
 *
 * @author gl32
 * @date 01/01/2023
 */
public abstract class ExpDefinition extends Definition {
    private int index;
    private boolean isfield = false;
    public void setOperand(DAddr operand) {
        this.operand = operand;
    }
    public void setIndex(int i){
        index = i;
    }
    public int getIndex(){
        return index;
    }
    public boolean isField(){
        return isfield;
    }
    public void setisField(boolean t){
         isfield = t;
    }
    public DAddr getOperand() {
        return operand;
    }
    private DAddr operand;

    public ExpDefinition(Type type, Location location) {
        super(type, location);
    }

}

package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.List;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl32
 * @date 01/01/2023
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void add(Object object) {
        args.add((Type) object);
    }
    
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }

    @Override
    public boolean equals(Object ob){
        if (this == ob) {
            return true;
        }
        Signature other = (Signature) ob;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i=0; i< this.size(); i++) {
            if (!(this.paramNumber(i).sameType(other.paramNumber(i)))){
                return false;
            }
        }
        return true;
    }

}

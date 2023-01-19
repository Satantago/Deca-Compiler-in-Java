package fr.ensimag.deca.context;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;
import org.apache.commons.lang.Validate;

/**
 * Definition of a class.
 *
 * @author gl32
 * @date 01/01/2023
 */
public class ClassDefinition extends TypeDefinition {

    private DAddr regAdresse;

    public DAddr getAdresse(){
        return regAdresse;
    }
    public void setAdresse(DAddr regAdresse){
        this.regAdresse = regAdresse;
    }
    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public void incNumberOfFields() {
        this.numberOfFields++;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int n) {
        Validate.isTrue(n >= 0);
        numberOfMethods = n;
    }
    
    public int incNumberOfMethods() {
        numberOfMethods++;
        return numberOfMethods;
    }

    private int numberOfFields = 0;
    private int numberOfMethods = 0;
    
    @Override
    public boolean isClass() {
        return true;
    }
    
    @Override
    public ClassType getType() {
        // Cast succeeds by construction because the type has been correctly set
        // in the constructor.
        return (ClassType) super.getType();
    };

    public ClassDefinition getSuperClass() {
        return superClass;
    }

    private final EnvironmentExp members;
    private final ClassDefinition superClass; 

    public EnvironmentExp getMembers() {
        return members;
    }

    public ClassDefinition(ClassType type, Location location, ClassDefinition superClass) {
        super(type, location);
        EnvironmentExp parent;
        if (superClass != null) {
            parent = superClass.getMembers();
            this.setNumberOfFields(superClass.getNumberOfFields());
            this.setNumberOfMethods(superClass.getNumberOfMethods());
        } else {
            parent = null;
        }
        members = new EnvironmentExp(parent);
        this.superClass = superClass;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof ClassDefinition) {
            ClassDefinition other = (ClassDefinition) obj;
            return this.getType().getName().equals(other.getType().getName());
        }
        return false;
    }

    
}

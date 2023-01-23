package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;

import java.rmi.server.ObjID;
import java.util.HashMap;
import java.util.Map;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.DeclMethod;
import fr.ensimag.deca.tree.Location;


// A FAIRE: étendre cette classe pour traiter la partie "avec objet" de Déca
/**
 * Environment containing types. Initially contains predefined identifiers, more
 * classes can be added with declareClass().
 *
 * @author gl32
 * @date 01/01/2023
 */
public class EnvironmentType {
    public EnvironmentType(DecacCompiler compiler) {
        
        envTypes = new HashMap<Symbol, TypeDefinition>();
        
        Symbol intSymb = compiler.createSymbol("int");
        INT = new IntType(intSymb);
        envTypes.put(intSymb, new TypeDefinition(INT, Location.BUILTIN));

        Symbol floatSymb = compiler.createSymbol("float");
        FLOAT = new FloatType(floatSymb);
        envTypes.put(floatSymb, new TypeDefinition(FLOAT, Location.BUILTIN));

        Symbol voidSymb = compiler.createSymbol("void");
        VOID = new VoidType(voidSymb);
        envTypes.put(voidSymb, new TypeDefinition(VOID, Location.BUILTIN));

        Symbol booleanSymb = compiler.createSymbol("boolean");
        BOOLEAN = new BooleanType(booleanSymb);
        envTypes.put(booleanSymb, new TypeDefinition(BOOLEAN, Location.BUILTIN));

        Symbol nullSymb = compiler.createSymbol("null");
        NULL = new NullType(nullSymb);


        Symbol stringSymb = compiler.createSymbol("string");
        STRING = new StringType(stringSymb);
        // not added to envTypes, it's not visible for the user.

        Symbol ourobjet = compiler.createSymbol("Object"); 
        CLASS = new ClassType(ourobjet); 
        envTypes.put(ourobjet,new ClassDefinition(CLASS,  Location.BUILTIN , null));
        //Signature equals
        Symbol equal = compiler.createSymbol("equals");
        Signature sig = new Signature();
        sig.add(CLASS);
        MethodDefinition obEquals = new MethodDefinition(BOOLEAN, Location.BUILTIN, sig, 0);
        ClassDefinition obDef = (ClassDefinition) envTypes.get(ourobjet);
        try {
        obDef.getMembers().declare(equal, obEquals);
        obDef.incNumberOfMethods();
        } catch (EnvironmentExp.DoubleDefException d) {
        }
        
    }

    private final Map<Symbol, TypeDefinition> envTypes;

    /**
     *  Getter of Type definition of a symbol 
     *
     * @param s (the symbol concerned)
     */
    public TypeDefinition defOfType(Symbol s) {
        return envTypes.get(s);
    }
    
    public void declareClass(Symbol name, ClassDefinition def) throws DoubleDefException {
        if (envTypes.containsKey(name)){
                throw new DoubleDefException();
        }
        else {
            envTypes.put(name, def);
        }
    }

    public final VoidType    VOID;
    public final IntType     INT;
    public final FloatType   FLOAT;
    public final StringType  STRING;
    public final ClassType   CLASS;
    public final BooleanType BOOLEAN;
    public final NullType NULL;
}

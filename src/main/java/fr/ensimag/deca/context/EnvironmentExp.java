package fr.ensimag.deca.context;

import java.util.HashMap;
import java.util.Hashtable;

import fr.ensimag.deca.tools.SymbolTable.Symbol;

/**
 * Dictionary associating identifier's ExpDefinition to their names.
 * 
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 * 
 * The dictionary at the head of this list thus corresponds to the "current" 
 * block (eg class).
 * 
 * Searching a definition (through method get) is done in the "current" 
 * dictionary and in the parentEnvironment if it fails. 
 * 
 * Insertion (through method declare) is always done in the "current" dictionary.
 * 
 * @author gl32
 * @date 01/01/2023
 */
public class EnvironmentExp {
    // A FAIRE : implémenter la structure de donnée représentant un
    // environnement (association nom -> définition, avec possibilité
    // d'empilement).

    // on utilise une table de hachage pour associer un nom à une définition 
    // chaque nom est associé à une définition
    HashMap <nom,definition > env ;  

    EnvironmentExp parentEnvironment;
    
    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
        this.env = new HashMap <nom,definition >();
    }

    //TODO
    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public ExpDefinition get(Symbol key) {
        // on regarde si la clé est dans l'environnement
        ExpDefinition def = env.get(key); 
        if (def  == null ) {
            if (parentEnvironment != null) { 
            return parentEnvironment.get(key); // on renvoie dans l'environnement parent si la clé n'est pas dans l'environnement actuel 
            }   
            else { 
                return null;
            }
        }
        else{ 
            return def; // on renvoie la définition associée à la clé si elle est dans l'environnement actuel 
        }

        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     * 
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary 
     * - or, hides the previous declaration otherwise.
     * 
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {

        if(env.contains(name)){ // si la clé est déjà dans l'environnement actuel 
            throw new DoubleDefException(); // on renvoie une exception 
        }
        else{ 
            env.put(name,def); // sinon on ajoute la clé et la définition associée à l'environnement actuel 
        }
        
        throw new UnsupportedOperationException("not yet implemented");
    }

}

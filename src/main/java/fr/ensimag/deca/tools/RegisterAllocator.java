package fr.ensimag.deca.tools;
import java.util.ArrayDeque;
import java.util.Deque;
import fr.ensimag.ima.pseudocode.*;

public class RegisterAllocator {
    private int nbrGB; // Nombre de registres GB
    private Deque<Integer> registerUsed; // Liste des registres utilisés
    private boolean[] registerStatus; // Etat des registres (utilisé ou non)
    private int nbreMaxRegistre = 16; // Nombre maximal de registres disponibles
    
    // Constructeur
    public RegisterAllocator(){
        this.nbrGB = 1;
        this.registerStatus = new boolean[nbreMaxRegistre];
        this.registerUsed = new ArrayDeque<>();
        this.registerStatus[0]=true;
        this.registerStatus[1]=true;
        for(int i = 2;i<nbreMaxRegistre;i++){
            this.registerStatus[i]=false;
        }
    }
    
    // Méthode pour obtenir un registre libre
    public int newRegister(){
        for(int i=2;i<nbreMaxRegistre;i++){
            if(!this.registerStatus[i]){
                this.registerUsed.add(i);
                this.registerStatus[i]=true;
                return i;
            }
        }
        throw new IllegalStateException("Aucun registre disponible");
    }
    
    // Méthode pour obtenir le dernier registre utilisé
    public int popRegister(){
        assert !this.registerUsed.isEmpty() : "Aucun registre n'a été utilisé pop";
        return this.registerUsed.peekLast();
    }
    public int triRegister(int lastUsed){
        assert !this.registerUsed.isEmpty() : "Aucun registre n'a été utilisé tri ";
        this.registerUsed.remove(lastUsed);
        this.registerUsed.add(lastUsed);
        return this.registerUsed.peekLast();
    }
    
    // Méthode pour libérer le dernier registre utilisé
    public void freeRegistre(){
        assert !this.registerUsed.isEmpty() : "Aucun registre n'a été utilisé free";
        int registreLibere = this.registerUsed.poll();
        this.registerStatus[registreLibere]=false;
    }
    
    // Méthode pour obtenir un registre GB
    public DAddr newGBRegistre() {
        RegisterOffset GBRegistre = new RegisterOffset(nbrGB,Register.GB);
        this.nbrGB ++;
        return GBRegistre;
    }
}



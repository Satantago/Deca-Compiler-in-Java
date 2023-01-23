package fr.ensimag.deca.tools;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

public class RegisterAllocator {
    private int nbrGB; // Nombre de registres GB
    private int nbrSP; // Nombre de registres GB
    private int nbrClass; // Nombre de registres GB
    private Deque<Integer> registerUsed; // Liste des registres utilises
    private LinkedList<DAddr> listeGB; // Liste des registres utilises
    private LinkedList<String> listeMethodClass; //
    private boolean[] registerStatus; // Etat des registres (utilise ou non)
    private int nbreMaxRegistre = 16; // Nombre maximal de registres disponibles
    private int maxSP;// Nombre de registre max dans la pile utilise
    private int cmptInitClass;
    private int cmptInitParam;
    private int classIndex;
    private int b;
    
    // Constructeur
    public RegisterAllocator(){
        this.b = 0;
        this.nbrGB = 1;
        this.classIndex = 0;
        this.nbrSP = 1;
        this.maxSP = 2;
        this.nbrClass = 1;
        this.cmptInitParam = -3;
        this.cmptInitClass = 0;
        this.registerStatus = new boolean[nbreMaxRegistre];
        this.registerUsed = new ArrayDeque<>();
        this.listeGB = new LinkedList<>();
        this.listeMethodClass = new LinkedList<>();
        this.registerStatus[0]=true;
        this.registerStatus[1]=true;
        for(int i = 2;i<nbreMaxRegistre;i++){
            this.registerStatus[i]=false;
        }
    }

    public int getClassIndex() {return classIndex;}

    public void setClassIndex(int i) { classIndex = i;}


    public void incFMA() {
        this.b ++;
    }
    public void decFMA() {
        this.b --;
    }
    public int getFMA() {return b;}

    public LinkedList<String> getListMethodClass(){
        return listeMethodClass;
    }
    public void setListMethodClass(LinkedList<String> l){
        listeMethodClass = l;
    }

    public int getCmptInitClass(){
        return cmptInitClass;
    }

    public void decCmptInitParam(){
        cmptInitParam --;
    }

    public int getCmptInitParam(){
        return cmptInitParam;
    }

    public void incCmptInitClass(){
        cmptInitClass ++;
    }
    public void initCmptInitParam(){
        cmptInitParam = -3;
    }

    public int getMaxSP(){
        return maxSP;
    }
    public int getNbrClass(){
        return nbrClass;
    }
    public int getNbGB(){
        return nbrGB;
    }
    public void setNbrClass(int x){
        nbrClass=x;
    }
    // modification ici -r
    public void setnbreMaxRegistre(int nbreMaxRegistre){
        this.nbreMaxRegistre = nbreMaxRegistre;
    }
    // modification ici -r
    public int  getnbreMaxRegistre(){
        return nbreMaxRegistre;
    }

    /**
     *  Methode pour obtenir un registre libre
     * @param compiler
     * @return
     */
    public int newRegister(DecacCompiler compiler){
        for(int i=2;i<nbreMaxRegistre;i++){
            if(!this.registerStatus[i]){
                this.registerUsed.addLast(i);
                this.registerStatus[i]=true;
                return i;
            }
        }
        int registreLibere = this.registerUsed.pollFirst();
        compiler.addInstruction(new PUSH(Register.getR(registreLibere)));
        this.nbrSP ++;
        if(nbrSP > maxSP)
            this.maxSP = nbrSP;

        this.registerStatus[registreLibere]=true;
        this.registerUsed.addLast(registreLibere);
        return registreLibere;
    }
    
    // Methode pour obtenir le dernier registre utilisé
    public int popRegister(){
        assert !this.registerUsed.isEmpty() : "Aucun registre n'a été utilisé pop";
        return this.registerUsed.peekLast();
    }


    /**
     * Methode qui actualise l'orde des registres utilises
     * Prend le dernier registre et le met a la fin de la liste
     * @param lastUsed
     * @return
     */
    public int triRegister(int lastUsed){
        assert !this.registerUsed.isEmpty() : "Aucun registre n'a été utilisé tri ";
        this.registerUsed.remove(lastUsed);
        this.registerUsed.addLast(lastUsed);
        return this.registerUsed.peekLast();
    }

    /**
     * Retourne l'avant dernier registre utilisé
     * @return
     */
    public int getLastButOne(){
        int registreLast = this.registerUsed.removeLast();
        int registerLastButOne = this.registerUsed.peekLast();
        this.registerUsed.addLast(registreLast);
        return registerLastButOne;
    }
    
    // Methode pour libérer le dernier registre utilise
    public void freeRegistre(DecacCompiler compiler){
        assert !this.registerUsed.isEmpty() : "Aucun registre n'a été utilisé free";
        int registreLibere = this.registerUsed.removeLast();
        if(nbrSP > nbrGB){
            compiler.addInstruction(new POP(Register.getR(registreLibere)));
            this.registerUsed.addFirst(registreLibere);
            this.nbrSP --;
        }
        else 
            this.registerStatus[registreLibere]=false;
    }
    public void freeRegistreLastButOne(DecacCompiler compiler){
        assert !this.registerUsed.isEmpty() : "Aucun registre n'a été utilisé free";
        int registreLast = this.registerUsed.removeLast();
        int registerLastButOne = this.registerUsed.removeLast();
        this.registerUsed.addLast(registreLast);
        if(nbrSP > nbrGB){
            compiler.addInstruction(new POP(Register.getR(registerLastButOne)));
            this.registerUsed.addFirst(registerLastButOne);
            this.nbrSP --;
        }
        else 
            this.registerStatus[registerLastButOne]=false;
    }
    
    // Méthode pour obtenir un registre GB
    public DAddr newGBRegistre() {
        RegisterOffset GBRegistre = new RegisterOffset(nbrGB,Register.GB);
        listeGB.add(GBRegistre);
        this.nbrGB ++;
        this.nbrSP ++;
        this.maxSP ++;
        return GBRegistre;
    }

    public DAddr getGBRegistre(int offset) {
        return listeGB.get(offset-1);
    }
}



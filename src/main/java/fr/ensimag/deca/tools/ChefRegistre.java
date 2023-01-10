package fr.ensimag.deca.tools;


import java.util.LinkedList;
import fr.ensimag.ima.pseudocode.*;

public class ChefRegistre {
    
    private int nbrGB = 0;
    private int indice = -1;
    private LinkedList<Integer> listRegistre ;
    private int nbreMaxRegistre = 16;
    private boolean[] etatRegistre ;


    public ChefRegistre(){
        this.indice = 0;
        this.etatRegistre = new boolean[nbreMaxRegistre];
        this.listRegistre = new LinkedList<>();
        for(int i =0;i<nbreMaxRegistre;i++){
            this.etatRegistre[i]=false;
        }
    }

    public GPRegister getRegistreLibre(){ // a modifier apres comme pricipe d ordennace !! (Ps:Ilias)
            this.etatRegistre[this.indice] = true;
            this.indice ++;
            this.listRegistre.add(this.indice);
            return Register.getR(indice);
    }

    public GPRegister getLastRegisterUtiliser(){ // ajouter un assert !!!
        //assert   !this.listRegistre.isEmpty()
            return Register.getR(this.listRegistre.getLast());
        
    }

    public void freelastRegistre(){  
        if(! this.listRegistre.isEmpty()){
            
            this.etatRegistre[this.listRegistre.getFirst()] = false;
            this.listRegistre.removeFirst();
            this.indice --;
        }
    }

    public DAddr getGBRegistre() {
        RegisterOffset GBRegistre = new RegisterOffset(nbrGB,Register.GB);
        this.nbrGB ++;
        return GBRegistre;
    }
    
    //public GPRegister 
}


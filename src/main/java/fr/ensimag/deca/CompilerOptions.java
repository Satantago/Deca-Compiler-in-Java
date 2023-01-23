package fr.ensimag.deca;

import java.io.File;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fr.ensimag.ima.pseudocode.Register;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl32
 * @date 01/01/2023
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }
    public boolean getRegister() {
        return register;
    }

    public boolean getDecompiler() {
        return decompiler;
    }
    public boolean getVerify() {
        return verify;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    public int getnbRegister() {
        return nb_register;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean verify = false;
    private boolean decompiler = false;
    private boolean printBanner = false;
    private boolean register = false;
    private int nb_register = 0;
    private boolean nocheck = false;
    private int error_options = 0;

    private List<File> sourceFiles = new ArrayList<File>();

    
    public void parseArgs(String[] args) throws CLIException {

        //le fichier entré doit être un .deca sinon il est rejeté
        
        

/*
        for(int i = 0 ; i <args.length ; i++){
            // if we find -v and -p error options = 1
            if(args[i].equals("-v")){
                error_options = 1;
            }
            else if(args[i].equals("-p")){
                error_options = 1;
            }

       
        }*/
        for(int i = 0 ; i <args.length ; i++){
            if(args[i].equals("-b")){
                printBanner = true;
            }
            else if(args[i].equals("-P")){
                parallel = true ;
            }
            else if(args[i].equals("-p")) {
                decompiler = true ;
            }
            else if(args[i].equals("-d")){
                debug++  ;
            }
            else if(args[i].equals("-v")) {
                verify = true  ;
            }
            else if(args[i].equals("-r")){ 
                register = true ; 
                try{
                    nb_register  = Integer.parseInt(args[i+1]);
                } catch(NumberFormatException e){
                    throw new CLIException("Le parametre n'est pas un entier ! ");
                }
                if(nb_register < 5 || nb_register >16){ 
                    throw new CLIException("Le nombre de registre doit être compris entre 5 et 16");
                }
                i++;
            }
            else{
                if(args[i].endsWith(".deca")) {
                    sourceFiles.add(new File(args[i]));
                }
                else{
                    throw new CLIException("Le fichier entré n'est pas un .deca");
                }
            }
/*
            else if(args[i].equals("-n")){
                nocheck = true  ;  
            }  
            */
            }
            if(verify && decompiler ){
                throw new CLIException("-v et -p sont incompatibles");
            }
            if(! printBanner && sourceFiles.isEmpty()){
                throw new CLIException("Pas de fichier a compiler");
            }


        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

        // throw new UnsupportedOperationException("not yet implemented");
    }

    protected void displayUsage() {
       System.out.println("---------------------------------------------------------USAGE-----------------------------------------------------------------------");
       System.out.println("|                  decac [[-p | -v] [-n] [-r X] [-d]* [-P] [-w] <fichier deca>...] | [-b]                                             |\n");
       System.out.println("|------------------------------------------------------------Options----------------------------------------------------------------- |\n");
       System.out.println("| . -b (banner) : affiche une bannière indiquant le nom de l'équipe                                                                   |\n") ; 
       System.out.println("| . -p (parse) : arrête decac après l'étape de construction de l'arbre, et affiche la décompilation de ce dernier                     |\n") ; 
       System.out.println("| (i.e. s'il n'y a qu'un fichier source à compiler, la sortie doit être un programme deca syntaxiquement correct)                     |\n") ;
       System.out.println("| . -v (verification) : arrête decac après l'étape de vérifications produit aucune sortie en l'absence d'erreur)                      |\n") ; 
       System.out.println("| . -P (parallel) : s'il y a plusieurs fichiers sources,lance la compilation des fichiers en parallèle (pour accélérer la compilation)|\n");
       System.out.println("| . -n (no check) : supprime les tests à l'exécution spécifiés dans les points 11.1 et 11.3 de la sémantique de Deca                  |\n");
       System.out.println("| . -r X (registers) : limite les registres banalisés disponibles à  R0 ... R{X-1}, avec 4 <= X <= 16)                                |\n");
       System.out.println("| . -d (debug) : active les traces de debug. Répéter l'option plusieurs fois pour avoir plus de traces                                |\n");
       System.out.println(" ------------------------------------------------------------------------------------------------------------------------------------");

    
    }
    
}
package fr.ensimag.deca;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

    public boolean getDecompiler() {
        return decompiler;
    }
    public boolean getVerify() {
        return verify;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean verify = false;
    private boolean decompiler = false;
    private boolean printBanner = false;
    private List<File> sourceFiles = new ArrayList<File>();

    
    public void parseArgs(String[] args) throws CLIException {
        // A FAIRE : parcourir args pour positionner les options correctement.
        for(int i = 0 ; i <args.length ; i++){
            if(args[i].equals("-b")){
                printBanner = true;
            }
            else if(args[i].equals("-P")){
                parallel = true ;
            }
            else if(args[i].equals("-p")){
                decompiler = true ;
            }
            else if(args[i].equals("-d")){
                debug = 1  ;
            }
            else if(args[i].equals("-v")){
                verify = true  ;
            }  
            else{
                sourceFiles.add(new File(args[i]));
            }
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

       System.out.println(". -b (banner) : affiche une bannière indiquant le nom de l'équipe \n ") ; 
       System.out.println(". -p (parse) : arrête decac après l'étape de construction de l'arbre, et affiche la décompilation de ce dernier \n (i.e. s'il n'y a qu'un fichier source à compiler, la sortie doit être un programme deca syntaxiquement correct) \n ") ;
       System.out.println(". -v (verification) : arrête decac après l'étape de vérifications produit aucune sortie en l'absence d'erreur) \n ") ; 
       System.out.println(". -P (parallel) : s'il y a plusieurs fichiers sources,lance la compilation des fichiers en parallèle (pour accélérer la compilation)\n");

    
    }
    
}
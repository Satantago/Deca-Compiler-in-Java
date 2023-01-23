package fr.ensimag.deca;

import java.io.File;
import org.apache.log4j.Logger;

import fr.ensimag.deca.tree.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl32
 * @date 01/01/2023
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {

            System.out.println("888888ba                                          .88888.                                                   d8888b. d8888b.");  
            System.out.println("88    `8b                                        d8'   `88                                                      `88     `88 ");
            System.out.println("88     88 .d8888b. .d8888b. .d8888b. .d8888b.    88        88d888b. .d8888b. dP    dP  88d888b. .d8888b.     aaad8' .aaadP'");
            System.out.println("88     88 88ooood8 88'  \"  88'  `88 88'  `\"\"     88   YP88 88'  `88 88'  `88 88    88  88'  `88 88ooood8        `88 88'" );    
            System.out.println("88    .8P 88.  ... 88.  ... 88.  .88 88.  ...    Y8.   .88 88       88.  .88 88.  .88  88.  .88 88.  ...        .88 88.");    
            System.out.println("8888888P  `88888P' `88888P' `88888P8 `88888P'     `88888'  dP       `88888P' `88888P'  88Y888P' `88888P'    d88888P Y88888P"); 
            System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo~88~oooooooooooooooooooooooooooooooooo");
            System.out.println("                                                                                       dP                                  ");  
                 
            System.out.println("Boulahfa Erazki Gaoua Lachiri Sekkal");



            if (options.getSourceFiles().isEmpty()) {
                return;
            }
        }
        if (options.getSourceFiles().isEmpty()) {
            options.displayUsage();
            throw new UnsupportedOperationException("Il y'a pas de fichier spécifié à ce nom ");
        }
        if (options.getDebug()==1) { 

            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                compiler.compile();
                compiler.displayIMAProgram();  
            } 
        }       




        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            List<DecacCompiler> liste_compilation = new ArrayList<DecacCompiler>(); 
            int nb_worker_threads  = Runtime.getRuntime().availableProcessors();
            ExecutorService exe = Executors.newFixedThreadPool(nb_worker_threads); 
            List<Future<Boolean>> non_calule = new ArrayList<>(); 
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                liste_compilation.add(compiler);
            }
            for (DecacCompiler compiler : liste_compilation) {
                non_calule.add(exe.submit(() -> compiler.compile())) ;
            } 
            for (Future<Boolean> future : non_calule) {
                  if (!future.get()) {
                    error = true;
                  }
                }
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
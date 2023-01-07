package fr.ensimag.deca;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
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
    
    public static void main(String[] args) throws IOException {
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
            System.out.println("Equipe 32 ");


        }
        if (options.getSourceFiles().isEmpty()) {
            //throw new UnsupportedOperationException("decac without argument not yet implemented");
        }
        if (options.getParallel()) {
            List<DecacCompiler> compilers = new ArrayList<>();
            for (File source : options.getSourceFiles()) {
              DecacCompiler compiler = new DecacCompiler(options, source);
              compilers.add(compiler);
            }
          
            // utilisez le paquetage java.util.concurrent pour lancer la compilation de chaque fichier en parallèle
            int numWorkers = Runtime.getRuntime().availableProcessors(); // nombre de threads
            ExecutorService executorService = Executors.newFixedThreadPool(numWorkers); // pool de threads
            List<Future<Boolean>> futures = new ArrayList<>(); // liste des résultats des threads
          
            for (DecacCompiler compiler : compilers) {

              Future<Boolean> future = executorService.submit(() -> compiler.compile()); // lance le thread

              futures.add(future);
            }
          
            for (Future<Boolean> future : futures) {
              try {
                if (!future.get()) { // attend la fin du thread et récupère le résultat (true si pas d'erreur)  
                  error = true;
                }
              } catch (InterruptedException | ExecutionException e) {
                // gérer les exceptions
              }
            }


        } else {
            for (File source : options.getSourceFiles()) {
                //System.out.println("lol");

                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}

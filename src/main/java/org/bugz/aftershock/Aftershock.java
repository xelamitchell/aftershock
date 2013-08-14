package org.bugz.aftershock;

import java.util.Arrays;

/**
 * Aftershock is a fork of Jake2 (a direct Java port of Quake II / id Tech 2
 * game engine).
 * 
 * @author bugz
 */
public class Aftershock {
    
    public static void main(String[] args) {
        
        boolean server = false;
        
        // Checks there are arguments
        if(Arrays.asList(args).contains("-")) {
        
            for(String a : args) {

                // Server argument (dedicated mode)
                if(a.equals("s") || a.equals("server")) {
    //                logger.info("Starting in dedicated (server) mode.\n");
                    server = true;
                }
            }

            // TODO check configured items in properties file
            // TODO Sets Locale either from arguments or properties
            
        }
        
    }
    
}

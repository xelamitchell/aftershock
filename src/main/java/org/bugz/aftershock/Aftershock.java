package org.bugz.aftershock;

/**
 *
 * @author bugz
 */
public class Aftershock {
    
    public static void main(String[] args) {
        
        boolean dedicated = false;
        
        // Checks command line arguments
        for(String a : args) {
            
            // Checks whether the application is in dedicated mode (Server).
            if(a.equalsIgnoreCase("-d") || a.equalsIgnoreCase("-dedicated")) {
//                Com.Printf("Starting in dedicated mode.\n");
                dedicated = true;
            }
        }
    	
    	// TODO check configured items in properties file
        // TODO Sets Locale either from arguments or properties
        
    }
    
}

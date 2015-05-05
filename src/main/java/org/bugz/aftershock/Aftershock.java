package org.bugz.aftershock;

import static org.bugz.aftershock.engine.Mode.CLIENT;
import static org.bugz.aftershock.engine.Mode.SERVER;

import org.bugz.aftershock.client.splash.Splash;
import org.bugz.aftershock.engine.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * (non-javadoc)
 * 
 * TODO Arguments can be flexible as in Linux composed of:
 * Command -<letters> [parameters]
 * 
 * Parameters:
 * 
 * -man : Aftershock Manual
 * -a <location> : aftershock dir (can start with an alternative aftershock).
 * Location is the location of the alternative aftershock directory from which
 * the application will read properties and resources.
 * -s : Server (starts application as Server). Application runs as client by
 * default.
 * -v <level> : Verbosity (logs are also output in standard output). Level is an
 * optional parameter to increase or decrease log level.
 * -version : Version
 */
/**
 * Aftershock is a rewrite of <a href="http://bytonic.de/index.html">Jake2</a>
 * (a direct Java port of Quake II / id Tech 2 C game engine codebase).
 *
 * @author bugz
 */
public class Aftershock {

    private static final Logger logger = LoggerFactory.getLogger(Aftershock.class);
    
    public static void main(String[] args) {

        Mode mode = CLIENT;

        // Checks there are arguments
        for(String argument : args) {

            // TODO may have to split arguments by space as they will be in the
            // format: aftershock -<arguments> <parameters>
            
            // Server argument (dedicated mode)
            if(argument.contains("s")) {
                logger.debug("Starting in dedicated (server) mode...");
                mode = SERVER;
            }
        }

        logger.debug("Starting in {} mode...", mode.name().toLowerCase());
        
        // TODO check configured items in properties file
        // TODO sets Locale either from arguments or properties
        Splash splash = null;
        if(SERVER == mode) {
            splash = new Splash();
            splash.setVisible(true);
        }

        // TODO Consider injection
        Environment environment = new Environment();
        environment.addPropertyChangeListener("update", splash);
        environment.initialise();
        
        Boolean running = true;

        Long oldTime = System.currentTimeMillis();
        Long newTime;
        Long time;

//        while(running) {
//
//            newTime = System.currentTimeMillis();
//            time = newTime - oldTime;
//
////                if(time > 0) {
////                }
//
//            oldTime = newTime;
//
//        }
        
        if(splash != null) {
            splash.dispose();
        }
        
    }
    
}

package org.bugz.aftershock;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.logging.Level;
import org.bugz.aftershock.client.splash.Splash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aftershock is a fork of Jake2 (a direct Java port of Quake II / id Tech 2
 * game engine).
 *
 * @author bugz
 */
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
public class Aftershock {

    private static final Logger logger = LoggerFactory.getLogger(Aftershock.class);
    
    public static void main(String[] args) {

        boolean server = false;

        // Checks there are arguments
        for(String argument : args) {

            // TODO may have to split arguments by space as they will be in the
            // format: aftershock -<arguments> <parameters>
            
            // Server argument (dedicated mode)
            if(argument.contains("s")) {
                logger.debug("Starting in dedicated (server) mode...");
                server = true;
            }
        }

        // TODO check configured items in properties file
        // TODO sets Locale either from arguments or properties
        if(!server) {
            logger.debug("Starting in client mode...");
            Splash dataDialog = new Splash();
            dataDialog.setVisible(true);
        }

        Environment.initialise();
        
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
    }

}

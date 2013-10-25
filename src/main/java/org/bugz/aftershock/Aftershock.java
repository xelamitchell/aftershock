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
 * -b <location> : basedir (can start with an alternative baseq2). Location is
 * the location of the alternative baseq2 directory.
 * -c : Client (starts both a server and client at the same time).
 * -v <level> : Verbose (logs are also output in standard output). Level is an
 * optional parameter to increase or decrease log level
 * -version : Version
 */
public class Aftershock {

    private static final Logger logger = LoggerFactory.getLogger(Aftershock.class);
    
    public static void main(String[] args) {

        boolean server = false;

        // Checks there are arguments
        for(String argument : args) {

            // Server argument (dedicated mode)
            if(argument.equals("-s")) {
                logger.debug("Starting in dedicated (server) mode...");
                server = true;
            }
        }

        // TODO check configured items in properties file
        // TODO Sets Locale either from arguments or properties
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

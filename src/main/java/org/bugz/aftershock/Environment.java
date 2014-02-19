package org.bugz.aftershock;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds environmental information for the engine.
 * 
 * @author bugz
 */
/*
 * (non-javadoc)
 * 
 * TODO Should this class initialise subsystems like QCommon?
 * 
 * Implement OS-Provider Strategy for items such as locating
 * user.home (problematic under Windoze:
 * http://stackoverflow.com/questions/585534/what-is-the-best-way-to-find-the-users-home-directory-in-java)
 * The code is currently quite nix-specific.
 * 
 * Initialise the subsystems of Aftershock:
 * 1. If argument -a is passed check against the location
 * 2. If an aftershock.properties exists then read and setup
 * 3. If not a real home then
 * 3a. Check that default .aftershock (nix), appdata (windoze) is set
 * 3b. If not set home up with a basic property file
 * 3c. Ask for aftershock (resource) folder
 * 3d. Write aftershock folder to property file
 * 4. If so read the properties
 * 5. Start subsystems:
 * 5a. Sound
 * 5b. Network
 * 5c. Rendering
 */
public final class Environment {
    
    private static final Logger logger = LoggerFactory.getLogger(Environment.class);
    
    public static final String JAVA_VERSION = System.getProperty("java.version");
    
    public static final String OS_ARCHITECTURE = System.getProperty("os.arch");
    public static final String OS_NAME = System.getProperty("os.name");
    
    public static final String HOME_DIRECTORY = System.getProperty("user.home");
    public static final String AFTERSHOCK_HOME = HOME_DIRECTORY + File.separator + ".aftershock";
    public static final String AFTERSHOCK_PROPERTIES = AFTERSHOCK_HOME + File.separator + "aftershock.properties";
    
    private Environment() {}
    
    public static void initialise() {
        
        // Check Aftershock home and create it if it does not exist
        File home = new File(AFTERSHOCK_HOME);
        Boolean homeExists = home.exists();
        if(!homeExists) {
            home.mkdirs();
        }
        logger.info("{} {}", home.getPath(), (homeExists) ? "found" : "created");
        
        // Copy the default properties into Aftershock home
        File properties = new File(AFTERSHOCK_PROPERTIES);
        Boolean propertiesExists = properties.exists();
        if(!propertiesExists) {
            createProperties(home, properties);
        }
        logger.info("aftershock.properties {}", (propertiesExists) ? "found" : "created");
        
        
        
    }
    
    /**
     * Creates a copy of the aftershock.properties file.
     * 
     * @param properties 
     */
    private static Boolean createProperties(File home, File properties) {
        
        String templateURI = "/aftershock.properties";
        File template;
        try {
            template = new File(Environment.class.getResource(templateURI).toURI());
        } catch(URISyntaxException urise) {
            logger.error("Unable to load aftershock.properties template", urise);
            template = new File(templateURI);
        }
        
        if(template.exists()) {
            try {
                Path copy = Files.copy(template.toPath(), properties.toPath(), LinkOption.NOFOLLOW_LINKS);
                logger.debug("{} successfully set up in {}", copy.toString(), home.getPath());
            } catch(IOException ioe) {
                logger.error("Could not copy {} to {}", template.getPath(), home.getPath(), ioe);
            }
        }
        
        return properties.exists();
    }
    
    /*
     * Environment exists so we load properties
     */
    private void setup() {
        
    }
    
}

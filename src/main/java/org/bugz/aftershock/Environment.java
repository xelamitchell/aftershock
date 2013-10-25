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
 * 1. Check that .aftershock (nix), appdata (lose) is set
 * 2. If not set home up with a basic property file
 * 2a. Ask for aftershock (resource) folder
 * 2b. Write aftershock folder to property file
 * 3. If so read the properties
 * 4. Start subsystems:
 * 4a. Sound
 * 4b. Network
 * 4c. Rendering
 */
public final class Environment {
    
    private static final Logger logger = LoggerFactory.getLogger(Environment.class);
    
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");
    public static final String JAVA_VERSION = System.getProperty("java.version");
    
    public static final String OS_ARCHITECTURE = System.getProperty("os.arch");
    public static final String OS_NAME = System.getProperty("os.name");
    
    public static final String HOME_DIRECTORY = System.getProperty("user.home");
    public static final String AFTERSHOCK_HOME = HOME_DIRECTORY + FILE_SEPARATOR + ".aftershock";
    public static final String AFTERSHOCK_PROPERTIES = AFTERSHOCK_HOME + FILE_SEPARATOR + "aftershock.properties";
    
    private Environment() {}
    
    public static void initialise() {
        
        // Check Aftershock home and create it if it does not exist
        File home = new File(AFTERSHOCK_HOME);
        if(!home.exists()) {
            boolean created = home.mkdirs();
            logger.debug("{} created", home.getPath(), created);
        }
        else {
            logger.debug("{} found", home.getPath());
        }
        
        // Copy the default properties into Aftershock home
        File properties = new File(AFTERSHOCK_PROPERTIES);
        
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
        
    }
    
    private File setupHome() {
        throw new UnsupportedOperationException("Not supported yet!");
    }
    
    /*
     * Environment does not exist: first run of the application
     */
    private void create() {
        
    }
    
    /*
     * Environment exists so we load properties
     */
    private void setup() {
        
    }
    
}

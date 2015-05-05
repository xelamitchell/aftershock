package org.bugz.aftershock;

import static org.bugz.aftershock.engine.Mode.CLIENT;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 *   a. Check that default .aftershock (nix), appdata (windoze) is set
 *   b. If not, set home up with a basic property file
 *   c. Ask for aftershock (resource) folder
 *   d. Write aftershock folder to property file
 * 4. If so read the properties
 * 5. Start subsystems:
 *   a. Sound
 *   b. Network
 *   c. Rendering
 */
/**
 * Holds environmental information for the engine.
 * 
 * @author bugz
 */
public final class Environment {
    
    private static final Logger logger = LoggerFactory.getLogger(Environment.class);
    
    public static final String JAVA_VERSION = System.getProperty("java.version");
    
    public static final String OS_ARCHITECTURE = System.getProperty("os.arch");
    public static final String OS_NAME = System.getProperty("os.name");
    
    public static final String HOME_DIRECTORY = System.getProperty("user.home");
    /*
     * (non-javadoc)
     * 
     * TODO home notation ('.' or hidden) is based on the OS running the
     * software.
     */
    public static final String AFTERSHOCK_HOME = HOME_DIRECTORY + File.separator + ".aftershock";
    public static final String AFTERSHOCK_PROPERTIES_NAME = "aftershock.properties";
    public static final String AFTERSHOCK_PROPERTIES_PATH = AFTERSHOCK_HOME + File.separator + AFTERSHOCK_PROPERTIES_NAME;
    
    private final Properties properties = new Properties();
    
    private final PropertyChangeSupport listeners;
    
    public Environment() {
        this.listeners = new PropertyChangeSupport(this);
    }
    
    public void initialise() {
        
//        sleep();
        
        File home = new File(AFTERSHOCK_HOME);
        if(!home.exists()) {
            home.mkdirs();
        }
        logger.info("{} {}", home.getPath(), (home.exists()) ? "found" : "created");
        listeners.firePropertyChange("update", null, home.getPath() + " " + ((home.exists()) ? "found" : "created"));
//        sleep();
        
        // Copy the default properties into Aftershock home
        File configuration = new File(AFTERSHOCK_PROPERTIES_PATH);
        if(!configuration.exists()) {
            create(home, configuration);
        }
        logger.info("aftershock.properties {}", (configuration.exists()) ? "found" : "created");
        listeners.firePropertyChange("update", null, configuration.getPath() + " " + ((configuration.exists()) ? "found" : "created"));
//        sleep();
        
        setup();
        logger.debug("{}", properties);
//        sleep();
        
    }
    
    //Creates a copy of the aftershock.properties template.
    private Boolean create(File home, File properties) {
        
        String uri = "/aftershock.properties";
        File template;
        try {
            template = new File(Environment.class.getResource(uri).toURI());
        } catch(URISyntaxException urise) {
            logger.error("Unable to load aftershock.properties template", urise);
            template = new File(uri);
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
    
    // Load environment from an existing aftershock.properties file.
    private void setup() {
        
        File configuration = new File(AFTERSHOCK_PROPERTIES_PATH);
        
        try(FileInputStream input = new FileInputStream(configuration)) {
            properties.load(input);
        } catch (IOException ioe) {
            logger.warn("Unable to load {}.", AFTERSHOCK_PROPERTIES_PATH, ioe);
        }
        
        // Set property defaults.
        
        final String AFTERSHOCK_RESOURCES = "aftershock.resources";
        if(StringUtils.isEmpty(properties.getProperty(AFTERSHOCK_RESOURCES))) {

            final String AFTERSHOCK_RESOURCES_PATH = AFTERSHOCK_HOME + File.separator + "resources";
            properties.setProperty(AFTERSHOCK_RESOURCES, AFTERSHOCK_RESOURCES_PATH);

            // Create the resource folder
            File resources = new File(AFTERSHOCK_RESOURCES_PATH);
            if(!resources.exists()) {
                resources.mkdirs();
                logger.debug("Resource folder successfully created.");
            }
        }
        
        final String AFTERSHOCK_MODE = "aftershock.mode";
        if(StringUtils.isEmpty(properties.getProperty(AFTERSHOCK_MODE))) {
            properties.setProperty(AFTERSHOCK_MODE, CLIENT.name());
        }
        
    }
    
    private void sleep() {
        
        try {
            Thread.sleep(350);
        } catch(InterruptedException ie) {
            logger.warn("Sleep interrupted.", ie);
        }
    }
    
    public List<PropertyChangeListener> getPropertyChangeListeners() {
        return Arrays.asList(listeners.getPropertyChangeListeners());
    }

    public List<PropertyChangeListener> getPropertyChangeListeners(String property) {
        return Arrays.asList(listeners.getPropertyChangeListeners(property));
    }
    
    public synchronized void setPropertyChangeListeners(Map<String, PropertyChangeListener> listeners) {
        
        for(String key : listeners.keySet()) {
            addPropertyChangeListener(key, listeners.get(key));
        }
    }
    
    public synchronized void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        this.listeners.addPropertyChangeListener(property, listener);
    }

    public synchronized void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        this.listeners.removePropertyChangeListener(property, listener);
    }
    
}

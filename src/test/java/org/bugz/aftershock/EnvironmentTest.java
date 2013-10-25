package org.bugz.aftershock;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentTest {
    
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentTest.class);
    
    @Test
    public void shouldRetrieveEnvironmentVariables() {
        
        logger.info("Home = {}", Environment.HOME_DIRECTORY);
        logger.info("Aftershock = {}", Environment.AFTERSHOCK_HOME);
        logger.info("{} : {}", Environment.OS_NAME, Environment.OS_ARCHITECTURE);
        logger.info("Java = {}", Environment.JAVA_VERSION);
        
    }
    
}

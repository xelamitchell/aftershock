package org.bugz.aftershock.engine.renderer.pak;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PakManagerTest {

    private static final Logger logger = LoggerFactory.getLogger(PakManagerTest.class);
    
    private static final String PAK_LOCATION = "/home/bugz/workspace/jake2/docs/files/baseq2/pak0.pak";
    
    private PakManager manager = new PakManager();
    
    @Test
    public void shouldLoadAPakFile() {
        
        Pak pak = null;
        try {
            pak = manager.load(PAK_LOCATION);
        } catch(FileNotFoundException fnfe) {
            logger.warn("Could not find file {}", PAK_LOCATION, fnfe);
        } catch(IOException ioe) {
            logger.warn("Could not read file {}", PAK_LOCATION, ioe);
        }
        
    }
    
}

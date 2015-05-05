package org.bugz.aftershock.client.splash;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Retrieves the start-up splash image.
 * 
 * @author bugz
 */
public class SplashImage extends Canvas {

    private static final Logger logger = LoggerFactory.getLogger(SplashImage.class);
    
    private Image image;

    SplashImage() {

        setSize(400, 200);
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/splash.png"));
        } catch(Exception e) {
            logger.warn("Unable to find splash image", e);
        }

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

}

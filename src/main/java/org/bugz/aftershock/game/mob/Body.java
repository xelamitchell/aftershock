package org.bugz.aftershock.game.mob;

/**
 * Defines body type.
 * 
 * @author bugz
 */
public class Body {
    
    /** Represents the body size which can be different based on body type. */
    public enum Size {
        
        FINE,
        DIMINUTIVE,
        TINY,
        SMALL,
        /** The mean size for most humanoid creatures. */
        MEDIUM,
        LARGE,
        HUGE,
        GARGANTUAN,
        COLOSSAL;
        
    }
    
}

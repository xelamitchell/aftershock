package org.bugz.aftershock.engine.renderer.pak;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A {@code PakFile} represents a file collected within a {@link Pak}.
 * 
 * @author bugz
 */
public class PakFile {
    
    private static final Integer NAME_SIZE = 56;
    private static final byte[] NAME_DEFINITION = new byte[NAME_SIZE];
    
    private final String name;
    private final Integer position;
    private final Integer length;

    public PakFile(String name, Integer position, Integer length) {
        this.name = name;
        this.position = position;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    public static byte[] getNameSize() {
        return NAME_DEFINITION;
    }
    
}

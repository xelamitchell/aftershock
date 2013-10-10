package org.bugz.aftershock.engine.renderer.pak;

import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The Quake2 package (.pak) format is a linear compression of a directory
 * system holding game assets and may be composed of multiple sources.
 *
 * @author bugz
 */
public class Pak {

    /**
     * To ensure that the directory being read is Pak, the header must contain a
     * magic number equal to "PACK".
     */
    private static final Integer MAGIC_NUMBER = (('K' << 24) + ('C' << 16) + ('A' << 8) + 'P');
    
    private static final Integer MAXIMUM_NUMBER_OF_FILES = 4096;
    private static final Integer PACK_SIZE = 64;
    
    private String name;
    // An access to the pak file itself
    private RandomAccessFile handle;
    private Map<String, PakFile> files;

    public Pak(String name, RandomAccessFile handle, Map<String, PakFile> files) {
        this.name = name;
        this.handle = handle;
        this.files = files;
    }

    public Pak(String name, RandomAccessFile handle) {
        this(name, handle, new HashMap<String, PakFile>());
    }
    
    public String getName() {
        return name;
    }

    /**
     * A reference to the .pak file itself for easy access.
     * 
     * @return 
     */
    public RandomAccessFile getHandle() {
        return handle;
    }

    public Map<String, PakFile> getFiles() {
        return files;
    }
    
    public void addFile(PakFile file) {
        this.files.put(file.getName(), file);
    }
    
    public static Boolean isPack(Integer id) {
        return MAGIC_NUMBER.equals(id);
    }
    
    /**
     * Checks whether the pak contains too many files.
     * <p>
     * <b>NOTE: Should use {@link #getLength(java.lang.Integer)} to calculate
     * the actual number of files for the pack before calling this method.</b>
     * </p>
     * 
     * @param length
     * @return 
     */
    public static Boolean tooManyFiles(Integer length) {
        return length > MAXIMUM_NUMBER_OF_FILES;
    }
    
    public static Integer getLength(Integer length) {
        return length / PACK_SIZE;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}

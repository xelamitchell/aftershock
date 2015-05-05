package org.bugz.aftershock.engine.renderer.pak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * (non-javadoc)
 * 
 * TODO The manager can do the following operations
 * initialise() -> loads all packs found in the user's home
 * load(packName) -> Makes paks available in memory
 * load(String filename) -> Retrieves a packed resource
 * read(packName) -> Returns RandomAccessFile for streaming information
 * write(packName, data) -> persists information into the pak
 * 
 * Ideally the pak would be loaded once on startup and could be reloaded by
 * console command.
 */
/**
 *
 * @author bugz
 */
public class PakManager {
    
    private static final Logger logger = LoggerFactory.getLogger(PakManager.class);
    
    private static final String READ = "r";

    private Map<String, Pak> packs = new HashMap<>();
    
    public Pak load(String packName) throws FileNotFoundException, IOException {
        
        RandomAccessFile pack = new RandomAccessFile(packName, READ);
        ByteBuffer handle;
        try (FileChannel channel = pack.getChannel()) {
            handle = channel.map(FileChannel.MapMode.READ_ONLY, 0, pack.length());
            handle.order(ByteOrder.LITTLE_ENDIAN);
        }
        
        if(handle.limit() < 1) {
            return null;
        }
        
        // Extracts pack's header information
        Integer id = handle.getInt();
        Integer offset = handle.getInt();
        Integer size = handle.getInt();
        
        // Validates the pack's contents
        if(!Pak.isPack(id)) {
            logger.error("{} is not a valid PAK file", packName);
        }
        
        // The number of files contained within this pack
        int fileCount = Pak.getFileCount(size);
        if(Pak.isTooBig(size)) {
            logger.error("{} has too many files: {} files counted", packName, fileCount);
        }
        
        // Moves the handle's cursor to the end of the pack's header
        handle.position(offset);
        
        final byte[] FILE_POSITON = PakFile.getNameSize();
        final String FILENAME = FILE_POSITON.toString().trim();
        Map<String, PakFile> files = new HashMap<>(fileCount);
        
        // Extracts the files collected within this pack
        for(int i = 0; i < Pak.getFileCount(size); i++) {
            
            // Moves the handle's cursor to the beginning of a file
            handle.get(FILE_POSITON);
            
            // Currently always overwrites the previously found file
            // Why?
            files.put(FILENAME, new PakFile(FILENAME, handle.getInt(), handle.getInt()));
        }
        
        Pak pak = new Pak(packName, pack, files);
        logger.info("Loaded {}", pak);
        
        return pak;
    }
    
    /**
     *
     * @param path
     *
     * @deprecated Needs to be moved out as PakManager is only related to the
     * management of .pak files not the filesystem itself
     */
    @Deprecated
    public void createHome(String path) {

        File directory = new File(path);
        if(directory.isDirectory() && !directory.exists()) {
            directory.mkdirs();
            logger.info("Created HOME at {}", path);
        }
        else {
            logger.info("HOME already exists");
        }
    }
    
}

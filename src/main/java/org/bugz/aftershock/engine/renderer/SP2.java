package org.bugz.aftershock.engine.renderer;

/**
 * Sprite file format.
 * 
 * @author bugz
 */
public class SP2 {
    
    /**
     * To ensure that the file being opened is an SP2 sprite, the header must
     * contain a magic number equal to "IDS2".
     */
    private static final int MAGIC_NUMBER = (('2' << 24) + ('S' << 16) + ('D' << 8) + 'I');
    /**
     * To ensure that the file being opened is an SP2 sprite, the file version
     * must be equal to 2.
     */
    private static final int VERSION = 2;
    
    /*
    // little-endian "IDS2"
    public static final int IDSPRITEHEADER = (('2'<<24)+('S'<<16)+('D'<<8)+'I');
    public static final int SPRITE_VERSION = 2;

    public static class dsprframe_t {
        
        public int width, height;
        public int origin_x, origin_y; // raster coordinates inside pic
        public String name; // name of pcx file (MAX_SKINNAME)

        public dsprframe_t(ByteBuffer b) {
            width = b.getInt();
            height = b.getInt();
            origin_x = b.getInt();
            origin_y = b.getInt();

            byte[] nameBuf = new byte[MAX_SKINNAME];
            b.get(nameBuf);
            name = new String(nameBuf).trim();
        }
        
    }

    public static class dsprite_t {
        
        public int ident;
        public int version;
        public int numframes;
        public dsprframe_t frames[]; // variable sized

        public dsprite_t(ByteBuffer b) {
            
            ident = b.getInt();
            version = b.getInt();
            numframes = b.getInt();

            frames = new dsprframe_t[numframes];
            for(int i=0; i < numframes; i++) {
                    frames[i] = new dsprframe_t(b);	
            }
        }
        
    }
     */
    
}

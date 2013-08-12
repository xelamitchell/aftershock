package org.bugz.aftershock.engine.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Personal Computer Exchange (PCX) image file format. Used for model textures.
 * 
 * @author bugz
 */
// TODO Could be Proxied and Flyweight as theoretically only a single instance
// of the model is necessary (multiple skins should be attachable to the model).
public class PCX {

    public PCX(ByteBuffer buffer) {
        decompose(buffer);
    }
    
    public PCX(byte[] bytes) {
        this(ByteBuffer.wrap(bytes));
    }
    
    private void decompose(ByteBuffer buffer) {
        
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        
    }
    
    private int unsign(short s) {
        return s & LITTLE_ENDIAN_SHIFT;
    }
    
    private static final int LITTLE_ENDIAN_SHIFT = 0xFFFF;
    private static final int PALETTE_SIZE = 48;
    private static final int FILLER_SIZE = 58;
    
/*
        // size of byte arrays
        static final int PALETTE_SIZE = 48;
        static final int FILLER_SIZE = 58;

        public byte manufacturer;
        public byte version;
        public byte encoding;
        public byte bits_per_pixel;
        public int xmin, ymin, xmax, ymax; // unsigned short
        public int hres, vres; // unsigned short
        public byte[] palette; //unsigned byte; size 48
        public byte reserved;
        public byte color_planes;
        public int bytes_per_line; // unsigned short
        public int palette_type; // unsigned short
        public byte[] filler; // size 58
        public ByteBuffer data; //unbounded data

        public pcx_t(byte[] dataBytes) {
            this(ByteBuffer.wrap(dataBytes));
        }

        public pcx_t(ByteBuffer b) {
            
            // is stored as little endian
            b.order(ByteOrder.LITTLE_ENDIAN);

            // fill header
            manufacturer = b.get();
            version = b.get();
            encoding = b.get();
            bits_per_pixel = b.get();
            xmin = b.getShort() & 0xffff;
            ymin = b.getShort() & 0xffff;
            xmax = b.getShort() & 0xffff;
            ymax = b.getShort() & 0xffff;
            hres = b.getShort() & 0xffff;
            vres = b.getShort() & 0xffff;
            b.get(palette = new byte[PALETTE_SIZE]);
            reserved = b.get();
            color_planes = b.get();
            bytes_per_line = b.getShort() & 0xffff;
            palette_type = b.getShort() & 0xffff;
            b.get(filler = new byte[FILLER_SIZE]);

            // fill data
            data = b.slice();
                
        }
 */
    
}

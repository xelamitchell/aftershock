package org.bugz.aftershock.engine.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Truevision Graphics Adapter (TGA) image file format. Used for sky planes.
 * 
 * @author bugz
 */
public class TGA {
    
    public TGA(ByteBuffer buffer) {
        decompose(buffer);
    }
    
    public TGA(byte[] bytes) {
        this(ByteBuffer.wrap(bytes));
    }
    
    private void decompose(ByteBuffer buffer) {
        
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        
    }
    
    private int unsign(short s) {
        return s & LITTLE_ENDIAN_SHIFT;
    }
    
    private static final int LITTLE_ENDIAN_SHIFT = 0xFFFF;
    
    /*
    public static class tga_t {

        // targa header
        public int id_length, colormap_type, image_type; // unsigned char
        public int colormap_index, colormap_length; // unsigned short
        public int colormap_size; // unsigned char
        public int x_origin, y_origin, width, height; // unsigned short
        public int pixel_size, attributes; // unsigned char

        public ByteBuffer data; // (un)compressed data

        public tga_t(byte[] dataBytes) {
            this(ByteBuffer.wrap(dataBytes));
        }

        public tga_t(ByteBuffer b) {
            
            // is stored as little endian
            b.order(ByteOrder.LITTLE_ENDIAN);

            // fill header
            id_length = b.get() & 0xFF;
            colormap_type = b.get() & 0xFF;
            image_type = b.get() & 0xFF;
            colormap_index = b.getShort() & 0xFFFF;
            colormap_length = b.getShort() & 0xFFFF;
            colormap_size = b.get() & 0xFF;
            x_origin = b.getShort() & 0xFFFF;
            y_origin = b.getShort() & 0xFFFF;
            width = b.getShort() & 0xFFFF;
            height = b.getShort() & 0xFFFF;
            pixel_size = b.get() & 0xFF;
            attributes = b.get() & 0xFF;

            // fill data
            data = b.slice();
            
        }			

    }
     */
    
}

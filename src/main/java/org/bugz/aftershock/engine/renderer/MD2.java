package org.bugz.aftershock.engine.renderer;

/**
 * Original Quake2 model format.
 * 
 * @author bugz
 */
public class MD2 {
    
    /**
     * To ensure that the file being opened is an MD2 model, the header must
     * contain a magic number equal to "IDP2".
     */
    private static final int MAGIC_NUMBER = (('2'<<24) + ('P'<<16) + ('D'<<8) + 'I');
    /**
     * To ensure that the file being opened is an MD2 model, the file version
     * must be equal to 8.
     */
    private static final int VERSION = 8;
    
    /*
    ========================================================================

    .MD2 triangle model file format

    ========================================================================

    public static final int IDALIASHEADER = (('2'<<24)+('P'<<16)+('D'<<8)+'I');
    public static final int ALIAS_VERSION = 8;

    public static final int MAX_TRIANGLES = 4096;
    public static final int MAX_VERTS = 2048;
    public static final int MAX_FRAMES = 512;
    public static final int MAX_MD2SKINS = 32;
    public static final int MAX_SKINNAME = 64;

    public static class dstvert_t {
        
        public short s;
        public short t;

        public dstvert_t(ByteBuffer b) {
            s = b.getShort();
            t = b.getShort();
        }
        
    }

    public static class dtriangle_t {
        
        public short index_xyz[] = { 0, 0, 0 };
        public short index_st[] = { 0, 0, 0 };

        public dtriangle_t(ByteBuffer b) {
            index_xyz[0] = b.getShort();
            index_xyz[1] = b.getShort();
            index_xyz[2] = b.getShort();

            index_st[0] = b.getShort();
            index_st[1] = b.getShort();
            index_st[2] = b.getShort();
        }
        
    }

    public static final int DTRIVERTX_V0 =  0;
    public static final int DTRIVERTX_V1 = 1;
    public static final int DTRIVERTX_V2 = 2;
    public static final int DTRIVERTX_LNI = 3;
    public static final int DTRIVERTX_SIZE = 4;

    public static class  daliasframe_t {

        public float[] scale = {0, 0, 0}; // multiply byte verts by this
        public float[] translate = {0, 0, 0};	// then add this
        public String name; // frame name from grabbing (size 16)
        public int[] verts;	// variable sized

        public daliasframe_t(ByteBuffer b) {
            scale[0] = b.getFloat(); scale[1] = b.getFloat(); scale[2] = b.getFloat();
            translate[0] = b.getFloat(); translate[1] = b.getFloat(); translate[2] = b.getFloat();
            byte[] nameBuf = new byte[16];
            b.get(nameBuf);
            name = new String(nameBuf).trim();
        }

    }

    //	   the glcmd format:
    //	   a positive integer starts a tristrip command, followed by that many
    //	   vertex structures.
    //	   a negative integer starts a trifan command, followed by -x vertexes
    //	   a zero indicates the end of the command list.
    //	   a vertex consists of a floating point s, a floating point t,
    //	   and an integer vertex index.

    public static class dmdl_t {
        
        public int ident;
        public int version;

        public int skinwidth;
        public int skinheight;
        public int framesize; // byte size of each frame

        public int num_skins;
        public int num_xyz;
        public int num_st; // greater than num_xyz for seams
        public int num_tris;
        public int num_glcmds; // dwords in strip/fan command list
        public int num_frames;

        public int ofs_skins; // each skin is a MAX_SKINNAME string
        public int ofs_st; // byte offset from start for stverts
        public int ofs_tris; // offset for dtriangles
        public int ofs_frames; // offset for first frame
        public int ofs_glcmds;
        public int ofs_end; // end of file

        // wird extra gebraucht
        public String[] skinNames;
        public dstvert_t[] stVerts;
        public dtriangle_t[] triAngles;
        public int[] glCmds;
        public daliasframe_t[] aliasFrames;

        public dmdl_t(ByteBuffer b) {

            ident = b.getInt();
            version = b.getInt();

            skinwidth = b.getInt();
            skinheight = b.getInt();
            framesize = b.getInt(); // byte size of each frame

            num_skins = b.getInt();
            num_xyz = b.getInt();
            num_st = b.getInt(); // greater than num_xyz for seams
            num_tris = b.getInt();
            num_glcmds = b.getInt(); // dwords in strip/fan command list
            num_frames = b.getInt();

            ofs_skins = b.getInt(); // each skin is a MAX_SKINNAME string
            ofs_st = b.getInt(); // byte offset from start for stverts
            ofs_tris = b.getInt(); // offset for dtriangles
            ofs_frames = b.getInt(); // offset for first frame
            ofs_glcmds = b.getInt();
            ofs_end = b.getInt(); // end of file

        }

        // new members for vertex array handling
        public FloatBuffer textureCoordBuf = null;
        public IntBuffer vertexIndexBuf = null;
        public int[] counts = null;
        public IntBuffer[] indexElements = null;
        
    }
    */
    
}

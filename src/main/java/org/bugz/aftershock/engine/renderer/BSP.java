package org.bugz.aftershock.engine.renderer;

/**
 * Binary Space Partitioning (BSP) file format. Used to generate fast rendering
 * maps.
 * 
 * @author bugz
 */
public class BSP {
    
    /*
    public static final int IDBSPHEADER = (('P'<<24)+('S'<<16)+('B'<<8)+'I');

    // =============================================================================

    public static class dheader_t {

        public dheader_t(ByteBuffer bb) {
            
            bb.order(ByteOrder.LITTLE_ENDIAN);
            this.ident = bb.getInt();
            this.version = bb.getInt();

            for(int n = 0; n < Defines.HEADER_LUMPS; n++)
                lumps[n] = new lump_t(bb.getInt(), bb.getInt());

        }

        public int ident;
        public int version;
        public lump_t lumps[] = new lump_t[Defines.HEADER_LUMPS];
        
    }

    public static class dmodel_t {

        public dmodel_t(ByteBuffer bb) {
            
            bb.order(ByteOrder.LITTLE_ENDIAN);

            for(int j = 0; j < 3; j++)
                mins[j] = bb.getFloat();

            for(int j = 0; j < 3; j++)
                maxs[j] = bb.getFloat();

            for(int j = 0; j < 3; j++)
                origin[j] = bb.getFloat();

            headnode = bb.getInt();
            firstface = bb.getInt();
            numfaces = bb.getInt();
            
        }
        
        public float mins[] = { 0, 0, 0 };
        public float maxs[] = { 0, 0, 0 };
        public float origin[] = { 0, 0, 0 }; // for sounds or lights
        public int headnode;
        public int firstface, numfaces; // submodels just draw faces
        // without walking the bsp tree

        public static int SIZE = 3 * 4 + 3 * 4 + 3 * 4 + 4 + 8;
        
    }

    public static class dvertex_t {

        public static final int SIZE = 3 * 4; // 3 mal 32 bit float 

        public float[] point = { 0, 0, 0 };

        public dvertex_t(ByteBuffer b) {
            point[0] = b.getFloat();
            point[1] = b.getFloat();
            point[2] = b.getFloat();
        }
        
    }

    // planes (x&~1) and (x&~1)+1 are always opposites
    public static class dplane_t {

        public dplane_t(ByteBuffer bb) {
            bb.order(ByteOrder.LITTLE_ENDIAN);

            normal[0] = (bb.getFloat());
            normal[1] = (bb.getFloat());
            normal[2] = (bb.getFloat());

            dist = (bb.getFloat());
            type = (bb.getInt());
        }

        public float normal[] = { 0, 0, 0 };
        public float dist;
        public int type; // PLANE_X - PLANE_ANYZ ?remove? trivial to regenerate

        public static final int SIZE = 3 * 4 + 4 + 4;
        
    }

    public static class dnode_t {

        public dnode_t(ByteBuffer bb) {

            bb.order(ByteOrder.LITTLE_ENDIAN);
            planenum = bb.getInt();

            children[0] = bb.getInt();
            children[1] = bb.getInt();

            for(int j = 0; j < 3; j++)
                mins[j] = bb.getShort();

            for(int j = 0; j < 3; j++)
                maxs[j] = bb.getShort();

            firstface = bb.getShort() & 0xffff;
            numfaces = bb.getShort() & 0xffff;

        }

        public int planenum;
        public int children[] = { 0, 0 };
        // negative numbers are -(leafs+1), not nodes
        public short mins[] = { 0, 0, 0 }; // for frustom culling
        public short maxs[] = { 0, 0, 0 };

        
        // unsigned short	firstface;
        // unsigned short	numfaces;	// counting both sides

        public int firstface;
        public int numfaces;

        public static int SIZE = 4 + 8 + 6 + 6 + 2 + 2; // counting both sides
        
    }

    // note that edge 0 is never used, because negative edge nums are used for
    // counterclockwise use of the edge in a face

    public static class dedge_t {
        // unsigned short v[2];
        int v[] = { 0, 0 };
    }

    public static class dface_t {

        public static final int SIZE = 4 * Defines.SIZE_OF_SHORT + 2 * Defines.SIZE_OF_INT + Defines.MAXLIGHTMAPS;

        //unsigned short	planenum;
        public int planenum;
        public short side;

        public int firstedge; // we must support > 64k edges
        public short numedges;
        public short texinfo;

        // lighting info
        public byte styles[] = new byte[Defines.MAXLIGHTMAPS];
        public int lightofs; // start of [numstyles*surfsize] samples

        public dface_t(ByteBuffer b) {
            planenum = b.getShort() & 0xFFFF;
            side = b.getShort();
            firstedge = b.getInt();
            numedges = b.getShort();
            texinfo = b.getShort();
            b.get(styles);
            lightofs = b.getInt();
        }

    }

    public static class dleaf_t {

        public dleaf_t(byte[] cmod_base, int i, int j) {
            this(ByteBuffer.wrap(cmod_base, i, j).order(ByteOrder.LITTLE_ENDIAN));
        }

        public dleaf_t(ByteBuffer bb) {
            contents = bb.getInt();
            cluster = bb.getShort();
            area = bb.getShort();

            mins[0] = bb.getShort();
            mins[1] = bb.getShort();
            mins[2] = bb.getShort();

            maxs[0] = bb.getShort();
            maxs[1] = bb.getShort();
            maxs[2] = bb.getShort();

            firstleafface = bb.getShort() & 0xffff;
            numleaffaces = bb.getShort() & 0xffff;

            firstleafbrush = bb.getShort() & 0xffff;
            numleafbrushes = bb.getShort() & 0xffff;
        }

        public static final int SIZE = 4 + 8 * 2 + 4 * 2;

        public int contents; // OR of all brushes (not needed?)

        public short cluster;
        public short area;

        public short mins[] = { 0, 0, 0 }; // for frustum culling
        public short maxs[] = { 0, 0, 0 };

        public int firstleafface; // unsigned short
        public int numleaffaces; // unsigned short

        public int firstleafbrush; // unsigned short
        public int numleafbrushes; // unsigned short
        
    }

    public static class dbrushside_t {

        public dbrushside_t(ByteBuffer bb) {
            bb.order(ByteOrder.LITTLE_ENDIAN);
            planenum = bb.getShort() & 0xffff;
            texinfo = bb.getShort();
        }

        //unsigned short planenum;
        int planenum; // facing out of the leaf

        short texinfo;

        public static int SIZE = 4;
        
    }

    public static class dbrush_t {

        public dbrush_t(ByteBuffer bb) {
            bb.order(ByteOrder.LITTLE_ENDIAN);
            firstside = bb.getInt();
            numsides = bb.getInt();
            contents = bb.getInt();
        }

        public static int SIZE = 3 * 4;

        int firstside;
        int numsides;
        int contents;
        
    }

    //	#define	ANGLE_UP	-1
    //	#define	ANGLE_DOWN	-2

    // the visibility lump consists of a header with a count, then
    // byte offsets for the PVS and PHS of each cluster, then the raw
    // compressed bit vectors
    // #define	DVIS_PVS	0
    // #define	DVIS_PHS	1

    public static class dvis_t {

        public dvis_t(ByteBuffer bb) {
            
            numclusters = bb.getInt();
            bitofs = new int[numclusters][2];

            for(int i = 0; i < numclusters; i++) {
                bitofs[i][0] = bb.getInt();
                bitofs[i][1] = bb.getInt();
            }
        }

        public int numclusters;
        public int bitofs[][] = new int[8][2]; // bitofs[numclusters][2]
        
    }

    // each area has a list of portals that lead into other areas
    // when portals are closed, other areas may not be visible or
    // hearable even if the vis info says that it should be

    public static class dareaportal_t {

        public dareaportal_t() {}

        public dareaportal_t(ByteBuffer bb) {
            bb.order(ByteOrder.LITTLE_ENDIAN);
            portalnum = bb.getInt();
            otherarea = bb.getInt();
        }

        int portalnum;
        int otherarea;

        public static int SIZE = 8;
        
    }

    public static class darea_t {

        public darea_t(ByteBuffer bb) {
            bb.order(ByteOrder.LITTLE_ENDIAN);
            numareaportals = bb.getInt();
            firstareaportal = bb.getInt();
        }
        
        int numareaportals;
        int firstareaportal;

        public static int SIZE = 8;
        
    }
     */
    
}

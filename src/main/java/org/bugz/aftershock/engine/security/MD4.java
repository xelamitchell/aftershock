package org.bugz.aftershock.engine.security;

import java.security.MessageDigest;

/**
 * Represents MD4 Message-Digest algorithm.
 * <p/>
 * <b>NOTE:</b> This algorithm's security is considered severely compromised.
 * 
 * @author bugz
 */
public class MD4 extends MessageDigest implements Cloneable {

    public MD4() {
        super("MD4");
        engineReset();
    }

    /**
     * This constructor implements cloneability of this class.
     * 
     * @param md4
     */
    private MD4(MD4 md4) {
        this();

        context = (int[]) md4.context.clone();
        buffer = (byte[]) md4.buffer.clone();
        count = md4.count;

    }

    /**
     * Returns a copy of this MD object.
     */
    // TODO Needs to be refactored to a more appropriate implementation
    public Object clone() {
        return new MD4(this);
    }

    /**
     * Resets this object disregarding any temporary data present at the time
     * of the invocation of this call.
     */
    @Override
    public void engineReset() {

        // initial values of MD4 i.e. a, b, c, d
        // as per rfc-1320; they are low-order byte first
        context[0] = 0x67452301;
        context[1] = 0xEFCDAB89;
        context[2] = 0x98BADCFE;
        context[3] = 0x10325476;
        count = 0L;

        for (int i = 0; i < BLOCK_LENGTH; i++) {
            buffer[i] = 0;
        }

    }

    /**
     * Continues an MD4 message digest using the input byte.
     * 
     * @param b
     */
    @Override
    public void engineUpdate(byte b) {

        // compute number of bytes still unhashed; ie. present in buffer
        int i = (int) (count % BLOCK_LENGTH);
        count++; // update number of bytes
        buffer[i] = b;

        if (i == BLOCK_LENGTH - 1) {
            transform(buffer, 0);
        }

    }

    /**
     * MD4 block update operation.
     * <p/>
     * Continues an MD4 message digest operation, by filling the buffer,
     * {@code transform}ing data in 512-bit message block(s), updating the variables
     * context and count, and buffering the remaining bytes in buffer for the
     * next update or finish.
     *
     * @param input input block
     * @param offset start of meaningful bytes in input
     * @param length count of bytes in input block to consider
     */
    @Override
    public void engineUpdate(byte[] input, int offset, int length) {

        // make sure we don't exceed input's allocated size or length
        if (offset < 0 || length < 0 || (long) offset + length > input.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        // compute number of bytes still unhashed; ie. present in buffer
        int bufferNdx = (int) (count % BLOCK_LENGTH);
        count += length; // update number of bytes
        int partLen = BLOCK_LENGTH - bufferNdx;
        int i = 0;

        if (length >= partLen) {

            System.arraycopy(input, offset, buffer, bufferNdx, partLen);

            transform(buffer, 0);

            for (i = partLen; i + BLOCK_LENGTH - 1 < length; i += BLOCK_LENGTH) {
                transform(input, offset + i);
            }

            bufferNdx = 0;

        }

        // buffer remaining input
        if (i < length) {
            System.arraycopy(input, offset + i, buffer, bufferNdx, length - i);
        }

    }

    /**
     * Completes the hash computation by performing final operations such
     * as padding. At the return of this engineDigest, the MD4 engine is
     * reset.
     *
     * @return the array of bytes for the resulting hash value.
     */
    @Override
    public byte[] engineDigest() {

        // pad output to 56 mod 64; as RFC1320 puts it: congruent to 448 mod 512
        int bufferNdx = (int) (count % BLOCK_LENGTH);
        int padding = (bufferNdx < 56) ? (56 - bufferNdx) : (120 - bufferNdx);

        // padding is alwas binary 1 followed by binary 0s
        byte[] tail = new byte[padding + 8];
        tail[0] = (byte) 0x80;

        // append length before final transform:
        // save number of bits, casting the long to an array of 8 bytes
        // save low-order byte first.
        for (int i = 0; i < 8; i++) {
            tail[padding + i] = (byte) ((count * 8) >>> (8 * i));
        }

        engineUpdate(tail, 0, tail.length);

        byte[] result = new byte[16];
        // cast this MD4's context (array of 4 ints) into an array of 16 bytes.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i * 4 + j] = (byte) (context[i] >>> (8 * j));
            }
        }

        // reset the engine
        engineReset();

        return result;
    }

    /**
     * MD4 basic transformation.
     * <p/>
     * Transforms context based on 512 bits from input block starting
     * from the offset'th byte.
     *
     * @param block input sub-array.
     * @param offset starting position of sub-array.
     */
    private void transform(byte[] block, int offset) {

        // encodes 64 bytes from input block into an array of 16 32-bit
        // entities. Use A as a temporary variable.
        for (int i = 0; i < 16; i++) {
            temporary[i] = (block[offset++] & 0xFF) | (block[offset++] & 0xFF)
                    << 8 | (block[offset++] & 0xFF)
                    << 16 | (block[offset++] & 0xFF)
                    << 24;
        }

        int A = context[0];
        int B = context[1];
        int C = context[2];
        int D = context[3];

        A = FF(A, B, C, D, temporary[0], 3);
        D = FF(D, A, B, C, temporary[1], 7);
        C = FF(C, D, A, B, temporary[2], 11);
        B = FF(B, C, D, A, temporary[3], 19);
        A = FF(A, B, C, D, temporary[4], 3);
        D = FF(D, A, B, C, temporary[5], 7);
        C = FF(C, D, A, B, temporary[6], 11);
        B = FF(B, C, D, A, temporary[7], 19);
        A = FF(A, B, C, D, temporary[8], 3);
        D = FF(D, A, B, C, temporary[9], 7);
        C = FF(C, D, A, B, temporary[10], 11);
        B = FF(B, C, D, A, temporary[11], 19);
        A = FF(A, B, C, D, temporary[12], 3);
        D = FF(D, A, B, C, temporary[13], 7);
        C = FF(C, D, A, B, temporary[14], 11);
        B = FF(B, C, D, A, temporary[15], 19);

        A = GG(A, B, C, D, temporary[0], 3);
        D = GG(D, A, B, C, temporary[4], 5);
        C = GG(C, D, A, B, temporary[8], 9);
        B = GG(B, C, D, A, temporary[12], 13);
        A = GG(A, B, C, D, temporary[1], 3);
        D = GG(D, A, B, C, temporary[5], 5);
        C = GG(C, D, A, B, temporary[9], 9);
        B = GG(B, C, D, A, temporary[13], 13);
        A = GG(A, B, C, D, temporary[2], 3);
        D = GG(D, A, B, C, temporary[6], 5);
        C = GG(C, D, A, B, temporary[10], 9);
        B = GG(B, C, D, A, temporary[14], 13);
        A = GG(A, B, C, D, temporary[3], 3);
        D = GG(D, A, B, C, temporary[7], 5);
        C = GG(C, D, A, B, temporary[11], 9);
        B = GG(B, C, D, A, temporary[15], 13);

        A = HH(A, B, C, D, temporary[0], 3);
        D = HH(D, A, B, C, temporary[8], 9);
        C = HH(C, D, A, B, temporary[4], 11);
        B = HH(B, C, D, A, temporary[12], 15);
        A = HH(A, B, C, D, temporary[2], 3);
        D = HH(D, A, B, C, temporary[10], 9);
        C = HH(C, D, A, B, temporary[6], 11);
        B = HH(B, C, D, A, temporary[14], 15);
        A = HH(A, B, C, D, temporary[1], 3);
        D = HH(D, A, B, C, temporary[9], 9);
        C = HH(C, D, A, B, temporary[5], 11);
        B = HH(B, C, D, A, temporary[13], 15);
        A = HH(A, B, C, D, temporary[3], 3);
        D = HH(D, A, B, C, temporary[11], 9);
        C = HH(C, D, A, B, temporary[7], 11);
        B = HH(B, C, D, A, temporary[15], 15);

        context[0] += A;
        context[1] += B;
        context[2] += C;
        context[3] += D;

    }

    // The basic MD4 atomic functions.
    private int FF(int a, int b, int c, int d, int x, int s) {
        int t = a + ((b & c) | (~b & d)) + x;
        return t << s | t >>> (32 - s);
    }

    private int GG(int a, int b, int c, int d, int x, int s) {
        int t = a + ((b & (c | d)) | (c & d)) + x + 0x5A827999;
        return t << s | t >>> (32 - s);
    }

    private int HH(int a, int b, int c, int d, int x, int s) {
        int t = a + (b ^ c ^ d) + x + 0x6ED9EBA1;
        return t << s | t >>> (32 - s);
    }
    
    /**
     * Bugfixed, now works prima (RST).
     */
//    public static int Com_BlockChecksum(byte[] buffer, int length) {
//
//        int val;
//        MD4 md4 = new MD4();
//
//        md4.engineUpdate(buffer, 0, length);
//        byte data[] = md4.engineDigest();		
//        ByteBuffer bb = ByteBuffer.wrap(data);
//        bb.order(ByteOrder.LITTLE_ENDIAN);
//        val = bb.getInt() ^ bb.getInt() ^ bb.getInt() ^ bb.getInt();
//        
//        return val;
//    }
    
    /** 4 32-bit words (interim result). */
    private int[] context = new int[4];
    /** Number of bytes processed so far mod. 2 power of 64. */
    private long count;
    /** 512 bits input buffer = 16 x 32-bit words holds until reaches 512 bits. */
    private byte[] buffer = new byte[BLOCK_LENGTH];
    /** 512 bits work buffer = 16 x 32-bit words. */
    private int[] temporary = new int[16];
    
    /** The size in bytes of the input block to the transformation algorithm. */
    private static final int BLOCK_LENGTH = 64; // = 512 / 8;
    
}

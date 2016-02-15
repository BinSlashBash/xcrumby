package com.squareup.okhttp.internal.spdy;

import android.support.v4.view.MotionEventCompat;
import com.google.android.gms.location.LocationRequest;
import java.io.IOException;
import java.io.OutputStream;
import org.json.zip.JSONzip;

class Huffman {
    private static final int[] CODES;
    private static final byte[] CODE_LENGTHS;
    private static final Huffman INSTANCE;
    private final Node root;

    private static final class Node {
        private final Node[] children;
        private final int symbol;
        private final int terminalBits;

        Node() {
            this.children = new Node[JSONzip.end];
            this.symbol = 0;
            this.terminalBits = 0;
        }

        Node(int symbol, int bits) {
            this.children = null;
            this.symbol = symbol;
            int b = bits & 7;
            if (b == 0) {
                b = 8;
            }
            this.terminalBits = b;
        }
    }

    static {
        CODES = new int[]{67108794, 67108795, 67108796, 67108797, 67108798, 67108799, 67108800, 67108801, 67108802, 67108803, 67108804, 67108805, 67108806, 67108807, 67108808, 67108809, 67108810, 67108811, 67108812, 67108813, 67108814, 67108815, 67108816, 67108817, 67108818, 67108819, 67108820, 67108821, 67108822, 67108823, 67108824, 67108825, 6, 8188, 496, 16380, 32764, 30, 100, 8189, 1018, 497, 1019, 1020, 101, LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, 31, 7, 0, 1, 2, 8, 32, 33, 34, 35, 36, 37, 38, 236, 131068, 39, 32765, 1021, 32766, 103, 237, 238, LocationRequest.PRIORITY_LOW_POWER, 239, LocationRequest.PRIORITY_NO_POWER, 106, 498, 240, 499, 500, 501, 107, 108, 241, 242, 502, 503, 109, 40, 243, 504, 505, 244, 506, 507, 2044, 67108826, 2045, 16381, 110, 262142, 9, 111, 10, 41, 11, 112, 42, 43, 12, 245, 246, 44, 45, 46, 13, 47, 508, 48, 49, 14, 113, 114, 115, 116, 117, 247, 131069, 4092, 131070, 4093, 67108827, 67108828, 67108829, 67108830, 67108831, 67108832, 67108833, 67108834, 67108835, 67108836, 67108837, 67108838, 67108839, 67108840, 67108841, 67108842, 67108843, 67108844, 67108845, 67108846, 67108847, 67108848, 67108849, 67108850, 67108851, 67108852, 67108853, 67108854, 67108855, 67108856, 67108857, 67108858, 67108859, 67108860, 67108861, 67108862, 67108863, 33554304, 33554305, 33554306, 33554307, 33554308, 33554309, 33554310, 33554311, 33554312, 33554313, 33554314, 33554315, 33554316, 33554317, 33554318, 33554319, 33554320, 33554321, 33554322, 33554323, 33554324, 33554325, 33554326, 33554327, 33554328, 33554329, 33554330, 33554331, 33554332, 33554333, 33554334, 33554335, 33554336, 33554337, 33554338, 33554339, 33554340, 33554341, 33554342, 33554343, 33554344, 33554345, 33554346, 33554347, 33554348, 33554349, 33554350, 33554351, 33554352, 33554353, 33554354, 33554355, 33554356, 33554357, 33554358, 33554359, 33554360, 33554361, 33554362, 33554363, 33554364, 33554365, 33554366, 33554367, 33554368, 33554369, 33554370, 33554371, 33554372, 33554373, 33554374, 33554375, 33554376, 33554377, 33554378, 33554379, 33554380, 33554381, 33554382, 33554383, 33554384, 33554385, 33554386, 33554387, 33554388, 33554389, 33554390, 33554391, 33554392, 33554393, 33554394, 33554395};
        CODE_LENGTHS = new byte[]{(byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 5, (byte) 13, (byte) 9, (byte) 14, (byte) 15, (byte) 6, (byte) 7, (byte) 13, (byte) 10, (byte) 9, (byte) 10, (byte) 10, (byte) 7, (byte) 7, (byte) 6, (byte) 5, (byte) 4, (byte) 4, (byte) 4, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 8, (byte) 17, (byte) 6, (byte) 15, (byte) 10, (byte) 15, (byte) 7, (byte) 8, (byte) 8, (byte) 7, (byte) 8, (byte) 7, (byte) 7, (byte) 9, (byte) 8, (byte) 9, (byte) 9, (byte) 9, (byte) 7, (byte) 7, (byte) 8, (byte) 8, (byte) 9, (byte) 9, (byte) 7, (byte) 6, (byte) 8, (byte) 9, (byte) 9, (byte) 8, (byte) 9, (byte) 9, (byte) 11, (byte) 26, (byte) 11, (byte) 14, (byte) 7, (byte) 18, (byte) 5, (byte) 7, (byte) 5, (byte) 6, (byte) 5, (byte) 7, (byte) 6, (byte) 6, (byte) 5, (byte) 8, (byte) 8, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 6, (byte) 9, (byte) 6, (byte) 6, (byte) 5, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 8, (byte) 17, (byte) 12, (byte) 17, (byte) 12, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 26, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25, (byte) 25};
        INSTANCE = new Huffman();
    }

    public static Huffman get() {
        return INSTANCE;
    }

    private Huffman() {
        this.root = new Node();
        buildTree();
    }

    void encode(byte[] data, OutputStream out) throws IOException {
        long current = 0;
        int n = 0;
        for (byte b : data) {
            int b2 = b & MotionEventCompat.ACTION_MASK;
            int code = CODES[b2];
            int nbits = CODE_LENGTHS[b2];
            current = (current << nbits) | ((long) code);
            n += nbits;
            while (n >= 8) {
                n -= 8;
                out.write((int) (current >> n));
            }
        }
        if (n > 0) {
            out.write((int) ((current << (8 - n)) | ((long) (MotionEventCompat.ACTION_MASK >>> n))));
        }
    }

    int encodedLength(byte[] bytes) {
        long len = 0;
        for (byte b : bytes) {
            len += (long) CODE_LENGTHS[b & MotionEventCompat.ACTION_MASK];
        }
        return (int) ((7 + len) >> 3);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    byte[] decode(byte[] r9) throws java.io.IOException {
        /*
        r8 = this;
        r1 = new java.io.ByteArrayOutputStream;
        r1.<init>();
        r6 = r8.root;
        r3 = 0;
        r5 = 0;
        r4 = 0;
    L_0x000a:
        r7 = r9.length;
        if (r4 >= r7) goto L_0x0050;
    L_0x000d:
        r7 = r9[r4];
        r0 = r7 & 255;
        r7 = r3 << 8;
        r3 = r7 | r0;
        r5 = r5 + 8;
    L_0x0017:
        r7 = 8;
        if (r5 < r7) goto L_0x003f;
    L_0x001b:
        r7 = r5 + -8;
        r7 = r3 >>> r7;
        r2 = r7 & 255;
        r7 = r6.children;
        r6 = r7[r2];
        r7 = r6.children;
        if (r7 != 0) goto L_0x003c;
    L_0x002d:
        r7 = r6.symbol;
        r1.write(r7);
        r7 = r6.terminalBits;
        r5 = r5 - r7;
        r6 = r8.root;
        goto L_0x0017;
    L_0x003c:
        r5 = r5 + -8;
        goto L_0x0017;
    L_0x003f:
        r4 = r4 + 1;
        goto L_0x000a;
    L_0x0042:
        r7 = r6.symbol;
        r1.write(r7);
        r7 = r6.terminalBits;
        r5 = r5 - r7;
        r6 = r8.root;
    L_0x0050:
        if (r5 <= 0) goto L_0x006a;
    L_0x0052:
        r7 = 8 - r5;
        r7 = r3 << r7;
        r2 = r7 & 255;
        r7 = r6.children;
        r6 = r7[r2];
        r7 = r6.children;
        if (r7 != 0) goto L_0x006a;
    L_0x0064:
        r7 = r6.terminalBits;
        if (r7 <= r5) goto L_0x0042;
    L_0x006a:
        r7 = r1.toByteArray();
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.Huffman.decode(byte[]):byte[]");
    }

    private void buildTree() {
        for (int i = 0; i < CODE_LENGTHS.length; i++) {
            addCode(i, CODES[i], CODE_LENGTHS[i]);
        }
    }

    private void addCode(int sym, int code, byte len) {
        Node terminal = new Node(sym, len);
        Node current = this.root;
        while (len > 8) {
            len = (byte) (len - 8);
            int i = (code >>> len) & MotionEventCompat.ACTION_MASK;
            if (current.children == null) {
                throw new IllegalStateException("invalid dictionary: prefix not unique");
            }
            if (current.children[i] == null) {
                current.children[i] = new Node();
            }
            current = current.children[i];
        }
        int shift = 8 - len;
        int start = (code << shift) & MotionEventCompat.ACTION_MASK;
        int end = 1 << shift;
        for (i = start; i < start + end; i++) {
            current.children[i] = terminal;
        }
    }
}

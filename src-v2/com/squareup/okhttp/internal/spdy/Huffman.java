/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class Huffman {
    private static final int[] CODES = new int[]{67108794, 67108795, 67108796, 67108797, 67108798, 67108799, 67108800, 67108801, 67108802, 67108803, 67108804, 67108805, 67108806, 67108807, 67108808, 67108809, 67108810, 67108811, 67108812, 67108813, 67108814, 67108815, 67108816, 67108817, 67108818, 67108819, 67108820, 67108821, 67108822, 67108823, 67108824, 67108825, 6, 8188, 496, 16380, 32764, 30, 100, 8189, 1018, 497, 1019, 1020, 101, 102, 31, 7, 0, 1, 2, 8, 32, 33, 34, 35, 36, 37, 38, 236, 131068, 39, 32765, 1021, 32766, 103, 237, 238, 104, 239, 105, 106, 498, 240, 499, 500, 501, 107, 108, 241, 242, 502, 503, 109, 40, 243, 504, 505, 244, 506, 507, 2044, 67108826, 2045, 16381, 110, 262142, 9, 111, 10, 41, 11, 112, 42, 43, 12, 245, 246, 44, 45, 46, 13, 47, 508, 48, 49, 14, 113, 114, 115, 116, 117, 247, 131069, 4092, 131070, 4093, 67108827, 67108828, 67108829, 67108830, 67108831, 67108832, 67108833, 67108834, 67108835, 67108836, 67108837, 67108838, 67108839, 67108840, 67108841, 67108842, 67108843, 67108844, 67108845, 67108846, 67108847, 67108848, 67108849, 67108850, 67108851, 67108852, 67108853, 67108854, 67108855, 67108856, 67108857, 67108858, 67108859, 67108860, 67108861, 67108862, 67108863, 33554304, 33554305, 33554306, 33554307, 33554308, 33554309, 33554310, 33554311, 33554312, 33554313, 33554314, 33554315, 33554316, 33554317, 33554318, 33554319, 33554320, 33554321, 33554322, 33554323, 33554324, 33554325, 33554326, 33554327, 33554328, 33554329, 33554330, 33554331, 33554332, 33554333, 33554334, 33554335, 33554336, 33554337, 33554338, 33554339, 33554340, 33554341, 33554342, 33554343, 33554344, 33554345, 33554346, 33554347, 33554348, 33554349, 33554350, 33554351, 33554352, 33554353, 33554354, 33554355, 33554356, 33554357, 33554358, 33554359, 33554360, 33554361, 33554362, 33554363, 33554364, 33554365, 33554366, 33554367, 33554368, 33554369, 33554370, 33554371, 33554372, 33554373, 33554374, 33554375, 33554376, 33554377, 33554378, 33554379, 33554380, 33554381, 33554382, 33554383, 33554384, 33554385, 33554386, 33554387, 33554388, 33554389, 33554390, 33554391, 33554392, 33554393, 33554394, 33554395};
    private static final byte[] CODE_LENGTHS = new byte[]{26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 5, 13, 9, 14, 15, 6, 7, 13, 10, 9, 10, 10, 7, 7, 6, 5, 4, 4, 4, 5, 6, 6, 6, 6, 6, 6, 6, 8, 17, 6, 15, 10, 15, 7, 8, 8, 7, 8, 7, 7, 9, 8, 9, 9, 9, 7, 7, 8, 8, 9, 9, 7, 6, 8, 9, 9, 8, 9, 9, 11, 26, 11, 14, 7, 18, 5, 7, 5, 6, 5, 7, 6, 6, 5, 8, 8, 6, 6, 6, 5, 6, 9, 6, 6, 5, 7, 7, 7, 7, 7, 8, 17, 12, 17, 12, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
    private static final Huffman INSTANCE = new Huffman();
    private final Node root = new Node();

    private Huffman() {
        this.buildTree();
    }

    private void addCode(int n2, int n3, byte by2) {
        Node node = new Node(n2, by2);
        Node node2 = this.root;
        while (by2 > 8) {
            by2 = (byte)(by2 - 8);
            n2 = n3 >>> by2 & 255;
            if (node2.children == null) {
                throw new IllegalStateException("invalid dictionary: prefix not unique");
            }
            if (node2.children[n2] == null) {
                Node.access$000((Node)node2)[n2] = new Node();
            }
            node2 = node2.children[n2];
        }
        by2 = (byte)(8 - by2);
        for (n2 = n3 = n3 << by2 & 255; n2 < n3 + (1 << by2); ++n2) {
            Node.access$000((Node)node2)[n2] = node;
        }
    }

    private void buildTree() {
        for (int i2 = 0; i2 < CODE_LENGTHS.length; ++i2) {
            this.addCode(i2, CODES[i2], CODE_LENGTHS[i2]);
        }
    }

    public static Huffman get() {
        return INSTANCE;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    byte[] decode(byte[] var1_1) throws IOException {
        var8_2 = new ByteArrayOutputStream();
        var6_3 = this.root;
        var4_4 = 0;
        var2_5 = 0;
        var3_6 = 0;
        do {
            var5_7 = var2_5;
            var7_8 = var6_3;
            if (var3_6 >= var1_1.length) ** GOTO lbl25
            var4_4 = var4_4 << 8 | var1_1[var3_6] & 255;
            var2_5 += 8;
            while (var2_5 >= 8) {
                if (Node.access$000(var6_3 = Node.access$000(var6_3)[var4_4 >>> var2_5 - 8 & 255]) == null) {
                    var8_2.write(Node.access$100(var6_3));
                    var2_5 -= Node.access$200(var6_3);
                    var6_3 = this.root;
                    continue;
                }
                var2_5 -= 8;
            }
            ++var3_6;
        } while (true);
lbl-1000: // 1 sources:
        {
            var8_2.write(Node.access$100((Node)var1_1));
            var5_7 -= Node.access$200((Node)var1_1);
            var7_8 = this.root;
lbl25: // 2 sources:
            if (var5_7 <= 0) return var8_2.toByteArray();
            var1_1 = Node.access$000(var7_8)[var4_4 << 8 - var5_7 & 255];
            if (Node.access$000((Node)var1_1) != null) return var8_2.toByteArray();
            ** while (Node.access$200((Node)var1_1) <= var5_7)
        }
lbl29: // 1 sources:
        return var8_2.toByteArray();
    }

    void encode(byte[] arrby, OutputStream outputStream) throws IOException {
        long l2 = 0;
        int n2 = 0;
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            int n3 = arrby[i2] & 255;
            int n4 = CODES[n3];
            n3 = CODE_LENGTHS[n3];
            l2 = l2 << n3 | (long)n4;
            n2 += n3;
            while (n2 >= 8) {
                outputStream.write((int)(l2 >> (n2 -= 8)));
            }
        }
        if (n2 > 0) {
            outputStream.write((int)(l2 << 8 - n2 | (long)(255 >>> n2)));
        }
    }

    int encodedLength(byte[] arrby) {
        long l2 = 0;
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            byte by2 = arrby[i2];
            l2 += (long)CODE_LENGTHS[by2 & 255];
        }
        return (int)(7 + l2 >> 3);
    }

    private static final class Node {
        private final Node[] children;
        private final int symbol;
        private final int terminalBits;

        Node() {
            this.children = new Node[256];
            this.symbol = 0;
            this.terminalBits = 0;
        }

        Node(int n2, int n3) {
            this.children = null;
            this.symbol = n2;
            n2 = n3 &= 7;
            if (n3 == 0) {
                n2 = 8;
            }
            this.terminalBits = n2;
        }

        static /* synthetic */ int access$100(Node node) {
            return node.symbol;
        }

        static /* synthetic */ int access$200(Node node) {
            return node.terminalBits;
        }
    }

}


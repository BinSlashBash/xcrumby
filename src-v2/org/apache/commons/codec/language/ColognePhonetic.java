/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class ColognePhonetic
implements StringEncoder {
    private static final char[] AEIJOUY = new char[]{'A', 'E', 'I', 'J', 'O', 'U', 'Y'};
    private static final char[] AHKLOQRUX;
    private static final char[] AHOUKQX;
    private static final char[] CKQ;
    private static final char[] GKQ;
    private static final char[][] PREPROCESS_MAP;
    private static final char[] SCZ;
    private static final char[] SZ;
    private static final char[] TDX;
    private static final char[] WFPV;

    static {
        SCZ = new char[]{'S', 'C', 'Z'};
        WFPV = new char[]{'W', 'F', 'P', 'V'};
        GKQ = new char[]{'G', 'K', 'Q'};
        CKQ = new char[]{'C', 'K', 'Q'};
        AHKLOQRUX = new char[]{'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X'};
        SZ = new char[]{'S', 'Z'};
        AHOUKQX = new char[]{'A', 'H', 'O', 'U', 'K', 'Q', 'X'};
        TDX = new char[]{'T', 'D', 'X'};
        PREPROCESS_MAP = new char[][]{{'\u00c4', 'A'}, {'\u00dc', 'U'}, {'\u00d6', 'O'}, {'\u00df', 'S'}};
    }

    private static boolean arrayContains(char[] arrc, char c2) {
        int n2 = arrc.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (arrc[i2] != c2) continue;
            return true;
        }
        return false;
    }

    private String preprocess(String string2) {
        string2 = (String)string2.toUpperCase(Locale.GERMAN).toCharArray();
        block0 : for (int i2 = 0; i2 < string2.length; ++i2) {
            if (string2[i2] <= 90) continue;
            char[][] arrc = PREPROCESS_MAP;
            int n2 = arrc.length;
            int n3 = 0;
            do {
                if (n3 >= n2) continue block0;
                char[] arrc2 = arrc[n3];
                if (string2[i2] == arrc2[0]) {
                    string2[i2] = (String)arrc2[1];
                    continue block0;
                }
                ++n3;
            } while (true);
        }
        return new String((char[])string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String colognePhonetic(String object) {
        if (object == null) {
            return null;
        }
        Object object2 = this.preprocess((String)object);
        object = new CologneOutputBuffer(object2.length() * 2);
        object2 = new CologneInputBuffer(object2.toCharArray());
        int n2 = 45;
        int n3 = 47;
        int n4 = object2.length();
        while (n4 > 0) {
            char c2 = object2.removeNext();
            int n5 = object2.length();
            char c3 = n5 > 0 ? (char)object2.getNextChar() : '-';
            if (ColognePhonetic.arrayContains(AEIJOUY, c2)) {
                n2 = 48;
                n4 = n5;
            } else if (c2 == 'H' || c2 < 'A' || c2 > 'Z') {
                n4 = n5;
                if (n3 == 47) continue;
                n2 = 45;
                n4 = n5;
            } else if (c2 == 'B' || c2 == 'P' && c3 != 'H') {
                n2 = 49;
                n4 = n5;
            } else if (!(c2 != 'D' && c2 != 'T' || ColognePhonetic.arrayContains(SCZ, c3))) {
                n2 = 50;
                n4 = n5;
            } else if (ColognePhonetic.arrayContains(WFPV, c2)) {
                n2 = 51;
                n4 = n5;
            } else if (ColognePhonetic.arrayContains(GKQ, c2)) {
                n2 = 52;
                n4 = n5;
            } else if (c2 == 'X' && !ColognePhonetic.arrayContains(CKQ, (char)n2)) {
                n2 = 52;
                object2.addLeft('S');
                n4 = n5 + 1;
            } else if (c2 == 'S' || c2 == 'Z') {
                n2 = 56;
                n4 = n5;
            } else if (c2 == 'C') {
                if (n3 == 47) {
                    if (ColognePhonetic.arrayContains(AHKLOQRUX, c3)) {
                        n2 = 52;
                        n4 = n5;
                    } else {
                        n2 = 56;
                        n4 = n5;
                    }
                } else if (ColognePhonetic.arrayContains(SZ, (char)n2) || !ColognePhonetic.arrayContains(AHOUKQX, c3)) {
                    n2 = 56;
                    n4 = n5;
                } else {
                    n2 = 52;
                    n4 = n5;
                }
            } else if (ColognePhonetic.arrayContains(TDX, c2)) {
                n2 = 56;
                n4 = n5;
            } else if (c2 == 'R') {
                n2 = 55;
                n4 = n5;
            } else if (c2 == 'L') {
                n2 = 53;
                n4 = n5;
            } else if (c2 == 'M' || c2 == 'N') {
                n2 = 54;
                n4 = n5;
            } else {
                n2 = c2;
                n4 = n5;
            }
            if (n2 != 45 && (n3 != n2 && (n2 != 48 || n3 == 47) || n2 < 48 || n2 > 56)) {
                object.addRight((char)n2);
            }
            n3 = n2;
            n2 = c2;
        }
        return object.toString();
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + object.getClass().getName() + ".");
        }
        return this.encode((String)object);
    }

    @Override
    public String encode(String string2) {
        return this.colognePhonetic(string2);
    }

    public boolean isEncodeEqual(String string2, String string3) {
        return this.colognePhonetic(string2).equals(this.colognePhonetic(string3));
    }

    private abstract class CologneBuffer {
        protected final char[] data;
        protected int length;
        final /* synthetic */ ColognePhonetic this$0;

        public CologneBuffer(ColognePhonetic colognePhonetic, int n2) {
            this.this$0 = colognePhonetic;
            this.length = 0;
            this.data = new char[n2];
            this.length = 0;
        }

        public CologneBuffer(ColognePhonetic colognePhonetic, char[] arrc) {
            this.this$0 = colognePhonetic;
            this.length = 0;
            this.data = arrc;
            this.length = arrc.length;
        }

        protected abstract char[] copyData(int var1, int var2);

        public int length() {
            return this.length;
        }

        public String toString() {
            return new String(this.copyData(0, this.length));
        }
    }

    private class CologneInputBuffer
    extends CologneBuffer {
        public CologneInputBuffer(char[] arrc) {
            super(ColognePhonetic.this, arrc);
        }

        public void addLeft(char c2) {
            ++this.length;
            this.data[this.getNextPos()] = c2;
        }

        @Override
        protected char[] copyData(int n2, int n3) {
            char[] arrc = new char[n3];
            System.arraycopy(this.data, this.data.length - this.length + n2, arrc, 0, n3);
            return arrc;
        }

        public char getNextChar() {
            return this.data[this.getNextPos()];
        }

        protected int getNextPos() {
            return this.data.length - this.length;
        }

        public char removeNext() {
            char c2 = this.getNextChar();
            --this.length;
            return c2;
        }
    }

    private class CologneOutputBuffer
    extends CologneBuffer {
        public CologneOutputBuffer(int n2) {
            super(ColognePhonetic.this, n2);
        }

        public void addRight(char c2) {
            this.data[this.length] = c2;
            ++this.length;
        }

        @Override
        protected char[] copyData(int n2, int n3) {
            char[] arrc = new char[n3];
            System.arraycopy(this.data, n2, arrc, 0, n3);
            return arrc;
        }
    }

}


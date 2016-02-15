/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.util.Arrays;
import org.json.JSONException;

public class Kim {
    private byte[] bytes = null;
    private int hashcode = 0;
    public int length = 0;
    private String string = null;

    /*
     * Enabled aggressive block sorting
     */
    public Kim(String string2) throws JSONException {
        int n2 = string2.length();
        this.hashcode = 0;
        this.length = 0;
        if (n2 > 0) {
            int n3;
            int n4;
            int n5;
            for (n3 = 0; n3 < n2; ++n3) {
                n4 = string2.charAt(n3);
                if (n4 <= 127) {
                    ++this.length;
                    continue;
                }
                if (n4 <= 16383) {
                    this.length += 2;
                    continue;
                }
                n5 = n3;
                if (n4 >= 55296) {
                    n5 = n3;
                    if (n4 <= 57343) {
                        n5 = n3 + 1;
                        n3 = string2.charAt(n5);
                        if (n4 > 56319 || n3 < 56320 || n3 > 57343) {
                            throw new JSONException("Bad UTF16");
                        }
                    }
                }
                this.length += 3;
                n3 = n5;
            }
            this.bytes = new byte[this.length];
            n5 = 0;
            int n6 = 1;
            for (n3 = 0; n3 < n2; ++n3) {
                int n7 = string2.charAt(n3);
                if (n7 <= 127) {
                    this.bytes[n5] = (byte)n7;
                    n4 = n6 + n7;
                    this.hashcode += n4;
                    ++n5;
                } else if (n7 <= 16383) {
                    n4 = n7 >>> 7 | 128;
                    this.bytes[n5] = (byte)n4;
                    n4 = n6 + n4;
                    this.hashcode += n4;
                    n6 = n7 & 127;
                    this.bytes[++n5] = (byte)n6;
                    this.hashcode += (n4 += n6);
                    ++n5;
                } else {
                    int n8 = n7;
                    n4 = n3;
                    if (n7 >= 55296) {
                        n8 = n7;
                        n4 = n3;
                        if (n7 <= 56319) {
                            n4 = n3 + 1;
                            n8 = ((n7 & 1023) << 10 | string2.charAt(n4) & 1023) + 65536;
                        }
                    }
                    n3 = n8 >>> 14 | 128;
                    this.bytes[n5] = (byte)n3;
                    n3 = n6 + n3;
                    this.hashcode += n3;
                    n6 = n8 >>> 7 & 255 | 128;
                    this.bytes[++n5] = (byte)n6;
                    this.hashcode += (n3 += n6);
                    n6 = n8 & 127;
                    this.bytes[++n5] = (byte)n6;
                    n6 = n3 + n6;
                    this.hashcode += n6;
                    ++n5;
                    n3 = n4;
                    n4 = n6;
                }
                n6 = n4;
            }
            this.hashcode += n6 << 16;
        }
    }

    public Kim(Kim kim, int n2, int n3) {
        this(kim.bytes, n2, n3);
    }

    public Kim(byte[] arrby, int n2) {
        this(arrby, 0, n2);
    }

    public Kim(byte[] arrby, int n2, int n3) {
        int n4 = 1;
        this.hashcode = 0;
        this.length = n3 - n2;
        if (this.length > 0) {
            this.bytes = new byte[this.length];
            for (n3 = 0; n3 < this.length; ++n3) {
                int n5 = arrby[n3 + n2] & 255;
                this.hashcode += (n4 += n5);
                this.bytes[n3] = (byte)n5;
            }
            this.hashcode += n4 << 16;
        }
    }

    public static int characterSize(int n2) throws JSONException {
        if (n2 < 0 || n2 > 1114111) {
            throw new JSONException(new StringBuffer().append("Bad character ").append(n2).toString());
        }
        if (n2 <= 127) {
            return 1;
        }
        if (n2 <= 16383) {
            return 2;
        }
        return 3;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int characterAt(int n2) throws JSONException {
        int n3 = this.get(n2);
        if ((n3 & 128) == 0) {
            return n3;
        }
        int n4 = this.get(n2 + 1);
        if ((n4 & 128) == 0) {
            n3 = n4 = (n3 & 127) << 7 | n4;
            if (n4 > 127) return n3;
            throw new JSONException(new StringBuffer().append("Bad character at ").append(n2).toString());
        }
        int n5 = this.get(n2 + 2);
        n4 = (n3 & 127) << 14 | (n4 & 127) << 7 | n5;
        if ((n5 & 128) != 0) throw new JSONException(new StringBuffer().append("Bad character at ").append(n2).toString());
        if (n4 <= 16383) throw new JSONException(new StringBuffer().append("Bad character at ").append(n2).toString());
        if (n4 > 1114111) throw new JSONException(new StringBuffer().append("Bad character at ").append(n2).toString());
        n3 = n4;
        if (n4 < 55296) return n3;
        if (n4 <= 57343) throw new JSONException(new StringBuffer().append("Bad character at ").append(n2).toString());
        return n4;
    }

    public int copy(byte[] arrby, int n2) {
        System.arraycopy(this.bytes, 0, arrby, n2, this.length);
        return this.length + n2;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof Kim)) {
            return false;
        }
        if (this == (object = (Kim)object)) {
            return true;
        }
        if (this.hashcode != object.hashcode) return false;
        return Arrays.equals(this.bytes, object.bytes);
    }

    public int get(int n2) throws JSONException {
        if (n2 < 0 || n2 > this.length) {
            throw new JSONException(new StringBuffer().append("Bad character at ").append(n2).toString());
        }
        return this.bytes[n2] & 255;
    }

    public int hashCode() {
        return this.hashcode;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() throws JSONException {
        if (this.string == null) {
            int n2;
            int n3 = 0;
            char[] arrc = new char[this.length];
            for (int i2 = 0; i2 < this.length; i2 += Kim.characterSize((int)n2)) {
                n2 = this.characterAt(i2);
                if (n2 < 65536) {
                    arrc[n3] = (char)n2;
                    ++n3;
                    continue;
                }
                arrc[n3] = (char)(55296 | n2 - 65536 >>> 10);
                arrc[++n3] = (char)(56320 | n2 & 1023);
                ++n3;
            }
            this.string = new String(arrc, 0, n3);
        }
        return this.string;
    }
}


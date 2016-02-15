/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import okio.Base64;
import okio.Util;

public final class ByteString
implements Serializable {
    public static final ByteString EMPTY;
    private static final char[] HEX_DIGITS;
    private static final long serialVersionUID = 1;
    final byte[] data;
    private transient int hashCode;
    private transient String utf8;

    static {
        HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        EMPTY = ByteString.of(new byte[0]);
    }

    ByteString(byte[] arrby) {
        this.data = arrby;
    }

    public static ByteString decodeBase64(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("base64 == null");
        }
        if ((string2 = (String)Base64.decode(string2)) != null) {
            return new ByteString((byte[])string2);
        }
        return null;
    }

    public static ByteString decodeHex(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("hex == null");
        }
        if (string2.length() % 2 != 0) {
            throw new IllegalArgumentException("Unexpected hex string: " + string2);
        }
        byte[] arrby = new byte[string2.length() / 2];
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            arrby[i2] = (byte)((ByteString.decodeHexDigit(string2.charAt(i2 * 2)) << 4) + ByteString.decodeHexDigit(string2.charAt(i2 * 2 + 1)));
        }
        return ByteString.of(arrby);
    }

    private static int decodeHexDigit(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - 48;
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return c2 - 97 + 10;
        }
        if (c2 >= 'A' && c2 <= 'F') {
            return c2 - 65 + 10;
        }
        throw new IllegalArgumentException("Unexpected hex digit: " + c2);
    }

    public static ByteString encodeUtf8(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("s == null");
        }
        ByteString byteString = new ByteString(string2.getBytes(Util.UTF_8));
        byteString.utf8 = string2;
        return byteString;
    }

    public static /* varargs */ ByteString of(byte ... arrby) {
        if (arrby == null) {
            throw new IllegalArgumentException("data == null");
        }
        return new ByteString((byte[])arrby.clone());
    }

    public static ByteString of(byte[] arrby, int n2, int n3) {
        if (arrby == null) {
            throw new IllegalArgumentException("data == null");
        }
        Util.checkOffsetAndCount(arrby.length, n2, n3);
        byte[] arrby2 = new byte[n3];
        System.arraycopy(arrby, n2, arrby2, 0, n3);
        return new ByteString(arrby2);
    }

    public static ByteString read(InputStream inputStream, int n2) throws IOException {
        int n3;
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + n2);
        }
        byte[] arrby = new byte[n2];
        for (int i2 = 0; i2 < n2; i2 += n3) {
            n3 = inputStream.read(arrby, i2, n2 - i2);
            if (n3 != -1) continue;
            throw new EOFException();
        }
        return new ByteString(arrby);
    }

    private void readObject(ObjectInputStream object) throws IOException {
        object = ByteString.read((InputStream)object, object.readInt());
        try {
            Field field = ByteString.class.getDeclaredField("data");
            field.setAccessible(true);
            field.set(this, object.data);
            return;
        }
        catch (NoSuchFieldException var1_2) {
            throw new AssertionError();
        }
        catch (IllegalAccessException var1_3) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.data.length);
        objectOutputStream.write(this.data);
    }

    public String base64() {
        return Base64.encode(this.data);
    }

    public boolean equals(Object object) {
        if (object == this || object instanceof ByteString && Arrays.equals(((ByteString)object).data, this.data)) {
            return true;
        }
        return false;
    }

    public byte getByte(int n2) {
        return this.data[n2];
    }

    public int hashCode() {
        int n2 = this.hashCode;
        if (n2 != 0) {
            return n2;
        }
        this.hashCode = n2 = Arrays.hashCode(this.data);
        return n2;
    }

    public String hex() {
        char[] arrc = new char[this.data.length * 2];
        byte[] arrby = this.data;
        int n2 = arrby.length;
        int n3 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            byte by2 = arrby[i2];
            int n4 = n3 + 1;
            arrc[n3] = HEX_DIGITS[by2 >> 4 & 15];
            n3 = n4 + 1;
            arrc[n4] = HEX_DIGITS[by2 & 15];
        }
        return new String(arrc);
    }

    public int size() {
        return this.data.length;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ByteString toAsciiLowercase() {
        void var3_6;
        block3 : {
            byte by2;
            int n2 = 0;
            do {
                ByteString byteString = this;
                if (n2 >= this.data.length) break block3;
                by2 = this.data[n2];
                if (by2 >= 65 && by2 <= 90) break;
                ++n2;
            } while (true);
            byte[] arrby = (byte[])this.data.clone();
            arrby[n2] = (byte)(by2 + 32);
            ++n2;
            while (n2 < arrby.length) {
                by2 = arrby[n2];
                if (by2 >= 65 && by2 <= 90) {
                    arrby[n2] = (byte)(by2 + 32);
                }
                ++n2;
            }
            ByteString byteString = new ByteString(arrby);
        }
        return var3_6;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ByteString toAsciiUppercase() {
        void var3_6;
        block3 : {
            byte by2;
            int n2 = 0;
            do {
                ByteString byteString = this;
                if (n2 >= this.data.length) break block3;
                by2 = this.data[n2];
                if (by2 >= 97 && by2 <= 122) break;
                ++n2;
            } while (true);
            byte[] arrby = (byte[])this.data.clone();
            arrby[n2] = (byte)(by2 - 32);
            ++n2;
            while (n2 < arrby.length) {
                by2 = arrby[n2];
                if (by2 >= 97 && by2 <= 122) {
                    arrby[n2] = (byte)(by2 - 32);
                }
                ++n2;
            }
            ByteString byteString = new ByteString(arrby);
        }
        return var3_6;
    }

    public byte[] toByteArray() {
        return (byte[])this.data.clone();
    }

    public String toString() {
        if (this.data.length == 0) {
            return "ByteString[size=0]";
        }
        if (this.data.length <= 16) {
            return String.format("ByteString[size=%s data=%s]", this.data.length, this.hex());
        }
        try {
            String string2 = String.format("ByteString[size=%s md5=%s]", this.data.length, ByteString.of(MessageDigest.getInstance("MD5").digest(this.data)).hex());
            return string2;
        }
        catch (NoSuchAlgorithmException var1_2) {
            throw new AssertionError();
        }
    }

    public String utf8() {
        String string2 = this.utf8;
        if (string2 != null) {
            return string2;
        }
        this.utf8 = string2 = new String(this.data, Util.UTF_8);
        return string2;
    }

    public void write(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        outputStream.write(this.data);
    }
}


/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.n;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class p {
    private final n jP;
    private final SecureRandom ki;

    public p(n n2, SecureRandom secureRandom) {
        this.jP = n2;
        this.ki = secureRandom;
    }

    static void c(byte[] arrby) {
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            arrby[i2] = (byte)(arrby[i2] ^ 68);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] b(String object) throws a {
        try {
            object = this.jP.a((String)object, false);
            if (object.length != 32) {
                throw new a(this);
            }
            object = ByteBuffer.wrap((byte[])object, 4, 16);
            byte[] arrby = new byte[16];
            object.get(arrby);
            p.c(arrby);
            return arrby;
        }
        catch (IllegalArgumentException var1_2) {
            throw new a(this, var1_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] c(byte[] object, String string2) throws a {
        if (object.length != 16) {
            throw new a(this);
        }
        try {
            byte[] arrby = this.jP.a(string2, false);
            if (arrby.length <= 16) {
                throw new a(this);
            }
            Object object2 = ByteBuffer.allocate(arrby.length);
            object2.put(arrby);
            object2.flip();
            string2 = (String)new byte[16];
            arrby = new byte[arrby.length - 16];
            object2.get((byte[])string2);
            object2.get(arrby);
            object = new SecretKeySpec((byte[])object, "AES");
            object2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            object2.init(2, (Key)object, new IvParameterSpec((byte[])string2));
            return object2.doFinal(arrby);
        }
        catch (NoSuchAlgorithmException var1_2) {
            throw new a(this, var1_2);
        }
        catch (InvalidKeyException var1_3) {
            throw new a(this, var1_3);
        }
        catch (IllegalBlockSizeException var1_4) {
            throw new a(this, var1_4);
        }
        catch (NoSuchPaddingException var1_5) {
            throw new a(this, var1_5);
        }
        catch (BadPaddingException var1_6) {
            throw new a(this, var1_6);
        }
        catch (InvalidAlgorithmParameterException var1_7) {
            throw new a(this, var1_7);
        }
        catch (IllegalArgumentException var1_8) {
            throw new a(this, var1_8);
        }
    }

    public class a
    extends Exception {
        final /* synthetic */ p kj;

        public a(p p2) {
            this.kj = p2;
        }

        public a(p p2, Throwable throwable) {
            this.kj = p2;
            super(throwable);
        }
    }

}


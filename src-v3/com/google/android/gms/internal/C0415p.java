package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.google.android.gms.internal.p */
public class C0415p {
    private final C0412n jP;
    private final SecureRandom ki;

    /* renamed from: com.google.android.gms.internal.p.a */
    public class C0414a extends Exception {
        final /* synthetic */ C0415p kj;

        public C0414a(C0415p c0415p) {
            this.kj = c0415p;
        }

        public C0414a(C0415p c0415p, Throwable th) {
            this.kj = c0415p;
            super(th);
        }
    }

    public C0415p(C0412n c0412n, SecureRandom secureRandom) {
        this.jP = c0412n;
        this.ki = secureRandom;
    }

    static void m1189c(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] ^ 68);
        }
    }

    public byte[] m1190b(String str) throws C0414a {
        try {
            byte[] a = this.jP.m1185a(str, false);
            if (a.length != 32) {
                throw new C0414a(this);
            }
            byte[] bArr = new byte[16];
            ByteBuffer.wrap(a, 4, 16).get(bArr);
            C0415p.m1189c(bArr);
            return bArr;
        } catch (Throwable e) {
            throw new C0414a(this, e);
        }
    }

    public byte[] m1191c(byte[] bArr, String str) throws C0414a {
        if (bArr.length != 16) {
            throw new C0414a(this);
        }
        try {
            byte[] a = this.jP.m1185a(str, false);
            if (a.length <= 16) {
                throw new C0414a(this);
            }
            ByteBuffer allocate = ByteBuffer.allocate(a.length);
            allocate.put(a);
            allocate.flip();
            byte[] bArr2 = new byte[16];
            a = new byte[(a.length - 16)];
            allocate.get(bArr2);
            allocate.get(a);
            Key secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, secretKeySpec, new IvParameterSpec(bArr2));
            return instance.doFinal(a);
        } catch (Throwable e) {
            throw new C0414a(this, e);
        } catch (Throwable e2) {
            throw new C0414a(this, e2);
        } catch (Throwable e22) {
            throw new C0414a(this, e22);
        } catch (Throwable e222) {
            throw new C0414a(this, e222);
        } catch (Throwable e2222) {
            throw new C0414a(this, e2222);
        } catch (Throwable e22222) {
            throw new C0414a(this, e22222);
        } catch (Throwable e222222) {
            throw new C0414a(this, e222222);
        }
    }
}

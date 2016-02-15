/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import junit.framework.AssertionFailedError;
import junit.framework.ComparisonFailure;

@Deprecated
public class Assert {
    protected Assert() {
    }

    public static void assertEquals(byte by2, byte by3) {
        Assert.assertEquals(null, by2, by3);
    }

    public static void assertEquals(char c2, char c3) {
        Assert.assertEquals(null, c2, c3);
    }

    public static void assertEquals(double d2, double d3, double d4) {
        Assert.assertEquals(null, d2, d3, d4);
    }

    public static void assertEquals(float f2, float f3, float f4) {
        Assert.assertEquals(null, f2, f3, f4);
    }

    public static void assertEquals(int n2, int n3) {
        Assert.assertEquals(null, n2, n3);
    }

    public static void assertEquals(long l2, long l3) {
        Assert.assertEquals(null, l2, l3);
    }

    public static void assertEquals(Object object, Object object2) {
        Assert.assertEquals(null, object, object2);
    }

    public static void assertEquals(String string2, byte by2, byte by3) {
        Assert.assertEquals(string2, new Byte(by2), new Byte(by3));
    }

    public static void assertEquals(String string2, char c2, char c3) {
        Assert.assertEquals(string2, new Character(c2), new Character(c3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void assertEquals(String string2, double d2, double d3, double d4) {
        if (Double.compare(d2, d3) == 0 || Math.abs(d2 - d3) <= d4) {
            return;
        }
        Assert.failNotEquals(string2, new Double(d2), new Double(d3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void assertEquals(String string2, float f2, float f3, float f4) {
        if (Float.compare(f2, f3) == 0 || Math.abs(f2 - f3) <= f4) {
            return;
        }
        Assert.failNotEquals(string2, new Float(f2), new Float(f3));
    }

    public static void assertEquals(String string2, int n2, int n3) {
        Assert.assertEquals(string2, new Integer(n2), new Integer(n3));
    }

    public static void assertEquals(String string2, long l2, long l3) {
        Assert.assertEquals(string2, new Long(l2), new Long(l3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void assertEquals(String string2, Object object, Object object2) {
        if (object == null && object2 == null || object != null && object.equals(object2)) {
            return;
        }
        Assert.failNotEquals(string2, object, object2);
    }

    public static void assertEquals(String string2, String string3) {
        Assert.assertEquals(null, string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void assertEquals(String string2, String string3, String string4) {
        if (string3 == null && string4 == null || string3 != null && string3.equals(string4)) {
            return;
        }
        if (string2 == null) {
            string2 = "";
        }
        throw new ComparisonFailure(string2, string3, string4);
    }

    public static void assertEquals(String string2, short s2, short s3) {
        Assert.assertEquals(string2, new Short(s2), new Short(s3));
    }

    public static void assertEquals(String string2, boolean bl2, boolean bl3) {
        Assert.assertEquals(string2, (Object)bl2, (Object)bl3);
    }

    public static void assertEquals(short s2, short s3) {
        Assert.assertEquals(null, s2, s3);
    }

    public static void assertEquals(boolean bl2, boolean bl3) {
        Assert.assertEquals(null, bl2, bl3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void assertFalse(String string2, boolean bl2) {
        bl2 = !bl2;
        Assert.assertTrue(string2, bl2);
    }

    public static void assertFalse(boolean bl2) {
        Assert.assertFalse(null, bl2);
    }

    public static void assertNotNull(Object object) {
        Assert.assertNotNull(null, object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void assertNotNull(String string2, Object object) {
        boolean bl2 = object != null;
        Assert.assertTrue(string2, bl2);
    }

    public static void assertNotSame(Object object, Object object2) {
        Assert.assertNotSame(null, object, object2);
    }

    public static void assertNotSame(String string2, Object object, Object object2) {
        if (object == object2) {
            Assert.failSame(string2);
        }
    }

    public static void assertNull(Object object) {
        if (object != null) {
            Assert.assertNull("Expected: <null> but was: " + object.toString(), object);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void assertNull(String string2, Object object) {
        boolean bl2 = object == null;
        Assert.assertTrue(string2, bl2);
    }

    public static void assertSame(Object object, Object object2) {
        Assert.assertSame(null, object, object2);
    }

    public static void assertSame(String string2, Object object, Object object2) {
        if (object == object2) {
            return;
        }
        Assert.failNotSame(string2, object, object2);
    }

    public static void assertTrue(String string2, boolean bl2) {
        if (!bl2) {
            Assert.fail(string2);
        }
    }

    public static void assertTrue(boolean bl2) {
        Assert.assertTrue(null, bl2);
    }

    public static void fail() {
        Assert.fail(null);
    }

    public static void fail(String string2) {
        if (string2 == null) {
            throw new AssertionFailedError();
        }
        throw new AssertionFailedError(string2);
    }

    public static void failNotEquals(String string2, Object object, Object object2) {
        Assert.fail(Assert.format(string2, object, object2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void failNotSame(String string2, Object object, Object object2) {
        string2 = string2 != null ? string2 + " " : "";
        Assert.fail(string2 + "expected same:<" + object + "> was not:<" + object2 + ">");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void failSame(String string2) {
        string2 = string2 != null ? string2 + " " : "";
        Assert.fail(string2 + "expected not same");
    }

    public static String format(String string2, Object object, Object object2) {
        String string3;
        String string4 = string3 = "";
        if (string2 != null) {
            string4 = string3;
            if (string2.length() > 0) {
                string4 = string2 + " ";
            }
        }
        return string4 + "expected:<" + object + "> but was:<" + object2 + ">";
    }
}


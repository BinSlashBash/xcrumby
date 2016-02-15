/*
 * Decompiled with CFR 0_110.
 */
package org.junit;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.ComparisonFailure;
import org.junit.internal.ArrayComparisonFailure;
import org.junit.internal.ExactComparisonCriteria;
import org.junit.internal.InexactComparisonCriteria;

public class Assert {
    protected Assert() {
    }

    public static void assertArrayEquals(String string2, byte[] arrby, byte[] arrby2) throws ArrayComparisonFailure {
        Assert.internalArrayEquals(string2, arrby, arrby2);
    }

    public static void assertArrayEquals(String string2, char[] arrc, char[] arrc2) throws ArrayComparisonFailure {
        Assert.internalArrayEquals(string2, arrc, arrc2);
    }

    public static void assertArrayEquals(String string2, double[] arrd, double[] arrd2, double d2) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(d2).arrayEquals(string2, arrd, arrd2);
    }

    public static void assertArrayEquals(String string2, float[] arrf, float[] arrf2, float f2) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(f2).arrayEquals(string2, arrf, arrf2);
    }

    public static void assertArrayEquals(String string2, int[] arrn, int[] arrn2) throws ArrayComparisonFailure {
        Assert.internalArrayEquals(string2, arrn, arrn2);
    }

    public static void assertArrayEquals(String string2, long[] arrl, long[] arrl2) throws ArrayComparisonFailure {
        Assert.internalArrayEquals(string2, arrl, arrl2);
    }

    public static void assertArrayEquals(String string2, Object[] arrobject, Object[] arrobject2) throws ArrayComparisonFailure {
        Assert.internalArrayEquals(string2, arrobject, arrobject2);
    }

    public static void assertArrayEquals(String string2, short[] arrs, short[] arrs2) throws ArrayComparisonFailure {
        Assert.internalArrayEquals(string2, arrs, arrs2);
    }

    public static void assertArrayEquals(byte[] arrby, byte[] arrby2) {
        Assert.assertArrayEquals(null, arrby, arrby2);
    }

    public static void assertArrayEquals(char[] arrc, char[] arrc2) {
        Assert.assertArrayEquals(null, arrc, arrc2);
    }

    public static void assertArrayEquals(double[] arrd, double[] arrd2, double d2) {
        Assert.assertArrayEquals(null, arrd, arrd2, d2);
    }

    public static void assertArrayEquals(float[] arrf, float[] arrf2, float f2) {
        Assert.assertArrayEquals(null, arrf, arrf2, f2);
    }

    public static void assertArrayEquals(int[] arrn, int[] arrn2) {
        Assert.assertArrayEquals(null, arrn, arrn2);
    }

    public static void assertArrayEquals(long[] arrl, long[] arrl2) {
        Assert.assertArrayEquals(null, arrl, arrl2);
    }

    public static void assertArrayEquals(Object[] arrobject, Object[] arrobject2) {
        Assert.assertArrayEquals(null, arrobject, arrobject2);
    }

    public static void assertArrayEquals(short[] arrs, short[] arrs2) {
        Assert.assertArrayEquals(null, arrs, arrs2);
    }

    @Deprecated
    public static void assertEquals(double d2, double d3) {
        Assert.assertEquals(null, d2, d3);
    }

    public static void assertEquals(double d2, double d3, double d4) {
        Assert.assertEquals(null, d2, d3, d4);
    }

    public static void assertEquals(float f2, float f3, float f4) {
        Assert.assertEquals(null, f2, f3, f4);
    }

    public static void assertEquals(long l2, long l3) {
        Assert.assertEquals(null, l2, l3);
    }

    public static void assertEquals(Object object, Object object2) {
        Assert.assertEquals(null, object, object2);
    }

    @Deprecated
    public static void assertEquals(String string2, double d2, double d3) {
        Assert.fail("Use assertEquals(expected, actual, delta) to compare floating-point numbers");
    }

    public static void assertEquals(String string2, double d2, double d3, double d4) {
        if (Assert.doubleIsDifferent(d2, d3, d4)) {
            Assert.failNotEquals(string2, new Double(d2), new Double(d3));
        }
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

    public static void assertEquals(String string2, long l2, long l3) {
        Assert.assertEquals(string2, (Object)l2, (Object)l3);
    }

    public static void assertEquals(String string2, Object object, Object object2) {
        if (Assert.equalsRegardingNull(object, object2)) {
            return;
        }
        if (object instanceof String && object2 instanceof String) {
            if (string2 == null) {
                string2 = "";
            }
            throw new ComparisonFailure(string2, (String)object, (String)object2);
        }
        Assert.failNotEquals(string2, object, object2);
    }

    @Deprecated
    public static void assertEquals(String string2, Object[] arrobject, Object[] arrobject2) {
        Assert.assertArrayEquals(string2, arrobject, arrobject2);
    }

    @Deprecated
    public static void assertEquals(Object[] arrobject, Object[] arrobject2) {
        Assert.assertArrayEquals(arrobject, arrobject2);
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

    public static void assertNotEquals(double d2, double d3, double d4) {
        Assert.assertNotEquals(null, d2, d3, d4);
    }

    public static void assertNotEquals(long l2, long l3) {
        Assert.assertNotEquals(null, l2, l3);
    }

    public static void assertNotEquals(Object object, Object object2) {
        Assert.assertNotEquals(null, object, object2);
    }

    public static void assertNotEquals(String string2, double d2, double d3, double d4) {
        if (!Assert.doubleIsDifferent(d2, d3, d4)) {
            Assert.failEquals(string2, new Double(d2));
        }
    }

    public static void assertNotEquals(String string2, long l2, long l3) {
        Assert.assertNotEquals(string2, (Object)l2, (Object)l3);
    }

    public static void assertNotEquals(String string2, Object object, Object object2) {
        if (Assert.equalsRegardingNull(object, object2)) {
            Assert.failEquals(string2, object);
        }
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
        Assert.assertNull(null, object);
    }

    public static void assertNull(String string2, Object object) {
        if (object == null) {
            return;
        }
        Assert.failNotNull(string2, object);
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

    public static <T> void assertThat(T t2, Matcher<? super T> matcher) {
        Assert.assertThat("", t2, matcher);
    }

    public static <T> void assertThat(String string2, T t2, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(string2, t2, matcher);
    }

    public static void assertTrue(String string2, boolean bl2) {
        if (!bl2) {
            Assert.fail(string2);
        }
    }

    public static void assertTrue(boolean bl2) {
        Assert.assertTrue(null, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean doubleIsDifferent(double d2, double d3, double d4) {
        if (Double.compare(d2, d3) == 0 || Math.abs(d2 - d3) <= d4) {
            return false;
        }
        return true;
    }

    private static boolean equalsRegardingNull(Object object, Object object2) {
        if (object == null) {
            if (object2 == null) {
                return true;
            }
            return false;
        }
        return Assert.isEquals(object, object2);
    }

    public static void fail() {
        Assert.fail(null);
    }

    public static void fail(String string2) {
        if (string2 == null) {
            throw new AssertionError();
        }
        throw new AssertionError((Object)string2);
    }

    private static void failEquals(String string2, Object object) {
        String string3 = "Values should be different. ";
        if (string2 != null) {
            string3 = string2 + ". ";
        }
        Assert.fail(string3 + "Actual: " + object);
    }

    private static void failNotEquals(String string2, Object object, Object object2) {
        Assert.fail(Assert.format(string2, object, object2));
    }

    private static void failNotNull(String string2, Object object) {
        String string3 = "";
        if (string2 != null) {
            string3 = string2 + " ";
        }
        Assert.fail(string3 + "expected null, but was:<" + object + ">");
    }

    private static void failNotSame(String string2, Object object, Object object2) {
        String string3 = "";
        if (string2 != null) {
            string3 = string2 + " ";
        }
        Assert.fail(string3 + "expected same:<" + object + "> was not:<" + object2 + ">");
    }

    private static void failSame(String string2) {
        String string3 = "";
        if (string2 != null) {
            string3 = string2 + " ";
        }
        Assert.fail(string3 + "expected not same");
    }

    static String format(String string2, Object object, Object object2) {
        String string3;
        String string4 = string3 = "";
        if (string2 != null) {
            string4 = string3;
            if (!string2.equals("")) {
                string4 = string2 + " ";
            }
        }
        if ((string2 = String.valueOf(object)).equals(string3 = String.valueOf(object2))) {
            return string4 + "expected: " + Assert.formatClassAndValue(object, string2) + " but was: " + Assert.formatClassAndValue(object2, string3);
        }
        return string4 + "expected:<" + string2 + "> but was:<" + string3 + ">";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String formatClassAndValue(Object object, String string2) {
        if (object == null) {
            object = "null";
            do {
                return (String)object + "<" + string2 + ">";
                break;
            } while (true);
        }
        object = object.getClass().getName();
        return (String)object + "<" + string2 + ">";
    }

    private static void internalArrayEquals(String string2, Object object, Object object2) throws ArrayComparisonFailure {
        new ExactComparisonCriteria().arrayEquals(string2, object, object2);
    }

    private static boolean isEquals(Object object, Object object2) {
        return object.equals(object2);
    }
}


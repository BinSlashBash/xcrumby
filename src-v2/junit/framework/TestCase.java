/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestResult;

public abstract class TestCase
extends Assert
implements Test {
    private String fName;

    public TestCase() {
        this.fName = null;
    }

    public TestCase(String string2) {
        this.fName = string2;
    }

    public static void assertEquals(byte by2, byte by3) {
        Assert.assertEquals(by2, by3);
    }

    public static void assertEquals(char c2, char c3) {
        Assert.assertEquals(c2, c3);
    }

    public static void assertEquals(double d2, double d3, double d4) {
        Assert.assertEquals(d2, d3, d4);
    }

    public static void assertEquals(float f2, float f3, float f4) {
        Assert.assertEquals(f2, f3, f4);
    }

    public static void assertEquals(int n2, int n3) {
        Assert.assertEquals(n2, n3);
    }

    public static void assertEquals(long l2, long l3) {
        Assert.assertEquals(l2, l3);
    }

    public static void assertEquals(Object object, Object object2) {
        Assert.assertEquals(object, object2);
    }

    public static void assertEquals(String string2, byte by2, byte by3) {
        Assert.assertEquals(string2, by2, by3);
    }

    public static void assertEquals(String string2, char c2, char c3) {
        Assert.assertEquals(string2, c2, c3);
    }

    public static void assertEquals(String string2, double d2, double d3, double d4) {
        Assert.assertEquals(string2, d2, d3, d4);
    }

    public static void assertEquals(String string2, float f2, float f3, float f4) {
        Assert.assertEquals(string2, f2, f3, f4);
    }

    public static void assertEquals(String string2, int n2, int n3) {
        Assert.assertEquals(string2, n2, n3);
    }

    public static void assertEquals(String string2, long l2, long l3) {
        Assert.assertEquals(string2, l2, l3);
    }

    public static void assertEquals(String string2, Object object, Object object2) {
        Assert.assertEquals(string2, object, object2);
    }

    public static void assertEquals(String string2, String string3) {
        Assert.assertEquals(string2, string3);
    }

    public static void assertEquals(String string2, String string3, String string4) {
        Assert.assertEquals(string2, string3, string4);
    }

    public static void assertEquals(String string2, short s2, short s3) {
        Assert.assertEquals(string2, s2, s3);
    }

    public static void assertEquals(String string2, boolean bl2, boolean bl3) {
        Assert.assertEquals(string2, bl2, bl3);
    }

    public static void assertEquals(short s2, short s3) {
        Assert.assertEquals(s2, s3);
    }

    public static void assertEquals(boolean bl2, boolean bl3) {
        Assert.assertEquals(bl2, bl3);
    }

    public static void assertFalse(String string2, boolean bl2) {
        Assert.assertFalse(string2, bl2);
    }

    public static void assertFalse(boolean bl2) {
        Assert.assertFalse(bl2);
    }

    public static void assertNotNull(Object object) {
        Assert.assertNotNull(object);
    }

    public static void assertNotNull(String string2, Object object) {
        Assert.assertNotNull(string2, object);
    }

    public static void assertNotSame(Object object, Object object2) {
        Assert.assertNotSame(object, object2);
    }

    public static void assertNotSame(String string2, Object object, Object object2) {
        Assert.assertNotSame(string2, object, object2);
    }

    public static void assertNull(Object object) {
        Assert.assertNull(object);
    }

    public static void assertNull(String string2, Object object) {
        Assert.assertNull(string2, object);
    }

    public static void assertSame(Object object, Object object2) {
        Assert.assertSame(object, object2);
    }

    public static void assertSame(String string2, Object object, Object object2) {
        Assert.assertSame(string2, object, object2);
    }

    public static void assertTrue(String string2, boolean bl2) {
        Assert.assertTrue(string2, bl2);
    }

    public static void assertTrue(boolean bl2) {
        Assert.assertTrue(bl2);
    }

    public static void fail() {
        Assert.fail();
    }

    public static void fail(String string2) {
        Assert.fail(string2);
    }

    public static void failNotEquals(String string2, Object object, Object object2) {
        Assert.failNotEquals(string2, object, object2);
    }

    public static void failNotSame(String string2, Object object, Object object2) {
        Assert.failNotSame(string2, object, object2);
    }

    public static void failSame(String string2) {
        Assert.failSame(string2);
    }

    public static String format(String string2, Object object, Object object2) {
        return Assert.format(string2, object, object2);
    }

    @Override
    public int countTestCases() {
        return 1;
    }

    protected TestResult createResult() {
        return new TestResult();
    }

    public String getName() {
        return this.fName;
    }

    public TestResult run() {
        TestResult testResult = this.createResult();
        this.run(testResult);
        return testResult;
    }

    @Override
    public void run(TestResult testResult) {
        testResult.run(this);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void runBare() throws Throwable {
        Throwable throwable;
        block10 : {
            throwable = null;
            this.setUp();
            this.runTest();
            try {
                this.tearDown();
            }
            catch (Throwable var2_3) {
                if (!false) {
                    throwable = var2_3;
                }
                break block10;
            }
            catch (Throwable throwable2) {
                try {
                    this.tearDown();
                    throwable = throwable2;
                }
                catch (Throwable var3_6) {
                    throwable = throwable2;
                    if (throwable2 != null) break block10;
                    throwable = var3_6;
                }
            }
        }
        if (throwable == null) return;
        throw throwable;
        catch (Throwable throwable3) {
            try {
                this.tearDown();
            }
            catch (Throwable var2_5) {
                if (false) throw throwable3;
                throw throwable3;
            }
            throw throwable3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void runTest() throws Throwable {
        TestCase.assertNotNull("TestCase.fName cannot be null", this.fName);
        Method method = null;
        try {
            Method method2;
            method = method2 = this.getClass().getMethod(this.fName, null);
        }
        catch (NoSuchMethodException var2_5) {
            TestCase.fail("Method \"" + this.fName + "\" not found");
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            TestCase.fail("Method \"" + this.fName + "\" should be public");
        }
        try {
            method.invoke(this, new Object[0]);
            return;
        }
        catch (InvocationTargetException var1_2) {
            var1_2.fillInStackTrace();
            throw var1_2.getTargetException();
        }
        catch (IllegalAccessException var1_3) {
            var1_3.fillInStackTrace();
            throw var1_3;
        }
    }

    public void setName(String string2) {
        this.fName = string2;
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public String toString() {
        return this.getName() + "(" + this.getClass().getName() + ")";
    }
}


/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.internal.MethodSorter;

public class TestSuite
implements Test {
    private String fName;
    private Vector<Test> fTests = new Vector(10);

    public TestSuite() {
    }

    public TestSuite(Class<?> class_) {
        this.addTestsFromTestCase(class_);
    }

    public TestSuite(Class<? extends TestCase> class_, String string2) {
        this(class_);
        this.setName(string2);
    }

    public TestSuite(String string2) {
        this.setName(string2);
    }

    public /* varargs */ TestSuite(Class<?> ... arrclass) {
        int n2 = arrclass.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.addTest(this.testCaseForClass(arrclass[i2]));
        }
    }

    public TestSuite(Class<? extends TestCase>[] arrclass, String string2) {
        this(arrclass);
        this.setName(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void addTestMethod(Method method, List<String> list, Class<?> class_) {
        String string2 = method.getName();
        if (list.contains(string2)) {
            return;
        }
        if (!this.isPublicTestMethod(method)) {
            if (!this.isTestMethod(method)) return;
            this.addTest(TestSuite.warning("Test method isn't public: " + method.getName() + "(" + class_.getCanonicalName() + ")"));
            return;
        }
        list.add(string2);
        this.addTest(TestSuite.createTest(class_, string2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void addTestsFromTestCase(Class<?> class_) {
        this.fName = class_.getName();
        try {
            TestSuite.getTestConstructor(class_);
        }
        catch (NoSuchMethodException var4_2) {
            this.addTest(TestSuite.warning("Class " + class_.getName() + " has no public constructor TestCase(String name) or TestCase()"));
            return;
        }
        if (!Modifier.isPublic(class_.getModifiers())) {
            this.addTest(TestSuite.warning("Class " + class_.getName() + " is not public"));
            return;
        }
        Class class_2 = class_;
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            if (!Test.class.isAssignableFrom(class_2)) {
                if (this.fTests.size() != 0) return;
                this.addTest(TestSuite.warning("No tests found in " + class_.getName()));
                return;
            }
            Method[] arrmethod = MethodSorter.getDeclaredMethods(class_2);
            int n2 = arrmethod.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                this.addTestMethod(arrmethod[i2], arrayList, class_);
            }
            class_2 = class_2.getSuperclass();
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Test createTest(Class<?> class_, String string2) {
        void var1_9;
        Constructor constructor;
        void var0_3;
        block9 : {
            block10 : {
                Object obj;
                try {
                    constructor = TestSuite.getTestConstructor(class_);
                }
                catch (NoSuchMethodException var1_10) {
                    return TestSuite.warning("Class " + class_.getName() + " has no public constructor TestCase(String name) or TestCase()");
                }
                if (constructor.getParameterTypes().length != 0) break block9;
                Object obj2 = obj = constructor.newInstance(new Object[0]);
                if (!(obj instanceof TestCase)) break block10;
                ((TestCase)obj).setName((String)var1_9);
                Object obj3 = obj;
            }
            do {
                return (Test)var0_3;
                break;
            } while (true);
        }
        try {
            Object obj = constructor.newInstance(var1_9);
            return (Test)var0_3;
        }
        catch (InstantiationException var0_5) {
            return TestSuite.warning("Cannot instantiate test case: " + (String)var1_9 + " (" + TestSuite.exceptionToString(var0_5) + ")");
        }
        catch (InvocationTargetException var0_6) {
            return TestSuite.warning("Exception in constructor: " + (String)var1_9 + " (" + TestSuite.exceptionToString(var0_6.getTargetException()) + ")");
        }
        catch (IllegalAccessException var0_7) {
            return TestSuite.warning("Cannot access test case: " + (String)var1_9 + " (" + TestSuite.exceptionToString(var0_7) + ")");
        }
    }

    private static String exceptionToString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static Constructor<?> getTestConstructor(Class<?> class_) throws NoSuchMethodException {
        try {
            Constructor constructor = class_.getConstructor(String.class);
            return constructor;
        }
        catch (NoSuchMethodException var1_2) {
            return class_.getConstructor(new Class[0]);
        }
    }

    private boolean isPublicTestMethod(Method method) {
        if (this.isTestMethod(method) && Modifier.isPublic(method.getModifiers())) {
            return true;
        }
        return false;
    }

    private boolean isTestMethod(Method method) {
        if (method.getParameterTypes().length == 0 && method.getName().startsWith("test") && method.getReturnType().equals(Void.TYPE)) {
            return true;
        }
        return false;
    }

    private Test testCaseForClass(Class<?> class_) {
        if (TestCase.class.isAssignableFrom(class_)) {
            return new TestSuite(class_.asSubclass(TestCase.class));
        }
        return TestSuite.warning(class_.getCanonicalName() + " does not extend TestCase");
    }

    public static Test warning(final String string2) {
        return new TestCase("warning"){

            @Override
            protected void runTest() {
                .fail(string2);
            }
        };
    }

    public void addTest(Test test) {
        this.fTests.add(test);
    }

    public void addTestSuite(Class<? extends TestCase> class_) {
        this.addTest(new TestSuite(class_));
    }

    @Override
    public int countTestCases() {
        int n2 = 0;
        Iterator<Test> iterator = this.fTests.iterator();
        while (iterator.hasNext()) {
            n2 += iterator.next().countTestCases();
        }
        return n2;
    }

    public String getName() {
        return this.fName;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void run(TestResult testResult) {
        for (Test test : this.fTests) {
            if (testResult.shouldStop()) {
                return;
            }
            this.runTest(test, testResult);
        }
    }

    public void runTest(Test test, TestResult testResult) {
        test.run(testResult);
    }

    public void setName(String string2) {
        this.fName = string2;
    }

    public Test testAt(int n2) {
        return this.fTests.get(n2);
    }

    public int testCount() {
        return this.fTests.size();
    }

    public Enumeration<Test> tests() {
        return this.fTests.elements();
    }

    public String toString() {
        if (this.getName() != null) {
            return this.getName();
        }
        return super.toString();
    }

}


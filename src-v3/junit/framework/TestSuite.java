package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.junit.internal.MethodSorter;

public class TestSuite implements Test {
    private String fName;
    private Vector<Test> fTests;

    /* renamed from: junit.framework.TestSuite.1 */
    static class C14581 extends TestCase {
        final /* synthetic */ String val$message;

        C14581(String x0, String str) {
            this.val$message = str;
            super(x0);
        }

        protected void runTest() {
            TestCase.fail(this.val$message);
        }
    }

    public static Test createTest(Class<?> theClass, String name) {
        try {
            Constructor<?> constructor = getTestConstructor(theClass);
            try {
                Object test;
                if (constructor.getParameterTypes().length == 0) {
                    test = constructor.newInstance(new Object[0]);
                    if (test instanceof TestCase) {
                        ((TestCase) test).setName(name);
                    }
                } else {
                    test = constructor.newInstance(new Object[]{name});
                }
                return (Test) test;
            } catch (InstantiationException e) {
                return warning("Cannot instantiate test case: " + name + " (" + exceptionToString(e) + ")");
            } catch (InvocationTargetException e2) {
                return warning("Exception in constructor: " + name + " (" + exceptionToString(e2.getTargetException()) + ")");
            } catch (IllegalAccessException e3) {
                return warning("Cannot access test case: " + name + " (" + exceptionToString(e3) + ")");
            }
        } catch (NoSuchMethodException e4) {
            return warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()");
        }
    }

    public static Constructor<?> getTestConstructor(Class<?> theClass) throws NoSuchMethodException {
        try {
            return theClass.getConstructor(new Class[]{String.class});
        } catch (NoSuchMethodException e) {
            return theClass.getConstructor(new Class[0]);
        }
    }

    public static Test warning(String message) {
        return new C14581("warning", message);
    }

    private static String exceptionToString(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public TestSuite() {
        this.fTests = new Vector(10);
    }

    public TestSuite(Class<?> theClass) {
        this.fTests = new Vector(10);
        addTestsFromTestCase(theClass);
    }

    private void addTestsFromTestCase(Class<?> theClass) {
        this.fName = theClass.getName();
        try {
            getTestConstructor(theClass);
            if (Modifier.isPublic(theClass.getModifiers())) {
                List<String> names = new ArrayList();
                for (Class<?> superClass = theClass; Test.class.isAssignableFrom(superClass); superClass = superClass.getSuperclass()) {
                    for (Method each : MethodSorter.getDeclaredMethods(superClass)) {
                        addTestMethod(each, names, theClass);
                    }
                }
                if (this.fTests.size() == 0) {
                    addTest(warning("No tests found in " + theClass.getName()));
                    return;
                }
                return;
            }
            addTest(warning("Class " + theClass.getName() + " is not public"));
        } catch (NoSuchMethodException e) {
            addTest(warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()"));
        }
    }

    public TestSuite(Class<? extends TestCase> theClass, String name) {
        this((Class) theClass);
        setName(name);
    }

    public TestSuite(String name) {
        this.fTests = new Vector(10);
        setName(name);
    }

    public TestSuite(Class<?>... classes) {
        this.fTests = new Vector(10);
        for (Class<?> each : classes) {
            addTest(testCaseForClass(each));
        }
    }

    private Test testCaseForClass(Class<?> each) {
        if (TestCase.class.isAssignableFrom(each)) {
            return new TestSuite(each.asSubclass(TestCase.class));
        }
        return warning(each.getCanonicalName() + " does not extend TestCase");
    }

    public TestSuite(Class<? extends TestCase>[] classes, String name) {
        this((Class[]) classes);
        setName(name);
    }

    public void addTest(Test test) {
        this.fTests.add(test);
    }

    public void addTestSuite(Class<? extends TestCase> testClass) {
        addTest(new TestSuite((Class) testClass));
    }

    public int countTestCases() {
        int count = 0;
        Iterator i$ = this.fTests.iterator();
        while (i$.hasNext()) {
            count += ((Test) i$.next()).countTestCases();
        }
        return count;
    }

    public String getName() {
        return this.fName;
    }

    public void run(TestResult result) {
        Iterator i$ = this.fTests.iterator();
        while (i$.hasNext()) {
            Test each = (Test) i$.next();
            if (!result.shouldStop()) {
                runTest(each, result);
            } else {
                return;
            }
        }
    }

    public void runTest(Test test, TestResult result) {
        test.run(result);
    }

    public void setName(String name) {
        this.fName = name;
    }

    public Test testAt(int index) {
        return (Test) this.fTests.get(index);
    }

    public int testCount() {
        return this.fTests.size();
    }

    public Enumeration<Test> tests() {
        return this.fTests.elements();
    }

    public String toString() {
        if (getName() != null) {
            return getName();
        }
        return super.toString();
    }

    private void addTestMethod(Method m, List<String> names, Class<?> theClass) {
        String name = m.getName();
        if (!names.contains(name)) {
            if (isPublicTestMethod(m)) {
                names.add(name);
                addTest(createTest(theClass, name));
            } else if (isTestMethod(m)) {
                addTest(warning("Test method isn't public: " + m.getName() + "(" + theClass.getCanonicalName() + ")"));
            }
        }
    }

    private boolean isPublicTestMethod(Method m) {
        return isTestMethod(m) && Modifier.isPublic(m.getModifiers());
    }

    private boolean isTestMethod(Method m) {
        return m.getParameterTypes().length == 0 && m.getName().startsWith("test") && m.getReturnType().equals(Void.TYPE);
    }
}

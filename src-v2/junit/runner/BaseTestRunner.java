/*
 * Decompiled with CFR 0_110.
 */
package junit.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
import java.util.Properties;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestSuite;

public abstract class BaseTestRunner
implements TestListener {
    public static final String SUITE_METHODNAME = "suite";
    private static Properties fPreferences;
    static boolean fgFilterStack;
    static int fgMaxMessageLength;
    boolean fLoading = true;

    static {
        fgMaxMessageLength = 500;
        fgFilterStack = true;
        fgMaxMessageLength = BaseTestRunner.getPreference("maxmessage", fgMaxMessageLength);
    }

    static boolean filterLine(String string2) {
        String[] arrstring = new String[]{"junit.framework.TestCase", "junit.framework.TestResult", "junit.framework.TestSuite", "junit.framework.Assert.", "junit.swingui.TestRunner", "junit.awtui.TestRunner", "junit.textui.TestRunner", "java.lang.reflect.Method.invoke("};
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            if (string2.indexOf(arrstring[i2]) <= 0) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getFilteredTrace(String string2) {
        if (BaseTestRunner.showStackRaw()) {
            return string2;
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        BufferedReader bufferedReader = new BufferedReader(new StringReader(string2));
        try {
            String string3;
            while ((string3 = bufferedReader.readLine()) != null) {
                if (BaseTestRunner.filterLine(string3)) continue;
                printWriter.println(string3);
            }
            return stringWriter.toString();
        }
        catch (Exception exception) {
            return string2;
        }
    }

    public static String getFilteredTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return BaseTestRunner.getFilteredTrace(stringWriter.getBuffer().toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getPreference(String string2, int n2) {
        if ((string2 = BaseTestRunner.getPreference(string2)) == null) {
            return n2;
        }
        try {
            int n3 = Integer.parseInt(string2);
            return n3;
        }
        catch (NumberFormatException var0_1) {
            return n2;
        }
    }

    public static String getPreference(String string2) {
        return BaseTestRunner.getPreferences().getProperty(string2);
    }

    protected static Properties getPreferences() {
        if (fPreferences == null) {
            fPreferences = new Properties();
            fPreferences.put("loading", "true");
            fPreferences.put("filterstack", "true");
            BaseTestRunner.readPreferences();
        }
        return fPreferences;
    }

    private static File getPreferencesFile() {
        return new File(System.getProperty("user.home"), "junit.properties");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void readPreferences() {
        FileInputStream fileInputStream;
        InputStream inputStream = null;
        try {
            fileInputStream = new FileInputStream(BaseTestRunner.getPreferencesFile());
        }
        catch (IOException var1_4) {}
        try {
            BaseTestRunner.setPreferences(new Properties(BaseTestRunner.getPreferences()));
            BaseTestRunner.getPreferences().load(fileInputStream);
            return;
        }
        catch (IOException var0_2) {
            inputStream = fileInputStream;
        }
        {
            if (inputStream == null) return;
            try {
                inputStream.close();
                return;
            }
            catch (IOException var0_1) {
                return;
            }
        }
    }

    public static void savePreferences() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(BaseTestRunner.getPreferencesFile());
        try {
            BaseTestRunner.getPreferences().store(fileOutputStream, "");
            return;
        }
        finally {
            fileOutputStream.close();
        }
    }

    public static void setPreference(String string2, String string3) {
        BaseTestRunner.getPreferences().put(string2, string3);
    }

    protected static void setPreferences(Properties properties) {
        fPreferences = properties;
    }

    protected static boolean showStackRaw() {
        if (!BaseTestRunner.getPreference("filterstack").equals("true") || !fgFilterStack) {
            return true;
        }
        return false;
    }

    public static String truncate(String string2) {
        String string3 = string2;
        if (fgMaxMessageLength != -1) {
            string3 = string2;
            if (string2.length() > fgMaxMessageLength) {
                string3 = string2.substring(0, fgMaxMessageLength) + "...";
            }
        }
        return string3;
    }

    @Override
    public void addError(Test test, Throwable throwable) {
        synchronized (this) {
            this.testFailed(1, test, throwable);
            return;
        }
    }

    @Override
    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        synchronized (this) {
            this.testFailed(2, test, (Throwable)((Object)assertionFailedError));
            return;
        }
    }

    protected void clearStatus() {
    }

    public String elapsedTimeAsString(long l2) {
        return NumberFormat.getInstance().format((double)l2 / 1000.0);
    }

    @Override
    public void endTest(Test test) {
        synchronized (this) {
            this.testEnded(test.toString());
            return;
        }
    }

    public String extractClassName(String string2) {
        String string3 = string2;
        if (string2.startsWith("Default package for")) {
            string3 = string2.substring(string2.lastIndexOf(".") + 1);
        }
        return string3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Test getTest(String var1_1) {
        if (var1_1.length() <= 0) {
            this.clearStatus();
            return null;
        }
        var2_6 = this.loadSuiteClass((String)var1_1);
        try {
            var1_1 = var2_6.getMethod("suite", new Class[0]);
        }
        catch (Exception var1_3) {
            this.clearStatus();
            return new TestSuite(var2_6);
        }
        if (!Modifier.isStatic(var1_1.getModifiers())) {
            this.runFailed("Suite() method must be static");
            return null;
        }
        ** GOTO lbl24
        catch (ClassNotFoundException var2_7) {
            var2_8 = var3_9 = var2_7.getMessage();
            if (var3_9 == null) {
                var2_8 = var1_1;
            }
            this.runFailed("Class not found \"" + (String)var2_8 + "\"");
            return null;
        }
        catch (Exception var1_2) {
            this.runFailed("Error: " + var1_2.toString());
            return null;
        }
lbl24: // 1 sources:
        try {
            var1_1 = var2_6 = (Test)var1_1.invoke(null, new Class[0]);
            if (var2_6 == null) return var1_1;
            this.clearStatus();
            return var2_6;
        }
        catch (InvocationTargetException var1_4) {
            this.runFailed("Failed to invoke suite():" + var1_4.getTargetException().toString());
            return null;
        }
        catch (IllegalAccessException var1_5) {
            this.runFailed("Failed to invoke suite():" + var1_5.toString());
            return null;
        }
    }

    protected Class<?> loadSuiteClass(String string2) throws ClassNotFoundException {
        return Class.forName(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected String processArguments(String[] arrstring) {
        String string2 = null;
        int n2 = 0;
        while (n2 < arrstring.length) {
            if (arrstring[n2].equals("-noloading")) {
                this.setLoading(false);
            } else if (arrstring[n2].equals("-nofilterstack")) {
                fgFilterStack = false;
            } else if (arrstring[n2].equals("-c")) {
                if (arrstring.length > n2 + 1) {
                    string2 = this.extractClassName(arrstring[n2 + 1]);
                } else {
                    System.out.println("Missing Test class name");
                }
                ++n2;
            } else {
                string2 = arrstring[n2];
            }
            ++n2;
        }
        return string2;
    }

    protected abstract void runFailed(String var1);

    public void setLoading(boolean bl2) {
        this.fLoading = bl2;
    }

    @Override
    public void startTest(Test test) {
        synchronized (this) {
            this.testStarted(test.toString());
            return;
        }
    }

    public abstract void testEnded(String var1);

    public abstract void testFailed(int var1, Test var2, Throwable var3);

    public abstract void testStarted(String var1);

    protected boolean useReloadingTestSuiteLoader() {
        if (BaseTestRunner.getPreference("loading").equals("true") && this.fLoading) {
            return true;
        }
        return false;
    }
}


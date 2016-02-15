/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.regex.Pattern;

public class VersionUtil {
    private static final Pattern V_SEP = Pattern.compile("[-_./;:]");
    private final Version _v;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected VersionUtil() {
        Version version;
        Version version2 = null;
        try {
            version2 = version = VersionUtil.versionFor(this.getClass());
        }
        catch (Exception var2_3) {
            System.err.println("ERROR: Failed to load Version information from " + this.getClass());
        }
        version = version2;
        if (version2 == null) {
            version = Version.unknownVersion();
        }
        this._v = version;
    }

    private static final void _close(Closeable closeable) {
        try {
            closeable.close();
            return;
        }
        catch (IOException var0_1) {
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Version doReadVersion(Reader object) {
        String string2;
        BufferedReader bufferedReader;
        Object object3;
        Object object4;
        Object object2;
        block4 : {
            object2 = null;
            object4 = null;
            String string3 = null;
            string2 = null;
            String string4 = null;
            bufferedReader = new BufferedReader((Reader)object);
            object = object4;
            try {
                object3 = bufferedReader.readLine();
                object = string4;
                object2 = string3;
                if (object3 == null) break block4;
                object = object4;
                object2 = object3;
                object4 = bufferedReader.readLine();
                object = string4;
                object2 = object4;
                if (object4 == null) break block4;
                object = object4;
                object2 = object3;
                object = string4 = bufferedReader.readLine();
                object2 = object4;
            }
            catch (IOException var2_9) {
                VersionUtil._close(bufferedReader);
                object3 = string2;
                object4 = object;
                string2 = object2;
            }
        }
        VersionUtil._close(bufferedReader);
        string2 = object3;
        object4 = object2;
        object3 = object;
        object = object4;
        if (object4 != null) {
            object = object4.trim();
        }
        object2 = object3;
        if (object3 == null) return VersionUtil.parseVersion(string2, (String)object, (String)object2);
        object2 = object3.trim();
        return VersionUtil.parseVersion(string2, (String)object, (String)object2);
        catch (Throwable throwable) {
            VersionUtil._close(bufferedReader);
            throw throwable;
        }
    }

    public static Version mavenVersionFor(ClassLoader object, String object2, String string2) {
        if ((object = object.getResourceAsStream("META-INF/maven/" + object2.replaceAll("\\.", "/") + "/" + string2 + "/pom.properties")) != null) {
            try {
                object2 = new Properties();
                object2.load((InputStream)object);
                string2 = object2.getProperty("version");
                String string3 = object2.getProperty("artifactId");
                object2 = VersionUtil.parseVersion(string2, object2.getProperty("groupId"), string3);
                return object2;
            }
            catch (IOException var1_2) {
                VersionUtil._close((Closeable)object);
            }
        }
        return Version.unknownVersion();
        finally {
            VersionUtil._close((Closeable)object);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Version packageVersionFor(Class<?> class_) {
        class_ = Class.forName(class_.getPackage().getName() + ".PackageVersion", true, class_.getClassLoader());
        {
            catch (Exception exception) {
                return null;
            }
        }
        try {
            return ((Versioned)class_.newInstance()).version();
        }
        catch (Exception var1_3) {
            throw new IllegalArgumentException("Failed to get Versioned out of " + class_);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Version parseVersion(String string2, String string3, String string4) {
        Object var6_3 = null;
        int n2 = 0;
        if (string2 == null) return null;
        if ((string2 = string2.trim()).length() <= 0) {
            return null;
        }
        String[] arrstring = V_SEP.split(string2);
        int n3 = VersionUtil.parseVersionPart(arrstring[0]);
        int n4 = arrstring.length > 1 ? VersionUtil.parseVersionPart(arrstring[1]) : 0;
        if (arrstring.length > 2) {
            n2 = VersionUtil.parseVersionPart(arrstring[2]);
        }
        string2 = var6_3;
        if (arrstring.length > 3) {
            string2 = arrstring[3];
        }
        return new Version(n3, n4, n2, string2, string3, string4);
    }

    protected static int parseVersionPart(String string2) {
        int n2 = 0;
        int n3 = 0;
        int n4 = string2.length();
        char c2;
        while (n3 < n4 && (c2 = string2.charAt(n3)) <= '9' && c2 >= '0') {
            n2 = n2 * 10 + (c2 - 48);
            ++n3;
        }
        return n2;
    }

    public static final void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }

    public static Version versionFor(Class<?> object) {
        Version version = VersionUtil.packageVersionFor(object);
        if (version != null) {
            return version;
        }
        if ((object = object.getResourceAsStream("VERSION.txt")) == null) {
            return Version.unknownVersion();
        }
        try {
            version = VersionUtil.doReadVersion(new InputStreamReader((InputStream)object, "UTF-8"));
            return version;
        }
        catch (UnsupportedEncodingException var1_2) {
            Version version2 = Version.unknownVersion();
            return version2;
        }
        finally {
            VersionUtil._close((Closeable)object);
        }
    }

    public Version version() {
        return this._v;
    }
}


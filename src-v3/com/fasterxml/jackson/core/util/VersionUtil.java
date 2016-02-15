package com.fasterxml.jackson.core.util;

import com.crumby.impl.device.DeviceFragment;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Hex;

public class VersionUtil {
    private static final Pattern V_SEP;
    private final Version _v;

    static {
        V_SEP = Pattern.compile("[-_./;:]");
    }

    protected VersionUtil() {
        Version v = null;
        try {
            v = versionFor(getClass());
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load Version information from " + getClass());
        }
        if (v == null) {
            v = Version.unknownVersion();
        }
        this._v = v;
    }

    public Version version() {
        return this._v;
    }

    public static Version versionFor(Class<?> cls) {
        Version packageVersionFor = packageVersionFor(cls);
        if (packageVersionFor != null) {
            return packageVersionFor;
        }
        InputStream in = cls.getResourceAsStream("VERSION.txt");
        if (in == null) {
            return Version.unknownVersion();
        }
        try {
            packageVersionFor = doReadVersion(new InputStreamReader(in, Hex.DEFAULT_CHARSET_NAME));
            return packageVersionFor;
        } catch (UnsupportedEncodingException e) {
            packageVersionFor = Version.unknownVersion();
            return packageVersionFor;
        } finally {
            _close(in);
        }
    }

    public static Version packageVersionFor(Class<?> cls) {
        Class<?> vClass;
        try {
            vClass = Class.forName(cls.getPackage().getName() + ".PackageVersion", true, cls.getClassLoader());
            return ((Versioned) vClass.newInstance()).version();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get Versioned out of " + vClass);
        } catch (Exception e2) {
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.fasterxml.jackson.core.Version doReadVersion(java.io.Reader r5) {
        /*
        r3 = 0;
        r2 = 0;
        r0 = 0;
        r1 = new java.io.BufferedReader;
        r1.<init>(r5);
        r3 = r1.readLine();	 Catch:{ IOException -> 0x002c, all -> 0x0031 }
        if (r3 == 0) goto L_0x0018;
    L_0x000e:
        r2 = r1.readLine();	 Catch:{ IOException -> 0x002c, all -> 0x0031 }
        if (r2 == 0) goto L_0x0018;
    L_0x0014:
        r0 = r1.readLine();	 Catch:{ IOException -> 0x002c, all -> 0x0031 }
    L_0x0018:
        _close(r1);
    L_0x001b:
        if (r2 == 0) goto L_0x0021;
    L_0x001d:
        r2 = r2.trim();
    L_0x0021:
        if (r0 == 0) goto L_0x0027;
    L_0x0023:
        r0 = r0.trim();
    L_0x0027:
        r4 = parseVersion(r3, r2, r0);
        return r4;
    L_0x002c:
        r4 = move-exception;
        _close(r1);
        goto L_0x001b;
    L_0x0031:
        r4 = move-exception;
        _close(r1);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.util.VersionUtil.doReadVersion(java.io.Reader):com.fasterxml.jackson.core.Version");
    }

    public static Version mavenVersionFor(ClassLoader cl, String groupId, String artifactId) {
        InputStream pomProperties = cl.getResourceAsStream("META-INF/maven/" + groupId.replaceAll("\\.", DeviceFragment.REGEX_BASE) + DeviceFragment.REGEX_BASE + artifactId + "/pom.properties");
        if (pomProperties != null) {
            Version parseVersion;
            try {
                Properties props = new Properties();
                props.load(pomProperties);
                parseVersion = parseVersion(props.getProperty("version"), props.getProperty("groupId"), props.getProperty("artifactId"));
                return parseVersion;
            } catch (IOException e) {
                parseVersion = e;
            } finally {
                _close(pomProperties);
            }
        }
        return Version.unknownVersion();
    }

    public static Version parseVersion(String s, String groupId, String artifactId) {
        String str = null;
        int i = 0;
        if (s != null) {
            s = s.trim();
            if (s.length() > 0) {
                int parseVersionPart;
                String[] parts = V_SEP.split(s);
                int parseVersionPart2 = parseVersionPart(parts[0]);
                if (parts.length > 1) {
                    parseVersionPart = parseVersionPart(parts[1]);
                } else {
                    parseVersionPart = 0;
                }
                if (parts.length > 2) {
                    i = parseVersionPart(parts[2]);
                }
                if (parts.length > 3) {
                    str = parts[3];
                }
                return new Version(parseVersionPart2, parseVersionPart, i, str, groupId, artifactId);
            }
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static int parseVersionPart(java.lang.String r6) {
        /*
        r3 = 0;
        r1 = 0;
        r2 = r6.length();
    L_0x0006:
        if (r1 >= r2) goto L_0x0014;
    L_0x0008:
        r0 = r6.charAt(r1);
        r4 = 57;
        if (r0 > r4) goto L_0x0014;
    L_0x0010:
        r4 = 48;
        if (r0 >= r4) goto L_0x0015;
    L_0x0014:
        return r3;
    L_0x0015:
        r4 = r3 * 10;
        r5 = r0 + -48;
        r3 = r4 + r5;
        r1 = r1 + 1;
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.util.VersionUtil.parseVersionPart(java.lang.String):int");
    }

    private static final void _close(Closeable c) {
        try {
            c.close();
        } catch (IOException e) {
        }
    }

    public static final void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }
}

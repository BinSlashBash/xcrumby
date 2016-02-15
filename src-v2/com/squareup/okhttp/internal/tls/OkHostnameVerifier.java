/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.tls;

import com.squareup.okhttp.internal.tls.DistinguishedNameParser;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

public final class OkHostnameVerifier
implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;
    public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();
    private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    private OkHostnameVerifier() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<String> getSubjectAltNames(X509Certificate list, int n2) {
        ArrayList arrayList = new ArrayList();
        try {
            list = list.getSubjectAlternativeNames();
            if (list == null) {
                return Collections.emptyList();
            }
            Iterator iterator = list.iterator();
            do {
                Integer n3;
                list = arrayList;
                if (!iterator.hasNext()) return list;
                list = iterator.next();
                if (list == null || list.size() < 2 || (n3 = (Integer)((Object)list.get(0))) == null || n3 != n2 || (list = (String)((Object)list.get(1))) == null) continue;
                arrayList.add(list);
            } while (true);
        }
        catch (CertificateParsingException var1_2) {
            return Collections.emptyList();
        }
    }

    static boolean verifyAsIpAddress(String string2) {
        return VERIFY_AS_IP_ADDRESS.matcher(string2).matches();
    }

    private boolean verifyHostName(String string2, X509Certificate object) {
        string2 = string2.toLowerCase(Locale.US);
        boolean bl2 = false;
        for (String string3 : this.getSubjectAltNames((X509Certificate)object, 2)) {
            bl2 = true;
            if (!this.verifyHostName(string2, string3)) continue;
            return true;
        }
        if (!bl2 && (object = new DistinguishedNameParser(object.getSubjectX500Principal()).findMostSpecific("cn")) != null) {
            return this.verifyHostName(string2, (String)object);
        }
        return false;
    }

    private boolean verifyIpAddress(String string2, X509Certificate object) {
        object = this.getSubjectAltNames((X509Certificate)object, 7).iterator();
        while (object.hasNext()) {
            if (!string2.equalsIgnoreCase((String)object.next())) continue;
            return true;
        }
        return false;
    }

    public boolean verify(String string2, X509Certificate x509Certificate) {
        if (OkHostnameVerifier.verifyAsIpAddress(string2)) {
            return this.verifyIpAddress(string2, x509Certificate);
        }
        return this.verifyHostName(string2, x509Certificate);
    }

    @Override
    public boolean verify(String string2, SSLSession sSLSession) {
        try {
            boolean bl2 = this.verify(string2, (X509Certificate)sSLSession.getPeerCertificates()[0]);
            return bl2;
        }
        catch (SSLException var1_2) {
            return false;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean verifyHostName(String string2, String string3) {
        boolean bl2;
        int n2;
        boolean bl3 = true;
        if (string2 == null) return false;
        if (string2.length() == 0) return false;
        if (string3 == null) return false;
        if (string3.length() == 0) {
            return false;
        }
        if (!(string3 = string3.toLowerCase(Locale.US)).contains("*")) {
            return string2.equals(string3);
        }
        if (string3.startsWith("*.")) {
            bl2 = bl3;
            if (string2.regionMatches(0, string3, 2, string3.length() - 2)) return bl2;
        }
        if ((n2 = string3.indexOf(42)) > string3.indexOf(46)) {
            return false;
        }
        if (!string2.regionMatches(0, string3, 0, n2)) {
            return false;
        }
        int n3 = string3.length() - (n2 + 1);
        int n4 = string2.length() - n3;
        if (string2.indexOf(46, n2) < n4) {
            return false;
        }
        bl2 = bl3;
        if (string2.regionMatches(n4, string3, n2 + 1, n3)) return bl2;
        return false;
    }
}


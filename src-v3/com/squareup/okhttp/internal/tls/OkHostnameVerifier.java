package com.squareup.okhttp.internal.tls;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public final class OkHostnameVerifier implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;
    public static final OkHostnameVerifier INSTANCE;
    private static final Pattern VERIFY_AS_IP_ADDRESS;

    static {
        INSTANCE = new OkHostnameVerifier();
        VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    }

    private OkHostnameVerifier() {
    }

    public boolean verify(String host, SSLSession session) {
        try {
            return verify(host, (X509Certificate) session.getPeerCertificates()[0]);
        } catch (SSLException e) {
            return false;
        }
    }

    public boolean verify(String host, X509Certificate certificate) {
        return verifyAsIpAddress(host) ? verifyIpAddress(host, certificate) : verifyHostName(host, certificate);
    }

    static boolean verifyAsIpAddress(String host) {
        return VERIFY_AS_IP_ADDRESS.matcher(host).matches();
    }

    private boolean verifyIpAddress(String ipAddress, X509Certificate certificate) {
        for (String altName : getSubjectAltNames(certificate, ALT_IPA_NAME)) {
            if (ipAddress.equalsIgnoreCase(altName)) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyHostName(String hostName, X509Certificate certificate) {
        hostName = hostName.toLowerCase(Locale.US);
        boolean hasDns = false;
        for (String altName : getSubjectAltNames(certificate, ALT_DNS_NAME)) {
            hasDns = true;
            if (verifyHostName(hostName, altName)) {
                return true;
            }
        }
        if (!hasDns) {
            String cn = new DistinguishedNameParser(certificate.getSubjectX500Principal()).findMostSpecific("cn");
            if (cn != null) {
                return verifyHostName(hostName, cn);
            }
        }
        return false;
    }

    private List<String> getSubjectAltNames(X509Certificate certificate, int type) {
        List<String> result = new ArrayList();
        try {
            Collection<?> subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) {
                return Collections.emptyList();
            }
            Iterator i$ = subjectAltNames.iterator();
            while (i$.hasNext()) {
                List<?> entry = (List) i$.next();
                if (entry != null && entry.size() >= ALT_DNS_NAME) {
                    Integer altNameType = (Integer) entry.get(0);
                    if (altNameType != null && altNameType.intValue() == type) {
                        String altName = (String) entry.get(1);
                        if (altName != null) {
                            result.add(altName);
                        }
                    }
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    public boolean verifyHostName(String hostName, String cn) {
        if (hostName == null || hostName.length() == 0 || cn == null || cn.length() == 0) {
            return false;
        }
        cn = cn.toLowerCase(Locale.US);
        if (!cn.contains("*")) {
            return hostName.equals(cn);
        }
        if (cn.startsWith("*.") && hostName.regionMatches(0, cn, ALT_DNS_NAME, cn.length() - 2)) {
            return true;
        }
        int asterisk = cn.indexOf(42);
        if (asterisk > cn.indexOf(46)) {
            return false;
        }
        if (!hostName.regionMatches(0, cn, 0, asterisk)) {
            return false;
        }
        int suffixLength = cn.length() - (asterisk + 1);
        int suffixStart = hostName.length() - suffixLength;
        if (hostName.indexOf(46, asterisk) < suffixStart) {
            return false;
        }
        if (hostName.regionMatches(suffixStart, cn, asterisk + 1, suffixLength)) {
            return true;
        }
        return false;
    }
}

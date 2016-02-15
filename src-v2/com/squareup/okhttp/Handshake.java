/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

public final class Handshake {
    private final String cipherSuite;
    private final List<Certificate> localCertificates;
    private final List<Certificate> peerCertificates;

    private Handshake(String string2, List<Certificate> list, List<Certificate> list2) {
        this.cipherSuite = string2;
        this.peerCertificates = list;
        this.localCertificates = list2;
    }

    public static Handshake get(String string2, List<Certificate> list, List<Certificate> list2) {
        if (string2 == null) {
            throw new IllegalArgumentException("cipherSuite == null");
        }
        return new Handshake(string2, Util.immutableList(list), Util.immutableList(list2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Handshake get(SSLSession list) {
        List<Certificate> list2;
        String string2 = list.getCipherSuite();
        if (string2 == null) {
            throw new IllegalStateException("cipherSuite == null");
        }
        try {
            list2 = list.getPeerCertificates();
        }
        catch (SSLPeerUnverifiedException var1_3) {
            list2 = null;
        }
        list2 = list2 != null ? Util.immutableList(list2) : Collections.emptyList();
        if ((list = list.getLocalCertificates()) != null) {
            list = Util.immutableList(list);
            return new Handshake(string2, list2, list);
        }
        list = Collections.emptyList();
        return new Handshake(string2, list2, list);
    }

    public String cipherSuite() {
        return this.cipherSuite;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof Handshake)) {
            return false;
        }
        object = (Handshake)object;
        if (!this.cipherSuite.equals(object.cipherSuite)) return false;
        if (!this.peerCertificates.equals(object.peerCertificates)) return false;
        if (!this.localCertificates.equals(object.localCertificates)) return false;
        return true;
    }

    public int hashCode() {
        return ((this.cipherSuite.hashCode() + 527) * 31 + this.peerCertificates.hashCode()) * 31 + this.localCertificates.hashCode();
    }

    public List<Certificate> localCertificates() {
        return this.localCertificates;
    }

    public Principal localPrincipal() {
        if (!this.localCertificates.isEmpty()) {
            return ((X509Certificate)this.localCertificates.get(0)).getSubjectX500Principal();
        }
        return null;
    }

    public List<Certificate> peerCertificates() {
        return this.peerCertificates;
    }

    public Principal peerPrincipal() {
        if (!this.peerCertificates.isEmpty()) {
            return ((X509Certificate)this.peerCertificates.get(0)).getSubjectX500Principal();
        }
        return null;
    }
}


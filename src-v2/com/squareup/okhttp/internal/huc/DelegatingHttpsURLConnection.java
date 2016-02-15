/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Handshake;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

abstract class DelegatingHttpsURLConnection
extends HttpsURLConnection {
    private final HttpURLConnection delegate;

    public DelegatingHttpsURLConnection(HttpURLConnection httpURLConnection) {
        super(httpURLConnection.getURL());
        this.delegate = httpURLConnection;
    }

    @Override
    public void addRequestProperty(String string2, String string3) {
        this.delegate.addRequestProperty(string2, string3);
    }

    @Override
    public void connect() throws IOException {
        this.connected = true;
        this.delegate.connect();
    }

    @Override
    public void disconnect() {
        this.delegate.disconnect();
    }

    @Override
    public boolean getAllowUserInteraction() {
        return this.delegate.getAllowUserInteraction();
    }

    @Override
    public String getCipherSuite() {
        Handshake handshake = this.handshake();
        if (handshake != null) {
            return handshake.cipherSuite();
        }
        return null;
    }

    @Override
    public int getConnectTimeout() {
        return this.delegate.getConnectTimeout();
    }

    @Override
    public Object getContent() throws IOException {
        return this.delegate.getContent();
    }

    @Override
    public Object getContent(Class[] arrclass) throws IOException {
        return this.delegate.getContent(arrclass);
    }

    @Override
    public String getContentEncoding() {
        return this.delegate.getContentEncoding();
    }

    @Override
    public int getContentLength() {
        return this.delegate.getContentLength();
    }

    @Override
    public String getContentType() {
        return this.delegate.getContentType();
    }

    @Override
    public long getDate() {
        return this.delegate.getDate();
    }

    @Override
    public boolean getDefaultUseCaches() {
        return this.delegate.getDefaultUseCaches();
    }

    @Override
    public boolean getDoInput() {
        return this.delegate.getDoInput();
    }

    @Override
    public boolean getDoOutput() {
        return this.delegate.getDoOutput();
    }

    @Override
    public InputStream getErrorStream() {
        return this.delegate.getErrorStream();
    }

    @Override
    public long getExpiration() {
        return this.delegate.getExpiration();
    }

    @Override
    public String getHeaderField(int n2) {
        return this.delegate.getHeaderField(n2);
    }

    @Override
    public String getHeaderField(String string2) {
        return this.delegate.getHeaderField(string2);
    }

    @Override
    public long getHeaderFieldDate(String string2, long l2) {
        return this.delegate.getHeaderFieldDate(string2, l2);
    }

    @Override
    public int getHeaderFieldInt(String string2, int n2) {
        return this.delegate.getHeaderFieldInt(string2, n2);
    }

    @Override
    public String getHeaderFieldKey(int n2) {
        return this.delegate.getHeaderFieldKey(n2);
    }

    @Override
    public Map<String, List<String>> getHeaderFields() {
        return this.delegate.getHeaderFields();
    }

    @Override
    public abstract HostnameVerifier getHostnameVerifier();

    @Override
    public long getIfModifiedSince() {
        return this.delegate.getIfModifiedSince();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.delegate.getInputStream();
    }

    @Override
    public boolean getInstanceFollowRedirects() {
        return this.delegate.getInstanceFollowRedirects();
    }

    @Override
    public long getLastModified() {
        return this.delegate.getLastModified();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Certificate[] getLocalCertificates() {
        Object object = this.handshake();
        if (object == null || (object = object.localCertificates()).isEmpty()) {
            return null;
        }
        return object.toArray(new Certificate[object.size()]);
    }

    @Override
    public Principal getLocalPrincipal() {
        Handshake handshake = this.handshake();
        if (handshake != null) {
            return handshake.localPrincipal();
        }
        return null;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.delegate.getOutputStream();
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        Handshake handshake = this.handshake();
        if (handshake != null) {
            return handshake.peerPrincipal();
        }
        return null;
    }

    @Override
    public Permission getPermission() throws IOException {
        return this.delegate.getPermission();
    }

    @Override
    public int getReadTimeout() {
        return this.delegate.getReadTimeout();
    }

    @Override
    public String getRequestMethod() {
        return this.delegate.getRequestMethod();
    }

    @Override
    public Map<String, List<String>> getRequestProperties() {
        return this.delegate.getRequestProperties();
    }

    @Override
    public String getRequestProperty(String string2) {
        return this.delegate.getRequestProperty(string2);
    }

    @Override
    public int getResponseCode() throws IOException {
        return this.delegate.getResponseCode();
    }

    @Override
    public String getResponseMessage() throws IOException {
        return this.delegate.getResponseMessage();
    }

    @Override
    public abstract SSLSocketFactory getSSLSocketFactory();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        Object object = this.handshake();
        if (object == null || (object = object.peerCertificates()).isEmpty()) {
            return null;
        }
        return object.toArray(new Certificate[object.size()]);
    }

    @Override
    public URL getURL() {
        return this.delegate.getURL();
    }

    @Override
    public boolean getUseCaches() {
        return this.delegate.getUseCaches();
    }

    protected abstract Handshake handshake();

    @Override
    public void setAllowUserInteraction(boolean bl2) {
        this.delegate.setAllowUserInteraction(bl2);
    }

    @Override
    public void setChunkedStreamingMode(int n2) {
        this.delegate.setChunkedStreamingMode(n2);
    }

    @Override
    public void setConnectTimeout(int n2) {
        this.delegate.setConnectTimeout(n2);
    }

    @Override
    public void setDefaultUseCaches(boolean bl2) {
        this.delegate.setDefaultUseCaches(bl2);
    }

    @Override
    public void setDoInput(boolean bl2) {
        this.delegate.setDoInput(bl2);
    }

    @Override
    public void setDoOutput(boolean bl2) {
        this.delegate.setDoOutput(bl2);
    }

    @Override
    public void setFixedLengthStreamingMode(int n2) {
        this.delegate.setFixedLengthStreamingMode(n2);
    }

    @Override
    public abstract void setHostnameVerifier(HostnameVerifier var1);

    @Override
    public void setIfModifiedSince(long l2) {
        this.delegate.setIfModifiedSince(l2);
    }

    @Override
    public void setInstanceFollowRedirects(boolean bl2) {
        this.delegate.setInstanceFollowRedirects(bl2);
    }

    @Override
    public void setReadTimeout(int n2) {
        this.delegate.setReadTimeout(n2);
    }

    @Override
    public void setRequestMethod(String string2) throws ProtocolException {
        this.delegate.setRequestMethod(string2);
    }

    @Override
    public void setRequestProperty(String string2, String string3) {
        this.delegate.setRequestProperty(string2, string3);
    }

    @Override
    public abstract void setSSLSocketFactory(SSLSocketFactory var1);

    @Override
    public void setUseCaches(boolean bl2) {
        this.delegate.setUseCaches(bl2);
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public boolean usingProxy() {
        return this.delegate.usingProxy();
    }
}


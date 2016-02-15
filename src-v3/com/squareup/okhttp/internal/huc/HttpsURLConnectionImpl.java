package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.OkHttpClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

public final class HttpsURLConnectionImpl extends DelegatingHttpsURLConnection {
    private final HttpURLConnectionImpl delegate;

    public /* bridge */ /* synthetic */ void addRequestProperty(String x0, String x1) {
        super.addRequestProperty(x0, x1);
    }

    public /* bridge */ /* synthetic */ void connect() throws IOException {
        super.connect();
    }

    public /* bridge */ /* synthetic */ void disconnect() {
        super.disconnect();
    }

    public /* bridge */ /* synthetic */ boolean getAllowUserInteraction() {
        return super.getAllowUserInteraction();
    }

    public /* bridge */ /* synthetic */ String getCipherSuite() {
        return super.getCipherSuite();
    }

    public /* bridge */ /* synthetic */ int getConnectTimeout() {
        return super.getConnectTimeout();
    }

    public /* bridge */ /* synthetic */ Object getContent() throws IOException {
        return super.getContent();
    }

    public /* bridge */ /* synthetic */ Object getContent(Class[] x0) throws IOException {
        return super.getContent(x0);
    }

    public /* bridge */ /* synthetic */ String getContentEncoding() {
        return super.getContentEncoding();
    }

    public /* bridge */ /* synthetic */ int getContentLength() {
        return super.getContentLength();
    }

    public /* bridge */ /* synthetic */ String getContentType() {
        return super.getContentType();
    }

    public /* bridge */ /* synthetic */ long getDate() {
        return super.getDate();
    }

    public /* bridge */ /* synthetic */ boolean getDefaultUseCaches() {
        return super.getDefaultUseCaches();
    }

    public /* bridge */ /* synthetic */ boolean getDoInput() {
        return super.getDoInput();
    }

    public /* bridge */ /* synthetic */ boolean getDoOutput() {
        return super.getDoOutput();
    }

    public /* bridge */ /* synthetic */ InputStream getErrorStream() {
        return super.getErrorStream();
    }

    public /* bridge */ /* synthetic */ long getExpiration() {
        return super.getExpiration();
    }

    public /* bridge */ /* synthetic */ long getHeaderFieldDate(String x0, long x1) {
        return super.getHeaderFieldDate(x0, x1);
    }

    public /* bridge */ /* synthetic */ int getHeaderFieldInt(String x0, int x1) {
        return super.getHeaderFieldInt(x0, x1);
    }

    public /* bridge */ /* synthetic */ String getHeaderFieldKey(int x0) {
        return super.getHeaderFieldKey(x0);
    }

    public /* bridge */ /* synthetic */ Map getHeaderFields() {
        return super.getHeaderFields();
    }

    public /* bridge */ /* synthetic */ long getIfModifiedSince() {
        return super.getIfModifiedSince();
    }

    public /* bridge */ /* synthetic */ InputStream getInputStream() throws IOException {
        return super.getInputStream();
    }

    public /* bridge */ /* synthetic */ boolean getInstanceFollowRedirects() {
        return super.getInstanceFollowRedirects();
    }

    public /* bridge */ /* synthetic */ long getLastModified() {
        return super.getLastModified();
    }

    public /* bridge */ /* synthetic */ Certificate[] getLocalCertificates() {
        return super.getLocalCertificates();
    }

    public /* bridge */ /* synthetic */ Principal getLocalPrincipal() {
        return super.getLocalPrincipal();
    }

    public /* bridge */ /* synthetic */ OutputStream getOutputStream() throws IOException {
        return super.getOutputStream();
    }

    public /* bridge */ /* synthetic */ Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return super.getPeerPrincipal();
    }

    public /* bridge */ /* synthetic */ Permission getPermission() throws IOException {
        return super.getPermission();
    }

    public /* bridge */ /* synthetic */ int getReadTimeout() {
        return super.getReadTimeout();
    }

    public /* bridge */ /* synthetic */ String getRequestMethod() {
        return super.getRequestMethod();
    }

    public /* bridge */ /* synthetic */ Map getRequestProperties() {
        return super.getRequestProperties();
    }

    public /* bridge */ /* synthetic */ String getRequestProperty(String x0) {
        return super.getRequestProperty(x0);
    }

    public /* bridge */ /* synthetic */ int getResponseCode() throws IOException {
        return super.getResponseCode();
    }

    public /* bridge */ /* synthetic */ String getResponseMessage() throws IOException {
        return super.getResponseMessage();
    }

    public /* bridge */ /* synthetic */ Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        return super.getServerCertificates();
    }

    public /* bridge */ /* synthetic */ URL getURL() {
        return super.getURL();
    }

    public /* bridge */ /* synthetic */ boolean getUseCaches() {
        return super.getUseCaches();
    }

    public /* bridge */ /* synthetic */ void setAllowUserInteraction(boolean x0) {
        super.setAllowUserInteraction(x0);
    }

    public /* bridge */ /* synthetic */ void setChunkedStreamingMode(int x0) {
        super.setChunkedStreamingMode(x0);
    }

    public /* bridge */ /* synthetic */ void setConnectTimeout(int x0) {
        super.setConnectTimeout(x0);
    }

    public /* bridge */ /* synthetic */ void setDefaultUseCaches(boolean x0) {
        super.setDefaultUseCaches(x0);
    }

    public /* bridge */ /* synthetic */ void setDoInput(boolean x0) {
        super.setDoInput(x0);
    }

    public /* bridge */ /* synthetic */ void setDoOutput(boolean x0) {
        super.setDoOutput(x0);
    }

    public /* bridge */ /* synthetic */ void setIfModifiedSince(long x0) {
        super.setIfModifiedSince(x0);
    }

    public /* bridge */ /* synthetic */ void setInstanceFollowRedirects(boolean x0) {
        super.setInstanceFollowRedirects(x0);
    }

    public /* bridge */ /* synthetic */ void setReadTimeout(int x0) {
        super.setReadTimeout(x0);
    }

    public /* bridge */ /* synthetic */ void setRequestMethod(String x0) throws ProtocolException {
        super.setRequestMethod(x0);
    }

    public /* bridge */ /* synthetic */ void setRequestProperty(String x0, String x1) {
        super.setRequestProperty(x0, x1);
    }

    public /* bridge */ /* synthetic */ void setUseCaches(boolean x0) {
        super.setUseCaches(x0);
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ boolean usingProxy() {
        return super.usingProxy();
    }

    public HttpsURLConnectionImpl(URL url, OkHttpClient client) {
        this(new HttpURLConnectionImpl(url, client));
    }

    public HttpsURLConnectionImpl(HttpURLConnectionImpl delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    protected Handshake handshake() {
        if (this.delegate.httpEngine != null) {
            return this.delegate.httpEngine.hasResponse() ? this.delegate.httpEngine.getResponse().handshake() : this.delegate.handshake;
        } else {
            throw new IllegalStateException("Connection has not yet been established");
        }
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.delegate.client.setHostnameVerifier(hostnameVerifier);
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.delegate.client.getHostnameVerifier();
    }

    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.delegate.client.setSslSocketFactory(sslSocketFactory);
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return this.delegate.client.getSslSocketFactory();
    }

    public long getContentLengthLong() {
        return this.delegate.getContentLengthLong();
    }

    public void setFixedLengthStreamingMode(long contentLength) {
        this.delegate.setFixedLengthStreamingMode(contentLength);
    }

    public long getHeaderFieldLong(String field, long defaultValue) {
        return this.delegate.getHeaderFieldLong(field, defaultValue);
    }
}

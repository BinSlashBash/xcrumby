package com.squareup.okhttp.internal;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.http.RouteSelector;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

public class Platform {
    private static final Platform PLATFORM;

    private static class JettyNegoProvider implements InvocationHandler {
        private final List<String> protocols;
        private String selected;
        private boolean unsupported;

        public JettyNegoProvider(List<String> protocols) {
            this.protocols = protocols;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            Class<?> returnType = method.getReturnType();
            if (args == null) {
                args = Util.EMPTY_STRING_ARRAY;
            }
            if (methodName.equals("supports") && Boolean.TYPE == returnType) {
                return Boolean.valueOf(true);
            }
            if (methodName.equals("unsupported") && Void.TYPE == returnType) {
                this.unsupported = true;
                return null;
            } else if (methodName.equals("protocols") && args.length == 0) {
                return this.protocols;
            } else {
                if ((methodName.equals("selectProtocol") || methodName.equals("select")) && String.class == returnType && args.length == 1 && (args[0] instanceof List)) {
                    String str;
                    List<String> peerProtocols = args[0];
                    int size = peerProtocols.size();
                    for (int i = 0; i < size; i++) {
                        if (this.protocols.contains(peerProtocols.get(i))) {
                            str = (String) peerProtocols.get(i);
                            this.selected = str;
                            return str;
                        }
                    }
                    str = (String) this.protocols.get(0);
                    this.selected = str;
                    return str;
                } else if ((!methodName.equals("protocolSelected") && !methodName.equals("selected")) || args.length != 1) {
                    return method.invoke(this, args);
                } else {
                    this.selected = (String) args[0];
                    return null;
                }
            }
        }
    }

    private static class Android extends Platform {
        private final Method getNpnSelectedProtocol;
        protected final Class<?> openSslSocketClass;
        private final Method setHostname;
        private final Method setNpnProtocols;
        private final Method setUseSessionTickets;
        private final Method trafficStatsTagSocket;
        private final Method trafficStatsUntagSocket;

        private Android(Class<?> openSslSocketClass, Method setUseSessionTickets, Method setHostname, Method trafficStatsTagSocket, Method trafficStatsUntagSocket, Method setNpnProtocols, Method getNpnSelectedProtocol) {
            this.openSslSocketClass = openSslSocketClass;
            this.setUseSessionTickets = setUseSessionTickets;
            this.setHostname = setHostname;
            this.trafficStatsTagSocket = trafficStatsTagSocket;
            this.trafficStatsUntagSocket = trafficStatsUntagSocket;
            this.setNpnProtocols = setNpnProtocols;
            this.getNpnSelectedProtocol = getNpnSelectedProtocol;
        }

        public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
            try {
                socket.connect(address, connectTimeout);
            } catch (SecurityException se) {
                IOException ioException = new IOException("Exception in connect");
                ioException.initCause(se);
                throw ioException;
            }
        }

        public void configureTls(SSLSocket socket, String uriHost, String tlsVersion) {
            super.configureTls(socket, uriHost, tlsVersion);
            if (tlsVersion.equals(RouteSelector.TLS_V1) && this.openSslSocketClass.isInstance(socket)) {
                try {
                    this.setUseSessionTickets.invoke(socket, new Object[]{Boolean.valueOf(true)});
                    this.setHostname.invoke(socket, new Object[]{uriHost});
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e2) {
                    throw new AssertionError(e2);
                }
            }
        }

        public void setProtocols(SSLSocket socket, List<Protocol> protocols) {
            if (this.setNpnProtocols != null && this.openSslSocketClass.isInstance(socket)) {
                try {
                    this.setNpnProtocols.invoke(socket, new Object[]{Platform.concatLengthPrefixed(protocols)});
                } catch (IllegalAccessException e) {
                    throw new AssertionError(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }

        public String getSelectedProtocol(SSLSocket socket) {
            if (this.getNpnSelectedProtocol == null) {
                return null;
            }
            if (!this.openSslSocketClass.isInstance(socket)) {
                return null;
            }
            try {
                byte[] npnResult = (byte[]) this.getNpnSelectedProtocol.invoke(socket, new Object[0]);
                if (npnResult == null) {
                    return null;
                }
                return new String(npnResult, Util.UTF_8);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public void tagSocket(Socket socket) throws SocketException {
            if (this.trafficStatsTagSocket != null) {
                try {
                    this.trafficStatsTagSocket.invoke(null, new Object[]{socket});
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }

        public void untagSocket(Socket socket) throws SocketException {
            if (this.trafficStatsUntagSocket != null) {
                try {
                    this.trafficStatsUntagSocket.invoke(null, new Object[]{socket});
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
    }

    private static class JdkWithJettyBootPlatform extends Platform {
        private final Class<?> clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Class<?> serverProviderClass;

        public JdkWithJettyBootPlatform(Method putMethod, Method getMethod, Class<?> clientProviderClass, Class<?> serverProviderClass) {
            this.putMethod = putMethod;
            this.getMethod = getMethod;
            this.clientProviderClass = clientProviderClass;
            this.serverProviderClass = serverProviderClass;
        }

        public void setProtocols(SSLSocket socket, List<Protocol> protocols) {
            try {
                List<String> names = new ArrayList(protocols.size());
                int size = protocols.size();
                for (int i = 0; i < size; i++) {
                    Protocol protocol = (Protocol) protocols.get(i);
                    if (protocol != Protocol.HTTP_1_0) {
                        names.add(protocol.toString());
                    }
                }
                Object provider = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(names));
                this.putMethod.invoke(null, new Object[]{socket, provider});
            } catch (InvocationTargetException e) {
                throw new AssertionError(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public String getSelectedProtocol(SSLSocket socket) {
            String str = null;
            try {
                JettyNegoProvider provider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, new Object[]{socket}));
                if (!provider.unsupported && provider.selected == null) {
                    Logger.getLogger("com.squareup.okhttp.OkHttpClient").log(Level.INFO, "NPN/ALPN callback dropped: SPDY and HTTP/2 are disabled. Is npn-boot or alpn-boot on the boot class path?");
                } else if (!provider.unsupported) {
                    str = provider.selected;
                }
                return str;
            } catch (InvocationTargetException e) {
                throw new AssertionError();
            } catch (IllegalAccessException e2) {
                throw new AssertionError();
            }
        }
    }

    static {
        PLATFORM = findPlatform();
    }

    public static Platform get() {
        return PLATFORM;
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public void logW(String warning) {
        System.out.println(warning);
    }

    public void tagSocket(Socket socket) throws SocketException {
    }

    public void untagSocket(Socket socket) throws SocketException {
    }

    public URI toUriLenient(URL url) throws URISyntaxException {
        return url.toURI();
    }

    public void configureTls(SSLSocket socket, String uriHost, String tlsVersion) {
        if (tlsVersion.equals(RouteSelector.SSL_V3)) {
            socket.setEnabledProtocols(new String[]{RouteSelector.SSL_V3});
        }
    }

    public String getSelectedProtocol(SSLSocket socket) {
        return null;
    }

    public void setProtocols(SSLSocket socket, List<Protocol> list) {
    }

    public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
        socket.connect(address, connectTimeout);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.squareup.okhttp.internal.Platform findPlatform() {
        /*
        r2 = "com.android.org.conscrypt.OpenSSLSocketImpl";
        r3 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x006f, NoSuchMethodException -> 0x0077 }
    L_0x0006:
        r2 = "setUseSessionTickets";
        r10 = 1;
        r10 = new java.lang.Class[r10];	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        r20 = 0;
        r21 = java.lang.Boolean.TYPE;	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        r10[r20] = r21;	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        r4 = r3.getMethod(r2, r10);	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        r2 = "setHostname";
        r10 = 1;
        r10 = new java.lang.Class[r10];	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        r20 = 0;
        r21 = java.lang.String.class;
        r10[r20] = r21;	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        r5 = r3.getMethod(r2, r10);	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        r6 = 0;
        r7 = 0;
        r2 = "android.net.TrafficStats";
        r19 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x010a, NoSuchMethodException -> 0x0107 }
        r2 = "tagSocket";
        r10 = 1;
        r10 = new java.lang.Class[r10];	 Catch:{ ClassNotFoundException -> 0x010a, NoSuchMethodException -> 0x0107 }
        r20 = 0;
        r21 = java.net.Socket.class;
        r10[r20] = r21;	 Catch:{ ClassNotFoundException -> 0x010a, NoSuchMethodException -> 0x0107 }
        r0 = r19;
        r6 = r0.getMethod(r2, r10);	 Catch:{ ClassNotFoundException -> 0x010a, NoSuchMethodException -> 0x0107 }
        r2 = "untagSocket";
        r10 = 1;
        r10 = new java.lang.Class[r10];	 Catch:{ ClassNotFoundException -> 0x010a, NoSuchMethodException -> 0x0107 }
        r20 = 0;
        r21 = java.net.Socket.class;
        r10[r20] = r21;	 Catch:{ ClassNotFoundException -> 0x010a, NoSuchMethodException -> 0x0107 }
        r0 = r19;
        r7 = r0.getMethod(r2, r10);	 Catch:{ ClassNotFoundException -> 0x010a, NoSuchMethodException -> 0x0107 }
    L_0x004e:
        r8 = 0;
        r9 = 0;
        r2 = "setNpnProtocols";
        r10 = 1;
        r10 = new java.lang.Class[r10];	 Catch:{ NoSuchMethodException -> 0x0104, ClassNotFoundException -> 0x0101 }
        r20 = 0;
        r21 = byte[].class;
        r10[r20] = r21;	 Catch:{ NoSuchMethodException -> 0x0104, ClassNotFoundException -> 0x0101 }
        r8 = r3.getMethod(r2, r10);	 Catch:{ NoSuchMethodException -> 0x0104, ClassNotFoundException -> 0x0101 }
        r2 = "getNpnSelectedProtocol";
        r10 = 0;
        r10 = new java.lang.Class[r10];	 Catch:{ NoSuchMethodException -> 0x0104, ClassNotFoundException -> 0x0101 }
        r9 = r3.getMethod(r2, r10);	 Catch:{ NoSuchMethodException -> 0x0104, ClassNotFoundException -> 0x0101 }
    L_0x0068:
        r2 = new com.squareup.okhttp.internal.Platform$Android;	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        r10 = 0;
        r2.<init>(r4, r5, r6, r7, r8, r9, r10);	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
    L_0x006e:
        return r2;
    L_0x006f:
        r13 = move-exception;
        r2 = "org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl";
        r3 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x0101, NoSuchMethodException -> 0x0077 }
        goto L_0x0006;
    L_0x0077:
        r2 = move-exception;
    L_0x0078:
        r15 = "org.eclipse.jetty.alpn.ALPN";
        r14 = java.lang.Class.forName(r15);	 Catch:{ ClassNotFoundException -> 0x00f7, NoSuchMethodException -> 0x00ff }
    L_0x007e:
        r2 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2.<init>();	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = r2.append(r15);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r10 = "$Provider";
        r2 = r2.append(r10);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = r2.toString();	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r16 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2.<init>();	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = r2.append(r15);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r10 = "$ClientProvider";
        r2 = r2.append(r10);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = r2.toString();	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r11 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2.<init>();	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = r2.append(r15);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r10 = "$ServerProvider";
        r2 = r2.append(r10);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = r2.toString();	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r18 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = "put";
        r10 = 2;
        r10 = new java.lang.Class[r10];	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r20 = 0;
        r21 = javax.net.ssl.SSLSocket.class;
        r10[r20] = r21;	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r20 = 1;
        r10[r20] = r16;	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r17 = r14.getMethod(r2, r10);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = "get";
        r10 = 1;
        r10 = new java.lang.Class[r10];	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r20 = 0;
        r21 = javax.net.ssl.SSLSocket.class;
        r10[r20] = r21;	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r12 = r14.getMethod(r2, r10);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r2 = new com.squareup.okhttp.internal.Platform$JdkWithJettyBootPlatform;	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        r0 = r17;
        r1 = r18;
        r2.<init>(r0, r12, r11, r1);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        goto L_0x006e;
    L_0x00ef:
        r2 = move-exception;
    L_0x00f0:
        r2 = new com.squareup.okhttp.internal.Platform;
        r2.<init>();
        goto L_0x006e;
    L_0x00f7:
        r13 = move-exception;
        r15 = "org.eclipse.jetty.npn.NextProtoNego";
        r14 = java.lang.Class.forName(r15);	 Catch:{ ClassNotFoundException -> 0x00ef, NoSuchMethodException -> 0x00ff }
        goto L_0x007e;
    L_0x00ff:
        r2 = move-exception;
        goto L_0x00f0;
    L_0x0101:
        r2 = move-exception;
        goto L_0x0078;
    L_0x0104:
        r2 = move-exception;
        goto L_0x0068;
    L_0x0107:
        r2 = move-exception;
        goto L_0x004e;
    L_0x010a:
        r2 = move-exception;
        goto L_0x004e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.Platform.findPlatform():com.squareup.okhttp.internal.Platform");
    }

    static byte[] concatLengthPrefixed(List<Protocol> protocols) {
        Buffer result = new Buffer();
        int size = protocols.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = (Protocol) protocols.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                result.writeByte(protocol.toString().length());
                result.writeUtf8(protocol.toString());
            }
        }
        return result.readByteArray();
    }
}

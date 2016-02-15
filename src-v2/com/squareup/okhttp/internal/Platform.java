/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

public class Platform {
    private static final Platform PLATFORM = Platform.findPlatform();

    /*
     * Enabled aggressive block sorting
     */
    static byte[] concatLengthPrefixed(List<Protocol> list) {
        Buffer buffer = new Buffer();
        int n2 = 0;
        int n3 = list.size();
        while (n2 < n3) {
            Protocol protocol = list.get(n2);
            if (protocol != Protocol.HTTP_1_0) {
                buffer.writeByte(protocol.toString().length());
                buffer.writeUtf8(protocol.toString());
            }
            ++n2;
        }
        return buffer.readByteArray();
    }

    /*
     * Exception decompiling
     */
    private static Platform findPlatform() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:371)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:449)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    public static Platform get() {
        return PLATFORM;
    }

    public void configureTls(SSLSocket sSLSocket, String string2, String string3) {
        if (string3.equals("SSLv3")) {
            sSLSocket.setEnabledProtocols(new String[]{"SSLv3"});
        }
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int n2) throws IOException {
        socket.connect(inetSocketAddress, n2);
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public String getSelectedProtocol(SSLSocket sSLSocket) {
        return null;
    }

    public void logW(String string2) {
        System.out.println(string2);
    }

    public void setProtocols(SSLSocket sSLSocket, List<Protocol> list) {
    }

    public void tagSocket(Socket socket) throws SocketException {
    }

    public URI toUriLenient(URL uRL) throws URISyntaxException {
        return uRL.toURI();
    }

    public void untagSocket(Socket socket) throws SocketException {
    }

    private static class Android
    extends Platform {
        private final Method getNpnSelectedProtocol;
        protected final Class<?> openSslSocketClass;
        private final Method setHostname;
        private final Method setNpnProtocols;
        private final Method setUseSessionTickets;
        private final Method trafficStatsTagSocket;
        private final Method trafficStatsUntagSocket;

        private Android(Class<?> class_, Method method, Method method2, Method method3, Method method4, Method method5, Method method6) {
            this.openSslSocketClass = class_;
            this.setUseSessionTickets = method;
            this.setHostname = method2;
            this.trafficStatsTagSocket = method3;
            this.trafficStatsUntagSocket = method4;
            this.setNpnProtocols = method5;
            this.getNpnSelectedProtocol = method6;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void configureTls(SSLSocket sSLSocket, String string2, String string3) {
            super.configureTls(sSLSocket, string2, string3);
            if (!string3.equals("TLSv1") || !this.openSslSocketClass.isInstance(sSLSocket)) return;
            try {
                this.setUseSessionTickets.invoke(sSLSocket, true);
                this.setHostname.invoke(sSLSocket, string2);
                return;
            }
            catch (InvocationTargetException var1_2) {
                throw new RuntimeException(var1_2);
            }
            catch (IllegalAccessException var1_3) {
                throw new AssertionError(var1_3);
            }
        }

        @Override
        public void connectSocket(Socket socket, InetSocketAddress serializable, int n2) throws IOException {
            try {
                socket.connect((SocketAddress)serializable, n2);
                return;
            }
            catch (SecurityException var1_2) {
                serializable = new IOException("Exception in connect");
                serializable.initCause(var1_2);
                throw serializable;
            }
        }

        @Override
        public String getSelectedProtocol(SSLSocket object) {
            block6 : {
                if (this.getNpnSelectedProtocol == null) {
                    return null;
                }
                if (!this.openSslSocketClass.isInstance(object)) {
                    return null;
                }
                object = (byte[])this.getNpnSelectedProtocol.invoke(object, new Object[0]);
                if (object != null) break block6;
                return null;
            }
            try {
                object = new String((byte[])object, Util.UTF_8);
                return object;
            }
            catch (InvocationTargetException var1_2) {
                throw new RuntimeException(var1_2);
            }
            catch (IllegalAccessException var1_3) {
                throw new AssertionError(var1_3);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setProtocols(SSLSocket sSLSocket, List<Protocol> list) {
            if (this.setNpnProtocols == null || !this.openSslSocketClass.isInstance(sSLSocket)) {
                return;
            }
            try {
                byte[] arrby = Android.concatLengthPrefixed(list);
                this.setNpnProtocols.invoke(sSLSocket, arrby);
                return;
            }
            catch (IllegalAccessException var1_2) {
                throw new AssertionError(var1_2);
            }
            catch (InvocationTargetException var1_3) {
                throw new RuntimeException(var1_3);
            }
        }

        @Override
        public void tagSocket(Socket socket) throws SocketException {
            if (this.trafficStatsTagSocket == null) {
                return;
            }
            try {
                this.trafficStatsTagSocket.invoke(null, socket);
                return;
            }
            catch (IllegalAccessException var1_2) {
                throw new RuntimeException(var1_2);
            }
            catch (InvocationTargetException var1_3) {
                throw new RuntimeException(var1_3);
            }
        }

        @Override
        public void untagSocket(Socket socket) throws SocketException {
            if (this.trafficStatsUntagSocket == null) {
                return;
            }
            try {
                this.trafficStatsUntagSocket.invoke(null, socket);
                return;
            }
            catch (IllegalAccessException var1_2) {
                throw new RuntimeException(var1_2);
            }
            catch (InvocationTargetException var1_3) {
                throw new RuntimeException(var1_3);
            }
        }
    }

    private static class JdkWithJettyBootPlatform
    extends Platform {
        private final Class<?> clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Class<?> serverProviderClass;

        public JdkWithJettyBootPlatform(Method method, Method method2, Class<?> class_, Class<?> class_2) {
            this.putMethod = method;
            this.getMethod = method2;
            this.clientProviderClass = class_;
            this.serverProviderClass = class_2;
        }

        @Override
        public String getSelectedProtocol(SSLSocket object) {
            block5 : {
                object = (JettyNegoProvider)Proxy.getInvocationHandler(this.getMethod.invoke(null, object));
                if (((JettyNegoProvider)object).unsupported || ((JettyNegoProvider)object).selected != null) break block5;
                Logger.getLogger("com.squareup.okhttp.OkHttpClient").log(Level.INFO, "NPN/ALPN callback dropped: SPDY and HTTP/2 are disabled. Is npn-boot or alpn-boot on the boot class path?");
                return null;
            }
            try {
                if (!((JettyNegoProvider)object).unsupported) {
                    object = ((JettyNegoProvider)object).selected;
                    return object;
                }
            }
            catch (InvocationTargetException var1_2) {
                throw new AssertionError();
            }
            catch (IllegalAccessException var1_3) {
                throw new AssertionError();
            }
            return null;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void setProtocols(SSLSocket var1_1, List<Protocol> var2_4) {
            block7 : {
                try {
                    var5_5 = new ArrayList<String>(var2_4.size());
                    var3_6 = 0;
                    var4_7 = var2_4.size();
lbl5: // 2 sources:
                    if (var3_6 < var4_7) {
                        var6_8 = (Protocol)var2_4.get(var3_6);
                        if (var6_8 != Protocol.HTTP_1_0) {
                            var5_5.add((String)var6_8.toString());
                        }
                        break block7;
                    }
                    var2_4 = Platform.class.getClassLoader();
                    var6_8 = this.clientProviderClass;
                    var7_9 = this.serverProviderClass;
                    var5_5 = new JettyNegoProvider((List<String>)var5_5);
                    var2_4 = Proxy.newProxyInstance((ClassLoader)var2_4, new Class[]{var6_8, var7_9}, (InvocationHandler)var5_5);
                    this.putMethod.invoke(null, new Object[]{var1_1, var2_4});
                    return;
                }
                catch (InvocationTargetException var1_2) {
                    throw new AssertionError(var1_2);
                }
                catch (IllegalAccessException var1_3) {
                    throw new AssertionError(var1_3);
                }
            }
            ++var3_6;
            ** GOTO lbl5
        }
    }

    private static class JettyNegoProvider
    implements InvocationHandler {
        private final List<String> protocols;
        private String selected;
        private boolean unsupported;

        public JettyNegoProvider(List<String> list) {
            this.protocols = list;
        }

        @Override
        public Object invoke(Object object, Method method, Object[] arrobject) throws Throwable {
            String string2 = method.getName();
            Class class_ = method.getReturnType();
            object = arrobject;
            if (arrobject == null) {
                object = Util.EMPTY_STRING_ARRAY;
            }
            if (string2.equals("supports") && Boolean.TYPE == class_) {
                return true;
            }
            if (string2.equals("unsupported") && Void.TYPE == class_) {
                this.unsupported = true;
                return null;
            }
            if (string2.equals("protocols") && object.length == 0) {
                return this.protocols;
            }
            if ((string2.equals("selectProtocol") || string2.equals("select")) && String.class == class_ && object.length == 1 && object[0] instanceof List) {
                object = (List)object[0];
                int n2 = object.size();
                for (int i2 = 0; i2 < n2; ++i2) {
                    if (!this.protocols.contains(object.get(i2))) continue;
                    this.selected = object = (String)object.get(i2);
                    return object;
                }
                this.selected = object = this.protocols.get(0);
                return object;
            }
            if ((string2.equals("protocolSelected") || string2.equals("selected")) && object.length == 1) {
                this.selected = (String)object[0];
                return null;
            }
            return method.invoke(this, (Object[])object);
        }
    }

}


/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import oauth.signpost.http.HttpParameters;

public class OAuth {
    public static final String ENCODING = "UTF-8";
    public static final String FORM_ENCODED = "application/x-www-form-urlencoded";
    public static final String HTTP_AUTHORIZATION_HEADER = "Authorization";
    public static final String OAUTH_CALLBACK = "oauth_callback";
    public static final String OAUTH_CALLBACK_CONFIRMED = "oauth_callback_confirmed";
    public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String OAUTH_NONCE = "oauth_nonce";
    public static final String OAUTH_SIGNATURE = "oauth_signature";
    public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";
    public static final String OAUTH_VERIFIER = "oauth_verifier";
    public static final String OAUTH_VERSION = "oauth_version";
    public static final String OUT_OF_BAND = "oob";
    public static final String VERSION_1_0 = "1.0";
    private static final PercentEscaper percentEncoder = new PercentEscaper("-._~", false);

    public static String addQueryParameters(String string2, Map<String, String> map) {
        String[] arrstring = new String[map.size() * 2];
        int n2 = 0;
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String string3;
            arrstring[n2] = string3 = iterator.next();
            arrstring[n2 + 1] = map.get(string3);
            n2 += 2;
        }
        return OAuth.addQueryParameters(string2, arrstring);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static /* varargs */ String addQueryParameters(String string2, String ... arrstring) {
        void var1_2;
        String string3 = string2.contains("?") ? "&" : "?";
        StringBuilder stringBuilder = new StringBuilder(string2 + string3);
        int n2 = 0;
        while (n2 < var1_2.length) {
            if (n2 > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(OAuth.percentEncode((String)var1_2[n2]) + "=" + OAuth.percentEncode((String)var1_2[n2 + 1]));
            n2 += 2;
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String addQueryString(String string2, String string3) {
        void var1_2;
        String string4 = string2.contains("?") ? "&" : "?";
        StringBuilder stringBuilder = new StringBuilder(string2 + string4);
        stringBuilder.append((String)var1_2);
        return stringBuilder.toString();
    }

    public static void debugOut(String string2, String string3) {
        if (System.getProperty("debug") != null) {
            System.out.println("[SIGNPOST] " + string2 + ": " + string3);
        }
    }

    public static HttpParameters decodeForm(InputStream object) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)object));
        StringBuilder stringBuilder = new StringBuilder();
        object = bufferedReader.readLine();
        while (object != null) {
            stringBuilder.append((String)object);
            object = bufferedReader.readLine();
        }
        return OAuth.decodeForm(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static HttpParameters decodeForm(String string2) {
        HttpParameters httpParameters = new HttpParameters();
        if (OAuth.isEmpty(string2)) {
            return httpParameters;
        }
        String[] arrstring = string2.split("\\&");
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            String string3 = arrstring[n3];
            int n4 = string3.indexOf(61);
            if (n4 < 0) {
                string2 = OAuth.percentDecode(string3);
                string3 = null;
            } else {
                string2 = OAuth.percentDecode(string3.substring(0, n4));
                string3 = OAuth.percentDecode(string3.substring(n4 + 1));
            }
            httpParameters.put(string2, string3);
            ++n3;
        }
        return httpParameters;
    }

    public static <T extends Map.Entry<String, String>> String formEncode(Collection<T> collection) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OAuth.formEncode(collection, byteArrayOutputStream);
        return new String(byteArrayOutputStream.toByteArray());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T extends Map.Entry<String, String>> void formEncode(Collection<T> iterator, OutputStream outputStream) throws IOException {
        if (iterator != null) {
            boolean bl2 = true;
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                if (bl2) {
                    bl2 = false;
                } else {
                    outputStream.write(38);
                }
                outputStream.write(OAuth.percentEncode(OAuth.safeToString(entry.getKey())).getBytes());
                outputStream.write(61);
                outputStream.write(OAuth.percentEncode(OAuth.safeToString(entry.getValue())).getBytes());
            }
        }
    }

    public static boolean isEmpty(String string2) {
        if (string2 == null || string2.length() == 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static HttpParameters oauthHeaderToParamsMap(String arrstring) {
        HttpParameters httpParameters = new HttpParameters();
        if (arrstring == null || !arrstring.startsWith("OAuth ")) {
            return httpParameters;
        }
        arrstring = arrstring.substring("OAuth ".length()).split(",");
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            String[] arrstring2 = arrstring[n3].split("=");
            httpParameters.put(arrstring2[0].trim(), arrstring2[1].replace("\"", "").trim());
            ++n3;
        }
        return httpParameters;
    }

    public static String percentDecode(String string2) {
        if (string2 == null) {
            return "";
        }
        try {
            string2 = URLDecoder.decode(string2, "UTF-8");
            return string2;
        }
        catch (UnsupportedEncodingException var0_1) {
            throw new RuntimeException(var0_1.getMessage(), var0_1);
        }
    }

    public static String percentEncode(String string2) {
        if (string2 == null) {
            return "";
        }
        return percentEncoder.escape(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static /* varargs */ String prepareOAuthHeader(String ... arrstring) {
        StringBuilder stringBuilder = new StringBuilder("OAuth ");
        int n2 = 0;
        while (n2 < arrstring.length) {
            if (n2 > 0) {
                stringBuilder.append(", ");
            }
            boolean bl2 = arrstring[n2].startsWith("oauth_") || arrstring[n2].startsWith("x_oauth_");
            String string2 = bl2 ? OAuth.percentEncode(arrstring[n2 + 1]) : arrstring[n2 + 1];
            stringBuilder.append(OAuth.percentEncode(arrstring[n2]) + "=\"" + string2 + "\"");
            n2 += 2;
        }
        return stringBuilder.toString();
    }

    public static final String safeToString(Object object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    public static String toHeaderElement(String string2, String string3) {
        return OAuth.percentEncode(string2) + "=\"" + OAuth.percentEncode(string3) + "\"";
    }

    public static <T extends Map.Entry<String, String>> Map<String, String> toMap(Collection<T> object) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                String string2 = (String)entry.getKey();
                if (hashMap.containsKey(string2)) continue;
                hashMap.put(string2, (String)entry.getValue());
            }
        }
        return hashMap;
    }
}


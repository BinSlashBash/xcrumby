/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.signature;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import org.apache.commons.codec.binary.Base64;

public abstract class OAuthMessageSigner
implements Serializable {
    private static final long serialVersionUID = 4445779788786131202L;
    private transient Base64 base64 = new Base64();
    private String consumerSecret;
    private String tokenSecret;

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.base64 = new Base64();
    }

    protected String base64Encode(byte[] arrby) {
        return new String(this.base64.encode(arrby));
    }

    protected byte[] decodeBase64(String string2) {
        return this.base64.decode(string2.getBytes());
    }

    public String getConsumerSecret() {
        return this.consumerSecret;
    }

    public abstract String getSignatureMethod();

    public String getTokenSecret() {
        return this.tokenSecret;
    }

    public void setConsumerSecret(String string2) {
        this.consumerSecret = string2;
    }

    public void setTokenSecret(String string2) {
        this.tokenSecret = string2;
    }

    public abstract String sign(HttpRequest var1, HttpParameters var2) throws OAuthMessageSignerException;
}


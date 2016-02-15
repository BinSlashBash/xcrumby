package oauth.signpost.signature;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import org.apache.commons.codec.binary.Base64;

public abstract class OAuthMessageSigner implements Serializable {
    private static final long serialVersionUID = 4445779788786131202L;
    private transient Base64 base64;
    private String consumerSecret;
    private String tokenSecret;

    public abstract String getSignatureMethod();

    public abstract String sign(HttpRequest httpRequest, HttpParameters httpParameters) throws OAuthMessageSignerException;

    public OAuthMessageSigner() {
        this.base64 = new Base64();
    }

    public String getConsumerSecret() {
        return this.consumerSecret;
    }

    public String getTokenSecret() {
        return this.tokenSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    protected byte[] decodeBase64(String s) {
        return this.base64.decode(s.getBytes());
    }

    protected String base64Encode(byte[] b) {
        return new String(this.base64.encode(b));
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.base64 = new Base64();
    }
}

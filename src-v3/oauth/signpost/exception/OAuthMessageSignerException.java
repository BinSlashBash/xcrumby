package oauth.signpost.exception;

public class OAuthMessageSignerException extends OAuthException {
    public OAuthMessageSignerException(String message) {
        super(message);
    }

    public OAuthMessageSignerException(Exception cause) {
        super((Throwable) cause);
    }
}

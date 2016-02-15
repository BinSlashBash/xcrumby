package oauth.signpost.exception;

public class OAuthCommunicationException extends OAuthException {
    private String responseBody;

    public OAuthCommunicationException(Exception cause) {
        super("Communication with the service provider failed: " + cause.getLocalizedMessage(), cause);
    }

    public OAuthCommunicationException(String message, String responseBody) {
        super(message);
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        return this.responseBody;
    }
}

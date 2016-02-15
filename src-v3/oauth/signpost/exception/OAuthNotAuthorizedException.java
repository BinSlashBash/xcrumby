package oauth.signpost.exception;

public class OAuthNotAuthorizedException extends OAuthException {
    private static final String ERROR = "Authorization failed (server replied with a 401). This can happen if the consumer key was not correct or the signatures did not match.";
    private String responseBody;

    public OAuthNotAuthorizedException() {
        super(ERROR);
    }

    public OAuthNotAuthorizedException(String responseBody) {
        super(ERROR);
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        return this.responseBody;
    }
}

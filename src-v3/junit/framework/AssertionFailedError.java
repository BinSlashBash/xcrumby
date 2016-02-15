package junit.framework;

import com.crumby.impl.crumby.UnsupportedUrlFragment;

public class AssertionFailedError extends AssertionError {
    private static final long serialVersionUID = 1;

    public AssertionFailedError(String message) {
        super(defaultString(message));
    }

    private static String defaultString(String message) {
        return message == null ? UnsupportedUrlFragment.DISPLAY_NAME : message;
    }
}

package org.hamcrest.text;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsNull;

public final class IsEmptyString extends BaseMatcher<String> {
    private static final IsEmptyString INSTANCE;
    private static final Matcher<String> NULL_OR_EMPTY_INSTANCE;

    static {
        INSTANCE = new IsEmptyString();
        NULL_OR_EMPTY_INSTANCE = AnyOf.anyOf(IsNull.nullValue(), INSTANCE);
    }

    public boolean matches(Object item) {
        return item != null && (item instanceof String) && ((String) item).equals(UnsupportedUrlFragment.DISPLAY_NAME);
    }

    public void describeTo(Description description) {
        description.appendText("an empty string");
    }

    @Factory
    public static Matcher<String> isEmptyString() {
        return INSTANCE;
    }

    @Factory
    public static Matcher<String> isEmptyOrNullString() {
        return NULL_OR_EMPTY_INSTANCE;
    }
}

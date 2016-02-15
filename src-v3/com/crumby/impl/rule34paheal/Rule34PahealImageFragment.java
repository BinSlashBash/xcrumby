package com.crumby.impl.rule34paheal;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class Rule34PahealImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    /* renamed from: com.crumby.impl.rule34paheal.Rule34PahealImageFragment.1 */
    class C14671 extends UniversalImageProducer {
        C14671() {
        }

        protected String getScriptName() {
            return Rule34PahealImageFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return Rule34PahealFragment.BASE_URL;
        }

        protected String getRegexForMatchingId() {
            return Rule34PahealImageFragment.REGEX_URL;
        }
    }

    static {
        REGEX_URL = Rule34PahealFragment.REGEX_BASE + "/post/view/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = Rule34PahealFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C14671();
    }

    protected String getTagUrl(String tag) {
        return "http://rule34.paheal.net?tags=" + Uri.encode(tag.replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "_"));
    }
}

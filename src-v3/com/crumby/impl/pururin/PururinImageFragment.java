package com.crumby.impl.pururin;

import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;

public class PururinImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    /* renamed from: com.crumby.impl.pururin.PururinImageFragment.1 */
    class C14661 extends UniversalImageProducer {
        C14661() {
        }

        protected String getScriptName() {
            return PururinImageFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return PururinFragment.BASE_URL;
        }

        protected String getRegexForMatchingId() {
            return PururinImageFragment.REGEX_URL;
        }
    }

    static {
        REGEX_URL = PururinFragment.REGEX_BASE + "/view/([0-9]+).*/" + CAPTURE_NUMERIC_REPEATING + DeviceFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = PururinThumbsFragment.class;
    }

    protected String getTagUrl(String tag) {
        return null;
    }

    protected GalleryProducer createProducer() {
        return new C14661();
    }
}

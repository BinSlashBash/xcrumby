package android.support.v4.text;

import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import java.util.Locale;

public class TextUtilsCompat {
    private static String ARAB_SCRIPT_SUBTAG;
    private static String HEBR_SCRIPT_SUBTAG;
    public static final Locale ROOT;

    public static String htmlEncode(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
                    sb.append("&quot;");
                    break;
                case C0065R.styleable.TwoWayView_android_minWidth /*38*/:
                    sb.append("&amp;");
                    break;
                case C0065R.styleable.TwoWayView_android_minHeight /*39*/:
                    sb.append("&#39;");
                    break;
                case C0065R.styleable.TwoWayView_android_rotationY /*60*/:
                    sb.append("&lt;");
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusForward /*62*/:
                    sb.append("&gt;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    public static int getLayoutDirectionFromLocale(Locale locale) {
        if (!(locale == null || locale.equals(ROOT))) {
            String scriptSubtag = ICUCompat.getScript(ICUCompat.addLikelySubtags(locale.toString()));
            if (scriptSubtag == null) {
                return getLayoutDirectionFromFirstChar(locale);
            }
            if (scriptSubtag.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG) || scriptSubtag.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)) {
                return 1;
            }
        }
        return 0;
    }

    private static int getLayoutDirectionFromFirstChar(Locale locale) {
        switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
            case Std.STD_FILE /*1*/:
            case Std.STD_URL /*2*/:
                return 1;
            default:
                return 0;
        }
    }

    static {
        ROOT = new Locale(UnsupportedUrlFragment.DISPLAY_NAME, UnsupportedUrlFragment.DISPLAY_NAME);
        ARAB_SCRIPT_SUBTAG = "Arab";
        HEBR_SCRIPT_SUBTAG = "Hebr";
    }
}

/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.style.LeadingMarginSpan
 *  android.text.style.LeadingMarginSpan$Standard
 *  android.text.style.QuoteSpan
 *  android.text.style.RelativeSizeSpan
 *  android.text.style.StyleSpan
 *  android.text.style.TypefaceSpan
 *  android.text.style.URLSpan
 */
package in.uncod.android.bypass;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import in.uncod.android.bypass.Document;
import in.uncod.android.bypass.Element;

public class Bypass {
    private static final float[] HEADER_SIZES;

    static {
        System.loadLibrary("bypass");
        HEADER_SIZES = new float[]{1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1.0f};
    }

    private native Document processMarkdown(String var1);

    /*
     * Enabled aggressive block sorting
     */
    private CharSequence recurseElement(Element element) {
        int n2;
        String string2;
        Object object = new CharSequence[element.size()];
        for (n2 = 0; n2 < element.size(); ++n2) {
            object[n2] = this.recurseElement(element.children[n2]);
        }
        CharSequence charSequence = TextUtils.concat((CharSequence[])object);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        object = string2 = element.getText();
        if (element.size() == 0) {
            object = string2;
            if (element.getParent().getType() != Element.Type.BLOCK_CODE) {
                object = string2.replace('\n', ' ');
            }
        }
        if (element.getParent() != null && element.getParent().getType() == Element.Type.LIST_ITEM && element.getType() == Element.Type.LIST) {
            spannableStringBuilder.append((CharSequence)"\n");
        }
        if (element.getType() == Element.Type.LIST_ITEM) {
            spannableStringBuilder.append((CharSequence)"\u2022");
        }
        spannableStringBuilder.append((CharSequence)object);
        spannableStringBuilder.append(charSequence);
        if (element.getType() != Element.Type.LIST || element.getParent() == null) {
            if (element.getType() == Element.Type.LIST_ITEM) {
                if (element.size() <= 0 || !element.children[element.size() - 1].isBlockElement()) {
                    spannableStringBuilder.append((CharSequence)"\n");
                }
            } else if (element.isBlockElement()) {
                spannableStringBuilder.append((CharSequence)"\n\n");
            }
        }
        if (element.getType() == Element.Type.HEADER) {
            n2 = Integer.parseInt(element.getAttribute("level"));
            spannableStringBuilder.setSpan((Object)new RelativeSizeSpan(HEADER_SIZES[n2]), 0, spannableStringBuilder.length(), 33);
            spannableStringBuilder.setSpan((Object)new StyleSpan(1), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        } else {
            if (element.getType() == Element.Type.LIST_ITEM && element.getParent().getParent() != null) {
                spannableStringBuilder.setSpan((Object)new LeadingMarginSpan.Standard(20), 0, spannableStringBuilder.length(), 33);
                return spannableStringBuilder;
            }
            if (element.getType() == Element.Type.EMPHASIS) {
                spannableStringBuilder.setSpan((Object)new StyleSpan(2), 0, spannableStringBuilder.length(), 33);
                return spannableStringBuilder;
            }
            if (element.getType() == Element.Type.DOUBLE_EMPHASIS) {
                spannableStringBuilder.setSpan((Object)new StyleSpan(1), 0, spannableStringBuilder.length(), 33);
                return spannableStringBuilder;
            }
            if (element.getType() == Element.Type.TRIPLE_EMPHASIS) {
                spannableStringBuilder.setSpan((Object)new StyleSpan(3), 0, spannableStringBuilder.length(), 33);
                return spannableStringBuilder;
            }
            if (element.getType() == Element.Type.CODE_SPAN) {
                spannableStringBuilder.setSpan((Object)new TypefaceSpan("monospace"), 0, spannableStringBuilder.length(), 33);
                return spannableStringBuilder;
            }
            if (element.getType() == Element.Type.LINK) {
                spannableStringBuilder.setSpan((Object)new URLSpan(element.getAttribute("link")), 0, spannableStringBuilder.length(), 33);
                return spannableStringBuilder;
            }
            if (element.getType() != Element.Type.BLOCK_QUOTE) return spannableStringBuilder;
            {
                spannableStringBuilder.setSpan((Object)new QuoteSpan(), 0, spannableStringBuilder.length(), 33);
                spannableStringBuilder.setSpan((Object)new StyleSpan(2), 0, spannableStringBuilder.length(), 33);
                return spannableStringBuilder;
            }
        }
    }

    public CharSequence markdownToSpannable(String object) {
        object = this.processMarkdown((String)object);
        CharSequence[] arrcharSequence = new CharSequence[object.getElementCount()];
        for (int i2 = 0; i2 < object.getElementCount(); ++i2) {
            arrcharSequence[i2] = this.recurseElement(object.getElement(i2));
        }
        return TextUtils.concat((CharSequence[])arrcharSequence);
    }
}


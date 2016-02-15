/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.Spannable
 *  android.text.SpannableString
 *  android.text.Spanned
 *  android.text.style.CharacterStyle
 */
package com.crumby.lib;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;

public class RichTextUtils {
    public static <A extends CharacterStyle, B extends CharacterStyle> Spannable replaceAll(Spanned spanned, Class<A> arrcharacterStyle, SpanConverter<A, B> spanConverter) {
        spanned = new SpannableString((CharSequence)spanned);
        for (CharacterStyle characterStyle : (CharacterStyle[])spanned.getSpans(0, spanned.length(), arrcharacterStyle)) {
            int n2 = spanned.getSpanStart((Object)characterStyle);
            int n3 = spanned.getSpanEnd((Object)characterStyle);
            int n4 = spanned.getSpanFlags((Object)characterStyle);
            spanned.removeSpan((Object)characterStyle);
            spanned.setSpan(spanConverter.convert(characterStyle), n2, n3, n4);
        }
        return spanned;
    }

    public static interface SpanConverter<A extends CharacterStyle, B extends CharacterStyle> {
        public B convert(A var1);
    }

}


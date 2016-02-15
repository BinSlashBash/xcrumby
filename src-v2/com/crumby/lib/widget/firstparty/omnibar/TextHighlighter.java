/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.widget.firstparty.omnibar;

import com.crumby.lib.widget.firstparty.omnibar.HighlightText;

public class TextHighlighter {
    public static void highlight(HighlightText highlightText, String arrstring, String string2) {
        arrstring = arrstring.trim().split(" ");
        int n2 = 0;
        string2 = string2.toLowerCase();
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            arrstring[i2] = arrstring[i2].toLowerCase();
            n2 = string2.indexOf(arrstring[i2], n2);
            highlightText.set(n2, arrstring[i2].length() + n2);
            n2 += arrstring[i2].length();
        }
    }
}


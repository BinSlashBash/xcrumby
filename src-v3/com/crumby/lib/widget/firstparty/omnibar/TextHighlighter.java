package com.crumby.lib.widget.firstparty.omnibar;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class TextHighlighter {
    public static void highlight(HighlightText sb, String searchText, String toBeHighlighted) {
        String[] searches = searchText.trim().split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        int lastIndex = 0;
        toBeHighlighted = toBeHighlighted.toLowerCase();
        for (int i = 0; i < searches.length; i++) {
            searches[i] = searches[i].toLowerCase();
            int index = toBeHighlighted.indexOf(searches[i], lastIndex);
            sb.set(index, searches[i].length() + index);
            lastIndex = index + searches[i].length();
        }
    }
}

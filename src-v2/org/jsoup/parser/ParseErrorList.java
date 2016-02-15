/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import java.util.ArrayList;
import org.jsoup.parser.ParseError;

class ParseErrorList
extends ArrayList<ParseError> {
    private static final int INITIAL_CAPACITY = 16;
    private final int maxSize;

    ParseErrorList(int n2, int n3) {
        super(n2);
        this.maxSize = n3;
    }

    static ParseErrorList noTracking() {
        return new ParseErrorList(0, 0);
    }

    static ParseErrorList tracking(int n2) {
        return new ParseErrorList(16, n2);
    }

    boolean canAddError() {
        if (this.size() < this.maxSize) {
            return true;
        }
        return false;
    }

    int getMaxSize() {
        return this.maxSize;
    }
}


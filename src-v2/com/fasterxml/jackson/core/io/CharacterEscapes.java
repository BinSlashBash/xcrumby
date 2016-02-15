/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import java.io.Serializable;
import java.util.Arrays;

public abstract class CharacterEscapes
implements Serializable {
    public static final int ESCAPE_CUSTOM = -2;
    public static final int ESCAPE_NONE = 0;
    public static final int ESCAPE_STANDARD = -1;

    public static int[] standardAsciiEscapesForJSON() {
        int[] arrn = CharTypes.get7BitOutputEscapes();
        return Arrays.copyOf(arrn, arrn.length);
    }

    public abstract int[] getEscapeCodesForAscii();

    public abstract SerializableString getEscapeSequence(int var1);
}


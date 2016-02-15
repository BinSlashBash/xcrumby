/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.danbooru;

import com.crumby.lib.ExtraAttributes;

public class DanbooruAttributes
extends ExtraAttributes {
    private final String[] artistTags;
    private final String[] characterTags;
    private final String[] copyrightTags;

    public DanbooruAttributes(String[] arrstring, String[] arrstring2, String[] arrstring3) {
        this.characterTags = arrstring;
        this.artistTags = arrstring2;
        this.copyrightTags = arrstring3;
    }

    public String[] getArtistTags() {
        return this.artistTags;
    }

    public String[] getCharacterTags() {
        return this.characterTags;
    }

    public String[] getCopyrightTags() {
        return this.copyrightTags;
    }
}


package com.crumby.impl.danbooru;

import com.crumby.lib.ExtraAttributes;

public class DanbooruAttributes extends ExtraAttributes {
    private final String[] artistTags;
    private final String[] characterTags;
    private final String[] copyrightTags;

    public DanbooruAttributes(String[] characterTags, String[] artistTag, String[] copyrightTags) {
        this.characterTags = characterTags;
        this.artistTags = artistTag;
        this.copyrightTags = copyrightTags;
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

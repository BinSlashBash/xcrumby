/*
 * Decompiled with CFR 0_110.
 */
package it.gmariotti.changelibs.library;

import it.gmariotti.changelibs.R;

public class Constants {
    public static final int mChangeLogFileResourceId = R.raw.changelog;
    public static final int mRowHeaderLayoutId;
    public static final int mRowLayoutId;
    public static final int mStringVersionHeader;

    static {
        mRowLayoutId = R.layout.changelogrow_layout;
        mRowHeaderLayoutId = R.layout.changelogrowheader_layout;
        mStringVersionHeader = R.string.changelog_header_version;
    }
}


package it.gmariotti.changelibs.library;

import it.gmariotti.changelibs.C0659R;

public class Constants {
    public static final int mChangeLogFileResourceId;
    public static final int mRowHeaderLayoutId;
    public static final int mRowLayoutId;
    public static final int mStringVersionHeader;

    static {
        mChangeLogFileResourceId = C0659R.raw.changelog;
        mRowLayoutId = C0659R.layout.changelogrow_layout;
        mRowHeaderLayoutId = C0659R.layout.changelogrowheader_layout;
        mStringVersionHeader = C0659R.string.changelog_header_version;
    }
}

/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package it.gmariotti.changelibs.library.internal;

import android.content.Context;
import android.content.res.Resources;
import it.gmariotti.changelibs.R;

public class ChangeLogRow {
    public static final int BUGFIX = 1;
    public static final int DEFAULT = 0;
    public static final int IMPROVEMENT = 2;
    private boolean bulletedList;
    protected String changeDate;
    private String changeText;
    private String changeTextTitle;
    protected boolean header;
    private int type;
    protected int versionCode;
    protected String versionName;

    public String getChangeDate() {
        return this.changeDate;
    }

    public String getChangeText() {
        return this.changeText;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getChangeText(Context object) {
        if (object == null) {
            return this.getChangeText();
        }
        String string2 = "";
        switch (this.type) {
            default: {
                object = string2;
                do {
                    return (String)object + " " + this.changeText;
                    break;
                } while (true);
            }
            case 1: {
                object = object.getResources().getString(R.string.changelog_row_prefix_bug).replaceAll("\\[", "<").replaceAll("\\]", ">");
                return (String)object + " " + this.changeText;
            }
            case 2: 
        }
        object = object.getResources().getString(R.string.changelog_row_prefix_improvement).replaceAll("\\[", "<").replaceAll("\\]", ">");
        return (String)object + " " + this.changeText;
    }

    public String getChangeTextTitle() {
        return this.changeTextTitle;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public boolean isBulletedList() {
        return this.bulletedList;
    }

    public boolean isHeader() {
        return this.header;
    }

    public void parseChangeText(String string2) {
        String string3 = string2;
        if (string2 != null) {
            string3 = string2.replaceAll("\\[", "<").replaceAll("\\]", ">");
        }
        this.setChangeText(string3);
    }

    public void setBulletedList(boolean bl2) {
        this.bulletedList = bl2;
    }

    public void setChangeDate(String string2) {
        this.changeDate = string2;
    }

    public void setChangeText(String string2) {
        this.changeText = string2;
    }

    public void setChangeTextTitle(String string2) {
        this.changeTextTitle = string2;
    }

    public void setHeader(boolean bl2) {
        this.header = bl2;
    }

    public void setType(int n2) {
        this.type = n2;
    }

    public void setVersionCode(int n2) {
        this.versionCode = n2;
    }

    public void setVersionName(String string2) {
        this.versionName = string2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("header=" + this.header);
        stringBuilder.append(",");
        stringBuilder.append("versionName=" + this.versionName);
        stringBuilder.append(",");
        stringBuilder.append("versionCode=" + this.versionCode);
        stringBuilder.append(",");
        stringBuilder.append("bulletedList=" + this.bulletedList);
        stringBuilder.append(",");
        stringBuilder.append("changeText=" + this.changeText);
        return stringBuilder.toString();
    }
}


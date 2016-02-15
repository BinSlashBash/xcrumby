/*
 * Decompiled with CFR 0_110.
 */
package it.gmariotti.changelibs.library.internal;

import it.gmariotti.changelibs.library.internal.ChangeLogRow;

public class ChangeLogRowHeader
extends ChangeLogRow {
    public ChangeLogRowHeader() {
        this.setHeader(true);
        this.setBulletedList(false);
        this.setChangeTextTitle(null);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("header=" + this.header);
        stringBuilder.append(",");
        stringBuilder.append("versionName=" + this.versionName);
        stringBuilder.append(",");
        stringBuilder.append("changeDate=" + this.changeDate);
        return stringBuilder.toString();
    }
}


/*
 * Decompiled with CFR 0_110.
 */
package it.gmariotti.changelibs.library.internal;

import it.gmariotti.changelibs.library.internal.ChangeLogRow;
import java.util.LinkedList;

public class ChangeLog {
    private boolean bulletedList;
    private LinkedList<ChangeLogRow> rows = new LinkedList();

    public void addRow(ChangeLogRow changeLogRow) {
        if (changeLogRow != null) {
            if (this.rows == null) {
                this.rows = new LinkedList();
            }
            this.rows.add(changeLogRow);
        }
    }

    public void clearAllRows() {
        this.rows = new LinkedList();
    }

    public LinkedList<ChangeLogRow> getRows() {
        return this.rows;
    }

    public boolean isBulletedList() {
        return this.bulletedList;
    }

    public void setBulletedList(boolean bl2) {
        this.bulletedList = bl2;
    }

    public void setRows(LinkedList<ChangeLogRow> linkedList) {
        this.rows = linkedList;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bulletedList=" + this.bulletedList);
        stringBuilder.append("\n");
        if (this.rows != null) {
            for (ChangeLogRow changeLogRow : this.rows) {
                stringBuilder.append("row=[");
                stringBuilder.append(changeLogRow.toString());
                stringBuilder.append("]\n");
            }
        } else {
            stringBuilder.append("rows:none");
        }
        return stringBuilder.toString();
    }
}


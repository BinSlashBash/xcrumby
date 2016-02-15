/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import java.io.Serializable;

public class Version
implements Comparable<Version>,
Serializable {
    private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, null, null, null);
    private static final long serialVersionUID = 1;
    protected final String _artifactId;
    protected final String _groupId;
    protected final int _majorVersion;
    protected final int _minorVersion;
    protected final int _patchLevel;
    protected final String _snapshotInfo;

    @Deprecated
    public Version(int n2, int n3, int n4, String string2) {
        this(n2, n3, n4, string2, null, null);
    }

    public Version(int n2, int n3, int n4, String string2, String string3, String string4) {
        this._majorVersion = n2;
        this._minorVersion = n3;
        this._patchLevel = n4;
        this._snapshotInfo = string2;
        string2 = string3;
        if (string3 == null) {
            string2 = "";
        }
        this._groupId = string2;
        string2 = string4;
        if (string4 == null) {
            string2 = "";
        }
        this._artifactId = string2;
    }

    public static Version unknownVersion() {
        return UNKNOWN_VERSION;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int compareTo(Version version) {
        int n2;
        if (version == this) {
            return 0;
        }
        int n3 = n2 = this._groupId.compareTo(version._groupId);
        if (n2 != 0) return n3;
        n3 = n2 = this._artifactId.compareTo(version._artifactId);
        if (n2 != 0) return n3;
        n3 = n2 = this._majorVersion - version._majorVersion;
        if (n2 != 0) return n3;
        n3 = n2 = this._minorVersion - version._minorVersion;
        if (n2 != 0) return n3;
        return this._patchLevel - version._patchLevel;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        object = (Version)object;
        if (object._majorVersion != this._majorVersion) return false;
        if (object._minorVersion != this._minorVersion) return false;
        if (object._patchLevel != this._patchLevel) return false;
        if (!object._artifactId.equals(this._artifactId)) return false;
        if (object._groupId.equals(this._groupId)) return true;
        return false;
    }

    public String getArtifactId() {
        return this._artifactId;
    }

    public String getGroupId() {
        return this._groupId;
    }

    public int getMajorVersion() {
        return this._majorVersion;
    }

    public int getMinorVersion() {
        return this._minorVersion;
    }

    public int getPatchLevel() {
        return this._patchLevel;
    }

    public int hashCode() {
        return this._artifactId.hashCode() ^ this._groupId.hashCode() + this._majorVersion - this._minorVersion + this._patchLevel;
    }

    public boolean isSnapshot() {
        if (this._snapshotInfo != null && this._snapshotInfo.length() > 0) {
            return true;
        }
        return false;
    }

    public boolean isUknownVersion() {
        if (this == UNKNOWN_VERSION) {
            return true;
        }
        return false;
    }

    public String toFullString() {
        return this._groupId + '/' + this._artifactId + '/' + this.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._majorVersion).append('.');
        stringBuilder.append(this._minorVersion).append('.');
        stringBuilder.append(this._patchLevel);
        if (this.isSnapshot()) {
            stringBuilder.append('-').append(this._snapshotInfo);
        }
        return stringBuilder.toString();
    }
}


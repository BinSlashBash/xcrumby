package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Contents implements SafeParcelable {
    public static final Creator<Contents> CREATOR;
    final ParcelFileDescriptor Cj;
    final int Eu;
    final int Ev;
    final DriveId Ew;
    private boolean Ex;
    private boolean Ey;
    private boolean mClosed;
    final int xH;

    static {
        CREATOR = new C0265a();
    }

    Contents(int versionCode, ParcelFileDescriptor parcelFileDescriptor, int requestId, int mode, DriveId driveId) {
        this.mClosed = false;
        this.Ex = false;
        this.Ey = false;
        this.xH = versionCode;
        this.Cj = parcelFileDescriptor;
        this.Eu = requestId;
        this.Ev = mode;
        this.Ew = driveId;
    }

    public void close() {
        this.mClosed = true;
    }

    public int describeContents() {
        return 0;
    }

    public int fA() {
        return this.Eu;
    }

    public DriveId getDriveId() {
        return this.Ew;
    }

    public InputStream getInputStream() {
        if (this.mClosed) {
            throw new IllegalStateException("Contents have been closed, cannot access the input stream.");
        } else if (this.Ev != DriveFile.MODE_READ_ONLY) {
            throw new IllegalStateException("getInputStream() can only be used with contents opened with MODE_READ_ONLY.");
        } else if (this.Ex) {
            throw new IllegalStateException("getInputStream() can only be called once per Contents instance.");
        } else {
            this.Ex = true;
            return new FileInputStream(this.Cj.getFileDescriptor());
        }
    }

    public int getMode() {
        return this.Ev;
    }

    public OutputStream getOutputStream() {
        if (this.mClosed) {
            throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
        } else if (this.Ev != DriveFile.MODE_WRITE_ONLY) {
            throw new IllegalStateException("getOutputStream() can only be used with contents opened with MODE_WRITE_ONLY.");
        } else if (this.Ey) {
            throw new IllegalStateException("getOutputStream() can only be called once per Contents instance.");
        } else {
            this.Ey = true;
            return new FileOutputStream(this.Cj.getFileDescriptor());
        }
    }

    public ParcelFileDescriptor getParcelFileDescriptor() {
        if (!this.mClosed) {
            return this.Cj;
        }
        throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0265a.m238a(this, dest, flags);
    }
}

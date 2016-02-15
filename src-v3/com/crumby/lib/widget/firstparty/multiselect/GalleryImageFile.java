package com.crumby.lib.widget.firstparty.multiselect;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import com.crumby.CrDb;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.GalleryImage;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import java.io.FileOutputStream;

public class GalleryImageFile implements Target {
    private final String dir;
    private final String name;

    public GalleryImageFile(String dir, String name) {
        this.dir = dir;
        this.name = name;
    }

    public GalleryImageFile(GalleryImage image) {
        this.dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + DeviceFragment.REGEX_BASE;
        String name = image.getImageUrl();
        this.name = name.substring(name.lastIndexOf(47) + 1);
    }

    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
        Exception e;
        Throwable th;
        FileOutputStream out = null;
        try {
            CrDb.m0d("gallery image file", "saving: " + this.dir + this.name);
            FileOutputStream out2 = new FileOutputStream(this.dir + this.name);
            try {
                bitmap.compress(CompressFormat.PNG, 100, out2);
                if (out2 != null) {
                    try {
                        out2.close();
                    } catch (Throwable th2) {
                        out = out2;
                    }
                }
                out = out2;
            } catch (Exception e2) {
                e = e2;
                out = out2;
                try {
                    CrDb.m0d("gallery image file", "failed to save: " + this.dir + this.name + " reason:\n " + e.toString());
                    e.printStackTrace();
                    if (out != null) {
                        try {
                            out.close();
                        } catch (Throwable th3) {
                        }
                    }
                    CrDb.m0d("gallery image file", "saved!" + this.dir + this.name);
                } catch (Throwable th4) {
                    th = th4;
                    if (out != null) {
                        try {
                            out.close();
                        } catch (Throwable th5) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
                out = out2;
                if (out != null) {
                    out.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            CrDb.m0d("gallery image file", "failed to save: " + this.dir + this.name + " reason:\n " + e.toString());
            e.printStackTrace();
            if (out != null) {
                out.close();
            }
            CrDb.m0d("gallery image file", "saved!" + this.dir + this.name);
        }
        CrDb.m0d("gallery image file", "saved!" + this.dir + this.name);
    }

    public void onBitmapFailed(Drawable errorDrawable) {
    }

    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }
}

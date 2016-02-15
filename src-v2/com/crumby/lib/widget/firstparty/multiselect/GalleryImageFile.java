/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.drawable.Drawable
 *  android.os.Environment
 */
package com.crumby.lib.widget.firstparty.multiselect;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class GalleryImageFile
implements Target {
    private final String dir;
    private final String name;

    public GalleryImageFile(GalleryImage object) {
        this.dir = Environment.getExternalStoragePublicDirectory((String)Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        object = object.getImageUrl();
        this.name = object.substring(object.lastIndexOf(47) + 1);
    }

    public GalleryImageFile(String string2, String string3) {
        this.dir = string2;
        this.name = string3;
    }

    @Override
    public void onBitmapFailed(Drawable drawable2) {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void onBitmapLoaded(Bitmap var1_1, Picasso.LoadedFrom var2_7) {
        var3_10 = null;
        var4_12 = null;
        var2_7 = var3_10;
        CrDb.d("gallery image file", "saving: " + this.dir + this.name);
        var2_7 = var3_10;
        var3_10 = new FileOutputStream(this.dir + this.name);
        var1_1.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)var3_10);
        ** if (var3_10 == null) goto lbl15
lbl-1000: // 1 sources:
        {
            try {
                var3_10.close();
            }
            catch (Throwable var1_2) {}
        }
lbl15: // 2 sources:
        ** GOTO lbl41
        catch (Exception var3_11) {
            block15 : {
                var1_1 = var4_12;
                ** GOTO lbl25
                catch (Throwable var1_6) {
                    var2_7 = var3_10;
                    ** GOTO lbl-1000
                }
                catch (Exception var2_9) {
                    var1_1 = var3_10;
                    var3_10 = var2_9;
                }
lbl25: // 2 sources:
                var2_7 = var1_1;
                try {
                    CrDb.d("gallery image file", "failed to save: " + this.dir + this.name + " reason:\n " + var3_10.toString());
                    var2_7 = var1_1;
                    var3_10.printStackTrace();
                    if (var1_1 == null) break block15;
                }
                catch (Throwable var1_4) lbl-1000: // 2 sources:
                {
                    if (var2_7 == null) throw var1_5;
                    try {
                        var2_7.close();
                    }
                    finally {
                        throw var1_5;
                    }
                }
                try {
                    var1_1.close();
                }
                catch (Throwable var1_3) {}
            }
            CrDb.d("gallery image file", "saved!" + this.dir + this.name);
            return;
        }
    }

    @Override
    public void onPrepareLoad(Drawable drawable2) {
    }
}


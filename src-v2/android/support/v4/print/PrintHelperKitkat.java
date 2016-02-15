/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.pdf.PdfDocument
 *  android.graphics.pdf.PdfDocument$Page
 *  android.graphics.pdf.PdfDocument$PageInfo
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.CancellationSignal$OnCancelListener
 *  android.os.ParcelFileDescriptor
 *  android.print.PageRange
 *  android.print.PrintAttributes
 *  android.print.PrintAttributes$Builder
 *  android.print.PrintAttributes$MediaSize
 *  android.print.PrintDocumentAdapter
 *  android.print.PrintDocumentAdapter$LayoutResultCallback
 *  android.print.PrintDocumentAdapter$WriteResultCallback
 *  android.print.PrintDocumentInfo
 *  android.print.PrintDocumentInfo$Builder
 *  android.print.PrintJob
 *  android.print.PrintManager
 *  android.print.pdf.PrintedPdfDocument
 *  android.util.Log
 */
package android.support.v4.print;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class PrintHelperKitkat {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    private static final String LOG_TAG = "PrintHelperKitkat";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    int mColorMode = 2;
    final Context mContext;
    BitmapFactory.Options mDecodeOptions = null;
    private final Object mLock = new Object();
    int mOrientation = 1;
    int mScaleMode = 2;

    PrintHelperKitkat(Context context) {
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Matrix getMatrix(int n2, int n3, RectF rectF, int n4) {
        Matrix matrix = new Matrix();
        float f2 = rectF.width() / (float)n2;
        f2 = n4 == 2 ? Math.max(f2, rectF.height() / (float)n3) : Math.min(f2, rectF.height() / (float)n3);
        matrix.postScale(f2, f2);
        matrix.postTranslate((rectF.width() - (float)n2 * f2) / 2.0f, (rectF.height() - (float)n3 * f2) / 2.0f);
        return matrix;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Bitmap loadBitmap(Uri object, BitmapFactory.Options options) throws FileNotFoundException {
        if (object == null) throw new IllegalArgumentException("bad argument to loadBitmap");
        if (this.mContext == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        Object object2 = null;
        try {
            object2 = object = this.mContext.getContentResolver().openInputStream((Uri)object);
            options = BitmapFactory.decodeStream((InputStream)object, (Rect)null, (BitmapFactory.Options)options);
            if (object == null) return options;
        }
        catch (Throwable var1_3) {
            if (object2 == null) throw var1_3;
            try {
                object2.close();
            }
            catch (IOException iOException) {
                Log.w((String)"PrintHelperKitkat", (String)"close fail ", (Throwable)iOException);
                throw var1_3;
            }
            throw var1_3;
        }
        try {
            object.close();
            return options;
        }
        catch (IOException var1_2) {
            Log.w((String)"PrintHelperKitkat", (String)"close fail ", (Throwable)var1_2);
            return options;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Bitmap loadConstrainedBitmap(Uri object, int n2) throws FileNotFoundException {
        if (n2 <= 0) throw new IllegalArgumentException("bad argument to getScaledBitmap");
        if (object == null) throw new IllegalArgumentException("bad argument to getScaledBitmap");
        if (this.mContext == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        Object object2 = new BitmapFactory.Options();
        object2.inJustDecodeBounds = true;
        this.loadBitmap((Uri)object, (BitmapFactory.Options)object2);
        int n3 = object2.outWidth;
        int n4 = object2.outHeight;
        if (n3 <= 0) return null;
        if (n4 <= 0) {
            return null;
        }
        int n5 = 1;
        for (int i2 = Math.max((int)n3, (int)n4); i2 > n2; i2 >>>= 1, n5 <<= 1) {
        }
        if (n5 <= 0) return null;
        if (Math.min(n3, n4) / n5 <= 0) return null;
        object2 = this.mLock;
        // MONITORENTER : object2
        this.mDecodeOptions = new BitmapFactory.Options();
        this.mDecodeOptions.inMutable = true;
        this.mDecodeOptions.inSampleSize = n5;
        BitmapFactory.Options options = this.mDecodeOptions;
        // MONITOREXIT : object2
        try {
            object2 = this.loadBitmap((Uri)object, options);
            return object2;
        }
        finally {
            object = this.mLock;
            // MONITORENTER : object
            this.mDecodeOptions = null;
            // MONITOREXIT : object
        }
    }

    public int getColorMode() {
        return this.mColorMode;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getScaleMode() {
        return this.mScaleMode;
    }

    public void printBitmap(final String string2, final Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        final int n2 = this.mScaleMode;
        PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
        PrintAttributes.MediaSize mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
        }
        mediaSize = new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
        printManager.print(string2, new PrintDocumentAdapter(){
            private PrintAttributes mAttributes;

            /*
             * Enabled aggressive block sorting
             */
            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                boolean bl2 = true;
                this.mAttributes = printAttributes2;
                cancellationSignal = new PrintDocumentInfo.Builder(string2).setContentType(1).setPageCount(1).build();
                if (printAttributes2.equals((Object)printAttributes)) {
                    bl2 = false;
                }
                layoutResultCallback.onLayoutFinished((PrintDocumentInfo)cancellationSignal, bl2);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public void onWrite(PageRange[] printedPdfDocument, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                printedPdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                try {
                    cancellationSignal = printedPdfDocument.startPage(1);
                    RectF rectF = new RectF(cancellationSignal.getInfo().getContentRect());
                    rectF = PrintHelperKitkat.this.getMatrix(bitmap.getWidth(), bitmap.getHeight(), rectF, n2);
                    cancellationSignal.getCanvas().drawBitmap(bitmap, (Matrix)rectF, null);
                    printedPdfDocument.finishPage((PdfDocument.Page)cancellationSignal);
                    try {
                        printedPdfDocument.writeTo((OutputStream)new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                        writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                        {
                            do {
                                return;
                                break;
                            } while (true);
                        }
                    }
                    catch (IOException var3_6) {
                        Log.e((String)"PrintHelperKitkat", (String)"Error writing printed content", (Throwable)var3_6);
                        writeResultCallback.onWriteFailed(null);
                        return;
                    }
                }
                finally {
                    if (printedPdfDocument != null) {
                        printedPdfDocument.close();
                    }
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                }
            }
        }, (PrintAttributes)mediaSize);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void printBitmap(final String string2, Uri object) throws FileNotFoundException {
        object = new PrintDocumentAdapter((Uri)object, this.mScaleMode){
            AsyncTask<Uri, Boolean, Bitmap> loadBitmap;
            private PrintAttributes mAttributes;
            Bitmap mBitmap;
            final /* synthetic */ int val$fittingMode;
            final /* synthetic */ Uri val$imageFile;

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private void cancelLoad() {
                Object object = PrintHelperKitkat.this.mLock;
                synchronized (object) {
                    if (PrintHelperKitkat.this.mDecodeOptions != null) {
                        PrintHelperKitkat.this.mDecodeOptions.requestCancelDecode();
                        PrintHelperKitkat.this.mDecodeOptions = null;
                    }
                    return;
                }
            }

            public void onFinish() {
                super.onFinish();
                this.cancelLoad();
                this.loadBitmap.cancel(true);
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onLayout(final PrintAttributes printAttributes, final PrintAttributes printAttributes2, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                boolean bl2 = true;
                if (cancellationSignal.isCanceled()) {
                    layoutResultCallback.onLayoutCancelled();
                    this.mAttributes = printAttributes2;
                    return;
                }
                if (this.mBitmap == null) {
                    this.loadBitmap = new AsyncTask<Uri, Boolean, Bitmap>(){

                        protected /* varargs */ Bitmap doInBackground(Uri ... bitmap) {
                            try {
                                bitmap = PrintHelperKitkat.this.loadConstrainedBitmap(2.this.val$imageFile, 3500);
                                return bitmap;
                            }
                            catch (FileNotFoundException var1_2) {
                                return null;
                            }
                        }

                        protected void onCancelled(Bitmap bitmap) {
                            layoutResultCallback.onLayoutCancelled();
                        }

                        /*
                         * Enabled aggressive block sorting
                         */
                        protected void onPostExecute(Bitmap bitmap) {
                            boolean bl2 = true;
                            super.onPostExecute((Object)bitmap);
                            2.this.mBitmap = bitmap;
                            if (bitmap == null) {
                                layoutResultCallback.onLayoutFailed(null);
                                return;
                            }
                            bitmap = new PrintDocumentInfo.Builder(string2).setContentType(1).setPageCount(1).build();
                            if (printAttributes2.equals((Object)printAttributes)) {
                                bl2 = false;
                            }
                            layoutResultCallback.onLayoutFinished((PrintDocumentInfo)bitmap, bl2);
                        }

                        protected void onPreExecute() {
                            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){

                                public void onCancel() {
                                    2.this.cancelLoad();
                                    1.this.cancel(false);
                                }
                            });
                        }

                    };
                    this.loadBitmap.execute((Object[])new Uri[0]);
                    this.mAttributes = printAttributes2;
                    return;
                }
                cancellationSignal = new PrintDocumentInfo.Builder(string2).setContentType(1).setPageCount(1).build();
                if (printAttributes2.equals((Object)printAttributes)) {
                    bl2 = false;
                }
                layoutResultCallback.onLayoutFinished((PrintDocumentInfo)cancellationSignal, bl2);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public void onWrite(PageRange[] printedPdfDocument, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                printedPdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                try {
                    cancellationSignal = printedPdfDocument.startPage(1);
                    RectF rectF = new RectF(cancellationSignal.getInfo().getContentRect());
                    rectF = PrintHelperKitkat.this.getMatrix(this.mBitmap.getWidth(), this.mBitmap.getHeight(), rectF, this.val$fittingMode);
                    cancellationSignal.getCanvas().drawBitmap(this.mBitmap, (Matrix)rectF, null);
                    printedPdfDocument.finishPage((PdfDocument.Page)cancellationSignal);
                    try {
                        printedPdfDocument.writeTo((OutputStream)new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                        writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                        {
                            do {
                                return;
                                break;
                            } while (true);
                        }
                    }
                    catch (IOException var3_6) {
                        Log.e((String)"PrintHelperKitkat", (String)"Error writing printed content", (Throwable)var3_6);
                        writeResultCallback.onWriteFailed(null);
                        return;
                    }
                }
                finally {
                    if (printedPdfDocument != null) {
                        printedPdfDocument.close();
                    }
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                }
            }

        };
        PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(this.mColorMode);
        if (this.mOrientation == 1) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
        } else if (this.mOrientation == 2) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
        }
        printManager.print(string2, (PrintDocumentAdapter)object, builder.build());
    }

    public void setColorMode(int n2) {
        this.mColorMode = n2;
    }

    public void setOrientation(int n2) {
        this.mOrientation = n2;
    }

    public void setScaleMode(int n2) {
        this.mScaleMode = n2;
    }

}


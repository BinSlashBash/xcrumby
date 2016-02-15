package com.crumby.lib.widget.firstparty.multiselect;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import com.crumby.lib.GalleryImage;
import com.squareup.picasso.Target;
import java.io.File;

public class GalleryImageFile
  implements Target
{
  private final String dir;
  private final String name;
  
  public GalleryImageFile(GalleryImage paramGalleryImage)
  {
    this.dir = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/");
    paramGalleryImage = paramGalleryImage.getImageUrl();
    this.name = paramGalleryImage.substring(paramGalleryImage.lastIndexOf('/') + 1);
  }
  
  public GalleryImageFile(String paramString1, String paramString2)
  {
    this.dir = paramString1;
    this.name = paramString2;
  }
  
  public void onBitmapFailed(Drawable paramDrawable) {}
  
  /* Error */
  public void onBitmapLoaded(android.graphics.Bitmap paramBitmap, com.squareup.picasso.Picasso.LoadedFrom paramLoadedFrom)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: aload_3
    //   6: astore_2
    //   7: ldc 70
    //   9: new 16	java/lang/StringBuilder
    //   12: dup
    //   13: invokespecial 17	java/lang/StringBuilder:<init>	()V
    //   16: ldc 72
    //   18: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: aload_0
    //   22: getfield 41	com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile:dir	Ljava/lang/String;
    //   25: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: aload_0
    //   29: getfield 58	com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile:name	Ljava/lang/String;
    //   32: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: invokestatic 77	com/crumby/CrDb:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   41: aload_3
    //   42: astore_2
    //   43: new 79	java/io/FileOutputStream
    //   46: dup
    //   47: new 16	java/lang/StringBuilder
    //   50: dup
    //   51: invokespecial 17	java/lang/StringBuilder:<init>	()V
    //   54: aload_0
    //   55: getfield 41	com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile:dir	Ljava/lang/String;
    //   58: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: aload_0
    //   62: getfield 58	com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile:name	Ljava/lang/String;
    //   65: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   71: invokespecial 82	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   74: astore_3
    //   75: aload_1
    //   76: getstatic 88	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   79: bipush 100
    //   81: aload_3
    //   82: invokevirtual 94	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   85: pop
    //   86: aload_3
    //   87: ifnull +7 -> 94
    //   90: aload_3
    //   91: invokevirtual 97	java/io/FileOutputStream:close	()V
    //   94: ldc 70
    //   96: new 16	java/lang/StringBuilder
    //   99: dup
    //   100: invokespecial 17	java/lang/StringBuilder:<init>	()V
    //   103: ldc 99
    //   105: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: aload_0
    //   109: getfield 41	com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile:dir	Ljava/lang/String;
    //   112: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: aload_0
    //   116: getfield 58	com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile:name	Ljava/lang/String;
    //   119: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: invokestatic 77	com/crumby/CrDb:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   128: return
    //   129: astore_1
    //   130: goto -36 -> 94
    //   133: astore_3
    //   134: aload 4
    //   136: astore_1
    //   137: aload_1
    //   138: astore_2
    //   139: ldc 70
    //   141: new 16	java/lang/StringBuilder
    //   144: dup
    //   145: invokespecial 17	java/lang/StringBuilder:<init>	()V
    //   148: ldc 101
    //   150: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: aload_0
    //   154: getfield 41	com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile:dir	Ljava/lang/String;
    //   157: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: aload_0
    //   161: getfield 58	com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile:name	Ljava/lang/String;
    //   164: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: ldc 103
    //   169: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: aload_3
    //   173: invokevirtual 104	java/lang/Exception:toString	()Ljava/lang/String;
    //   176: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   182: invokestatic 77	com/crumby/CrDb:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   185: aload_1
    //   186: astore_2
    //   187: aload_3
    //   188: invokevirtual 107	java/lang/Exception:printStackTrace	()V
    //   191: aload_1
    //   192: ifnull -98 -> 94
    //   195: aload_1
    //   196: invokevirtual 97	java/io/FileOutputStream:close	()V
    //   199: goto -105 -> 94
    //   202: astore_1
    //   203: goto -109 -> 94
    //   206: astore_1
    //   207: aload_2
    //   208: ifnull +7 -> 215
    //   211: aload_2
    //   212: invokevirtual 97	java/io/FileOutputStream:close	()V
    //   215: aload_1
    //   216: athrow
    //   217: astore_2
    //   218: goto -3 -> 215
    //   221: astore_1
    //   222: aload_3
    //   223: astore_2
    //   224: goto -17 -> 207
    //   227: astore_2
    //   228: aload_3
    //   229: astore_1
    //   230: aload_2
    //   231: astore_3
    //   232: goto -95 -> 137
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	235	0	this	GalleryImageFile
    //   0	235	1	paramBitmap	android.graphics.Bitmap
    //   0	235	2	paramLoadedFrom	com.squareup.picasso.Picasso.LoadedFrom
    //   1	90	3	localFileOutputStream	java.io.FileOutputStream
    //   133	96	3	localException	Exception
    //   231	1	3	localLoadedFrom	com.squareup.picasso.Picasso.LoadedFrom
    //   3	132	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   90	94	129	java/lang/Throwable
    //   7	41	133	java/lang/Exception
    //   43	75	133	java/lang/Exception
    //   195	199	202	java/lang/Throwable
    //   7	41	206	finally
    //   43	75	206	finally
    //   139	185	206	finally
    //   187	191	206	finally
    //   211	215	217	java/lang/Throwable
    //   75	86	221	finally
    //   75	86	227	java/lang/Exception
  }
  
  public void onPrepareLoad(Drawable paramDrawable) {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/multiselect/GalleryImageFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
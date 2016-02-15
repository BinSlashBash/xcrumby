package com.uservoice.uservoicesdk.image;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownloadImageTask
  extends AsyncTask<Void, Void, Bitmap>
{
  private ImageView imageView;
  private final String url;
  
  public DownloadImageTask(String paramString, ImageView paramImageView)
  {
    this.url = paramString;
    this.imageView = paramImageView;
    paramImageView.setImageBitmap(null);
  }
  
  /* Error */
  protected Bitmap doInBackground(Void... paramVarArgs)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_1
    //   4: new 31	java/net/URL
    //   7: dup
    //   8: aload_0
    //   9: getfield 16	com/uservoice/uservoicesdk/image/DownloadImageTask:url	Ljava/lang/String;
    //   12: invokespecial 34	java/net/URL:<init>	(Ljava/lang/String;)V
    //   15: invokevirtual 38	java/net/URL:openStream	()Ljava/io/InputStream;
    //   18: astore_3
    //   19: aload_3
    //   20: astore_1
    //   21: aload_3
    //   22: astore_2
    //   23: aload_3
    //   24: invokestatic 44	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   27: astore 4
    //   29: aload_3
    //   30: invokevirtual 49	java/io/InputStream:close	()V
    //   33: aload 4
    //   35: areturn
    //   36: astore_3
    //   37: aload_1
    //   38: astore_2
    //   39: aload_3
    //   40: invokevirtual 52	java/lang/Exception:printStackTrace	()V
    //   43: aload_1
    //   44: invokevirtual 49	java/io/InputStream:close	()V
    //   47: aconst_null
    //   48: areturn
    //   49: astore_1
    //   50: aconst_null
    //   51: areturn
    //   52: astore_1
    //   53: aload_2
    //   54: invokevirtual 49	java/io/InputStream:close	()V
    //   57: aload_1
    //   58: athrow
    //   59: astore_1
    //   60: aload 4
    //   62: areturn
    //   63: astore_2
    //   64: goto -7 -> 57
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	67	0	this	DownloadImageTask
    //   0	67	1	paramVarArgs	Void[]
    //   1	53	2	localObject	Object
    //   63	1	2	localException1	Exception
    //   18	12	3	localInputStream	java.io.InputStream
    //   36	4	3	localException2	Exception
    //   27	34	4	localBitmap	Bitmap
    // Exception table:
    //   from	to	target	type
    //   4	19	36	java/lang/Exception
    //   23	29	36	java/lang/Exception
    //   43	47	49	java/lang/Exception
    //   4	19	52	finally
    //   23	29	52	finally
    //   39	43	52	finally
    //   29	33	59	java/lang/Exception
    //   53	57	63	java/lang/Exception
  }
  
  protected void onPostExecute(Bitmap paramBitmap)
  {
    ImageCache.getInstance().set(this.url, paramBitmap);
    this.imageView.setImageBitmap(paramBitmap);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/image/DownloadImageTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package com.crumby.impl.device;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DeviceFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "/";
  public static final String BREADCRUMB_NAME = "/";
  public static final String REGEX_BASE = "/";
  public static final String REGEX_URL = "/.*";
  public static final String ROOT_NAME = "device";
  List<String> crumbs;
  File dir;
  private List<GalleryImage> images;
  
  private void appendToBreadcrumbs(List<Breadcrumb> paramList, FragmentIndex paramFragmentIndex, int paramInt, String paramString1, String paramString2)
  {
    paramFragmentIndex = new Breadcrumb(getActivity().getBaseContext(), paramFragmentIndex, paramInt, paramString2);
    paramFragmentIndex.setBreadcrumbText(paramString1);
    paramList.add(paramFragmentIndex);
  }
  
  protected void cleanLinkUrl() {}
  
  protected GalleryProducer createProducer()
  {
    new SingleGalleryProducer()
    {
      protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramAnonymousArrayList)
      {
        File[] arrayOfFile = DeviceFragment.this.dir.listFiles(new DeviceFragment.ImageFileFilter(DeviceFragment.this, DeviceFragment.this.dir));
        int j = arrayOfFile.length;
        int i = 0;
        while (i < j)
        {
          File localFile = arrayOfFile[i];
          GalleryImage localGalleryImage = new GalleryImage(localFile.getName(), localFile.getAbsolutePath(), localFile.getName());
          if (localFile.isDirectory()) {
            localGalleryImage.setLinksToGallery(true);
          }
          paramAnonymousArrayList.add(localGalleryImage);
          i += 1;
        }
      }
    };
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    LinkedList localLinkedList = new LinkedList();
    String str1 = getUrl();
    String[] arrayOfString = str1.split("/");
    this.dir = Environment.getRootDirectory();
    FragmentIndex localFragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(str1);
    appendToBreadcrumbs(localLinkedList, localFragmentIndex, 1, "system", "/");
    int m = arrayOfString.length;
    int j = 0;
    str1 = "";
    int i = 0;
    if (j < m)
    {
      String str2 = arrayOfString[j];
      str1 = str1 + str2 + "/";
      if (!str2.equals(""))
      {
        int k = i + 1;
        if (i < 1) {
          i = k;
        }
        for (;;)
        {
          j += 1;
          break;
          try
          {
            this.dir = this.dir.listFiles(new SpecificFileFilter(this.dir, str2))[0];
            appendToBreadcrumbs(localLinkedList, localFragmentIndex, 1, str2, str1);
            i = k + 1;
          }
          catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
        }
      }
    }
    for (;;)
    {
      overrideBreadcrumbs(localLinkedList);
      return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
      break;
    }
  }
  
  class ImageFileFilter
    implements FileFilter
  {
    File file;
    private final String[] okFileExtensions = { "jpg", "png", "gif", "jpeg" };
    
    public ImageFileFilter(File paramFile)
    {
      this.file = paramFile;
    }
    
    public boolean accept(File paramFile)
    {
      if (paramFile.isDirectory()) {
        return true;
      }
      String[] arrayOfString = this.okFileExtensions;
      int j = arrayOfString.length;
      int i = 0;
      for (;;)
      {
        if (i >= j) {
          break label54;
        }
        String str = arrayOfString[i];
        if (paramFile.getName().toLowerCase().endsWith(str)) {
          break;
        }
        i += 1;
      }
      label54:
      return false;
    }
  }
  
  class SpecificFileFilter
    implements FileFilter
  {
    File file;
    String name;
    
    public SpecificFileFilter(File paramFile, String paramString)
    {
      this.file = paramFile;
      this.name = paramString;
    }
    
    public boolean accept(File paramFile)
    {
      return paramFile.getName().equals(this.name);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/device/DeviceFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
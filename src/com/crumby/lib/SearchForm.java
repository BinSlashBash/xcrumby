package com.crumby.lib;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.form.FormField;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class SearchForm
{
  private Map<String, FormField> form = new HashMap();
  
  public SearchForm(ViewGroup paramViewGroup)
  {
    parseForm(paramViewGroup);
  }
  
  private void parseForm(ViewGroup paramViewGroup)
  {
    int j = paramViewGroup.getChildCount();
    int i = 0;
    if (i < j)
    {
      View localView = paramViewGroup.getChildAt(i);
      if ((localView instanceof ViewGroup)) {
        parseForm((ViewGroup)localView);
      }
      for (;;)
      {
        i += 1;
        break;
        if ((localView instanceof FormField)) {
          this.form.put(((FormField)localView).getArgumentName(), (FormField)localView);
        }
      }
    }
  }
  
  private List<NameValuePair> retrieveFormData()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.form.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localArrayList.add(new BasicNameValuePair((String)localEntry.getKey(), ((FormField)localEntry.getValue()).getFieldValue()));
    }
    return localArrayList;
  }
  
  public String encodeFormData()
  {
    return URLEncodedUtils.format(retrieveFormData(), "UTF-8");
  }
  
  public void setEditorActionListener(TextView.OnEditorActionListener paramOnEditorActionListener)
  {
    if (this.form == null) {}
    for (;;)
    {
      return;
      Iterator localIterator = this.form.values().iterator();
      while (localIterator.hasNext())
      {
        FormField localFormField = (FormField)localIterator.next();
        if ((localFormField instanceof EditText)) {
          ((EditText)localFormField).setOnEditorActionListener(paramOnEditorActionListener);
        }
      }
    }
  }
  
  public String setFormData(URL paramURL)
  {
    Uri localUri = Uri.parse(paramURL.toString());
    Object localObject1;
    if (!localUri.isHierarchical())
    {
      localObject1 = "";
      return (String)localObject1;
    }
    Iterator localIterator = localUri.getQueryParameterNames().iterator();
    String str = "";
    for (;;)
    {
      localObject1 = str;
      if (!localIterator.hasNext()) {
        break;
      }
      Object localObject2 = (String)localIterator.next();
      localObject1 = GalleryProducer.getQueryParameter(localUri, paramURL.toString(), (String)localObject2);
      localObject2 = (FormField)this.form.get(localObject2);
      if ((localObject2 != null) && (!((FormField)localObject2).getFieldValue().equals(localObject1)))
      {
        ((FormField)localObject2).setFieldValue((String)localObject1);
        if (((FormField)localObject2).getNiceName() != null) {
          str = str + ((FormField)localObject2).getNiceName() + " " + (String)localObject1 + ", ";
        }
      }
    }
  }
  
  public void setFormData(String paramString)
  {
    try
    {
      setFormData(new URL(paramString));
      return;
    }
    catch (MalformedURLException paramString) {}
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/SearchForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
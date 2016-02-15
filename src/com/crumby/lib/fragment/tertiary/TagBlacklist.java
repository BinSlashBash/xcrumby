package com.crumby.lib.fragment.tertiary;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import com.crumby.GalleryViewer;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class TagBlacklist
  extends DialogFragment
{
  private EditText blacklist;
  private Handler handler;
  Runnable saveTagsRunnable;
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    paramBundle = super.onCreateDialog(paramBundle);
    paramBundle.getWindow().requestFeature(1);
    return paramBundle;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramBundle = paramLayoutInflater.inflate(2130903116, null);
    this.handler = new Handler();
    this.saveTagsRunnable = new Runnable()
    {
      public void run()
      {
        if (TagBlacklist.this.getActivity() == null) {}
        LinkedHashSet localLinkedHashSet;
        do
        {
          return;
          String[] arrayOfString = TagBlacklist.this.blacklist.getText().toString().trim().split("\\s+");
          localLinkedHashSet = new LinkedHashSet();
          int j = arrayOfString.length;
          int i = 0;
          while (i < j)
          {
            String str = arrayOfString[i];
            if (!str.trim().equals("")) {
              localLinkedHashSet.add(str);
            }
            i += 1;
          }
        } while ((GalleryViewer.BLACK_LISTED_TAGS != null) && (GalleryViewer.BLACK_LISTED_TAGS.equals(localLinkedHashSet)));
        PreferenceManager.getDefaultSharedPreferences(TagBlacklist.this.getActivity()).edit().putStringSet("crumbyTagBlacklistKey", localLinkedHashSet).commit();
        GalleryViewer.BLACK_LISTED_TAGS = localLinkedHashSet;
      }
    };
    this.blacklist = ((EditText)paramBundle.findViewById(2131493114));
    this.blacklist.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        TagBlacklist.this.handler.removeCallbacks(TagBlacklist.this.saveTagsRunnable);
        TagBlacklist.this.handler.postDelayed(TagBlacklist.this.saveTagsRunnable, 500L);
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    });
    paramLayoutInflater = "";
    paramViewGroup = paramLayoutInflater;
    if (GalleryViewer.BLACK_LISTED_TAGS != null)
    {
      Iterator localIterator = GalleryViewer.BLACK_LISTED_TAGS.iterator();
      for (;;)
      {
        paramViewGroup = paramLayoutInflater;
        if (!localIterator.hasNext()) {
          break;
        }
        paramViewGroup = (String)localIterator.next();
        paramLayoutInflater = paramLayoutInflater + paramViewGroup + " ";
      }
    }
    this.blacklist.setText(paramViewGroup);
    paramBundle.findViewById(2131493112).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TagBlacklist.this.getDialog() != null) {
          TagBlacklist.this.getDialog().dismiss();
        }
      }
    });
    return paramBundle;
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    if (this.saveTagsRunnable != null) {
      this.saveTagsRunnable.run();
    }
    super.onDismiss(paramDialogInterface);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/tertiary/TagBlacklist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
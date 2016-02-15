package com.crumby.impl.crumby;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.events.WebsiteSettingsChangedEvent;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.router.IndexAttributeValue;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.IndexSetting;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.uservoice.uservoicesdk.UserVoice;
import java.util.List;

public class CrumbySettings
  extends DialogFragment
{
  private static final String WANTS_TO_VOTE_IDEA = "wantsToVote";
  private static boolean showedChangeSettings;
  private Handler handler;
  private int lastPosition;
  private boolean mustRefreshHome;
  private SparseBooleanArray open;
  private GalleryProducer producer;
  private Runnable saveRunnable;
  List<IndexSetting> settings;
  private View settingsBottomBar;
  
  private void callSave()
  {
    if (!showedChangeSettings) {
      showChangeSettings();
    }
    this.mustRefreshHome = true;
    this.handler.removeCallbacks(this.saveRunnable);
    this.handler.postDelayed(this.saveRunnable, 500L);
  }
  
  private void hideSettingsBottomBar()
  {
    if (getActivity() == null) {
      return;
    }
    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean("wantsToVote", false).commit();
    this.settingsBottomBar.setVisibility(8);
  }
  
  private void showChangeSettings()
  {
    showedChangeSettings = true;
    if (getActivity() == null) {}
    while (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("crumbyHasVisitedSettings", false)) {
      return;
    }
    Toast localToast = Toast.makeText(getActivity(), "", 1);
    localToast.setGravity(17, 0, 0);
    TextView localTextView = (TextView)View.inflate(getActivity(), 2130903119, null);
    localTextView.setText("Hint: settings are auto-saved!");
    localToast.setView(localTextView);
    localToast.show();
    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean("crumbyHasVisitedSettings", true).commit();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    paramBundle = super.onCreateDialog(paramBundle);
    paramBundle.getWindow().requestFeature(1);
    return paramBundle;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramViewGroup = paramLayoutInflater.inflate(2130903051, null);
    paramBundle = PreferenceManager.getDefaultSharedPreferences(getActivity());
    paramBundle.edit().putBoolean("wantsSettingPrompt", false).commit();
    if (this.settings != null) {
      this.settings.clear();
    }
    if (this.handler == null)
    {
      this.handler = new Handler();
      this.saveRunnable = new Runnable()
      {
        public void run()
        {
          FragmentRouter.INSTANCE.saveSettings(CrumbySettings.this.getActivity());
        }
      };
    }
    this.settings = FragmentRouter.INSTANCE.getAllIndexSettings();
    this.settingsBottomBar = paramViewGroup.findViewById(2131492924);
    if (!paramBundle.getBoolean("wantsToVote", true)) {
      this.settingsBottomBar.setVisibility(8);
    }
    paramViewGroup.findViewById(2131492926).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "not interested in voting", null);
        CrumbySettings.this.hideSettingsBottomBar();
      }
    });
    paramViewGroup.findViewById(2131492925).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newNavigationEvent("uservoice", "settings vote");
        UserVoice.launchForum(CrumbySettings.this.getActivity());
        CrumbySettings.this.hideSettingsBottomBar();
      }
    });
    paramBundle = (ExpandableListView)paramViewGroup.findViewById(2131492923);
    paramLayoutInflater = paramLayoutInflater.inflate(2130903112, null);
    paramLayoutInflater.findViewById(2131493112).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (CrumbySettings.this.getDialog() != null) {
          CrumbySettings.this.getDialog().cancel();
        }
      }
    });
    paramBundle.addHeaderView(paramLayoutInflater);
    paramBundle.setAdapter(new BaseExpandableListAdapter()
    {
      private void inflateSetting(View paramAnonymousView, final IndexSetting paramAnonymousIndexSetting)
      {
        paramAnonymousView = (ViewGroup)paramAnonymousView.findViewById(2131493168);
        paramAnonymousView.removeAllViews();
        IndexField[] arrayOfIndexField = paramAnonymousIndexSetting.getFields();
        int j = arrayOfIndexField.length;
        int i = 0;
        if (i < j)
        {
          final IndexField localIndexField = arrayOfIndexField[i];
          final Object localObject2 = paramAnonymousIndexSetting.getAttributeValue(localIndexField.key);
          Object localObject1 = ((IndexAttributeValue)localObject2).getObject();
          if ((localObject1 instanceof Number))
          {
            localObject2 = new TextView(CrumbySettings.this.getActivity());
            ((TextView)localObject2).setText(localIndexField.name);
            new EditText(CrumbySettings.this.getActivity()).setText((String)localObject1);
            paramAnonymousView.addView((View)localObject2);
          }
          for (;;)
          {
            i += 1;
            break;
            if ((localObject1 instanceof Boolean))
            {
              CheckBox localCheckBox = new CheckBox(CrumbySettings.this.getActivity());
              localCheckBox.setText(localIndexField.name);
              localCheckBox.setTextColor(CrumbySettings.this.getResources().getColor(2131427518));
              localCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
              {
                public void onCheckedChanged(CompoundButton paramAnonymous2CompoundButton, boolean paramAnonymous2Boolean)
                {
                  Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, paramAnonymousIndexSetting.getDisplayName() + " checkbox change", paramAnonymous2Boolean + " " + localIndexField.name);
                  CrumbySettings.this.callSave();
                  localObject2.setValue(Boolean.valueOf(paramAnonymous2Boolean));
                }
              });
              localCheckBox.setChecked(((Boolean)localObject1).booleanValue());
              paramAnonymousView.addView(localCheckBox);
            }
            else if (((localObject1 instanceof String)) || (!(localObject1 instanceof Character))) {}
          }
        }
      }
      
      public Object getChild(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return null;
      }
      
      public long getChildId(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return 0L;
      }
      
      public View getChildView(int paramAnonymousInt1, int paramAnonymousInt2, boolean paramAnonymousBoolean, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousViewGroup = paramAnonymousView;
        if (paramAnonymousView == null) {
          paramAnonymousViewGroup = View.inflate(CrumbySettings.this.getActivity(), 2130903150, null);
        }
        inflateSetting(paramAnonymousViewGroup, (IndexSetting)CrumbySettings.this.settings.get(paramAnonymousInt1));
        return paramAnonymousViewGroup;
      }
      
      public int getChildrenCount(int paramAnonymousInt)
      {
        return 1;
      }
      
      public Object getGroup(int paramAnonymousInt)
      {
        return CrumbySettings.this.settings.get(paramAnonymousInt);
      }
      
      public int getGroupCount()
      {
        return CrumbySettings.this.settings.size();
      }
      
      public long getGroupId(int paramAnonymousInt)
      {
        return 0L;
      }
      
      public View getGroupView(int paramAnonymousInt, boolean paramAnonymousBoolean, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousViewGroup = paramAnonymousView;
        if (paramAnonymousView == null) {
          paramAnonymousViewGroup = View.inflate(CrumbySettings.this.getActivity(), 2130903149, null);
        }
        paramAnonymousView = (IndexSetting)CrumbySettings.this.settings.get(paramAnonymousInt);
        ((TextView)paramAnonymousViewGroup.findViewById(2131493099)).setText(paramAnonymousView.getDisplayName());
        Picasso.with(CrumbySettings.this.getActivity()).load(paramAnonymousView.getFaviconUrl()).into((ImageView)paramAnonymousViewGroup.findViewById(2131493167));
        return paramAnonymousViewGroup;
      }
      
      public boolean hasStableIds()
      {
        return false;
      }
      
      public boolean isChildSelectable(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return false;
      }
    });
    paramBundle.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt) {}
    });
    return paramViewGroup;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
  }
  
  public void onDestroyView()
  {
    this.settings.clear();
    super.onDestroyView();
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    super.onDismiss(paramDialogInterface);
    if (this.mustRefreshHome) {
      BusProvider.BUS.get().post(new WebsiteSettingsChangedEvent());
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/crumby/CrumbySettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
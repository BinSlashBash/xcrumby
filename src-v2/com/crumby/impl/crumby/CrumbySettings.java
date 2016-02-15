/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.preference.PreferenceManager
 *  android.util.SparseBooleanArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.BaseExpandableListAdapter
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.EditText
 *  android.widget.ExpandableListAdapter
 *  android.widget.ExpandableListView
 *  android.widget.ImageView
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.crumby.impl.crumby;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
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
extends DialogFragment {
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

    private void callSave() {
        if (!showedChangeSettings) {
            this.showChangeSettings();
        }
        this.mustRefreshHome = true;
        this.handler.removeCallbacks(this.saveRunnable);
        this.handler.postDelayed(this.saveRunnable, 500);
    }

    private void hideSettingsBottomBar() {
        if (this.getActivity() == null) {
            return;
        }
        PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("wantsToVote", false).commit();
        this.settingsBottomBar.setVisibility(8);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showChangeSettings() {
        showedChangeSettings = true;
        if (this.getActivity() == null || PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getBoolean("crumbyHasVisitedSettings", false)) {
            return;
        }
        Toast toast = Toast.makeText((Context)this.getActivity(), (CharSequence)"", (int)1);
        toast.setGravity(17, 0, 0);
        TextView textView = (TextView)View.inflate((Context)this.getActivity(), (int)2130903119, (ViewGroup)null);
        textView.setText((CharSequence)"Hint: settings are auto-saved!");
        toast.setView((View)textView);
        toast.show();
        PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("crumbyHasVisitedSettings", true).commit();
    }

    public Dialog onCreateDialog(Bundle bundle) {
        bundle = super.onCreateDialog(bundle);
        bundle.getWindow().requestFeature(1);
        return bundle;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = layoutInflater.inflate(2130903051, null);
        bundle = PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity());
        bundle.edit().putBoolean("wantsSettingPrompt", false).commit();
        if (this.settings != null) {
            this.settings.clear();
        }
        if (this.handler == null) {
            this.handler = new Handler();
            this.saveRunnable = new Runnable(){

                @Override
                public void run() {
                    FragmentRouter.INSTANCE.saveSettings((Context)CrumbySettings.this.getActivity());
                }
            };
        }
        this.settings = FragmentRouter.INSTANCE.getAllIndexSettings();
        this.settingsBottomBar = viewGroup.findViewById(2131492924);
        if (!bundle.getBoolean("wantsToVote", true)) {
            this.settingsBottomBar.setVisibility(8);
        }
        viewGroup.findViewById(2131492926).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "not interested in voting", null);
                CrumbySettings.this.hideSettingsBottomBar();
            }
        });
        viewGroup.findViewById(2131492925).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newNavigationEvent("uservoice", "settings vote");
                UserVoice.launchForum((Context)CrumbySettings.this.getActivity());
                CrumbySettings.this.hideSettingsBottomBar();
            }
        });
        bundle = (ExpandableListView)viewGroup.findViewById(2131492923);
        layoutInflater = layoutInflater.inflate(2130903112, null);
        layoutInflater.findViewById(2131493112).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (CrumbySettings.this.getDialog() != null) {
                    CrumbySettings.this.getDialog().cancel();
                }
            }
        });
        bundle.addHeaderView((View)layoutInflater);
        bundle.setAdapter((ExpandableListAdapter)new BaseExpandableListAdapter(){

            /*
             * Enabled aggressive block sorting
             */
            private void inflateSetting(View view, final IndexSetting indexSetting) {
                view = (ViewGroup)view.findViewById(2131493168);
                view.removeAllViews();
                IndexField[] arrindexField = indexSetting.getFields();
                int n2 = arrindexField.length;
                int n3 = 0;
                while (n3 < n2) {
                    final IndexField indexField = arrindexField[n3];
                    final IndexAttributeValue indexAttributeValue = indexSetting.getAttributeValue(indexField.key);
                    Object t2 = indexAttributeValue.getObject();
                    if (t2 instanceof Number) {
                        indexAttributeValue = new TextView((Context)CrumbySettings.this.getActivity());
                        indexAttributeValue.setText((CharSequence)indexField.name);
                        new EditText((Context)CrumbySettings.this.getActivity()).setText((CharSequence)((String)t2));
                        view.addView((View)indexAttributeValue);
                    } else if (t2 instanceof Boolean) {
                        CheckBox checkBox = new CheckBox((Context)CrumbySettings.this.getActivity());
                        checkBox.setText((CharSequence)indexField.name);
                        checkBox.setTextColor(CrumbySettings.this.getResources().getColor(2131427518));
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                            public void onCheckedChanged(CompoundButton compoundButton, boolean bl2) {
                                Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, indexSetting.getDisplayName() + " checkbox change", "" + bl2 + " " + indexField.name);
                                CrumbySettings.this.callSave();
                                indexAttributeValue.setValue(bl2);
                            }
                        });
                        checkBox.setChecked(((Boolean)t2).booleanValue());
                        view.addView((View)checkBox);
                    } else if (t2 instanceof String || t2 instanceof Character) {
                        // empty if block
                    }
                    ++n3;
                }
            }

            public Object getChild(int n2, int n3) {
                return null;
            }

            public long getChildId(int n2, int n3) {
                return 0;
            }

            public View getChildView(int n2, int n3, boolean bl2, View view, ViewGroup viewGroup) {
                viewGroup = view;
                if (view == null) {
                    viewGroup = View.inflate((Context)CrumbySettings.this.getActivity(), (int)2130903150, (ViewGroup)null);
                }
                this.inflateSetting((View)viewGroup, CrumbySettings.this.settings.get(n2));
                return viewGroup;
            }

            public int getChildrenCount(int n2) {
                return 1;
            }

            public Object getGroup(int n2) {
                return CrumbySettings.this.settings.get(n2);
            }

            public int getGroupCount() {
                return CrumbySettings.this.settings.size();
            }

            public long getGroupId(int n2) {
                return 0;
            }

            public View getGroupView(int n2, boolean bl2, View object, ViewGroup viewGroup) {
                viewGroup = object;
                if (object == null) {
                    viewGroup = View.inflate((Context)CrumbySettings.this.getActivity(), (int)2130903149, (ViewGroup)null);
                }
                object = CrumbySettings.this.settings.get(n2);
                ((TextView)viewGroup.findViewById(2131493099)).setText((CharSequence)object.getDisplayName());
                Picasso.with((Context)CrumbySettings.this.getActivity()).load(object.getFaviconUrl()).into((ImageView)viewGroup.findViewById(2131493167));
                return viewGroup;
            }

            public boolean hasStableIds() {
                return false;
            }

            public boolean isChildSelectable(int n2, int n3) {
                return false;
            }

        });
        bundle.setOnScrollListener(new AbsListView.OnScrollListener(){

            public void onScroll(AbsListView absListView, int n2, int n3, int n4) {
            }

            public void onScrollStateChanged(AbsListView absListView, int n2) {
            }
        });
        return viewGroup;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDestroyView() {
        this.settings.clear();
        super.onDestroyView();
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (this.mustRefreshHome) {
            BusProvider.BUS.get().post(new WebsiteSettingsChangedEvent());
        }
    }

}


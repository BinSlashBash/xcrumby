package com.crumby.impl.crumby;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.crumby.C0065R;
import com.crumby.lib.events.WebsiteSettingsChangedEvent;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.router.IndexAttributeValue;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.IndexSetting;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.squareup.picasso.Picasso;
import com.uservoice.uservoicesdk.UserVoice;
import java.util.List;

public class CrumbySettings extends DialogFragment {
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

    /* renamed from: com.crumby.impl.crumby.CrumbySettings.1 */
    class C00671 implements Runnable {
        C00671() {
        }

        public void run() {
            FragmentRouter.INSTANCE.saveSettings(CrumbySettings.this.getActivity());
        }
    }

    /* renamed from: com.crumby.impl.crumby.CrumbySettings.2 */
    class C00682 implements OnClickListener {
        C00682() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "not interested in voting", null);
            CrumbySettings.this.hideSettingsBottomBar();
        }
    }

    /* renamed from: com.crumby.impl.crumby.CrumbySettings.3 */
    class C00693 implements OnClickListener {
        C00693() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newNavigationEvent("uservoice", "settings vote");
            UserVoice.launchForum(CrumbySettings.this.getActivity());
            CrumbySettings.this.hideSettingsBottomBar();
        }
    }

    /* renamed from: com.crumby.impl.crumby.CrumbySettings.4 */
    class C00704 implements OnClickListener {
        C00704() {
        }

        public void onClick(View v) {
            if (CrumbySettings.this.getDialog() != null) {
                CrumbySettings.this.getDialog().cancel();
            }
        }
    }

    /* renamed from: com.crumby.impl.crumby.CrumbySettings.5 */
    class C00725 extends BaseExpandableListAdapter {

        /* renamed from: com.crumby.impl.crumby.CrumbySettings.5.1 */
        class C00711 implements OnCheckedChangeListener {
            final /* synthetic */ IndexAttributeValue val$attributeValue;
            final /* synthetic */ IndexField val$field;
            final /* synthetic */ IndexSetting val$setting;

            C00711(IndexSetting indexSetting, IndexField indexField, IndexAttributeValue indexAttributeValue) {
                this.val$setting = indexSetting;
                this.val$field = indexField;
                this.val$attributeValue = indexAttributeValue;
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, this.val$setting.getDisplayName() + " checkbox change", isChecked + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.val$field.name);
                CrumbySettings.this.callSave();
                this.val$attributeValue.setValue(Boolean.valueOf(isChecked));
            }
        }

        C00725() {
        }

        public int getGroupCount() {
            return CrumbySettings.this.settings.size();
        }

        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        public Object getGroup(int groupPosition) {
            return CrumbySettings.this.settings.get(groupPosition);
        }

        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        public long getGroupId(int groupPosition) {
            return 0;
        }

        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        public boolean hasStableIds() {
            return false;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(CrumbySettings.this.getActivity(), C0065R.layout.website_setting_group, null);
            }
            IndexSetting setting = (IndexSetting) CrumbySettings.this.settings.get(groupPosition);
            ((TextView) convertView.findViewById(C0065R.id.index_title)).setText(setting.getDisplayName());
            Picasso.with(CrumbySettings.this.getActivity()).load(setting.getFaviconUrl()).into((ImageView) convertView.findViewById(C0065R.id.index_image));
            return convertView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(CrumbySettings.this.getActivity(), C0065R.layout.website_setting_item, null);
            }
            inflateSetting(convertView, (IndexSetting) CrumbySettings.this.settings.get(groupPosition));
            return convertView;
        }

        private void inflateSetting(View convertView, IndexSetting setting) {
            ViewGroup specificSettings = (ViewGroup) convertView.findViewById(C0065R.id.website_specific_setting);
            specificSettings.removeAllViews();
            for (IndexField field : setting.getFields()) {
                IndexAttributeValue attributeValue = setting.getAttributeValue(field.key);
                Object value = attributeValue.getObject();
                if (value instanceof Number) {
                    TextView view = new TextView(CrumbySettings.this.getActivity());
                    view.setText(field.name);
                    new EditText(CrumbySettings.this.getActivity()).setText((String) value);
                    specificSettings.addView(view);
                } else if (value instanceof Boolean) {
                    CheckBox view2 = new CheckBox(CrumbySettings.this.getActivity());
                    view2.setText(field.name);
                    view2.setTextColor(CrumbySettings.this.getResources().getColor(C0065R.color.MenuItemGray));
                    view2.setOnCheckedChangeListener(new C00711(setting, field, attributeValue));
                    view2.setChecked(((Boolean) value).booleanValue());
                    specificSettings.addView(view2);
                } else if (!(value instanceof String) && (value instanceof Character)) {
                }
            }
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    /* renamed from: com.crumby.impl.crumby.CrumbySettings.6 */
    class C00736 implements OnScrollListener {
        C00736() {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    private void callSave() {
        if (!showedChangeSettings) {
            showChangeSettings();
        }
        this.mustRefreshHome = true;
        this.handler.removeCallbacks(this.saveRunnable);
        this.handler.postDelayed(this.saveRunnable, 500);
    }

    private void hideSettingsBottomBar() {
        if (getActivity() != null) {
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(WANTS_TO_VOTE_IDEA, false).commit();
            this.settingsBottomBar.setVisibility(8);
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(1);
        return dialog;
    }

    private void showChangeSettings() {
        showedChangeSettings = true;
        if (getActivity() != null && !PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("crumbyHasVisitedSettings", false)) {
            Toast toast = Toast.makeText(getActivity(), UnsupportedUrlFragment.DISPLAY_NAME, 1);
            toast.setGravity(17, 0, 0);
            TextView toastText = (TextView) View.inflate(getActivity(), C0065R.layout.toast_alert_success, null);
            toastText.setText("Hint: settings are auto-saved!");
            toast.setView(toastText);
            toast.show();
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean("crumbyHasVisitedSettings", true).commit();
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.mustRefreshHome) {
            BusProvider.BUS.get().post(new WebsiteSettingsChangedEvent());
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C0065R.layout.crumby_website_settings, null);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.edit().putBoolean(HomeFragment.WANTS_SETTING_PROMPT, false).commit();
        if (this.settings != null) {
            this.settings.clear();
        }
        if (this.handler == null) {
            this.handler = new Handler();
            this.saveRunnable = new C00671();
        }
        this.settings = FragmentRouter.INSTANCE.getAllIndexSettings();
        this.settingsBottomBar = view.findViewById(C0065R.id.settings_bottom_bar);
        if (!preferences.getBoolean(WANTS_TO_VOTE_IDEA, true)) {
            this.settingsBottomBar.setVisibility(8);
        }
        view.findViewById(C0065R.id.settings_not_interested).setOnClickListener(new C00682());
        view.findViewById(C0065R.id.settings_vote_on_it).setOnClickListener(new C00693());
        ExpandableListView list = (ExpandableListView) view.findViewById(C0065R.id.website_settings_list);
        View headerView = inflater.inflate(C0065R.layout.settings_top, null);
        headerView.findViewById(C0065R.id.done_with_website_settings).setOnClickListener(new C00704());
        list.addHeaderView(headerView);
        list.setAdapter(new C00725());
        list.setOnScrollListener(new C00736());
        return view;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDestroyView() {
        this.settings.clear();
        super.onDestroyView();
    }
}

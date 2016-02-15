package com.crumby.lib.fragment.tertiary;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class TagBlacklist extends DialogFragment {
    private EditText blacklist;
    private Handler handler;
    Runnable saveTagsRunnable;

    /* renamed from: com.crumby.lib.fragment.tertiary.TagBlacklist.1 */
    class C01061 implements Runnable {
        C01061() {
        }

        public void run() {
            if (TagBlacklist.this.getActivity() != null) {
                String[] tags = TagBlacklist.this.blacklist.getText().toString().trim().split("\\s+");
                LinkedHashSet<String> tagSet = new LinkedHashSet();
                for (String tag : tags) {
                    if (!tag.trim().equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                        tagSet.add(tag);
                    }
                }
                if (GalleryViewer.BLACK_LISTED_TAGS == null || !GalleryViewer.BLACK_LISTED_TAGS.equals(tagSet)) {
                    PreferenceManager.getDefaultSharedPreferences(TagBlacklist.this.getActivity()).edit().putStringSet(GalleryViewer.TAG_BLACKLIST_KEY, tagSet).commit();
                    GalleryViewer.BLACK_LISTED_TAGS = tagSet;
                }
            }
        }
    }

    /* renamed from: com.crumby.lib.fragment.tertiary.TagBlacklist.2 */
    class C01072 implements TextWatcher {
        C01072() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            TagBlacklist.this.handler.removeCallbacks(TagBlacklist.this.saveTagsRunnable);
            TagBlacklist.this.handler.postDelayed(TagBlacklist.this.saveTagsRunnable, 500);
        }
    }

    /* renamed from: com.crumby.lib.fragment.tertiary.TagBlacklist.3 */
    class C01083 implements OnClickListener {
        C01083() {
        }

        public void onClick(View v) {
            if (TagBlacklist.this.getDialog() != null) {
                TagBlacklist.this.getDialog().dismiss();
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C0065R.layout.tag_blacklist, null);
        this.handler = new Handler();
        this.saveTagsRunnable = new C01061();
        this.blacklist = (EditText) view.findViewById(C0065R.id.tag_blacklist);
        this.blacklist.addTextChangedListener(new C01072());
        String tagString = UnsupportedUrlFragment.DISPLAY_NAME;
        if (GalleryViewer.BLACK_LISTED_TAGS != null) {
            Iterator i$ = GalleryViewer.BLACK_LISTED_TAGS.iterator();
            while (i$.hasNext()) {
                tagString = tagString + ((String) i$.next()) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
            }
        }
        this.blacklist.setText(tagString);
        view.findViewById(C0065R.id.done_with_website_settings).setOnClickListener(new C01083());
        return view;
    }

    public void onDismiss(DialogInterface dialog) {
        if (this.saveTagsRunnable != null) {
            this.saveTagsRunnable.run();
        }
        super.onDismiss(dialog);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(1);
        return dialog;
    }
}

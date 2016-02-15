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
 *  android.os.Bundle
 *  android.os.Handler
 *  android.preference.PreferenceManager
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.EditText
 */
package com.crumby.lib.fragment.tertiary;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import com.crumby.GalleryViewer;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class TagBlacklist
extends DialogFragment {
    private EditText blacklist;
    private Handler handler;
    Runnable saveTagsRunnable;

    public Dialog onCreateDialog(Bundle bundle) {
        bundle = super.onCreateDialog(bundle);
        bundle.getWindow().requestFeature(1);
        return bundle;
    }

    public View onCreateView(LayoutInflater object, ViewGroup object2, Bundle bundle) {
        bundle = object.inflate(2130903116, null);
        this.handler = new Handler();
        this.saveTagsRunnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                if (TagBlacklist.this.getActivity() == null) {
                    return;
                }
                String[] arrstring = TagBlacklist.this.blacklist.getText().toString().trim().split("\\s+");
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
                for (String string2 : arrstring) {
                    if (string2.trim().equals("")) continue;
                    linkedHashSet.add(string2);
                }
                if (GalleryViewer.BLACK_LISTED_TAGS != null) {
                    if (GalleryViewer.BLACK_LISTED_TAGS.equals(linkedHashSet)) return;
                }
                PreferenceManager.getDefaultSharedPreferences((Context)TagBlacklist.this.getActivity()).edit().putStringSet("crumbyTagBlacklistKey", linkedHashSet).commit();
                GalleryViewer.BLACK_LISTED_TAGS = linkedHashSet;
            }
        };
        this.blacklist = (EditText)bundle.findViewById(2131493114);
        this.blacklist.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                TagBlacklist.this.handler.removeCallbacks(TagBlacklist.this.saveTagsRunnable);
                TagBlacklist.this.handler.postDelayed(TagBlacklist.this.saveTagsRunnable, 500);
            }

            public void beforeTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
            }

            public void onTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
            }
        });
        object2 = object = "";
        if (GalleryViewer.BLACK_LISTED_TAGS != null) {
            Iterator<String> iterator = GalleryViewer.BLACK_LISTED_TAGS.iterator();
            do {
                object2 = object;
                if (!iterator.hasNext()) break;
                object2 = iterator.next();
                object = (String)object + (String)object2 + " ";
            } while (true);
        }
        this.blacklist.setText((CharSequence)object2);
        bundle.findViewById(2131493112).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (TagBlacklist.this.getDialog() != null) {
                    TagBlacklist.this.getDialog().dismiss();
                }
            }
        });
        return bundle;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (this.saveTagsRunnable != null) {
            this.saveTagsRunnable.run();
        }
        super.onDismiss(dialogInterface);
    }

}


/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.ViewGroup
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.Window
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.uservoice.uservoicesdk.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.BaseListActivity;
import com.uservoice.uservoicesdk.flow.InitManager;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;

public abstract class InstantAnswersActivity
extends BaseListActivity {
    private InstantAnswersAdapter adapter;

    protected abstract InstantAnswersAdapter createAdapter();

    protected abstract int getDiscardDialogMessage();

    @Override
    public void onBackPressed() {
        if (this.adapter.hasText()) {
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setTitle(R.string.uv_confirm);
            builder.setMessage(this.getDiscardDialogMessage());
            builder.setPositiveButton(R.string.uv_yes, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    InstantAnswersActivity.this.finish();
                }
            });
            builder.setNegativeButton(R.string.uv_no, null);
            builder.show();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getListView().setDivider(null);
        this.adapter = this.createAdapter();
        this.setListAdapter((ListAdapter)this.adapter);
        this.getListView().setOnHierarchyChangeListener((ViewGroup.OnHierarchyChangeListener)this.adapter);
        this.getListView().setOnItemClickListener((AdapterView.OnItemClickListener)this.adapter);
        this.getListView().setDescendantFocusability(262144);
        new InitManager((Context)this, new Runnable(){

            @Override
            public void run() {
                InstantAnswersActivity.this.onInitialize();
            }
        }).init();
        this.getWindow().setSoftInputMode(36);
    }

    protected void onInitialize() {
        this.adapter.notifyDataSetChanged();
    }

}


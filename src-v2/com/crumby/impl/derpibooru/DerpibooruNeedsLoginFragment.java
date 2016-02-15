/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.AccountManagerCallback
 *  android.accounts.AccountManagerFuture
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.derpibooru;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.GalleryViewer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;

public abstract class DerpibooruNeedsLoginFragment
extends GalleryGridFragment {
    private String user;

    private void deferredInitializeAdapter() {
        super.initializeAdapter();
    }

    @Override
    protected void initializeAdapter() {
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.getViewer().authLoginPromptIfNeeded("derpibooru", new AccountManagerCallback<Bundle>(){

            public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                try {
                    DerpibooruNeedsLoginFragment.this.user = ((Bundle)accountManagerFuture.getResult()).getString("authAccount");
                    DerpibooruNeedsLoginFragment.this.deferredInitializeAdapter();
                    return;
                }
                catch (Exception var1_2) {
                    DerpibooruNeedsLoginFragment.this.getErrorView().show(DisplayError.COULD_NOT_AUTHENTICATE_USER, var1_2.getMessage(), DerpibooruNeedsLoginFragment.this.getUrl());
                    return;
                }
            }
        });
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void scrollToImageInList(int n2) {
        if (this.adapter == null) {
            return;
        }
        super.scrollToImageInList(n2);
    }

}


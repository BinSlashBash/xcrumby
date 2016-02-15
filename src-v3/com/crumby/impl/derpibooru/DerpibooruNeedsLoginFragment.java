package com.crumby.impl.derpibooru;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.widget.firstparty.DisplayError;

public abstract class DerpibooruNeedsLoginFragment extends GalleryGridFragment {
    private String user;

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruNeedsLoginFragment.1 */
    class C00861 implements AccountManagerCallback<Bundle> {
        C00861() {
        }

        public void run(AccountManagerFuture<Bundle> future) {
            try {
                DerpibooruNeedsLoginFragment.this.user = ((Bundle) future.getResult()).getString("authAccount");
                DerpibooruNeedsLoginFragment.this.deferredInitializeAdapter();
            } catch (Exception e) {
                DerpibooruNeedsLoginFragment.this.getErrorView().show(DisplayError.COULD_NOT_AUTHENTICATE_USER, e.getMessage(), DerpibooruNeedsLoginFragment.this.getUrl());
            }
        }
    }

    public void scrollToImageInList(int position) {
        if (this.adapter != null) {
            super.scrollToImageInList(position);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getViewer().authLoginPromptIfNeeded(DerpibooruFragment.BREADCRUMB_NAME, new C00861());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void deferredInitializeAdapter() {
        super.initializeAdapter();
    }

    protected void initializeAdapter() {
    }
}

/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 */
package com.google.android.gms.wallet.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.dynamic.f;
import com.google.android.gms.internal.iz;
import com.google.android.gms.internal.ja;
import com.google.android.gms.internal.jh;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

public final class WalletFragment
extends Fragment {
    private final Fragment Hv;
    private WalletFragmentInitParams acA;
    private MaskedWalletRequest acB;
    private Boolean acC;
    private b acH;
    private final com.google.android.gms.dynamic.b acI;
    private final c acJ;
    private a acK;
    private WalletFragmentOptions acz;
    private boolean mCreated = false;

    public WalletFragment() {
        this.acI = com.google.android.gms.dynamic.b.a(this);
        this.acJ = new c();
        this.acK = new a(this);
        this.Hv = this;
    }

    public static WalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        WalletFragment walletFragment = new WalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", (Parcelable)walletFragmentOptions);
        walletFragment.Hv.setArguments(bundle);
        return walletFragment;
    }

    public int getState() {
        if (this.acH != null) {
            return this.acH.getState();
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.acH != null) {
            this.acH.initialize(walletFragmentInitParams);
            this.acA = null;
            return;
        } else {
            if (this.acA != null) {
                Log.w((String)"WalletFragment", (String)"initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
                return;
            }
            this.acA = walletFragmentInitParams;
            if (this.acB == null) return;
            {
                Log.w((String)"WalletFragment", (String)"updateMaskedWallet() was called before initialize()");
                return;
            }
        }
    }

    public void onActivityResult(int n2, int n3, Intent intent) {
        super.onActivityResult(n2, n3, intent);
        if (this.acH != null) {
            this.acH.onActivityResult(n2, n3, intent);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreate(Bundle bundle) {
        WalletFragmentOptions walletFragmentOptions;
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams)bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.acA != null) {
                    Log.w((String)"WalletFragment", (String)"initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
                }
                this.acA = walletFragmentInitParams;
            }
            if (this.acB == null) {
                this.acB = (MaskedWalletRequest)bundle.getParcelable("maskedWalletRequest");
            }
            if (bundle.containsKey("walletFragmentOptions")) {
                this.acz = (WalletFragmentOptions)bundle.getParcelable("walletFragmentOptions");
            }
            if (bundle.containsKey("enabled")) {
                this.acC = bundle.getBoolean("enabled");
            }
        } else if (this.Hv.getArguments() != null && (walletFragmentOptions = (WalletFragmentOptions)this.Hv.getArguments().getParcelable("extraWalletFragmentOptions")) != null) {
            walletFragmentOptions.I((Context)this.Hv.getActivity());
            this.acz = walletFragmentOptions;
        }
        this.mCreated = true;
        this.acJ.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.acJ.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mCreated = false;
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        if (this.acz == null) {
            this.acz = WalletFragmentOptions.a((Context)activity, attributeSet);
        }
        attributeSet = new Bundle();
        attributeSet.putParcelable("attrKeyWalletFragmentOptions", (Parcelable)this.acz);
        this.acJ.onInflate(activity, (Bundle)attributeSet, bundle);
    }

    public void onPause() {
        super.onPause();
        this.acJ.onPause();
    }

    public void onResume() {
        super.onResume();
        this.acJ.onResume();
        FragmentManager fragmentManager = this.Hv.getActivity().getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("GooglePlayServicesErrorDialog");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable((Context)this.Hv.getActivity()), this.Hv.getActivity(), -1);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.acJ.onSaveInstanceState(bundle);
        if (this.acA != null) {
            bundle.putParcelable("walletFragmentInitParams", (Parcelable)this.acA);
            this.acA = null;
        }
        if (this.acB != null) {
            bundle.putParcelable("maskedWalletRequest", (Parcelable)this.acB);
            this.acB = null;
        }
        if (this.acz != null) {
            bundle.putParcelable("walletFragmentOptions", (Parcelable)this.acz);
            this.acz = null;
        }
        if (this.acC != null) {
            bundle.putBoolean("enabled", this.acC.booleanValue());
            this.acC = null;
        }
    }

    public void onStart() {
        super.onStart();
        this.acJ.onStart();
    }

    public void onStop() {
        super.onStop();
        this.acJ.onStop();
    }

    public void setEnabled(boolean bl2) {
        if (this.acH != null) {
            this.acH.setEnabled(bl2);
            this.acC = null;
            return;
        }
        this.acC = bl2;
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.acK.a(onStateChangedListener);
    }

    public void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        if (this.acH != null) {
            this.acH.updateMaskedWalletRequest(maskedWalletRequest);
            this.acB = null;
            return;
        }
        this.acB = maskedWalletRequest;
    }

    public static interface OnStateChangedListener {
        public void onStateChanged(WalletFragment var1, int var2, int var3, Bundle var4);
    }

    static class a
    extends ja.a {
        private OnStateChangedListener acL;
        private final WalletFragment acM;

        a(WalletFragment walletFragment) {
            this.acM = walletFragment;
        }

        @Override
        public void a(int n2, int n3, Bundle bundle) {
            if (this.acL != null) {
                this.acL.onStateChanged(this.acM, n2, n3, bundle);
            }
        }

        public void a(OnStateChangedListener onStateChangedListener) {
            this.acL = onStateChangedListener;
        }
    }

    private static class b
    implements LifecycleDelegate {
        private final iz acF;

        private b(iz iz2) {
            this.acF = iz2;
        }

        private int getState() {
            try {
                int n2 = this.acF.getState();
                return n2;
            }
            catch (RemoteException var2_2) {
                throw new RuntimeException((Throwable)var2_2);
            }
        }

        private void initialize(WalletFragmentInitParams walletFragmentInitParams) {
            try {
                this.acF.initialize(walletFragmentInitParams);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeException((Throwable)var1_2);
            }
        }

        private void onActivityResult(int n2, int n3, Intent intent) {
            try {
                this.acF.onActivityResult(n2, n3, intent);
                return;
            }
            catch (RemoteException var3_4) {
                throw new RuntimeException((Throwable)var3_4);
            }
        }

        private void setEnabled(boolean bl2) {
            try {
                this.acF.setEnabled(bl2);
                return;
            }
            catch (RemoteException var2_2) {
                throw new RuntimeException((Throwable)var2_2);
            }
        }

        private void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            try {
                this.acF.updateMaskedWalletRequest(maskedWalletRequest);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeException((Throwable)var1_2);
            }
        }

        @Override
        public void onCreate(Bundle bundle) {
            try {
                this.acF.onCreate(bundle);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeException((Throwable)var1_2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                layoutInflater = (View)e.d(this.acF.onCreateView(e.h(layoutInflater), e.h(viewGroup), bundle));
                return layoutInflater;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeException((Throwable)var1_2);
            }
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public void onDestroyView() {
        }

        @Override
        public void onInflate(Activity activity, Bundle object, Bundle bundle) {
            object = (WalletFragmentOptions)object.getParcelable("extraWalletFragmentOptions");
            try {
                this.acF.a(e.h(activity), (WalletFragmentOptions)object, bundle);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeException((Throwable)var1_2);
            }
        }

        @Override
        public void onLowMemory() {
        }

        @Override
        public void onPause() {
            try {
                this.acF.onPause();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeException((Throwable)var1_1);
            }
        }

        @Override
        public void onResume() {
            try {
                this.acF.onResume();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeException((Throwable)var1_1);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.acF.onSaveInstanceState(bundle);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeException((Throwable)var1_2);
            }
        }

        @Override
        public void onStart() {
            try {
                this.acF.onStart();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeException((Throwable)var1_1);
            }
        }

        @Override
        public void onStop() {
            try {
                this.acF.onStop();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeException((Throwable)var1_1);
            }
        }
    }

    private class c
    extends com.google.android.gms.dynamic.a<b>
    implements View.OnClickListener {
        private c() {
        }

        @Override
        protected void a(FrameLayout frameLayout) {
            int n2;
            Button button = new Button((Context)WalletFragment.this.Hv.getActivity());
            button.setText(R.string.wallet_buy_button_place_holder);
            int n3 = -1;
            int n4 = n2 = -2;
            int n5 = n3;
            if (WalletFragment.this.acz != null) {
                WalletFragmentStyle walletFragmentStyle = WalletFragment.this.acz.getFragmentStyle();
                n4 = n2;
                n5 = n3;
                if (walletFragmentStyle != null) {
                    DisplayMetrics displayMetrics = WalletFragment.this.Hv.getResources().getDisplayMetrics();
                    n5 = walletFragmentStyle.a("buyButtonWidth", displayMetrics, -1);
                    n4 = walletFragmentStyle.a("buyButtonHeight", displayMetrics, -2);
                }
            }
            button.setLayoutParams(new ViewGroup.LayoutParams(n5, n4));
            button.setOnClickListener((View.OnClickListener)this);
            frameLayout.addView((View)button);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected void a(f<b> f2) {
            Object object = WalletFragment.this.Hv.getActivity();
            if (WalletFragment.this.acH != null || !WalletFragment.this.mCreated || object == null) return;
            try {
                object = jh.a((Activity)object, WalletFragment.this.acI, WalletFragment.this.acz, WalletFragment.this.acK);
                WalletFragment.this.acH = new b((iz)object);
                WalletFragment.this.acz = null;
                f2.a(WalletFragment.this.acH);
            }
            catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                return;
            }
            if (WalletFragment.this.acA != null) {
                WalletFragment.this.acH.initialize(WalletFragment.this.acA);
                WalletFragment.this.acA = null;
            }
            if (WalletFragment.this.acB != null) {
                WalletFragment.this.acH.updateMaskedWalletRequest(WalletFragment.this.acB);
                WalletFragment.this.acB = null;
            }
            if (WalletFragment.this.acC == null) return;
            WalletFragment.this.acH.setEnabled(WalletFragment.this.acC);
            WalletFragment.this.acC = null;
        }

        public void onClick(View view) {
            view = WalletFragment.this.Hv.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable((Context)view), (Activity)view, -1);
        }
    }

}


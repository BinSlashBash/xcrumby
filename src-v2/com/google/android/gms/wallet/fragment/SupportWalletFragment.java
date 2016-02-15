/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
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
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.google.android.gms.dynamic.h;
import com.google.android.gms.internal.iz;
import com.google.android.gms.internal.ja;
import com.google.android.gms.internal.jh;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

public final class SupportWalletFragment
extends Fragment {
    private final Fragment Hz;
    private WalletFragmentInitParams acA;
    private MaskedWalletRequest acB;
    private Boolean acC;
    private b acv;
    private final h acw;
    private final c acx;
    private a acy;
    private WalletFragmentOptions acz;
    private boolean mCreated = false;

    public SupportWalletFragment() {
        this.acw = h.a(this);
        this.acx = new c();
        this.acy = new a(this);
        this.Hz = this;
    }

    public static SupportWalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        SupportWalletFragment supportWalletFragment = new SupportWalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", (Parcelable)walletFragmentOptions);
        supportWalletFragment.Hz.setArguments(bundle);
        return supportWalletFragment;
    }

    public int getState() {
        if (this.acv != null) {
            return this.acv.getState();
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.acv != null) {
            this.acv.initialize(walletFragmentInitParams);
            this.acA = null;
            return;
        } else {
            if (this.acA != null) {
                Log.w((String)"SupportWalletFragment", (String)"initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
                return;
            }
            this.acA = walletFragmentInitParams;
            if (this.acB == null) return;
            {
                Log.w((String)"SupportWalletFragment", (String)"updateMaskedWallet() was called before initialize()");
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int n2, int n3, Intent intent) {
        super.onActivityResult(n2, n3, intent);
        if (this.acv != null) {
            this.acv.onActivityResult(n2, n3, intent);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onCreate(Bundle bundle) {
        WalletFragmentOptions walletFragmentOptions;
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams)bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.acA != null) {
                    Log.w((String)"SupportWalletFragment", (String)"initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
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
        } else if (this.Hz.getArguments() != null && (walletFragmentOptions = (WalletFragmentOptions)this.Hz.getArguments().getParcelable("extraWalletFragmentOptions")) != null) {
            walletFragmentOptions.I((Context)this.Hz.getActivity());
            this.acz = walletFragmentOptions;
        }
        this.mCreated = true;
        this.acx.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.acx.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mCreated = false;
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        if (this.acz == null) {
            this.acz = WalletFragmentOptions.a((Context)activity, attributeSet);
        }
        attributeSet = new Bundle();
        attributeSet.putParcelable("attrKeyWalletFragmentOptions", (Parcelable)this.acz);
        this.acx.onInflate(activity, (Bundle)attributeSet, bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.acx.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.acx.onResume();
        FragmentManager fragmentManager = this.Hz.getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("GooglePlayServicesErrorDialog");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable((Context)this.Hz.getActivity()), this.Hz.getActivity(), -1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.acx.onSaveInstanceState(bundle);
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

    @Override
    public void onStart() {
        super.onStart();
        this.acx.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.acx.onStop();
    }

    public void setEnabled(boolean bl2) {
        if (this.acv != null) {
            this.acv.setEnabled(bl2);
            this.acC = null;
            return;
        }
        this.acC = bl2;
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.acy.a(onStateChangedListener);
    }

    public void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        if (this.acv != null) {
            this.acv.updateMaskedWalletRequest(maskedWalletRequest);
            this.acB = null;
            return;
        }
        this.acB = maskedWalletRequest;
    }

    public static interface OnStateChangedListener {
        public void onStateChanged(SupportWalletFragment var1, int var2, int var3, Bundle var4);
    }

    static class a
    extends ja.a {
        private OnStateChangedListener acD;
        private final SupportWalletFragment acE;

        a(SupportWalletFragment supportWalletFragment) {
            this.acE = supportWalletFragment;
        }

        @Override
        public void a(int n2, int n3, Bundle bundle) {
            if (this.acD != null) {
                this.acD.onStateChanged(this.acE, n2, n3, bundle);
            }
        }

        public void a(OnStateChangedListener onStateChangedListener) {
            this.acD = onStateChangedListener;
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
            Button button = new Button((Context)SupportWalletFragment.this.Hz.getActivity());
            button.setText(R.string.wallet_buy_button_place_holder);
            int n3 = -1;
            int n4 = n2 = -2;
            int n5 = n3;
            if (SupportWalletFragment.this.acz != null) {
                WalletFragmentStyle walletFragmentStyle = SupportWalletFragment.this.acz.getFragmentStyle();
                n4 = n2;
                n5 = n3;
                if (walletFragmentStyle != null) {
                    DisplayMetrics displayMetrics = SupportWalletFragment.this.Hz.getResources().getDisplayMetrics();
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
            Object object = SupportWalletFragment.this.Hz.getActivity();
            if (SupportWalletFragment.this.acv != null || !SupportWalletFragment.this.mCreated || object == null) return;
            try {
                object = jh.a((Activity)object, SupportWalletFragment.this.acw, SupportWalletFragment.this.acz, SupportWalletFragment.this.acy);
                SupportWalletFragment.this.acv = new b((iz)object);
                SupportWalletFragment.this.acz = null;
                f2.a(SupportWalletFragment.this.acv);
            }
            catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                return;
            }
            if (SupportWalletFragment.this.acA != null) {
                SupportWalletFragment.this.acv.initialize(SupportWalletFragment.this.acA);
                SupportWalletFragment.this.acA = null;
            }
            if (SupportWalletFragment.this.acB != null) {
                SupportWalletFragment.this.acv.updateMaskedWalletRequest(SupportWalletFragment.this.acB);
                SupportWalletFragment.this.acB = null;
            }
            if (SupportWalletFragment.this.acC == null) return;
            SupportWalletFragment.this.acv.setEnabled(SupportWalletFragment.this.acC);
            SupportWalletFragment.this.acC = null;
        }

        public void onClick(View object) {
            object = SupportWalletFragment.this.Hz.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable((Context)object), (Activity)object, -1);
        }
    }

}


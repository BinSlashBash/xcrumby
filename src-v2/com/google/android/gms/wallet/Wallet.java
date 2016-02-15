/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.wallet;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.iu;
import com.google.android.gms.internal.jf;
import com.google.android.gms.internal.jg;
import com.google.android.gms.internal.ji;
import com.google.android.gms.internal.jj;
import com.google.android.gms.internal.ka;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.Payments;
import java.util.Locale;

public final class Wallet {
    public static final Api<WalletOptions> API;
    public static final Payments Payments;
    public static final ka aco;
    public static final iu acp;
    private static final Api.c<jg> wx;
    private static final Api.b<jg, WalletOptions> wy;

    static {
        wx = new Api.c();
        wy = new Api.b<jg, WalletOptions>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public jg a(Context context, Looper looper, fc fc2, WalletOptions walletOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                fq.b(context instanceof Activity, (Object)"An Activity must be used for Wallet APIs");
                context = (Activity)context;
                if (walletOptions != null) {
                    do {
                        return new jg((Activity)context, looper, connectionCallbacks, onConnectionFailedListener, walletOptions.environment, fc2.getAccountName(), walletOptions.theme);
                        break;
                    } while (true);
                }
                walletOptions = new WalletOptions();
                return new jg((Activity)context, looper, connectionCallbacks, onConnectionFailedListener, walletOptions.environment, fc2.getAccountName(), walletOptions.theme);
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };
        API = new Api<WalletOptions>(wy, wx, new Scope[0]);
        Payments = new jf();
        aco = new jj();
        acp = new ji();
    }

    private Wallet() {
    }

    @Deprecated
    public static void changeMaskedWallet(GoogleApiClient googleApiClient, String string2, String string3, int n2) {
        Payments.changeMaskedWallet(googleApiClient, string2, string3, n2);
    }

    @Deprecated
    public static void checkForPreAuthorization(GoogleApiClient googleApiClient, int n2) {
        Payments.checkForPreAuthorization(googleApiClient, n2);
    }

    @Deprecated
    public static void loadFullWallet(GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int n2) {
        Payments.loadFullWallet(googleApiClient, fullWalletRequest, n2);
    }

    @Deprecated
    public static void loadMaskedWallet(GoogleApiClient googleApiClient, MaskedWalletRequest maskedWalletRequest, int n2) {
        Payments.loadMaskedWallet(googleApiClient, maskedWalletRequest, n2);
    }

    @Deprecated
    public static void notifyTransactionStatus(GoogleApiClient googleApiClient, NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        Payments.notifyTransactionStatus(googleApiClient, notifyTransactionStatusRequest);
    }

    public static final class WalletOptions
    implements Api.ApiOptions.HasOptions {
        public final int environment;
        public final int theme;

        private WalletOptions() {
            this(new Builder());
        }

        private WalletOptions(Builder builder) {
            this.environment = builder.acq;
            this.theme = builder.mTheme;
        }

        public static final class Builder {
            private int acq = 0;
            private int mTheme = 0;

            public WalletOptions build() {
                return new WalletOptions(this);
            }

            public Builder setEnvironment(int n2) {
                if (n2 == 0 || n2 == 2 || n2 == 1) {
                    this.acq = n2;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid environment value %d", n2));
            }

            public Builder setTheme(int n2) {
                if (n2 == 0 || n2 == 1) {
                    this.mTheme = n2;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid theme value %d", n2));
            }
        }

    }

    public static abstract class a<R extends Result>
    extends a.b<R, jg> {
        public a() {
            super(wx);
        }
    }

    public static abstract class b
    extends a<Status> {
        @Override
        protected /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        protected Status f(Status status) {
            return status;
        }
    }

}


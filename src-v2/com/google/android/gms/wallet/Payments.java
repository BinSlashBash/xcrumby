/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.wallet;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;

public interface Payments {
    public void changeMaskedWallet(GoogleApiClient var1, String var2, String var3, int var4);

    public void checkForPreAuthorization(GoogleApiClient var1, int var2);

    public void loadFullWallet(GoogleApiClient var1, FullWalletRequest var2, int var3);

    public void loadMaskedWallet(GoogleApiClient var1, MaskedWalletRequest var2, int var3);

    public void notifyTransactionStatus(GoogleApiClient var1, NotifyTransactionStatusRequest var2);
}


package com.google.android.gms.wallet;

import com.google.android.gms.common.api.GoogleApiClient;

public interface Payments {
    void changeMaskedWallet(GoogleApiClient googleApiClient, String str, String str2, int i);

    void checkForPreAuthorization(GoogleApiClient googleApiClient, int i);

    void loadFullWallet(GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int i);

    void loadMaskedWallet(GoogleApiClient googleApiClient, MaskedWalletRequest maskedWalletRequest, int i);

    void notifyTransactionStatus(GoogleApiClient googleApiClient, NotifyTransactionStatusRequest notifyTransactionStatusRequest);
}

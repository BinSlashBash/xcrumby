/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;

public final class AccountPicker {
    private AccountPicker() {
    }

    public static Intent a(Account account, ArrayList<Account> arrayList, String[] arrstring, boolean bl2, String string2, String string3, String[] arrstring2, Bundle bundle, boolean bl3) {
        return AccountPicker.a(account, arrayList, arrstring, bl2, string2, string3, arrstring2, bundle, bl3, 0, 0);
    }

    public static Intent a(Account account, ArrayList<Account> arrayList, String[] arrstring, boolean bl2, String string2, String string3, String[] arrstring2, Bundle bundle, boolean bl3, int n2, int n3) {
        Intent intent = new Intent();
        intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
        intent.setPackage("com.google.android.gms");
        intent.putExtra("allowableAccounts", arrayList);
        intent.putExtra("allowableAccountTypes", arrstring);
        intent.putExtra("addAccountOptions", bundle);
        intent.putExtra("selectedAccount", (Parcelable)account);
        intent.putExtra("alwaysPromptForAccount", bl2);
        intent.putExtra("descriptionTextOverride", string2);
        intent.putExtra("authTokenType", string3);
        intent.putExtra("addAccountRequiredFeatures", arrstring2);
        intent.putExtra("setGmsCoreAccount", bl3);
        intent.putExtra("overrideTheme", n2);
        intent.putExtra("overrideCustomTheme", n3);
        return intent;
    }

    public static Intent newChooseAccountIntent(Account account, ArrayList<Account> arrayList, String[] arrstring, boolean bl2, String string2, String string3, String[] arrstring2, Bundle bundle) {
        return AccountPicker.a(account, arrayList, arrstring, bl2, string2, string3, arrstring2, bundle, false);
    }
}


/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

public enum el {
    wR("Ok"),
    wS("BadAuthentication"),
    wT("InvalidSecondFactor"),
    wU("NotVerified"),
    wV("TermsNotAgreed"),
    wW("Unknown"),
    wX("UNKNOWN_ERR"),
    wY("AccountDeleted"),
    wZ("AccountDisabled"),
    xa("ServiceDisabled"),
    xb("ServiceUnavailable"),
    xc("CaptchaRequired"),
    xd("NetworkError"),
    xe("UserCancel"),
    xf("PermissionDenied"),
    xg("DeviceManagementRequiredOrSyncDisabled"),
    xh("ClientLoginDisabled"),
    xi("NeedPermission"),
    xj("WeakPassword"),
    xk("ALREADY_HAS_GMAIL"),
    xl("BadRequest"),
    xm("BadUsername"),
    xn("LoginFail"),
    xo("NotLoggedIn"),
    xp("NoGmail"),
    xq("RequestDenied"),
    xr("ServerError"),
    xs("UsernameUnavailable"),
    xt("DeletedGmail"),
    xu("SocketTimeout"),
    xv("ExistingUsername"),
    xw("NeedsBrowser"),
    xx("GPlusOther"),
    xy("GPlusNickname"),
    xz("GPlusInvalidChar"),
    xA("GPlusInterstitial"),
    xB("ProfileUpgradeError"),
    xC("INVALID_SCOPE");
    
    public static String xD;
    public static String xE;
    private final String xF;

    static {
        xD = "Error";
        xE = "status";
    }

    private el(String string3) {
        this.xF = string3;
    }
}


package com.google.android.gms.internal;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;

public class eb extends WebChromeClient {
    private final dz lC;

    /* renamed from: com.google.android.gms.internal.eb.1 */
    static class C03691 implements OnCancelListener {
        final /* synthetic */ JsResult rI;

        C03691(JsResult jsResult) {
            this.rI = jsResult;
        }

        public void onCancel(DialogInterface dialog) {
            this.rI.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.eb.2 */
    static class C03702 implements OnClickListener {
        final /* synthetic */ JsResult rI;

        C03702(JsResult jsResult) {
            this.rI = jsResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.rI.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.eb.3 */
    static class C03713 implements OnClickListener {
        final /* synthetic */ JsResult rI;

        C03713(JsResult jsResult) {
            this.rI = jsResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.rI.confirm();
        }
    }

    /* renamed from: com.google.android.gms.internal.eb.4 */
    static class C03724 implements OnCancelListener {
        final /* synthetic */ JsPromptResult rJ;

        C03724(JsPromptResult jsPromptResult) {
            this.rJ = jsPromptResult;
        }

        public void onCancel(DialogInterface dialog) {
            this.rJ.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.eb.5 */
    static class C03735 implements OnClickListener {
        final /* synthetic */ JsPromptResult rJ;

        C03735(JsPromptResult jsPromptResult) {
            this.rJ = jsPromptResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.rJ.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.eb.6 */
    static class C03746 implements OnClickListener {
        final /* synthetic */ JsPromptResult rJ;
        final /* synthetic */ EditText rK;

        C03746(JsPromptResult jsPromptResult, EditText editText) {
            this.rJ = jsPromptResult;
            this.rK = editText;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.rJ.confirm(this.rK.getText().toString());
        }
    }

    /* renamed from: com.google.android.gms.internal.eb.7 */
    static /* synthetic */ class C03757 {
        static final /* synthetic */ int[] rL;

        static {
            rL = new int[MessageLevel.values().length];
            try {
                rL[MessageLevel.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                rL[MessageLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                rL[MessageLevel.LOG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                rL[MessageLevel.TIP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                rL[MessageLevel.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public eb(dz dzVar) {
        this.lC = dzVar;
    }

    private static void m848a(Builder builder, String str, JsResult jsResult) {
        builder.setMessage(str).setPositiveButton(17039370, new C03713(jsResult)).setNegativeButton(17039360, new C03702(jsResult)).setOnCancelListener(new C03691(jsResult)).create().show();
    }

    private static void m849a(Context context, Builder builder, String str, String str2, JsPromptResult jsPromptResult) {
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        View textView = new TextView(context);
        textView.setText(str);
        View editText = new EditText(context);
        editText.setText(str2);
        linearLayout.addView(textView);
        linearLayout.addView(editText);
        builder.setView(linearLayout).setPositiveButton(17039370, new C03746(jsPromptResult, editText)).setNegativeButton(17039360, new C03735(jsPromptResult)).setOnCancelListener(new C03724(jsPromptResult)).create().show();
    }

    protected final void m850a(View view, int i, CustomViewCallback customViewCallback) {
        cc bH = this.lC.bH();
        if (bH == null) {
            dw.m823z("Could not get ad overlay when showing custom view.");
            customViewCallback.onCustomViewHidden();
            return;
        }
        bH.m2985a(view, customViewCallback);
        bH.setRequestedOrientation(i);
    }

    protected boolean m851a(Context context, String str, String str2, String str3, JsResult jsResult, JsPromptResult jsPromptResult, boolean z) {
        Builder builder = new Builder(context);
        builder.setTitle(str);
        if (z) {
            m849a(context, builder, str2, str3, jsPromptResult);
        } else {
            m848a(builder, str2, jsResult);
        }
        return true;
    }

    public final void onCloseWindow(WebView webView) {
        if (webView instanceof dz) {
            cc bH = ((dz) webView).bH();
            if (bH == null) {
                dw.m823z("Tried to close an AdWebView not associated with an overlay.");
                return;
            } else {
                bH.close();
                return;
            }
        }
        dw.m823z("Tried to close a WebView that wasn't an AdWebView.");
    }

    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String str = "JS: " + consoleMessage.message() + " (" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")";
        switch (C03757.rL[consoleMessage.messageLevel().ordinal()]) {
            case Std.STD_FILE /*1*/:
                dw.m820w(str);
                break;
            case Std.STD_URL /*2*/:
                dw.m823z(str);
                break;
            case Std.STD_URI /*3*/:
            case Std.STD_CLASS /*4*/:
                dw.m821x(str);
                break;
            case Std.STD_JAVA_TYPE /*5*/:
                dw.m819v(str);
                break;
            default:
                dw.m821x(str);
                break;
        }
        return super.onConsoleMessage(consoleMessage);
    }

    public final boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebViewTransport webViewTransport = (WebViewTransport) resultMsg.obj;
        WebView webView = new WebView(view.getContext());
        webView.setWebViewClient(this.lC.bI());
        webViewTransport.setWebView(webView);
        resultMsg.sendToTarget();
        return true;
    }

    public final void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, QuotaUpdater quotaUpdater) {
        long j = 5242880 - totalUsedQuota;
        if (j <= 0) {
            quotaUpdater.updateQuota(currentQuota);
            return;
        }
        if (currentQuota == 0) {
            if (estimatedSize > j || estimatedSize > 1048576) {
                estimatedSize = 0;
            }
        } else if (estimatedSize == 0) {
            estimatedSize = Math.min(Math.min(131072, j) + currentQuota, 1048576);
        } else {
            if (estimatedSize <= Math.min(1048576 - currentQuota, j)) {
                currentQuota += estimatedSize;
            }
            estimatedSize = currentQuota;
        }
        quotaUpdater.updateQuota(estimatedSize);
    }

    public final void onHideCustomView() {
        cc bH = this.lC.bH();
        if (bH == null) {
            dw.m823z("Could not get ad overlay when hiding custom view.");
        } else {
            bH.aL();
        }
    }

    public final boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        return m851a(webView.getContext(), url, message, null, result, null, false);
    }

    public final boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
        return m851a(webView.getContext(), url, message, null, result, null, false);
    }

    public final boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
        return m851a(webView.getContext(), url, message, null, result, null, false);
    }

    public final boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
        return m851a(webView.getContext(), url, message, defaultValue, null, result, true);
    }

    public final void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, QuotaUpdater quotaUpdater) {
        long j = 131072 + spaceNeeded;
        if (5242880 - totalUsedQuota < j) {
            quotaUpdater.updateQuota(0);
        } else {
            quotaUpdater.updateQuota(j);
        }
    }

    public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        m850a(view, -1, customViewCallback);
    }
}

package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.webkit.WebView;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.activity.InstantAnswersActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.deflection.Deflection;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint({"ValidFragment", "NewApi"})
public class ArticleDialogFragment extends DialogFragmentBugfixed {
    private final Article article;
    private String deflectingType;
    private WebView webView;

    /* renamed from: com.uservoice.uservoicesdk.dialog.ArticleDialogFragment.1 */
    class C06351 implements OnClickListener {
        C06351() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (ArticleDialogFragment.this.getActivity() instanceof InstantAnswersActivity) {
                Deflection.trackDeflection("unhelpful", ArticleDialogFragment.this.deflectingType, ArticleDialogFragment.this.article);
                ((InstantAnswersAdapter) ((InstantAnswersActivity) ArticleDialogFragment.this.getActivity()).getListAdapter()).notHelpful();
                return;
            }
            new UnhelpfulDialogFragment().show(ArticleDialogFragment.this.getActivity().getSupportFragmentManager(), "UnhelpfulDialogFragment");
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.ArticleDialogFragment.2 */
    class C06362 implements OnClickListener {
        C06362() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Babayaga.track(Event.VOTE_ARTICLE, ArticleDialogFragment.this.article.getId());
            if (ArticleDialogFragment.this.getActivity() instanceof InstantAnswersActivity) {
                Deflection.trackDeflection("helpful", ArticleDialogFragment.this.deflectingType, ArticleDialogFragment.this.article);
                new HelpfulDialogFragment().show(ArticleDialogFragment.this.getActivity().getSupportFragmentManager(), "HelpfulDialogFragment");
            }
        }
    }

    public ArticleDialogFragment(Article article, String deflectingType) {
        this.article = article;
        this.deflectingType = deflectingType;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setTitle(C0621R.string.uv_article_instant_answer_question);
        this.webView = new WebView(getActivity());
        if (!Utils.isDarkTheme(getActivity())) {
            builder.setInverseBackgroundForced(true);
        }
        builder.setView(this.webView);
        Utils.displayArticle(this.webView, this.article, getActivity());
        builder.setNegativeButton(C0621R.string.uv_no, new C06351());
        builder.setPositiveButton(C0621R.string.uv_very_yes, new C06362());
        Babayaga.track(Event.VIEW_ARTICLE, this.article.getId());
        return builder.create();
    }

    public void onDismiss(DialogInterface dialog) {
        this.webView.onPause();
        this.webView.loadUrl("about:blank");
        super.onDismiss(dialog);
    }
}

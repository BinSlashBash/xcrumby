/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 *  android.view.View
 *  android.webkit.WebView
 *  android.widget.ListAdapter
 */
package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListAdapter;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.InstantAnswersActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.deflection.Deflection;
import com.uservoice.uservoicesdk.dialog.DialogFragmentBugfixed;
import com.uservoice.uservoicesdk.dialog.HelpfulDialogFragment;
import com.uservoice.uservoicesdk.dialog.UnhelpfulDialogFragment;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint(value={"ValidFragment", "NewApi"})
public class ArticleDialogFragment
extends DialogFragmentBugfixed {
    private final Article article;
    private String deflectingType;
    private WebView webView;

    public ArticleDialogFragment(Article article, String string2) {
        this.article = article;
        this.deflectingType = string2;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = new AlertDialog.Builder((Context)this.getActivity());
        bundle.setTitle(R.string.uv_article_instant_answer_question);
        this.webView = new WebView((Context)this.getActivity());
        if (!Utils.isDarkTheme((Context)this.getActivity())) {
            bundle.setInverseBackgroundForced(true);
        }
        bundle.setView((View)this.webView);
        Utils.displayArticle(this.webView, this.article, (Context)this.getActivity());
        bundle.setNegativeButton(R.string.uv_no, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                if (ArticleDialogFragment.this.getActivity() instanceof InstantAnswersActivity) {
                    Deflection.trackDeflection("unhelpful", ArticleDialogFragment.this.deflectingType, ArticleDialogFragment.this.article);
                    ((InstantAnswersAdapter)((InstantAnswersActivity)ArticleDialogFragment.this.getActivity()).getListAdapter()).notHelpful();
                    return;
                }
                new UnhelpfulDialogFragment().show(ArticleDialogFragment.this.getActivity().getSupportFragmentManager(), "UnhelpfulDialogFragment");
            }
        });
        bundle.setPositiveButton(R.string.uv_very_yes, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                Babayaga.track(Babayaga.Event.VOTE_ARTICLE, ArticleDialogFragment.this.article.getId());
                if (ArticleDialogFragment.this.getActivity() instanceof InstantAnswersActivity) {
                    Deflection.trackDeflection("helpful", ArticleDialogFragment.this.deflectingType, ArticleDialogFragment.this.article);
                    new HelpfulDialogFragment().show(ArticleDialogFragment.this.getActivity().getSupportFragmentManager(), "HelpfulDialogFragment");
                }
            }
        });
        Babayaga.track(Babayaga.Event.VIEW_ARTICLE, this.article.getId());
        return bundle.create();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        this.webView.onPause();
        this.webView.loadUrl("about:blank");
        super.onDismiss(dialogInterface);
    }

}


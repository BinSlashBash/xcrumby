/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.uservoice.uservoicesdk.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.activity.InstantAnswersActivity;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;
import com.uservoice.uservoicesdk.ui.PostIdeaAdapter;

public class PostIdeaActivity
extends InstantAnswersActivity {
    @Override
    protected InstantAnswersAdapter createAdapter() {
        return new PostIdeaAdapter(this);
    }

    @Override
    protected int getDiscardDialogMessage() {
        return R.string.uv_msg_confirm_discard_idea;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(R.string.uv_idea_form_title);
    }

    @Override
    protected void onInitialize() {
        if (Session.getInstance().getForum() != null) {
            super.onInitialize();
            return;
        }
        Forum.loadForum(Session.getInstance().getConfig().getForumId(), new DefaultCallback<Forum>((Context)this){

            @Override
            public void onModel(Forum forum) {
                Session.getInstance().setForum(forum);
                PostIdeaActivity.this.onInitialize();
            }
        });
    }

}


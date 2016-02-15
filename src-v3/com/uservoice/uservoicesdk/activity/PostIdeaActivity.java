package com.uservoice.uservoicesdk.activity;

import android.content.Context;
import android.os.Bundle;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;
import com.uservoice.uservoicesdk.ui.PostIdeaAdapter;

public class PostIdeaActivity extends InstantAnswersActivity {

    /* renamed from: com.uservoice.uservoicesdk.activity.PostIdeaActivity.1 */
    class C14321 extends DefaultCallback<Forum> {
        C14321(Context x0) {
            super(x0);
        }

        public void onModel(Forum model) {
            Session.getInstance().setForum(model);
            super.onInitialize();
        }
    }

    protected void onInitialize() {
        if (Session.getInstance().getForum() != null) {
            super.onInitialize();
        } else {
            Forum.loadForum(Session.getInstance().getConfig().getForumId(), new C14321(this));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(C0621R.string.uv_idea_form_title);
    }

    protected InstantAnswersAdapter createAdapter() {
        return new PostIdeaAdapter(this);
    }

    protected int getDiscardDialogMessage() {
        return C0621R.string.uv_msg_confirm_discard_idea;
    }
}

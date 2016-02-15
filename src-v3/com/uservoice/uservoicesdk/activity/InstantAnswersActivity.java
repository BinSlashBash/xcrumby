package com.uservoice.uservoicesdk.activity;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.flow.InitManager;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;

public abstract class InstantAnswersActivity extends BaseListActivity {
    private InstantAnswersAdapter adapter;

    /* renamed from: com.uservoice.uservoicesdk.activity.InstantAnswersActivity.1 */
    class C06291 implements Runnable {
        C06291() {
        }

        public void run() {
            InstantAnswersActivity.this.onInitialize();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.InstantAnswersActivity.2 */
    class C06302 implements OnClickListener {
        C06302() {
        }

        public void onClick(DialogInterface dialog, int which) {
            InstantAnswersActivity.this.finish();
        }
    }

    protected abstract InstantAnswersAdapter createAdapter();

    protected abstract int getDiscardDialogMessage();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setDivider(null);
        this.adapter = createAdapter();
        setListAdapter(this.adapter);
        getListView().setOnHierarchyChangeListener(this.adapter);
        getListView().setOnItemClickListener(this.adapter);
        getListView().setDescendantFocusability(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START);
        new InitManager(this, new C06291()).init();
        getWindow().setSoftInputMode(36);
    }

    protected void onInitialize() {
        this.adapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        if (this.adapter.hasText()) {
            Builder builder = new Builder(this);
            builder.setTitle(C0621R.string.uv_confirm);
            builder.setMessage(getDiscardDialogMessage());
            builder.setPositiveButton(C0621R.string.uv_yes, new C06302());
            builder.setNegativeButton(C0621R.string.uv_no, null);
            builder.show();
            return;
        }
        super.onBackPressed();
    }
}

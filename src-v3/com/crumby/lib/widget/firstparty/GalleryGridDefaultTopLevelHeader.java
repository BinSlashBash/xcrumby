package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.lib.events.OmniformEvent;
import com.crumby.lib.widget.thirdparty.HorizontalFlowLayout;

public class GalleryGridDefaultTopLevelHeader extends HorizontalFlowLayout {
    private View openLogin;
    private View openSearch;

    /* renamed from: com.crumby.lib.widget.firstparty.GalleryGridDefaultTopLevelHeader.1 */
    class C01251 implements OnClickListener {
        C01251() {
        }

        public void onClick(View v) {
            BusProvider.BUS.get().post(new OmniformEvent(OmniformEvent.LOGIN));
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.GalleryGridDefaultTopLevelHeader.2 */
    class C01262 implements OnClickListener {
        C01262() {
        }

        public void onClick(View v) {
            BusProvider.BUS.get().post(new OmniformEvent(OmniformEvent.SEARCH));
        }
    }

    public GalleryGridDefaultTopLevelHeader(Context context) {
        super(context);
    }

    public GalleryGridDefaultTopLevelHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryGridDefaultTopLevelHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.openLogin = findViewById(C0065R.id.jump_to_login);
        this.openLogin.setOnClickListener(new C01251());
        this.openSearch = findViewById(C0065R.id.jump_to_search);
        this.openSearch.setOnClickListener(new C01262());
    }
}

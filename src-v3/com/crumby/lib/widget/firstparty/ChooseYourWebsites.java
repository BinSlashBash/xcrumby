package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.crumby.C0065R;
import com.crumby.lib.router.FragmentRouter;
import java.util.List;

public class ChooseYourWebsites extends GridView {

    /* renamed from: com.crumby.lib.widget.firstparty.ChooseYourWebsites.1 */
    class C01211 extends BaseAdapter {
        final /* synthetic */ List val$indexSettingList;

        C01211(List list) {
            this.val$indexSettingList = list;
        }

        public int getCount() {
            return this.val$indexSettingList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ChooseYourWebsites.this.getContext(), C0065R.layout.choose_website_item, null);
            }
            return null;
        }
    }

    public ChooseYourWebsites(Context context) {
        super(context);
    }

    public ChooseYourWebsites(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseYourWebsites(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        setAdapter(new C01211(FragmentRouter.INSTANCE.getAllIndexSettings()));
    }
}

package com.crumby.lib.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryClickHandler;
import com.crumby.lib.fragment.ImageClickListener;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import twowayview.TwoWayView;
import twowayview.TwoWayView.Orientation;

public class HomeGalleryList extends LinearLayout implements GalleryList, GalleryClickHandler {
    private GalleryListAdapter adapter;
    private Button button;
    private boolean clicked;
    private boolean doNotStart;
    private ErrorView errorView;
    private boolean fetchOnVisible;
    private GalleryImage image;
    private TwoWayView list;
    private MultiSelect multiselect;
    private GalleryProducer producer;
    private View progress;

    /* renamed from: com.crumby.lib.widget.HomeGalleryList.1 */
    class C01171 implements OnClickListener {
        final /* synthetic */ GalleryImage val$image;

        C01171(GalleryImage galleryImage) {
            this.val$image = galleryImage;
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.MISCELLANEOUS, "horizontal list image click", this.val$image.getLinkUrl());
            BusProvider.BUS.get().post(new UrlChangeEvent(this.val$image.getLinkUrl()));
        }
    }

    public HomeGalleryList(Context context) {
        super(context);
    }

    public HomeGalleryList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeGalleryList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(GalleryImage image, GalleryProducer producer, MultiSelect multiSelect, boolean startFetching) {
        if (!this.doNotStart) {
            this.list = (TwoWayView) findViewById(C0065R.id.home_horizontal_list);
            this.list.setOrientation(Orientation.HORIZONTAL);
            this.button = (Button) findViewById(C0065R.id.website_button);
            if (image.hasIcon()) {
                this.button.setCompoundDrawablesWithIntrinsicBounds(image.getIcon(), 0, 0, 0);
            }
            if (image.getLinkUrl() == null) {
                this.button.setEnabled(false);
                this.button.setBackgroundDrawable(null);
            } else {
                this.button.setOnClickListener(new C01171(image));
            }
            this.button.setText(image.getTitle());
            this.button.setVisibility(0);
            this.errorView = (ErrorView) findViewById(C0065R.id.error_view);
            this.progress = findViewById(C0065R.id.loading_list);
            this.image = image;
            this.producer = producer;
            this.adapter = new GalleryListAdapter();
            this.adapter.initialize(this);
            if (startFetching) {
                this.adapter.startFetchingAndThenFinish();
            }
            this.list.setAdapter(this.adapter);
            this.list.setOnItemClickListener(new ImageClickListener(this, multiSelect, "horizontal"));
        }
    }

    public AdapterView getList() {
        return this.list;
    }

    public GalleryProducer getProducer() {
        return this.producer;
    }

    public GalleryImage getImage() {
        return this.image;
    }

    public Bundle getArguments() {
        return null;
    }

    protected void onDetachedFromWindow() {
        if (this.producer != null) {
            this.producer.removeConsumer(this.adapter);
            if (this.list != null) {
                this.list.setAdapter(null);
            }
        }
        super.onDetachedFromWindow();
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public boolean getUserVisibleHint() {
        return isShown();
    }

    public void indicateProgressChange(float completed) {
        if (completed == GalleryViewer.PROGRESS_COMPLETED) {
            this.progress.setVisibility(8);
        } else {
            this.progress.setVisibility(0);
        }
    }

    public void showError(DisplayError error, String reason, String url) {
        if (this.errorView != null) {
            this.errorView.show(error, reason, url);
        }
    }

    public void goToImage(View view, GalleryImage image, int i) {
        if (!this.clicked) {
            this.clicked = true;
            this.producer.shareAndSetCurrentImageFocus(image.getPosition());
            BusProvider.BUS.get().post(new UrlChangeEvent(image.getLinkUrl(), this.producer));
        }
    }

    public void start() {
        this.adapter.startFetchingAndThenFinish();
    }

    public void cancel() {
        this.doNotStart = true;
        if (this.producer != null) {
            this.producer.haltDownload();
        }
    }
}

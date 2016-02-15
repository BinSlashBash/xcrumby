package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.fragment.adapter.ImageWrapper;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class HomeHorizontalList extends LinearLayout implements GalleryConsumer, OnClickListener {
    private GalleryImage image;
    private GalleryProducer producer;

    /* renamed from: com.crumby.lib.widget.firstparty.HomeHorizontalList.1 */
    class C01281 implements OnClickListener {
        C01281() {
        }

        public void onClick(View view) {
            BusProvider.BUS.get().post(new UrlChangeEvent(HomeHorizontalList.this.image.getLinkUrl()));
        }
    }

    public HomeHorizontalList(Context context) {
        super(context);
    }

    public HomeHorizontalList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeHorizontalList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void showError(Exception e) {
        clearLoading();
        View errorView = View.inflate(getContext(), C0065R.layout.home_horizontal_list_error, null);
        super.addView(errorView);
        addViewGrow(errorView, 0);
        ((TextView) findViewById(C0065R.id.error_message)).setText(e.getMessage());
        int maxWidth = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        errorView.measure(0, 0);
        if (errorView.getMeasuredWidth() > maxWidth) {
            errorView.setLayoutParams(new LayoutParams(maxWidth, -2));
        }
    }

    public void finishLoading() {
    }

    private void clearLoading() {
    }

    public void addViewGrow(View child, long time) {
        child.setVisibility(0);
        child.setScaleX(0.0f);
        child.setScaleY(0.0f);
        child.animate().scaleX(GalleryViewer.PROGRESS_COMPLETED).scaleY(GalleryViewer.PROGRESS_COMPLETED).setStartDelay(time).setDuration(400);
    }

    public boolean addImages(List<GalleryImage> images) {
        clearLoading();
        for (int i = 0; i < Math.min(images.size(), 7); i++) {
            View view = getChildAt(i + 1);
            GridImageWrapper wrapper = new GridImageWrapper(view, false);
            wrapper.clear();
            wrapper.set((GalleryImage) images.get(i));
            view.setTag(wrapper);
            view.setOnClickListener(this);
            int width = GalleryListFragment.THUMBNAIL_HEIGHT;
            Picasso.with(getContext()).load(wrapper.getImage().getThumbnailUrl()).resize(width, width).centerCrop().into(wrapper.getImageView());
            addViewGrow(view, (long) (i * 400));
        }
        images.add(this.image);
        Button button = new Button(getContext());
        button.setText("See more...");
        button.setOnClickListener(new C01281());
        addView(button);
        return false;
    }

    public void notifyDataSetChanged() {
    }

    public void setProducer(String url, GalleryProducer producer) {
        this.producer = producer;
        this.image = new GalleryImage(url);
        List<String> path = new ArrayList();
        path.add(FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(url).getBreadcrumbName());
        this.image.setPath(path);
        producer.initialize(this, this.image, null, true);
    }

    public void onClick(View view) {
        GalleryImage image = ((ImageWrapper) view.getTag()).getImage();
        this.producer.shareAndSetCurrentImageFocus(image.getPosition());
        BusProvider.BUS.get().post(new UrlChangeEvent(image.getLinkUrl(), this.producer));
    }
}

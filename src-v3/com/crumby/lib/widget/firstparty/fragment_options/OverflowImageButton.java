package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.InsertFragmentLinkIntoDatabaseEvent;
import java.lang.reflect.Field;

public class OverflowImageButton extends ImageButton implements OnClickListener {
    private GalleryImage image;
    private String url;

    /* renamed from: com.crumby.lib.widget.firstparty.fragment_options.OverflowImageButton.1 */
    class C01311 implements OnMenuItemClickListener {
        C01311() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == C0065R.id.overflow_share_image) {
                OverflowImageButton.this.share();
            } else if (item.getItemId() == C0065R.id.overflow_open_image_in_web) {
                OverflowImageButton.this.openImageInWeb();
            }
            return true;
        }
    }

    public OverflowImageButton(Context context) {
        super(context);
    }

    public OverflowImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverflowImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(String url) {
        this.url = url;
        setOnClickListener(this);
        setVisibility(0);
    }

    public void initialize(GalleryImage image) {
        this.image = image;
        setOnClickListener(this);
    }

    private void share() {
        Intent sharingIntent = new Intent("android.intent.action.SEND");
        sharingIntent.setType("text/plain");
        String shareBody = UnsupportedUrlFragment.DISPLAY_NAME;
        String subject = "Image found with crumby!";
        if (this.image != null && this.image.getTitle() != null) {
            shareBody = this.image.getLinkUrl();
            subject = this.image.getTitle();
            this.url = this.image.getLinkUrl();
        } else if (this.url != null && !this.url.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            shareBody = this.url;
        } else {
            return;
        }
        shareBody = shareBody + ". Found with Crumby, Universal Image Browser [http://tinyurl.com/getcrumby]";
        sharingIntent.putExtra("android.intent.extra.SUBJECT", subject);
        sharingIntent.putExtra("android.intent.extra.TEXT", shareBody);
        Analytics.INSTANCE.newEvent(AnalyticsCategories.SHARING, "image share", this.url);
        getContext().startActivity(Intent.createChooser(sharingIntent, "Share \"" + this.url + "\" via"));
    }

    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(getContext(), this);
        popup.getMenuInflater().inflate(C0065R.menu.overflow_image_popup, popup.getMenu());
        Field[] arr$ = popup.getClass().getDeclaredFields();
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            Field field = arr$[i$];
            try {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class.forName(menuPopupHelper.getClass().getName()).getMethod("setForceShowIcon", new Class[]{Boolean.TYPE}).invoke(menuPopupHelper, new Object[]{Boolean.valueOf(true)});
                    break;
                }
                i$++;
            } catch (Exception e) {
            }
        }
        popup.setOnMenuItemClickListener(new C01311());
        popup.show();
    }

    private void favoriteImage() {
        String thumbnailUrl = null;
        if (this.image != null) {
            thumbnailUrl = this.image.getThumbnailUrl();
        }
        BusProvider.BUS.get().post(new InsertFragmentLinkIntoDatabaseEvent(this.url, thumbnailUrl));
    }

    private void openImageInWeb() {
        String url = (this.image == null || this.image.getLinkUrl() == null) ? this.url : this.image.getLinkUrl();
        openWebBrowser(url, getContext());
    }

    public static void openWebBrowser(String url, Context context) {
        if (url != null) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "open in web", url);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }
}

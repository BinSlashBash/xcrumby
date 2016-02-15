package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.download.ImageDownloadListener;
import com.crumby.lib.download.ImageDownloadManager;
import java.util.ArrayList;

public class DownloadIndicator extends TextView implements ImageDownloadListener {

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.DownloadIndicator.1 */
    class C01351 implements Runnable {
        C01351() {
        }

        public void run() {
            DownloadIndicator.this.setCount(ImageDownloadManager.INSTANCE.getDownloadingCount());
        }
    }

    public DownloadIndicator(Context context) {
        super(context);
    }

    public DownloadIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DownloadIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void update(ImageDownload download) {
        updateCounter();
    }

    private void updateCounter() {
        post(new C01351());
    }

    private void setCount(int count) {
        setText(count + UnsupportedUrlFragment.DISPLAY_NAME);
        setAlpha(GalleryViewer.PROGRESS_COMPLETED);
        clearAnimation();
        if (count <= 0) {
            setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(C0065R.drawable.ic_action_accept), null, null, null);
            setText(null);
            animate().alpha(0.0f).setStartDelay(300).setDuration(1000);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        invalidate();
    }

    public void update(ArrayList<ImageDownload> arrayList) {
        updateCounter();
    }

    public void terminate() {
        setCount(0);
    }
}

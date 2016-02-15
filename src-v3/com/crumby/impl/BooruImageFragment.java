package com.crumby.impl;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.widget.firstparty.ImageCommentsView;

public abstract class BooruImageFragment extends GalleryImageFragment implements OnClickListener {
    private ImageCommentsView comments;
    private TextView description;
    private ViewGroup mainTags;
    private View title;

    /* renamed from: com.crumby.impl.BooruImageFragment.1 */
    class C00661 implements Runnable {
        C00661() {
        }

        public void run() {
            try {
                if (BooruImageFragment.this.getActivity() != null) {
                    if (BooruImageFragment.this.getImage().getDescription() != null) {
                        BooruImageFragment.this.description.setText(BooruImageFragment.this.getImage().getDescription());
                        BooruImageFragment.this.description.setVisibility(0);
                    }
                    BooruImageFragment.this.comments.initialize(BooruImageFragment.this.getImage().getComments());
                    BooruImageFragment.this.addTags();
                    BooruImageFragment.this.alternateFillMetadata();
                }
            } catch (ClassCastException e) {
                BooruImageFragment.this.indicateMetadataError("Could not load tags: ", e);
            } catch (NullPointerException e2) {
                BooruImageFragment.this.indicateMetadataError("Could not load tags: ", e2);
            }
        }
    }

    protected abstract String getTagUrl(String str);

    protected int getBooruLayout() {
        return C0065R.layout.booru_image_metadata;
    }

    protected ViewGroup inflateMetadataLayout(LayoutInflater inflater) {
        ViewGroup view = (ViewGroup) inflater.inflate(getBooruLayout(), null);
        this.mainTags = (ViewGroup) view.findViewById(C0065R.id.booru_tags);
        this.title = view.findViewById(C0065R.id.booru_tags_title);
        this.comments = (ImageCommentsView) view.findViewById(C0065R.id.comments_view);
        this.description = (TextView) view.findViewById(C0065R.id.booru_description);
        this.description.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

    private OnClickListener getThis() {
        return this;
    }

    protected void addTags(View title, ViewGroup tagContainer, String[] tags) {
        CrDb.logTime("booru image fragment", "tags", true);
        int i = 0;
        int tagWidth = (int) getResources().getDimension(C0065R.dimen.maximum_tag_width);
        for (String tag : tags) {
            if (!(tag == null || tag.trim().equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
                Button button = new Button(tagContainer.getContext());
                button.setMaxWidth(tagWidth);
                tagContainer.addView(button);
                button.setText(tag);
                button.setOnClickListener(getThis());
                i++;
            }
        }
        if (i == 0) {
            title.setVisibility(8);
            tagContainer.setVisibility(8);
        }
        CrDb.logTime("booru image fragment", "tags", false);
    }

    protected void addTags() {
        addTags(this.title, this.mainTags, getImage().getTags());
    }

    protected void alternateFillMetadata() {
    }

    protected void fillMetadataView() {
        this.mainTags.postDelayed(new C00661(), 400);
    }

    public void onClick(View v) {
        String tag = ((Button) v).getText().toString();
        Analytics.INSTANCE.newNavigationEvent("booru metadata click", tag);
        postUrlChangeToBus(getTagUrl(tag), null);
    }
}

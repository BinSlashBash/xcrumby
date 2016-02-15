package com.crumby.impl.device;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DeviceFragment extends GalleryGridFragment {
    public static final String BASE_URL = "/";
    public static final String BREADCRUMB_NAME = "/";
    public static final String REGEX_BASE = "/";
    public static final String REGEX_URL = "/.*";
    public static final String ROOT_NAME = "device";
    List<String> crumbs;
    File dir;
    private List<GalleryImage> images;

    class ImageFileFilter implements FileFilter {
        File file;
        private final String[] okFileExtensions;

        public ImageFileFilter(File newfile) {
            this.okFileExtensions = new String[]{"jpg", "png", "gif", "jpeg"};
            this.file = newfile;
        }

        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            for (String extension : this.okFileExtensions) {
                if (file.getName().toLowerCase().endsWith(extension)) {
                    return true;
                }
            }
            return false;
        }
    }

    class SpecificFileFilter implements FileFilter {
        File file;
        String name;

        public SpecificFileFilter(File newfile, String name) {
            this.file = newfile;
            this.name = name;
        }

        public boolean accept(File file) {
            if (file.getName().equals(this.name)) {
                return true;
            }
            return false;
        }
    }

    /* renamed from: com.crumby.impl.device.DeviceFragment.1 */
    class C12751 extends SingleGalleryProducer {
        C12751() {
        }

        protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) {
            for (File file : DeviceFragment.this.dir.listFiles(new ImageFileFilter(DeviceFragment.this.dir))) {
                GalleryImage image = new GalleryImage(file.getName(), file.getAbsolutePath(), file.getName());
                if (file.isDirectory()) {
                    image.setLinksToGallery(true);
                }
                galleryImages.add(image);
            }
        }
    }

    private void appendToBreadcrumbs(List<Breadcrumb> breadcrumbs, FragmentIndex index, int last, String crumb, String url) {
        Breadcrumb breadcrumb = new Breadcrumb(getActivity().getBaseContext(), index, last, url);
        breadcrumb.setBreadcrumbText(crumb);
        breadcrumbs.add(breadcrumb);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Breadcrumb> breadcrumbs = new LinkedList();
        String url = getUrl();
        String[] paths = url.split(REGEX_BASE);
        this.dir = Environment.getRootDirectory();
        FragmentIndex index = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(url);
        String crumbs = UnsupportedUrlFragment.DISPLAY_NAME;
        appendToBreadcrumbs(breadcrumbs, index, 1, "system", REGEX_BASE);
        String[] arr$ = paths;
        int len$ = arr$.length;
        int i$ = 0;
        String crumbs2 = crumbs;
        int i = 0;
        while (i$ < len$) {
            int i2;
            String path = arr$[i$];
            crumbs2 = crumbs2 + path + REGEX_BASE;
            if (path.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                i2 = i;
            } else {
                i2 = i + 1;
                if (i < 1) {
                    continue;
                } else {
                    try {
                        this.dir = this.dir.listFiles(new SpecificFileFilter(this.dir, path))[0];
                        appendToBreadcrumbs(breadcrumbs, index, 1, path, crumbs2);
                        i2++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
            i$++;
            i = i2;
        }
        overrideBreadcrumbs(breadcrumbs);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void cleanLinkUrl() {
    }

    protected GalleryProducer createProducer() {
        return new C12751();
    }
}

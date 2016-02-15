/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Environment
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.device;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DeviceFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "/";
    public static final String BREADCRUMB_NAME = "/";
    public static final String REGEX_BASE = "/";
    public static final String REGEX_URL = "/.*";
    public static final String ROOT_NAME = "device";
    List<String> crumbs;
    File dir;
    private List<GalleryImage> images;

    private void appendToBreadcrumbs(List<Breadcrumb> list, FragmentIndex object, int n2, String string2, String string3) {
        object = new Breadcrumb(this.getActivity().getBaseContext(), (FragmentIndex)object, n2, string3);
        object.setBreadcrumbText(string2);
        list.add((Breadcrumb)((Object)object));
    }

    @Override
    protected void cleanLinkUrl() {
    }

    @Override
    protected GalleryProducer createProducer() {
        return new SingleGalleryProducer(){

            @Override
            protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) {
                for (File file : DeviceFragment.this.dir.listFiles(new ImageFileFilter(DeviceFragment.this.dir))) {
                    GalleryImage galleryImage = new GalleryImage(file.getName(), file.getAbsolutePath(), file.getName());
                    if (file.isDirectory()) {
                        galleryImage.setLinksToGallery(true);
                    }
                    arrayList.add(galleryImage);
                }
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LinkedList<Breadcrumb> linkedList = new LinkedList<Breadcrumb>();
        String string2 = this.getUrl();
        String[] arrstring = string2.split("/");
        this.dir = Environment.getRootDirectory();
        FragmentIndex fragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(string2);
        this.appendToBreadcrumbs(linkedList, fragmentIndex, 1, "system", "/");
        int n2 = arrstring.length;
        string2 = "";
        int n3 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            String string3 = arrstring[i2];
            string2 = string2 + string3 + "/";
            if (string3.equals("")) continue;
            int n4 = n3 + 1;
            if (n3 < 1) {
                n3 = n4;
                continue;
            }
            try {
                this.dir = this.dir.listFiles(new SpecificFileFilter(this.dir, string3))[0];
            }
            catch (ArrayIndexOutOfBoundsException var8_6) {
                // empty catch block
                break;
            }
            this.appendToBreadcrumbs(linkedList, fragmentIndex, 1, string3, string2);
            n3 = n4 + 1;
            continue;
        }
        this.overrideBreadcrumbs(linkedList);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    class ImageFileFilter
    implements FileFilter {
        File file;
        private final String[] okFileExtensions;

        public ImageFileFilter(File file) {
            this.okFileExtensions = new String[]{"jpg", "png", "gif", "jpeg"};
            this.file = file;
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            String[] arrstring = this.okFileExtensions;
            int n2 = arrstring.length;
            int n3 = 0;
            while (n3 < n2) {
                String string2 = arrstring[n3];
                if (file.getName().toLowerCase().endsWith(string2)) return true;
                ++n3;
            }
            return false;
        }
    }

    class SpecificFileFilter
    implements FileFilter {
        File file;
        String name;

        public SpecificFileFilter(File file, String string2) {
            this.file = file;
            this.name = string2;
        }

        @Override
        public boolean accept(File file) {
            if (file.getName().equals(this.name)) {
                return true;
            }
            return false;
        }
    }

}


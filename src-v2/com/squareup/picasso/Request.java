/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.net.Uri
 */
package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.Uri;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Request {
    private static final long TOO_LONG_LOG = TimeUnit.SECONDS.toNanos(5);
    public final boolean centerCrop;
    public final boolean centerInside;
    public final Bitmap.Config config;
    public final boolean hasRotationPivot;
    int id;
    public final int resourceId;
    public final float rotationDegrees;
    public final float rotationPivotX;
    public final float rotationPivotY;
    long started;
    public final int targetHeight;
    public final int targetWidth;
    public final List<Transformation> transformations;
    public final Uri uri;

    /*
     * Enabled aggressive block sorting
     */
    private Request(Uri uri, int n2, List<Transformation> list, int n3, int n4, boolean bl2, boolean bl3, float f2, float f3, float f4, boolean bl4, Bitmap.Config config) {
        this.uri = uri;
        this.resourceId = n2;
        this.transformations = list == null ? null : Collections.unmodifiableList(list);
        this.targetWidth = n3;
        this.targetHeight = n4;
        this.centerCrop = bl2;
        this.centerInside = bl3;
        this.rotationDegrees = f2;
        this.rotationPivotX = f3;
        this.rotationPivotY = f4;
        this.hasRotationPivot = bl4;
        this.config = config;
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    String getName() {
        if (this.uri != null) {
            return String.valueOf(this.uri.getPath());
        }
        return Integer.toHexString(this.resourceId);
    }

    boolean hasCustomTransformations() {
        if (this.transformations != null) {
            return true;
        }
        return false;
    }

    public boolean hasSize() {
        if (this.targetWidth != 0) {
            return true;
        }
        return false;
    }

    String logId() {
        long l2 = System.nanoTime() - this.started;
        if (l2 > TOO_LONG_LOG) {
            return this.plainId() + '+' + TimeUnit.NANOSECONDS.toSeconds(l2) + 's';
        }
        return this.plainId() + '+' + TimeUnit.NANOSECONDS.toMillis(l2) + "ms";
    }

    boolean needsMatrixTransform() {
        if (this.targetWidth != 0 || this.rotationDegrees != 0.0f) {
            return true;
        }
        return false;
    }

    boolean needsTransformation() {
        if (this.needsMatrixTransform() || this.hasCustomTransformations()) {
            return true;
        }
        return false;
    }

    String plainId() {
        return "[R" + this.id + ']';
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Request{");
        if (this.resourceId > 0) {
            stringBuilder.append(this.resourceId);
        } else {
            stringBuilder.append((Object)this.uri);
        }
        if (this.transformations != null && !this.transformations.isEmpty()) {
            for (Transformation transformation : this.transformations) {
                stringBuilder.append(' ').append(transformation.key());
            }
        }
        if (this.targetWidth > 0) {
            stringBuilder.append(" resize(").append(this.targetWidth).append(',').append(this.targetHeight).append(')');
        }
        if (this.centerCrop) {
            stringBuilder.append(" centerCrop");
        }
        if (this.centerInside) {
            stringBuilder.append(" centerInside");
        }
        if (this.rotationDegrees != 0.0f) {
            stringBuilder.append(" rotation(").append(this.rotationDegrees);
            if (this.hasRotationPivot) {
                stringBuilder.append(" @ ").append(this.rotationPivotX).append(',').append(this.rotationPivotY);
            }
            stringBuilder.append(')');
        }
        if (this.config != null) {
            stringBuilder.append(' ').append((Object)this.config);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static final class Builder {
        private boolean centerCrop;
        private boolean centerInside;
        private Bitmap.Config config;
        private boolean hasRotationPivot;
        private int resourceId;
        private float rotationDegrees;
        private float rotationPivotX;
        private float rotationPivotY;
        private int targetHeight;
        private int targetWidth;
        private List<Transformation> transformations;
        private Uri uri;

        public Builder(int n2) {
            this.setResourceId(n2);
        }

        public Builder(Uri uri) {
            this.setUri(uri);
        }

        Builder(Uri uri, int n2) {
            this.uri = uri;
            this.resourceId = n2;
        }

        private Builder(Request request) {
            this.uri = request.uri;
            this.resourceId = request.resourceId;
            this.targetWidth = request.targetWidth;
            this.targetHeight = request.targetHeight;
            this.centerCrop = request.centerCrop;
            this.centerInside = request.centerInside;
            this.rotationDegrees = request.rotationDegrees;
            this.rotationPivotX = request.rotationPivotX;
            this.rotationPivotY = request.rotationPivotY;
            this.hasRotationPivot = request.hasRotationPivot;
            if (request.transformations != null) {
                this.transformations = new ArrayList<Transformation>(request.transformations);
            }
            this.config = request.config;
        }

        public Request build() {
            if (this.centerInside && this.centerCrop) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            }
            if (this.centerCrop && this.targetWidth == 0) {
                throw new IllegalStateException("Center crop requires calling resize.");
            }
            if (this.centerInside && this.targetWidth == 0) {
                throw new IllegalStateException("Center inside requires calling resize.");
            }
            return new Request(this.uri, this.resourceId, this.transformations, this.targetWidth, this.targetHeight, this.centerCrop, this.centerInside, this.rotationDegrees, this.rotationPivotX, this.rotationPivotY, this.hasRotationPivot, this.config);
        }

        public Builder centerCrop() {
            if (this.centerInside) {
                throw new IllegalStateException("Center crop can not be used after calling centerInside");
            }
            this.centerCrop = true;
            return this;
        }

        public Builder centerInside() {
            if (this.centerCrop) {
                throw new IllegalStateException("Center inside can not be used after calling centerCrop");
            }
            this.centerInside = true;
            return this;
        }

        public Builder clearCenterCrop() {
            this.centerCrop = false;
            return this;
        }

        public Builder clearCenterInside() {
            this.centerInside = false;
            return this;
        }

        public Builder clearResize() {
            this.targetWidth = 0;
            this.targetHeight = 0;
            this.centerCrop = false;
            this.centerInside = false;
            return this;
        }

        public Builder clearRotation() {
            this.rotationDegrees = 0.0f;
            this.rotationPivotX = 0.0f;
            this.rotationPivotY = 0.0f;
            this.hasRotationPivot = false;
            return this;
        }

        public Builder config(Bitmap.Config config) {
            this.config = config;
            return this;
        }

        boolean hasImage() {
            if (this.uri != null || this.resourceId != 0) {
                return true;
            }
            return false;
        }

        boolean hasSize() {
            if (this.targetWidth != 0) {
                return true;
            }
            return false;
        }

        public Builder resize(int n2, int n3) {
            if (n2 <= 0) {
                throw new IllegalArgumentException("Width must be positive number.");
            }
            if (n3 <= 0) {
                throw new IllegalArgumentException("Height must be positive number.");
            }
            this.targetWidth = n2;
            this.targetHeight = n3;
            return this;
        }

        public Builder rotate(float f2) {
            this.rotationDegrees = f2;
            return this;
        }

        public Builder rotate(float f2, float f3, float f4) {
            this.rotationDegrees = f2;
            this.rotationPivotX = f3;
            this.rotationPivotY = f4;
            this.hasRotationPivot = true;
            return this;
        }

        public Builder setResourceId(int n2) {
            if (n2 == 0) {
                throw new IllegalArgumentException("Image resource ID may not be 0.");
            }
            this.resourceId = n2;
            this.uri = null;
            return this;
        }

        public Builder setUri(Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("Image URI may not be null.");
            }
            this.uri = uri;
            this.resourceId = 0;
            return this;
        }

        public Builder transform(Transformation transformation) {
            if (transformation == null) {
                throw new IllegalArgumentException("Transformation must not be null.");
            }
            if (this.transformations == null) {
                this.transformations = new ArrayList<Transformation>(2);
            }
            this.transformations.add(transformation);
            return this;
        }
    }

}


/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.appwidget.AppWidgetManager
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.widget.RemoteViews
 */
package com.squareup.picasso;

import android.app.Notification;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;
import com.squareup.picasso.Action;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Utils;

abstract class RemoteViewsAction
extends Action<RemoteViewsTarget> {
    final RemoteViews remoteViews;
    final int viewId;

    RemoteViewsAction(Picasso picasso, Request request, RemoteViews remoteViews, int n2, int n3, boolean bl2, String string2) {
        super(picasso, new RemoteViewsTarget(remoteViews, n2), request, bl2, false, n3, null, string2);
        this.remoteViews = remoteViews;
        this.viewId = n2;
    }

    @Override
    void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        this.remoteViews.setImageViewBitmap(this.viewId, bitmap);
        this.update();
    }

    @Override
    public void error(Exception exception) {
        if (this.errorResId != 0) {
            this.setImageResource(this.errorResId);
        }
    }

    void setImageResource(int n2) {
        this.remoteViews.setImageViewResource(this.viewId, n2);
        this.update();
    }

    abstract void update();

    static class AppWidgetAction
    extends RemoteViewsAction {
        private final int[] appWidgetIds;

        AppWidgetAction(Picasso picasso, Request request, RemoteViews remoteViews, int n2, int[] arrn, boolean bl2, int n3, String string2) {
            super(picasso, request, remoteViews, n2, n3, bl2, string2);
            this.appWidgetIds = arrn;
        }

        @Override
        void update() {
            AppWidgetManager.getInstance((Context)this.picasso.context).updateAppWidget(this.appWidgetIds, this.remoteViews);
        }
    }

    static class NotificationAction
    extends RemoteViewsAction {
        private final Notification notification;
        private final int notificationId;

        NotificationAction(Picasso picasso, Request request, RemoteViews remoteViews, int n2, int n3, Notification notification, boolean bl2, int n4, String string2) {
            super(picasso, request, remoteViews, n2, n4, bl2, string2);
            this.notificationId = n3;
            this.notification = notification;
        }

        @Override
        void update() {
            ((NotificationManager)Utils.getService(this.picasso.context, "notification")).notify(this.notificationId, this.notification);
        }
    }

    static class RemoteViewsTarget {
        final RemoteViews remoteViews;
        final int viewId;

        RemoteViewsTarget(RemoteViews remoteViews, int n2) {
            this.remoteViews = remoteViews;
            this.viewId = n2;
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) return false;
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (RemoteViewsTarget)object;
            if (this.viewId != object.viewId) return false;
            if (this.remoteViews.equals((Object)object.remoteViews)) return true;
            return false;
        }

        public int hashCode() {
            return this.remoteViews.hashCode() * 31 + this.viewId;
        }
    }

}


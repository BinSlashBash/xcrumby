/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RemoteViews;

class NotificationCompatHoneycomb {
    NotificationCompatHoneycomb() {
    }

    /*
     * Enabled aggressive block sorting
     */
    static Notification add(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n2, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap) {
        context = new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
        boolean bl2 = (notification.flags & 2) != 0;
        context = context.setOngoing(bl2);
        bl2 = (notification.flags & 8) != 0;
        context = context.setOnlyAlertOnce(bl2);
        bl2 = (notification.flags & 16) != 0;
        context = context.setAutoCancel(bl2).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent);
        if ((notification.flags & 128) != 0) {
            bl2 = true;
            return context.setFullScreenIntent(pendingIntent2, bl2).setLargeIcon(bitmap).setNumber(n2).getNotification();
        }
        bl2 = false;
        return context.setFullScreenIntent(pendingIntent2, bl2).setLargeIcon(bitmap).setNumber(n2).getNotification();
    }
}


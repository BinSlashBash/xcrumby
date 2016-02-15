/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$BigPictureStyle
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$InboxStyle
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
import java.util.ArrayList;
import java.util.Iterator;

class NotificationCompatJellybean {
    private Notification.Builder b;

    /*
     * Enabled aggressive block sorting
     */
    public NotificationCompatJellybean(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n2, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n3, int n4, boolean bl2, boolean bl3, int n5, CharSequence charSequence4) {
        context = new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
        boolean bl4 = (notification.flags & 2) != 0;
        context = context.setOngoing(bl4);
        bl4 = (notification.flags & 8) != 0;
        context = context.setOnlyAlertOnce(bl4);
        bl4 = (notification.flags & 16) != 0;
        context = context.setAutoCancel(bl4).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent);
        bl4 = (notification.flags & 128) != 0;
        this.b = context.setFullScreenIntent(pendingIntent2, bl4).setLargeIcon(bitmap).setNumber(n2).setUsesChronometer(bl3).setPriority(n5).setProgress(n3, n4, bl2);
    }

    public void addAction(int n2, CharSequence charSequence, PendingIntent pendingIntent) {
        this.b.addAction(n2, charSequence, pendingIntent);
    }

    public void addBigPictureStyle(CharSequence charSequence, boolean bl2, CharSequence charSequence2, Bitmap bitmap, Bitmap bitmap2, boolean bl3) {
        charSequence = new Notification.BigPictureStyle(this.b).setBigContentTitle(charSequence).bigPicture(bitmap);
        if (bl3) {
            charSequence.bigLargeIcon(bitmap2);
        }
        if (bl2) {
            charSequence.setSummaryText(charSequence2);
        }
    }

    public void addBigTextStyle(CharSequence charSequence, boolean bl2, CharSequence charSequence2, CharSequence charSequence3) {
        charSequence = new Notification.BigTextStyle(this.b).setBigContentTitle(charSequence).bigText(charSequence3);
        if (bl2) {
            charSequence.setSummaryText(charSequence2);
        }
    }

    public void addInboxStyle(CharSequence charSequence, boolean bl2, CharSequence object, ArrayList<CharSequence> arrayList) {
        charSequence = new Notification.InboxStyle(this.b).setBigContentTitle(charSequence);
        if (bl2) {
            charSequence.setSummaryText((CharSequence)object);
        }
        object = arrayList.iterator();
        while (object.hasNext()) {
            charSequence.addLine((CharSequence)object.next());
        }
    }

    public Notification build() {
        return this.b.build();
    }
}


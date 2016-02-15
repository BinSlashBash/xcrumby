/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.media.MediaPlayer$OnErrorListener
 *  android.media.MediaPlayer$OnPreparedListener
 *  android.os.Handler
 *  android.text.TextUtils
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.MediaController
 *  android.widget.VideoView
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dz;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public final class cg
extends FrameLayout
implements MediaPlayer.OnCompletionListener,
MediaPlayer.OnErrorListener,
MediaPlayer.OnPreparedListener {
    private final dz lC;
    private final MediaController os;
    private final a ot;
    private final VideoView ou;
    private long ov;
    private String ow;

    public cg(Context context, dz dz2) {
        super(context);
        this.lC = dz2;
        this.ou = new VideoView(context);
        dz2 = new FrameLayout.LayoutParams(-1, -1, 17);
        this.addView((View)this.ou, (ViewGroup.LayoutParams)dz2);
        this.os = new MediaController(context);
        this.ot = new a(this);
        this.ot.aW();
        this.ou.setOnCompletionListener((MediaPlayer.OnCompletionListener)this);
        this.ou.setOnPreparedListener((MediaPlayer.OnPreparedListener)this);
        this.ou.setOnErrorListener((MediaPlayer.OnErrorListener)this);
    }

    private static void a(dz dz2, String string2) {
        cg.a(dz2, string2, new HashMap<String, String>(1));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void a(dz dz2, String string2, String string3) {
        boolean bl2 = string3 == null;
        int n2 = bl2 ? 2 : 3;
        HashMap<String, String> hashMap = new HashMap<String, String>(n2);
        hashMap.put("what", string2);
        if (!bl2) {
            hashMap.put("extra", string3);
        }
        cg.a(dz2, "error", hashMap);
    }

    private static void a(dz dz2, String string2, String string3, String string4) {
        HashMap<String, String> hashMap = new HashMap<String, String>(2);
        hashMap.put(string3, string4);
        cg.a(dz2, string2, hashMap);
    }

    private static void a(dz dz2, String string2, Map<String, String> map) {
        map.put("event", string2);
        dz2.a("onVideoEvent", map);
    }

    public void aU() {
        if (!TextUtils.isEmpty((CharSequence)this.ow)) {
            this.ou.setVideoPath(this.ow);
            return;
        }
        cg.a(this.lC, "no_src", null);
    }

    public void aV() {
        long l2 = this.ou.getCurrentPosition();
        if (this.ov != l2) {
            float f2 = (float)l2 / 1000.0f;
            cg.a(this.lC, "timeupdate", "time", String.valueOf(f2));
            this.ov = l2;
        }
    }

    public void b(MotionEvent motionEvent) {
        this.ou.dispatchTouchEvent(motionEvent);
    }

    public void destroy() {
        this.ot.cancel();
        this.ou.stopPlayback();
    }

    public void k(boolean bl2) {
        if (bl2) {
            this.ou.setMediaController(this.os);
            return;
        }
        this.os.hide();
        this.ou.setMediaController(null);
    }

    public void o(String string2) {
        this.ow = string2;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        cg.a(this.lC, "ended");
    }

    public boolean onError(MediaPlayer mediaPlayer, int n2, int n3) {
        cg.a(this.lC, String.valueOf(n2), String.valueOf(n3));
        return true;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        float f2 = (float)this.ou.getDuration() / 1000.0f;
        cg.a(this.lC, "canplaythrough", "duration", String.valueOf(f2));
    }

    public void pause() {
        this.ou.pause();
    }

    public void play() {
        this.ou.start();
    }

    public void seekTo(int n2) {
        this.ou.seekTo(n2);
    }

    private static final class a {
        private final Runnable kW;
        private volatile boolean ox = false;

        public a(final cg cg2) {
            this.kW = new Runnable(){
                private final WeakReference<cg> oy;

                @Override
                public void run() {
                    cg cg22 = this.oy.get();
                    if (!a.this.ox && cg22 != null) {
                        cg22.aV();
                        a.this.aW();
                    }
                }
            };
        }

        public void aW() {
            dv.rp.postDelayed(this.kW, 250);
        }

        public void cancel() {
            this.ox = true;
            dv.rp.removeCallbacks(this.kW);
        }

    }

}


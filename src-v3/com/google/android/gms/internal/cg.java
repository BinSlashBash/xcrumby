package com.google.android.gms.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.VideoView;
import com.google.android.gms.tagmanager.DataLayer;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public final class cg extends FrameLayout implements OnCompletionListener, OnErrorListener, OnPreparedListener {
    private final dz lC;
    private final MediaController os;
    private final C0344a ot;
    private final VideoView ou;
    private long ov;
    private String ow;

    /* renamed from: com.google.android.gms.internal.cg.a */
    private static final class C0344a {
        private final Runnable kW;
        private volatile boolean ox;

        /* renamed from: com.google.android.gms.internal.cg.a.1 */
        class C03431 implements Runnable {
            final /* synthetic */ C0344a oA;
            private final WeakReference<cg> oy;
            final /* synthetic */ cg oz;

            C03431(C0344a c0344a, cg cgVar) {
                this.oA = c0344a;
                this.oz = cgVar;
                this.oy = new WeakReference(this.oz);
            }

            public void run() {
                cg cgVar = (cg) this.oy.get();
                if (!this.oA.ox && cgVar != null) {
                    cgVar.aV();
                    this.oA.aW();
                }
            }
        }

        public C0344a(cg cgVar) {
            this.ox = false;
            this.kW = new C03431(this, cgVar);
        }

        public void aW() {
            dv.rp.postDelayed(this.kW, 250);
        }

        public void cancel() {
            this.ox = true;
            dv.rp.removeCallbacks(this.kW);
        }
    }

    public cg(Context context, dz dzVar) {
        super(context);
        this.lC = dzVar;
        this.ou = new VideoView(context);
        addView(this.ou, new LayoutParams(-1, -1, 17));
        this.os = new MediaController(context);
        this.ot = new C0344a(this);
        this.ot.aW();
        this.ou.setOnCompletionListener(this);
        this.ou.setOnPreparedListener(this);
        this.ou.setOnErrorListener(this);
    }

    private static void m688a(dz dzVar, String str) {
        m691a(dzVar, str, new HashMap(1));
    }

    public static void m689a(dz dzVar, String str, String str2) {
        Object obj = str2 == null ? 1 : null;
        Map hashMap = new HashMap(obj != null ? 2 : 3);
        hashMap.put("what", str);
        if (obj == null) {
            hashMap.put("extra", str2);
        }
        m691a(dzVar, "error", hashMap);
    }

    private static void m690a(dz dzVar, String str, String str2, String str3) {
        Map hashMap = new HashMap(2);
        hashMap.put(str2, str3);
        m691a(dzVar, str, hashMap);
    }

    private static void m691a(dz dzVar, String str, Map<String, String> map) {
        map.put(DataLayer.EVENT_KEY, str);
        dzVar.m832a("onVideoEvent", (Map) map);
    }

    public void aU() {
        if (TextUtils.isEmpty(this.ow)) {
            m689a(this.lC, "no_src", null);
        } else {
            this.ou.setVideoPath(this.ow);
        }
    }

    public void aV() {
        long currentPosition = (long) this.ou.getCurrentPosition();
        if (this.ov != currentPosition) {
            m690a(this.lC, "timeupdate", "time", String.valueOf(((float) currentPosition) / 1000.0f));
            this.ov = currentPosition;
        }
    }

    public void m692b(MotionEvent motionEvent) {
        this.ou.dispatchTouchEvent(motionEvent);
    }

    public void destroy() {
        this.ot.cancel();
        this.ou.stopPlayback();
    }

    public void m693k(boolean z) {
        if (z) {
            this.ou.setMediaController(this.os);
            return;
        }
        this.os.hide();
        this.ou.setMediaController(null);
    }

    public void m694o(String str) {
        this.ow = str;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        m688a(this.lC, "ended");
    }

    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        m689a(this.lC, String.valueOf(what), String.valueOf(extra));
        return true;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        m690a(this.lC, "canplaythrough", "duration", String.valueOf(((float) this.ou.getDuration()) / 1000.0f));
    }

    public void pause() {
        this.ou.pause();
    }

    public void play() {
        this.ou.start();
    }

    public void seekTo(int timeInMilliseconds) {
        this.ou.seekTo(timeInMilliseconds);
    }
}

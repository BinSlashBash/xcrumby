package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tagmanager.DataLayer.C0486b;
import com.google.android.gms.tagmanager.cd.C0501a;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager aay;
    private final DataLayer WK;
    private final C0529r Zg;
    private final C0491a aaw;
    private final ConcurrentMap<C1417n, Boolean> aax;
    private final Context mContext;

    /* renamed from: com.google.android.gms.tagmanager.TagManager.3 */
    static /* synthetic */ class C04903 {
        static final /* synthetic */ int[] aaA;

        static {
            aaA = new int[C0501a.values().length];
            try {
                aaA[C0501a.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                aaA[C0501a.CONTAINER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                aaA[C0501a.CONTAINER_DEBUG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.TagManager.a */
    interface C0491a {
        C1418o m1351a(Context context, TagManager tagManager, Looper looper, String str, int i, C0529r c0529r);
    }

    /* renamed from: com.google.android.gms.tagmanager.TagManager.1 */
    class C10581 implements C0486b {
        final /* synthetic */ TagManager aaz;

        C10581(TagManager tagManager) {
            this.aaz = tagManager;
        }

        public void m2442y(Map<String, Object> map) {
            Object obj = map.get(DataLayer.EVENT_KEY);
            if (obj != null) {
                this.aaz.bT(obj.toString());
            }
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.TagManager.2 */
    static class C10592 implements C0491a {
        C10592() {
        }

        public C1418o m2443a(Context context, TagManager tagManager, Looper looper, String str, int i, C0529r c0529r) {
            return new C1418o(context, tagManager, looper, str, i, c0529r);
        }
    }

    TagManager(Context context, C0491a containerHolderLoaderProvider, DataLayer dataLayer) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.aaw = containerHolderLoaderProvider;
        this.aax = new ConcurrentHashMap();
        this.WK = dataLayer;
        this.WK.m1345a(new C10581(this));
        this.WK.m1345a(new C1072d(this.mContext));
        this.Zg = new C0529r();
    }

    private void bT(String str) {
        for (C1417n bp : this.aax.keySet()) {
            bp.bp(str);
        }
    }

    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (aay == null) {
                if (context == null) {
                    bh.m1384w("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                aay = new TagManager(context, new C10592(), new DataLayer(new C1091v(context)));
            }
            tagManager = aay;
        }
        return tagManager;
    }

    void m1353a(C1417n c1417n) {
        this.aax.put(c1417n, Boolean.valueOf(true));
    }

    boolean m1354b(C1417n c1417n) {
        return this.aax.remove(c1417n) != null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    synchronized boolean m1355g(android.net.Uri r6) {
        /*
        r5 = this;
        monitor-enter(r5);
        r1 = com.google.android.gms.tagmanager.cd.kT();	 Catch:{ all -> 0x0049 }
        r0 = r1.m1400g(r6);	 Catch:{ all -> 0x0049 }
        if (r0 == 0) goto L_0x0085;
    L_0x000b:
        r2 = r1.getContainerId();	 Catch:{ all -> 0x0049 }
        r0 = com.google.android.gms.tagmanager.TagManager.C04903.aaA;	 Catch:{ all -> 0x0049 }
        r3 = r1.kU();	 Catch:{ all -> 0x0049 }
        r3 = r3.ordinal();	 Catch:{ all -> 0x0049 }
        r0 = r0[r3];	 Catch:{ all -> 0x0049 }
        switch(r0) {
            case 1: goto L_0x0021;
            case 2: goto L_0x004c;
            case 3: goto L_0x004c;
            default: goto L_0x001e;
        };
    L_0x001e:
        r0 = 1;
    L_0x001f:
        monitor-exit(r5);
        return r0;
    L_0x0021:
        r0 = r5.aax;	 Catch:{ all -> 0x0049 }
        r0 = r0.keySet();	 Catch:{ all -> 0x0049 }
        r1 = r0.iterator();	 Catch:{ all -> 0x0049 }
    L_0x002b:
        r0 = r1.hasNext();	 Catch:{ all -> 0x0049 }
        if (r0 == 0) goto L_0x001e;
    L_0x0031:
        r0 = r1.next();	 Catch:{ all -> 0x0049 }
        r0 = (com.google.android.gms.tagmanager.C1417n) r0;	 Catch:{ all -> 0x0049 }
        r3 = r0.getContainerId();	 Catch:{ all -> 0x0049 }
        r3 = r3.equals(r2);	 Catch:{ all -> 0x0049 }
        if (r3 == 0) goto L_0x002b;
    L_0x0041:
        r3 = 0;
        r0.br(r3);	 Catch:{ all -> 0x0049 }
        r0.refresh();	 Catch:{ all -> 0x0049 }
        goto L_0x002b;
    L_0x0049:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
    L_0x004c:
        r0 = r5.aax;	 Catch:{ all -> 0x0049 }
        r0 = r0.keySet();	 Catch:{ all -> 0x0049 }
        r3 = r0.iterator();	 Catch:{ all -> 0x0049 }
    L_0x0056:
        r0 = r3.hasNext();	 Catch:{ all -> 0x0049 }
        if (r0 == 0) goto L_0x001e;
    L_0x005c:
        r0 = r3.next();	 Catch:{ all -> 0x0049 }
        r0 = (com.google.android.gms.tagmanager.C1417n) r0;	 Catch:{ all -> 0x0049 }
        r4 = r0.getContainerId();	 Catch:{ all -> 0x0049 }
        r4 = r4.equals(r2);	 Catch:{ all -> 0x0049 }
        if (r4 == 0) goto L_0x0077;
    L_0x006c:
        r4 = r1.kV();	 Catch:{ all -> 0x0049 }
        r0.br(r4);	 Catch:{ all -> 0x0049 }
        r0.refresh();	 Catch:{ all -> 0x0049 }
        goto L_0x0056;
    L_0x0077:
        r4 = r0.ke();	 Catch:{ all -> 0x0049 }
        if (r4 == 0) goto L_0x0056;
    L_0x007d:
        r4 = 0;
        r0.br(r4);	 Catch:{ all -> 0x0049 }
        r0.refresh();	 Catch:{ all -> 0x0049 }
        goto L_0x0056;
    L_0x0085:
        r0 = 0;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.TagManager.g(android.net.Uri):boolean");
    }

    public DataLayer getDataLayer() {
        return this.WK;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String containerId, int defaultContainerResourceId) {
        PendingResult a = this.aaw.m1351a(this.mContext, this, null, containerId, defaultContainerResourceId, this.Zg);
        a.kh();
        return a;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String containerId, int defaultContainerResourceId, Handler handler) {
        PendingResult a = this.aaw.m1351a(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.Zg);
        a.kh();
        return a;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String containerId, int defaultContainerResourceId) {
        PendingResult a = this.aaw.m1351a(this.mContext, this, null, containerId, defaultContainerResourceId, this.Zg);
        a.kj();
        return a;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String containerId, int defaultContainerResourceId, Handler handler) {
        PendingResult a = this.aaw.m1351a(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.Zg);
        a.kj();
        return a;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String containerId, int defaultContainerResourceId) {
        PendingResult a = this.aaw.m1351a(this.mContext, this, null, containerId, defaultContainerResourceId, this.Zg);
        a.ki();
        return a;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String containerId, int defaultContainerResourceId, Handler handler) {
        PendingResult a = this.aaw.m1351a(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.Zg);
        a.ki();
        return a;
    }

    public void setVerboseLoggingEnabled(boolean enableVerboseLogging) {
        bh.setLogLevel(enableVerboseLogging ? 2 : 5);
    }
}

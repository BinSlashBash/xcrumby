package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.Iterator;
import java.util.LinkedList;

/* renamed from: com.google.android.gms.dynamic.a */
public abstract class C0304a<T extends LifecycleDelegate> {
    private T Hj;
    private Bundle Hk;
    private LinkedList<C0303a> Hl;
    private final C0307f<T> Hm;

    /* renamed from: com.google.android.gms.dynamic.a.5 */
    static class C03025 implements OnClickListener {
        final /* synthetic */ int Hu;
        final /* synthetic */ Context pB;

        C03025(Context context, int i) {
            this.pB = context;
            this.Hu = i;
        }

        public void onClick(View v) {
            this.pB.startActivity(GooglePlayServicesUtil.m109b(this.pB, this.Hu));
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.a */
    private interface C0303a {
        void m346b(LifecycleDelegate lifecycleDelegate);

        int getState();
    }

    /* renamed from: com.google.android.gms.dynamic.a.1 */
    class C08111 implements C0307f<T> {
        final /* synthetic */ C0304a Hn;

        C08111(C0304a c0304a) {
            this.Hn = c0304a;
        }

        public void m1746a(T t) {
            this.Hn.Hj = t;
            Iterator it = this.Hn.Hl.iterator();
            while (it.hasNext()) {
                ((C0303a) it.next()).m346b(this.Hn.Hj);
            }
            this.Hn.Hl.clear();
            this.Hn.Hk = null;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.2 */
    class C08122 implements C0303a {
        final /* synthetic */ C0304a Hn;
        final /* synthetic */ Activity Ho;
        final /* synthetic */ Bundle Hp;
        final /* synthetic */ Bundle Hq;

        C08122(C0304a c0304a, Activity activity, Bundle bundle, Bundle bundle2) {
            this.Hn = c0304a;
            this.Ho = activity;
            this.Hp = bundle;
            this.Hq = bundle2;
        }

        public void m1747b(LifecycleDelegate lifecycleDelegate) {
            this.Hn.Hj.onInflate(this.Ho, this.Hp, this.Hq);
        }

        public int getState() {
            return 0;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.3 */
    class C08133 implements C0303a {
        final /* synthetic */ C0304a Hn;
        final /* synthetic */ Bundle Hq;

        C08133(C0304a c0304a, Bundle bundle) {
            this.Hn = c0304a;
            this.Hq = bundle;
        }

        public void m1748b(LifecycleDelegate lifecycleDelegate) {
            this.Hn.Hj.onCreate(this.Hq);
        }

        public int getState() {
            return 1;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.4 */
    class C08144 implements C0303a {
        final /* synthetic */ C0304a Hn;
        final /* synthetic */ Bundle Hq;
        final /* synthetic */ FrameLayout Hr;
        final /* synthetic */ LayoutInflater Hs;
        final /* synthetic */ ViewGroup Ht;

        C08144(C0304a c0304a, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            this.Hn = c0304a;
            this.Hr = frameLayout;
            this.Hs = layoutInflater;
            this.Ht = viewGroup;
            this.Hq = bundle;
        }

        public void m1749b(LifecycleDelegate lifecycleDelegate) {
            this.Hr.removeAllViews();
            this.Hr.addView(this.Hn.Hj.onCreateView(this.Hs, this.Ht, this.Hq));
        }

        public int getState() {
            return 2;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.6 */
    class C08156 implements C0303a {
        final /* synthetic */ C0304a Hn;

        C08156(C0304a c0304a) {
            this.Hn = c0304a;
        }

        public void m1750b(LifecycleDelegate lifecycleDelegate) {
            this.Hn.Hj.onStart();
        }

        public int getState() {
            return 4;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.7 */
    class C08167 implements C0303a {
        final /* synthetic */ C0304a Hn;

        C08167(C0304a c0304a) {
            this.Hn = c0304a;
        }

        public void m1751b(LifecycleDelegate lifecycleDelegate) {
            this.Hn.Hj.onResume();
        }

        public int getState() {
            return 5;
        }
    }

    public C0304a() {
        this.Hm = new C08111(this);
    }

    private void m350a(Bundle bundle, C0303a c0303a) {
        if (this.Hj != null) {
            c0303a.m346b(this.Hj);
            return;
        }
        if (this.Hl == null) {
            this.Hl = new LinkedList();
        }
        this.Hl.add(c0303a);
        if (bundle != null) {
            if (this.Hk == null) {
                this.Hk = (Bundle) bundle.clone();
            } else {
                this.Hk.putAll(bundle);
            }
        }
        m354a(this.Hm);
    }

    private void aR(int i) {
        while (!this.Hl.isEmpty() && ((C0303a) this.Hl.getLast()).getState() >= i) {
            this.Hl.removeLast();
        }
    }

    public static void m352b(FrameLayout frameLayout) {
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        CharSequence c = GooglePlayServicesUtil.m113c(context, isGooglePlayServicesAvailable);
        CharSequence d = GooglePlayServicesUtil.m115d(context, isGooglePlayServicesAvailable);
        View linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        View textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(c);
        linearLayout.addView(textView);
        if (d != null) {
            View button = new Button(context);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(d);
            linearLayout.addView(button);
            button.setOnClickListener(new C03025(context, isGooglePlayServicesAvailable));
        }
    }

    protected void m353a(FrameLayout frameLayout) {
        C0304a.m352b(frameLayout);
    }

    protected abstract void m354a(C0307f<T> c0307f);

    public T fW() {
        return this.Hj;
    }

    public void onCreate(Bundle savedInstanceState) {
        m350a(savedInstanceState, new C08133(this, savedInstanceState));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        m350a(savedInstanceState, new C08144(this, frameLayout, inflater, container, savedInstanceState));
        if (this.Hj == null) {
            m353a(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.Hj != null) {
            this.Hj.onDestroy();
        } else {
            aR(1);
        }
    }

    public void onDestroyView() {
        if (this.Hj != null) {
            this.Hj.onDestroyView();
        } else {
            aR(2);
        }
    }

    public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
        m350a(savedInstanceState, new C08122(this, activity, attrs, savedInstanceState));
    }

    public void onLowMemory() {
        if (this.Hj != null) {
            this.Hj.onLowMemory();
        }
    }

    public void onPause() {
        if (this.Hj != null) {
            this.Hj.onPause();
        } else {
            aR(5);
        }
    }

    public void onResume() {
        m350a(null, new C08167(this));
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.Hj != null) {
            this.Hj.onSaveInstanceState(outState);
        } else if (this.Hk != null) {
            outState.putAll(this.Hk);
        }
    }

    public void onStart() {
        m350a(null, new C08156(this));
    }

    public void onStop() {
        if (this.Hj != null) {
            this.Hj.onStop();
        } else {
            aR(4);
        }
    }
}

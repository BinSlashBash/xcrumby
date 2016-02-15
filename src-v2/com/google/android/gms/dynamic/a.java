/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.f;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class a<T extends LifecycleDelegate> {
    private T Hj;
    private Bundle Hk;
    private LinkedList<a> Hl;
    private final f<T> Hm;

    public a() {
        this.Hm = new f<T>(){

            @Override
            public void a(T object) {
                a.this.Hj = object;
                object = a.this.Hl.iterator();
                while (object.hasNext()) {
                    ((a)object.next()).b(a.this.Hj);
                }
                a.this.Hl.clear();
                a.this.Hk = null;
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(Bundle bundle, a a2) {
        if (this.Hj != null) {
            a2.b((LifecycleDelegate)this.Hj);
            return;
        }
        if (this.Hl == null) {
            this.Hl = new LinkedList();
        }
        this.Hl.add(a2);
        if (bundle != null) {
            if (this.Hk == null) {
                this.Hk = (Bundle)bundle.clone();
            } else {
                this.Hk.putAll(bundle);
            }
        }
        this.a(this.Hm);
    }

    private void aR(int n2) {
        while (!this.Hl.isEmpty() && this.Hl.getLast().getState() >= n2) {
            this.Hl.removeLast();
        }
    }

    public static void b(FrameLayout frameLayout) {
        final Context context = frameLayout.getContext();
        final int n2 = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        String string2 = GooglePlayServicesUtil.c(context, n2);
        String string3 = GooglePlayServicesUtil.d(context, n2);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView((View)linearLayout);
        frameLayout = new TextView(frameLayout.getContext());
        frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.setText((CharSequence)string2);
        linearLayout.addView((View)frameLayout);
        if (string3 != null) {
            frameLayout = new Button(context);
            frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
            frameLayout.setText((CharSequence)string3);
            linearLayout.addView((View)frameLayout);
            frameLayout.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    context.startActivity(GooglePlayServicesUtil.b(context, n2));
                }
            });
        }
    }

    protected void a(FrameLayout frameLayout) {
        a.b(frameLayout);
    }

    protected abstract void a(f<T> var1);

    public T fW() {
        return this.Hj;
    }

    public void onCreate(final Bundle bundle) {
        this.a(bundle, new a(){

            @Override
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.Hj.onCreate(bundle);
            }

            @Override
            public int getState() {
                return 1;
            }
        });
    }

    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        this.a(bundle, new a(){

            @Override
            public void b(LifecycleDelegate lifecycleDelegate) {
                frameLayout.removeAllViews();
                frameLayout.addView(a.this.Hj.onCreateView(layoutInflater, viewGroup, bundle));
            }

            @Override
            public int getState() {
                return 2;
            }
        });
        if (this.Hj == null) {
            this.a(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.Hj != null) {
            this.Hj.onDestroy();
            return;
        }
        this.aR(1);
    }

    public void onDestroyView() {
        if (this.Hj != null) {
            this.Hj.onDestroyView();
            return;
        }
        this.aR(2);
    }

    public void onInflate(final Activity activity, final Bundle bundle, final Bundle bundle2) {
        this.a(bundle2, new a(){

            @Override
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.Hj.onInflate(activity, bundle, bundle2);
            }

            @Override
            public int getState() {
                return 0;
            }
        });
    }

    public void onLowMemory() {
        if (this.Hj != null) {
            this.Hj.onLowMemory();
        }
    }

    public void onPause() {
        if (this.Hj != null) {
            this.Hj.onPause();
            return;
        }
        this.aR(5);
    }

    public void onResume() {
        this.a(null, new a(){

            @Override
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.Hj.onResume();
            }

            @Override
            public int getState() {
                return 5;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onSaveInstanceState(Bundle bundle) {
        if (this.Hj != null) {
            this.Hj.onSaveInstanceState(bundle);
            return;
        } else {
            if (this.Hk == null) return;
            {
                bundle.putAll(this.Hk);
                return;
            }
        }
    }

    public void onStart() {
        this.a(null, new a(){

            @Override
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.Hj.onStart();
            }

            @Override
            public int getState() {
                return 4;
            }
        });
    }

    public void onStop() {
        if (this.Hj != null) {
            this.Hj.onStop();
            return;
        }
        this.aR(4);
    }

    private static interface a {
        public void b(LifecycleDelegate var1);

        public int getState();
    }

}


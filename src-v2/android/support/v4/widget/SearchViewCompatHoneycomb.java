/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.SearchManager
 *  android.app.SearchableInfo
 *  android.content.ComponentName
 *  android.content.Context
 *  android.view.View
 *  android.widget.SearchView
 *  android.widget.SearchView$OnCloseListener
 *  android.widget.SearchView$OnQueryTextListener
 */
package android.support.v4.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.SearchView;

class SearchViewCompatHoneycomb {
    SearchViewCompatHoneycomb() {
    }

    public static CharSequence getQuery(View view) {
        return ((SearchView)view).getQuery();
    }

    public static boolean isIconified(View view) {
        return ((SearchView)view).isIconified();
    }

    public static boolean isQueryRefinementEnabled(View view) {
        return ((SearchView)view).isQueryRefinementEnabled();
    }

    public static boolean isSubmitButtonEnabled(View view) {
        return ((SearchView)view).isSubmitButtonEnabled();
    }

    public static Object newOnCloseListener(final OnCloseListenerCompatBridge onCloseListenerCompatBridge) {
        return new SearchView.OnCloseListener(){

            public boolean onClose() {
                return onCloseListenerCompatBridge.onClose();
            }
        };
    }

    public static Object newOnQueryTextListener(final OnQueryTextListenerCompatBridge onQueryTextListenerCompatBridge) {
        return new SearchView.OnQueryTextListener(){

            public boolean onQueryTextChange(String string2) {
                return onQueryTextListenerCompatBridge.onQueryTextChange(string2);
            }

            public boolean onQueryTextSubmit(String string2) {
                return onQueryTextListenerCompatBridge.onQueryTextSubmit(string2);
            }
        };
    }

    public static View newSearchView(Context context) {
        return new SearchView(context);
    }

    public static void setIconified(View view, boolean bl2) {
        ((SearchView)view).setIconified(bl2);
    }

    public static void setMaxWidth(View view, int n2) {
        ((SearchView)view).setMaxWidth(n2);
    }

    public static void setOnCloseListener(Object object, Object object2) {
        ((SearchView)object).setOnCloseListener((SearchView.OnCloseListener)object2);
    }

    public static void setOnQueryTextListener(Object object, Object object2) {
        ((SearchView)object).setOnQueryTextListener((SearchView.OnQueryTextListener)object2);
    }

    public static void setQuery(View view, CharSequence charSequence, boolean bl2) {
        ((SearchView)view).setQuery(charSequence, bl2);
    }

    public static void setQueryHint(View view, CharSequence charSequence) {
        ((SearchView)view).setQueryHint(charSequence);
    }

    public static void setQueryRefinementEnabled(View view, boolean bl2) {
        ((SearchView)view).setQueryRefinementEnabled(bl2);
    }

    public static void setSearchableInfo(View view, ComponentName componentName) {
        view = (SearchView)view;
        view.setSearchableInfo(((SearchManager)view.getContext().getSystemService("search")).getSearchableInfo(componentName));
    }

    public static void setSubmitButtonEnabled(View view, boolean bl2) {
        ((SearchView)view).setSubmitButtonEnabled(bl2);
    }

    static interface OnCloseListenerCompatBridge {
        public boolean onClose();
    }

    static interface OnQueryTextListenerCompatBridge {
        public boolean onQueryTextChange(String var1);

        public boolean onQueryTextSubmit(String var1);
    }

}


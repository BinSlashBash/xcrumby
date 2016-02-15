/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewParent
 *  android.widget.LinearLayout
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbContainer;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestion;
import com.squareup.otto.Bus;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FragmentSuggestions
extends LinearLayout
implements View.OnClickListener {
    private BreadcrumbContainer breadcrumbContainer;
    private boolean searchGalleries;
    private Set<String> suggestionIds;
    int visible;

    public FragmentSuggestions(Context context) {
        super(context);
    }

    public FragmentSuggestions(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FragmentSuggestions(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void addSearchSuggestion(String string2) {
        if (string2.equals("")) {
            return;
        }
        if (!(string2.matches("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9\u00a0-\ud7ff\uf900-\ufdcf\ufdf0-\uffef][a-zA-Z0-9\u00a0-\ud7ff\uf900-\ufdcf\ufdf0-\uffef\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnprwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agksyz]|v[aceginu]|w[fs]|(?:\u03b4\u03bf\u03ba\u03b9\u03bc\u03ae|\u0438\u0441\u043f\u044b\u0442\u0430\u043d\u0438\u0435|\u0440\u0444|\u0441\u0440\u0431|\u05d8\u05e2\u05e1\u05d8|\u0622\u0632\u0645\u0627\u06cc\u0634\u06cc|\u0625\u062e\u062a\u0628\u0627\u0631|\u0627\u0644\u0627\u0631\u062f\u0646|\u0627\u0644\u062c\u0632\u0627\u0626\u0631|\u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629|\u0627\u0644\u0645\u063a\u0631\u0628|\u0627\u0645\u0627\u0631\u0627\u062a|\u0628\u06be\u0627\u0631\u062a|\u062a\u0648\u0646\u0633|\u0633\u0648\u0631\u064a\u0629|\u0641\u0644\u0633\u0637\u064a\u0646|\u0642\u0637\u0631|\u0645\u0635\u0631|\u092a\u0930\u0940\u0915\u094d\u0937\u093e|\u092d\u093e\u0930\u0924|\u09ad\u09be\u09b0\u09a4|\u0a2d\u0a3e\u0a30\u0a24|\u0aad\u0abe\u0ab0\u0aa4|\u0b87\u0ba8\u0bcd\u0ba4\u0bbf\u0baf\u0bbe|\u0b87\u0bb2\u0b99\u0bcd\u0b95\u0bc8|\u0b9a\u0bbf\u0b99\u0bcd\u0b95\u0baa\u0bcd\u0baa\u0bc2\u0bb0\u0bcd|\u0baa\u0bb0\u0bbf\u0b9f\u0bcd\u0b9a\u0bc8|\u0c2d\u0c3e\u0c30\u0c24\u0c4d|\u0dbd\u0d82\u0d9a\u0dcf|\u0e44\u0e17\u0e22|\u30c6\u30b9\u30c8|\u4e2d\u56fd|\u4e2d\u570b|\u53f0\u6e7e|\u53f0\u7063|\u65b0\u52a0\u5761|\u6d4b\u8bd5|\u6e2c\u8a66|\u9999\u6e2f|\ud14c\uc2a4\ud2b8|\ud55c\uad6d|xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-3e0b707e|xn\\-\\-45brj9c|xn\\-\\-80akhbyknj4f|xn\\-\\-90a3ac|xn\\-\\-9t4b11yi5a|xn\\-\\-clchc0ea0b2g2a9gcd|xn\\-\\-deba0ad|xn\\-\\-fiqs8s|xn\\-\\-fiqz9s|xn\\-\\-fpcrj9c3d|xn\\-\\-fzc2c9e2c|xn\\-\\-g6w251d|xn\\-\\-gecrj9c|xn\\-\\-h2brj9c|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-j6w193g|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-kprw13d|xn\\-\\-kpry57d|xn\\-\\-lgbbat1ad8j|xn\\-\\-mgbaam7a8h|xn\\-\\-mgbayh7gpa|xn\\-\\-mgbbh1a71e|xn\\-\\-mgbc0a9azcg|xn\\-\\-mgberp4a5d4ar|xn\\-\\-o3cw4h|xn\\-\\-ogbpf8fl|xn\\-\\-p1ai|xn\\-\\-pgbs0dh|xn\\-\\-s9brj9c|xn\\-\\-wgbh1c|xn\\-\\-wgbl6a|xn\\-\\-xkc2al3hye2a|xn\\-\\-xkc2dl3a5ee0h|xn\\-\\-yfro4i67o|xn\\-\\-ygbi2ammx|xn\\-\\-zckzah|xxx)|y[et]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9\u00a0-\ud7ff\uf900-\ufdcf\ufdf0-\uffef\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)") || string2.contains("?") || string2.contains("crumby://"))) {
            this.searchGalleries = true;
            this.addSuggestionWithNoBaseUrl("Search All Galleries for \"" + string2 + "\"", " " + string2, this.getResources().getDrawable(2130837640));
            return;
        }
        this.searchGalleries = false;
    }

    private FragmentSuggestion addSuggestion(FragmentLink fragmentLink, Drawable drawable2) {
        if (this.visible >= this.getChildCount()) {
            return null;
        }
        int n2 = this.visible;
        this.visible = n2 + 1;
        FragmentSuggestion fragmentSuggestion = (FragmentSuggestion)this.getChildAt(n2);
        fragmentSuggestion.setImage(drawable2);
        fragmentSuggestion.setFragmentLink(fragmentLink);
        return fragmentSuggestion;
    }

    private FragmentSuggestion addSuggestion(String string2, String string3, String string4) {
        return this.addSuggestion(new FragmentLink(string2, string3, string4, 0), null);
    }

    private FragmentSuggestion addSuggestionWithNoBaseUrl(String object, String string2, Drawable drawable2) {
        object = new FragmentLink((String)object, string2, null, 0);
        object.setHideBaseUrl(true);
        object.setMandatory(true);
        return this.addSuggestion((FragmentLink)object, drawable2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void appendFinal(String string2, String object, boolean bl2) {
        boolean bl3;
        FragmentIndex fragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(string2);
        boolean bl4 = false;
        if (fragmentIndex == null) return;
        if (!fragmentIndex.getFragmentClass().equals(UnsupportedUrlFragment.class)) {
            if (bl2) {
                bl3 = bl4;
                if (!this.suggestionIds.contains(string2)) {
                    if ((object = this.addSuggestion("Favorite?", string2, (String)object)) == null) {
                        return;
                    }
                    object.setHideBackground(true);
                    object.notFavoritedYet();
                    bl3 = bl4;
                }
            } else {
                bl3 = true;
            }
        } else {
            bl3 = true;
        }
        if (!bl3) return;
        this.appendTryParse(string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void appendFragmentSuggestions(String var1_1, List<FragmentLink> var2_3) {
        // MONITORENTER : this
        var2_2 = var2_2.iterator();
        do {
            if (!var2_2.hasNext()) ** GOTO lbl-1000
            var3_3 = (FragmentLink)var2_2.next();
            if (this.suggestionIds.contains(var3_3.getBaseUrl())) continue;
            var4_4 = this.addSuggestion(var3_3, null);
            if (var4_4 == null) lbl-1000: // 2 sources:
            {
                // MONITOREXIT : this
                return;
            }
            var4_4.highlight(var1_1);
            this.suggestionIds.add(var3_3.getBaseUrl());
        } while (true);
    }

    public void appendTryParse(String string2) {
        if (this.suggestionIds.isEmpty() && !this.searchGalleries) {
            this.addSuggestionWithNoBaseUrl("Try to parse \"" + string2 + "\"?", string2, this.getResources().getDrawable(2130837645));
        }
    }

    public boolean canSearchGalleries() {
        return this.searchGalleries;
    }

    public int getVisible() {
        return this.visible;
    }

    public void hide() {
        this.setVisibility(8);
    }

    public void onClick(View object) {
        if (!(object = (FragmentSuggestion)object.getParent()).isClickable()) {
            return;
        }
        this.breadcrumbContainer.removeBreadcrumbs();
        object = object.getUrl();
        Analytics.INSTANCE.newNavigationEvent("fragment suggestion click", (String)object);
        BusProvider.BUS.get().post(new UrlChangeEvent((String)object));
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
            this.getChildAt(i2).findViewById(2131492974).setOnClickListener((View.OnClickListener)this);
        }
        this.suggestionIds = new HashSet<String>();
    }

    public int remainingSpace() {
        return this.getChildCount() - this.visible - 2;
    }

    public void removeSuggestions(String string2) {
        this.visible = 0;
        for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
            this.getChildAt(i2).setVisibility(8);
        }
        this.suggestionIds.clear();
        this.addSearchSuggestion(string2);
    }

    public void setBreadcrumbContainer(BreadcrumbContainer breadcrumbContainer) {
        this.breadcrumbContainer = breadcrumbContainer;
    }

    public void show() {
        this.setVisibility(0);
    }
}


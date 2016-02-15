package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.crumby.GalleryViewer;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.widget.firstparty.GalleryPanel;
import java.util.List;

public class OmnibarView
  extends LinearLayout
{
  private BreadcrumbContainer breadcrumbContainer;
  private GalleryPanel galleryPanel;
  private OmnibarModal omnibarModal;
  private OmnisearchField searchField;
  
  public OmnibarView(Context paramContext)
  {
    super(paramContext);
  }
  
  public OmnibarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public OmnibarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void hideBreadcrumbs()
  {
    this.breadcrumbContainer.hide();
    this.searchField.show(this.breadcrumbContainer.getCurrentBreadcrumbs());
  }
  
  private void showBreadcrumbs()
  {
    this.searchField.hide();
    this.breadcrumbContainer.show();
  }
  
  public void clearBreadcrumbs()
  {
    this.breadcrumbContainer.removeBreadcrumbs();
  }
  
  public void dismissGalleryPanel()
  {
    this.omnibarModal.hide();
    this.galleryPanel.hide();
  }
  
  public void dismissOmnibarModal()
  {
    this.breadcrumbContainer.focus();
    this.omnibarModal.hide();
  }
  
  public boolean galleryPanelShowing()
  {
    return this.galleryPanel.isShowing();
  }
  
  public void hideModals()
  {
    clearFocus();
    showBreadcrumbs();
    this.galleryPanel.hide();
    this.omnibarModal.hide();
  }
  
  public void initialize(final GalleryViewer paramGalleryViewer)
  {
    setHideBreadcrumbListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramGalleryViewer.showOmniSearch();
      }
    });
    setOmnibarModal(paramGalleryViewer.findViewById(2131493071), paramGalleryViewer.getLayoutInflater(), new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramGalleryViewer.hideOmniSearch();
      }
    });
    setGalleryPanel(paramGalleryViewer.findViewById(2131492999));
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.searchField = ((OmnisearchField)findViewById(2131492897));
    this.breadcrumbContainer = ((BreadcrumbContainer)findViewById(2131492907));
    this.breadcrumbContainer.initialize(this);
    this.searchField.setBreadcrumbContainer(this.breadcrumbContainer);
  }
  
  public void override(List<Breadcrumb> paramList)
  {
    this.breadcrumbContainer.override(paramList);
  }
  
  public void pageLoading()
  {
    this.breadcrumbContainer.stopLoadingMode();
  }
  
  public void pageNotLoading()
  {
    this.breadcrumbContainer.refreshMode();
  }
  
  public void setGalleryPanel(View paramView)
  {
    this.galleryPanel = ((GalleryPanel)paramView);
    this.galleryPanel.setOmnibarView(this);
  }
  
  public void setHideBreadcrumbListener(View.OnClickListener paramOnClickListener)
  {
    this.breadcrumbContainer.setClickable(true);
    this.breadcrumbContainer.setOnClickListener(paramOnClickListener);
  }
  
  public void setOmnibarModal(View paramView, LayoutInflater paramLayoutInflater, View.OnClickListener paramOnClickListener)
  {
    this.omnibarModal = ((OmnibarModal)paramView);
    this.omnibarModal.setOnClickListener(paramOnClickListener);
    this.omnibarModal.shareSuggestionsViewWith(this.searchField);
    this.searchField.setProgressIndicator((FragmentSuggestionsIndicator)paramView.findViewById(2131493075));
    this.searchField.setOmniformContainer((OmniformContainer)paramView.findViewById(2131493079));
  }
  
  public void showAccount()
  {
    this.searchField.showAccount();
  }
  
  public void showGalleryPanel(int paramInt, Breadcrumb paramBreadcrumb)
  {
    this.omnibarModal.show(false);
    this.galleryPanel.rebuildForm(paramInt, paramBreadcrumb);
  }
  
  public void showOmniSearchModal()
  {
    hideBreadcrumbs();
    this.galleryPanel.hide();
    this.omnibarModal.show(true);
  }
  
  public void update(GalleryViewerFragment paramGalleryViewerFragment, boolean paramBoolean)
  {
    showBreadcrumbs();
    this.searchField.setLink(paramGalleryViewerFragment);
    this.breadcrumbContainer.newPage(paramGalleryViewerFragment, paramBoolean);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/OmnibarView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
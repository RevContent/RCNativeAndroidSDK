package com.revcontent.rcnativeandroidsdk.banner;

public interface BannerEventListener {
    /**
     * Will notify that the {@link com.revcontent.rcnativeandroidsdk.banner.RCNativeSliderBanner}
     * has finished loading the content.
     */
    void onLoaded();

    /**
     * Will notify that the {@link com.revcontent.rcnativeandroidsdk.banner.RCNativeSliderBanner}
     * was closed by user.
     */
    void onClosed();

    /**
     * Will notify that the {@link com.revcontent.rcnativeandroidsdk.banner.RCNativeSliderBanner}
     * was tapped by user and user was navigated by link.
     */
    void onLinkTap();
}

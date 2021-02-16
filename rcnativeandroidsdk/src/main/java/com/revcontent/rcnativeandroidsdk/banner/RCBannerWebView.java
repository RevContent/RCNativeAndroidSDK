package com.revcontent.rcnativeandroidsdk.banner;

import android.content.Context;
import android.util.AttributeSet;

import com.revcontent.rcnativeandroidsdk.base.BaseWidgetWebView;

final class RCBannerWebView extends BaseWidgetWebView {

    protected final String bannerSizeKey = "{banner-size}";
    protected final String bannerSizeVal = BannerSize.W320XH50.toString();
    private String bannerSize = null;

    public RCBannerWebView(Context context) {
        super(context);
    }

    public RCBannerWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RCBannerWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void setBannerSize(BannerSize banner) {
        this.bannerSize = banner.toString();
    }

    @Override
    protected void loadHTMLContent() {
        this.htmlWidget =
                "<!doctype html> <html> <head> <style> html, body { margin:0; padding: 0; } @media (prefers-color-scheme: dark) { html, body { background: #000; } }</style> </head> <body> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <div id=\"{widget-host}\" data-rc-widget data-widget-host=\"{widget-host}\" data-endpoint=\"{endpoint}\" data-gdpr={gdpr} data-gdpr-consent={gdpr-consent} data-us-privacy={us-privacy} data-is-secured=\"{is-secured}\" data-widget-id=\"{widget-id}\" data-banner-size=\"{banner-size}\"></div><script src=\"{js-src}\" > </script> </body> </html>";
    }

    @Override
    protected String generateWidgetHTML() {
        String result = super.generateWidgetHTML();
        result = result.replace(bannerSizeKey, bannerSize == null ? bannerSizeVal : bannerSize);
        return result;
    }
}
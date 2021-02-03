package com.revcontent.rcnativeandroidsdk.base;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.revcontent.rcnativeandroidsdk.OnSizeChangedListener;
import com.revcontent.rcnativeandroidsdk.RCNativeSDK;

import java.util.Map;

public abstract class BaseWidgetWebView extends WebView {
    protected final String defaultBaseURL = "https://performance.revcontent.dev";
    protected final String widgetHostKey = "{widget-host}";
    protected final String widgetHostVal = "habitat";
    protected final String endPointKey = "{endpoint}";
    protected final String endPointVal = "trends.revcontent.com";
    protected final String jsSrcKey = "{js-src}";
    protected final String jsSrcVal = "https://assets.revcontent.com/master/delivery.js";
    protected final String deferKey = "{defer}";
    protected final String deferVal = "defer";
    protected final String widgetIdKey = "{widget-id}";

    protected final String gdprIsApplicableParam = "data-gdpr=";
    protected final String gdprIsApplicableKey = "{gdpr}";
    protected String gdprIsApplicableVal = "";

    protected final String gdprConsentParam = "data-gdpr-consent=";
    protected final String gdprConsentKey = "{gdpr-consent}";
    protected String gdprConsentVal = "";

    protected final String usPrivacyParam = "data-us-privacy=";
    protected final String usPrivacyKey = "{us-privacy}";
    protected String usPrivacyVal = "";

    protected String htmlWidget = null;
    protected String widgetId = null;
    protected String baseUrl = null;
    protected Map<String, String> widgetSubId = null;
    protected OnSizeChangedListener sizeChangedListener = null;

    public BaseWidgetWebView(Context context) {
        super(context);
        initWidget();
    }

    public BaseWidgetWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWidget();
    }

    public BaseWidgetWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWidget();
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        if (sizeChangedListener != null) {
            sizeChangedListener.onSizeChanged(h, w);
        }
    }

    protected void initWidget() {
        this.loadHTMLContent();
        this.getSettings().setJavaScriptEnabled(true);
    }

    /**
     * Adds the listener for the changes of view's height and width.
     * If you'd like to receive all resizing callbacks, call this method before {@link #loadWidget()}
     * method.
     * {@link com.revcontent.rcnativeandroidsdk.RCNativeJSWidgetView} currently supports one
     * OnSizeChangedListener.
     *
     * @param sizeChangedListener OnSizeChangedListener;
     */
    public void addOnSizeChangedListener(OnSizeChangedListener sizeChangedListener) {
        this.sizeChangedListener = sizeChangedListener;
    }

    /**
     * Removes the listener for the changes of view's height and width.
     */
    public void removeOnSizeChangedListener() {
        this.sizeChangedListener = null;
    }

    /**
     * Clears the resource cache. Note that the cache is per-application, so this will clear the
     * cache for all WebViews used.
     *
     * @param includeDiskFiles if false, only the RAM cache is cleared;
     */
    public void removeCache(boolean includeDiskFiles) {
        clearCache(includeDiskFiles);
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    /**
     * Method should be called before {@link #loadWidget} to apply GDPR consent parameters.
     *
     * @param isGDPRApplicable determines whether GDPR is applicable
     * @param consentString GDPR consent string. Is IAB standard URL-safe base64 encoded value
     */
    public void setGDPRConsentInfo(@NonNull Boolean isGDPRApplicable, @NonNull String consentString) {
        this.gdprIsApplicableVal = getStringWithParentheses( isGDPRApplicable ? "1" : "0");
        this.gdprConsentVal = getStringWithParentheses(consentString);
    }

    /**
     * Clears GDPR consent parameters applied via method  {@link #setGDPRConsentInfo}
     * To reload widget call method  {@link #loadWidget()} after it.
     */
    public void clearGDPRConsentInfo() {
        this.gdprIsApplicableVal = "";
        this.gdprConsentVal = "";
    }

    /**
     * Method should be called before {@link #loadWidget} to apply CCPA consent parameter.
     *
     * @param consentString CCPA consent string. Is IAB standard URL-encoded U.S. Privacy string.
     */
    public void setUSPrivacyConsentInfo(@NonNull String consentString) {
        this.usPrivacyVal = getStringWithParentheses(consentString);
    }

    /**
     * Clears CCPA consent parameter applied via method  {@link #setUSPrivacyConsentInfo}
     * To reload widget call method {@link #loadWidget()} after it.
     */
    public void clearUSPrivacyConsentInfo() {
        this.usPrivacyVal = "";
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Deprecated
    public void setWidgetId(String widgetId, Map<String, String> widgetSubId, String siteUrl) {
        this.widgetId = widgetId;
        this.widgetSubId = widgetSubId;
    }

    public void loadWidget() {
        String message = validateWidget();
        if (message == null) {
            String html = generateWidgetHTML();
            if (baseUrl != null) {
                loadDataWithBaseURL(baseUrl, html, "text/html", "UTF-8", null);
            } else {
                loadDataWithBaseURL(defaultBaseURL, html, "text/html", "UTF-8", null);
            }
        } else {
            System.out.println(message);
        }
    }

    protected void loadHTMLContent() {
        this.htmlWidget =
                "<!doctype html> <html> <head> <style> html, body { margin:0; padding: 0; } @media (prefers-color-scheme: dark) { html, body { background: #000; } }</style> </head> <body> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <div id=\"widget1\" data-rc-widget data-widget-host=\"{widget-host}\" data-endpoint=\"{endpoint}\" data-gdpr={gdpr} data-gdpr-consent={gdpr-consent} data-us-privacy={us-privacy} data-is-secured=\"{is-secured}\" data-widget-id=\"{widget-id}\"></div><script src=\"{js-src}\" defer=\"{defer}\"> </script> </body> </html>";
    }

    private String validateWidget() {
        if (!RCNativeSDK.initialized()) {
            return "RCSDK -> SDK not initialzied.";
        }
        if (this.htmlWidget == null) {
            return "RCSDK -> RCJSWidgetView: Widget not loaded.";
        }
        if (this.widgetId == null) {
            return "RCSDK -> RCJSWidgetView: WidgetId is required.";
        }
        return null;
    }

    private String getStringWithParentheses(String text){
        return String.format("\"%s\"", text);
    }

    protected String generateWidgetHTML() {
        String result = this.htmlWidget.replace(widgetHostKey, widgetHostVal);
        result = result.replace(endPointKey, endPointVal);
        result = result.replace(widgetIdKey, this.widgetId);
        result = result.replace(jsSrcKey, jsSrcVal);
        result = result.replace(deferKey, deferVal);
        result = updateGDPRConsentParams(result);
        result = updateCCPAConsentParam(result);
        return result;
    }

    private String updateGDPRConsentParams(String htmlText){
        String resultText = htmlText;
        if (!gdprIsApplicableVal.isEmpty() && !gdprConsentVal.isEmpty()){
            resultText = resultText.replace(gdprIsApplicableKey, gdprIsApplicableVal);
            resultText = resultText.replace(gdprConsentKey, gdprConsentVal);
        } else {
            resultText = resultText.replace(gdprIsApplicableParam, "");
            resultText = resultText.replace(gdprIsApplicableKey, "");
            resultText = resultText.replace(gdprConsentParam, "");
            resultText = resultText.replace(gdprConsentKey, "");
        }
        return resultText;
    }

    private String updateCCPAConsentParam(String htmlText){
        String resultText = htmlText;
        if (!usPrivacyVal.isEmpty()){
            resultText = resultText.replace(usPrivacyKey, usPrivacyVal);
        } else {
            resultText = resultText.replace(usPrivacyParam, "");
            resultText = resultText.replace(usPrivacyKey, "");
        }
        return resultText;
    }
}
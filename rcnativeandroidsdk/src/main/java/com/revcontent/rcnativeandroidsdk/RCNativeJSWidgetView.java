package com.revcontent.rcnativeandroidsdk;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import org.json.JSONObject;
import java.util.Map;

public final class RCNativeJSWidgetView extends WebView {
    private final String defaultBaseURL = "https://performance.revcontent.dev";
    private final String widgetHostKey = "{widget-host}";
    private final String widgetHostVal = "habitat";
    private final String endPointKey = "{endpoint}";
    private final String endPointVal = "trends.revcontent.com";
    private final String isSecuredKey = "{is-secured}";
    private final String isSecuredVal = "true";
    private final String jsSrcKey = "{js-src}";
    private final String jsSrcVal = "https://assets.revcontent.com/master/delivery.js";
    private final String deferKey = "{defer}";
    private final String deferVal = "defer";
    private final String widgetIdKey = "{widget-id}";
    private final String widgetSubIdKey = "{sub-ids}";
    private final String sourceUrlKey = "{source-url}";

    private final String gdprIsApplicableParam = "data-gdpr=";
    private final String gdprIsApplicableKey = "{gdpr}";
    private  String gdprIsApplicableVal = "";

    private final String gdprConsentParam = "data-gdpr-consent=";
    private final String gdprConsentKey = "{gdpr-consent}";
    private  String gdprConsentVal = "";

    private final String usPrivacyParam = "data-us-privacy=";
    private final String usPrivacyKey = "{us-privacy}";
    private  String usPrivacyVal = "";

    private String htmlWidget = null;
    private String widgetId = null;
    private String baseUrl = null;
    private String siteUrl = null;
    private Map<String, String> widgetSubId = null;
    private OnSizeChangedListener sizeChangedListener = null;

    public RCNativeJSWidgetView(Context context) {
        super(context);
        initWidget();
    }

    public RCNativeJSWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWidget();
    }

    public RCNativeJSWidgetView(Context context, AttributeSet attrs, int defStyle) {
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

    public void initWidget() {
        this.loadHTMLContent();
        this.getSettings().setJavaScriptEnabled(true);
    }

    /**
     * Adds the listener for the changes of view's height and width.
     * If you'd like to receive all resizing callbacks, call this method before {@link #initWidget}
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

    private void loadHTMLContent() {
        this.htmlWidget =
                "<!doctype html> <html> <head> <style> html, body { margin:0; padding: 0; } @media (prefers-color-scheme: dark) { html, body { background: #000; } }</style> </head> <body> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <div id=\"widget1\" data-rc-widget data-widget-host=\"{widget-host}\" data-endpoint=\"{endpoint}\" data-gdpr={gdpr} data-gdpr-consent={gdpr-consent} data-us-privacy={us-privacy} data-is-secured=\"{is-secured}\" data-widget-id=\"{widget-id}\" data-sub-ids=\"{sub-ids}\"></div><script src=\"{js-src}\" defer=\"{defer}\"> </script> </body> </html>";
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
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

    public void setWidgetSubId(Map<String, String> widgetSubId) {
        this.widgetSubId = widgetSubId;
    }

    public void setWidgetId(String widgetId, Map<String, String> widgetSubId, String siteUrl) {
        this.widgetId = widgetId;
        this.widgetSubId = widgetSubId;
        this.siteUrl = siteUrl;
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

    private String generateWidgetHTML() {
        String result = this.htmlWidget.replace(widgetHostKey, widgetHostVal);
        result = result.replace(endPointKey, endPointVal);
        result = result.replace(isSecuredKey, isSecuredVal);
        result = result.replace(widgetIdKey, this.widgetId);
        //   result = result.replace(sourceUrlKey,this.siteUrl)
        result = result.replace(jsSrcKey, jsSrcVal);
        result = result.replace(deferKey, deferVal);
        result = updateGDPRConsentParams(result);
        result = updateCCPAConsentParam(result);
        if (this.widgetSubId != null) {
            JSONObject jsonObject = new JSONObject(this.widgetSubId);
            String jsonString = jsonObject.toString();
            System.out.println(jsonObject.toString());
            String replacedJsonString = jsonString.replaceAll("\"", "&quot;");
            result = result.replace(widgetSubIdKey, replacedJsonString);
        } else {
            result = result.replace(widgetSubIdKey, "");
        }
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

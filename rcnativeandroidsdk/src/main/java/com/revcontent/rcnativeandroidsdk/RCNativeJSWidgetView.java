package com.revcontent.rcnativeandroidsdk;

import android.content.Context;
import android.util.AttributeSet;

import com.revcontent.rcnativeandroidsdk.base.BaseWidgetWebView;

import org.json.JSONObject;
import java.util.Map;

public final class RCNativeJSWidgetView extends BaseWidgetWebView {

    protected final String isSecuredKey = "{is-secured}";
    protected final String isSecuredVal = "true";
    protected final String widgetSubIdKey = "{sub-ids}";

    public RCNativeJSWidgetView(Context context) {
        super(context);
    }

    public RCNativeJSWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RCNativeJSWidgetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Deprecated
    public void setSiteUrl(String siteUrl) { }

    public void setWidgetSubId(Map<String, String> widgetSubId) {
        this.widgetSubId = widgetSubId;
    }

    @Override
    protected String generateWidgetHTML() {
        String result = super.generateWidgetHTML();
        result = result.replace(isSecuredKey, isSecuredVal);
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

    @Override
    protected void loadHTMLContent() {
        this.htmlWidget =
                "<!doctype html> <html> <head> <style> html, body { margin:0; padding: 0; } @media (prefers-color-scheme: dark) { html, body { background: #000; } }</style> </head> <body> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <div id=\"widget1\" data-rc-widget data-widget-host=\"{widget-host}\" data-endpoint=\"{endpoint}\" data-gdpr={gdpr} data-gdpr-consent={gdpr-consent} data-us-privacy={us-privacy} data-is-secured=\"{is-secured}\" data-widget-id=\"{widget-id}\" data-sub-ids=\"{sub-ids}\"></div><script src=\"{js-src}\" defer=\"{defer}\"> </script> </body> </html>";
    }
}

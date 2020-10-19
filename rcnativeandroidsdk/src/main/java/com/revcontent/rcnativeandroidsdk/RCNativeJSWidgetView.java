package com.revcontent.rcnativeandroidsdk;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
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

    private String htmlWidget = null;
    private String widgetId = null;
    private String baseUrl = null;
    private String siteUrl = null;
    private Map<String, String> widgetSubId = null;

    private boolean isInitMeasuring = true;
    private boolean isUnspecifiedHeight = false; // wrap_content used for height size;
    private int updateIndex = 0;

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

    public void initWidget() {
        this.loadHTMLContent();
        this.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInitMeasuring) {
            isInitMeasuring = false;
            //case wrap_content used
            if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED){
                isUnspecifiedHeight = true;
                //2000px - approximate min player height to cover the player
                setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 2000);
                ViewGroup.LayoutParams params = getLayoutParams();
                Log.d("TEST", "" + computeVerticalScrollRange());
                params.height = 2000;
                requestLayout(); //will trigger view sizes re-measuring
                Runnable run  = new Runnable() {
                    @Override
                    public void run() {
                        ViewGroup.LayoutParams params = getLayoutParams();
                        params.height = computeVerticalScrollRange();
                        requestLayout();
                        updateIndex++;
                        if (updateIndex < 5){
                            postDelayed(this, 5000);
                        }
                    }
                };
                //will update height of view based on verticalScrollRange method
                postDelayed(run, 5000);
            } else {
                //case height is defined
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } else if (isUnspecifiedHeight){
            if (MeasureSpec.getSize(heightMeasureSpec) == 0) {
                //when the size of view hasn't been calculated yet and height attribute is wrap_content;
                setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 2000);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void loadHTMLContent() {
        this.htmlWidget =
                "<!doctype html> <html> <head> <style> html, body { margin:0; padding: 0; } @media (prefers-color-scheme: dark) { html, body { background: #000; } }</style> </head> <body> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <div id=\"widget1\" data-rc-widget data-widget-host=\"{widget-host}\" data-endpoint=\"{endpoint}\" data-is-secured=\"{is-secured}\" data-widget-id=\"{widget-id}\" data-sub-ids=\"{sub-ids}\"></div><script src=\"{js-src}\" defer=\"{defer}\"> </script> </body> </html>";
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
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
        String message = this.validateWidget();
        if (message == null) {
            String html = this.generateWidgetHTML();
            if (this.baseUrl != null) {
                this.loadDataWithBaseURL(this.baseUrl, html, "text/html", "UTF-8", null);
            } else {
                this.loadDataWithBaseURL(defaultBaseURL, html, "text/html", "UTF-8", null);
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

    private String generateWidgetHTML() {
        String result = this.htmlWidget.replace(widgetHostKey, widgetHostVal);
        result = result.replace(endPointKey, endPointVal);
        result = result.replace(isSecuredKey, isSecuredVal);
        result = result.replace(widgetIdKey, this.widgetId);
        //   result = result.replace(sourceUrlKey,this.siteUrl)
        result = result.replace(jsSrcKey, jsSrcVal);
        result = result.replace(deferKey, deferVal);
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
}

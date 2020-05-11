package com.revcontent.rcnativeandroidsdk;

import android.content.Context;
import android.webkit.WebView;

import java.util.Map;

public final class RCNactiveJSWidgetView extends WebView {
    private final String baseURL = "https://performance.revcontent.dev";
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
    private String siteUrl = null;
    private Map<String,String> widgetSubId = null;

    public RCNactiveJSWidgetView(Context context){
        super(context);
        this.loadHTMLContent();
    }

    private void loadHTMLContent(){
        this.htmlWidget = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><div id=\"widget1\" data-rc-widget data-widget-host=\"{widget-host}\" data-endpoint=\"{endpoint}\" data-is-secured=\"{is-secured}\" data-widget-id=\"{widget-id}\" data-sub-ids=\"{sub-ids}\"></div><script src=\"{js-src}\" defer=\"{defer}\"></script>";
    }

    public void setWidgetId(String widgetId){
        this.widgetId = widgetId;
    }

    public void setSiteUrl(String siteUrl){
        this.siteUrl = siteUrl;
    }

    public void setWidgetSubId(Map<String,String> widgetSubId){
        this.widgetSubId = widgetSubId;
    }

    public void setWidgetId(String widgetId, Map<String,String> widgetSubId, String siteUrl){
        this.widgetId = widgetId;
        this.widgetSubId = widgetSubId;
        this.siteUrl = siteUrl;
    }

    public void loadWidget(){
        String message = this.validateWidget();
        if(message == null){
            String html = this.generateWidgetHTML();
            this.loadDataWithBaseURL(baseURL,html,"text/html","UTF-8",null);
        }else{
            System.out.println(message);
        }
    }

    private String validateWidget(){
        if(!RCNativeSDK.initialized()){
            return "RCSDK -> SDK not initialzied.";
        }
        if(this.htmlWidget == null){
            return "RCSDK -> RCJSWidgetView: Widget not loaded.";
        }
        if(this.widgetId == null){
            return "RCSDK -> RCJSWidgetView: WidgetId is required.";
        }
        return null;
    }

    private String generateWidgetHTML(){
        String result = this.htmlWidget.replace(widgetHostKey,widgetHostVal);
        result = result.replace(endPointKey,endPointVal);
        result = result.replace(isSecuredKey, isSecuredVal);
        result = result.replace(widgetIdKey, this.widgetId);
        //   result = result.replace(sourceUrlKey,this.siteUrl)
        result = result.replace(jsSrcKey,jsSrcVal);
        result = result.replace(deferKey,deferVal);
        if(this.widgetSubId != null){
//            let jsonData = try? JSONSerialization.data(withJSONObject: self.widgetSubId!, options: []);
//            let jsonString = String(data: jsonData!, encoding: .utf8);
//            let replacedJsonString = jsonString!.replacingOccurrences(of: "\"", with: "&quot;");
//            result = result.replacingOccurrences(of: widgetSubIdKey, with: replacedJsonString);
        }else{
            result = result.replace(widgetSubIdKey,"");
        }
        return result;
    }
}

package com.revcontent.rcnativeandroidsdkexample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.revcontent.rcnativeandroidsdk.RCNativeJSWidgetView;
import com.revcontent.rcnativeandroidsdk.RCNativeSDK;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RCNativeJSWidgetView widgetView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RCNativeSDK.setup();
        widgetView = new RCNativeJSWidgetView(this);
        Map map = new HashMap();
        map.put("category","entertainment");
        map.put("utm_code","123456");
        widgetView.setWidgetId("168072");
        widgetView.setWidgetSubId(map);
        widgetView.setBaseUrl("https://performance.revcontent.dev");
        setContentView(widgetView);
        widgetView.loadWidget();
    }
}

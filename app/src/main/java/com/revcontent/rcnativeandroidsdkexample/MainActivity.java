package com.revcontent.rcnativeandroidsdkexample;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import com.revcontent.rcnativeandroidsdk.RCNativeJSWidgetView;
import com.revcontent.rcnativeandroidsdk.RCNativeSDK;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Should be called before widget usage
        RCNativeSDK.setup();

        // Get the widget view from layout
        RCNativeJSWidgetView widgetView = findViewById(R.id.rc_widget);

        // WidgetId is required
        widgetView.setWidgetId("168072");

        // WidgetSubId is optional
        Map<String, String> map = new HashMap<>();
        map.put("category","entertainment");
        map.put("utm_code","123456");
        widgetView.setWidgetSubId(map);

        // baseUrl defined here
        widgetView.setBaseUrl("https://performance.revcontent.dev");

        //will load the given data into widget;
        widgetView.loadWidget();
    }
}

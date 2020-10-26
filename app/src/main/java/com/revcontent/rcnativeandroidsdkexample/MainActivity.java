package com.revcontent.rcnativeandroidsdkexample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.revcontent.rcnativeandroidsdk.RCNativeJSWidgetView;
import com.revcontent.rcnativeandroidsdk.RCNativeSDK;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RCNativeSDK.setup();
        setContentView(R.layout.activity_main);
        //RCNativeJSWidgetView widgetView = new RCNativeJSWidgetView(this);
        RCNativeJSWidgetView widgetView = findViewById(R.id.rc_widget);
        Map<String, String> map = new HashMap<>();
        map.put("category","entertainment");
        map.put("utm_code","123456");
        widgetView.setWidgetId("168072");
        widgetView.setWidgetSubId(map);
        widgetView.setBaseUrl("https://performance.revcontent.dev");
       // setContentView(widgetView);
        widgetView.loadWidget();
    }
}

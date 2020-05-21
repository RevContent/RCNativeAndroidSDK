package com.revcontent.rcnativeandroidsdkexample;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.revcontent.rcnativeandroidsdk.RCNactiveJSWidgetView;
import com.revcontent.rcnativeandroidsdk.RCNativeSDK;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RCNactiveJSWidgetView widgetView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RCNativeSDK.setup();
        widgetView = new RCNactiveJSWidgetView(this);
        Map map = new HashMap();
        map.put("category","entertainment");
        map.put("utm_code","123456");
        widgetView.setWidgetId("66620");
        widgetView.setWidgetSubId(map);
        setContentView(widgetView);
        widgetView.loadWidget();
    }
}

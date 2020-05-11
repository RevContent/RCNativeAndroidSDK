package com.revcontent.rcnativeandroidsdkexample;
import com.revcontent.rcnativeandroidsdk.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RCNativeSDK.setup();
        RCNactiveJSWidgetView widgetView = new RCNactiveJSWidgetView(this);
        Map map = new HashMap();
        map.put("category","entertainment");
        map.put("utm_code","123456");
        widgetView.setWidgetId("66620");
        widgetView.setWidgetSubId(map);
        WebSettings webSettings = widgetView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setContentView(widgetView);
        widgetView.loadWidget();
    }
}

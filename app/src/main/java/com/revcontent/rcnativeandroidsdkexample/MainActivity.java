package com.revcontent.rcnativeandroidsdkexample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.revcontent.rcnativeandroidsdk.RCNativeJSWidgetView;
import com.revcontent.rcnativeandroidsdk.RCNativeSDK;
import com.revcontent.rcnativeandroidsdk.banner.BannerSize;
import com.revcontent.rcnativeandroidsdk.banner.RCNativeSliderBanner;

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

        initBanner();
    }

    private void initBanner() {
        // Create instance of banner.
        // RCNativeSliderBanner will try and find a parent view to hold RCNativeSliderBanner view
        // from the value given to view;
        final RCNativeSliderBanner banner = new RCNativeSliderBanner(findViewById(R.id.main));

        // Start loading banner.
        // Once loaded, banner will appear from the bottom of parent view.
        // Required parameters - widgetID & BannerSize from public enum.
        banner.loadBanner(1233, BannerSize.W970XH250);
    }
}

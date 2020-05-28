
## Introduction:

Revcontent's Android library written in Java for enables you quickly and reliably include our JS widgets into your application.

## Prerequisites 

- Android Studio
- Android SDK

## Features 

- Load widget by WidgetId
- Load widget by SubId (Optional)
- Flexible widget size

## Installation 

- Create a new project in Android Studio as you would normally for e.g. MyApp.

- In your project’s build.gradle add the following line:
	
    	allprojects{
        	repositories{
            	...
                maven { url 'https://jitpack.io' }
            }
        }

- In your app’s build.gradle add the dependency:

	    dependencies{
            implementation 'com.github.RevContent:RCNativeAndroidSDK:0.1.1'
        }

## Usage


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
            private RCNactiveJSWidgetView widgetView = null
    		protected void onCreate(Bundle savedInstanceState) {
       		 super.onCreate(savedInstanceState);
        		RCNativeSDK.setup();
        	    widgetView = new RCNactiveJSWidgetView(this);
                // WidgetId is required
                widgetView.setWidgetId("66620");  
                // WidgetSubId is optional
        		Map map = new HashMap();
        		map.put("category","entertainment");
        		map.put("utm_code","123456");
        		widgetView.setWidgetSubId(map);
                // baseUrl is optional
                widgetView.setBaseUrl("https://performance.revcontent.dev");
        		setContentView(widgetView);
        		widgetView.loadWidget();
    		}
        }
        
 ## License
 
 MIT

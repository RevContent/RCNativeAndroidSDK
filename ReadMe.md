
##Introduction:

An Android library written in Java for the Revcontent to enables you to receive the same ad fill you would see in our traditional ad placements in a more flexible format.

##Prerequisites 

- Android Studio
- Androd SDK

##Features 

- Load widget by WidgetId
- Load widget by SubId (Optional)
- Flexible widget size

#Installation 

- Create a new project in Android Studio as you would normally for e.g. MyApp.

- In your project’s build.gradle add the following line:
	
    	allprojects{
        	repositories{
            	...
                maven { url ‘https://jitpack.io’ }
            }
        }

- In your app’s build.gradle add the dependency:

	    dependencies{
            implementation 'com.github.RevContent:RCNativeAndroidSDK:0.1.0
        }

##Usage


		package com.revcontent.rcnativeandroidsdkexample;
		import android.os.Bundle;
		import androidx.appcompat.app.AppCompatActivity;
		import com.revcontent.rcnativeandroidsdk.RCNactiveJSWidgetView;
		import com.revcontent.rcnativeandroidsdk.RCNativeSDK;
		import java.util.HashMap;
		import java.util.Map;

		public class MainActivity extends AppCompatActivity {
    	
        	@Override
            private RCNactiveJSWidgetView widgetView = null
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
        
 ##License
 
 MIT

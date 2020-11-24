
## Introduction:

Revcontent's Android library written in Java for enables you quickly and reliably include our JS widgets into your application.

## Prerequisites 

- Android Studio
- Android SDK

## Features 

- Load widget by WidgetId
- Load widget by SubId (Optional)
- Define Base URL as FQDN or Article URL
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
            implementation 'com.github.RevContent:RCNativeAndroidSDK:0.1.4'
        }

## Usage

#### Basic usage in app

```
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
```

#### Using OnSizeChangedListener

```
        //Optionally, you can add a listener for the widget's size changes.
        widgetView.addOnSizeChangedListener(new OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int height, int width) {
                //add your code here
            }
        });

        //OnSizeChangedListener can be removed like this:
        widgetView.removeOnSizeChangedListener();
```

## Changelog

https://github.com/RevContent/RCNativeAndroidSDK/blob/master/CHANGELOG.md
        
 ## License
 
 MIT

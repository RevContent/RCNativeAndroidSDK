
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


## GDPR
The General Data Protection Regulation (GDPR) is the toughest privacy and security law in the world. Though it was drafted and passed by the European Union (EU), it imposes obligations onto organizations anywhere, so long as they target or collect data related to people in the EU. The regulation was put into effect on May 25, 2018.

Revcontent's widget supports GDPR consent parameters.
In order to provide user's GDPR consent status you can implement your own or third party [CMP](https://github.com/InteractiveAdvertisingBureau/GDPR-Transparency-and-Consent-Framework/blob/master/Mobile%20In-App%20Consent%20APIs%20v1.0%20Final.md).
You can also check the [demo app](https://github.com/InteractiveAdvertisingBureau/GDPR-Transparency-and-Consent-Framework/tree/master/In-App%20Reference/Android) provided by IAB
The parameters are optional and can be passed to Revcontent's widget via below method, which should be called before loadWidget() method:

```
// boolean value, which determines if GDPR is applicable.
boolean isGDPRApplicable = true;

// GDPR consent string is IAB standard URL-safe base64 encoded value.
String gdprConsent = "<GDPR_CONSENT_STRING>";

widgetView.setGDPRConsentInfo(isGDPRApplicable, gdprConsent);

// GDPR consent info should be provided before this method call:
widgetView.loadWidget();

```

## CCPA
The California Consumer Privacy Act (CCPA) is a law that allows any California consumer to demand to see all the information a company has saved on them, as well as a full list of all the third parties that data is shared with. In addition, the California law allows consumers to sue companies if the privacy guidelines are violated, even if there is no breach.

Revcontent's widget supports CCPA consent parameter.
In order to provide user's CCPA consent status you should get [US Privacy String](https://github.com/InteractiveAdvertisingBureau/USPrivacy/blob/master/CCPA/US%20Privacy%20String.md).

The parameter is optional and can be passed to Revcontent's widget via below method, which should be called before loadWidget() method:

```
// CCPA consent string. Is IAB standard URL-encoded U.S. Privacy string.
String ccpaConsent = "<CCPA_CONSENT_STRING>";
widgetView.setUSPrivacyConsentInfo(ccpaConsent);

//CCPA consent info should be provided before this method call
widgetView.loadWidget();

```

 ## License
 
 MIT

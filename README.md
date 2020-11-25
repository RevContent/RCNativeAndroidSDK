
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

## Ads.txt for Publishers

[Click Here](https://www.revcontent.com/ads_txt) for the lines needed for your Ads.txt file to work with Revcontent.

### What is Ads.txt?

Ads.txt is an initiative founded by the [IAB Technology Laboratory](https://iabtechlab.com/) to increase transparency in the programmatic landscape. The goal is to ensure publishers’ inventory is only sold through authorized partners to prevent counterfeiting in addition to providing advertisers more control over their purchased inventory.

### What exactly is ads.txt?

- Authorized Digital Sellers (ads.txt for short) is an IAB initiative to improve transparency and reduce fraud in programmatic advertising.
- Ads.txt is a publicly available file that publishers create and add to their websites. The file is plain text and contains the names of the authorized networks that have permission to sell their inventory.

### How does ads.txt benefit me?

- The Ads.txt file can help protect your brand from counterfeit inventory that is intentionally mislabeled as originating from a specific domain, app, or video.
- Declaring authorized sellers will open up more demand as many buyers will not purchase inventory unless they have an ads.txt file outlining authorized to sellers.

### How do I create a .txt file?

Create your file as a text (.txt) using Notepad or a similar program.

- Your file should be hosted at the root level for your domain.Example: https://example.com/ads.txt
- Root Domain is defined as one level down from the [Public Suffix List](https://publicsuffix.org/list/), which is also how it is defined in the [IAB ads.txt specification](https://iabtechlab.com/ads-txt/). Example: google.co.uk would be considered a root domain as co.uk is on the Public Suffix List, but maps.google.co.uk would not be considered a root domain.

### What information is included in an ads.txt file?

Publishers should include a separate line in the file for each authorized seller. Each line in a publisher’s ads.txt list requires three pieces of data (plus a fourth optional field) <Field #1>, <Field #2>, <Field #3>, <Field #4>:
- The domain name of the advertising system (required) Example: revcontent.com
- Publisher’s Account ID (required) Example: 12345 (Your publisher ID for Revcontent)
- Type of Account/Relationship (required) DIRECT - A direct business contract between the publisher and the advertising system RESELLER - Publishers who do not directly control the account indicated in  should specify RESELLER NOTE: This field is case-sensitive and should always be in all-caps for both DIRECT and RESELLER
- Certification Authority ID (optional) A current certification authority is the Trustworty Accountability Group (TAG), and the TAG ID would be included here.

Information that needs to be in your ads.txt file as a publisher with Revcontent should be copied directly from the list found inside of your account settings. [Click here](https://www.revcontent.com/ads_txt) to go directly to the relevant page.

### How do I create an ads.txt file in DFP?

Check out [Google’s Step-By-Step Guide](https://support.google.com/dfp_premium/answer/7544382) on ads.txt management in DFP.

### Need more Information?
You can learn more about this IAB initiative [here](https://iabtechlab.com/ads-txt/). For more information from Revcontent, feel free to reach out to your Publisher Account Representative.

## License
 
 MIT

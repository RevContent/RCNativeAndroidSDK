package com.revcontent.rcnativeandroidsdkexample;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.revcontent.rcnativeandroidsdkexample.utils.ViewHeightMatcher;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webContent;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.matcher.DomMatchers.hasElementWithId;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class RCWebViewInstrumentedTest {

    /**
     * Before start testing WebView, check that WIDGET_ID value is the same, as in MainActivity
     */
    final int WIDGET_ID = 168072;
    final String WIDGET_ID_NAME = "rc_cont_" + WIDGET_ID;

    @Test
    public void onWebViewInit_ExistsElementWithSetWidgetId() {
        // Lazily launch the Activity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        //checks whether widget id exists
        onWebView().check(webContent(hasElementWithId(WIDGET_ID_NAME)));
        scenario.close();
    }

    @Test
    public void onWebViewInit_HeaderIsPresent() {
        // Lazily launch the Activity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        // Selects the RC WebView and checks whether widget has the standard header text
        onWebView(withId(R.id.rc_widget))
                .withElement(findElement(Locator.ID, WIDGET_ID_NAME))
                .check(webMatches(getText(), containsString("From the Web")));
        scenario.close();
    }

    @Test
    public void onWebViewInit_WebViewSizeIsNotZero() {
        // the timeout enough for WebView to load content
        final int timeOut = 1000 * 5;

        // Lazily launch the Activity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        try {
            Thread.sleep(timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //check whether web view height is not zero
        onView(withId(R.id.rc_widget)).check(matches(not(new ViewHeightMatcher(0))));
        scenario.close();
    }
}
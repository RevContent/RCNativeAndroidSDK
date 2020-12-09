package com.revcontent.rcnativeandroidsdkexample.utils;

import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ViewHeightMatcher extends TypeSafeMatcher<View> {
    private final int expectedHeight;

    public ViewHeightMatcher(int expectedHeight) {
        super(View.class);
        this.expectedHeight = expectedHeight;
    }

    @Override
    protected boolean matchesSafely(View target) {
        int targetHeight = target.getHeight();
        return targetHeight == expectedHeight;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with SizeMatcher height: ");
        description.appendValue(expectedHeight);
    }
}

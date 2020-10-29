package com.revcontent.rcnativeandroidsdk;

public interface OnSizeChangedListener {
    /**
     * Will Notify that the {@link com.revcontent.rcnativeandroidsdk.RCNativeJSWidgetView} view
     * size has changed.
     *
     * @param height new height value in pixels;
     * @param width new width value in pixels;
     */
    void onSizeChanged(int height, int width);
}

package com.revcontent.rcnativeandroidsdk.banner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.revcontent.rcnativeandroidsdk.R;
import com.revcontent.rcnativeandroidsdk.connectivity.ConnectionMonitor;

class RCSliderBannerLayout extends ConstraintLayout {

    protected final int BANNER_LOAD_DELAY = 2000;

    private RCBannerWebView webView;

    private BannerSize bannerSize = BannerSize.W320XH50;

    private boolean isBannerLoaded = false;

    @Nullable
    private BannerEventListener eventListener = null;

    public RCSliderBannerLayout(@NonNull Context context) {
        this(context, null, 0, 0);
    }

    public RCSliderBannerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public RCSliderBannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    public RCSliderBannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    boolean getIsBannerLoaded()  {
        return isBannerLoaded;
    }

    void loadBanner(int widgetID, BannerSize size) {
        if (webView != null) {
            //prepare layout container
            bannerSize = size;
            requestLayout();

            //load banner
            webView.setWidgetId(String.valueOf(widgetID));
            webView.setBannerSize(size);
            webView.loadWidget();
        }
    }

    void addEventListener(BannerEventListener eventListener) {
        this.eventListener = eventListener;
    }

    void removeEventListener() {
        this.eventListener = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        updateWebViewSize(bannerSize);
    }

    private void updateWebViewSize(BannerSize size) {
        ViewGroup.LayoutParams lp = webView.getLayoutParams();
        int initHeight = lp.height;
        int initWidth = lp.width;

        int maxHeight = getMeasuredHeight() - getContext().getResources().getDimensionPixelSize(R.dimen.button_size);
        int maxWidth = getMeasuredWidth();

        double contentHeight = size.height;
        double contentWidth = size.width;

        double scale = 100;

        if (size.height > maxHeight || size.width > maxWidth){
            double widthRatio = maxWidth * 100d / size.width;
            double heightRatio = maxHeight * 100d / size.height;

            scale = Math.min(widthRatio, heightRatio);

            contentHeight = (int)(contentHeight * (int)scale / 100d);
            contentWidth = (int)(contentWidth * (int)scale / 100d);
        }

        webView.setInitialScale((int)scale);

        if (initHeight != contentHeight || initWidth != contentWidth){
            lp.height = (int) (contentHeight);
            lp.width = (int)  (contentWidth);
            webView.setLayoutParams(lp);
            requestLayout();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        webView = findViewById(R.id.banner_web_view);
        ImageView closeView = findViewById(R.id.banner_close);
        closeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventListener != null) {
                    eventListener.onClosed();
                }
            }
        });
        initWebView();
    }

    private void initWebView() {
        webView.setInitialScale(100);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        // disable scroll on touch
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    if (eventListener != null) eventListener.onLinkTap();
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                if (eventListener != null)
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ConnectionMonitor monitor = new ConnectionMonitor(getContext());
                            if (monitor.isNetworkAvailable()){
                                isBannerLoaded = true;
                                eventListener.onLoaded();
                            }
                        }
                    }, BANNER_LOAD_DELAY);
            }
        });
    }
}
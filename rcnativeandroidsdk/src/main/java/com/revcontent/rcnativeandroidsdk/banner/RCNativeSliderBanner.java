package com.revcontent.rcnativeandroidsdk.banner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.revcontent.rcnativeandroidsdk.R;

import java.util.concurrent.Callable;

public class RCNativeSliderBanner {

    @NonNull
    private final ViewGroup targetParent;
    @NonNull
    private final RCSliderBannerLayout view;
    @Nullable
    private View anchorView;

    private int extraBottomMarginAnchorView;
    private int extraBottomMarginWindowInset;
    private int extraLeftMarginWindowInset;
    private int extraRightMarginWindowInset;

    private Boolean isShowWhenLoaded = true;

    @Nullable
    private BannerEventListener eventListener = null;

    /**
     * @param view The view to find a parent from. This view is also used to find the anchor
     *                   view when calling {@link RCNativeSliderBanner#setAnchorView(int)}.
     */

    public RCNativeSliderBanner(@NonNull View view) {
        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view.");
        }
        targetParent = parent;
        Context context = view.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = (RCSliderBannerLayout) inflater.inflate(R.layout.banner_view, targetParent, false);

        // Make sure that we fit system windows and have a listener to apply any insets
        this.view.setFitsSystemWindows(true);
        ViewCompat.setOnApplyWindowInsetsListener(
                this.view,
                new OnApplyWindowInsetsListener() {
                    @NonNull
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(
                            View v, @NonNull WindowInsetsCompat insets) {
                        // Save window insets for additional margins, e.g., to dodge the system navigation bar
                        extraBottomMarginWindowInset = insets.getSystemWindowInsetBottom();
                        extraLeftMarginWindowInset = insets.getSystemWindowInsetLeft();
                        extraRightMarginWindowInset = insets.getSystemWindowInsetRight();
                        updateMargins();
                        return insets;
                    }
                });

        this.view.addEventListener(
                new BannerEventListener() {
                    @Override
                    public void onLoaded() {
                        if (eventListener != null) {
                            eventListener.onLoaded();
                        }

                        if (isShowWhenLoaded) {
                            slideUp(RCNativeSliderBanner.this.view);
                        }
                    }

                    @Override
                    public void onClosed() {
                        slideDown(RCNativeSliderBanner.this.view, null);
                        if (eventListener != null) {
                            eventListener.onClosed();
                        }
                    }

                    @Override
                    public void onLinkTap() {
                        if (eventListener != null) {
                            eventListener.onLinkTap();
                        }
                    }
                }
        );
    }

    /**
     * Will show banner if it's already loaded.
     * If banner hasn't finished loading yet, will show it once it finishes.
     *
     * @return false value if the banner is already visible;
     */
    public boolean show() {
        if (view.getIsBannerLoaded()) {
            if (view.getVisibility() == View.VISIBLE) return false;
            slideUp(view);
        } else {
            showWhenLoaded(true);
        }
        return true;
    }

    /**
     * @param isShowWhenLoaded sets whether banner should be shown when loaded.
     */
    public void showWhenLoaded(Boolean isShowWhenLoaded) {
        this.isShowWhenLoaded = isShowWhenLoaded;
    }

    /**
     * Method will start loading widget in the WebView.
     * If banner was already loaded - will do nothing.
     * It's required to create new {@link RCNativeSliderBanner} object for each banner.
     *
     * @param widgetID id of widget, which used to set banner content.
     * @param size     {@link BannerSize} one of enums existing banner sizes.
     */
    public void loadBanner(final int widgetID, final BannerSize size) {
        if (this.view.getParent() != null) return;
        // Set view to INVISIBLE so it doesn't flash on the screen before the inset adjustment is
        // handled and the enter animation is started
        view.setVisibility(View.INVISIBLE);
        targetParent.addView(this.view);

        if (anchorView == null) {
            updateMargins();
            view.loadBanner(widgetID, size);
        } else {
            calculateBottomMarginForAnchorView(new Callable<Void>() {
                public Void call() {
                    updateMargins();
                    view.loadBanner(widgetID, size);
                    return null;
                }
            });
        }
    }

    /**
     * Closes banner if visible and then detaches banner from parent view.
     * <p>
     * In order to show banner after this method call you'll need to create new instance of
     * {@link RCNativeSliderBanner}.
     */
    public void cancel() {
        if (view.getVisibility() == View.VISIBLE) {
            slideDown(view, new Callable<Void>() {
                public Void call() {
                    detachView();
                    return null;
                }
            });
        } else {
            // If the view isn't visible, just remove it
            detachView();
        }

    }

    private void detachView() {
        // hide and remove the view from the parent (if attached)
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        anchorView = null;
        showWhenLoaded(false);
    }

    /**
     * Adds the listener for the banner events.
     * The possible events are:
     * - when the banner finished loading;
     * - when the banner was closed by user;
     * - when user was navigated by the link after tapping on the banner;
     *
     * @param eventListener BannerEventListener;
     */
    public void addEventListener(BannerEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Removes the listener for the banner events.
     */
    public void removeEventListener() {
        this.eventListener = null;
    }

    @Nullable
    private ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
    }

    /**
     * Sets the id of the view the {@link RCNativeSliderBanner} should be anchored above.
     *
     * @throws IllegalArgumentException if the anchor view was not found as child of banner's parent.
     */
    public void setAnchorView(@IdRes int anchorViewId) {
        this.anchorView = targetParent.findViewById(anchorViewId);
        if (this.anchorView == null) {
            throw new IllegalArgumentException("Unable to find anchor view with id: " + anchorViewId);
        }
    }

    /**
     * Sets the view the {@link RCNativeSliderBanner} should be anchored above.
     */
    public void setAnchorView(@Nullable View anchorView) {
        this.anchorView = anchorView;
    }

    private void calculateBottomMarginForAnchorView(final Callable<Void> onResult) {
        if (anchorView == null) {
            extraBottomMarginAnchorView = 0;
            try {
                onResult.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        anchorView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        int[] anchorViewLocation = new int[2];
                        anchorView.getLocationOnScreen(anchorViewLocation);
                        int anchorViewAbsoluteYTop = anchorViewLocation[1];

                        int[] targetParentLocation = new int[2];
                        targetParent.getLocationOnScreen(targetParentLocation);
                        int targetParentAbsoluteYBottom = targetParentLocation[1] + targetParent.getHeight();

                        extraBottomMarginAnchorView = targetParentAbsoluteYBottom - anchorViewAbsoluteYTop;
                        try {
                            onResult.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        anchorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
        );


    }

    private void updateMargins() {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            Log.w("TAG", "Unable to update margins because layout params are not MarginLayoutParams");
            return;
        }

        int extraBottomMargin = anchorView != null ? extraBottomMarginAnchorView : extraBottomMarginWindowInset;
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginParams.bottomMargin = extraBottomMargin;
        marginParams.leftMargin = extraLeftMarginWindowInset;
        marginParams.rightMargin = extraRightMarginWindowInset;
        view.requestLayout();
    }

    private void slideUp(final View view) {
        view.setAlpha(0.f);
        view.setVisibility(View.VISIBLE);
        view.setTranslationY(view.getHeight());
        view.animate()
                .translationY(0)
                .setDuration(300)
                .alpha(1.f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                        view.setAlpha(1.f);
                    }
                });

    }

    private void slideDown(final View view, @Nullable final Callable<Void> onFinish) {
        view.animate()
                .translationY(view.getHeight())
                .setDuration(300)
                .alpha(0.f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // superfluous restoration
                        view.setVisibility(View.GONE);
                        view.setAlpha(1.f);
                        view.setTranslationY(0.f);
                        try {
                            onFinish.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}


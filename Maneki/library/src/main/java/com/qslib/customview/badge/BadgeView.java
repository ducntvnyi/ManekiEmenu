package com.qslib.customview.badge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import com.qslib.library.R;

/**
 * Created by Dang on 10/16/2015.
 */
@SuppressWarnings("ALL")
public class BadgeView extends TextView {
    // position of badge view
    public static final int POSITION_TOP_LEFT = 1;
    public static final int POSITION_TOP_RIGHT = 2;
    public static final int POSITION_BOTTOM_LEFT = 3;
    public static final int POSITION_BOTTOM_RIGHT = 4;
    public static final int POSITION_CENTER = 5;

    private static final int DEFAULT_MARGIN_DIP = 5;
    private static final int DEFAULT_LR_PADDING_DIP = 5;
    private static final int DEFAULT_POSITION = POSITION_TOP_RIGHT;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private static Animation fadeIn;
    private static Animation fadeOut;

    private Context context;
    private View target;
    private Drawable badgeBackgroundDrawable;

    private int badgePosition;
    private int badgeMarginHorizontal;
    private int badgeMarginVertical;
    private int textColor;
    private int targetTabIndex;

    private boolean isShown;

    /**
     * @param context
     */
    public BadgeView(Context context) {
        this(context, (AttributeSet) null, android.R.attr.textViewStyle);
    }

    /**
     * @param context
     * @param attrs
     */
    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    /**
     * Constructor -
     * <p>
     * create a new BadgeView instance attached to a target {@link android.view.View}.
     *
     * @param context context for this view.
     * @param target  the View to attach the badge to.
     */
    public BadgeView(Context context, View target) {
        this(context, null, android.R.attr.textViewStyle, target, 0);
    }

    /**
     * Constructor -
     * <p>
     * create a new BadgeView instance attached to a target {@link android.widget.TabWidget}
     * tab at a given index.
     *
     * @param context context for this view.
     * @param target  the TabWidget to attach the badge to.
     * @param index   the position of the tab within the target.
     */
    public BadgeView(Context context, TabWidget target, int index) {
        this(context, null, android.R.attr.textViewStyle, target, index);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public BadgeView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     * @param target
     * @param tabIndex
     */
    public BadgeView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
        super(context, attrs, defStyle);
        init(context, target, tabIndex);
    }

    /**
     * @param context
     * @param target
     * @param tabIndex
     */
    private void init(Context context, View target, int tabIndex) {
        try {
            this.context = context;
            this.target = target;
            this.targetTabIndex = tabIndex;
            this.isShown = false;

            // apply defaults
            this.badgePosition = DEFAULT_POSITION;
            this.badgeMarginHorizontal = dipToPixels(DEFAULT_MARGIN_DIP);
            this.badgeMarginVertical = badgeMarginHorizontal;

            this.setTypeface(Typeface.DEFAULT);
            int paddingPixels = dipToPixels(DEFAULT_LR_PADDING_DIP);
            this.setPadding(paddingPixels, 0, paddingPixels, 0);
            this.textColor = DEFAULT_TEXT_COLOR;
            this.setTextColor(textColor);
            this.setGravity(Gravity.CENTER);

            this.fadeIn = new AlphaAnimation(0, 1);
            this.fadeIn.setInterpolator(new DecelerateInterpolator());
            this.fadeIn.setDuration(200);

            this.fadeOut = new AlphaAnimation(1, 0);
            this.fadeOut.setInterpolator(new AccelerateInterpolator());
            this.fadeOut.setDuration(200);

            if (this.target != null) {
                this.applyTo(this.target);
            } else {
                this.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param target
     */
    private void applyTo(View target) {
        try {
            LayoutParams lp = target.getLayoutParams();
            ViewParent parent = target.getParent();
            FrameLayout container = new FrameLayout(context);

            if (target instanceof TabWidget)   // set target to the relevant tab child container
            {
                target = ((TabWidget) target).getChildTabViewAt(targetTabIndex);
                this.target = target;
                ((ViewGroup) target).addView(container,
                        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                setVisibility(View.GONE);
                container.addView(this);
            } else {
                ViewGroup group = (ViewGroup) parent;
                int index = group.indexOfChild(target);
                group.removeView(target);
                group.addView(container, index, lp);
                container.addView(target);
                setVisibility(View.GONE);
                container.addView(this);
                group.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Make the badge visible in the UI.
     */
    public void show() {
        show(false, null);
    }

    /**
     * Make the badge visible in the UI.
     *
     * @param animate flag to apply the default fade-in animation.
     */
    public void show(boolean animate) {
        show(animate, fadeIn);
    }

    /**
     * Make the badge visible in the UI.
     *
     * @param anim Animation to apply to the view when made visible.
     */
    public void show(Animation anim) {
        show(true, anim);
    }

    /**
     * Make the badge non-visible in the UI.
     */
    public void hide() {
        hide(false, null);
    }

    /**
     * Make the badge non-visible in the UI.
     *
     * @param animate flag to apply the default fade-out animation.
     */
    public void hide(boolean animate) {
        hide(animate, fadeOut);
    }

    /**
     * Make the badge non-visible in the UI.
     *
     * @param anim Animation to apply to the view when made non-visible.
     */
    public void hide(Animation anim) {
        hide(true, anim);
    }

    /**
     * Toggle the badge visibility in the UI.
     */
    public void toggle() {
        toggle(false, null, null);
    }

    /**
     * Toggle the badge visibility in the UI.
     *
     * @param animate flag to apply the default fade-in/out animation.
     */
    public void toggle(boolean animate) {
        toggle(animate, fadeIn, fadeOut);
    }

    /**
     * Toggle the badge visibility in the UI.
     *
     * @param animIn  Animation to apply to the view when made visible.
     * @param animOut Animation to apply to the view when made non-visible.
     */
    public void toggle(Animation animIn, Animation animOut) {
        toggle(true, animIn, animOut);
    }

    /**
     * @param animate
     * @param anim
     */
    private void show(boolean animate, Animation anim) {
        try {
            if (getBackground() == null) {
                if (badgeBackgroundDrawable == null)
                    badgeBackgroundDrawable = getDefaultBackground();
                setBackgroundDrawable(badgeBackgroundDrawable);
            }

            applyLayoutParams();

            if (animate) this.startAnimation(anim);
            this.setVisibility(View.VISIBLE);
            this.isShown = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param animate
     * @param anim
     */
    private void hide(boolean animate, Animation anim) {
        try {
            this.setVisibility(View.GONE);
            if (animate) this.startAnimation(anim);
            this.isShown = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param animate
     * @param animIn
     * @param animOut
     */
    private void toggle(boolean animate, Animation animIn, Animation animOut) {
        try {
            if (isShown) {
                this.hide(animate && (animOut != null), animOut);
            } else {
                this.show(animate && (animIn != null), animIn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private Drawable getDefaultBackground() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                return getResources().getDrawable(R.drawable.bg_badge, getContext().getTheme());
            } else {
                return getResources().getDrawable(R.drawable.bg_badge);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * apply layout params
     */
    @SuppressLint("RtlHardcoded")
    private void applyLayoutParams() {
        try {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            switch (badgePosition) {
                case POSITION_TOP_LEFT:
                    layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                    layoutParams.setMargins(badgeMarginHorizontal, badgeMarginVertical, 0, 0);
                    break;
                case POSITION_TOP_RIGHT:
                    layoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
                    layoutParams.setMargins(0, badgeMarginVertical, badgeMarginHorizontal, 0);
                    break;
                case POSITION_BOTTOM_LEFT:
                    layoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
                    layoutParams.setMargins(badgeMarginHorizontal, 0, 0, badgeMarginVertical);
                    break;
                case POSITION_BOTTOM_RIGHT:
                    layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                    layoutParams.setMargins(0, 0, badgeMarginHorizontal, badgeMarginVertical);
                    break;
                case POSITION_CENTER:
                    layoutParams.gravity = Gravity.CENTER;
                    layoutParams.setMargins(0, 0, 0, 0);
                    break;
                default:
                    break;
            }

            setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Is this badge currently visible in the UI?
     */
    @Override
    public boolean isShown() {
        return isShown;
    }

    /**
     * Set the positioning of this badge.
     *
     * @param layoutPosition one of POSITION_TOP_LEFT, POSITION_TOP_RIGHT, POSITION_BOTTOM_LEFT, POSITION_BOTTOM_RIGHT, POSITION_CENTER.
     */
    public BadgeView setBadgePosition(int layoutPosition) {
        this.badgePosition = layoutPosition;
        return this;
    }

    /**
     * Set the horizontal/vertical margin from the target View that is applied to this badge.
     *
     * @param badgeMargin the margin in pixels.
     */
    public BadgeView setBadgeMargin(int badgeMargin) {
        this.badgeMarginHorizontal = badgeMargin;
        this.badgeMarginVertical = badgeMargin;
        return this;
    }

    /**
     * Set the horizontal/vertical margin from the target View that is applied to this badge.
     *
     * @param horizontal margin in pixels.
     * @param vertical   margin in pixels.
     */
    public BadgeView setBadgeMargin(int horizontal, int vertical) {
        this.badgeMarginHorizontal = horizontal;
        this.badgeMarginVertical = vertical;
        return this;
    }

    /**
     * Set the color value of the badge background.
     *
     * @param drawableId the badge background color.
     */
    public BadgeView setBadgeBackgroundColor(int drawableId) {
        this.badgeBackgroundDrawable = getContext().getResources().getDrawable(drawableId);
        this.setBackgroundDrawable(this.badgeBackgroundDrawable);
        return this;
    }

    /**
     * Set the color value of the badge number.
     *
     * @param textColor
     * @return
     */
    private BadgeView setNumberColor(int textColor) {
        this.textColor = getContext().getResources().getColor(textColor);
        this.setTextColor(this.textColor);
        return this;
    }

    /**
     * set badge number
     *
     * @param number
     * @return
     */
    public void setBadgeNumber(int number) {
        try {
            this.setText(String.valueOf(number));
            if (number <= 0) hide();
            else show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * convertObjectToMapString dp to pixel
     *
     * @param dip
     * @return
     */
    private int dipToPixels(int dip) {
        try {
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
            return (int) px;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}

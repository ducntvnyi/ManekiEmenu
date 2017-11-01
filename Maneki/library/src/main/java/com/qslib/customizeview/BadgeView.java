package com.qslib.customizeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
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
    private static final int DEFAULT_CORNER_RADIUS_DIP = 8;
    private static final int DEFAULT_POSITION = POSITION_TOP_RIGHT;
    private static final int DEFAULT_BADGE_COLOR = Color.parseColor("#CCFF0000"); //Color.RED;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private static Animation fadeIn;
    private static Animation fadeOut;

    private Context context;
    private View target;
    private ShapeDrawable badgeBg;

    private int badgePosition;
    private int badgeMarginH;
    private int badgeMarginV;
    private int badgeBackgroundColor;
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
            badgePosition = DEFAULT_POSITION;
            badgeMarginH = dipToPixels(DEFAULT_MARGIN_DIP);
            badgeMarginV = badgeMarginH;
            badgeBackgroundColor = DEFAULT_BADGE_COLOR;

            setTypeface(Typeface.DEFAULT);
            int paddingPixels = dipToPixels(DEFAULT_LR_PADDING_DIP);
            setPadding(paddingPixels, 0, paddingPixels, 0);
            textColor = DEFAULT_TEXT_COLOR;
            setTextColor(textColor);

            fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator());
            fadeIn.setDuration(200);

            fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setInterpolator(new AccelerateInterpolator());
            fadeOut.setDuration(200);

            if (this.target != null) {
                applyTo(this.target);
            } else {
                show();
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

            if (target instanceof TabWidget) {
                // set target to the relevant tab child container
                target = ((TabWidget) target).getChildTabViewAt(targetTabIndex);
                this.target = target;
                ((ViewGroup) target).addView(container,
                        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                this.setVisibility(View.GONE);
                container.addView(this);
            } else {
                ViewGroup group = (ViewGroup) parent;
                int index = group.indexOfChild(target);
                group.removeView(target);
                group.addView(container, index, lp);
                container.addView(target);
                this.setVisibility(View.GONE);
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
                if (badgeBg == null) badgeBg = setDefaultBackground();
                setBackgroundDrawable(badgeBg);
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
                hide(animate && (animOut != null), animOut);
            } else {
                show(animate && (animIn != null), animIn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Increment the numeric badge label. If the current badge label cannot be converted to
     * an integer value, its label will be set to "0".
     *
     * @param offset the increment offset.
     */
    public int increment(int offset) {
        int i = 0;

        try {
            CharSequence txt = getText();
            if (txt != null) {
                try {
                    i = Integer.parseInt(txt.toString());
                } catch (NumberFormatException e) {
                    i = 0;
                }
            } else {
                i = 0;
            }

            i = i + offset;
            setText(String.valueOf(i));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }

    /**
     * Decrement the numeric badge label. If the current badge label cannot be converted to
     * an integer value, its label will be set to "0".
     *
     * @param offset the decrement offset.
     */
    public int decrement(int offset) {
        return increment(-offset);
    }

    /**
     * @return
     */
    private ShapeDrawable setDefaultBackground() {
        try {
            int r = dipToPixels(DEFAULT_CORNER_RADIUS_DIP);
            float[] outerR = new float[]{r, r, r, r, r, r, r, r};

            RoundRectShape rr = new RoundRectShape(outerR, null, null);
            ShapeDrawable drawable = new ShapeDrawable(rr);
            drawable.getPaint().setColor(badgeBackgroundColor);

            return drawable;
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
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            switch (badgePosition) {
                case POSITION_TOP_LEFT:
                    lp.gravity = Gravity.LEFT | Gravity.TOP;
                    lp.setMargins(badgeMarginH, badgeMarginV, 0, 0);
                    break;
                case POSITION_TOP_RIGHT:
                    lp.gravity = Gravity.RIGHT | Gravity.TOP;
                    lp.setMargins(0, badgeMarginV, badgeMarginH, 0);
                    break;
                case POSITION_BOTTOM_LEFT:
                    lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                    lp.setMargins(badgeMarginH, 0, 0, badgeMarginV);
                    break;
                case POSITION_BOTTOM_RIGHT:
                    lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                    lp.setMargins(0, 0, badgeMarginH, badgeMarginV);
                    break;
                case POSITION_CENTER:
                    lp.gravity = Gravity.CENTER;
                    lp.setMargins(0, 0, 0, 0);
                    break;
                default:
                    break;
            }

            setLayoutParams(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the target View this badge has been attached to.
     */
    public View getTarget() {
        return target;
    }

    /**
     * Is this badge currently visible in the UI?
     */
    @Override
    public boolean isShown() {
        return isShown;
    }

    /**
     * Returns the positioning of this badge.
     * <p>
     * one of POSITION_TOP_LEFT, POSITION_TOP_RIGHT, POSITION_BOTTOM_LEFT, POSITION_BOTTOM_RIGHT, POSITION_CENTER.
     */
    public int getBadgePosition() {
        return badgePosition;
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
     * Returns the horizontal margin from the target View that is applied to this badge.
     */
    public int getHorizontalBadgeMargin() {
        return badgeMarginH;
    }

    /**
     * Returns the vertical margin from the target View that is applied to this badge.
     */
    public int getVerticalBadgeMargin() {
        return badgeMarginV;
    }

    /**
     * Set the horizontal/vertical margin from the target View that is applied to this badge.
     *
     * @param badgeMargin the margin in pixels.
     */
    public BadgeView setBadgeMargin(int badgeMargin) {
        this.badgeMarginH = badgeMargin;
        this.badgeMarginV = badgeMargin;
        return this;
    }

    /**
     * Set the horizontal/vertical margin from the target View that is applied to this badge.
     *
     * @param horizontal margin in pixels.
     * @param vertical   margin in pixels.
     */
    public BadgeView setBadgeMargin(int horizontal, int vertical) {
        this.badgeMarginH = horizontal;
        this.badgeMarginV = vertical;
        return this;
    }

    /**
     * Returns the color value of the badge background.
     */
    public int getBadgeBackgroundColor() {
        return badgeBackgroundColor;
    }

    /**
     * Set the color value of the badge background.
     *
     * @param badgeBackgroundColor the badge background color.
     */
    public BadgeView setBadgeBackgroundColor(int badgeBackgroundColor) {
        this.badgeBackgroundColor = badgeBackgroundColor;
        this.badgeBg = setDefaultBackground();
        return this;
    }

    /**
     * @return the color value of the badge number.
     */
    private int getNumberColor() {
        return textColor;
    }

    /**
     * Set the color value of the badge number.
     *
     * @param textColor
     * @return
     */
    private BadgeView setNumberColor(int textColor) {
        this.textColor = textColor;
        this.setTextColor(this.textColor);
        return this;
    }

    /**
     * set badge number
     *
     * @param number
     * @return
     */
    public BadgeView setBadgeNumber(int number) {
        this.setText(String.valueOf(number));
        return this;
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

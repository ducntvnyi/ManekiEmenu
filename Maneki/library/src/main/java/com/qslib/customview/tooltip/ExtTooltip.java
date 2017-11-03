package com.qslib.customview.tooltip;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qslib.library.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dang on 6/29/2016.
 */
public class ExtTooltip {
    /**
     * get instance
     *
     * @param activity
     * @return
     */
    public static ExtTooltip getInstance(Activity activity) {
        return new ExtTooltip(activity);
    }

    private Activity mContext;

    // gravity
    public static final int GRAVITY_TOP = 0;
    public static final int GRAVITY_BOTTOM = 1;
    public static final int GRAVITY_LEFT = 2;
    public static final int GRAVITY_RIGHT = 3;

    // transaction
    public static final int DIRECTION_X = 0;
    public static final int DIRECTION_Y = 1;

    // mDialog
    private Dialog mDialog;

    // animation
    private AnimatorSet animatorSetForDialogShow;
    private AnimatorSet animatorSetForDialogDismiss;
    private List<Animator> objectAnimatorsForDialogShow;
    private List<Animator> objectAnimatorsForDialogDismiss;

    private int[] location;
    private int gravity;
    private int backgroundColor;

    private View contentView;
    private ImageView ivNarrow;
    private LinearLayout llContent;
    private RelativeLayout rlOutsideBackground;
    private View attachedView;

    private boolean touchOutsideDismiss;

    private OnExtTooltipDismissed onExtTooltipDismissed;
    private OnExtTooltipShow onExtTooltipShow;

    public ExtTooltip(Activity context) {
        initExtTooltip(context);
    }

    /**
     * init mDialog
     *
     * @param context
     */
    private void initExtTooltip(final Activity context) {
        try {
            this.mContext = context;

            LayoutInflater layoutInflater = mContext.getLayoutInflater();
            View dialogView = layoutInflater.inflate(R.layout.tooltip_lay_out, null);
            ViewTreeObserver viewTreeObserver = dialogView.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    relocation(location);
                }
            });

            this.rlOutsideBackground = (RelativeLayout) dialogView.findViewById(R.id.rlOutsideBackground);
            this.setTouchOutsideDismiss(true);
            this.ivNarrow = (ImageView) dialogView.findViewById(R.id.ivNarrow);
            this.llContent = (LinearLayout) dialogView.findViewById(R.id.llContent);
            this.mDialog = new Dialog(context, isFullScreen() ? android.R.style.Theme_Translucent_NoTitleBar_Fullscreen : android.R.style.Theme_Translucent_NoTitleBar);
            this.mDialog.setContentView(dialogView);
            this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    try {
                        if (onExtTooltipDismissed != null) {
                            onExtTooltipDismissed.onDismissed();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            this.mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    try {
                        if (onExtTooltipShow != null) {
                            onExtTooltipShow.onShow();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            this.animatorSetForDialogShow = new AnimatorSet();
            this.animatorSetForDialogDismiss = new AnimatorSet();
            this.objectAnimatorsForDialogShow = new ArrayList<>();
            this.objectAnimatorsForDialogDismiss = new ArrayList<>();

            this.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final View.OnTouchListener outsideBackgroundListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            try {
                if (touchOutsideDismiss && mDialog != null) {
                    onDialogDismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    };

    /**
     * get mDialog instance
     */
    public Dialog getDialog() {
        return mDialog;
    }

    /**
     * config default value
     */
    private void init() {
        try {
            this.setLocation(new int[]{0, 0})
                    .setGravity(GRAVITY_BOTTOM)
                    .setTouchOutsideDismiss(true)
                    .setOutsideColor(Color.TRANSPARENT)
                    .setBackgroundColor(Color.BLUE)
                    .setMatchParent(true)
                    .setMarginLeftAndRight(24, 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set layout view
     *
     * @param layout
     * @return
     */
    public ExtTooltip setExtTooltipLayout(View layout) {
        try {
            if (layout != null) {
                this.contentView = layout;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set layout view from layout id
     *
     * @param layoutResourceId
     * @return
     */
    public ExtTooltip setLayoutResourceId(int layoutResourceId) {
        try {
            View view = mContext.getLayoutInflater().inflate(layoutResourceId, null);
            setExtTooltipLayout(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set location on screen
     *
     * @param location
     * @return
     */
    public ExtTooltip setLocation(int[] location) {
        this.location = location;
        return this;
    }

    /**
     * pin to view
     *
     * @param attachedView
     * @return
     */
    public ExtTooltip setLocationByAttachedView(View attachedView) {
        try {
            if (attachedView != null) {
                this.attachedView = attachedView;
                int[] attachedViewLocation = new int[2];
                attachedView.getLocationOnScreen(attachedViewLocation);
                switch (gravity) {
                    case GRAVITY_BOTTOM:
                        attachedViewLocation[0] += attachedView.getWidth() / 2;
                        attachedViewLocation[1] += attachedView.getHeight();
                        break;
                    case GRAVITY_TOP:
                        attachedViewLocation[0] += attachedView.getWidth() / 2;
                        break;
                    case GRAVITY_LEFT:
                        attachedViewLocation[1] += attachedView.getHeight() / 2;
                        break;
                    case GRAVITY_RIGHT:
                        attachedViewLocation[0] += attachedView.getWidth();
                        attachedViewLocation[1] += attachedView.getHeight() / 2;
                }
                setLocation(attachedViewLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * get view attached
     *
     * @return
     */
    public View getAttachedView() {
        return this.attachedView;
    }

    /**
     * set gravity view
     *
     * @param gravity
     * @return
     */
    public ExtTooltip setGravity(int gravity) {
        try {
            if (gravity != GRAVITY_BOTTOM && gravity != GRAVITY_TOP && gravity != GRAVITY_LEFT && gravity != GRAVITY_RIGHT) {
                gravity = GRAVITY_BOTTOM;
            }

            this.gravity = gravity;
            switch (this.gravity) {
                case GRAVITY_BOTTOM:
                    ivNarrow.setBackgroundResource(R.drawable.bg_ext_tooltip_triangle_bottom);
                    break;
                case GRAVITY_TOP:
                    ivNarrow.setBackgroundResource(R.drawable.bg_ext_tooltip_triangle_top);
                    break;
                case GRAVITY_LEFT:
                    ivNarrow.setBackgroundResource(R.drawable.bg_ext_tooltip_triangle_left);
                    break;
                case GRAVITY_RIGHT:
                    ivNarrow.setBackgroundResource(R.drawable.bg_ext_tooltip_triangle_right);
                    break;
            }

            llContent.setBackgroundResource(R.drawable.bg_ext_tooltip_round_corner);
            if (attachedView != null) {
                this.setLocationByAttachedView(attachedView);
            }

            this.setBackgroundColor(backgroundColor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set layout match parent
     *
     * @param matchParent
     * @return
     */
    public ExtTooltip setMatchParent(boolean matchParent) {
        try {
            ViewGroup.LayoutParams layoutParams = llContent.getLayoutParams();
            layoutParams.width = matchParent ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
            llContent.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set margin left, right view
     *
     * @param left
     * @param right
     * @return
     */
    public ExtTooltip setMarginLeftAndRight(int left, int right) {
        try {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llContent.getLayoutParams();
            layoutParams.setMargins(left, 0, right, 0);
            llContent.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set margin top, bottom layout
     *
     * @param top
     * @param bottom
     * @return
     */
    public ExtTooltip setMarginTopAndBottom(int top, int bottom) {
        try {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llContent.getLayoutParams();
            layoutParams.setMargins(0, top, 0, bottom);
            llContent.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set touch out side to dismiss view
     *
     * @param touchOutsideDismiss
     * @return
     */
    public ExtTooltip setTouchOutsideDismiss(boolean touchOutsideDismiss) {
        try {
            this.touchOutsideDismiss = touchOutsideDismiss;
            if (touchOutsideDismiss) {
                rlOutsideBackground.setOnTouchListener(outsideBackgroundListener);
            } else {
                rlOutsideBackground.setOnTouchListener(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set out side view color
     *
     * @param color
     * @return
     */
    public ExtTooltip setOutsideColor(int color) {
        try {
            rlOutsideBackground.setBackgroundColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set background view color
     *
     * @param color
     * @return
     */
    public ExtTooltip setBackgroundColor(int color) {
        try {
            this.backgroundColor = color;
            LayerDrawable drawableNarrow = (LayerDrawable) ivNarrow.getBackground();
            GradientDrawable shapeNarrow = (GradientDrawable) (((RotateDrawable) drawableNarrow.findDrawableByLayerId(R.id.shape_id)).getDrawable());
            if (shapeNarrow != null) {
                shapeNarrow.setColor(color);
            } else {
                Toast.makeText(mContext, "Shape is null", Toast.LENGTH_SHORT).show();
            }

            GradientDrawable drawableRound = (GradientDrawable) llContent.getBackground();
            if (drawableRound != null) {
                drawableRound.setColor(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * show tooltip
     *
     * @return
     */
    public ExtTooltip show() {
        try {
            if (mDialog != null) {
                if (contentView == null) {
                    throw new RuntimeException("Do you use setExtTooltipLayout() or setLayoutResourceId()?");
                }

                if (llContent.getChildCount() > 0) {
                    llContent.removeAllViews();
                }

                llContent.addView(contentView);
                mDialog.show();
                onDialogShowing();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return this;
    }

    public View getTipViewInstance() {
        return rlOutsideBackground.findViewById(R.id.rlParentForAnimate);
    }

    /**
     * @param direction
     * @param duration
     * @param values
     * @return
     */
    public ExtTooltip setAnimationTranslationShow(int direction, int duration, float... values) {
        return setAnimationTranslation(true, direction, duration, values);
    }

    /**
     * @param direction
     * @param duration
     * @param values
     * @return
     */
    public ExtTooltip setAnimationTranslationDismiss(int direction, int duration, float... values) {
        return setAnimationTranslation(false, direction, duration, values);
    }

    /**
     * @param isShow
     * @param direction
     * @param duration
     * @param values
     * @return
     */
    private ExtTooltip setAnimationTranslation(boolean isShow, int direction, int duration, float... values) {
        try {
            if (direction != DIRECTION_X && direction != DIRECTION_Y) {
                direction = DIRECTION_X;
            }

            String propertyName = "";
            switch (direction) {
                case DIRECTION_X:
                    propertyName = "translationX";
                    break;
                case DIRECTION_Y:
                    propertyName = "translationY";
                    break;
            }

            ObjectAnimator animator = ObjectAnimator.ofFloat(rlOutsideBackground.findViewById(R.id.rlParentForAnimate), propertyName, values)
                    .setDuration(duration);
            if (isShow) {
                objectAnimatorsForDialogShow.add(animator);
            } else {
                objectAnimatorsForDialogDismiss.add(animator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * @param duration
     * @param values
     * @return
     */
    public ExtTooltip setAnimationAlphaShow(int duration, float... values) {
        return setAnimationAlpha(true, duration, values);
    }

    /**
     * @param duration
     * @param values
     * @return
     */
    public ExtTooltip setAnimationAlphaDismiss(int duration, float... values) {
        return setAnimationAlpha(false, duration, values);
    }

    /**
     * @param isShow
     * @param duration
     * @param values
     * @return
     */
    private ExtTooltip setAnimationAlpha(boolean isShow, int duration, float... values) {
        try {
            ObjectAnimator animator = ObjectAnimator.ofFloat(rlOutsideBackground.findViewById(R.id.rlParentForAnimate), "alpha", values).setDuration(duration);
            if (isShow) {
                objectAnimatorsForDialogShow.add(animator);
            } else {
                objectAnimatorsForDialogDismiss.add(animator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    private void onDialogShowing() {
        try {
            if (animatorSetForDialogShow != null && objectAnimatorsForDialogShow != null && objectAnimatorsForDialogShow.size() > 0) {
                animatorSetForDialogShow.playTogether(objectAnimatorsForDialogShow);
                animatorSetForDialogShow.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void onDialogDismiss() {
        try {
            if (animatorSetForDialogDismiss.isRunning()) return;

            if (animatorSetForDialogDismiss != null && objectAnimatorsForDialogDismiss != null && objectAnimatorsForDialogDismiss.size() > 0) {
                animatorSetForDialogDismiss.playTogether(objectAnimatorsForDialogDismiss);
                animatorSetForDialogDismiss.start();
                animatorSetForDialogDismiss.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        try {
                            if (mContext != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    if (!mContext.isDestroyed() && mDialog != null) {
                                        mDialog.dismiss();
                                    }
                                } else {
                                    if (mDialog != null) {
                                        mDialog.dismiss();
                                        mDialog = null;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            } else {
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * dismiss mDialog
     */
    public void dismiss() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                onDialogDismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * relocation position view
     *
     * @param location
     */
    private void relocation(int[] location) {
        try {
            float statusBarHeight = isFullScreen() ? 0.0f : getStatusBarHeight();

            ivNarrow.setX(location[0] - ivNarrow.getWidth() / 2);
            ivNarrow.setY(location[1] - ivNarrow.getHeight() / 2 - statusBarHeight);
            switch (gravity) {
                case GRAVITY_BOTTOM:
                    llContent.setY(location[1] - ivNarrow.getHeight() / 2 - statusBarHeight + ivNarrow.getHeight());
                    break;
                case GRAVITY_TOP:
                    llContent.setY(location[1] - llContent.getHeight() - statusBarHeight - ivNarrow.getHeight() / 2);
                    break;
                case GRAVITY_LEFT:
                    llContent.setX(location[0] - llContent.getWidth() - ivNarrow.getWidth() / 2);
                    break;
                case GRAVITY_RIGHT:
                    llContent.setX(location[0] + ivNarrow.getWidth() / 2);
                    break;
            }

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llContent.getLayoutParams();
            switch (gravity) {
                case GRAVITY_BOTTOM:
                case GRAVITY_TOP:
                    int triangleCenterX = (int) (ivNarrow.getX() + ivNarrow.getWidth() / 2);
                    int contentWidth = llContent.getWidth();
                    int rightMargin = getScreenWidth() - triangleCenterX;
                    int leftMargin = getScreenWidth() - rightMargin;
                    int availableLeftMargin = leftMargin - layoutParams.leftMargin;
                    int availableRightMargin = rightMargin - layoutParams.rightMargin;
                    int x = 0;
                    if (contentWidth / 2 <= availableLeftMargin && contentWidth / 2 <= availableRightMargin) {
                        x = triangleCenterX - contentWidth / 2;
                    } else {
                        if (availableLeftMargin <= availableRightMargin) {
                            x = layoutParams.leftMargin;
                        } else {
                            x = getScreenWidth() - (contentWidth + layoutParams.rightMargin);
                        }
                    }
                    llContent.setX(x);
                    break;
                case GRAVITY_LEFT:
                case GRAVITY_RIGHT:
                    int triangleCenterY = (int) (ivNarrow.getY() + ivNarrow.getHeight() / 2);
                    int contentHeight = llContent.getHeight();
                    int topMargin = triangleCenterY;
                    int bottomMargin = getScreenHeight() - topMargin;
                    int availableTopMargin = topMargin - layoutParams.topMargin;
                    int availableBottomMargin = bottomMargin - layoutParams.bottomMargin;
                    int y = 0;
                    if (contentHeight / 2 <= availableTopMargin && contentHeight / 2 <= availableBottomMargin) {
                        y = triangleCenterY - contentHeight / 2;
                    } else {
                        if (availableTopMargin <= availableBottomMargin) {
                            y = layoutParams.topMargin;
                        } else {
                            y = getScreenHeight() - (contentHeight + layoutParams.topMargin);
                        }
                    }
                    llContent.setY(y);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getScreenWidth() {
        try {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int getScreenHeight() {
        try {
            int statusBarHeight = isFullScreen() ? 0 : getStatusBarHeight();
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            return metrics.heightPixels - statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int getStatusBarHeight() {
        int result = 0;

        try {
            int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = mContext.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean isFullScreen() {
        try {
            int flags = mContext.getWindow().getAttributes().flags;
            boolean flag = false;
            if ((flags & 1024) == 1024) {
                flag = true;
            }

            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ExtTooltip setCancelable(boolean cancelable) {
        try {
            mDialog.setCancelable(cancelable);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public ExtTooltip setOnExtTooltipDismissed(OnExtTooltipDismissed onExtTooltipDismissed) {
        this.onExtTooltipDismissed = onExtTooltipDismissed;
        return this;
    }


    public interface OnExtTooltipDismissed {
        void onDismissed();
    }


    public ExtTooltip setOnExtTooltipShow(OnExtTooltipShow onExtTooltipShow) {
        this.onExtTooltipShow = onExtTooltipShow;
        return this;
    }

    public interface OnExtTooltipShow {
        public void onShow();
    }
}

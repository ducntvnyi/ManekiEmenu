package com.qslib.fragment.animation;

import android.support.annotation.IntDef;
import android.view.animation.Transformation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Move Animation
 * Created by Dang on 5/27/2016.
 */
public class MoveAnimation extends ViewPropertyAnimation {
    @IntDef({UP, DOWN, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Direction {
    }

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    protected
    @Direction
    int mDirection;
    protected boolean mEnter;

    /**
     * Create new Animation.
     *
     * @param direction Direction of animation
     * @param enter     true for Enter / false for Exit
     * @param duration  Duration of Animation
     * @return
     */
    public static MoveAnimation create(@Direction int direction, boolean enter, long duration) {
        try {
            switch (direction) {
                case UP:
                case DOWN:
                    return new VerticalMoveAnimation(direction, enter, duration);
                case LEFT:
                case RIGHT:
                    return new HorizontalMoveAnimation(direction, enter, duration);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private MoveAnimation(@Direction int direction, boolean enter, long duration) {
        try {
            mDirection = direction;
            mEnter = enter;
            setDuration(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class VerticalMoveAnimation extends MoveAnimation {
        private VerticalMoveAnimation(@Direction int direction, boolean enter, long duration) {
            super(direction, enter, duration);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            try {
                float value = mEnter ? (interpolatedTime - 1.0f) : interpolatedTime;
                if (mDirection == DOWN) value *= -1.0f;
                mTranslationY = -value * mHeight;

                super.applyTransformation(interpolatedTime, t);
                applyTransformation(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class HorizontalMoveAnimation extends MoveAnimation {
        private HorizontalMoveAnimation(@Direction int direction, boolean enter, long duration) {
            super(direction, enter, duration);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            try {
                float value = mEnter ? (interpolatedTime - 1.0f) : interpolatedTime;
                if (mDirection == RIGHT) value *= -1.0f;
                mTranslationX = -value * mWidth;

                super.applyTransformation(interpolatedTime, t);
                applyTransformation(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
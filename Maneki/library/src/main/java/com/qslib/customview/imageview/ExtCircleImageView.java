package com.qslib.customview.imageview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.qslib.library.R;

/**
 * Created by Dang on 6/23/2016.
 */
public class ExtCircleImageView extends ImageView {
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLOR_DRAWABLE_DIMENSION = 2;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_FILL_COLOR = Color.TRANSPARENT;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();
    private final Paint mFillPaint = new Paint();

    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    private int mFillColor = DEFAULT_FILL_COLOR;

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mDrawableRadius;
    private float mBorderRadius;

    private ColorFilter mColorFilter;

    private boolean mReady;
    private boolean mSetupPending;
    private boolean mBorderOverlay;
    private boolean mDisableCircularTransformation;

    public ExtCircleImageView(Context context) {
        super(context);
        init();
    }

    public ExtCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        try {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExtCircleImageView, defStyle, 0);
            mBorderWidth = a.getDimensionPixelSize(R.styleable.ExtCircleImageView_extBorderWidth, DEFAULT_BORDER_WIDTH);
            mBorderColor = a.getColor(R.styleable.ExtCircleImageView_extBorderColor, DEFAULT_BORDER_COLOR);
            mBorderOverlay = a.getBoolean(R.styleable.ExtCircleImageView_extBorderOverlay, DEFAULT_BORDER_OVERLAY);
            mFillColor = a.getColor(R.styleable.ExtCircleImageView_extFillColor, DEFAULT_FILL_COLOR);
            a.recycle();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            super.setScaleType(SCALE_TYPE);
            mReady = true;
            if (mSetupPending) {
                setup();
                mSetupPending = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            if (mDisableCircularTransformation) {
                super.onDraw(canvas);
                return;
            }

            if (mBitmap == null) {
                return;
            }

            if (mFillColor != Color.TRANSPARENT) {
                canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mFillPaint);
            }

            canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mBitmapPaint);
            if (mBorderWidth > 0) {
                canvas.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius, mBorderPaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        try {
            if (borderColor == mBorderColor) {
                return;
            }

            mBorderColor = borderColor;
            mBorderPaint.setColor(mBorderColor);
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @deprecated Use {@link #setBorderColor(int)} instead
     */
    @Deprecated
    public void setBorderColorResource(@ColorRes int borderColorRes) {
        try {
            setBorderColor(getContext().getResources().getColor(borderColorRes));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the color drawn behind the circle-shaped drawable.
     *
     * @return The color drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public int getFillColor() {
        return mFillColor;
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColor The color to be drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public void setFillColor(@ColorInt int fillColor) {
        try {
            if (fillColor == mFillColor) {
                return;
            }

            mFillColor = fillColor;
            mFillPaint.setColor(fillColor);
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColorRes The color resource to be resolved to a color and
     *                     drawn behind the drawable
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public void setFillColorResource(@ColorRes int fillColorRes) {
        try {
            setFillColor(getContext().getResources().getColor(fillColorRes));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        try {
            if (borderWidth == mBorderWidth) {
                return;
            }

            mBorderWidth = borderWidth;
            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isBorderOverlay() {
        return mBorderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        try {
            if (borderOverlay == mBorderOverlay) {
                return;
            }

            mBorderOverlay = borderOverlay;
            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDisableCircularTransformation() {
        return mDisableCircularTransformation;
    }

    public void setDisableCircularTransformation(boolean disableCircularTransformation) {
        if (mDisableCircularTransformation == disableCircularTransformation) {
            return;
        }

        mDisableCircularTransformation = disableCircularTransformation;
        initializeBitmap();
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        initializeBitmap();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        try {
            if (cf == mColorFilter) {
                return;
            }

            mColorFilter = cf;
            applyColorFilter();
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ColorFilter getColorFilter() {
        return mColorFilter;
    }

    private void applyColorFilter() {
        if (mBitmapPaint != null) {
            mBitmapPaint.setColorFilter(mColorFilter);
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        try {
            if (drawable == null) {
                return null;
            }

            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void initializeBitmap() {
        try {
            if (mDisableCircularTransformation) {
                mBitmap = null;
            } else {
                mBitmap = getBitmapFromDrawable(getDrawable());
            }
            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        try {
            if (!mReady) {
                mSetupPending = true;
                return;
            }

            if (getWidth() == 0 && getHeight() == 0) {
                return;
            }

            if (mBitmap == null) {
                invalidate();
                return;
            }

            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            mBitmapPaint.setAntiAlias(true);
            mBitmapPaint.setShader(mBitmapShader);

            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(mBorderWidth);

            mFillPaint.setStyle(Paint.Style.FILL);
            mFillPaint.setAntiAlias(true);
            mFillPaint.setColor(mFillColor);

            mBitmapHeight = mBitmap.getHeight();
            mBitmapWidth = mBitmap.getWidth();

            mBorderRect.set(calculateBounds());
            mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2.0f, (mBorderRect.width() - mBorderWidth) / 2.0f);

            mDrawableRect.set(mBorderRect);
            if (!mBorderOverlay && mBorderWidth > 0) {
                mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f);
            }
            mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f);

            applyColorFilter();
            updateShaderMatrix();
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RectF calculateBounds() {
        try {
            int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

            int sideLength = Math.min(availableWidth, availableHeight);

            float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
            float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

            return new RectF(left, top, left + sideLength, top + sideLength);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;
    }

    private void updateShaderMatrix() {
        try {
            float scale;
            float dx = 0;
            float dy = 0;

            mShaderMatrix.set(null);

            if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
                scale = mDrawableRect.height() / (float) mBitmapHeight;
                dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
            } else {
                scale = mDrawableRect.width() / (float) mBitmapWidth;
                dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
            }

            mShaderMatrix.setScale(scale, scale);
            mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);

            mBitmapShader.setLocalMatrix(mShaderMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


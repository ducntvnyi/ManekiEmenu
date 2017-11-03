package com.qslib.customview.imageview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.qslib.library.R;

/**
 * Created by Dang on 7/8/2016.
 */
public class ExtRoundedImageView extends ImageView {
    public static final String TAG = ExtRoundedImageView.class.getSimpleName();

    private static final ScaleType[] sScaleTypeArray = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };

    // Set default scale type to FIT_XY, which is default scale type of
    // original ImageView.
    private ScaleType mScaleType = ScaleType.FIT_XY;

    private float mLeftTopCornerRadius = 0.0f;
    private float mRightTopCornerRadius = 0.0f;
    private float mLeftBottomCornerRadius = 0.0f;
    private float mRightBottomCornerRadius = 0.0f;

    private float mBorderWidth = 0.0f;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);

    private boolean isOval = false;
    private int imageResourceId = 0;
    private float[] mRadius = new float[]{0, 0, 0, 0, 0, 0, 0, 0};

    private Drawable imageDrawable;

    public ExtRoundedImageView(Context context) {
        super(context);
    }

    public ExtRoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtRoundedImageView, defStyle, 0);

            final int index = typedArray.getInt(R.styleable.ExtRoundedImageView_android_scaleType, -1);
            if (index >= 0) setScaleType(sScaleTypeArray[index]);

            mLeftTopCornerRadius = typedArray.getDimensionPixelSize(R.styleable.ExtRoundedImageView_leftTopCornerRadiusRound, 0);
            mRightTopCornerRadius = typedArray.getDimensionPixelSize(R.styleable.ExtRoundedImageView_rightTopCornerRadiusRound, 0);
            mLeftBottomCornerRadius = typedArray.getDimensionPixelSize(R.styleable.ExtRoundedImageView_leftBottomCornerRadiusRound, 0);
            mRightBottomCornerRadius = typedArray.getDimensionPixelSize(R.styleable.ExtRoundedImageView_rightBottomCornerRadiusRound, 0);

            if (mLeftTopCornerRadius < 0.0f || mRightTopCornerRadius < 0.0f
                    || mLeftBottomCornerRadius < 0.0f || mRightBottomCornerRadius < 0.0f) {
                throw new IllegalArgumentException("radius values cannot be negative.");
            }

            mRadius = new float[]{
                    mLeftTopCornerRadius, mLeftTopCornerRadius,
                    mRightTopCornerRadius, mRightTopCornerRadius,
                    mRightBottomCornerRadius, mRightBottomCornerRadius,
                    mLeftBottomCornerRadius, mLeftBottomCornerRadius};

            mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.ExtRoundedImageView_borderWidthRound, 0);
            if (mBorderWidth < 0) {
                throw new IllegalArgumentException("border width cannot be negative.");
            }

            mBorderColor = typedArray.getColorStateList(R.styleable.ExtRoundedImageView_borderColorRound);
            if (mBorderColor == null) {
                mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
            }

            isOval = typedArray.getBoolean(R.styleable.ExtRoundedImageView_ovalRound, false);
            typedArray.recycle();

            updateDrawable();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        try {
            super.setScaleType(scaleType);
            this.mScaleType = scaleType;
            this.updateDrawable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        try {
            this.imageResourceId = 0;
            this.imageDrawable = ExtRoundedCornerDrawable.fromDrawable(drawable, getResources());
            super.setImageDrawable(this.imageDrawable);
            this.updateDrawable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        try {
            this.imageResourceId = 0;
            this.imageDrawable = ExtRoundedCornerDrawable.fromBitmap(bm, getResources());
            super.setImageDrawable(this.imageDrawable);
            this.updateDrawable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setImageResource(int resId) {
        try {
            if (this.imageResourceId != resId) {
                this.imageResourceId = resId;
                this.imageDrawable = resolveResource();
                super.setImageDrawable(this.imageDrawable);
                this.updateDrawable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        try {
            super.setImageURI(uri);
            this.setImageDrawable(getDrawable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Drawable resolveResource() {
        try {
            Resources resources = getResources();
            if (resources == null) return null;

            Drawable drawable = null;
            if (this.imageResourceId != 0) {
                try {
                    drawable = resources.getDrawable(this.imageResourceId);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    this.imageResourceId = 0;
                }
            }

            return ExtRoundedCornerDrawable.fromDrawable(drawable, resources);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void updateDrawable() {
        try {
            if (this.imageDrawable == null) return;
            ((ExtRoundedCornerDrawable) this.imageDrawable).setScaleType(mScaleType);
            ((ExtRoundedCornerDrawable) this.imageDrawable).setCornerRadii(mRadius);
            ((ExtRoundedCornerDrawable) this.imageDrawable).setBorderWidth(mBorderWidth);
            ((ExtRoundedCornerDrawable) this.imageDrawable).setBorderColor(mBorderColor);
            ((ExtRoundedCornerDrawable) this.imageDrawable).setOval(isOval);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getCornerRadius() {
        return mLeftTopCornerRadius;
    }

    /**
     * Set radii for each corner.
     *
     * @param leftTop     The desired radius for left-top corner in dip.
     * @param rightTop    The desired desired radius for right-top corner in dip.
     * @param leftBottom  The desired radius for left-bottom corner in dip.
     * @param rightBottom The desired radius for right-bottom corner in dip.
     */
    public void setCornerRadiiDP(float leftTop, float rightTop, float leftBottom, float rightBottom) {
        try {
            final float density = getResources().getDisplayMetrics().density;

            final float lt = leftTop * density;
            final float rt = rightTop * density;
            final float lb = leftBottom * density;
            final float rb = rightBottom * density;

            this.mRadius = new float[]{lt, lt, rt, rt, rb, rb, lb, lb};
            this.updateDrawable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * Set border width.
     *
     * @param width The desired width in dip.
     */
    public void setBorderWidthDP(float width) {
        try {
            float scaledWidth = getResources().getDisplayMetrics().density * width;
            if (this.mBorderWidth == scaledWidth) return;

            this.mBorderWidth = scaledWidth;
            this.updateDrawable();
            this.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }

    public void setBorderColor(int color) {
        setBorderColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return mBorderColor;
    }

    public void setBorderColor(ColorStateList colors) {
        try {
            if (this.mBorderColor.equals(colors)) return;

            this.mBorderColor = (colors != null) ? colors : ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
            this.updateDrawable();
            if (this.mBorderWidth > 0) {
                this.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOval() {
        return isOval;
    }

    public void setOval(boolean oval) {
        try {
            this.isOval = oval;
            this.updateDrawable();
            this.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ExtRoundedCornerDrawable extends Drawable {
        private static final String TAG = ExtRoundedCornerDrawable.class.getSimpleName();

        private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

        private RectF mBounds = new RectF();
        private RectF mBorderBounds = new RectF();
        private final RectF mBitmapRect = new RectF();

        private final int mBitmapWidth;
        private final int mBitmapHeight;

        private final Paint mBitmapPaint;
        private final Paint mBorderPaint;

        private BitmapShader mBitmapShader;

        private float[] mRadius = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
        private float[] mBorderRadius = new float[]{0, 0, 0, 0, 0, 0, 0, 0};

        private boolean mOval = false;

        private float mBorderWidth = 0;
        private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
        // Set default scale type to FIT_XY, which is default scale type of
        // original ImageView.
        private ScaleType mScaleType = ScaleType.FIT_XY;

        private Path mPath = new Path();
        private Bitmap mBitmap;
        private boolean mBoundsConfigured = false;

        public ExtRoundedCornerDrawable(Bitmap bitmap, Resources resources) {
            this.mBitmap = bitmap;
            this.mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            if (bitmap != null) {
                this.mBitmapWidth = bitmap.getScaledWidth(resources.getDisplayMetrics());
                this.mBitmapHeight = bitmap.getScaledHeight(resources.getDisplayMetrics());
            } else {
                this.mBitmapWidth = this.mBitmapHeight = -1;
            }

            this.mBitmapRect.set(0, 0, this.mBitmapWidth, this.mBitmapHeight);

            this.mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.mBitmapPaint.setStyle(Paint.Style.FILL);
            this.mBitmapPaint.setShader(this.mBitmapShader);

            this.mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.mBorderPaint.setStyle(Paint.Style.STROKE);
            this.mBorderPaint.setColor(this.mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
            this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
        }

        public static ExtRoundedCornerDrawable fromBitmap(Bitmap bitmap, Resources resources) {
            if (bitmap != null) {
                return new ExtRoundedCornerDrawable(bitmap, resources);
            }

            return null;
        }

        public static Drawable fromDrawable(Drawable drawable, Resources resources) {
            try {
                if (drawable != null) {
                    if (drawable instanceof ExtRoundedCornerDrawable) {
                        return drawable;
                    } else if (drawable instanceof LayerDrawable) {
                        LayerDrawable layerDrawable = (LayerDrawable) drawable;

                        for (int i = 0; i < layerDrawable.getNumberOfLayers(); i++) {
                            Drawable d = layerDrawable.getDrawable(i);
                            layerDrawable.setDrawableByLayerId(layerDrawable.getId(i), fromDrawable(d, resources));
                        }

                        return layerDrawable;
                    }

                    Bitmap bm = drawableToBitmap(drawable);
                    if (bm != null) {
                        return new ExtRoundedCornerDrawable(bm, resources);
                    } else {
                        Log.w(TAG, "Failed to create bitmap from drawable!");
                    }
                }

                return drawable;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        public static Bitmap drawableToBitmap(Drawable drawable) {
            if (drawable == null) return null;

            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            Bitmap bitmap;
            int width = Math.max(drawable.getIntrinsicWidth(), 2);
            int height = Math.max(drawable.getIntrinsicHeight(), 2);

            try {
                bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                bitmap = null;
            }

            return bitmap;
        }

        @Override
        public boolean isStateful() {
            return mBorderColor.isStateful();
        }

        @Override
        protected boolean onStateChange(int[] state) {
            int newColor = mBorderColor.getColorForState(state, 0);
            if (mBorderPaint.getColor() != newColor) {
                mBorderPaint.setColor(newColor);
                return true;
            } else {
                return super.onStateChange(state);
            }
        }

        private void configureBounds(Canvas canvas) {
            try {
                // I have discovered a truly marvelous explanation of this,
                // which this comment space is too narrow to contain. :)
                // If you want to understand what's going on here,
                // See http://www.joooooooooonhokim.com/?p=289
                Rect clipBounds = canvas.getClipBounds();
                Matrix canvasMatrix = canvas.getMatrix();

                if (ScaleType.CENTER == this.mScaleType) {
                    this.mBounds.set(clipBounds);
                } else if (ScaleType.CENTER_CROP == this.mScaleType) {
                    applyScaleToRadius(canvasMatrix);
                    this.mBounds.set(clipBounds);
                } else if (ScaleType.FIT_XY == this.mScaleType) {
                    Matrix m = new Matrix();
                    m.setRectToRect(mBitmapRect, new RectF(clipBounds), Matrix.ScaleToFit.FILL);
                    this.mBitmapShader.setLocalMatrix(m);
                    this.mBounds.set(clipBounds);
                } else if (ScaleType.FIT_START == this.mScaleType || ScaleType.FIT_END == this.mScaleType
                        || ScaleType.FIT_CENTER == this.mScaleType || ScaleType.CENTER_INSIDE == this.mScaleType) {
                    applyScaleToRadius(canvasMatrix);
                    this.mBounds.set(this.mBitmapRect);
                } else if (ScaleType.MATRIX == this.mScaleType) {
                    applyScaleToRadius(canvasMatrix);
                    this.mBounds.set(this.mBitmapRect);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void applyScaleToRadius(Matrix matrix) {
            try {
                float[] values = new float[9];
                matrix.getValues(values);
                for (int i = 0; i < mRadius.length; i++) {
                    mRadius[i] = mRadius[i] / values[0];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void adjustCanvasForBorder(Canvas canvas) {
            try {
                Matrix canvasMatrix = canvas.getMatrix();
                final float[] values = new float[9];
                canvasMatrix.getValues(values);

                final float scaleFactorX = values[0];
                final float scaleFactorY = values[4];
                final float translateX = values[2];
                final float translateY = values[5];

                final float newScaleX = this.mBounds.width()
                        / (this.mBounds.width() + this.mBorderWidth + this.mBorderWidth);
                final float newScaleY = mBounds.height()
                        / (this.mBounds.height() + this.mBorderWidth + this.mBorderWidth);

                canvas.scale(newScaleX, newScaleY);
                if (ScaleType.FIT_START == this.mScaleType || ScaleType.FIT_END == this.mScaleType
                        || ScaleType.FIT_XY == this.mScaleType || ScaleType.FIT_CENTER == this.mScaleType
                        || ScaleType.CENTER_INSIDE == this.mScaleType || ScaleType.MATRIX == this.mScaleType) {
                    canvas.translate(this.mBorderWidth, this.mBorderWidth);
                } else if (ScaleType.CENTER == this.mScaleType || ScaleType.CENTER_CROP == this.mScaleType) {
                    // First, make translate values to 0
                    canvas.translate(
                            -translateX / (newScaleX * scaleFactorX),
                            -translateY / (newScaleY * scaleFactorY));
                    // Then, set the final translate values.
                    canvas.translate(-(this.mBounds.left - this.mBorderWidth), -(this.mBounds.top - this.mBorderWidth));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void adjustBorderWidthAndBorderBounds(Canvas canvas) {
            try {
                Matrix canvasMatrix = canvas.getMatrix();
                final float[] values = new float[9];
                canvasMatrix.getValues(values);

                final float scaleFactor = values[0];

                float viewWidth = this.mBounds.width() * scaleFactor;
                this.mBorderWidth = (this.mBorderWidth * this.mBounds.width()) / (viewWidth - (2 * this.mBorderWidth));
                this.mBorderPaint.setStrokeWidth(this.mBorderWidth);

                this.mBorderBounds.set(this.mBounds);
                this.mBorderBounds.inset(-this.mBorderWidth / 2, -this.mBorderWidth / 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void setBorderRadii() {
            try {
                for (int i = 0; i < this.mRadius.length; i++) {
                    if (this.mRadius[i] > 0) {
                        this.mBorderRadius[i] = this.mRadius[i];
                        this.mRadius[i] = this.mRadius[i] - this.mBorderWidth;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void draw(Canvas canvas) {
            try {
                canvas.save();
                if (!this.mBoundsConfigured) {
                    configureBounds(canvas);
                    if (this.mBorderWidth > 0) {
                        this.adjustBorderWidthAndBorderBounds(canvas);
                        this.setBorderRadii();
                    }
                    this.mBoundsConfigured = true;
                }

                if (mOval) {
                    if (mBorderWidth > 0) {
                        adjustCanvasForBorder(canvas);
                        mPath.addOval(mBounds, Path.Direction.CW);
                        canvas.drawPath(mPath, mBitmapPaint);
                        mPath.reset();
                        mPath.addOval(mBorderBounds, Path.Direction.CW);
                        canvas.drawPath(mPath, mBorderPaint);
                    } else {
                        mPath.addOval(mBounds, Path.Direction.CW);
                        canvas.drawPath(mPath, mBitmapPaint);
                    }
                } else {
                    if (mBorderWidth > 0) {
                        adjustCanvasForBorder(canvas);
                        mPath.addRoundRect(mBounds, mRadius, Path.Direction.CW);
                        canvas.drawPath(mPath, mBitmapPaint);
                        mPath.reset();
                        mPath.addRoundRect(mBorderBounds, mBorderRadius, Path.Direction.CW);
                        canvas.drawPath(mPath, mBorderPaint);
                    } else {
                        mPath.addRoundRect(mBounds, mRadius, Path.Direction.CW);
                        canvas.drawPath(mPath, mBitmapPaint);
                    }
                }
                canvas.restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setCornerRadii(float[] radius) {
            try {
                if (radius == null) return;

                if (radius.length != 8) {
                    throw new ArrayIndexOutOfBoundsException("radii[] needs 8 values");
                }

                for (int i = 0; i < radius.length; i++) {
                    mRadius[i] = radius[i];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getOpacity() {
            try {
                return (mBitmap == null || mBitmap.hasAlpha() || mBitmapPaint.getAlpha() < 255) ? PixelFormat.TRANSLUCENT
                        : PixelFormat.OPAQUE;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        public void setAlpha(int alpha) {
            try {
                this.mBitmapPaint.setAlpha(alpha);
                this.invalidateSelf();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            try {
                this.mBitmapPaint.setColorFilter(cf);
                this.invalidateSelf();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setDither(boolean dither) {
            try {
                this.mBitmapPaint.setDither(dither);
                this.invalidateSelf();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setFilterBitmap(boolean filter) {
            try {
                this.mBitmapPaint.setFilterBitmap(filter);
                this.invalidateSelf();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getIntrinsicWidth() {
            return mBitmapWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mBitmapHeight;
        }

        public float getBorderWidth() {
            return mBorderWidth;
        }

        public void setBorderWidth(float width) {
            this.mBorderWidth = width;
            this.mBorderPaint.setStrokeWidth(width);
        }

        public int getBorderColor() {
            return mBorderColor.getDefaultColor();
        }

        public void setBorderColor(int color) {
            this.setBorderColor(ColorStateList.valueOf(color));
        }

        public ColorStateList getBorderColors() {
            return mBorderColor;
        }

        /**
         * Controls border color of this ImageView.
         *
         * @param colors The desired border color. If it's null, no border will be
         *               drawn.
         */
        public void setBorderColor(ColorStateList colors) {
            try {
                if (colors == null) {
                    this.mBorderWidth = 0;
                    this.mBorderColor = ColorStateList.valueOf(Color.TRANSPARENT);
                    this.mBorderPaint.setColor(Color.TRANSPARENT);
                } else {
                    this.mBorderColor = colors;
                    this.mBorderPaint.setColor(mBorderColor.getColorForState(getState(),
                            DEFAULT_BORDER_COLOR));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean isOval() {
            return mOval;
        }

        public void setOval(boolean oval) {
            this.mOval = oval;
        }

        public ScaleType getScaleType() {
            return mScaleType;
        }

        public void setScaleType(ScaleType scaleType) {
            try {
                if (scaleType == null) return;
                this.mScaleType = scaleType;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
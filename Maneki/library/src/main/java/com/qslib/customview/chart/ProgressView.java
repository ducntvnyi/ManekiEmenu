package com.qslib.customview.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by tuyennm on 8/19/2016.
 */
public class ProgressView extends SquareLayout {
    private static final String TAG = ProgressView.class.getSimpleName();

    private Paint mArcPaint;
    private RectF rectArc;
    private RectF rectCircle;
    private double progress;
    private double maxValue = 100;
    private int paddingDefault = 2;
    private int progressColor;
    private Paint mCirclePaint;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        try {
            this.rectCircle = new RectF();
            this.rectArc = new RectF();

            this.setWillNotDraw(false);

            this.mArcPaint = new Paint() {
                {
                    setDither(true);
                    setStyle(Style.STROKE);
                    setStrokeCap(Cap.ROUND);
                    setStrokeJoin(Join.BEVEL);
                    setStrokeWidth(PixelUtils.convertDpToPixel(getContext(), 3));
                    setColor(progressColor);
                    setAntiAlias(true);
                }
            };

            this.mCirclePaint = new Paint() {
                {
                    setDither(true);
                    setStyle(Style.STROKE);
                    setStrokeCap(Cap.ROUND);
                    setStrokeJoin(Join.BEVEL);
                    setColor(progressColor);
                    setStrokeWidth(PixelUtils.convertDpToPixel(getContext(), 1));
                    setAntiAlias(true);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            int canvasWidth = canvas.getWidth();
            int canvasHeight = canvas.getHeight();
            // int centerX = canvasWidth >> 1;
            // int centerY = canvasHeight >> 1;
            int radius = canvasWidth > canvasHeight ? canvasHeight >> 1 : canvasWidth >> 1;

            float arcStrokeWidth = mArcPaint.getStrokeWidth();
            // this.rectArc.set(centerX - radius + arcStrokeWidth, centerY - radius + arcStrokeWidth, centerX + radius - arcStrokeWidth, centerY + radius - arcStrokeWidth);

            canvas.drawCircle(canvasWidth >> 1, canvasHeight >> 1, radius - arcStrokeWidth - PixelUtils.convertDpToPixel(getContext(), 3), mCirclePaint);
            canvas.drawArc(rectArc, -90, (float) (progress / maxValue * 360), false, mArcPaint);

            if (rectArc != null && mArcPaint != null)
                canvas.drawArc(rectArc, -90, (float) (progress / maxValue * 360), false, mArcPaint);
            if (rectCircle != null && mCirclePaint != null)
                canvas.drawArc(rectCircle, -90, 360, false, mCirclePaint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        try {
            int padding = (int) PixelUtils.convertDpToPixel(getContext(), paddingDefault);
            this.setPadding(padding, padding, padding, padding);
            this.rectCircle.set(getPaddingLeft() + mArcPaint.getStrokeWidth(),
                    getPaddingTop() + mArcPaint.getStrokeWidth(),
                    w - getPaddingRight() - mArcPaint.getStrokeWidth(),
                    h - getPaddingBottom() - mArcPaint.getStrokeWidth());
            this.rectArc.set(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgress(double progress) {
        try {
            this.progress = progress;
            this.invalidate();
            this.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMaxValue(double maxValue) {
        try {
            this.maxValue = maxValue;
            this.invalidate();
            this.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public double getProgress() {
        return this.progress;
    }

    public int getProgressColor() {
        return this.progressColor;
    }

    public void setProgressColor(int progressColor) {
        try {
            this.progressColor = progressColor;
            this.mArcPaint.setColor(progressColor);
            this.mCirclePaint.setColor(progressColor);
            this.invalidate();
            this.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

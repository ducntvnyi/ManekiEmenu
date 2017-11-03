package com.qslib.customview.chart;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qslib.library.R;

/**
 * Created by tuyennm on 8/17/2016.
 */
public class CircleChart extends FrameLayout implements ValueAnimator.AnimatorUpdateListener {
    private static final String TAG = CircleChart.class.getSimpleName();
    private static final long DEFAULT_ANIMATION_DURATION = 1000;

    private ProgressView progressView;
    private TextView txtPercentText;
    private TextView txtTitle;
    private float progress;
    private String title;
    private int maxValue;
    private int progressColor;
    private TextView txtProgress;
    private String description;
    private TextView txtDescription;
    private ValueAnimator valueAnimator;
    private boolean bAnimation;
    private long animDuration = DEFAULT_ANIMATION_DURATION;

    public CircleChart(Context context) {
        super(context);
        init();
    }

    public CircleChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init() {
        init(null);
    }

    private void init(AttributeSet attrs) {
        try {
            inflate(getContext(), R.layout.circle_chart, this);

            this.progressView = (ProgressView) findViewById(R.id.progress_view);
            this.txtPercentText = (TextView) findViewById(R.id.txt_percent_text);
            this.txtTitle = (TextView) findViewById(R.id.txt_title);
            this.txtProgress = (TextView) findViewById(R.id.txt_progress);
            this.txtDescription = (TextView) findViewById(R.id.txt_description);

            if (attrs != null) {
                TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ArcChart, 0, 0);

                title = typedArray.getString(R.styleable.ArcChart_title);
                description = typedArray.getString(R.styleable.ArcChart_description);
                progress = typedArray.getInt(R.styleable.ArcChart_progress, 0);
                maxValue = typedArray.getInt(R.styleable.ArcChart_max_value, 100);
                bAnimation = typedArray.getBoolean(R.styleable.ArcChart_animation, true);
                progressColor = typedArray.getColor(R.styleable.ArcChart_progress_color, getResources().getColor(R.color.progress_default_color));

                GradientDrawable titleDrawable = (GradientDrawable) typedArray.getResources().getDrawable(R.drawable.shape_title);
                titleDrawable.setColor(progressColor);
                txtTitle.setBackgroundDrawable(titleDrawable);

                progressView.setProgress(progress);
                progressView.setMaxValue(maxValue);
                progressView.setProgressColor(progressColor);

                updateUI();
                typedArray.recycle();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateProgress(int progress) {
        try {
            if (valueAnimator != null && valueAnimator.isRunning()) {
                valueAnimator.end();
            }

            if (bAnimation) {
                valueAnimator = ValueAnimator.ofFloat(this.progress, progress);
                valueAnimator.setDuration(animDuration);
                valueAnimator.addUpdateListener(this);
                valueAnimator.start();
                updateUI();
            } else {
                setProgress(progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgress(int progress) {
        try {
            if (progress > maxValue) this.progress = maxValue;
            else this.progress = progress;

            progressView.setProgress(progress);
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getProgress() {
        return progress;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        try {
            this.maxValue = maxValue;
            progressView.setMaxValue(maxValue);
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {
        try {
            txtPercentText.setText((int) ((double) (this.progress / this.maxValue) * 100) + getContext().getString(R.string.percent_lable));
            txtProgress.setText(String.valueOf((int) this.progress));
            if (title != null) txtTitle.setText(title);
            if (description != null) txtDescription.setText(description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUIView(float total, float value) {
        try {
            this.progress =Math.round ((value / total) * 100);
            Log.e("UpdateUIView", "progress:: " + this.progress);
            txtPercentText.setText((int) this.progress + getContext().getString(R.string.percent_lable));
            txtProgress.setText(String.valueOf((int) value));
            if (this.progress > maxValue) this.progress = maxValue;
            progressView.setProgress((int) this.progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        this.title = title;
        updateUI();
    }

    public void setProgressColor(int progressColor) {
        try {
            this.progressColor = progressColor;
            progressView.setProgressColor(progressColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDescription(String description) {
        this.description = description;
        updateUI();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        try {
            float intermediateValue = (float) valueAnimator.getAnimatedValue();
            Log.e(TAG, intermediateValue + "");
            setProgress((int) intermediateValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getAnimDuration() {
        return animDuration;
    }

    public void setAnimDuration(long animDuration) {
        this.animDuration = animDuration;
    }
}

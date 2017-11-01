package com.qslib.viewanimation.specials.out;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;
import com.qslib.viewanimation.BaseViewAnimator;
import com.qslib.viewanimation.easing.Glider;
import com.qslib.viewanimation.easing.Skill;

public class TakingOffAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        try {
            getAnimatorAgent().playTogether(
                    Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1f, 1.5f)),
                    Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1f, 1.5f)),
                    Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 1, 0))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

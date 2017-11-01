package com.qslib.viewanimation.specials.in;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;
import com.qslib.viewanimation.BaseViewAnimator;
import com.qslib.viewanimation.easing.Glider;
import com.qslib.viewanimation.easing.Skill;

public class DropOutAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        try {
            int distance = target.getTop() + target.getHeight();
            getAnimatorAgent().playTogether(
                    ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                    Glider.glide(Skill.BounceEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "translationY", -distance, 0))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

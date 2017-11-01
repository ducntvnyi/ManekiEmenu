package com.qslib.recycleview.animator.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

@SuppressWarnings("ALL")
public class SlideInRightAnimationAdapter extends AnimationAdapter {
  public SlideInRightAnimationAdapter(RecyclerView.Adapter adapter) {
    super(adapter);
  }

  @Override protected Animator[] getAnimators(View view) {
    return new Animator[] {
        ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
    };
  }
}

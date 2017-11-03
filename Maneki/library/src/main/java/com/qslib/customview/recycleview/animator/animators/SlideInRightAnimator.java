package com.qslib.customview.recycleview.animator.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;

public class SlideInRightAnimator extends BaseItemAnimator {
  public SlideInRightAnimator() {

  }

  public SlideInRightAnimator(Interpolator interpolator) {
    mInterpolator = interpolator;
  }

  @Override protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
    try {
      ViewCompat.animate(holder.itemView)
          .translationX(holder.itemView.getRootView().getWidth())
          .setDuration(getRemoveDuration())
          .setInterpolator(mInterpolator)
          .setListener(new DefaultRemoveVpaListener(holder))
          .setStartDelay(getRemoveDelay(holder))
          .start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
    try {
      ViewCompat.setTranslationX(holder.itemView, holder.itemView.getRootView().getWidth());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
    try {
      ViewCompat.animate(holder.itemView)
          .translationX(0)
          .setDuration(getAddDuration())
          .setInterpolator(mInterpolator)
          .setListener(new DefaultAddVpaListener(holder))
          .setStartDelay(getAddDelay(holder))
          .start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

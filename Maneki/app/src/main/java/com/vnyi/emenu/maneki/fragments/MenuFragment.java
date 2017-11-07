package com.vnyi.emenu.maneki.fragments;


import android.animation.Animator;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.MenuAdapter;
import com.vnyi.emenu.maneki.customviews.ButtonFont;
import com.vnyi.emenu.maneki.customviews.CartAnimationUtil;
import com.vnyi.emenu.maneki.models.AnimationView;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.utils.ViewUtils;
import com.vnyi.emenu.maneki.utils.VyniUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import java8.util.stream.StreamSupport;

public class MenuFragment extends BaseFragment {


    @BindView(R.id.btnViewOrder)
    ButtonFont btnViewOrder;
    @BindView(R.id.ivShowConfig)
    ImageView ivShowConfig;
    @BindView(R.id.llMenuLeft)
    View llMenuLeft;
    @BindView(R.id.tvCart)
    TextView tvCart;
    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    @BindView(R.id.ivPrevious)
    ImageView ivPrevious;
    @BindView(R.id.ivNext)
    ImageView ivNext;

    private int itemCounter = 0;
    private MenuAdapter mMenuAdapter;
    private List<Branch> mBranches;
    private AnimationView mAnimationView;
    LinearLayoutManager mLayoutManager;
    private int position;

    public static Fragment newInstance() {
        Fragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.menu_fragment;
    }

    @Override
    public void initViews() {
        try {
            RxTextView.textChanges(tvCart)
                    .debounce(5, TimeUnit.SECONDS)
                    .observeOn(Schedulers.io())
                    .subscribe(charSequence -> {
                        // call api
                        Log.e(TAG, "==> Cart:: " + charSequence);
                    });

            // init menu adapter
            mBranches = new ArrayList<>();
            mMenuAdapter = new MenuAdapter(mContext, mBranches, branchConsumer -> {
                position = branchConsumer.getPosition();
                selectMenu(branchConsumer);

            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvMenu.setAdapter(mMenuAdapter);
            rvMenu.setLayoutManager(layoutManager);

        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }


    }

    @Override
    public void initData() {

        try {
            mBranches.add(new Branch(0, "Combo"));
            mBranches.add(new Branch(1, "Combo"));
            mBranches.add(new Branch(2, "Ga"));
            mBranches.add(new Branch(3, "Bo"));
            mBranches.add(new Branch(4, "hai san"));
            mBranches.add(new Branch(5, "Ga nam"));
            mBranches.add(new Branch(6, "Lon quan"));
            mBranches.add(new Branch(7, "Vit Hap"));
            mBranches.add(new Branch(8, "Bo Nam"));
            mBranches.add(new Branch(9, "Hai San 3 mien"));
            mBranches.add(new Branch(10, "Ruou vang"));
            mMenuAdapter.setBranchList(mBranches);
            rvMenu.setAdapter(mMenuAdapter);
            position = 0;
            selectMenu(mBranches.get(position));
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }
    }

    @OnClick(R.id.ivShowConfig)
    void onClickShowConfig() {
//        mActivity.changeTab(Constant.INDEX_CONFIG);
        makeFlyAnimation(ivShowConfig);
    }

    /**
     * add to cart with animation
     *
     * @param imageView ( setBackground temp)
     * @param view      (get bitmap)
     */

    void onClickAddToCart(ImageView imageView, View view) {
        try {
            rvMenu.postDelayed(() -> {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), ViewUtils.getBitmapFromView(view));
                imageView.setBackground(bitmapDrawable);
                makeFlyAnimation(imageView);
                imageView.setBackgroundColor(mActivity.getColorResource(R.color.color_transparent));
            }, 100);
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }


    }

    @OnClick(R.id.btnViewOrder)
    void onClickViewOrder() {
        try {
            DialogConfirmOrderFragment.newInstance().show(getFragmentManager(), "");
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }
    }

    @OnClick(R.id.ivPrevious)
    void onPreviousMenu() {

        try {
            Branch branch = null;
            if (position == 0) {
                branch = mBranches.get(mBranches.size() - 1);
                position = mBranches.size() - 1;
            } else {
                branch = mBranches.get(position - 1);
                --position;
            }
            selectMenu(branch);
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }

    }

    @OnClick(R.id.ivNext)
    void onNextMenu() {
        try {
            Branch branch = null;
            if (position == (mBranches.size() - 1)) {
                branch = mBranches.get(0);
                position = 0;
            } else {
                branch = mBranches.get(position + 1);
                ++position;
            }
            selectMenu(branch);
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }

    }

    @OnClick(R.id.ivDown)
    void onDownMenu() {
        onNextMenu();
    }


    private void makeFlyAnimation(ImageView targetView) {

        try {
            new CartAnimationUtil().attachActivity(mActivity).setTargetView(targetView).setMoveDuration(700).setDestView(btnViewOrder).setAnimationListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //                addItemToCart();
                    Toast.makeText(mContext, "Continue Shopping...", Toast.LENGTH_SHORT).show();
                    tvCart.setText("" + (++itemCounter) + "");

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).startAnimation();
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }


    }

    private void selectMenu(Branch branchSelected) {
        try {
            if (branchSelected == null) return;
            StreamSupport.stream(mBranches).forEach(branch -> branch.setSelected(branch.getBranchId() == branchSelected.getBranchId()));
            mMenuAdapter.notifyDataSetChanged();
            rvMenu.smoothScrollToPosition(position);
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }
    }


}

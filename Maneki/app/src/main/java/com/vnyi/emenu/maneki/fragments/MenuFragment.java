package com.vnyi.emenu.maneki.fragments;


import android.animation.Animator;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.ItemAdapter;
import com.vnyi.emenu.maneki.adapters.MenuAdapter;
import com.vnyi.emenu.maneki.customviews.ButtonFont;
import com.vnyi.emenu.maneki.customviews.CartAnimationUtil;
import com.vnyi.emenu.maneki.models.AnimationView;
import com.vnyi.emenu.maneki.models.ItemModel;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.ViewUtils;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    @BindView(R.id.rvItem)
    RecyclerView rvItem;
    @BindView(R.id.ivPrevious)
    ImageView ivPrevious;
    @BindView(R.id.ivNext)
    ImageView ivNext;

    private int itemCounter = 0;
    private MenuAdapter mMenuAdapter;
    private List<Branch> mBranches;

    private ItemAdapter mItemAdapter;
    private List<ItemModel> mItemModels;

    private AnimationView mAnimationView;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGridLayoutManager;
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

            debounceOrderItem();
            // init menu adapter
            mBranches = new ArrayList<>();
            mMenuAdapter = new MenuAdapter(mContext, mBranches, branchConsumer -> {
                position = branchConsumer.getPosition();
                selectMenu(branchConsumer);

            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvMenu.setAdapter(mMenuAdapter);
            rvMenu.setLayoutManager(layoutManager);

            // init item adapter
            mItemModels = new ArrayList<>();
            mItemAdapter = new ItemAdapter(mContext, mItemModels, animationView -> {
                onClickAddToCart(animationView.getImageView(), animationView.getView());

            });
            mGridLayoutManager = new GridLayoutManager(mContext, 4);
            rvItem.setAdapter(mItemAdapter);
            rvItem.setLayoutManager(mGridLayoutManager);

        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }


    }

    private void debounceOrderItem() {
        try {
            RxTextView.textChanges(tvCart)
                    .debounce(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CharSequence>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(CharSequence value) {
                            updateItem(value);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
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

            mItemModels.add(new ItemModel(1, "Hai san", "69.000 Vnd"));
            mItemModels.add(new ItemModel(2, "Sushi ca ngu", "100.000 Vnd"));
            mItemModels.add(new ItemModel(3, "Sushi ca ngu Hai san 1223", "111.000 Vnd"));
            mItemModels.add(new ItemModel(4, "Hai san", "90.000 Vnd"));
            mItemModels.add(new ItemModel(5, "Hai san", "60.000 Vnd"));
            mItemModels.add(new ItemModel(6, "Hai san", "50.000 Vnd"));
            mItemModels.add(new ItemModel(7, "Hai san", "120.000 Vnd"));
            mItemModels.add(new ItemModel(8, "Hai san", "104.000 Vnd"));
            mItemModels.add(new ItemModel(9, "Hai san", "200.000 Vnd"));
            mItemModels.add(new ItemModel(10, "Hai san", "99.000 Vnd"));
            mItemModels.add(new ItemModel(11, "Hai san", "69.000 Vnd"));
            mItemModels.add(new ItemModel(12, "Sushi ca ngu", "100.000 Vnd"));
            mItemModels.add(new ItemModel(13, "Sushi ca ngu", "222.000 Vnd"));
            mItemModels.add(new ItemModel(14, "Hai san", "90.000 Vnd"));
            mItemModels.add(new ItemModel(15, "Hai san", "60.000 Vnd"));
            mItemModels.add(new ItemModel(16, "Hai san", "50.000 Vnd"));
            mItemModels.add(new ItemModel(17, "Hai san", "120.000 Vnd"));
            mItemModels.add(new ItemModel(18, "Hai san", "104.000 Vnd"));
            mItemModels.add(new ItemModel(19, "Hai san", "200.000 Vnd"));
            mItemModels.add(new ItemModel(20, "Hai san", "99.000 Vnd"));
            mItemAdapter.setItemModelList(mItemModels);

        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    @OnClick(R.id.ivShowConfig)
    void onClickShowConfig() {
        mActivity.changeTab(Constant.INDEX_CONFIG);
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
                imageView.setVisibility(View.GONE);
            }, 100);
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }


    }

    @OnClick(R.id.btnViewOrder)
    void onClickViewOrder() {
        try {
            DialogConfirmOrderFragment.newInstance().show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
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
            VnyiUtils.LogException(TAG, e);
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
            VnyiUtils.LogException(TAG, e);
        }

    }

    @OnClick(R.id.ivDown)
    void onDownMenu() {
        onNextMenu();
    }


    private void makeFlyAnimation(ImageView targetView) {

        try {
            new CartAnimationUtil().attachActivity(mActivity).setTargetView(targetView).setMoveDuration(500).setDestView(btnViewOrder).setAnimationListener(new Animator.AnimatorListener() {
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
            VnyiUtils.LogException(TAG, e);
        }


    }

    private void selectMenu(Branch branchSelected) {
        try {
            if (branchSelected == null) return;
            StreamSupport.stream(mBranches).forEach(branch -> branch.setSelected(branch.getBranchId() == branchSelected.getBranchId()));
//            Observable.just(mBranches).concatMap(Observable::fromArray).forEach(branches -> branches.forEach(branch -> branch.setSelected(branch.getBranchId() == branchSelected.getBranchId())));
            mMenuAdapter.notifyDataSetChanged();
            rvMenu.smoothScrollToPosition(position);
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    private void updateItem(CharSequence charSequence) {
        if (itemCounter == 0) return;
        Log.e(TAG, "==> updateItem:: " + charSequence);
        Toast.makeText(mContext, "Order item successfully", Toast.LENGTH_SHORT).show();
    }

}

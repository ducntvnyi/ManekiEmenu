package com.vnyi.emenu.maneki.fragments;


import android.animation.Animator;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.ButtonFont;
import com.vnyi.emenu.maneki.customviews.CartAnimationUtil;
import com.vnyi.emenu.maneki.models.AnimationView;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;
import com.vnyi.emenu.maneki.models.response.ItemCategoryNoListNote;
import com.vnyi.emenu.maneki.models.response.TicketLoadInfo;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
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
    private List<ItemCategoryNoListNote> mItemCategoryNoListNotes;

    private ItemAdapter mItemAdapter;
    private List<ItemCategoryDetail> mItemModels;
    private ItemCategoryDetail mItemCategoryDetail;


    private AnimationView mAnimationView;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private int position = 0;

    private ConfigValueModel mConfigValueModel;

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

            mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
            debounceOrderItem();
            // init menu adapter
            mItemCategoryNoListNotes = new ArrayList<>();
            mMenuAdapter = new MenuAdapter(mContext, mItemCategoryNoListNotes, branchConsumer -> {
                position = branchConsumer.getPosition();
                selectMenu(branchConsumer);

            });
            mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvMenu.setAdapter(mMenuAdapter);
            rvMenu.setLayoutManager(mLayoutManager);

            // init item adapter
            mItemModels = new ArrayList<>();
            mItemAdapter = new ItemAdapter(mContext, mItemModels, animationView -> {
                onClickAddToCart(animationView.getImageView(), animationView.getView());
                if (itemCounter == 0) {
                    mItemCategoryDetail = animationView.getCategoryDetail();
                } else {
                    categoryDetailsOrder.add(animationView.getCategoryDetail());
                }
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
            loadData();

        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }
    // orderDetailId su dung

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
            ItemCategoryNoListNote categoryNoListNote = null;
            if (position == 0) {
                categoryNoListNote = mItemCategoryNoListNotes.get(mItemCategoryNoListNotes.size() - 1);
                position = mItemCategoryNoListNotes.size() - 1;
            } else {
                categoryNoListNote = mItemCategoryNoListNotes.get(position - 1);
                --position;
            }
            selectMenu(categoryNoListNote);
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }

    }

    @OnClick(R.id.ivNext)
    void onNextMenu() {
        try {
            ItemCategoryNoListNote categoryNoListNote = null;
            if (position == (mItemCategoryNoListNotes.size() - 1)) {
                categoryNoListNote = mItemCategoryNoListNotes.get(0);
                position = 0;
            } else {
                categoryNoListNote = mItemCategoryNoListNotes.get(position + 1);
                ++position;
            }
            selectMenu(categoryNoListNote);
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
                    ++itemCounter;

                    if (itemCounter == 1) {
                        requestPostTicketUpdateItem(mConfigValueModel, ticketId, 0, 1, mItemCategoryDetail, ticketUpdateItem -> {
                            VnyiPreference.getInstance(getContext()).putInt(VnyiApiServices.ORDER_DETAIL_ID, ticketUpdateItem.getOrderDetailId());
                        });
                    } else {
                        tvCart.setText("" + itemCounter + "");
                    }

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

    private void selectMenu(ItemCategoryNoListNote categoryNoListNote) {
        try {
            if (categoryNoListNote == null) return;

            StreamSupport.stream(mItemCategoryNoListNotes).forEach(noListNote -> noListNote.setSelected(noListNote.getGroupID() == categoryNoListNote.getGroupID()));

            mMenuAdapter.notifyDataSetChanged();
            rvMenu.smoothScrollToPosition(position);

            // load item right
            loadMenuRight(categoryNoListNote.getGroupID(), ticketId);

        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    private void updateItem(CharSequence charSequence) {
        if (itemCounter == 0 || itemCounter == 1) return;
        Log.e(TAG, "==> updateItem:: " + charSequence);
        Toast.makeText(mContext, "Order item successfully", Toast.LENGTH_SHORT).show();
        int orderDetailId = VnyiPreference.getInstance(mContext).getInt(VnyiApiServices.ORDER_DETAIL_ID);
        new UpdateItemTask().execute(orderDetailId);
    }

    private List<ItemCategoryDetail> categoryDetailsOrder = new ArrayList<>();

    private class UpdateItemTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "==> UpdateItemTask onPreExecute");
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            Log.e(TAG, "==> UpdateItemTask doInBackground");
            int orderDetailId = integers[0];
            for (ItemCategoryDetail categoryDetail : categoryDetailsOrder) {
                requestPostTicketUpdateItem(mConfigValueModel, ticketId, orderDetailId, (int) (categoryDetail.getOrderedQuantity() + 1), categoryDetail, ticketUpdateInfo -> {
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            categoryDetailsOrder.clear();
            Log.e(TAG, "==> UpdateItemTask onPostExecute");
        }
    }

    // step1: lúc đầu thì ticketId =0 sau đó gọi hàm tickLoadInfo để tạo bill,
    // Step2: sau đó gọi Ticket_UpdateInfo để lấy ticketId,
    // step3: sau đó thêm item goi Ticket_UpdateItem // save OrderDetailId
    // step4: tiếp tục ơrder sd OrderDetailId

    int ticketId = 0;

    private void loadData() {
        showDialog();

        TicketLoadInfo ticketInfo = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_TICKET, TicketLoadInfo.class);
        if (ticketInfo == null) {
            requestTicketUpdateInfo(mConfigValueModel, ticketUpdateInfo -> {
                VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_TICKET_UPDATE_INFO, ticketUpdateInfo);
                ticketId = ticketUpdateInfo.getTicketId();
                loadItem(ticketId);
            });
        } else {
            ticketId = ticketInfo.getTicketId();
            loadItem(ticketId);
        }


    }

    private void loadItem(int ticketId) {
        showDialog();
        ticketLoadInfo(mConfigValueModel, ticketId, ticketLoadInfo -> {
            VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_TICKET, ticketLoadInfo);
            loadMenuLeft(ticketId);
        });
    }

    private void loadMenuLeft(int ticketId) {
        showDialog();


        getListItemCategoryNoTicket(mConfigValueModel, ticketId, noTicketModel -> {
            // Update UI menu left
            mItemCategoryNoListNotes = noTicketModel.getItemCategoryNoListNotes();
            mMenuAdapter.setMenuList(mItemCategoryNoListNotes);
            selectMenu(mItemCategoryNoListNotes.get(position));

            Log.e(TAG, "==> menu Left:: " + noTicketModel.toString());
            loadMenuRight(noTicketModel.getItemCategoryNoListNotes().get(0).getGroupID(), ticketId);
        });
    }

    private void loadMenuRight(int categoryId, int ticketId) {

        showDialog();
        getListItemCategoryDetail(mConfigValueModel, false, categoryId, ticketId, categoryDetailModel -> {
            // Update UI menu right
            mItemModels = categoryDetailModel.getItemCategoryDetails();
            mItemAdapter.setItemModelList(mItemModels);

            dismissDialog();
            Log.e(TAG, "==> menu right:: " + categoryDetailModel.toString());
        });
    }
}

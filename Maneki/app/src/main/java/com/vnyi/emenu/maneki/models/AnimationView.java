package com.vnyi.emenu.maneki.models;

import android.view.View;
import android.widget.ImageView;

import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;

/**
 * Created by Hungnd on 11/7/17.
 */

public class AnimationView {

    private int position;
    private ItemCategoryDetail categoryDetail;
    private ImageView imageView;
    private View view;

    public AnimationView(ImageView imageView, View view) {
        this.imageView = imageView;
        this.view = view;
    }

    public AnimationView(ItemCategoryDetail itemModel, ImageView imageView, View view) {
        this.categoryDetail = itemModel;
        this.imageView = imageView;
        this.view = view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ItemCategoryDetail getCategoryDetail() {
        return categoryDetail;
    }

    public void setCategoryDetail(ItemCategoryDetail categoryDetail) {
        this.categoryDetail = categoryDetail;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}

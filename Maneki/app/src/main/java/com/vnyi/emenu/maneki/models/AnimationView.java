package com.vnyi.emenu.maneki.models;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Hungnd on 11/7/17.
 */

public class AnimationView {

    private int position;
    private ItemModel itemModel;
    private ImageView imageView;
    private View view;

    public AnimationView(ImageView imageView, View view) {
        this.imageView = imageView;
        this.view = view;
    }

    public AnimationView(ItemModel itemModel, ImageView imageView, View view) {
        this.itemModel = itemModel;
        this.imageView = imageView;
        this.view = view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ItemModel getItemModel() {
        return itemModel;
    }

    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
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

package com.vnyi.emenu.maneki.models;

import android.view.View;
import android.widget.ImageView;

import com.vnyi.emenu.maneki.models.response.Branch;

/**
 * Created by Hungnd on 11/7/17.
 */

public class AnimationView {

    private int position;
    private Branch branch;
    private ImageView imageView;
    private View view;

    public AnimationView(ImageView imageView, View view) {
        this.imageView = imageView;
        this.view = view;
    }

    public AnimationView(Branch branch, ImageView imageView, View view) {
        this.branch = branch;
        this.imageView = imageView;
        this.view = view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Branch getBranch() {
        return branch;
    }


    public void setBranch(Branch branch) {
        this.branch = branch;
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

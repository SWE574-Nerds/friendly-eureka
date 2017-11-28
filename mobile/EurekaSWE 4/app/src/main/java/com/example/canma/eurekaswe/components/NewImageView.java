package com.example.canma.eurekaswe.components;

import android.content.Context;
import android.util.AttributeSet;



public class NewImageView extends android.support.v7.widget.AppCompatImageView {

    public NewImageView(Context context) {
        super(context);
    }

    public NewImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        // Optimization so we don't measure twice unless we need to
        if (width != height) {
            setMeasuredDimension(width, width);
        }
    }

}
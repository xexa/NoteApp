package com.example.noteappmvvm.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {
    public final int verticalSpacingheight;

    public VerticalSpacingItemDecorator(int spacingheight) {
        verticalSpacingheight = spacingheight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = verticalSpacingheight;
    }
}

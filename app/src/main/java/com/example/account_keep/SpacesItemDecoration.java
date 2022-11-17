package com.example.account_keep;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * class used to set the offset of items in recycler view
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    //left
    private final int lspace;
    //right
    private final int rspace;
    //top
    private final int tspace;


    public SpacesItemDecoration(int tspace, int lspace, int rspace) {
        this.tspace = tspace;
        this.lspace = lspace;
        this.rspace = rspace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = lspace;
        outRect.right = rspace;
        outRect.bottom = tspace;
    }
}

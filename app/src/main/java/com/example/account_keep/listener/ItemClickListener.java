package com.example.account_keep.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account_keep.activity.MainActivity;

public class ItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetectorCompat gestureDetector;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public ItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener onItemClickListener){
        this.clickListener = onItemClickListener;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                    clickListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e){
                View childView  = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && clickListener != null){
                    clickListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e){
        gestureDetector.onTouchEvent(e);
        return false;
    }

}

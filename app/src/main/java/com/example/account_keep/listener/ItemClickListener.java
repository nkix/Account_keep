package com.example.account_keep.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account_keep.R;

public class ItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetectorCompat gestureDetector;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public ItemClickListener(final RecyclerView recyclerView, OnItemClickListener onItemClickListener){
        this.clickListener = onItemClickListener;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && clickListener != null &&
                        childView.findViewById(R.id.deleteButton).getVisibility() == View.INVISIBLE){
                    final int pos = recyclerView.getChildAdapterPosition(childView);

                    if(pos != RecyclerView.NO_POSITION){
                        clickListener.onItemClick(childView, pos);
                        return true;
                    }
                }
                /*else if(childView.findViewById(R.id.deleteButton).getVisibility() == View.VISIBLE &&
                        childView != null && clickListener != null){
                    return false;
                }*/
                return false;
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
        View childView  = view.findChildViewUnder(e.getX(), e.getY());
        if(childView!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){
            //clickListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            if(e.getAction()==MotionEvent.ACTION_BUTTON_PRESS){
                return false;
            }
            return true;
        }
        return false;
    }

}

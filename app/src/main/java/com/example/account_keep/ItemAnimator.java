package com.example.account_keep;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement animator with source code of DefaultItemAnimator
 * Animation of items in RecyclerView
 */
public class ItemAnimator extends SimpleItemAnimator {
    List<RecyclerView.ViewHolder> removeHolders = new ArrayList<>();
    List<RecyclerView.ViewHolder> removeAnimators = new ArrayList<>();
    List<RecyclerView.ViewHolder> moveHolders = new ArrayList<>();
    List<RecyclerView.ViewHolder> moveAnimators = new ArrayList<>();


    @Override
    public void runPendingAnimations() {
        if(!removeHolders.isEmpty()){
            for(RecyclerView.ViewHolder holder: removeHolders){
                animateRemoveImpl(holder);
            }
            removeHolders.clear();
        }
        if(!moveHolders.isEmpty()){
            for(RecyclerView.ViewHolder holder: moveHolders){
                animateMoveImpl(holder);
            }
            moveHolders.clear();
        }
    }

    @Override
    public void endAnimation(@NonNull RecyclerView.ViewHolder item) {

    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return (!removeHolders.isEmpty() && !removeAnimators.isEmpty() &&
                !moveHolders.isEmpty() && !moveAnimators.isEmpty());
    }

    @Override
    public boolean animateRemove(final RecyclerView.ViewHolder holder) {
        resetAnimation(holder);
        removeHolders.add(holder);
        return true;
    }

    private void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimator animation = view.animate();
        removeAnimators.add(holder);
        animation.setDuration(500).alpha(0).scaleX(1).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        dispatchRemoveStarting(holder);
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animation.setListener(null);
                        view.setAlpha(1);
                        view.setScaleX(0);
                        dispatchRemoveFinished(holder);
                        removeAnimators.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        return false;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        holder.itemView.setTranslationY(fromY - toY);
        moveHolders.add(holder);
        return true;
    }

    private void animateMoveImpl(final RecyclerView.ViewHolder holder){

    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }



    /**
     * Check the state of currently pending and running animations. If there are none
     * pending/running, call {@link #dispatchAnimationsFinished()} to notify any
     * listeners.
     */
    void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }


    private void resetAnimation(RecyclerView.ViewHolder holder) {
        endAnimation(holder);
    }

}



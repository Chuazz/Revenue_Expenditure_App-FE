package com.example.quanlythuchi.util;

import android.animation.Animator;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;

public class TouchguardClickAnimator implements RecyclerView.OnItemTouchListener {

    private Callback mCallback;

    public TouchguardClickAnimator(Callback callback) {
        mCallback = callback;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && mCallback != null) {
            Animator animator = mCallback.createAnimator(child);
            if (animator != null) {
                animator.start();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public interface Callback {
        Animator createAnimator(View view);
    }
}

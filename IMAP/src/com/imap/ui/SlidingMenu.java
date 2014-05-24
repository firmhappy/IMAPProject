package com.imap.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class SlidingMenu extends RelativeLayout {

	private View mDetailView; // ²àÀ­²Ëµ¥View
	private SlidingView mSlidingView; // ×ÜView

	public SlidingMenu(Context context) {
		super(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void addViews(View center, View right) {
		setRightView(right);
		setCenterView(center);
	}

	public void setRightView(View right) {
		LayoutParams behindParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		behindParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(right, behindParams);
		mDetailView = right;

	}

	public void setCenterView(View center) {
		LayoutParams aboveParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mSlidingView = new SlidingView(getContext());
		addView(mSlidingView, aboveParams);
		mSlidingView.setView(center);
		mSlidingView.invalidate();
		mSlidingView.setDetailView(mDetailView);
	}

	public void showRightView() {
		IMapApplication.isRight = true;
		mSlidingView.showRightView();
	}

}

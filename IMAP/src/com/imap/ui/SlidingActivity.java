package com.imap.ui;

import com.baidu.mapapi.BMapManager;
import com.imap.R;
import com.imap.location.IMapApplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class SlidingActivity extends FragmentActivity {

	private SlidingMenu mSlidingMenu; // 侧拉菜单
	private RightFragment rightFragment;
	private CenterFragment centerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		IMapApplication app = (IMapApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplicationContext());
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(new IMapApplication.MyGeneralListener());
		}
		setContentView(R.layout.main);

		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();

		rightFragment = new RightFragment();
		t.replace(R.id.right_frame, rightFragment);

		centerFragment = new CenterFragment();
		t.replace(R.id.center_frame, centerFragment);
		t.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	public void showRight() {
		mSlidingMenu.showRightView();
	}

}

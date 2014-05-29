package com.imap.ui;

import java.util.ArrayList;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.imap.R;
import com.imap.bean.Message;
import com.imap.bean.MyLinearLayout;
import com.imap.util.BMapUtil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CenterFragment extends Fragment {

	private Button showRight, publish; // 侧拉按钮,发布按钮

	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	// 定位图层
	MyLocationOverlay myLocationOverlay = null;
	private MyOverlay mOverlay;
	// UI相关
	boolean isFirstLoc = true;// 是否首次定位
	MapView mMapView = null; // 地图View
	private MapController mMapController = null;
	public static ArrayList<Message> messages = new ArrayList<Message>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(R.layout.center, null);
		showRight = (Button) mView.findViewById(R.id.setting_button);
		publish = (Button) mView.findViewById(R.id.publish_button);
		// Publish Button的监听器，就先使用内部类的方式吧！
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/*
				 * 此处实际内容应该是跳转到信息发布的界面
				 * 此处不进行任何实际修改
				 */
				GeoPoint p = new GeoPoint((int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6));
				messages.add(new Message(p,"巴拉巴拉","菠萝菠萝蜜"));
				CenterFragment.this.getActivity().getResources()
						.getDrawable(R.drawable.icon_gcoding);
				OverlayItem item = new OverlayItem(p, "!!!", "???");
				mOverlay.addItem(item);
				mMapView.refresh();

			}
		});
		// 地图初始化
		mMapView = (MapView) mView.findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapView.getController().setZoom(14);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);

		// 定位初始化
		mLocClient = new LocationClient(getActivity());
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		// 定位图层初始化
		myLocationOverlay = new MyLocationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(locData);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// 修改定位数据后刷新图层生效
		mMapView.refresh();
		initMyOverlay();
		return mView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		showRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showRight();
			}
		});
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// 退出时销毁定位
		if (mLocClient != null)
			mLocClient.stop();
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();
			// 是手动触发请求或首次定位时，移动到定位点
			if (isFirstLoc) {
				// 移动地图到定位点
				Log.d("LocationOverlay", "receive location, animate to it");
				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));
				myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
			}
			// 首次定位完成
			isFirstLoc = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	// 这个，应该是初始化自定义图层的
	private void initMyOverlay() {
		Drawable mark = this.getResources()
				.getDrawable(R.drawable.icon_gcoding);
		mOverlay = new MyOverlay(mark, mMapView,this.getActivity());
		mMapView.getOverlays().add(mOverlay);
		mMapView.refresh();
		getMessages();
		for (int i = 0; i < messages.size(); i++) {
			OverlayItem item = new OverlayItem(messages.get(i).getP(), "!!!", "???");
			mOverlay.addItem(item);
		}
		mMapView.refresh();
	}
	/*
	 * getMessages()用于从服务器端获取全部message
	 */
	private void getMessages(){
		GeoPoint p = new GeoPoint((int) (37.872973 * 1e6),
				(int) (112.603397 * 1e6));
		messages.add(new Message(p,"我是标题","我是内容"));
	}

}

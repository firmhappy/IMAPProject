package com.imap.ui;

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
import com.imap.location.MyOverlay;
import com.imap.publish.PublishActivity;
import com.imap.util.BMapUtil;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

public class CenterFragment extends Fragment {

	private Button publish, bt;
	private ImageView showRight;

	// ��λ���
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	// ��λͼ��
	MyLocationOverlay myLocationOverlay = null;
	// ��������ͼ��
	private PopupOverlay pop = null;// ��������ͼ�㣬����ڵ�ʱʹ��
	private MyOverlay mOverlay;
	private TextView popupText = null;// ����view
	private View viewCache = null;

	// UI���
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	// ��ͼ��أ�ʹ�ü̳�MapView��MyLocationMapViewĿ������дtouch�¼�ʵ�����ݴ���
	// ���������touch�¼���������̳У�ֱ��ʹ��MapView����
	MapView mMapView = null; // ��ͼView
	private MapController mMapController = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(R.layout.center, null);
		showRight = (ImageView) mView.findViewById(R.id.setting_button);
		bt = (Button) LayoutInflater.from(this.getActivity())
				.inflate(R.layout.demoview, null).findViewById(R.id.button1);
		publish = (Button) mView.findViewById(R.id.publish_button);

		// ��ͼ��ʼ��
		mMapView = (MapView) mView.findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapView.getController().setZoom(14);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);

		// ��λ��ʼ��
		mLocClient = new LocationClient(getActivity());
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		// ��λͼ���ʼ��
		myLocationOverlay = new MyLocationOverlay(mMapView);
		// ���ö�λ����
		myLocationOverlay.setData(locData);
		// ��Ӷ�λͼ��
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// �޸Ķ�λ���ݺ�ˢ��ͼ����Ч
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

		// Publish Button�ļ�����������ʹ���ڲ���ķ�ʽ�ɣ�
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(),PublishActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
				/*�ⲿ����ע�͵���
				System.out.println("ADD");
				GeoPoint p = new GeoPoint((int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6));
				Drawable mark = CenterFragment.this.getActivity()
						.getResources().getDrawable(R.drawable.icon_gcoding);
				OverlayItem item = new OverlayItem(p, "!!!", "???");
				mOverlay.addItem(item);
				mMapView.refresh();
				*/
			}
		});
	}

	/**
	 * ������������ͼ��
	 */

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
		// �˳�ʱ���ٶ�λ
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
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// �������ʾ��λ����Ȧ����accuracy��ֵΪ0����
			locData.accuracy = location.getRadius();
			// �˴��������� locData�ķ�����Ϣ, �����λ SDK δ���ط�����Ϣ���û������Լ�ʵ�����̹�����ӷ�����Ϣ��
			locData.direction = location.getDerect();
			// ���¶�λ����
			myLocationOverlay.setData(locData);
			// ����ͼ������ִ��ˢ�º���Ч
			mMapView.refresh();
			// ���ֶ�����������״ζ�λʱ���ƶ�����λ��
			if (isFirstLoc) {
				// �ƶ���ͼ����λ��
				Log.d("LocationOverlay", "receive location, animate to it");
				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));
				myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
			}
			// �״ζ�λ���
			isFirstLoc = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	// �̳�MyLocationOverlay��дdispatchTapʵ�ֵ������
	public class LocationOverlay extends MyLocationOverlay {

		public LocationOverlay(MapView mapView) {
			super(mapView);
		}

	}

	// �����Ӧ���ǳ�ʼ���Զ���ͼ���
	private void initMyOverlay() {
		GeoPoint p = new GeoPoint((int) (37.872973 * 1e6),
				(int) (112.603397 * 1e6));
		Drawable mark = this.getResources()
				.getDrawable(R.drawable.icon_gcoding);
		OverlayItem item = new OverlayItem(p, "!!!", "???");
		mOverlay = new MyOverlay(mark, mMapView);
		MyOverlay.setView(bt);
		mMapView.getOverlays().add(mOverlay);
		mMapView.refresh();
		mOverlay.addItem(item);
		mMapView.refresh();
	}

}

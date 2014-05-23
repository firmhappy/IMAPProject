package com.imap.ui;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.imap.R;
import com.imap.util.BMapUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CenterFragment extends Fragment {

	private Button showRight; // ������ť

	// ��λ���
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	// ��λͼ��
	LocationOverlay myLocationOverlay = null;
	// ��������ͼ��
	private PopupOverlay pop = null;// ��������ͼ�㣬����ڵ�ʱʹ��
	private TextView popupText = null;// ����view
	private View viewCache = null;

	// UI���
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	// ��ͼ��أ�ʹ�ü̳�MapView��MyLocationMapViewĿ������дtouch�¼�ʵ�����ݴ���
	// ���������touch�¼���������̳У�ֱ��ʹ��MapView����
	MyLocationMapView mMapView = null; // ��ͼView
	private MapController mMapController = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(R.layout.center, null);
		showRight = (Button) mView.findViewById(R.id.setting_button);

		// ��ͼ��ʼ��
		mMapView = (MyLocationMapView) mView.findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapView.getController().setZoom(14);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);
		// ���� ��������ͼ��
		createPaopao();

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
		myLocationOverlay = new LocationOverlay(mMapView);
		// ���ö�λ����
		myLocationOverlay.setData(locData);
		// ��Ӷ�λͼ��
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// �޸Ķ�λ���ݺ�ˢ��ͼ����Ч
		mMapView.refresh();
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

	/**
	 * ������������ͼ��
	 */
	public void createPaopao() {
		viewCache = getActivity().getLayoutInflater().inflate(
				R.layout.custom_text_view, null);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		// ���ݵ����Ӧ�ص�
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
			}
		};
		pop = new PopupOverlay(mMapView, popListener);
		MyLocationMapView.pop = pop;
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

		@Override
		protected boolean dispatchTap() {
			// �������¼�,��������
			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setText("�ҵ�λ��");
			pop.showPopup(BMapUtil.getBitmapFromView(popupText), new GeoPoint(
					(int) (locData.latitude * 1e6),
					(int) (locData.longitude * 1e6)), 8);
			return true;
		}

	}
}

package com.imap.ui;

import java.util.ArrayList;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.imap.R;
import com.imap.bean.IMAPApplication;
import com.imap.bean.Message;
import com.imap.bean.MyLinearLayout;
import com.imap.push.PublishActivity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class CenterFragment extends Fragment implements OnMarkerClickListener{

	private Button publish, bt;
	private ImageView showRight;

	// ��λ���
	private LocationClient mLocClient;
	private MyLocationData locData;
	public MyLocationListenner myListener = new MyLocationListenner();

	// UI���
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ

	MapView mMapView = null; // ��ͼView
	private BaiduMap mMap;
	private ArrayList<Message> mMessages;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ��ͼ��ע��
		SDKInitializer.initialize(this.getActivity().getApplicationContext());
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(R.layout.center, null);
		showRight = (ImageView) mView.findViewById(R.id.setting_button);
		bt = (Button) LayoutInflater.from(this.getActivity())
				.inflate(R.layout.demoview, null).findViewById(R.id.button1);
		publish = (Button) mView.findViewById(R.id.publish_button);

		// ��ͼ��ʼ��
		mMapView = (MapView) mView.findViewById(R.id.bmapView);
		mMap = mMapView.getMap();
		mMap.setMyLocationEnabled(true);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(new LatLng(
				39.963175, 116.400244), 13);
		mMap.setMapStatus(msu);
		
		loadmyoverlay();
		mMap.setOnMarkerClickListener(this);
		//�����������Ϊ�˻�ȡ����Ҫ�ĵ�ַ�ľ�γ�ȣ������������ԣ��պ�ɾ����
		mMap.setOnMapClickListener(new OnMapClickListener(){

			@Override
			public void onMapClick(LatLng arg0) {
				System.out.println("Location:");
				System.out.println(arg0.latitude);
				System.out.println(arg0.longitude);
				mMap.hideInfoWindow();
				
			}

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}});
		

		// ��λ��ʼ��
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
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

				Intent intent = new Intent(getActivity(), PublishActivity.class);
				Bundle bundle = new Bundle();
				bundle.putDouble("latitude", locData.latitude);
				bundle.putDouble("longtitude", locData.longitude);
				intent.putExtras(bundle);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.in_from_down,
						R.anim.out_to_up);
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
		//ÿ�����»��ѵ�ͼ���ͼ��ػ���
		mMapView.onResume();
		loadmyoverlay();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// �˳�ʱ���ٶ�λ
		if (mLocClient != null)
			mLocClient.stop();
		mMapView.onDestroy();
		super.onDestroy();
	}

	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mMapView == null)
				return;
			locData = new MyLocationData.Builder()
					.latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				MapStatusUpdate msu = MapStatusUpdateFactory
						.newLatLng(new LatLng(location.getLatitude(), location
								.getLongitude()));
				mMap.animateMapStatus(msu);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}
	
	/**
	 * ���ݻ��棬����Marker
	 */
	private void loadmyoverlay(){
		mMap.clear();
		mMessages=((IMAPApplication)this.getActivity().getApplication()).getMessages();
		BitmapDescriptor bdA = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		OverlayOptions options;
		for(int i=0;i<mMessages.size();i++){
			Message message=mMessages.get(i);
			System.out.println(message.toString());
			options=new MarkerOptions().position(message.getP()).icon(bdA).title(i+"");
			mMap.addOverlay(options);
		}
	}

	/**
	 * Marker�ĵ������
	 */
	public boolean onMarkerClick(Marker arg0) {
		MyLinearLayout info=new MyLinearLayout(this.getActivity());
		Message message=mMessages.get(Integer.parseInt(arg0.getTitle()));
		info.addMessage(message);
		Point p=mMap.getProjection().toScreenLocation(arg0.getPosition());
		p.y-=47;
		InfoWindow infoWindow=new InfoWindow(info, mMap.getProjection().fromScreenLocation(p), null);
		mMap.showInfoWindow(infoWindow);
		return true;
	}
	
	
}

package com.imap.ui;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class IMapApplication extends Application {

	private static IMapApplication mInstance = null;
	public boolean m_bKeyRight = true;
	BMapManager mBMapManager = null;
	//static boolean isRight = false;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initEngineManager(this);
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(new MyGeneralListener())) {
			Toast.makeText(
					IMapApplication.getInstance().getApplicationContext(),
					"BMapManager  ��ʼ������!", Toast.LENGTH_LONG).show();
		}
	}

	public static IMapApplication getInstance() {
		return mInstance;
	}

	// �����¼���������������ͨ�������������Ȩ��֤�����
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						IMapApplication.getInstance().getApplicationContext(),
						"���������������", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						IMapApplication.getInstance().getApplicationContext(),
						"������ȷ�ļ���������", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			// ����ֵ��ʾkey��֤δͨ��
			if (iError != 0) {
				// ��ȨKey����
				Toast.makeText(
						IMapApplication.getInstance().getApplicationContext(),
						"��������ȷ����ȨKey,������������������Ƿ�������error: "
								+ iError, Toast.LENGTH_LONG).show();
				IMapApplication.getInstance().m_bKeyRight = false;
			} else {
				IMapApplication.getInstance().m_bKeyRight = true;
				Toast.makeText(
						IMapApplication.getInstance().getApplicationContext(),
						"key��֤�ɹ�", Toast.LENGTH_LONG).show();
			}
		}
	}
}

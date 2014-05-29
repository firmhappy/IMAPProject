package com.imap.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.imap.R;
import com.imap.bean.MyLinearLayout;
import com.imap.util.BMapUtil;

public class MyOverlay extends ItemizedOverlay<OverlayItem> {

	static MyLinearLayout view;
	private PopupOverlay pop;
	private GeoPoint p;
	private OverlayItem overlayitem;
	private MapView arg1;
	private TextView tv1, tv2;

	public MyOverlay(Drawable arg0, MapView arg1, Context context) {
		super(arg0, arg1);
		this.arg1 = arg1;
		view = new MyLinearLayout(context);
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		if (pop != null) {
			pop.hidePop();
			arg1.removeView(view);
		}

		return false;
	}

	@Override
	protected boolean onTap(int arg0) {
		pop = new PopupOverlay(arg1, new PopupClickListener() {
			public void onClickedPopup(int arg0) {
				System.out.println("POPIndex:" + arg0);
				pop.hidePop();

			}
		});
		overlayitem = this.getItem(arg0);
		p = overlayitem.getPoint();
		pop.hidePop();
		view.addMessage(CenterFragment.messages.get(arg0));
		Bitmap bit = BMapUtil.getBitmapFromView(view);
		pop.showPopup(bit, p, 32);
		return true;
	}

}

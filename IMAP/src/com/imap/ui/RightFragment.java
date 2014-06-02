package com.imap.ui;

import com.imap.R;
import com.imap.setting.SettingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class RightFragment extends Fragment {
	
	private TextView settingTextView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.right, null);
		settingTextView = (TextView)view.findViewById(R.id.app_setting);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		settingTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});
	}

}

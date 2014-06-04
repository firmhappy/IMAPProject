package com.imap.setting;

import com.imap.R;
import com.imap.ui.SlidingActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	
	private CompoundButton switchButton_message, switchButton_lock;  //分别是接收推送和应用锁
	private ImageView imageView_back;  //返回按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		switchButton_message = (CompoundButton)this.findViewById(R.id.switch_new_message);
		switchButton_lock = (CompoundButton)this.findViewById(R.id.switch_app_lock);
		imageView_back = (ImageView)this.findViewById(R.id.back);
		
		switchButton_message.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Toast.makeText(getApplicationContext(), "接收推送开", 0).show();
				}else{
					Toast.makeText(getApplicationContext(), "接收推送关", 0).show();
				}
			}
		});
		
		switchButton_lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Toast.makeText(getApplicationContext(), "应用锁开", 0).show();
				}else{
					Toast.makeText(getApplicationContext(), "应用锁关", 0).show();
				}
			}
		});
		
		imageView_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this, SlidingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
				finish();
			}
		});
	}
	
}

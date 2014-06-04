package com.imap.publish;

import com.imap.R;
import com.imap.setting.SettingActivity;
import com.imap.ui.SlidingActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PublishActivity extends Activity {

	private ImageView backImageView; //返回按钮
	private Button nameSlectButton;   //选择发表时显示的昵称
	private EditText messageEditText;  //编辑区域aa
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_publish);
		
		backImageView = (ImageView)this.findViewById(R.id.back);
		nameSlectButton = (Button)this.findViewById(R.id.publish_name);
		messageEditText = (EditText)this.findViewById(R.id.news_publish_edit);
		
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*此处应该弹出提示框，提示是否放弃发表*/
				Intent intent = new Intent(PublishActivity.this, SlidingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
				finish();
			}
		});
	}

}

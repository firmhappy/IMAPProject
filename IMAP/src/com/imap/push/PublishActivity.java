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

	private ImageView backImageView; //���ذ�ť
	private Button nameSlectButton;   //ѡ�񷢱�ʱ��ʾ���ǳ�
	private EditText messageEditText;  //�༭����aa
	
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
				
				/*�˴�Ӧ�õ�����ʾ����ʾ�Ƿ��������*/
				Intent intent = new Intent(PublishActivity.this, SlidingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
				finish();
			}
		});
	}

}

package com.imap.push;

import com.baidu.mapapi.model.LatLng;
import com.imap.R;
import com.imap.bean.IMAPApplication;
import com.imap.bean.Message;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PublishActivity extends Activity implements OnClickListener {

	private ImageView backImageView; // 返回按钮
	private Button nameSlectButton, publishButton; // 选择发表时显示的昵称
	private EditText messageEditText, titleEditText; // 编辑区域
	private LatLng p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_publish);

		p = new LatLng(this.getIntent().getExtras().getDouble("latitude"), this
				.getIntent().getExtras().getDouble("longtitude"));

		backImageView = (ImageView) this.findViewById(R.id.back);
		nameSlectButton = (Button) this.findViewById(R.id.publish_name);
		messageEditText = (EditText) this
				.findViewById(R.id.news_publish_editmessage);
		titleEditText = (EditText) this.findViewById(R.id.news_publish_title);
		publishButton = (Button) this.findViewById(R.id.publish_button);

		publishButton.setOnClickListener(this);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/* 此处应该弹出提示框，提示是否放弃发表 */
				overridePendingTransition(R.anim.in_from_left,
						R.anim.out_to_right);
				finish();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.publish_button:
			publishMessage();
			break;

		}
	}

	private void publishMessage() {
		if (titleEditText.getText().toString().equals(""))
			Toast.makeText(this, "请输入标题！！！", Toast.LENGTH_SHORT).show();
		else if (messageEditText.getText().toString().equals(""))
			Toast.makeText(this, "请输入内容！！！", Toast.LENGTH_SHORT).show();
		else {
			Message newMessage = new Message(p, titleEditText.getText()
					.toString(), messageEditText.getText().toString());
			/*
			 * 此处为与服务器通讯
			 */
			//与服务器通讯成功，本地直接加入缓存
			if (true) {
				IMAPApplication app=(IMAPApplication) this.getApplication();
				app.addMessage(newMessage);
			}
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			finish();
		}
	}
}

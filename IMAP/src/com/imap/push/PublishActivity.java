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

	private ImageView backImageView; // ���ذ�ť
	private Button nameSlectButton, publishButton; // ѡ�񷢱�ʱ��ʾ���ǳ�
	private EditText messageEditText, titleEditText; // �༭����
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

				/* �˴�Ӧ�õ�����ʾ����ʾ�Ƿ�������� */
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
			Toast.makeText(this, "��������⣡����", Toast.LENGTH_SHORT).show();
		else if (messageEditText.getText().toString().equals(""))
			Toast.makeText(this, "���������ݣ�����", Toast.LENGTH_SHORT).show();
		else {
			Message newMessage = new Message(p, titleEditText.getText()
					.toString(), messageEditText.getText().toString());
			/*
			 * �˴�Ϊ�������ͨѶ
			 */
			//�������ͨѶ�ɹ�������ֱ�Ӽ��뻺��
			if (true) {
				IMAPApplication app=(IMAPApplication) this.getApplication();
				app.addMessage(newMessage);
			}
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			finish();
		}
	}
}

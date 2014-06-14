package com.imap.bean;

import com.imap.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyLinearLayout extends LinearLayout {
	private TextView tv_title, tv_message;

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.information, this,true);
		tv_title = (TextView) this.findViewById(R.id.information_tvtitle);
		tv_message = (TextView) this.findViewById(R.id.information_tvmessage);
	}

	public MyLinearLayout(Context context) {
		this(context,null);
		/*
		super(context);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.information, null);
		tv_title = (TextView) this.findViewById(R.id.information_tvtitle);
		tv_message = (TextView) this.findViewById(R.id.information_tvmessage);
		*/
	}

	public void addMessage(Message message) {
		tv_title.setText(message.getTitle());
		tv_message.setText(message.getMessage());
	}

}

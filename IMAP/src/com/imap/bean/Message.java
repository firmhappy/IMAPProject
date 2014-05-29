package com.imap.bean;

import com.baidu.platform.comapi.basestruct.GeoPoint;

/*
 * 用于保存每一条信息
 */
public class Message {
	private GeoPoint p;
	private String title,message;
	public Message(GeoPoint p, String title, String message) {
		super();
		this.p = p;
		this.title = title;
		this.message = message;
	}
	public GeoPoint getP() {
		return p;
	}
	public void setP(GeoPoint p) {
		this.p = p;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}

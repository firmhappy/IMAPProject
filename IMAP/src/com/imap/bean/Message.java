package com.imap.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.model.LatLng;

/*
 * 用于保存每一条信息
 */
public class Message {
	private String title,message;
	private LatLng p;
	public Message(LatLng p, String title, String message) {
		super();
		this.p = p;
		this.title = title;
		this.message = message;
	}
	
	
	
	public LatLng getP() {
		return p;
	}



	public void setP(LatLng p) {
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
	
	public JSONObject getJSONObject() throws JSONException{
		JSONObject json=new JSONObject();
		json.put("longtitude", this.getP().longitude);
		json.put("latitude", this.getP().latitude);
		json.put("title", this.getTitle());
		json.put("message", this.getMessage());
		return json;
	}
	
	public static Message fromJSON(JSONObject json) throws JSONException{
		LatLng ll=new LatLng(json.getDouble("latitude"),json.getDouble("longtitude"));
		Message message=new Message(ll,json.getString("title"),json.getString("message"));
		return message;
	}



	public String toString() {
		String res="Message Info:\n+"+this.getP().toString()+"\nTitle:"+this.getTitle()+"\nMessage"+this.getMessage()+"\n-------------------------";
		return res;
	}
	
	
	

}

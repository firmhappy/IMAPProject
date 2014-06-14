package com.imap.bean;

import java.util.ArrayList;

import org.json.JSONException;

import com.imap.util.MessageFile;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * 重写系统自由的Application
 * 以此存储缓存池
 * @author 铁峰
 *
 */
public class IMAPApplication extends Application{
	private ArrayList<Message> messages=new ArrayList<Message>();
	/*
	 * 在无法通过Activity或者Context直接获取到Application，可以通过单例调用，获取Application
	 */
	private static IMAPApplication instance;
	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
		this.getSharedPreferences("mymessages", MODE_PRIVATE);
		try {
			MessageFile.takeMessages(instance);
		} catch (JSONException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	public void addMessage(Message message){
		this.messages.add(message);
	}

	public static IMAPApplication getInstance() {
		return instance;
	}

	public static void setInstance(IMAPApplication instance) {
		IMAPApplication.instance = instance;
	}
	
	
	
	
	
	
}

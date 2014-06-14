package com.imap.bean;

import java.util.ArrayList;

import org.json.JSONException;

import com.imap.util.MessageFile;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * ��дϵͳ���ɵ�Application
 * �Դ˴洢�����
 * @author ����
 *
 */
public class IMAPApplication extends Application{
	private ArrayList<Message> messages=new ArrayList<Message>();
	/*
	 * ���޷�ͨ��Activity����Contextֱ�ӻ�ȡ��Application������ͨ���������ã���ȡApplication
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

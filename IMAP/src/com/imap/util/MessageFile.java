package com.imap.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.baidu.mapapi.model.LatLng;
import com.imap.bean.IMAPApplication;
import com.imap.bean.Message;

/**
 * �Ի���Ĵ洢����أ��ӱ����ļ��������Ǿ�̬������ֱ�ӵ���
 * @author ����
 */
public class MessageFile {

	/**
	 * ���滺��
	 * @param context
	 * @throws JSONException
	 */
	public static void storeMessages(Context context) throws JSONException {
		ArrayList<Message> messages = IMAPApplication.getInstance()
				.getMessages();
		//�������Ϊ�գ�����û���ļ����ͼ����������ݣ���������
		if (messages == null || messages.size() == 0) {
			messages.add(new Message(new LatLng(37.829158, 112.59036), "���Ǳ���1",
					"��������1"));
			messages.add(new Message(new LatLng(37.810565, 112.570786),
					"���Ǳ���2", "��������2"));
			messages.add(new Message(new LatLng(37.789045, 112.586533),
					"���Ǳ���3", "��������3"));
		}
		JSONArray ja = new JSONArray();
		for (int i = 0; i < messages.size(); i++) {
			JSONObject jo = messages.get(i).getJSONObject();
			ja.put(jo);
		}
		SharedPreferences sp = context.getSharedPreferences("mymessages",
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.remove("messages");
		edit.putString("messages", ja.toString());
		edit.commit();
	}

	/**
	 * ���ػ���
	 * @param context
	 * @throws JSONException
	 */
	public static void takeMessages(Context context) throws JSONException {
		SharedPreferences sp = context.getSharedPreferences("mymessages",
				Context.MODE_PRIVATE);
		String json = sp.getString("messages", "");
		//�������Ϊ�գ�����û���ļ����ͼ����������ݣ���������
		if (json.equals("")) {
			MessageFile.storeMessages(context);
		}
		JSONArray jsonarray = new JSONArray(json);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jo = jsonarray.getJSONObject(i);
			IMAPApplication.getInstance().addMessage(Message.fromJSON(jo));
		}
	}

}

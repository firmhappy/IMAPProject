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
 * 对缓存的存储与加载（从本地文件），均是静态方法，直接调用
 * @author 铁峰
 */
public class MessageFile {

	/**
	 * 储存缓存
	 * @param context
	 * @throws JSONException
	 */
	public static void storeMessages(Context context) throws JSONException {
		ArrayList<Message> messages = IMAPApplication.getInstance()
				.getMessages();
		//如果缓存为空，本地没有文件，就加载虚拟数据，仅供测试
		if (messages == null || messages.size() == 0) {
			messages.add(new Message(new LatLng(37.829158, 112.59036), "我是标题1",
					"我是内容1"));
			messages.add(new Message(new LatLng(37.810565, 112.570786),
					"我是标题2", "我是内容2"));
			messages.add(new Message(new LatLng(37.789045, 112.586533),
					"我是标题3", "我是内容3"));
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
	 * 加载缓存
	 * @param context
	 * @throws JSONException
	 */
	public static void takeMessages(Context context) throws JSONException {
		SharedPreferences sp = context.getSharedPreferences("mymessages",
				Context.MODE_PRIVATE);
		String json = sp.getString("messages", "");
		//如果缓存为空，本地没有文件，就加载虚拟数据，仅供测试
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

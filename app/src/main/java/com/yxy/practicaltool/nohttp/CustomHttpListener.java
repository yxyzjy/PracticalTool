package com.yxy.practicaltool.nohttp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 小卷毛 on 2016/4/28 0028.
 */
public abstract class CustomHttpListener implements HttpListener<String> {

	private JSONObject object;
	private Context context;
	private boolean isGson;
	private Class<?> dataM;

	public CustomHttpListener(Context context, boolean isGson, Class<?> dataM) {
		this.context = context;
		this.isGson = isGson;
		this.dataM = dataM;
	}

	@Override
	public void onSucceed(int what, Response<String> response) {
		Log.e("onSucceed", "请求成功：\n" + response.get());
		if (response.get().length()>8000) {
			Log.e("onSucceed", "请求成功：\n" + response.get().substring(8000));
		}
		try {
			object = new JSONObject(response.get());
			if (isGson) {
				if (object.getString("ret").equals("200")) {
					Gson gson = new Gson();
					doWork(what, gson.fromJson(object.toString(), dataM), true);
					/*if (!"1".equals(object.getJSONObject("data").getString("code"))) {
						doWork(what, object.getJSONObject("data").getString("msg"), false);
//				CommonUtil.showToast(context, object.getJSONObject("data").getString("msg"));
					} else {
						if ("1".equals(object.getJSONObject("data").getString("code"))) {
							if (dataM != null) {
								Gson gson = new Gson();
								doWork(what, gson.fromJson(object.toString(), dataM), true);
							} else {
								doWork(what, object, false);
							}
						}
					}*/
				}else{
					doWork(what, object.getString("msg"), false);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			try {
				if(!isGson && "1".equals(object.getJSONObject("data").getString("code"))){
					doWork(what,object.getJSONObject("data").getString("msg"), true);
				}
				if(!isGson && !"1".equals(object.getJSONObject("data").getString("code"))){
					doWork(what,object.getJSONObject("data").getString("msg"), false);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			onFinally(object);
		}
	}

	public abstract void doWork(int what, Object data, boolean isSuccess);

	public void onFinally(JSONObject obj){}; //解析完成，如要执行操作，可重写该方法。

	@Override
	public void onFailed(int what, String url, Object tag, Exception e, int responseCode, long networkMillis) {
		Log.e("onFailed", "请求失败：\n" + url);
//		CommonUtil.showToast(context, "请求数据失败：\n"+ e.getMessage());
	}

}

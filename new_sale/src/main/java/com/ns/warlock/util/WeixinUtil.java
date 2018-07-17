package com.ns.warlock.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.warlock.util.ConstantWeChat;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ns.warlock.dto.AccessToken;

//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;
/**
 * 微信通用接口工具类
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class WeixinUtil {

	public static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	private static Map<String, Map<Long, String>> tokenMap = new HashMap<String, Map<Long, String>>();
	/**
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 微信网页授权-通过code换取网页授权openId
	 * @param code
	 * @return
	 */
	public static String getOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ ConstantWeChat.APPID
		+"&secret=" +  ConstantWeChat.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = httpsRequest(url, "GET", null);
		if (null != jsonObject) {
			return jsonObject.getString("openid");
		}
		return null;
	}
		
	/**
	 * 微信网页授权-通过code换取网页授权access_token
	 * @param code
	 * @return
	 * { "access_token":"ACCESS_TOKEN",
	 * "expires_in":7200,
	 * "refresh_token":"REFRESH_TOKEN",
	 * "openid":"OPENID",
	 * "scope":"SCOPE" }
	 */
	public static JSONObject getOpenIdMap(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ ConstantWeChat.APPID
		+"&secret=" +  ConstantWeChat.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
		return httpsRequest(url, "GET", null);
	}
	
	/**
	 * 微信网页授权-拉取用户信息(需scope为 snsapi_userinfo)
	 * @param access_token
	 * @param openid
	 * @return
	 * {    "openid":" OPENID",
	 * " nickname": NICKNAME,
	 * "sex":"1",
	 * "province":"PROVINCE"
	 * "city":"CITY",
	 * "country":"COUNTRY",
	 * "headimgurl":"http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
	 * "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
	 * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
	 * }
	 */
	public static JSONObject getUserInfoMap(String access_token, String openid){
		String getUserInfoUser = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		return httpsRequest(getUserInfoUser, "GET", null);
	}
	
	
	/**
	 * 获取access_token对象
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return AccessToken对象
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:" + jsonObject.getInteger("errcode")
						+ "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 获取token值
	 * 
	 * @return token
	 */
	public static String getToken() {
		return getToken(ConstantWeChat.APPID, ConstantWeChat.APPSECRET);
	}
	
	public static String getToken(String appId, String appsecret) {
		StringBuffer sb = new StringBuffer();
		sb.append(appId).append(",").append(appsecret);
		if(tokenMap.containsKey(sb.toString())){
			Map<Long, String> map = tokenMap.get(sb.toString());
			Long time = map.keySet().iterator().next();
			if(Calendar.getInstance().getTimeInMillis() - time > 7000000){
				String token = getTokenStr(appId, appsecret);
				if(token != null){
					map = new HashMap<>();
					map.put(Calendar.getInstance().getTimeInMillis(), token);
					tokenMap.put(sb.toString(), map);
				}
				return token;
			}else{
				return map.get(time);
			}
		}else{
			String token = getTokenStr(appId, appsecret);
			if(token != null){
				Map<Long, String> map = new HashMap<>();
				map.put(Calendar.getInstance().getTimeInMillis(), token);
				tokenMap.put(sb.toString(), map);
			}
			return token;
		}
	}
	
	private static String getTokenStr(String appId, String appsecret){
		AccessToken at = WeixinUtil.getAccessToken(appId, appsecret);
		if (null != at) {
			return at.getToken();
		} else {
			return null;
		}
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr && !"".equals(outputStr)) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:", e);
		}
		return jsonObject;
	}

	
	/**
	 * 将微信消息中的CreateTime转换成标准格式的时间（yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param createTime
	 *            消息创建时间
	 * @return String
	 */
	public static String formatTime(String createTime) {
		// 将微信传入的CreateTime转换成long类型，再乘以1000
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(msgCreateTime));
	}
}
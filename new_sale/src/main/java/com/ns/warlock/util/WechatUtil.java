package com.ns.warlock.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ns.warlock.util.ConstantWeChat;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ns.warlock.dto.AccessToken;

/**
 * 微信通用接口工具类
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class WechatUtil {

	public static Logger log = LoggerFactory.getLogger(WechatUtil.class);
	private static Map<String, AccessToken> tokenMap = new HashMap<String, AccessToken>();
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
	private static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken(jsonObject.getString("access_token"), jsonObject.getInteger("expires_in"));
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
	 * 只能单机部署,不能负载均衡
	 * @return token
	 */
	public static String getToken() {
		return getToken(ConstantWeChat.APPID, ConstantWeChat.APPSECRET);
	}
	
	/**
	 * 获取token值
	 * 只能单机部署,不能负载均衡
	 * @return token
	 */
	public static String getToken(String appId, String appsecret) {
		String sb = appId + appsecret;
		AccessToken token = null;
		if(tokenMap.containsKey(sb)){
			token = tokenMap.get(sb);
			if (System.currentTimeMillis() < token.getTimeMillis()) {
				return token.getToken();
			}
		}
		token = getAccessToken(appId, appsecret);
		tokenMap.put(sb, token);
		return token.getToken();
	}

	/**
	 * 创建永久二维码(字符串)
	 * 
	 * @param accessToken
	 * @param sceneStr 参数
	 *            场景str
	 * @return
	 */
	public static String createForeverStrTicket(String sceneStr) {
        return createForeverStrTicket(getToken(), sceneStr);
	}
	
	/**
	 * 创建永久二维码(字符串)
	 * 
	 * @param accessToken
	 * @param sceneStr 参数
	 *            场景str
	 * @return
	 */
	public static String createForeverStrTicket(String accessToken, String sceneStr) {
		//发送post 请求 ticke
        String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
        String param = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"sceneStr\"}}}".replace("sceneStr", sceneStr);
        JSONObject jsonObject = httpsRequest(url, "POST", param);
        if (null != jsonObject) {
        		return jsonObject.getString("ticket");
        }
        return null;
	}
	
	/**
	 * 获取永久二维码
	 * @param sceneStr
	 * @return 二维码的地址
	 */
	public static String getQrCodeUrl(String sceneStr) {
		String ticket = createForeverStrTicket(sceneStr);
		if (null != ticket) {			
			return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+urlEncode(ticket, "utf-8");
		}
		return null;
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
	
	public static boolean checkSignature(String signature,String timestamp,String nonce){
        String[] arr = new String[]{ConstantWeChat.TOKEN,timestamp,nonce};
        //排序
        Arrays.sort(arr);

        //生成字符串
        StringBuffer content = new StringBuffer();
        for(int i=0;i<arr.length;i++){
            content.append(arr[i]);
        }

        //sha1加密
        String temp = getSha1(content.toString());

        return temp.equals(signature);
    }

    /**
     * Sha1加密方法
     * @param str
     * @return
     */
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String urlEncode(String source, String encode) {
        String result = source;  
        try {
            result = URLEncoder.encode(source, encode);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }
}
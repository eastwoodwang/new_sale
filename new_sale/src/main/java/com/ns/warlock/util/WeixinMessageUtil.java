package com.ns.warlock.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 微信通用接口工具类
 * @author lzz
 * @version 1.0
 * 
 */
public class WeixinMessageUtil {
	
	//文本消息
	public static final String MESSAGE_TEXT = "text";
	//图片消息
    public static final String MESSAGE_IMAGE = "image";
    //语音消息
    public static final String MESSAGE_VOICE = "voice";
    //视频消息
    public static final String MESSAGE_VIDEO = "video";
    //小视频消息
    public static final String MESSAGE_SHORT_VIDEO = "shortvideo";
    //地理位置消息
    public static final String MESSAGE_LOCATION = "location";
    //链接消息
    public static final String MESSAGE_LINK = "link";
    //接收事件推送
    public static final String MESSAGE_EVENT = "event";

    //关注 和 扫码-未关注时扫码
    public static final String EVENT_SUB = "subscribe";
    //取消关注
    public static final String EVENT_UNSUB = "unsubscribe";
    //扫码-用户已关注时
    public static final String EVENT_SCAN = "SCAN";
    //上报地理位置
    public static final String EVENT_LOCATION = "LOCATION";
    //点击菜单拉取消息时的事件
    public static final String EVENT_CLICK = "CLICK";
    //点击菜单跳转链接时的事件
    public static final String EVENT_VIEW = "VIEW";

	/**
	 * xml转为map集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
      public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
    	
    	  //System.out.println("进xmlToMap");
    	  
    	  Map<String,String> map=new HashMap<String, String>();
    	  
    	  //dom4j saxReader解析xml
    	  SAXReader reader=new SAXReader();
    	  
    	  //从request中获取输入流
    	  InputStream ins = request.getInputStream();
    	  
    	  //解析xml文档
    	  Document doc =reader.read(ins);
    	  
    	  //获得根节点
    	  Element root = doc.getRootElement();
    	  
    	  //List存储  遍历
    	  List<Element> list=  root.elements();
    	  
    	  for (Element e : list) {
			  map.put(e.getName(), e.getText());
		}
    	  ins.close();
    	//  System.out.println(map.toString());
		return map;  
      }
}
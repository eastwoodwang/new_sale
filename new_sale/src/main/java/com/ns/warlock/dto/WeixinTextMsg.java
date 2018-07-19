package com.ns.warlock.dto;

import com.ns.warlock.util.WeixinMessageUtil;

public class WeixinTextMsg extends WeixinBaseMsg {

	private String Content;

	public String getXml() {
		return "<xml>"+getBaseXml()+"<Content><![CDATA["+this.Content+"]]></Content></xml>";
	}
	public WeixinTextMsg() {
	}
	
	public WeixinTextMsg(String content, String toUserName, String fromUserName) {
		this.Content = content;
		init(toUserName, fromUserName, WeixinMessageUtil.MESSAGE_TEXT);
	}
	
	public void init(String content, String toUserName, String fromUserName, String msgId) {
		this.Content = content;
		init(toUserName, fromUserName, WeixinMessageUtil.MESSAGE_TEXT,  msgId);
	}

	public void init(String content, String toUserName, String fromUserName, long createTime) {
		this.Content = content;
		init(toUserName, fromUserName, createTime, WeixinMessageUtil.MESSAGE_TEXT);
	}
	
	public void init(String content, String toUserName, String fromUserName, long createTime, String msgId) {
		this.Content = content;
		init(toUserName, fromUserName, createTime, WeixinMessageUtil.MESSAGE_TEXT, msgId);
	}
}

package com.ns.warlock.dto;

public class WeixinBaseMsg {

	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	private String MsgId;

	protected String getBaseXml() {
		return "<ToUserName><![CDATA[" + this.ToUserName + "]]></ToUserName>" + "<FromUserName><![CDATA["
				+ this.FromUserName + "]]></FromUserName><CreateTime>" + this.CreateTime
				+ "</CreateTime><MsgType><![CDATA[" + this.MsgType + "]]></MsgType><MsgId><![CDATA[" + this.MsgId
				+ "]]></MsgId>";
	}

	public void init(String toUserName, String fromUserName, String msgType) {
		init(toUserName, fromUserName, System.currentTimeMillis(), msgType);
	}

	public void init(String toUserName, String fromUserName, String msgType, String msgId) {
		init(toUserName, fromUserName, System.currentTimeMillis(), msgType, msgId);
	}

	public void init(String toUserName, String fromUserName, long createTime, String msgType) {
		init(toUserName, fromUserName, createTime, msgType, toUserName + createTime);
	}

	public void init(String toUserName, String fromUserName, long createTime, String msgType, String msgId) {
		this.ToUserName = toUserName;
		this.FromUserName = fromUserName;
		this.CreateTime = createTime;
		this.MsgType = msgType;
		this.MsgId = msgId;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

}

package com.ns.warlock.dto;

/**
 * 微信通用接口凭证
 */
public class AccessToken {
	
	/**
	 *  获取到的凭证
	 */
	private String token;
	
	/**
	 *  凭证有效时间，单位：秒
	 */
	private int expiresIn;
	
	/**
	 *  有效期时间戳，System.currentTimeMillis()
	 */
	private long timeMillis;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

	public AccessToken(String token, int expiresIn) {
		super();
		this.token = token;
		this.expiresIn = expiresIn;
		//提前十分钟失效
		this.timeMillis = System.currentTimeMillis() + (expiresIn*1000 - 60000);
	}
}
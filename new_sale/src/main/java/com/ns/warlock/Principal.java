package com.ns.warlock;

import java.io.Serializable;

/**
 *
 *
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 0L;

	/** ID */
	private Long id;

	/** 用户名 */
	private String username;

	//姓名
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(Long id, String username, String name) {
		this.id = id;
		this.username = username;
		this.name = name;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return username;
	}

}
package com.huasisoft.flow.platform.vo;

public enum RoleNodeType {
	
	/**
	 * 角色
	 */
	role("role"),
	/**
	 * 角色组
	 */
	group("node");
	
	private String name;
	
	private RoleNodeType(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}

}

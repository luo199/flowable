package com.huasisoft.flow.platform.vo;

public enum UnitType {
	
	/*Organization
	Person
	Department
	Position
	Group*/
	/**
	 * 人员
	 */
	person("Person"),
	/**
	 * 机构
	 */
	org("Organization"),
	/**
	 * 部门
	 */
	dept("Department"),
	/**
	 * 岗位
	 */
	position("Position"),
	/**
	 * 人员组
	 */
	group("Group");
	
	private String name;
	
	private UnitType(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public static String[] allUnitTypes() {
		UnitType[] types = UnitType.values();
		int size = types.length;
		String[] result = new String[size];
		for(int i=0;i<size;i++) {
			result[i]=types[i].getName();
		}
		return result;
	}
	
	

}

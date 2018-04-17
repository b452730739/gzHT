package com.elegps.javabean;

public class Buy_enrolment {

	private String Type = null; 	//产品类型
	private String TypeId = null;	//类型Id
	private String TypeName = null; //产品名
	private String TypeNum  = null; //产品数量
	private String ID = null;   	//产品Id
	
	public Buy_enrolment(){}
	
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTypeId() {
		return TypeId;
	}

	public void setTypeId(String typeId) {
		TypeId = typeId;
	}

	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	public String getTypeNum() {
		return TypeNum;
	}
	public void setTypeNum(String typeNum) {
		TypeNum = typeNum;
	}
	
	
}

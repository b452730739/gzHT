package com.elegps.javabean;

public class Buy_enrolment {

	private String Type = null; 	//��Ʒ����
	private String TypeId = null;	//����Id
	private String TypeName = null; //��Ʒ��
	private String TypeNum  = null; //��Ʒ����
	private String ID = null;   	//��ƷId
	
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

package com.elegps.javabean;

public class Video_users {

	private String CustName = null; //客服名称
	private String Mobile  = null;  //客服手机
	private String EmplNO = null ;  //客服工号
	private String Contact = null ;  //客服工号
	private String Email = null ;  //客服工号
	private String Fax = null ;  //客服工号
	private String CustAddr = null ;  //客服工号
	private String AcctType = null; //判断是否为内部账号
	
	/**
	 * @return the contact
	 */
	public String getContact() {
		return Contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		Contact = contact;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return Fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		Fax = fax;
	}
	/**
	 * @return the custAddr
	 */
	public String getCustAddr() {
		return CustAddr;
	}
	/**
	 * @param custAddr the custAddr to set
	 */
	public void setCustAddr(String custAddr) {
		CustAddr = custAddr;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String custName) {
		CustName = custName;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getEmplNO() {
		return EmplNO;
	}
	public void setEmplNO(String emplNO) {
		EmplNO = emplNO;
	}
	public String getAcctType() {
		return AcctType;
	}
	public void setAcctType(String acctType) {
		AcctType = acctType;
	}
	
	
}

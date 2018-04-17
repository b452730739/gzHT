package com.elegps.javabean;

public class GetComplainAdviseInfo {

	private String Content = null;
	private String Date = null;
	private String MachineNO = null;
	private String MachineModel = null;
	private String ProductDate = null;
	private String UUNO = null;
	private String GUID = null;
	
	/**
	 * @return the gUID
	 */
	public String getGUID() {
		return GUID;
	}

	/**
	 * @param gUID the gUID to set
	 */
	public void setGUID(String gUID) {
		GUID = gUID;
	}

	/**
	 * @return the uUNO
	 */
	public String getUUNO() {
		return UUNO;
	}

	/**
	 * @param uUNO the uUNO to set
	 */
	public void setUUNO(String uUNO) {
		UUNO = uUNO;
	}

	/**
	 * @return the machineNO
	 */
	public String getMachineNO() {
		return MachineNO;
	}

	/**
	 * @param machineNO the machineNO to set
	 */
	public void setMachineNO(String machineNO) {
		MachineNO = machineNO;
	}

	/**
	 * @return the machineModel
	 */
	public String getMachineModel() {
		return MachineModel;
	}

	/**
	 * @param machineModel the machineModel to set
	 */
	public void setMachineModel(String machineModel) {
		MachineModel = machineModel;
	}

	/**
	 * @return the productDate
	 */
	public String getProductDate() {
		return ProductDate;
	}

	/**
	 * @param productDate the productDate to set
	 */
	public void setProductDate(String productDate) {
		ProductDate = productDate;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	public String getDate() {
		return Date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		Date = date;
	}

}

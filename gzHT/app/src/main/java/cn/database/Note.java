package cn.database;

public class Note {
	private Integer id;
	private String title;
	private String et_name;
	private String et_people;
	private String et_phone;
	private String time;
	private String error;
	private String suggestion;
	
	
	public Note(Integer id, String title ,String et_name ,String et_people,
		String et_phone,String time	,String error ,String suggestion){
		
		this.id = id;
		this.title = title ;
		this.et_name = et_name;
		this.et_people = et_people;
		this.et_phone = et_phone;
		this.time = time;
		this.error = error;
		this.suggestion = suggestion;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the et_name
	 */
	public String getEt_name() {
		return et_name;
	}
	/**
	 * @param et_name the et_name to set
	 */
	public void setEt_name(String et_name) {
		this.et_name = et_name;
	}
	/**
	 * @return the et_people
	 */
	public String getEt_people() {
		return et_people;
	}
	/**
	 * @param et_people the et_people to set
	 */
	public void setEt_people(String et_people) {
		this.et_people = et_people;
	}
	/**
	 * @return the et_phone
	 */
	public String getEt_phone() {
		return et_phone;
	}
	/**
	 * @param et_phone the et_phone to set
	 */
	public void setEt_phone(String et_phone) {
		this.et_phone = et_phone;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * @return the suggestion
	 */
	public String getSuggestion() {
		return suggestion;
	}
	/**
	 * @param suggestion the suggestion to set
	 */
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	
}

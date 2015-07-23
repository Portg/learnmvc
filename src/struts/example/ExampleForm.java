package struts.example;

import struts.action.ActionForm;

public class ExampleForm extends ActionForm {

	public ExampleForm() {
		super();
	}

	public ExampleForm(String userName, String passWord, String randCode) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.randCode = randCode;
	}

	private String userName;

	private String passWord;

	private String randCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getRandCode() {
		return randCode;
	}

	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}

}

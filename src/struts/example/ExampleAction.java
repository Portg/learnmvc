package struts.example;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import struts.action.Action;
import struts.action.ActionForm;

public class ExampleAction implements Action {

	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionForm form,
			Map<String, String> actionForward) {

		String page = "failure";
		ExampleForm expform = (ExampleForm) form;
		if (expform.getUserName().equals("admin")
				&& expform.getPassWord().equals("pass")
				&& expform.getRandCode().equals("1234")) {
			page= "success";
		}
		return actionForward.get(page);
	}

}

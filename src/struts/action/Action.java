package struts.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

	String execute(HttpServletRequest request, HttpServletResponse response
			, ActionForm form, Map<String, String> actionForward);
}

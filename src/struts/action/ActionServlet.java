package struts.action;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet extends HttpServlet {

	private static final long serialVersionUID = -3137831652369054389L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String servletPath = req.getServletPath();
		String actionPath = servletPath.substring(1, servletPath.lastIndexOf("."));
		@SuppressWarnings("unchecked")
		Map<String, ActionMapping> map = (Map<String, ActionMapping>)req
				.getServletContext().getAttribute("actionMap");
		ActionMapping actionMapping = map.get(actionPath);
		String formType = actionMapping.getFormType();
		ActionForm form = this.fillForm(req, formType);

		String actionType = actionMapping.getType();

		Action action = this.getAction(actionType);
		String url = action.execute(req, resp, form
				, actionMapping.getForwardProperties());

		RequestDispatcher requestDispatcher = req.getRequestDispatcher(url);
		requestDispatcher.forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private Action getAction(String actionType) {
		Action action = null;
		try {
			Class<?> actionClazz = Class.forName(actionType);
			action               = (Action)actionClazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return action;
	}

	/**
	 * 
	 * @param request
	 * @param formType
	 * @return
	 */
	private ActionForm fillForm(HttpServletRequest request, String formType) {
		ActionForm actionForm = null;
		try {
			Class<?> formClazz = Class.forName(formType);
			actionForm         = (ActionForm) formClazz.newInstance();
			Field[] fields     = formClazz.getDeclaredFields();
			for(Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				field.set(actionForm, request.getParameter(fieldName));
				field.setAccessible(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForm;
	}
}

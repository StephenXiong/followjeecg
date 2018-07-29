package mvcframe.mvc;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcframe.base.BaseAction;
import mvcframe.base.BaseBean;
import mvcframe.base.ConfigBean;
import mvcframe.util.FullBean;

public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String actionPath = request.getServletPath();
		actionPath = actionPath.split("\\.")[0];
		Map<String, ConfigBean> map = (Map<String, ConfigBean>) this.getServletContext().getAttribute("xml");
		ConfigBean configBean = map.get(actionPath);
		String url = "";
		BaseBean baseBean = FullBean.convertBean(request, configBean.getBeanClass());
		try {
			BaseAction baseAction = (BaseAction) Class.forName(configBean.getActionClass()).newInstance();
			url = baseAction.execute(baseBean, configBean.getForwardMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dis = request.getRequestDispatcher(url);
		dis.forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

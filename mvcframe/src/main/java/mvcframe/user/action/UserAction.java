package mvcframe.user.action;

import java.util.Map;

import mvcframe.base.BaseAction;
import mvcframe.base.BaseBean;
import mvcframe.user.bean.User;

public class UserAction implements BaseAction {

	public String execute(BaseBean baseBean, Map<String, String> forwardMap) {
		User user = (User) baseBean;
		String url = "fail";
		if("admin".equals(user.getUsername())){
			url = "success";
		}
		return forwardMap.get(url);
	}


}

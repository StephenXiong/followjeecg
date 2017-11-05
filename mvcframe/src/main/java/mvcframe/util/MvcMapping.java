package mvcframe.util;

import java.util.HashMap;
import java.util.Map;

public class MvcMapping {

	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mvcframe.user.bean.User", "mvcframe.user.action.UserAction");
		return map;
	}
}

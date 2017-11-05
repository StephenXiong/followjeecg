package mvcframe.base;

import java.util.Map;

public interface BaseAction {

	String execute(BaseBean baseBean, Map<String, String> forwardMap);
}

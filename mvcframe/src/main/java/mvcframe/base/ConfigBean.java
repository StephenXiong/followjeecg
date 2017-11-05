package mvcframe.base;

import java.util.HashMap;
import java.util.Map;

public class ConfigBean {

	private String beanName;

	private String beanClass;

	private String actionName;

	private String actionClass;

	private String actionPath;

	private Map<String, String> forwardMap = new HashMap<String, String>();

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionClass() {
		return actionClass;
	}

	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public Map<String, String> getForwardMap() {
		return forwardMap;
	}

	public void setForwardMap(Map<String, String> forwardMap) {
		this.forwardMap = forwardMap;
	}

	public ConfigBean(String beanName, String beanClass, String actionName, String actionClass, String actionPath,
			Map<String, String> forwardMap) {
		super();
		this.beanName = beanName;
		this.beanClass = beanClass;
		this.actionName = actionName;
		this.actionClass = actionClass;
		this.actionPath = actionPath;
		this.forwardMap = forwardMap;
	}

}

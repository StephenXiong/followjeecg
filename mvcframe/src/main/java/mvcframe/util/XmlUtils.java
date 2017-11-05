package mvcframe.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import mvcframe.base.ConfigBean;

public class XmlUtils {

	public static Map<String, ConfigBean> parseConfig(String filePath) {
		File myxml = new File(filePath);
		SAXReader saxReader = new SAXReader();
		Map<String, ConfigBean> map = new HashMap<String, ConfigBean>();
		try {
			Document doc = saxReader.read(myxml);
			Element root = doc.getRootElement();
			Element beans = root.element("beans");
			Element actions = root.element("action-mapping");
			List<Element> childBeans = beans.elements("bean");
			List<Element> childActions = actions.elements("action");
			String actionPath = "";
			for (Element action : childActions) {
				String actionName = action.attributeValue("name");
				String actionClass = action.attributeValue("class");
				actionPath = action.attributeValue("path");
				String beanName = "";
				String beanClass = "";
				for (Element bean : childBeans) {
					beanName = bean.attributeValue("name");
					if (actionName.equals(beanName)) {
						beanClass = bean.attributeValue("class");
						break;
					}
				}
				List<Element> forwards = action.elements("forward");
				Map<String, String> forwardMap = new HashMap<String, String>();
				for (Element forward : forwards) {
					String forwardName = forward.attributeValue("name");
					String forwardPath = forward.attributeValue("path");
					forwardMap.put(forwardName, forwardPath);
				}
				ConfigBean configBean = new ConfigBean(beanName, beanClass, actionName, actionClass, actionPath,
						forwardMap);
				map.put(actionPath, configBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
}

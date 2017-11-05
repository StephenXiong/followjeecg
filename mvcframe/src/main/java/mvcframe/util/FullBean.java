package mvcframe.util;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import mvcframe.base.BaseBean;

public class FullBean {

	public static BaseBean convertBean(HttpServletRequest request, String clazzName) {
		BaseBean baseBean = null;
		try {
			Class clazz = Class.forName(clazzName);
			baseBean = (BaseBean) clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				f.set(baseBean, request.getParameter(f.getName()));
				f.setAccessible(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseBean;
	}
}

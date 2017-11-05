# self-mvc-frame

跟随JEECG 自学MVC框架
---------------

xml 配置文件格式为：

    <?xml version="1.0" encoding="UTF-8"?>
    <struts>
    	<beans>
    		<bean name="user" class= "mvcframe.user.bean.User"></bean>
    	</beans>
    	<action-mapping>
    		<action name="user" class= "mvcframe.user.action.UserAction" path="/user">
    			<forward name="success" path="/view/success.jsp"></forward>
    			<forward name="fail" path="/view/fail.jsp"></forward>
    		</action>
    	</action-mapping>
    </struts>

name 是自定义名称 bean的name必须与action—mapping中的actionName相同
class都是各自对应的实体类名称
action中的path为请求的controller名称，如ip:port/工程名/user.do 则path对应的是/user
forward是跳转的路径，目前主要是处理跳转页面逻辑

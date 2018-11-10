package com.demo.common;

import com.demo.common.model._MappingKit;
import com.demo.controller.system.AuthController;
import com.demo.controller.system.IndexControllers;
import com.demo.controller.system.SysLogController;
import com.demo.controller.system.UserController;
import com.demo.interceptor.ExceptionInterceptor;
import com.demo.interceptor.LoginInterceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		JFinal.start("WebRoot", 80, "/", 5);

		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		// JFinal.start("WebRoot", 80, "/");
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setViewType(ViewType.JSP);
		me.setUrlParaSeparator("&"); 
//		me.setBaseUploadPath("/var/www/upload");
		me.setBaseUploadPath("upload");
		/**
		 * 上传文件最大默认10M，设置为100M
		 */
		me.setMaxPostSize(10*Const.DEFAULT_MAX_POST_SIZE);
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		
		me.add(new AdminRoutes());//后端路由
		me.add(new FrontRoutes());//前端路由
		
	}
	
	public void configEngine(Engine me) {
		me.addSharedFunction("/common/_layout.html");
		me.addSharedFunction("/common/_paginate.html");
	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin();
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);
		// 配置ehCache插件
		me.add(new EhCachePlugin());
		//配置redis插件
		
	}
	
	/**
	 * 配置redis
	 * @return
	 */
	public static RedisPlugin createRedisPlugin(){
		String cacheName = PropKit.get("redisname");
		String host = PropKit.get("redisserverip");
		String password = PropKit.get("redispw");
		if(PropKit.getBoolean("devMode")){
			return new RedisPlugin(cacheName, host, 6379, 0, password, 2);
		}else{
			return new RedisPlugin(cacheName, host, 6379, 0, password, 0);
		}
	}
	
	
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new LoginInterceptor());
		me.add(new ExceptionInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
}

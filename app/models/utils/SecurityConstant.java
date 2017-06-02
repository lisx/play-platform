package models.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import org.apache.log4j.Logger;

import play.Play;
import play.modules.redis.Redis;
import framework.exceptions.ExceptionHandler;
import framework.logs.LogUtil;
import framework.utils.ContextUtil;
import framework.utils.DateUtil;
import framework.utils.DicUtil;
import framework.utils.SecurityUtil;
import framework.utils.StringUtil;

public class SecurityConstant {
	
	private final static Logger logger = LogUtil.getInstance(SecurityConstant.class);
	//是否开启菜单微调模式
	public static final boolean RESOURCE_MODIFY=Boolean.parseBoolean(ContextUtil.getProperty("security.resource.modify","false"));
	//菜单文件存放路径
	public static final String RESOURCES_MENUS_DIR="/conf/resources/menus";	
	//加载菜单资源文件时需要指定的yml文件的路径
	public static final String RESOURCES_MENUS_LOAD_PATH="resources/menus/";
	//企业资源文件前缀
	public static final String EM_RESOURCE_PREFIX="em_";
	//平台资源文件前缀
	public static final String PLATFORM_RESOURCE_PREFIX="platform_";
	public static final String DIC_PLATFORM_PREFIX="platform";
	//平台资源类型
	public static final String RESOURCE_TYPE_PLATFORM_NAME="PLATFORM";
	//企业资源类型
	public static final String RESOURCE_TYPE_EM_NAME="EM";
	//企业菜单资源
	public static final String RESOURCE_TYPE_MENU_NAME="MENU";
	//企业操作资源
	public static final String RESOURCE_TYPE_ACTION_NAME="ACTION";
	//角色类型名称
	public static final String DIC_ROLE_TYPE_NAME="platform.roleType";
	//用户状态|Job执行状态
	public static final String DIC_USER_STATUS_NAME="platform.ifUse";
	//是否系统用户
	public static final String DIC_SYSTEM_USER="platform.ifSystem";
	//下载状态
	public static final String DIC_TASK_STATUS_NAME="platform.taskStatus";
	
	//job管理
	public static final String PLAYFORM_JOB_MANAGE = "DIC.platform.jobManage";
	
	public static String iptables(){
		String iptables = ContextUtil.getProperty("interface.iptables");
		iptables = StringUtil.trim(iptables);
		String local = "127.0.0.1";
		if(iptables.indexOf(local)==-1){
			if(StringUtil.isBlank(iptables)){
				iptables=local;
			}else{
				iptables = iptables+","+local;
			}
		}
		return iptables;
	}
	
	//服务ip列表
	public static final String DIC_IP_LIST_NAME="ipList";
	
	public static String EM_DOMAIN = getEmDomain();
	
	public static String getEmDomain(){
		return ContextUtil.getProperty("em.domain", "");
	}
	
	//服务端口号
	public static final String PROJECT_PORT=getConfigPort();
	private static String getConfigPort(){
		return ContextUtil.getProperty("http.port","");
	}
	
	public static final String DIC_ALL_USER_NAME="allUserList";
	
	/**
	 * 日期转毫秒
	 * @param datetime
	 * @return
	 */
	public static long datetime2Millisecond(String datetime){
		long mills = 0;
		try {
			if(!StringUtil.isBlank(datetime)){
				Date date = new SimpleDateFormat(DateUtil.SHORT_TIME_PATTERN).parse(datetime);
				mills = date.getTime();
			}
		} catch (ParseException e) {
			ExceptionHandler.throwRuntimeException(e,logger);
		}
		return mills;
	}
	
	public final static String DIC_YES_OR_NO_NAME="platform.yesOrNo";
	
	public final static String DIC_NO_OR_YES_NAME="platform.noOrYes";
	
	public static boolean ifRunJob(Class<?> clazz){
		String result = Redis.hget(SecurityConstant.PLAYFORM_JOB_MANAGE, PLATFORM_RESOURCE_PREFIX+clazz.getSimpleName());
		boolean ifRun = false;
		if(!StringUtil.isBlank(result) && "1".equals(result)){
			ifRun = true;
		}
		return ifRun;
	}
	
	public final static String DIC_KEYS_NAME = "platform.keys";
}

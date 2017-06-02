package models.utils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.security.Permission;
import models.security.Resource;

import framework.utils.StringUtil;

import play.Play;
import play.test.Fixtures;

public class ResourceUtil {
	/**
	 * 初始化资源信息
	 */
	public static void initResources(){
		//如果允许修改资源信息
		if(SecurityConstant.RESOURCE_MODIFY){
			Resource.deleteAll();
			File resourceDir = Play.getFile(SecurityConstant.RESOURCES_MENUS_DIR);
			if(resourceDir.exists() && resourceDir.isDirectory()){
				File[] menus = resourceDir.listFiles();
				for(File f : menus){
					String name = f.getName();
					if(name.endsWith("menu.yml")){
						loadMenuResource(f);
					}else if(name.endsWith("action.yml")){
						loadActionResource(f);
					}
				}
			}
		}else{
			System.out.println("不需要加载资源");
		}
	}
	/**
	 * 加载菜单资源文件
	 * @param f
	 */
	public static void loadMenuResource(File f){
		String resourceType = f.getName().startsWith(SecurityConstant.PLATFORM_RESOURCE_PREFIX)?SecurityConstant.RESOURCE_TYPE_PLATFORM_NAME:SecurityConstant.RESOURCE_TYPE_EM_NAME;
		List<Map<String,Object>> menus =(List<Map<String,Object>>)Fixtures.loadYamlAsList(SecurityConstant.RESOURCES_MENUS_LOAD_PATH+f.getName());
		Set<String> permissionCodes = Permission.allMenuCode();
		Set<String> updateCodes= new HashSet<String>();
		if (null != menus && !menus.isEmpty()) {
			for(Map<String,Object> menu : menus){
				String name = StringUtil.trim(menu.get("name"));
				String code = StringUtil.trim(menu.get("code"));
				String url = StringUtil.trim(menu.get("url"));
				Resource r = new Resource(name,code,SecurityConstant.RESOURCE_TYPE_MENU_NAME,resourceType,url);
				r.save();
				if(!permissionCodes.contains(code)){
					permissionCodes.add(code);
					updateCodes.add(code);
				}
				List<Map<String,String>> nodeMenus = (List<Map<String,String>>)menu.get("resources");
				for(Map<String,String> node : nodeMenus){
					String nodeName = StringUtil.trim(node.get("name"));
					String nodeCode = StringUtil.trim(node.get("code"));
					String nodeUrl = StringUtil.trim(node.get("url"));
					Resource nodeResource = new Resource(nodeName,nodeCode,SecurityConstant.RESOURCE_TYPE_MENU_NAME,resourceType,nodeUrl);
					nodeResource.save();
					if(!permissionCodes.contains(nodeCode)){
						permissionCodes.add(nodeCode);
						updateCodes.add(nodeCode);
					}
				}
			}
		}
		Permission.batchAdd(updateCodes, SecurityConstant.RESOURCE_TYPE_MENU_NAME);
	}
	
	/**
	 * 加载菜单资源文件
	 * @param f
	 */
	public static void loadActionResource(File f){
		String resourceType = f.getName().startsWith(SecurityConstant.PLATFORM_RESOURCE_PREFIX)?SecurityConstant.RESOURCE_TYPE_PLATFORM_NAME:SecurityConstant.RESOURCE_TYPE_EM_NAME;
		List<Map<String,Object>> menus =(List<Map<String,Object>>)Fixtures.loadYamlAsList(SecurityConstant.RESOURCES_MENUS_LOAD_PATH+f.getName());
		Set<String> permissionCodes = Permission.allMenuCode();
		Set<String> updateCodes= new HashSet<String>();
		if (null !=menus && !menus.isEmpty()) {
			for(Map<String,Object> menu : menus){
				String name = StringUtil.trim(menu.get("name"));
				String code = StringUtil.trim(menu.get("code"));
				String url = StringUtil.trim(menu.get("url"));
				Resource r = new Resource(name,code,SecurityConstant.RESOURCE_TYPE_ACTION_NAME,resourceType,url);
				r.save();
				if(!permissionCodes.contains(code)){
					permissionCodes.add(code);
					updateCodes.add(code);
				}
				List<Map<String,String>> nodeMenus = (List<Map<String,String>>)menu.get("resources");
				for(Map<String,String> node : nodeMenus){
					String nodeName = StringUtil.trim(node.get("name"));
					String nodeCode = StringUtil.trim(node.get("code"));
					String nodeUrl = StringUtil.trim(node.get("url"));
					Resource nodeResource = new Resource(nodeName,nodeCode,SecurityConstant.RESOURCE_TYPE_ACTION_NAME,resourceType,nodeUrl);
					nodeResource.save();
					if(!permissionCodes.contains(nodeCode)){
						permissionCodes.add(nodeCode);
						updateCodes.add(nodeCode);
					}
				}
			}
		}
		Permission.batchAdd(updateCodes, SecurityConstant.RESOURCE_TYPE_ACTION_NAME);
	}
	
}

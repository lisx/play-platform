package models.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import models.utils.SecurityConstant;

import framework.base.BaseModel;
@Entity
@Table(name="T_PERMISSION")
public class Permission extends BaseModel {
	//资源编码
	public String code;
	//资源类型 MENU:资源 ACTION:操作
	public String type;
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="permissions")	
	public List<Role> roles = new ArrayList<Role>();
	
	public Permission(){
		
	}
	
	public Permission(String code,String type){
		this.code = code;
		this.type = type;
	}
	
	/**
	 * 所有菜单的资源编码
	 * @return
	 */
	public static Set<String> allMenuCode(){
		Set<String> result = new HashSet<String>();
		List<Permission> permissions =  queryByType(SecurityConstant.RESOURCE_TYPE_MENU_NAME);
		for(Permission p : permissions){
			result.add(p.code);
		}
		return result;
	}
	/**
	 * 所有操作的资源编码
	 * @return
	 */
	public static Set<String> allActionCode(){
		Set<String> result = new HashSet<String>();
		List<Permission> permissions = queryByType(SecurityConstant.RESOURCE_TYPE_ACTION_NAME);
		for(Permission p : permissions){
			result.add(p.code);
		}
		return result;
	}
	
	/**
	 * 根据资源类型查询
	 * @param type
	 * @return
	 */
	public static List<Permission> queryByType(String type){
		String sql="from Permission a where a.type=?";
		return Permission.find(sql, type).fetch();
	}
   /**
    * 批量新增权限信息
    * @param codes
    * @param type
    */
	public static void batchAdd(Set<String> codes ,String type){
		for(String code : codes){
			Permission p = new Permission(code,type);
			p.save();
		}
	}
	/**
	 * 初始化菜单信息
	 * @param permissions
	 * @return
	 */
	public static Map<String,String> initResource( Collection<Permission> permissions){
		Map<String,String> result = new HashMap<String,String>();
		JSONObject menuObj = new JSONObject();
		JSONObject actionObj = new JSONObject();
		if(null!=permissions){
			Resource r = null;
			String rootCode="";
			for(Permission  permission : permissions){
				r = Resource.findByCode(permission.code);
				if(null!=r){
					rootCode = r.getRootCode();
					JSONArray array = null;
					if(SecurityConstant.RESOURCE_TYPE_MENU_NAME.equals(permission.type)){
						if(!menuObj.containsKey(rootCode)){
							array = new JSONArray();
							array.add(r.code);
							menuObj.put(rootCode, array);
						}else{
							menuObj.accumulate(rootCode,r.code);
						}
					}else{
						if(!actionObj.containsKey(rootCode)){
							array = new JSONArray();
							array.add(r.code);
							actionObj.put(rootCode, array);
						}else{
							actionObj.accumulate(rootCode,r.code);
						}
					}
				}
			}
		}
		result.put(SecurityConstant.RESOURCE_TYPE_MENU_NAME,menuObj.toString());
		result.put(SecurityConstant.RESOURCE_TYPE_ACTION_NAME, actionObj.toString());
		return result;
	}
	
	public static List<Permission> queryByRType(String rtype){
		List<Permission> results = new ArrayList<Permission>();
		List<Permission> permissions = Permission.findAll();
		Resource resource = null;
		for (Permission permission : permissions) {
			resource = Resource.findByCode(permission.code);
			if (null != resource && rtype.equals(resource.rType)) {
				results.add(permission);
			}
		}
		return results;
	}
	
	
}

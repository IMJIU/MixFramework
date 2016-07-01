package com.framework.json;

import java.text.SimpleDateFormat;
import java.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MyTestJson {

	public static void main(String[] args) {
		MyUser myUser = new MyUser();
		myUser.setUserid(1L);
		myUser.setUsername("shihuan");
		myUser.setSex(true);
		myUser.setBirthday(new Date());
		String myuserjson = JSON.toJSONString(myUser, SerializerFeature.WriteDateUseDateFormat);
		System.out.println(myuserjson); // {"birthday":"2015-10-23 10:25:03","sex":true,"userid":1,"username":"shihuan"}

		String myuserjsondateformat = JSON.toJSONStringWithDateFormat(myUser, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
		System.out.println(myuserjsondateformat); // {"birthday":"2015-10-23","sex":true,"userid":1,"username":"shihuan"}

		String myuserjsondateformathm = JSON.toJSONStringWithDateFormat(myUser, "yyyy-MM-dd HH:mm:ss.SSS", SerializerFeature.WriteDateUseDateFormat);
		System.out.println(myuserjsondateformathm); // {"birthday":"2015-10-23 10:25:03.509","sex":true,"userid":1,"username":"shihuan"}

		MyUser myJsonUser = JSON.parseObject(myuserjson, MyUser.class);
		System.out.println(myJsonUser.getUserid() + " -- " + myJsonUser.getUsername() + " -- " + myJsonUser.getBirthday() + " -- " + myJsonUser.isSex());

		MyUser myJsonUserDateformat = JSON.parseObject(myuserjson, MyUser.class, Feature.AllowISO8601DateFormat);
		System.out.println(myJsonUserDateformat.getUserid() + " -- " + myJsonUserDateformat.getUsername() + " -- "
		        + getFormatString(myJsonUserDateformat.getBirthday()) + " -- " + myJsonUserDateformat.isSex());
		System.out.println("-----------------------------------------------------------------------------------------------------");

		MyUser myUser1 = new MyUser();
		myUser1.setUserid(1L);
		myUser1.setUsername("shihuan1");
		myUser1.setSex(true);
		myUser1.setBirthday(new Date());
		MyUser myUser2 = new MyUser();
		myUser2.setUserid(2L);
		myUser2.setUsername("shihuan2");
		myUser2.setSex(false);
		myUser2.setBirthday(new Date());
		List<MyUser> listmyusers = new ArrayList<MyUser>();
		listmyusers.add(myUser1);
		listmyusers.add(myUser2);

		// List -> JSON array
		String listmyusersjson = JSON.toJSONString(listmyusers, false); // 第二个参数表示是否格式化输出
		System.out.println(listmyusersjson); // [{"birthday":1445567103928,"sex":true,"userid":1,"username":"shihuan1"},{"birthday":1445567103928,"sex":false,"userid":2,"username":"shihuan2"}]

		// JSON array -> List
		List<MyUser> myUserList = JSON.parseArray(listmyusersjson, MyUser.class);
		for (MyUser myUserTmp : myUserList) {
			System.out.println(myUserTmp.getUserid() + " -- " + myUserTmp.getUsername() + " -- " + myUserTmp.isSex() + " -- " + myUserTmp.getBirthday());
		}
		System.out.println("--------------------------------------------------------------------------------------");

		System.out.println("复合List对象的VO对象开始......");
		MyGroupUser myGroupUser = new MyGroupUser();
		myGroupUser.setGroupid(1L);
		myGroupUser.setGroupname("gshihuan");
		myGroupUser.setListuser(listmyusers);
		String mygroupuserjson = JSON.toJSONString(myGroupUser, SerializerFeature.WriteDateUseDateFormat);
		System.out.println(mygroupuserjson);
		MyGroupUser myJsonGroupUser = JSON.parseObject(mygroupuserjson, MyGroupUser.class);
		System.out.println(myJsonGroupUser.getGroupid() + " -- " + myJsonGroupUser.getGroupname() + " -- " + myJsonGroupUser.getListuser().get(0).getUserid()
		        + "&" + myJsonGroupUser.getListuser().get(0).getUsername() + "&" + myJsonGroupUser.getListuser().get(0).getBirthday() + "&"
		        + myJsonGroupUser.getListuser().get(0).isSex() + " -- " + myJsonGroupUser.getListuser().get(1).getUserid() + "&"
		        + myJsonGroupUser.getListuser().get(1).getUsername() + "&" + myJsonGroupUser.getListuser().get(1).getBirthday() + "&"
		        + myJsonGroupUser.getListuser().get(1).isSex());
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("复合Set对象的VO对象开始......");
		Set<MyUser> setObjData = new HashSet<MyUser>();
		setObjData.add(myUser1);
		setObjData.add(myUser2);
		MyGroupSetUser myGroupSetUser = new MyGroupSetUser();
		myGroupSetUser.setGroupsetid(1L);
		myGroupSetUser.setGroupsetname("gsetshihuan");
		myGroupSetUser.setSetuser(setObjData);
		String mygroupsetuserjson = JSON.toJSONString(myGroupSetUser, SerializerFeature.WriteDateUseDateFormat);
		System.out.println(mygroupsetuserjson);
		MyGroupSetUser myJsonGroupSetUser = JSON.parseObject(mygroupsetuserjson, MyGroupSetUser.class);
		System.out.println(myJsonGroupSetUser.getGroupsetid() + " -- " + myJsonGroupSetUser.getGroupsetname());
		for (Iterator<MyUser> myUserIter = myJsonGroupSetUser.getSetuser().iterator(); myUserIter.hasNext();) {
			MyUser nbUserObj = myUserIter.next();
			System.out.println(nbUserObj.getUserid() + "&" + nbUserObj.getUsername() + "&" + nbUserObj.isSex() + "&" + nbUserObj.getBirthday());
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("复合Map对象的VO对象开始......");
		Map<String, MyUser> myUserMap = new HashMap<String, MyUser>();
		myUserMap.put("1", myUser1);
		myUserMap.put("2", myUser2);
		MyGroupMapUser myGroupMapUser = new MyGroupMapUser();
		myGroupMapUser.setGroupmapid(1L);
		myGroupMapUser.setGroupmapname("gmapshihuan");
		myGroupMapUser.setMapuser(myUserMap);
		String mygroupmapuserjson = JSON.toJSONString(myGroupMapUser, SerializerFeature.WriteDateUseDateFormat);
		System.out.println(mygroupmapuserjson);
		MyGroupMapUser myJsonGroupMapUser = JSON.parseObject(mygroupmapuserjson, MyGroupMapUser.class);
		System.out.println(myJsonGroupMapUser.getGroupmapid() + " -- " + myJsonGroupMapUser.getGroupmapname());
		Map<String, MyUser> myUserMapData = myJsonGroupMapUser.getMapuser();
		for (Object obj : myUserMapData.keySet()) {
			String key = (String) obj;
			MyUser value = (MyUser) myUserMapData.get(key);
			System.out.println(key + " : " + value.getUserid() + "&" + value.getUsername() + "&" + value.isSex() + "&" + value.getBirthday());
		}
		for (Entry<String, MyUser> userMapData : myJsonGroupMapUser.getMapuser().entrySet()) {
			Entry entry = (Entry) userMapData;
			String key = (String) entry.getKey();
			MyUser value = (MyUser) entry.getValue();
			System.out.println(key + " : " + value.getUserid() + "&" + value.getUsername() + "&" + value.isSex() + "&" + value.getBirthday());
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		// Map -> JSON
		Map<String, MyUser> map = new HashMap<String, MyUser>();
		map.put("a", myUser1);
		map.put("b", myUser2);
		String mapjson = JSON.toJSONString(map, false);
		System.out.println(mapjson);
		// JSON -> Map
		Map<String, MyUser> map1 = (Map<String, MyUser>) JSON.parse(mapjson);
		for (String key : map1.keySet()) {
			System.out.println(key + " : " + map1.get(key));
		}

		System.out.println("-----------------------------------------------------------------------------------------------------------------");

		String[] arrStrs = { "a", "b", "c" };
		String jsonarrStrs = JSON.toJSONString(arrStrs, false);
		System.out.println(jsonarrStrs);
		JSONArray jsonArray1 = JSON.parseArray(jsonarrStrs);
		for (Object o : jsonArray1) {
			System.out.println(o);
		}
		System.out.println(jsonArray1);

		System.out.println("-----------------------------------------------------------------------------------------------------------------");

		MyUser[] arrMyUsers = { myUser1, myUser2 };
		String jsonarrMyUsers = JSON.toJSONString(arrMyUsers, false);
		System.out.println(jsonarrMyUsers);
		JSONArray jsonArray2 = JSON.parseArray(jsonarrMyUsers);
		for (Object o : jsonArray2) {
			System.out.println(o);
		}
		System.out.println(jsonArray2);

		System.out.println("-----------------------------------------------------------------------------------------------------------------");

		Map zzmap = new HashMap();
		zzmap.put("a", "aaa");
		zzmap.put("b", "bbb");
		zzmap.put("c", "ccc");
		String zzmapjson = JSON.toJSONString(zzmap);
		System.out.println(zzmapjson);
		Map zzmapobj = JSON.parseObject(zzmapjson);
		for (Object o : zzmap.entrySet()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) o;
			System.out.println(entry.getKey() + "--->" + entry.getValue());
		}
	}

	private static String getFormatString(Date birthday) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(birthday);
	}

}
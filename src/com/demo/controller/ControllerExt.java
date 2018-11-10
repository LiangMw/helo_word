package com.demo.controller;

import com.demo.common.model.User;
import com.demo.common.model.Userlog;
import com.demo.util.MyException;
import com.demo.util.SysStatus;
import com.demo.util.TypeConverter;
import com.jfinal.core.Controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jfinal.i18n.I18n;
import com.jfinal.i18n.Res;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

/**
 * @Description:
 * @author jeff
 * @Date 2016年7月4日 下午4:32:51
 */
public class ControllerExt extends Controller {

	public static final String CODE = "code";
	public static final String MESSAGE = "msg";
	public static final String RESULT = "result";
	// public static Logger logger = Logger.getLogger(Controller.class);
	/**
	 * 日期类型转换 yyyy-MM-dd HH:mm:ss
	 */
	public SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	/**
	 * 日期类型转换 yyyy-MM-dd
	 */
	public SimpleDateFormat dfShort = new SimpleDateFormat("yyyy-MM-dd");

	public SimpleDateFormat dfYm = new SimpleDateFormat("yyyyMM");
	public SimpleDateFormat df_Ym = new SimpleDateFormat("yyyy-MM");
	public volatile boolean isError = false;
	
	/**
	 * 根据日期 格式为2017-05 计算当月的工作日，（工作日出去周六日，法定节假日除外）
	 * @param date
	 * @param sat true 包含周六 false 不包含周六
	 * @param sun true 包含周日 false 不包含周日
	 * @return 天数
	 */
	public int getWorkDays(String date,Boolean sat,Boolean sun){
		try {
			int count = 0;
			if (date == null || "".equals(date.trim()))
				return count;
			Date d= df_Ym.parse(date.trim());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			int month = calendar.get(calendar.MONTH);
			while(calendar.get(calendar.MONTH)<month+1){
				int day = calendar.get(calendar.DAY_OF_WEEK);
				//不排除周六周日
				if(sat&&sun){
					count ++;
				}else if(sat&&!sun)//只排除周日
				{
					if(!(day==calendar.SUNDAY)){
						count++;
					}
				}else if(!sat&&sun){//只排除周六
					if(!(day==calendar.SATURDAY)){
						count++;
					}
					
				}else{//排除周六日
					if(!(day==calendar.SUNDAY||day==calendar.SATURDAY)){
						count ++;
					}
				}
				calendar.add(calendar.DATE, 1);
			}
			return count;
			
		} catch (Exception e) {
			throw new RuntimeException("Can not count the day of the month");
		}
	}
	
	/***
	 * 得到查询的开始日期，根据days增量计算出结束日期
	 * @param bdate
	 * @param days
	 * @return
	 * @throws ParseException 
	 */
	public String getEndDate(String bdate,Integer days) throws ParseException {
		Date date = new Date();
		if(bdate==null||bdate.equals("")){
			return dfShort.format(date).toString();
		}else{
			date = dfShort.parse(bdate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
			date=cal.getTime();  
		}
		
		return dfShort.format(date).toString();
	}
	public HashMap<String, Object> resultMap = new HashMap<String, Object>();

	/**
	 * 获取当前的登录用户
	 * @return
	 */
	public User getCurrentUser(){
		return getSessionAttr("loginUser");	
	}
	
	public HashMap<String, Object> initHashMap() {

		resultMap.put(CODE, SysStatus.CODESUCCESS);
		resultMap.put(MESSAGE, SysStatus.initStatusMap().get(SysStatus.CODESUCCESS));

		return resultMap;
	}

	public HashMap<String, Object> errorMsg(Object msg) {

		isError = true;
		resultMap.clear();
		resultMap.put(CODE, SysStatus.CODECOMMONERROR);
		resultMap.put(MESSAGE, msg);
		return resultMap;

	}

	public HashMap<String, Object> successMsg(Object msg) {

		isError = true;
		resultMap.clear();
		resultMap.put(CODE, SysStatus.CODESUCCESS);
		resultMap.put(MESSAGE, msg);
		return resultMap;

	}
	/**
	 * initStatusMap中添加所有错误码对应的错误提示，然后返回
	 * 
	 * @param code
	 * @return
	 */
	public HashMap<String, Object> errorCode(String code) {

		isError = true;
		resultMap.clear();

		resultMap.put(CODE, code);
		resultMap.put(MESSAGE, SysStatus.initStatusMap().get(code));
		return resultMap;

	}

	public boolean isError(){
		return isError;
	}

	/**
	 * 返回错误提示信息及相应错误代码
	 * 
	 * @param errorMsg
	 * @param errorCode
	 */
	public void renderAppErrorAndCode(String errorMsg, String errorCode) {
		isError = true;
		resultMap.clear();

		resultMap.put(CODE, errorCode);
		resultMap.put(MESSAGE, errorMsg);
		renderAppJson(resultMap);
	}

	/**
	 * 返回错误提示信息及通用的错误代码
	 * 
	 * @param errorMsg
	 */
	public void renderAppError(String errorMsg) {
		errorMsg(errorMsg);
		renderAppJson(resultMap);
	}

	/**
	 * 返回成功提示信息及通用的错误代码
	 * 
	 * @param errorMsg
	 */
	public void renderAppSuccess(String successMsg) {
		successMsg(successMsg);
		renderAppJson(resultMap);
	}
	
	public void renderAppJson(Object obj) {
		if (!isError()) {
			resultMap.clear();
			resultMap.put(RESULT, obj);
			resultMap = initHashMap();

		} else {
			isError = false; // 初始化
		}
		this.renderJson(JsonKit.toJson(resultMap));
	}

	/**
	 * 获取国际化结果
	 * 
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		Res res = null;
		Integer lang = getParaToInt("lang");
		if (lang == null) {
			res = I18n.use("zh_CN");
		} else if (lang == 1) {
			res = I18n.use("en_US");
		}
		return res.get(key);
	}

	@SuppressWarnings("unchecked")
	public static final <T> T injModel(Class<T> modelClass, Map<String, String> parasMap, boolean skipConvertError) {
		Object temp = createInstance(modelClass);
		if (temp instanceof Model == false) {
			throw new IllegalArgumentException("getModel only support class of Model, using getBean for other class.");
		}

		Model<?> model = (Model<?>) temp;
		Table table = TableMapping.me().getTable(model.getClass());
		if (table == null) {
			throw new ActiveRecordException("The Table mapping of model: " + modelClass.getName() + " not exists or the ActiveRecordPlugin not start.");
		}

		// 对 paraMap进行遍历而不是对table.getColumnTypeMapEntrySet()进行遍历，以便支持
		// CaseInsensitiveContainerFactory
		// 以及支持界面的 attrName有误时可以感知并抛出异常避免出错
		for (Entry<String, String> entry : parasMap.entrySet()) {
			String paraName = entry.getKey();
			String attrName = paraName;
			// if (modelNameAndDot != null) {
			// if (paraName.startsWith(modelNameAndDot)) {
			// attrName = paraName.substring(modelNameAndDot.length());
			// } else {
			// continue ;
			// }
			// } else {
			// attrName = paraName;
			// }

			Class<?> colType = table.getColumnType(attrName);
			if (colType == null) {
				if (skipConvertError) {
					continue;
				} else {
					throw new ActiveRecordException("The model attribute " + attrName + " is not exists.");
				}
			}

			try {
				// String[] paraValueArray = entry.getValue();
				// String paraValue = (paraValueArray != null &&
				// paraValueArray.length > 0) ? paraValueArray[0] : null;
				//
				// Object value = paraValue != null ?
				// com.holdsoft.util.TypeConverter.convert(colType, paraValue) :
				// null;
				// model.set(attrName, value);
				String paraValue = entry.getValue();

				Object value = paraValue != null ? TypeConverter.convert(colType, paraValue) : null;
				model.set(attrName, value);
			} catch (Exception e) {
				if (skipConvertError == false) {
					throw new RuntimeException("Can not convert parameter: " + paraName, e);
				}
			}
		}

		return (T) model;
	}

	private static <T> T createInstance(Class<T> objClass) {
		try {
			return objClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Date strToDate(String value, Date defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			// return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.trim()); //返回值没有时分秒
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(value.trim());
		} catch (Exception e) {
			throw new RuntimeException("Can not convert parameter to datetime ");
		}
	}

	/**
	 * 转换为带时分秒的日期格式
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public Date toDateTime(String value, Date defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			// return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.trim()); //返回值没有时分秒
			return df.parse(value.trim());
		} catch (Exception e) {
			throw new RuntimeException("Can not convert parameter to datetime ");
		}
	}

	public String toDateTimeString(String value, String defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			return df.format(df.parse(value.trim()));
		} catch (Exception e) {
			throw new RuntimeException("Can not convert parameter to datetime String");
		}
	}

	public Date toDate(String value, Date defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			return dfShort.parse(value.trim());
		} catch (Exception e) {
			throw new RuntimeException("Can not convert parameter to date ");
		}
	}
/**
 * 将日期的月份增加increase 并转换格式为 2017-05
 * @param date
 * @param increase
 * @return
 */
	public String subMonthofOne(Date date,int increase){
		if(date == null) date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,increase);
		return  df_Ym.format(calendar.getTime());
		
	}
	/**
	 * 将格式为 2017-05的字符串转化为 date格式
	 * @param sdate
	 * @param defaultValue
	 * @return
	 */
	public Date toDate(String sdate,String defaultValue){
		try {
			if (sdate == null || "".equals(sdate.trim()))
				return df_Ym.parse(defaultValue);
			return df_Ym.parse(sdate.trim());
		} catch (Exception e) {
			throw new RuntimeException("Can not convert parameter to date ");
		}
		
	}
	public String getlastDayOfMonth(Date date){
		if(date == null) date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(ca.MONTH, 1);
		ca.add(ca.DAY_OF_MONTH, -1);
		return dfShort.format(ca.getTime());
	}
	public String toDateString(String value, String defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			return dfShort.format(dfShort.parse(value.trim()));
		} catch (Exception e) {
			throw new RuntimeException("Can not convert parameter to date String");
		}
	}

	public MyException BuildException(String forCustomer, String forDev, Exception e) {

		return new MyException(forCustomer, forDev, e);
	}

	public void putInMap(Map<String, String> parasMap, String parasKey, String parasValue) {
		// if (!StringUtils.isEmpty(parasValue)) {
		// parasMap.put(parasKey, parasValue);
		// }
		if (parasValue != null) {
			parasMap.put(parasKey, parasValue);
		}
	}
	 public Userlog userlog = new Userlog();
}

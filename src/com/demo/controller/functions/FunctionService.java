package com.demo.controller.functions;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.demo.controller.functions.resultbean.PictureTextBean;
import com.demo.util.HttpUtils;
import com.google.gson.Gson;

public class FunctionService {
	
	private String appCode = "78ddd918b0fc4a83a5f9b1e20ca1e83a";
	
	/**
	 * 解析电子书图片上的文字
	 * 
	 * @param picurl 网络图片地址、或图片经过base64加密后的字符串
	 * @return 带结果的对象
	 */
	public PictureTextBean getPictureText(String picurl){
		
		 	String host = "http://txtbookocr.market.alicloudapi.com";
		    String path = "/bookOCR";
		    String method = "POST";
		    String appcode = appCode;
		    Map<String, String> headers = new HashMap<String, String>();
		    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		    headers.put("Authorization", "APPCODE " + appcode);
//		    headers.put("image", "data:image/png;base64");
		    //根据API的要求，定义相对应的Content-Type
		    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		    Map<String, String> querys = new HashMap<String, String>();
		    Map<String, String> bodys = new HashMap<String, String>();
		    bodys.put("image", picurl);
		    try {
		    	/**
		    	* 重要提示如下:
		    	* HttpUtils请从
		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
		    	* 下载
		    	*
		    	* 相应的依赖请参照
		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
		    	*/
		    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
		    	//System.out.println(response.toString());
		    	//获取response的body
//		    	System.out.println(EntityUtils.toString(response.getEntity()));
		    	Gson gson=new Gson();
		        //1、
		        //解析对象：第一个参数：待解析的字符串 第二个参数结果数据类型的Class对象
		    	PictureTextBean grade=gson.fromJson(EntityUtils.toString(response.getEntity()), PictureTextBean.class);
		    	return grade;
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	return null;
		    }
			
	}
	
	
	

}

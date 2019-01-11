package com.demo.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.demo.util.HttpUtils;
import com.demo.util.ImageUtils;
import com.oreilly.servlet.Base64Encoder;

public class Main {
	
	public static void main(String[] a){
		    String host = "http://txtbookocr.market.alicloudapi.com";
		    String path = "/bookOCR";
		    String method = "POST";
		    String appcode = "78ddd918b0fc4a83a5f9b1e20ca1e83a";
		    Map<String, String> headers = new HashMap<String, String>();
		    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		    headers.put("Authorization", "APPCODE " + appcode);
//		    headers.put("image", "data:image/png;base64");
		    //根据API的要求，定义相对应的Content-Type
		    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		    Map<String, String> querys = new HashMap<String, String>();
		    Map<String, String> bodys = new HashMap<String, String>();
//		    bodys.put("image", "https://appbundle.holdsoft.cn/Screenshot_1547090720.png");
		    
		    StringBuilder sb = new StringBuilder();
//		    bodys.put("image", sb.toString());
		    bodys.put("image", ImageUtils.imageToBase64Str("D:\\0190111182351.png"));
//		    System.out.println(ImageUtils.imageToBase64Str("D:\\微信图片_20180404155220.png"));
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
		    	System.out.println(EntityUtils.toString(response.getEntity()));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		
	}

}

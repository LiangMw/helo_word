package com.demo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class StrOperate {
	
	public static  HashMap<Integer,String> map= new HashMap<Integer,String>();
	//系统设置  用户管理
	public static final Integer SYSTEMUSERMANAGE=4;
	//后台权限管理
	public static final Integer BACKMANAGE=5;
	//用户操作日志
	public static final Integer USERLOG=6;
	//Admin组织管理
	public static final Integer ADMINORGMANAGE=21;
	//Admin组织人员管理
	public static final Integer ADMINORGPEOPLEMANAGE=22;
	//Admin用户管理
	public static final Integer ADMINPEOPLEMANAGE=23;
	//组织管理
	public static final Integer ORGMANAGE=24;
	//组织人员管理
	public static final Integer ORGPEOPLEMANAGE=25;
	//用户管理
	public static final Integer PEOPLEMANAGE=26;
	//国际新闻
	public static final Integer WORLDNEWS=18;
	//国内新闻
	public static final Integer CHINANEWS=27;
	//所内新闻
	public static final Integer HOMENEWS=28;
	//技术动态
	public static final Integer TECHNOLOGY=29;
	//新闻回收站
	public static final Integer NEWSRUBBISH=19;
	//首页弹窗管理
	public static final Integer POPUP=32;
	//版本管理
	public static final Integer VERSION=33;
	//积分管理
	public static final Integer BONUSPOINTMANAGE=37;
	
	public static HashMap<Integer, String> initStatusMap() {

		map.put(StrOperate.SYSTEMUSERMANAGE, "系统用户管理：");
		map.put(StrOperate.BACKMANAGE, "后台权限管理：");
		
		
		return map;
	}
	 /**
     * 获取登录用户IP地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getHeader("Proxy-Client-IP");
       }
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getHeader("WL-Proxy-Client-IP");
       }
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getRemoteAddr();
       }
       if (ip.equals("0:0:0:0:0:0:0:1")) {
           ip = "本地";
       }
       return ip;
   }
    
    /** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{  
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
  
  
        System.out.println("info:"+url+" download success");   
  
    }  
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    } 
    
    
}

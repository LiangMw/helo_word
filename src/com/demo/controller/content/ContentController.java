package com.demo.controller.content;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import com.demo.common.SysConfig;
import com.demo.controller.ControllerExt;
import com.demo.util.DwzJson;
import com.demo.util.FileService;
import com.demo.util.StrOperate;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class ContentController extends ControllerExt {

	public void index(){
		Integer pageNumber=getParaToInt("pageNum")!=null?getParaToInt("pageNum"):1;
		Integer pageSize=getParaToInt("numPerPage")!=null?getParaToInt("numPerPage"):SysConfig.defaultPageSize;
//		Page<FFiles> Pagefiles=ContentService.getall(pageNumber, pageSize);
//		setAttr("page", Pagefiles);
		render("content.jsp");
	}
	
	/**
	 * 添加文件
	 */
	public void batchadduser() {
		render("BatchAddUser.jsp");
	}
	
	public void uploadFile() throws IOException {
		UploadFile uploadFile = this.getFile();
		// String fileName = uploadFile.getOriginalFileName();
		File file = uploadFile.getFile();
		FileService fs = new FileService();
//		Integer orgid = getParaToInt("orgid");
		String sysName = UUID.randomUUID().toString().replace("-", "");
		String filename = uploadFile.getOriginalFileName();
		//本地Windows用这个
		String path = PathKit.getWebRootPath() + "\\upload\\"+sysName;
		//linux下用这个
//		String path = "/var/www/upload/"+sysName;
		File t = new File(path);
		int impexcelstate = 1;
		try {
			t.createNewFile();
		} catch (IOException e) {
			impexcelstate = 0;
			e.printStackTrace();
		}
		fs.fileChannelCopy(file, t);
		file.delete();
//		FFiles impexcellog = new FFiles();
//		impexcellog.setUploadtime(new Date());
//		impexcellog.setFilename(filename);
//		if(filename.contains(".")){
//			impexcellog.setFiletype(filename.substring(filename.lastIndexOf(".")+1, filename.length()));
//		}
//		impexcellog.setEncryptname(sysName);
//		impexcellog.setState(impexcelstate);
//		impexcellog.setUploadid(Integer.parseInt(getSessionAttr("loginUserid")+""));
//		impexcellog.save();
		userlog.addLog(Integer.parseInt(getSessionAttr("loginUserid")+""), StrOperate.BACKMANAGE, getSessionAttr("ip")+"", "上传文件:"+filename);
		renderJson(DwzJson.successAndClose("上传成功", "content", "content"));
	}
	
	/**
	 * 下载文件
	 */
	public void downloadUrl() {
		String fileid = getPara("fileid");
//		FFiles file = ContentService.getFileById(fileid);
//		String path = (PathKit.getWebRootPath() + "\\download\\").replace("\\", "/");
//		String fileName = file.getFilename();
//		File f = new File(path + fileName);
//		FileService fs = new FileService();
//		File i = new File(PathKit.getWebRootPath() + "\\upload\\" +file.getEncryptname());
////		File i =new File("/var/www/upload/"+file.getEncryptname());
//		fs.fileChannelCopy(i, f);
//		if (f.isFile()) {
//			renderFile(f);
//		}
		
	}
}

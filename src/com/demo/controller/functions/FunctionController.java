package com.demo.controller.functions;

import com.demo.controller.ControllerExt;
import com.demo.controller.functions.resultbean.PictureTextBean;
import com.jfinal.aop.Clear;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

@Clear
public class FunctionController extends ControllerExt{

	FunctionService fs = new FunctionService();
	
	
	public void pictext(){
		String image = getPara("image");
		if (StrKit.isBlank(image)) {
			renderAppError("参数错误");
		} else {
			PictureTextBean result = fs.getPictureText(image);
			if (result == null) {
				renderAppError("转换失败");
			} else if("1".equals(result.getCode())){
				Record r = new Record();
				r.set("text", result.getResult());
				renderAppJson(r);
			}else{
				renderAppError(result.getMsg());
			}
		}
	}
	
	
}

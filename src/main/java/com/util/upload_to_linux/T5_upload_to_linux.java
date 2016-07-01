package com.util.upload_to_linux;

import com.util.upload_to_linux.base.*;

public class T5_upload_to_linux {

	public static void main(String[] args) throws Exception {
		//配置
		Base.server = ".net";
		Base.findType = "class";// class | html
		
		/**    【移动文件 】   */
		 FindOperator.processFile((s) -> MoveOperate.doMoveClass(s));
		
		/**     【上传class】    */
//		FindOperator.processFile((s) -> UploadOperate.doUploadClass(s));
		 
		/**    【扫描需要上传的html】   */
//		FindOperator.processFile((s) -> UploadOperate.findUploadHtmlWriteToFile(s));
		 
		/** 	【上传html文件】		*/
//		UploadOperate.doUploadHtmlFileListToLinux();
		 
		
		/**		【下载jar】	*/
//		DownloadOperator.download(Base.linux_lib_Path, Base.dao_jar, Base.moveToDir);
//		DownloadOperator.download(Base.linux_lib_Path, Base.service_jar, Base.moveToDir);
		
		/**		【上传jar】 	*/
//		UploadOperate.uploadToLinux(Base.linux_lib_Path, Base.moveToDir + Base.dao_jar);
//		UploadOperate.uploadToLinux(Base.linux_lib_Path, Base.moveToDir + Base.service_jar);
//		UploadOperate.uploadWarToLinux(Base.linux_idongri_Path,  Base.local_platform_war_path);
	}

}

package com.lord.web.controller.ueditor.upload;

import com.lord.common.model.sys.SysFile;
import com.lord.web.config.SpringUtils;
import com.lord.web.controller.FileController;
import com.lord.web.controller.ueditor.PathFormat;
import com.lord.web.controller.ueditor.define.AppInfo;
import com.lord.web.controller.ueditor.define.BaseState;
import com.lord.web.controller.ueditor.define.FileType;
import com.lord.web.controller.ueditor.define.State;
import org.apache.commons.codec.binary.Base64;

import java.util.Map;

public final class Base64Uploader {

	public static State saveFile(String content, Map<String, Object> conf) {
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		FileController fileController = SpringUtils.getBean(FileController.class);
		SysFile sysFile = fileController.uploadOneImageByBase64(data);//上传文件
		if (sysFile != null) {
			State state = new BaseState(true);
			state.putInfo("title", sysFile.getName());
			state.putInfo("size", sysFile.getFileSize());
			state.putInfo("url", sysFile.getFilePath());
			state.putInfo("type", sysFile.getFileSuffix());
			state.putInfo("original", sysFile.getFilePath());
			return state;
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static State save(String content, Map<String, Object> conf) {
		
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));
		
		savePath = savePath + suffix;
		String physicalPath = (String) conf.get("rootPath") + savePath;
		System.out.println("上传文件路径： " + physicalPath);

		State storageState = StorageManager.saveBinaryFile(data, physicalPath);

		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}
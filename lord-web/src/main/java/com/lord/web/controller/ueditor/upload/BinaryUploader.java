package com.lord.web.controller.ueditor.upload;

import com.lord.common.model.sys.SysFile;
import com.lord.utils.Preconditions;
import com.lord.web.config.SpringUtils;
import com.lord.web.controller.FileController;
import com.lord.web.controller.ueditor.PathFormat;
import com.lord.web.controller.ueditor.define.AppInfo;
import com.lord.web.controller.ueditor.define.BaseState;
import com.lord.web.controller.ueditor.define.FileType;
import com.lord.web.controller.ueditor.define.State;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {

	/**
	 * 保存文件的方法
	 * @param request
	 * @param conf
	 * @return
	 */
	public static final State saveFile(HttpServletRequest request,
			Map<String, Object> conf) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("upfile");
		Preconditions.checkNotNull(files, "文件不能为空");
		Preconditions.checkArgument(files.isEmpty(), "文件不能为空!");
		MultipartFile file = files.get(0);
		FileController fileController = SpringUtils.getBean(FileController.class);
		SysFile sysFile = fileController.uploadOneFile(file);//上传文件
		if (sysFile != null) {
			State state = new BaseState(true);
			state.putInfo("title", sysFile.getName());
			state.putInfo("size", sysFile.getFileSize());
			state.putInfo("url", sysFile.getFilePath());
			state.putInfo("type", sysFile.getFileSuffix());
			state.putInfo("original", sysFile.getFilePath());
			state.putInfo("width", sysFile.getWidth());
			state.putInfo("height", sysFile.getHeight());
			return state;
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String physicalPath = (String) conf.get("rootPath") + savePath;

			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);
			is.close();

			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}

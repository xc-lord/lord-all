package com.lord.web.controller.ueditor.upload;

import com.lord.web.controller.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.saveFile(this.request.getParameter(filedName),
					this.conf);
		} else {
			state = BinaryUploader.saveFile(this.request, this.conf);
		}

		return state;
	}
}

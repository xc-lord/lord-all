package com.lord.web.controller.ueditor.hunter;

import com.lord.biz.service.AppConfig;
import com.lord.common.constant.FileType;
import com.lord.common.dto.Pager;
import com.lord.common.model.sys.SysFile;
import com.lord.common.service.sys.SysFileService;
import com.lord.web.config.SpringUtils;
import com.lord.web.controller.ueditor.PathFormat;
import com.lord.web.controller.ueditor.define.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
		this.dir = this.rootPath + (String)conf.get( "dir" );

		this.rootPath = AppConfig.uploadDir;
		this.dir = this.rootPath + (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
		
	}

	public State listFile ( int index, int actionCode) {
		MultiState state = new MultiState( true );
		Integer page = 1;
		Integer pageSize = this.count;
		if (index > 0) {
			page = index/pageSize;
		}
		SysFile param = new SysFile();
		if (ActionMap.LIST_IMAGE == actionCode) {
			param.setFileType(FileType.Image.toString());
		}

		SysFileService sysFileService = SpringUtils.getBean(SysFileService.class);
		Pager<SysFile> pager = sysFileService.pageSysFile(param, page, this.count);//查询数据库
		if (pager.getTotalRows() > 0) {
			state = new MultiState( true );
			for (SysFile sysFile : pager.getList()) {
				BaseState fileState = new BaseState(true);
				fileState.putInfo( "url", sysFile.getFilePath());
				state.addState( fileState );
			}
		}
		state.putInfo( "start", index );
		state.putInfo( "total", pager.getTotalRows());
		return state;
	}

	public State listFile ( int index ) {
		
		File dir = new File( this.dir );
		State state = null;

		if ( !dir.exists() ) {
			return new BaseState( false, AppInfo.NOT_EXIST );
		}
		
		if ( !dir.isDirectory() ) {
			return new BaseState( false, AppInfo.NOT_DIRECTORY );
		}
		
		Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
		
		if ( index < 0 || index > list.size() ) {
			state = new MultiState( true );
		} else {
			Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
			state = this.getState( fileList );
		}
		
		state.putInfo( "start", index );
		state.putInfo( "total", list.size() );
		
		return state;
		
	}
	
	private State getState ( Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			state.addState( fileState );
		}
		
		return state;
		
	}
	
	private String getPath ( File file ) {
		String path = file.getAbsolutePath();
		return path.replace("\\", "/").replace(this.rootPath, "");
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}
	
}

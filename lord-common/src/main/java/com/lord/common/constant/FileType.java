package com.lord.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：文件类型
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年06月26日 15:42
 */
public enum FileType implements BaseEnumType {
    Image("图片", "bmp,dib,gif,jfif,jpe,jpeg,jpg,png,tif,tiff,ico,psd,svg"),
    Audio("音频", "mp3,ogg,wav,ape,cda,au,midi,mac,acc"),
    ZipFile("压缩包", "rar,zip,cab,arj,7-zip,tar,gzip,jar,ios,z,uue,ace,lzh,bz2,bz"),
    Document("文档", "pptx,docx,xlsx,doc,wps,xls,ppt,txt,sql,htm,html,pdf,dwg"),
    Video("视频", "wmv,asf,asx,rm,rmvb,mpg,mpeg,mpe,3gp,mov,mp4,m4v,avi,dat,mkv,flv,vob,swf"),
    File("文件", ""),;

    private String name;

    private Map<String,String> suffixMap;

    /**
     * 文件类型
     * @param name          文件类型名
     * @param suffixStr     文件的后缀名字符串，多个用逗号间隔
     */
    FileType(String name, String suffixStr) {
        this.name = name;
        if(this.name == null || this.name.equals(""))
            return;
        String[] suffixArr = suffixStr.split(",");
        Map<String, String> map = new HashMap<>();
        for (String s : suffixArr) {
            map.put(s, s);
        }
        this.suffixMap = map;
    }

    public Map<String, String> getSuffixMap() {
        return suffixMap;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * 根据文件的后缀名获取文件的类型
     * @param suffixName    文件后缀名
     * @return
     */
    public static FileType getFileType(String suffixName) {
        if (suffixName == null || suffixName.trim().equals("")) {
            return File;
        }
        if (suffixName.startsWith(".")) {
            suffixName = suffixName.replace(".", "");
        }
        suffixName = suffixName.toLowerCase();
        for (FileType fileType : FileType.values()) {
            Map<String, String> map = fileType.getSuffixMap();
            if (map.get(suffixName) != null) {
                return fileType;
            }
        }
        return File;
    }
}

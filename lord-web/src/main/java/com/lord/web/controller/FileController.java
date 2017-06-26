package com.lord.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.lord.biz.service.AppConfig;
import com.lord.common.constant.FileType;
import com.lord.common.model.sys.SysFile;
import com.lord.common.service.sys.SysFileService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 功能：图片和文件公共的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月24日 17:13:30
 */
@Api(description = "图片和文件公共的Api")
@RestController
public class FileController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysFileService sysFileService;

    /**
     * 上传单个文件
     * @param file  文件
     */
    private void uploadOneFile(MultipartFile file) {
        if (file == null) {
            return;
        }
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            logger.info("上传的文件名为：" + fileName);
            logger.info("上传的文件大小为：" + fileSize);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            logger.info("上传的后缀名为：" + suffixName);

            SysFile sysFile = new SysFile();
            sysFile.setName(fileName);
            sysFile.setFileSize(fileSize);
            sysFile.setFileType(FileType.getFileType(suffixName).toString());//文件类型

            // 文件上传后的路径
            String filePath = AppConfig.uploadDir;
            // 解决中文问题，liunx下中文路径，图片显示问题
            // fileName = UUID.randomUUID() + suffixName;
            File dest = new File(filePath + "/" + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            IOUtils.copy(file.getInputStream(), new FileOutputStream(dest));
        } catch (Exception e) {
            logger.error("上传文件失败：" + e.getMessage(), e);
        }
    }

    @ApiOperation(value="上传文件", notes="上传单个文件")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form")
    @RequestMapping(value = "/api/uploadFile", method = RequestMethod.POST)
    public Result uploadFile(MultipartFile file) {
        Preconditions.checkNotNull(file, "文件不能为空");
        Preconditions.checkArgument(file.isEmpty(), "文件不能为空!");

        JSONObject jsonObject = new JSONObject();
        uploadOneFile(file);
        return Result.success(jsonObject);
    }

    @ApiOperation(value="多文件上传", notes="多文件上传")
    @RequestMapping(value = "/api/uploadFileBatch", method = RequestMethod.POST)
    public void uploadFileBatch(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        Preconditions.checkNotNull(files, "文件不能为空");
        Preconditions.checkArgument(files.isEmpty(), "文件不能为空!");
        for (MultipartFile file : files) {
            uploadOneFile(file);
        }
    }
}

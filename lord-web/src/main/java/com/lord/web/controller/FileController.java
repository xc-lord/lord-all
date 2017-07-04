package com.lord.web.controller;

import com.lord.biz.service.AppConfig;
import com.lord.common.constant.FileType;
import com.lord.common.model.sys.SysFile;
import com.lord.common.service.sys.SysFileService;
import com.lord.utils.CommonUtils;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
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
    public SysFile uploadOneFile(MultipartFile file) {
        if (file == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            logger.info("上传的文件名为：" + fileName);
            logger.info("上传的文件大小为：" + fileSize);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            logger.info("上传的后缀名为：" + suffixName);
            String fileType = FileType.getFileType(suffixName).toString();//文件类型

            String fileUrl = getFileUrl(fileType, CommonUtils.getUUID(), suffixName);

            byteArrayOutputStream = getByteArrayOutputStream(file);
            String md5 = DigestUtils.md5Hex(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));//md5值
            SysFile sysFile = sysFileService.getSysFileByMd5(md5);//根据Md5值查询文件
            if (sysFile != null) {
                logger.info("文件{}已经上传过了", fileName);
                return sysFile;
            }

            String physicalPath = getPhysicalPath(fileUrl);
            File dest = new File(physicalPath);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            fileOutputStream = new FileOutputStream(dest);
            IOUtils.copy(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), fileOutputStream);

            SysFile pageObj = new SysFile();
            pageObj.setName(fileName);//上传前的文件名
            pageObj.setFileSize(fileSize);//文件大小，单位byte
            pageObj.setFileType(fileType);//文件类型
            pageObj.setFilePath(fileUrl);//文件路径
            pageObj.setMdCode(md5);//md5值
            pageObj.setFileSuffix(suffixName.toLowerCase());//文件后缀名
            sysFileService.saveOrUpdate(pageObj);//保存到数据库
            logger.info("上传文件{}成功，保存路径{}", fileName, physicalPath);
            return pageObj;

        } catch (Exception e) {
            logger.error("上传文件失败：" + e.getMessage(), e);
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                logger.error("上传文件关闭流失败：" + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 文件保存的硬盘地址
     * @param fileUrl   文件相对路径
     * @return  物理地址
     */
    private String getPhysicalPath(String fileUrl) {
        return AppConfig.uploadDir + fileUrl;
    }

    /**
     * 文件保存的相对路径
     * @param fileType      文件类型
     * @param fileName      文件名
     * @param suffixName    文件后缀名
     * @return  相对地址
     */
    private String getFileUrl(String fileType, String fileName, String suffixName) {
        String day = CommonUtils.dateFormat(new Date(), "yyyy/MM/dd");
        return "/" + fileType.toLowerCase() + "/" + day + "/" + fileName + suffixName;
    }

    /**
     * 解决InputStream只能读取一次的问题，将InputStream转换成ByteArrayOutputStream缓存起来
     * @param file  上传文件
     * @return ByteArrayOutputStream缓存
     * @throws IOException
     */
    private ByteArrayOutputStream getByteArrayOutputStream(MultipartFile file) throws IOException {
        InputStream fileInputStream = file.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fileInputStream.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        return baos;
    }

    @ApiOperation(value="上传文件", notes="上传单个文件")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form")
    @RequestMapping(value = "/api/uploadFile", method = RequestMethod.POST)
    public Result uploadFile(MultipartFile file) {
        Preconditions.checkNotNull(file, "文件不能为空");
        Preconditions.checkArgument(file.isEmpty(), "文件不能为空!");
        SysFile sysFile = uploadOneFile(file);
        if (sysFile == null) {
            Result.failure("上传失败");
        }
        return Result.success("上传成功", sysFile);
    }

    @ApiOperation(value="多文件上传", notes="多文件上传")
    @RequestMapping(value = "/api/uploadFileBatch", method = RequestMethod.POST)
    public Result uploadFileBatch(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        Preconditions.checkNotNull(files, "文件不能为空");
        Preconditions.checkArgument(files.isEmpty(), "文件不能为空!");
        List<SysFile> sysFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            SysFile sysFile = uploadOneFile(file);
            if (sysFile != null) {
                sysFiles.add(sysFile);
            }
        }
        if (sysFiles.size() < 1) {
            Result.failure("上传失败");
        }
        return Result.success("上传成功", sysFiles);
    }

    @ApiOperation(value = "Base64版本的图片上传", notes = "Base64版本的图片上传")
    @RequestMapping(value = "/api/file/uploadImageByBase64", method = {RequestMethod.GET, RequestMethod.POST})
    public Result uploadImageByBase64(String upFile) {
        Preconditions.checkArgument(StringUtils.isEmpty(upFile), "文件不能为空!");
        byte[] data = Base64.decodeBase64(upFile);
        SysFile sysFile = uploadOneImageByBase64(data);
        if (sysFile == null) {
            Result.failure("上传失败");
        }
        return Result.success("上传成功", sysFile);
    }

    /**
     * 使用Base64方式的上传图片
     * @param data    图片
     * @return  文件
     */
    public SysFile uploadOneImageByBase64(byte[] data) {
        OutputStream output = null;
        try {
            String suffixName = ".jpg";
            String fileType = FileType.Image.toString();
            String fileName = CommonUtils.getUUID();
            String fileUrl = getFileUrl(fileType, fileName, suffixName);//相对路径
            String physicalPath = getPhysicalPath(fileUrl);//绝对路径

            String md5 = DigestUtils.md5Hex(data);//文件的Md5值
            SysFile sysFile = sysFileService.getSysFileByMd5(md5);//根据Md5值查询文件
            if (sysFile != null) {
                logger.warn("文件{}已经上传过了", sysFile.getName());
                return sysFile;
            }

            long fileSize = data.length;
            File dest = new File(physicalPath);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            output = new FileOutputStream(dest);
            IOUtils.write(data, output);//写入文件

            SysFile pageObj = new SysFile();
            pageObj.setName(fileName);//上传前的文件名
            pageObj.setFileSize(fileSize);//文件大小，单位byte
            pageObj.setFileType(fileType);//文件类型
            pageObj.setFilePath(fileUrl);//文件路径
            pageObj.setMdCode(md5);//md5值
            pageObj.setFileSuffix(suffixName.toLowerCase());//文件后缀名
            sysFileService.saveOrUpdate(pageObj);//保存到数据库
            logger.info("上传文件{}成功，保存路径{}", fileName, physicalPath);
            return pageObj;
        } catch (IOException e) {
            logger.error("Base64版本的图片上传失败：" + e.getMessage(), e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

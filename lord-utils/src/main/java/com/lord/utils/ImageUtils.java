package com.lord.utils;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.CropParameter;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleParameter.Algorithm;
import com.alibaba.simpleimage.render.WatermarkParameter;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 功能：图片压缩裁剪工具类，基于阿里巴巴的SimpleImage
 * Git地址https://github.com/alibaba/simpleimage
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年06月20日 17:54
 */
public class ImageUtils {

    protected static ImageFormat outputFormat = ImageFormat.JPEG;

    public static void main(String[] args) {
        //1.等比例缩放
        String srcFileName = "D:\\upload\\temp\\src.jpeg";
        scaleNormal(srcFileName, "D:\\upload\\temp\\scaleNormal.jpg", 400, 600);
        //2.等比例缩放加水印
        scaleWithWaterMark(srcFileName, "D:\\upload\\temp\\scaleWithWaterMark.jpg", 400, 600, "D:\\upload\\temp\\watermark.png");
        //3.缩放到指定宽度
        scaleWithWidth(srcFileName, "D:\\upload\\temp\\scaleWithWidth.jpg", 400);
        //4.缩放到指定高度
        scaleWithHeight(srcFileName, "D:\\upload\\temp\\scaleWithHeight.jpg", 600);
        //5.裁切成正方形
        cutSquare(srcFileName, "D:\\upload\\temp\\cut.jpg");
        //6.从中间裁切
        cutCenter(srcFileName, "D:\\upload\\temp\\cutCenter.jpg", 400, 600);

    }

    /**
     * 是否图片
     *
     * @param fileName  图片文件名
     * @return
     * @throws Exception
     */
    public static boolean isPicture(String fileName) throws Exception {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        String tmpName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        String[] imageArray = {"bmp","dib","gif","jfif","jpe","jpeg","jpg","png","tif","tiff","ico"};
        for (int i = 0; i < imageArray.length; i++) {
            if (imageArray[i].equals(tmpName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 等比例缩放 会裁切部分内容
     *
     * @param src           原文件
     * @param target        目标文件
     * @param width         宽
     * @param height        高
     */
    @SuppressWarnings("static-access")
    public final static void scaleNormal(String src, String target, int width, int height) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            // 1.缩放
            ScaleParameter scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS); // 缩放参数
            CropParameter cropParam = new CropParameter(0, 0, width, height);// 裁切参数
            float srcRatio = (float) w / h;
            float dstRatio = (float) width / height;// 源宽高比和目标宽高比
            if (w < width && h < height) {// 如果图片宽和高都小于目标图片则不做缩放处理
                scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS);
                cropParam = new CropParameter(0, 0, w, h);
            } else if (srcRatio >= dstRatio) {
                int newWidth = getWidth(w, h, height);
                scaleParam = new ScaleParameter(newWidth + 1, height, Algorithm.LANCZOS);
                cropParam = new CropParameter((newWidth - width) / 2, 0, width, height);

            } else if (srcRatio < dstRatio) {
                int newHeight = getHeight(w, h, width);
                scaleParam = new ScaleParameter(width, newHeight + 1, Algorithm.LANCZOS);
                cropParam = new CropParameter(0, (newHeight - height) / 2, width, height);
            }
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
            // 2.裁切
            imageWrapper = new ImageWrapper(planrImage);
            planrImage = ImageCropHelper.crop(planrImage, cropParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);

        }
    }

    /**
     * 压缩图片到 指定尺寸,图片比目标图片小则不会变形(有水印）
     *
     * @param src           原文件
     * @param target        目标文件
     * @param width         宽
     * @param height        高
     * @param waterMark     水印文件
     */
    public final static void scaleWithWaterMark(String src, String target, int width, int height, String waterMark) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            // 1.缩放
            ScaleParameter scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS); // 缩放参数
            CropParameter cropParam = new CropParameter(0, 0, width, height);// 裁切参数
            float srcRatio = (float) w / h;
            float dstRatio = (float) width / height;// 源宽高比和目标宽高比
            if (w < width && h < height) {// 如果图片宽和高都小于目标图片则不做缩放处理
                scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS);
                cropParam = new CropParameter(0, 0, w, h);
            } else if (srcRatio >= dstRatio) {
                int newWidth = getWidth(w, h, height);
                scaleParam = new ScaleParameter(newWidth + 1, height, Algorithm.LANCZOS);
                cropParam = new CropParameter((newWidth - width) / 2, 0, width, height);

            } else if (srcRatio < dstRatio) {
                int newHeight = getHeight(w, h, width);
                scaleParam = new ScaleParameter(width, newHeight + 1, Algorithm.LANCZOS);
                cropParam = new CropParameter(0, (newHeight - height) / 2, width, height);
            }
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
            // 2.裁切
            imageWrapper = new ImageWrapper(planrImage);
            planrImage = ImageCropHelper.crop(planrImage, cropParam);
            // 3.打水印
            BufferedImage waterImage = ImageIO.read(new File(waterMark));
            ImageWrapper waterWrapper = new ImageWrapper(waterImage);
            WatermarkParameter param = new WatermarkParameter(waterWrapper, 1, 0, 0);
            imageWrapper = new ImageWrapper(planrImage);
            BufferedImage bufferedImage = ImageDrawHelper.drawWatermark(imageWrapper.getAsBufferedImage(), param);
            imageWrapper = new ImageWrapper(bufferedImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
        }

    }

    /**
     * 缩放到指定宽度 高度自适应
     *
     * @param src           原文件
     * @param target        目标文件
     * @param width         宽
     */
    @SuppressWarnings("static-access")
    public final static void scaleWithWidth(String src, String target, Integer width) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            // 1.缩放
            ScaleParameter scaleParam = null; // 缩放参数
            if (w < width) {// 如果图片宽和高都小于目标图片则不做缩放处理
                scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS);
            } else {
                int newHeight = getHeight(w, h, width);
                scaleParam = new ScaleParameter(width, newHeight + 1, Algorithm.LANCZOS);
            }
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);

        }

    }

    /**
     * 缩放到指定高度，宽度自适应
     *
     * @param src           原文件
     * @param target        目标文件
     * @param height        高
     */
    public final static void scaleWithHeight(String src, String target, Integer height) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            // 1.缩放
            ScaleParameter scaleParam = null; // 缩放参数
            if (w < height) {// 如果图片宽和高都小于目标图片则不做缩放处理
                scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS);

            } else {
                int newWidth = getWidth(w, h, height);
                scaleParam = new ScaleParameter(newWidth + 1, height, Algorithm.LANCZOS);

            }
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);

        }

    }

    /**
     * 根据宽、高和目标宽度 等比例求高度
     *
     * @param w     宽
     * @param h     高
     * @param width 目标宽度
     * @return  目标高度
     */
    public static Integer getHeight(Integer w, Integer h, Integer width) {

        return h * width / w;
    }

    /**
     * 根据宽、高和目标高度 等比例求宽度
     *
     * @param w         宽
     * @param h         高
     * @param height    目标高度
     * @return  目标宽度
     */
    public static Integer getWidth(Integer w, Integer h, Integer height) {
        return w * height / h;
    }

    /**
     * 从中间裁切需要的大小
     *
     * @param src           原文件
     * @param target        目标文件
     * @param width         宽
     * @param height        高
     */
    @SuppressWarnings("static-access")
    public final static void cutCenter(String src, String target, Integer width, Integer height) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();

            int x = (w - width) / 2;

            int y = (h - height) / 2;

            CropParameter cropParam = new CropParameter(x, y, width, height);// 裁切参数
            if (x < 0 || y < 0) {
                cropParam = new CropParameter(0, 0, w, h);// 裁切参数

            }

            PlanarImage planrImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
        }
    }

    /**
     * 按最小的宽或高，裁切为正文形
     *
     * @param src           原文件
     * @param target        目标文件
     */
    public final static void cutSquare(String src, String target) {
        File out = new File(target); // 目的图片
        FileOutputStream outStream = null;
        File in = new File(src); // 原图片
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();
            int width = 0;
            int height = 0;

            if (w >= h) {
                width = h;
                height = h;
            } else {
                width = w;
                height = w;
            }
            CropParameter cropParam = new CropParameter(0, 0, width, height);// 裁切参数
            PlanarImage planrImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
            imageWrapper = new ImageWrapper(planrImage);
            // 4.输出
            outStream = new FileOutputStream(out);
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleImageException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
        }
    }
}

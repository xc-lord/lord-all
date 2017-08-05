package com.lord.biz.utils;

import com.lord.utils.CommonUtils;
import com.lord.utils.dto.Code;
import com.lord.utils.exception.CommonException;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXValidator;
import org.dom4j.util.XMLErrorHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Iterator;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月05日 11:28
 */
public class AdsTemplateUtils
{
    /** 模板类型：模板  */
    public final static String T_MODEL = "模板";
    /** 模板类型：图片链接  */
    public final static String T_IMG = "图片链接";
    /** 模板类型：文字链接 */
    public final static String T_LINK = "文字链接";
    /** 模板类型：大文本  */
    public final static String T_BIG_TEXT = "大文本";
    /** 模板类型：文章  */
    public final static String T_ARTICLE = "文章";

    /**
     * 根据XSD文件验证XML是否正确，使用Dom4j的API
     * @param xsdPath   xsd验证文件路径
     * @param xml       xml内容
     * @return	正确true,错误false
     */
    public static boolean validateXml(final String xsdPath, String xml) {
        try {
            // 获取要校验xml文档实例
            // 创建一个读取工具
            Document xmlDocument = DocumentHelper.parseText(xml);
            // 创建默认的XML错误处理器
            XMLErrorHandler errorHandler = new XMLErrorHandler();
            // 获取基于 SAX 的解析器的实例
            SAXParserFactory factory = SAXParserFactory.newInstance();
            // 解析器在解析时验证 XML 内容。
            factory.setValidating(true);
            // 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            // 设置 XMLReader 的基础实现中的特定属性。核心功能和属性列表可以在
            // [url]http://sax.sourceforge.net/?selected=get-set[/url] 中找到。
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", "file:" + xsdPath);
            // 创建一个SAXValidator校验工具，并设置校验工具的属性
            SAXValidator validator = new SAXValidator(parser.getXMLReader());
            // 设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
            validator.setErrorHandler(errorHandler);

            // 校验
            validator.validate(xmlDocument);

            // 如果错误信息不为空，说明校验失败，打印错误信息
            if (errorHandler.getErrors().hasContent())
            {
                String msg = "XML文件通过XSD文件校验失败！原因：\n" + errorHandler.getErrors().getStringValue();
                //将错误信息document中的内容写入文件中
                System.err.println(msg);
                throw new CommonException(Code.ErrorFormat, msg);
            }
            return true;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据XSD文件验证XML是否正确，使用Dom4j的API
     * @param xml   xml内容
     * @return	正确true,错误false
     */
    public static boolean validateXml(String xml) {
        String xsdPath = CommonUtils.getClassPathFilePath("page_template.xsd");
        return validateXml(xsdPath, xml);
    }

    /**
     * 打印XML结构的数据
     * @param xml
     */
    public static void printXmlDate(String xml) {
        Document doc;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            System.err.println("xml格式有问题，转换成Document对象失败：" + e.getMessage());
            e.printStackTrace();
            return;
        }
        Element root = doc.getRootElement();
        Element keywordElm = root.element("keyword");

        String name = getElmValue(root, "name");
        String keyword = "/" + keywordElm.getText();

        System.out.println(name + "配置说明");
        System.out.println();
        System.out.println("1.根模板的配置说明：");
        System.out.println("根模板名称：" + name);
        System.out.println("根模板关键字为：" + keyword);
        System.out.println("根模板类型：模板");
        System.out.println();

        System.out.println("2.子模板的配置说明：");
        Element childrenElm = root.element("children");
        printChildrenElm(name, keyword, childrenElm, "\t", "2");
    }

    /**
     * 打印模板的子节点
     * @param parentName	父节点的名称
     * @param parentKeyword	父节点的关键字
     * @param childrenElm	所有子节点
     * @param blank			空白
     * @param serialNo		序号
     */
    @SuppressWarnings("unchecked")
    private static void printChildrenElm(String parentName, String parentKeyword, Element childrenElm, String blank, String serialNo) {
        blank = "";
        if(childrenElm == null) {
            return;
        }
        int index = 1;
        Iterator<Element> childrens = childrenElm.elementIterator("template");
        while(childrens.hasNext()) {
            final String thisSerialNo = serialNo + "." + index;
            Element children = childrens.next();
            String keyword = children.element("keyword").getTextTrim();
            String name = children.element("name").getTextTrim();
            String type = children.element("type").getTextTrim();

            String choose = getElmValue(children, "choose");
            boolean needChoose = false;
            if(choose != null) {
                needChoose = Boolean.parseBoolean(choose);
            }

            String defaultstr = getElmValue(children, "default");

            Element countElm = children.element("count");
            String count = "1";
            if(countElm != null) {
                count = children.element("count").getTextTrim();//数量可选
            }

            int templatenum = 1;
            Element templatenumElm = children.element("templatenum");
            String keyDesc = null;
            String thisKeyword = parentKeyword + "/" + keyword;
            if(templatenumElm != null) {
                templatenum = Integer.parseInt(templatenumElm.getTextTrim());
                keyDesc = "[注意：此模板的关键字必须以" + keyword + "开头，如：" + keyword + "_1、" + keyword + "_2、" + keyword + "_3，序号从1开始]";
                thisKeyword += "_1";
				/*for (int i = 1; i <= templatenum; i++) {
					System.out.println(blank + "keyword=" + keyword + "_" + i + "，name=" + name + "，type=" + type + "，count=" + count);
				}*/
            }

            System.out.println(blank + thisSerialNo + "、" + name + "的配置说明：");
            System.out.println(blank + "在父模板-" + parentName + "(" + parentKeyword + ")下创建" + templatenum + "个" + name +"的模板");
            System.out.println(blank + "模板名称：" + name + "[可自定义]");
            System.out.println(blank + "模板关键字：" + keyword);
            if(keyDesc != null) {
                System.out.println(blank + keyDesc);
            }
            if(needChoose) {
                System.out.println(blank + "模板的生成路径：【需要配置选择的显示类型】");
            }
            System.out.println(blank + "模板类型：" + type);
            if(!T_MODEL.equals(type)) {
                System.out.println(blank + "添加模板的内容为：" + count + "个" + type);
            }
            if(defaultstr != null) {
                boolean isdefault = Boolean.parseBoolean(defaultstr);
                String desc = "需要修改父模板的基础信息，将生成路径修改为:" + keyword.substring(0, 2);
                if(isdefault) {
                    System.out.println(blank + "默认在页面上，会显示此项");
                    System.out.println(blank + "[如果显示的是其他模板，需切换回来，则" + desc + "]");
                } else {
                    System.out.println(blank + "要在页面显示此项，" + desc);
                }
            }
            System.out.println();

            Element childrenElm2 = children.element("children");
            printChildrenElm(name, thisKeyword, childrenElm2, blank + "\t", thisSerialNo);
            index++;
        }
    }

    /**
     * 获得XML节点的值
     * @param parentElm
     * @param key
     * @return
     */
    public static String getElmValue(Element parentElm, String key) {
        Element element = parentElm.element(key);
        if(element != null) {
            return element.getTextTrim();
        }
        return null;
    }

    /**
     * 获取必要的值
     *
     * @param root
     * @param key
     * @return
     */
    public static String getNeedElmValue(Element root, String key) {
        Element keywordElm = root.element(key);
        if (keywordElm == null) {
            String msg = "xml格式有问题，" + key + "结点不存在";
            throw new CommonException(Code.ErrorFormat, msg);
        }
        String keyword = keywordElm.getText();
        if (StringUtils.isEmpty(keyword)) {
            String msg = "xml格式有问题，" + key + "结点的值为空";
            throw new CommonException(Code.ErrorFormat, msg);
        }
        return keyword;
    }

    public static Integer getElmValueInteger(Element templateElm, String key)
    {
        String str = getElmValue(templateElm, key);
        if(StringUtils.isEmpty(str))
            return null;
        return Integer.parseInt(str);
    }

    public static Boolean getElmValueBoolean(Element templateElm, String key)
    {
        String str = getElmValue(templateElm, key);
        if(StringUtils.isEmpty(str))
            return null;
        return Boolean.parseBoolean(str);
    }
}

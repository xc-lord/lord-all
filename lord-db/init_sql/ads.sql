CREATE TABLE `ads_space` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `keyword` varchar(200) NOT NULL COMMENT '关键字',
  `sub_keyword` varchar(100) NOT NULL COMMENT '子关键字',
  `ads_type` varchar(100) NOT NULL COMMENT '图片、文字、商品、文章',
  `ads_num` int(11) DEFAULT '0' COMMENT '元素的数量，0不限制数量',
  `show_state` bit(1) DEFAULT b'1' COMMENT '显示1、隐藏0',
  `space_img` varchar(300) DEFAULT NULL COMMENT '图片',
  `space_url` varchar(300) DEFAULT NULL COMMENT '链接',
  `level` int(11) DEFAULT '0' COMMENT '等级',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点Id',
  `page_id` bigint(20) NOT NULL COMMENT '页面id',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `intro` varchar(200) DEFAULT NULL COMMENT '介绍',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `order_value` bigint(20) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_keyword` (`keyword`)
) ENGINE=InnoDB AUTO_INCREMENT=1061 DEFAULT CHARSET=utf8 COMMENT='广告位';


CREATE TABLE `ads_element` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(300) DEFAULT NULL COMMENT '名称',
  `title` varchar(300) DEFAULT NULL COMMENT '标题',
  `sub_title` varchar(300) DEFAULT NULL COMMENT '子标题',
  `ads_img` varchar(300) DEFAULT NULL COMMENT '图片地址',
  `ads_url` varchar(300) DEFAULT NULL COMMENT '跳转的链接',
  `tags` varchar(300) DEFAULT NULL COMMENT '标签',
  `extension` varchar(300) DEFAULT NULL COMMENT '扩展属性',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `order_value` bigint(20) DEFAULT '1' COMMENT '排序值',
  `keyword` varchar(200) NOT NULL COMMENT '关键字',
  `target_id` varchar(100) DEFAULT NULL COMMENT '元素id',
  `target_type` varchar(100) NOT NULL COMMENT '元素类型',
  `page_id` bigint(20) NOT NULL COMMENT '页面id',
  `space_id` bigint(20) NOT NULL COMMENT '广告位Id',
  `tex` text COMMENT '大文本',
  `fouce` bit(1) DEFAULT b'0' COMMENT '是否高亮',
  `target` bit(1) DEFAULT b'1' COMMENT '是否新窗口打开',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1915 DEFAULT CHARSET=utf8mb4 COMMENT='广告位的元素';





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


CREATE TABLE `mis_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(40) NOT NULL COMMENT '名称',
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `letter` char(100) DEFAULT NULL COMMENT '拼音',
  `icon` varchar(40) DEFAULT NULL COMMENT '菜单图标',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点Id',
  `parent_name` varchar(40) DEFAULT NULL COMMENT '父节点名称',
  `parent_ids` varchar(500) DEFAULT NULL COMMENT '所有父节点Id',
  `leaf` bit(1) DEFAULT NULL COMMENT '是否叶子节点',
  `children_ids` varchar(500) DEFAULT NULL COMMENT '所有子节点Id',
  `order_value` bigint(20) DEFAULT NULL COMMENT '排序',
  `url` varchar(40) DEFAULT NULL COMMENT '菜单链接',
  `server_url` varchar(100) DEFAULT NULL COMMENT '服务端地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单';



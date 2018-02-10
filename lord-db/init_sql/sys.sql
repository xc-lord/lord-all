/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/4/4 18:00:09                            */
/*==============================================================*/


drop table if exists sys_config;

drop table if exists sys_dict;

drop table if exists sys_dict_group;

/*==============================================================*/
/* Table: sys_config                                            */
/*==============================================================*/
create table sys_config
(
   id                   bigint not null AUTO_INCREMENT comment '主键Id',
   name                 varchar(40) not null comment '配置名称',
   config_key           varchar(100) not null comment '配置key',
   config_value         varchar(500) comment '配置value',
   order_value          bigint comment '排序',
   parent_id            bigint comment '父配置',
   level                bit comment '等级',
   primary key (id)
);

alter table sys_config comment '系统配置表';

/*==============================================================*/
/* Table: sys_dict                                              */
/*==============================================================*/
create table sys_dict
(
   id                   bigint not null AUTO_INCREMENT comment '主键Id',
   name                 varchar(40) comment '名称',
   dict_key             varchar(100) not null comment '字典key',
   dict_value           varchar(200) comment '字典value',
   group_id             bigint not null comment '分组Id',
   dict_code            varchar(100) not null comment '分组code',
   order_value          bigint comment '排序',
   primary key (id)
);

alter table sys_dict comment '系统数据字典表';

/*==============================================================*/
/* Table: sys_dict_group                                        */
/*==============================================================*/
create table sys_dict_group
(
   id                   bigint not null AUTO_INCREMENT comment '主键Id',
   name                 varchar(40) not null comment '名称',
   dict_code            varchar(100) not null comment '分组code',
   order_value          bigint comment '排序',
   primary key (id)
);

alter table sys_dict_group comment '系统数据字典分组表';


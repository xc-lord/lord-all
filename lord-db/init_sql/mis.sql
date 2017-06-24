/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/4/7 9:49:59                             */
/*==============================================================*/


drop table if exists mis_menu;

drop table if exists mis_menu_right;

drop table if exists mis_role;

drop table if exists mis_role_menu;

drop table if exists mis_user;

drop table if exists mis_user_log;

/*==============================================================*/
/* Table: mis_menu                                              */
/*==============================================================*/
create table mis_menu
(
   id                   bigint not null comment '主键Id',
   name                 varchar(40) not null comment '菜单名称',
   url                  varchar(40) comment '菜单链接',
   icon                 varchar(40) comment '菜单图标',
   lever                int comment '菜单等级',
   order_value          bigint comment '排序',
   parent_id            bigint comment '父菜单',
   primary key (id)
);

alter table mis_menu comment '后台菜单信息';

/*==============================================================*/
/* Table: mis_menu_right                                        */
/*==============================================================*/
create table mis_menu_right
(
   id                   bigint not null comment '主键Id',
   menu_id              varchar(40) comment '菜单Id',
   right_code           varchar(40) comment '权限code',
   right_name           varchar(40) comment '权限名称',
   primary key (id)
);

alter table mis_menu_right comment '后台菜单的具体权限';

/*==============================================================*/
/* Table: mis_role                                              */
/*==============================================================*/
create table mis_role
(
   id                   bigint not null comment '主键Id',
   name                 varchar(40) not null comment '名称',
   intro                varchar(40) comment '说明',
   add_user_id          bigint comment '添加用户Id',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table mis_role comment '用户角色';

/*==============================================================*/
/* Table: mis_role_menu                                         */
/*==============================================================*/
create table mis_role_menu
(
   id                   bigint not null comment '主键Id',
   role_id              varchar(40) comment '角色Id',
   menu_id              varchar(40) comment '菜单Id',
   all_right            bit default 1 comment '是否有所有权限',
   right_code           varchar(200) comment '权限code',
   primary key (id)
);

alter table mis_role_menu comment '角色菜单权限';

/*==============================================================*/
/* Table: mis_user                                              */
/*==============================================================*/
create table mis_user
(
   id                   bigint not null comment '主键Id',
   username             varchar(40) not null comment '用户名',
   password             varchar(40) not null comment '密码',
   phone                varchar(20) comment '手机',
   status               varchar(40) comment '用户状态',
   nick_name            varchar(40) comment '昵称',
   real_name            varchar(40) comment '真实姓名',
   email                varchar(50) comment '邮箱',
   icon                 varchar(100) comment '头像',
   role_name            varchar(40) comment '用户角色',
   role_id              bigint comment '用户角色Id',
   add_user_id          bigint comment '添加用户Id',
   login_time           datetime comment '登录时间',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   removed              bit default 0 comment '是否删除',
   sex                  bit default 1 comment '性别：1男，2女',
   super_admin          bit not null default 0 comment '是否超级管理员',
   primary key (id)
);

alter table mis_user comment '后台管理员信息';

/*==============================================================*/
/* Table: mis_user_log                                          */
/*==============================================================*/
create table mis_user_log
(
   id                   bigint not null comment '主键Id',
   mis_user_id          bigint not null comment '用户Id',
   user_role            varchar(40) comment '用户角色',
   real_name            varchar(40) comment '用户真实名称',
   menu_id              varchar(40) comment '菜单Id',
   all_right            bit default 1 comment '是否有所有权限',
   log                  varchar(600) comment '权限code',
   target_id            bigint comment '针对的对象Id',
   target_type          varchar(40) comment '针对的对象类型',
   create_time          datetime not null comment '创建时间',
   primary key (id)
);

alter table mis_user_log comment '用户后台操作日志';
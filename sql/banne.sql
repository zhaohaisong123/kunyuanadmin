CREATE TABLE `baojia_banner`
(
    `banner_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `banner_name` varchar(255) DEFAULT '' COMMENT '名称',
    `banner_img`  varchar(255) DEFAULT '' COMMENT '图片',
    `link_url`    varchar(255) DEFAULT '' COMMENT '跳转地址',
    `banner_type`      char(1)      DEFAULT '0' COMMENT '类型 (0=主页, 1=主页广告页, 2=个人中心广告页)',
    `status`      char(1)      DEFAULT '0' COMMENT '状态 (0=Normal, 1=Disabled)',
    `create_by`   varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)  DEFAULT '' COMMENT '更新着',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`banner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

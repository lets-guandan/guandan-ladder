CREATE TABLE `user`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `uid`          varchar(32)  NOT NULL COMMENT '唯一标识',
    `username`     varchar(20)  NOT NULL COMMENT '用户名',
    `password`     varchar(255) NOT NULL COMMENT '密码',
    `nickname`     varchar(50)  NOT NULL COMMENT '昵称',
    `profile`      varchar(100) NOT NULL COMMENT '个人简介',
    `avatar_url`   varchar(500) NOT NULL COMMENT '头像url',
    `created_time` datetime     NOT NULL COMMENT '创建时间',
    `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `game_record`
(
    `id`                     int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `win_uid1`               varchar(32) NOT NULL COMMENT '胜者1',
    `win_uid2`               varchar(32) NOT NULL COMMENT '胜者2',
    `lose_uid1`              varchar(32) NOT NULL COMMENT '败者1',
    `lose_uid2`              varchar(32) NOT NULL COMMENT '败者2',
    `user_confirm_flag_bits` tinyint(4) NOT NULL DEFAULT '0' COMMENT '确认标记位，0b1111, 表示四位全部确认',
    `counted`                tinyint(1) NOT NULL DEFAULT '0' COMMENT '已统计到用户战绩',
    `game_time`              datetime    NOT NULL COMMENT '比赛时间',
    `created_time`           datetime    NOT NULL COMMENT '创建时间',
    `updated_time`           datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='比赛记录表';

CREATE TABLE `user_game_info`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uid`          varchar(32) NOT NULL COMMENT '用户id',
    `win_num`      int(11) NOT NULL DEFAULT '0' COMMENT '获胜局数',
    `total_num`    int(11) NOT NULL COMMENT '参与局数',
    `created_time` datetime    NOT NULL COMMENT '创建时间',
    `updated_time` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户战绩';



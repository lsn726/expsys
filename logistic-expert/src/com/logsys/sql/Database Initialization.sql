CREATE DATABASE `explogistic`;

CREATE TABLE `demand` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pn` char(20) NOT NULL COMMENT '物料号',
  `qty` double NOT NULL COMMENT '需求数量',
  `date` date NOT NULL COMMENT '需求日期',
  `dlvfix` int(5) DEFAULT '0' COMMENT '发货日期修正',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Date-Pn-Unique` (`pn`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=2729 DEFAULT CHARSET=utf8 COMMENT='需求表';

CREATE TABLE `demand_backup` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `version` datetime NOT NULL COMMENT '版本',
  `pn` char(20) NOT NULL COMMENT '物料号',
  `qty` double NOT NULL COMMENT '需求数量',
  `date` date NOT NULL COMMENT '需求日期',
  `dlvfix` int(5) DEFAULT '0' COMMENT '发货日期修正',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求备份表';

CREATE TABLE `model` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client` char(20) NOT NULL COMMENT '客户名',
  `model` char(20) NOT NULL COMMENT '型号',
  `prjcode` char(10) NOT NULL COMMENT '项目代号',
  `info` char(30) NOT NULL COMMENT '型号信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='型号表';

CREATE TABLE `material` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pn` char(30) NOT NULL COMMENT '物料号',
  `plant` char(10) NOT NULL COMMENT '工厂',
  `description` char(50) NOT NULL COMMENT '物料描述',
  `type` char(10) NOT NULL COMMENT '物料类型',
  `uom` char(10) NOT NULL COMMENT '计量单位',
  `price` double NOT NULL COMMENT '价格',
  `currency` char(10) NOT NULL COMMENT '价格货币',
  `qtyprice` int(10) NOT NULL COMMENT '价格计件数量',
  `provider` char(30) DEFAULT NULL COMMENT '供应商',
  `makebuy` char(30) DEFAULT NULL COMMENT 'MakeBuy',
  `buyer` char(30) DEFAULT NULL COMMENT '采购人',
  `inuse` tinyint(10) DEFAULT '0' COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物料表';

CREATE TABLE `conversion` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `origin` char(10) NOT NULL COMMENT '原单位',
  `target` char(10) NOT NULL COMMENT '目标单位',
  `factor` double NOT NULL COMMENT '1 origin=factor * target',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='换算表';

CREATE TABLE `bom` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lvl` int(3) NOT NULL COMMENT '层级',
  `pn` char(30) NOT NULL COMMENT '组件号',
  `qty` double NOT NULL COMMENT '消耗量',
  `unit` char(10) NOT NULL COMMENT '消耗单位',
  `version` date NOT NULL COMMENT 'BOM版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BOM';

CREATE TABLE `bom_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lvl` int(3) NOT NULL COMMENT '层级',
  `pn` char(30) NOT NULL COMMENT '组件号',
  `qty` double NOT NULL COMMENT '消耗量',
  `unit` char(10) NOT NULL COMMENT '消耗单位',
  `version` date NOT NULL COMMENT 'BOM版本',
  `validto`	date NOT NULL COMMENT '有效期至',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='BOM Backup';
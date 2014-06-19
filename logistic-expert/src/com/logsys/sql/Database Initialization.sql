CREATE DATABASE `explogistic`;

CREATE TABLE `demand` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pn` char(20) NOT NULL COMMENT '物料号',
  `qty` double NOT NULL COMMENT '需求数量',
  `date` date NOT NULL COMMENT '需求日期',
  `dlvfix` int(5) DEFAULT '0' COMMENT '发货日期修正',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Date-Pn-Unique` (`pn`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='需求表';

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
  `pq` int(6) NOT NULL COMMENT '包装数量',
  `mrpfactor` double(5,2) NOT NULL COMMENT 'MRP因数，为百分数，范围：0.0%-999.99%',
  `mrpround` int(6) NOT NULL COMMENT 'mrp取整值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='型号表'

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
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='BOM Backup';

CREATE TABLE `prodplan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `prdline` char(30) NOT NULL COMMENT '生产线',
  `pn` char(30) NOT NULL COMMENT '生产型号',
  `qty` double NOT NULL COMMENT '生产数量',
  `date` date NOT NULL COMMENT '生产日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_PLAN` (`prdline`,`date`,`pn`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='生产计划表'

CREATE TABLE `prodplan_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `prdline` char(30) NOT NULL COMMENT '生产线',
  `pn` char(30) NOT NULL COMMENT '生产型号',
  `qty` double NOT NULL COMMENT '生产数量',
  `date` date NOT NULL COMMENT '生产日期',
  `version` datetime NOT NULL COMMENT '计划版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='生产计划表'

CREATE TABLE `production` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `workcenter` char(50) NOT NULL COMMENT '工作中心',
  `date` date NOT NULL COMMENT '产出日期',
  `output` char(20) NOT NULL COMMENT '产出产品',
  `operqty` int(5) NOT NULL COMMENT '操作工数量',
  `qty` double NOT NULL COMMENT '产出数量',
  `tfbegin` timestamp NOT NULL COMMENT 'Time Frame产出时间开始',
  `tfend` timestamp NOT NULL COMMENT 'Time Frame产出时间结束',
  `effmin` int NOT NULL COMMENT '有效生产时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='生产表'

CREATE TABLE `cycletime` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `workcenter` char(50) NOT NULL COMMENT '工作中心',
  `output` char(30) NOT NULL COMMENT '产出产品',
  `operqty` int(5) NOT NULL COMMENT '操作工数量',
  `cycletime` double NOT NULL COMMENT '周期时间',
  `effbegin` date NOT NULL COMMENT '有效时间-起始时间',
  `effend` date NOT NULL COMMENT '有效时间-结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Design Cycle Time'

CREATE TABLE `matoperdoc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物料号码',
  `pn` char(30) COMMENT '物料号',
  `sloc` char(5) COMMENT '存储位置',
  `mvtype` char(5) NOT NULL COMMENT '物料移动类型',
  `docnum` char(15) NOT NULL COMMENT '操作文档号码',
  `postdate` date NOT NULL COMMENT '账目日期',
  `qty` double COMMENT '操作数量',
  `uom` char(5) COMMENT '计量单位',
  `plant` char(5) NOT NULL COMMENT '操作工厂',
  `ordernum` char(15) COMMENT '订单号码',
  `customer` char(10) COMMENT '客户号',
  `vendor` char(10) COMMENT '供应商号',
  `header` char(40) COMMENT '操作记录表头',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物料操作记录表'

CREATE TABLE `stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pn` char(30) NOT NULL COMMENT '物料号',
  `plant` char(10) NOT NULL COMMENT '工厂',
  `sloc` char(5) NOT NULL COMMENT '存储位置',
  `wkcenter` char(50) NOT NULL COMMENT '工作中心',
  `qtyuns` double NOT NULL COMMENT '非限制区数量',
  `uom` char(10) NOT NULL COMMENT '计量单位',
  `qtyqi` double NOT NULL COMMENT 'Quality Inspection Quantity',
  `qtyblk` double NOT NULL COMMENT 'Block Quantity',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `storloc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pn` char(30) DEFAULT NULL COMMENT '料号',
  `col` char(10) DEFAULT NULL COMMENT '列',
  `cell` char(10) DEFAULT NULL COMMENT '单元',
  `floor` char(10) DEFAULT NULL COMMENT '层',
  `bin` char(10) DEFAULT NULL COMMENT '格',
  `sloc` char(20) DEFAULT NULL COMMENT '库房',
  `pq` int(10) DEFAULT NULL COMMENT '包装数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=899 DEFAULT CHARSET=utf8 COMMENT='库位表'
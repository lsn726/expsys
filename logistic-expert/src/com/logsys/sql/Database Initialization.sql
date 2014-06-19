CREATE DATABASE `explogistic`;

CREATE TABLE `demand` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '����',
  `pn` char(20) NOT NULL COMMENT '���Ϻ�',
  `qty` double NOT NULL COMMENT '��������',
  `date` date NOT NULL COMMENT '��������',
  `dlvfix` int(5) DEFAULT '0' COMMENT '������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Date-Pn-Unique` (`pn`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='�����';

CREATE TABLE `demand_backup` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '����',
  `version` datetime NOT NULL COMMENT '�汾',
  `pn` char(20) NOT NULL COMMENT '���Ϻ�',
  `qty` double NOT NULL COMMENT '��������',
  `date` date NOT NULL COMMENT '��������',
  `dlvfix` int(5) DEFAULT '0' COMMENT '������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='���󱸷ݱ�';

CREATE TABLE `model` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `client` char(20) NOT NULL COMMENT '�ͻ���',
  `model` char(20) NOT NULL COMMENT '�ͺ�',
  `prjcode` char(10) NOT NULL COMMENT '��Ŀ����',
  `info` char(30) NOT NULL COMMENT '�ͺ���Ϣ',
  `pq` int(6) NOT NULL COMMENT '��װ����',
  `mrpfactor` double(5,2) NOT NULL COMMENT 'MRP������Ϊ�ٷ�������Χ��0.0%-999.99%',
  `mrpround` int(6) NOT NULL COMMENT 'mrpȡ��ֵ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='�ͺű�'

CREATE TABLE `material` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����',
  `pn` char(30) NOT NULL COMMENT '���Ϻ�',
  `plant` char(10) NOT NULL COMMENT '����',
  `description` char(50) NOT NULL COMMENT '��������',
  `type` char(10) NOT NULL COMMENT '��������',
  `uom` char(10) NOT NULL COMMENT '������λ',
  `price` double NOT NULL COMMENT '�۸�',
  `currency` char(10) NOT NULL COMMENT '�۸����',
  `qtyprice` int(10) NOT NULL COMMENT '�۸�Ƽ�����',
  `provider` char(30) DEFAULT NULL COMMENT '��Ӧ��',
  `makebuy` char(30) DEFAULT NULL COMMENT 'MakeBuy',
  `buyer` char(30) DEFAULT NULL COMMENT '�ɹ���',
  `inuse` tinyint(10) DEFAULT '0' COMMENT '�Ƿ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='���ϱ�';

CREATE TABLE `conversion` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `origin` char(10) NOT NULL COMMENT 'ԭ��λ',
  `target` char(10) NOT NULL COMMENT 'Ŀ�굥λ',
  `factor` double NOT NULL COMMENT '1 origin=factor * target',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='�����';

CREATE TABLE `bom` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `lvl` int(3) NOT NULL COMMENT '�㼶',
  `pn` char(30) NOT NULL COMMENT '�����',
  `qty` double NOT NULL COMMENT '������',
  `unit` char(10) NOT NULL COMMENT '���ĵ�λ',
  `version` date NOT NULL COMMENT 'BOM�汾',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BOM';

CREATE TABLE `bom_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `lvl` int(3) NOT NULL COMMENT '�㼶',
  `pn` char(30) NOT NULL COMMENT '�����',
  `qty` double NOT NULL COMMENT '������',
  `unit` char(10) NOT NULL COMMENT '���ĵ�λ',
  `version` date NOT NULL COMMENT 'BOM�汾',
  `validto`	date NOT NULL COMMENT '��Ч����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='BOM Backup';

CREATE TABLE `prodplan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `prdline` char(30) NOT NULL COMMENT '������',
  `pn` char(30) NOT NULL COMMENT '�����ͺ�',
  `qty` double NOT NULL COMMENT '��������',
  `date` date NOT NULL COMMENT '��������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_PLAN` (`prdline`,`date`,`pn`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='�����ƻ���'

CREATE TABLE `prodplan_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `prdline` char(30) NOT NULL COMMENT '������',
  `pn` char(30) NOT NULL COMMENT '�����ͺ�',
  `qty` double NOT NULL COMMENT '��������',
  `date` date NOT NULL COMMENT '��������',
  `version` datetime NOT NULL COMMENT '�ƻ��汾',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�����ƻ���'

CREATE TABLE `production` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `workcenter` char(50) NOT NULL COMMENT '��������',
  `date` date NOT NULL COMMENT '��������',
  `output` char(20) NOT NULL COMMENT '������Ʒ',
  `operqty` int(5) NOT NULL COMMENT '����������',
  `qty` double NOT NULL COMMENT '��������',
  `tfbegin` timestamp NOT NULL COMMENT 'Time Frame����ʱ�俪ʼ',
  `tfend` timestamp NOT NULL COMMENT 'Time Frame����ʱ�����',
  `effmin` int NOT NULL COMMENT '��Ч����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������'

CREATE TABLE `cycletime` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '��������',
  `workcenter` char(50) NOT NULL COMMENT '��������',
  `output` char(30) NOT NULL COMMENT '������Ʒ',
  `operqty` int(5) NOT NULL COMMENT '����������',
  `cycletime` double NOT NULL COMMENT '����ʱ��',
  `effbegin` date NOT NULL COMMENT '��Чʱ��-��ʼʱ��',
  `effend` date NOT NULL COMMENT '��Чʱ��-����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Design Cycle Time'

CREATE TABLE `matoperdoc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '���Ϻ���',
  `pn` char(30) COMMENT '���Ϻ�',
  `sloc` char(5) COMMENT '�洢λ��',
  `mvtype` char(5) NOT NULL COMMENT '�����ƶ�����',
  `docnum` char(15) NOT NULL COMMENT '�����ĵ�����',
  `postdate` date NOT NULL COMMENT '��Ŀ����',
  `qty` double COMMENT '��������',
  `uom` char(5) COMMENT '������λ',
  `plant` char(5) NOT NULL COMMENT '��������',
  `ordernum` char(15) COMMENT '��������',
  `customer` char(10) COMMENT '�ͻ���',
  `vendor` char(10) COMMENT '��Ӧ�̺�',
  `header` char(40) COMMENT '������¼��ͷ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='���ϲ�����¼��'

CREATE TABLE `stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `pn` char(30) NOT NULL COMMENT '���Ϻ�',
  `plant` char(10) NOT NULL COMMENT '����',
  `sloc` char(5) NOT NULL COMMENT '�洢λ��',
  `wkcenter` char(50) NOT NULL COMMENT '��������',
  `qtyuns` double NOT NULL COMMENT '������������',
  `uom` char(10) NOT NULL COMMENT '������λ',
  `qtyqi` double NOT NULL COMMENT 'Quality Inspection Quantity',
  `qtyblk` double NOT NULL COMMENT 'Block Quantity',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `storloc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pn` char(30) DEFAULT NULL COMMENT '�Ϻ�',
  `col` char(10) DEFAULT NULL COMMENT '��',
  `cell` char(10) DEFAULT NULL COMMENT '��Ԫ',
  `floor` char(10) DEFAULT NULL COMMENT '��',
  `bin` char(10) DEFAULT NULL COMMENT '��',
  `sloc` char(20) DEFAULT NULL COMMENT '�ⷿ',
  `pq` int(10) DEFAULT NULL COMMENT '��װ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=899 DEFAULT CHARSET=utf8 COMMENT='��λ��'
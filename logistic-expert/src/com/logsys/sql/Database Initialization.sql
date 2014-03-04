CREATE DATABASE `explogistic`;

CREATE TABLE `demand` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '����',
  `pn` char(20) NOT NULL COMMENT '���Ϻ�',
  `qty` double NOT NULL COMMENT '��������',
  `date` date NOT NULL COMMENT '��������',
  `dlvfix` int(5) DEFAULT '0' COMMENT '������������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Date-Pn-Unique` (`pn`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=2729 DEFAULT CHARSET=utf8 COMMENT='�����';

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ͺű�'
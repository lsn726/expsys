package com.logsys.hibernate;

import org.hibernate.dialect.MySQLInnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.DateType;

/**
 * MySql时间日期Dialect
 * @author lx8sn6
 */
public class MySqlDateTimeDialect extends MySQLInnoDBDialect {

	public MySqlDateTimeDialect() {
		super();
		registerFunction("date_add", new SQLFunctionTemplate(DateType.INSTANCE, "date_add(?1, INTERVAL ?2 ?3)"));
	}
	
}

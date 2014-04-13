package com.logsys.util;

import java.util.Date;

/**
 * 日期区间类，包含两个对象：开始时间/结束时间
 * @author lx8sn6
 */
public class DateInterval {
	
	public Date begindate;
	
	public Date enddate;

	public DateInterval(Date begindate, Date enddate) {
		super();
		this.begindate = begindate;
		this.enddate = enddate;
	}
	
	public DateInterval() {}
	
}

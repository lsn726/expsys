package com.logsys.util;

import java.util.Date;

/**
 * ���������࣬�����������󣺿�ʼʱ��/����ʱ��
 * @author lx8sn6
 */
public class DateInterval {
	
	public Date begindate;
	
	public Date enddate;

	public DateInterval(Date begindate, Date enddate) {
		this.begindate = begindate;
		this.enddate = enddate;
	}
	
	public DateInterval(long begindate, long enddate) {
		this.begindate = new Date(begindate);
		this.enddate = new Date(enddate);
	}
	
	public DateInterval() {}

	@Override
	public String toString() {
		return "DateInterval [begindate=" + begindate + ", enddate=" + enddate
				+ "]";
	}
	
}

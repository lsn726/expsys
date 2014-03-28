package com.logsys.util;

/**
 * 位置对象，包含两个值 ：column列/row行
 * @author lx8sn6
 */
public class Location {

	//行变量
	public int column;
	
	//列变量
	public int row;
	
	public Location(int row,int column) {
		this.column=column;
		this.row=row;
	}
	
}

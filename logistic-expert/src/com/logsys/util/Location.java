package com.logsys.util;

/**
 * 位置对象，包含两个值 ：column列/row行
 * @author lx8sn6
 */
public class Location {

	/**用于计算hashCode的质数因素*/
	private static final int PRIME_FACTOR=10007;
	
	//行变量
	public int column;
	
	//列变量
	public int row;
	
	public Location(int row,int column) {
		this.column=column;
		this.row=row;
	}
	
	public Location() {}
	
	public int hashCode() {
		int result=1;
		result=result*PRIME_FACTOR+column;
		result=result*PRIME_FACTOR+row;
		return result;
	}
	
	public boolean equals(Object obj) {
		if(this.hashCode()==obj.hashCode()) return true;
		else return false;
	}

	@Override
	public String toString() {
		return "Location [column=" + column + ", row=" + row + "]";
	}
	
}

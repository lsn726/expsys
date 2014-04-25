package com.logsys.util;

/**
 * λ�ö��󣬰�������ֵ ��column��/row��
 * @author lx8sn6
 */
public class Location {

	//�б���
	public int column;
	
	//�б���
	public int row;
	
	public Location(int row,int column) {
		this.column=column;
		this.row=row;
	}
	
	public Location() {}
	
	public int hashCode() {
		final int prime=31;
		int result=1;
		result=result*prime+column;
		result=result*prime+row;
		return result;
	}
	
	public boolean equals(Object obj) {
		if(this.hashCode()==obj.hashCode()) return true;
		else return false;
	}
	
}

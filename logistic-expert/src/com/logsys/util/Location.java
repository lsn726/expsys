package com.logsys.util;

/**
 * λ�ö��󣬰�������ֵ ��column��/row��
 * @author lx8sn6
 */
public class Location {

	/**���ڼ���hashCode����������*/
	private static final int PRIME_FACTOR=10007;
	
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

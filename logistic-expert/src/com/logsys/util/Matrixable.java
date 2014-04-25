package com.logsys.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �ɾ��󻯵����ݵ�Ԫ
 * @author lx8sn6
 *
 * @param <DataType> ��������
 */
public class Matrixable<DataType> {

	/**�б�ͷ*/
	private List<String> rowHeader;
	
	/**�б�ͷ*/
	private List<String> colHeader;
	
	/***/
	private Map<Location,DataType> datamap;
	
	public Matrixable() {
		datamap=new HashMap<Location,DataType>();
	}

	public List<String> getRowHeader() {
		return rowHeader;
	}

	public void setRowHeader(List<String> rowHeader) {
		this.rowHeader = rowHeader;
	}

	public List<String> getColHeader() {
		return colHeader;
	}

	public void setColHeader(List<String> colHeader) {
		this.colHeader = colHeader;
	}
	
	/**
	 * ��row/colλ�õ���������Ϊdata,row/col����ظ��򸲸�
	 * @param row ����
	 * @param col ����
	 * @param data ����
	 */
	public void setData(int row,int col,DataType data) {
		datamap.put(new Location(row,col), data);
	}
	
}

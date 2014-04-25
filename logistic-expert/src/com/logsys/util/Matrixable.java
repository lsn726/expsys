package com.logsys.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 可矩阵化的数据单元
 * @author lx8sn6
 *
 * @param <DataType> 数据类型
 */
public class Matrixable<DataType> {

	/**行表头*/
	private List<String> rowHeader;
	
	/**列表头*/
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
	 * 将row/col位置的数据设置为data,row/col如果重复则覆盖
	 * @param row 行数
	 * @param col 列数
	 * @param data 数据
	 */
	public void setData(int row,int col,DataType data) {
		datamap.put(new Location(row,col), data);
	}
	
}

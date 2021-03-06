package com.logsys.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * 可矩阵化的数据单元
 * @author lx8sn6
 */
public class Matrixable {
	
	private static final Logger logger=Logger.getLogger(Matrixable.class);

	/**行表头:行位置<->内容 双向一一映射图*/
	private BiMap<Integer,String> rowHeader;
	
	/**列表头:列位置->内容 双向一一映射图*/
	private BiMap<Integer,String> colHeader;
	
	/**位置->数据 映射图*/
	private Map<Location, Object> datamap;
	
	public Matrixable() {
		datamap=new HashMap<Location, Object>();
		rowHeader=HashBiMap.create();
		colHeader=HashBiMap.create();
	}
	
	/**
	 * 内容是否存在于行表头中
	 * @param content 行表头
	 * @return 存在true/不存在false
	 */
	public boolean isContentInRowHeader(String content) {
		return rowHeader.containsValue(content);
	}
	
	/**
	 * 内容是否存在于列表头中
	 * @param content 列表头
	 * @return 存在true/不存在false
	 */
	public boolean isContentInColHeader(String content) {
		return colHeader.containsValue(content);
	}
	
	/**
	 * 设置行表头的内容,表头内容不能重复
	 * @param position 行表头位置
	 * @param content 内容
	 * @return 成功true/失败false
	 */
	public boolean putRowHeaderCell(int position, String content) {
		if(rowHeader.containsValue(content)) {
			logger.error("不能写入行表头数据，内容["+content+"]已存在。");
			return false;
		}
		rowHeader.put(position, content);
		return true;
	}

	/**
	 * 设置列表头的内容,表头内容不能重复
	 * @param position 列表头位置
	 * @param content 内容
	 * @return 成功true/失败false
	 */
	public boolean putColHeaderCell(int position, String content) {
		if(colHeader.containsValue(content)) {
			logger.error("不能写入列表头数据，内容["+content+"]已存在。");
			return false;
		}
		colHeader.put(position, content);
		return true;
	}
	
	/**获取行表头数量*/
	public int getRowHeaderSize() {
		return rowHeader.size();
	}
	
	/**获取列表头数量*/
	public int getColHeaderSize() {
		return colHeader.size();
	}
	
	/**
	 * 由行表头内容获取这个内容在行向量中的位置
	 * @param rowcont 行内容
	 * @return 行位置
	 */
	public Integer getRowPosByRowHeader(String rowcont) {
		BiMap<String, Integer> inversemap=rowHeader.inverse();
		return inversemap.get(rowcont);
	}
	
	/**
	 * 由列表头内容获取这个内容在列向量中的位置
	 * @param colcont 列内容
	 * @return 列位置
	 */
	public Integer getColPosByColHeader(String colcont) {
		BiMap<String, Integer> inversemap=colHeader.inverse();
		return inversemap.get(colcont);
	}

	/**
	 * 将row/col位置的数据设置为data,如果该文职已存在数据则覆盖
	 * @param row 行数
	 * @param col 列数
	 * @param data 数据
	 */
	public void setData(int row,int col,Object data) {
		Location keyloc=new Location(row,col);
		if(datamap.containsKey(keyloc))
			logger.warn("数据矩阵中已经存在位置对象["+keyloc+"],原有数据将被覆盖。");
		datamap.put(keyloc, data);
	}
	
	/**
	 * 清空所在行
	 * @param delrow 需要清空的行数
	 * @return 清空成功true/清空失败false
	 */
	public boolean cleanRow(int delrow) {
		if(delrow<0) {
			logger.error("不能清空矩阵对象所在行["+delrow+"], 行数不能为负值。");
			return false;
		}
		int counter=0;
		Set<Location> removeloc=new HashSet<Location>();
		for(Location loc:datamap.keySet())	//遍历数据地图，删除指定所在行的数据
			if(loc.row==delrow)
				removeloc.add(loc);
		for(Location loc:removeloc) {
			datamap.remove(loc);
			counter++;
		}
		logger.info("已经从矩阵对象中删除指定行["+delrow+"]的数据共计["+counter+"]条。");
		return true;
	}
	
	/**
	 * 清空所在列
	 * @param delcol 需要清空的列数
	 * @return 清空成功true/清空失败false
	 */
	public boolean cleanCol(int delcol) {
		if(delcol<0) {
			logger.error("不能清空矩阵对象所在列["+delcol+"], 列数不能为负值。");
			return false;
		}
		int counter=0;
		Set<Location> removeloc=new HashSet<Location>();
		for(Location loc:datamap.keySet())	//遍历数据地图，删除指定所在行的数据
			if(loc.column==delcol)
				removeloc.add(loc);
		for(Location loc:removeloc) {
			datamap.remove(loc);
			counter++;
		}
		logger.info("已经从矩阵对象中删除指定列["+delcol+"]的数据共计["+counter+"]条。");
		return true;
	}
	
	/**
	 * 将行表头为rowheader，列表头为colheader的数据设置为data,如果数据已存在则覆盖
	 * @param rowheader 行表头
	 * @param colheader 列表头
	 * @param data 数据
	 * @return true成功写入/null没有相应的行或者列表头，无法定位
	 */
	public boolean setData(String rowheader,String colheader, Object data) {
		Integer rowindex=getRowPosByRowHeader(rowheader);
		if(rowindex==null) {
			logger.error("无法设置矩阵数据["+data+"],因为行表头["+rowheader+"]无法在行表头图中定位.另外，对应的列表头为["+colheader+"].");
			return false;
		}
		Integer colindex=getColPosByColHeader(colheader);
		if(colindex==null) {
			logger.error("无法设置矩阵数据["+data+"],因为列表头["+colheader+"]无法在列表头图中定位.另外，对应的行表头为["+rowheader+"].");
			return false;
		}
		Location keyloc=new Location(rowindex,colindex);
		if(datamap.containsKey(keyloc))
			logger.warn("数据矩阵中已经存在位置对象["+keyloc+"],原有数据将被覆盖。");
		datamap.put(keyloc, data);
		return true;
	}
	
	/**
	 * 获取指定行/列的矩阵数据
	 * @param row 指定行
	 * @param col 指定列
	 * @return 指定位置数据/null
	 */
	public Object getData(int row,int col) {
		return datamap.get(new Location(row,col));
	}
	
	/**
	 * 将这个Matrixable所代表的的矩阵数据写入Excel表格
	 * @param sheet Excel的Sheet对象
	 * @param beginloc 开始位置
	 */
	public void writeToExcelSheet(Sheet sheet,Location beginloc) {
		if(sheet==null||beginloc==null) {
			logger.error("不能将矩阵对象写入数据库，参数为空.");
			return;
		}
		for(Integer rowdiff:rowHeader.keySet())	{				//写入行表头
			Row row=sheet.createRow(beginloc.row+rowdiff+1);	//先创建列
			Cell cell=row.createCell(beginloc.column);			//创建单元格
			cell.setCellValue(rowHeader.get(rowdiff));			//写入行表头
		}
		Row colHeaderRow=sheet.createRow(beginloc.row);			//创建表头行对象
		for(Integer coldiff:colHeader.keySet())	{				//写入列表头
			colHeaderRow.createCell(beginloc.column+coldiff).setCellValue(colHeader.get(coldiff));
		}
		Cell cell;
		for(Location locdiff:datamap.keySet())	{				//写入数据内容
			cell=sheet.getRow(beginloc.row+locdiff.row+1).createCell(beginloc.column+locdiff.column);
			if(datamap.get(locdiff) instanceof Integer)	{		//如果是整数，将单元格格式设置为整数
				cell.setCellValue((Integer)datamap.get(locdiff));
			} else if(datamap.get(locdiff) instanceof Double) {
				cell.setCellValue((Double)datamap.get(locdiff));
			} else {
				logger.error("不能写入Excel表格，此Matrixable的数据类型尚未被支持。");
				return;
			}
		}
	}
	
}

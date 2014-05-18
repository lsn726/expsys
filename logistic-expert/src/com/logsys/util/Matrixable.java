package com.logsys.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * �ɾ��󻯵����ݵ�Ԫ
 * @author lx8sn6
 */
public class Matrixable {
	
	private static final Logger logger=Logger.getLogger(Matrixable.class);

	/**�б�ͷ:��λ��<->���� ˫��һһӳ��ͼ*/
	private BiMap<Integer,String> rowHeader;
	
	/**�б�ͷ:��λ��->���� ˫��һһӳ��ͼ*/
	private BiMap<Integer,String> colHeader;
	
	/**λ��->���� ӳ��ͼ*/
	private Map<Location, Object> datamap;
	
	public Matrixable() {
		datamap=new HashMap<Location, Object>();
		rowHeader=HashBiMap.create();
		colHeader=HashBiMap.create();
	}
	
	/**
	 * �����б�ͷ������
	 * @param position �б�ͷλ��
	 * @param content ����
	 */
	public void putRowHeaderCell(int position, String content) {
		rowHeader.put(position, content);
	}

	/**
	 * �����б�ͷ������
	 * @param position �б�ͷλ��
	 * @param content ����
	 */
	public void putColHeaderCell(int position, String content) {
		colHeader.put(position, content);
	}
	
	/**
	 * ���б�ͷ���ݻ�ȡ����������������е�λ��
	 * @param rowcont ������
	 * @return ��λ��
	 */
	public Integer getRowPosByRowHeader(String rowcont) {
		BiMap<String, Integer> inversemap=rowHeader.inverse();
		return inversemap.get(rowcont);
	}
	
	/**
	 * ���б�ͷ���ݻ�ȡ����������������е�λ��
	 * @param colcont ������
	 * @return ��λ��
	 */
	public Integer getColPosByColHeader(String colcont) {
		BiMap<String, Integer> inversemap=colHeader.inverse();
		return inversemap.get(colcont);
	}

	/**
	 * ��row/colλ�õ���������Ϊdata,�������ְ�Ѵ��������򸲸�
	 * @param row ����
	 * @param col ����
	 * @param data ����
	 */
	public void setData(int row,int col,Object data) {
		Location keyloc=new Location(row,col);
		if(datamap.containsKey(keyloc))
			logger.warn("���ݾ������Ѿ�����λ�ö���["+keyloc+"],ԭ�����ݽ������ǡ�");
		datamap.put(keyloc, data);
	}
	
	/**
	 * ���б�ͷΪrowheader���б�ͷΪcolheader����������Ϊdata,��������Ѵ����򸲸�
	 * @param rowheader �б�ͷ
	 * @param colheader �б�ͷ
	 * @param data ����
	 * @return true�ɹ�д��/nullû����Ӧ���л����б�ͷ���޷���λ
	 */
	public boolean setData(String rowheader,String colheader, Object data) {
		Integer rowindex=getRowPosByRowHeader(rowheader);
		if(rowindex==null) {
			logger.error("�޷����þ�������["+data+"],��Ϊ�б�ͷ+["+rowheader+"]�޷����б�ͷͼ�ж�λ.");
			return false;
		}
		Integer colindex=getColPosByColHeader(colheader);
		if(colindex==null) {
			logger.error("�޷����þ�������["+data+"],��Ϊ�б�ͷ["+colheader+"]�޷����б�ͷͼ�ж�λ.");
			return false;
		}
		Location keyloc=new Location(rowindex,colindex);
		if(datamap.containsKey(keyloc))
			logger.warn("���ݾ������Ѿ�����λ�ö���["+keyloc+"],ԭ�����ݽ������ǡ�");
		datamap.put(keyloc, data);
		return true;
	}
	
	/**
	 * ��ȡָ����/�еľ�������
	 * @param row ָ����
	 * @param col ָ����
	 * @return ָ��λ������/null
	 */
	public Object getData(int row,int col) {
		return datamap.get(new Location(row,col));
	}
	
	/**
	 * �����Matrixable������ĵľ�������д��Excel���
	 * @param sheet Excel��Sheet����
	 * @param beginloc ��ʼλ��
	 */
	public void writeToExcelSheet(Sheet sheet,Location beginloc) {
		if(sheet==null||beginloc==null) {
			logger.error("���ܽ��������д�����ݿ⣬����Ϊ��.");
			return;
		}
		for(Integer rowdiff:rowHeader.keySet())	{				//д���б�ͷ
			Row row=sheet.createRow(beginloc.row+rowdiff);		//�ȴ�����
			Cell cell=row.createCell(beginloc.column);			//������Ԫ��
			cell.setCellValue(rowHeader.get(rowdiff));			//д���б�ͷ
		}
		Row colHeaderRow=sheet.createRow(beginloc.row);			//������ͷ�ж���
		for(Integer coldiff:colHeader.keySet())	{				//д���б�ͷ
			colHeaderRow.createCell(beginloc.column+coldiff).setCellValue(colHeader.get(coldiff));
		}
		Cell cell;
		for(Location locdiff:datamap.keySet())	{				//д����������
			cell=sheet.getRow(beginloc.row+locdiff.row).createCell(beginloc.column+locdiff.column);
			if(datamap.get(locdiff) instanceof Integer)	{		//���������������Ԫ���ʽ����Ϊ����
				cell.setCellValue((Integer)datamap.get(locdiff));
			} else if(datamap.get(locdiff) instanceof Double) {
				cell.setCellValue((Double)datamap.get(locdiff));
			} else {
				logger.error("����д��Excel��񣬴�Matrixable������������δ��֧�֡�");
				return;
			}
		}
	}
	
}

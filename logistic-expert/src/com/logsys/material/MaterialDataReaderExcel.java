package com.logsys.material;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * ������Ϣ��ȡ��,��Excel�����ȡ������
 * @author lx8sn6
 */
public class MaterialDataReaderExcel {

	private static Logger logger=Logger.getLogger(MaterialDataReaderExcel.class);
	
	/**������*/
	private static final int ROW_HEADER=3;
	
	/**��ʼ��*/
	private static final int ROW_BEGIN=5;
	
	/**pn��*/
	private static final int COL_PN=1;
	
	/**������*/
	private static final int COL_PLANT=2;
	
	/**������*/
	private static final int COL_DESC=4;
	
	/**����������*/
	private static final int COL_TYPE=6;
	
	/**������λ��*/
	private static final int COL_UOM=8;
	
	/**�۸���*/
	private static final int COL_PRICE=14;
	
	/**������*/
	private static final int COL_CURRENCY=15;
	
	/**�Ƽ�������*/
	private static final int COL_PRICEQTY=16;
	
	/**
	 * ��ָ�����ļ�·����ȡ���ϻ�����Ϣ����
	 * @param filepath ָ�����ļ�·��
	 * @return �ɹ�������Ϣ�б�<pn,MaterialContent>/ʧ��null
	 */
	public static Map<String,MaterialContent> getMatDataFromExcel_MM60(String filepath) {
		if(filepath==null) {
			logger.error("����Ϊ��.");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("�ļ�������.");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("�ļ���Ŀ¼.");
			return null;
		}
		Workbook datasrc;
		InputStream readstream;
		Sheet sheet;
		try {
			readstream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(readstream);
			sheet=datasrc.getSheetAt(0);
			if(!isExcelContentValid(datasrc)) {
				logger.error("���������ļ���ʽ����");
				return null;
			}
		} catch(Throwable e) {
			logger.error("Excel�ļ���ȡ����.Ҳ�����ļ�����Excel�ļ���");
			return null;
		}
		Map<String,MaterialContent> matlist=new HashMap<String,MaterialContent>();
		MaterialContent mcontent;
		for(Row row:sheet) {
			if(row.getRowNum()<ROW_BEGIN) continue;
			mcontent=new MaterialContent();
			row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
			mcontent.setPn(row.getCell(COL_PN).getStringCellValue());
			mcontent.setPlant(row.getCell(COL_PLANT).getStringCellValue());
			mcontent.setDescription(row.getCell(COL_DESC).getStringCellValue());
			mcontent.setType(row.getCell(COL_TYPE).getStringCellValue());
			mcontent.setUom(row.getCell(COL_UOM).getStringCellValue());
			mcontent.setPrice(row.getCell(COL_PRICE).getNumericCellValue());
			mcontent.setCurrency(row.getCell(COL_CURRENCY).getStringCellValue());
			mcontent.setQtyprice((int)row.getCell(COL_PRICEQTY).getNumericCellValue());
			matlist.put(mcontent.getPn(),mcontent);
		}
		return matlist;
	}
	
	/**
	 * Excel�ļ���֤��,ͨ����ͷ�����ݸ�ʽ��֤�ļ��Ƿ���ȷ
	 * @param wb Excel��Workbook����
	 * @return ��֤ͨ��true/��֤ͨ��false
	 */
	private static boolean isExcelContentValid(Workbook wb) {
		if(wb==null) {
			logger.error("����Ϊ��.");
			return false;
		}
		Sheet sheet=wb.getSheetAt(0);
		Row header=sheet.getRow(ROW_HEADER);
		if(header.getCell(COL_PN).getStringCellValue().equals("Material"))
			if(header.getCell(COL_PLANT).getStringCellValue().equals("Plnt"))
				if(header.getCell(COL_DESC).getStringCellValue().equals("Material Description"))
					if(header.getCell(COL_TYPE).getStringCellValue().equals("MTyp"))
						if(header.getCell(COL_UOM).getStringCellValue().equals("BUn"))
							if(header.getCell(COL_PRICE).getStringCellValue().equals("      Price"))
								if(header.getCell(COL_CURRENCY).getStringCellValue().equals("Crcy"))
									if(header.getCell(COL_PRICEQTY).getStringCellValue().equals("   per"))
										return true;
		return false;
	}

	
	
}
package com.logsys.matoperdoc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.util.Location;

/**
 * SAP���������ϲ����ĵ�Excel���ݶ�ȡ��--MB51�ĵ������ݶ�ȡ��
 * @author lx8sn6
 */
public class MatOperDocContentSAPExcelReaderMB51 {

	private static final Logger logger=Logger.getLogger(MatOperDocContentSAPExcelReaderMB51.class);
	
	/**Excel����֤ͼ��λ��->�ַ���*/
	private static final Map<Location,String> excelValidatorMap=new HashMap<Location,String>();
	
	/**Excel���е����ڸ�ʽת����*/
	//private static final DateFormat dateformat=new SimpleDateFormat("yyyy.MM.dd");
	
	/**��ʽ��¼��ʼ����*/
	private static final int ROW_BEGIN=3;
	
	/**PN��*/
	private static final int COL_PN=1;
	
	/**Storage Location��*/
	private static final int COL_SLOC=1;
	
	/**Plant��*/
	private static final int COL_PLANT=10;
	
	/**��������*/
	private static final int COL_ORDER=10;
	
	/**�ĵ�����*/
	private static final int COL_DOCNUM=4;
	
	/**�ƶ�������*/
	private static final int COL_MVMTYPE=2;
	
	/**Posting Date��*/
	private static final int COL_PSTDATE=7;
	
	/**����������*/
	private static final int COL_QTY=8;
	
	/**������λ��*/
	private static final int COL_UOM=9;
	
	/**�ͻ���*/
	private static final int COL_CUSTOMER=12;
	
	/**��Ӧ����*/
	private static final int COL_VENDOR=13;
	
	/**��¼ͷ����*/
	private static final int COL_HEADER=14;
	
	static {
		//��ʼ����֤�ַ���
		excelValidatorMap.put(new Location(1,1), "Material");
		excelValidatorMap.put(new Location(1,5), "Material Description");
		excelValidatorMap.put(new Location(1,COL_PLANT), "Plnt");
		excelValidatorMap.put(new Location(1,11), "Name 1");
		excelValidatorMap.put(new Location(2,COL_PN), "SLoc");
		excelValidatorMap.put(new Location(2,COL_MVMTYPE), "MvT");
		excelValidatorMap.put(new Location(2,COL_DOCNUM), "Mat. Doc.");
		excelValidatorMap.put(new Location(2,COL_PSTDATE), "Pstng Date");
		excelValidatorMap.put(new Location(2,COL_QTY), "         Quantity");
		excelValidatorMap.put(new Location(2,COL_UOM), "BUn");
		excelValidatorMap.put(new Location(2,COL_ORDER), "Order");
		excelValidatorMap.put(new Location(2,COL_CUSTOMER), "Customer");
		excelValidatorMap.put(new Location(2,COL_VENDOR), "Vendor");
		excelValidatorMap.put(new Location(2,COL_HEADER), "Document Header Text");
	}
	
	/**
	 * ��SAP�е�����Excel������¼�ĵ����ݶ�ȡ��,�������ݵ�sheetλ��λ��0
	 * @param filepath �ļ�·��
	 * @return ���ϲ����б�/nullʧ��
	 */
	public static List<MatOperDocContent> readDataFromSAPExcel(String filepath) {
		Workbook wb;
		InputStream readstream;
		try {
			readstream=new FileInputStream(filepath);
			wb=WorkbookFactory.create(readstream);
		} catch(Throwable ex) {
			logger.error("���ܴ��ļ�·����ȡExcel�ļ���",ex);
			return null;
		}
		Sheet sheet;
		String matpn=null;	//��������pn
		String plant=null;	//����
		List<MatOperDocContent> doclist;	//�ĵ��б����
		MatOperDocContent doctemp;
		int lastrow=0;		//��¼��һ������
		try {
			sheet=wb.getSheetAt(0);
			if(!validateSheet(sheet)) return null;
			doclist=new ArrayList<MatOperDocContent>();
			for(Row row:sheet) {			//��ʼ����sheet��
				//System.out.println("���ڴ����"+row.getRowNum()+"��");
				if(row.getRowNum()<ROW_BEGIN) continue;		//�������С����ʼ�У������
				if(row.getRowNum()-lastrow>1) {				//���ǰһ�γ������У���˵��ǰһ�����ϲ�����¼�������µ����ϲ�����¼��ʼ
					if(row.getCell(COL_PLANT)!=null) {		//���к�����й������£�����¹���
						row.getCell(COL_PLANT).setCellType(Cell.CELL_TYPE_STRING);
						plant=row.getCell(COL_PLANT).getStringCellValue();
					} else
						plant=null;
					if(row.getCell(COL_PN)!=null) {			//���к������PN���£������PN
						row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
						matpn=row.getCell(COL_PN).getStringCellValue();
					} else
						matpn=null;
					lastrow=row.getRowNum();
					continue;								//���к�ĵ�һ��Ϊ���Ϻ͹�����Ϣ���������
				}
				if(row.getCell(COL_MVMTYPE)!=null) {		//���MVMCell��Ϊnull������Ҫ������һ�е�matpn��plant������ȡ����
					doctemp=extractMatOperDoc(row);
					if(doctemp==null) return null;			//��ȡʧ���򷵻�null;
					doctemp.setPn(matpn);
					doctemp.setPlant(plant);
					doclist.add(doctemp);
					lastrow=row.getRowNum();
					continue;
				}
				logger.error("���ܼ�����Excel����ȡ������¼�ĵ����ݣ���û�г������е�����£�������MVMTypeΪ�յ������λ����["+row.getRowNum()+"]");
				return null;
			}
			return doclist;
		} catch(Throwable ex) {
			logger.error("��ȡSAP����ȡ��Excel������¼�ĵ����ݳ��ִ���:",ex);
			return null;
		}
		
	}
	
	/**
	 * ��Excel������ȡContent��Ϣ
	 * @param row �ж���
	 * @param pn ��������
	 * @return ��ȡ�Ķ���/null
	 */
	private static MatOperDocContent extractMatOperDoc(Row row) {
		if(row==null) {
			logger.error("���ܴ��ж�������ȡ���ϲ�����¼���ж���Ϊ�ա�");
			return null;
		}
		try {
			MatOperDocContent operdoc=new MatOperDocContent();
			if(row.getCell(COL_SLOC)!=null) {
				row.getCell(COL_SLOC).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setSloc(row.getCell(COL_SLOC).getStringCellValue());
			}
			row.getCell(COL_MVMTYPE).setCellType(Cell.CELL_TYPE_STRING);
			operdoc.setMvtype(row.getCell(COL_MVMTYPE).getStringCellValue());
			row.getCell(COL_DOCNUM).setCellType(Cell.CELL_TYPE_STRING);
			operdoc.setDocnum(row.getCell(COL_DOCNUM).getStringCellValue());
			//operdoc.setPostdate(dateformat.parse(row.getCell(COL_PSTDATE).getStringCellValue()));
			operdoc.setPostdate(row.getCell(COL_PSTDATE).getDateCellValue());
			if(row.getCell(COL_QTY)!=null)
				operdoc.setQty(row.getCell(COL_QTY).getNumericCellValue());
			if(row.getCell(COL_UOM)!=null)
				operdoc.setUom(row.getCell(COL_UOM).getStringCellValue());
			if(row.getCell(COL_ORDER)!=null) {
				row.getCell(COL_ORDER).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setOrdernum(row.getCell(COL_ORDER).getStringCellValue());
			}
			if(row.getCell(COL_CUSTOMER)!=null) {
				row.getCell(COL_CUSTOMER).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setCustomer(row.getCell(COL_CUSTOMER).getStringCellValue());
			}
			if(row.getCell(COL_VENDOR)!=null) {
				row.getCell(COL_VENDOR).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setVendor(row.getCell(COL_VENDOR).getStringCellValue());
			}
			if(row.getCell(COL_HEADER)!=null) {
				row.getCell(COL_HEADER).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setHeader(row.getCell(COL_HEADER).getStringCellValue());
			}
			return operdoc;
		} catch(Throwable ex) {
			logger.error("���ܴ��ж�������ȡ���ϲ�����¼,Ϊ��["+row.getRowNum()+"]�С�",ex);
			return null;
		}
	}
	
	
	/**
	 * ��֤Excel��Sheet�Ƿ�����ȷ�����ϲ����ĵ���ȡ�����
	 * @param sheet Excel�е�Sheet
	 * @return ��֤�ɹ�true/��֤ʧ��false
	 */
	private static boolean validateSheet(Sheet sheet) {
		if(sheet==null) {
			logger.error("������֤Sheet�Ƿ�����ȷ�����ϲ����ĵ����,Sheet����Ϊ�ա�");
			return false;
		}
		Cell cell;
		for(Location loc:excelValidatorMap.keySet()) {
			cell=sheet.getRow(loc.row).getCell(loc.column);
			if(cell==null) {
				logger.error("���ϲ����ĵ�Sheet��֤ʧ�ܣ�λ��Row["+loc.row+"]Column["+loc.column+"]�ĵ�Ԫ��Ϊ�ա�");
				return false;
			}
			if(!cell.getStringCellValue().equals(excelValidatorMap.get(loc))) {
				logger.error("���ϲ����ĵ�Sheet��֤ʧ�ܣ�λ��Row["+loc.row+"]Column["+loc.column+"]������["+cell.getStringCellValue()+"]����֤�ַ���["+excelValidatorMap.get(loc)+"������");
				return false;
			}
		}
		return true;
	}
	
}

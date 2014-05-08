package com.logsys.matoperdoc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
 * SAP���������ϲ����ĵ�Excel���ݶ�ȡ��
 * @author lx8sn6
 */
public class MatOperDocContentSAPExcelReader {

	private static final Logger logger=Logger.getLogger(MatOperDocContentSAPExcelReader.class);
	
	/**Excel����֤ͼ��λ��->�ַ���*/
	private static Map<Location,String> excelValidatorMap=new HashMap<Location,String>();
	
	/**��ʽ��¼��ʼ����*/
	private static final int ROW_BEGIN=3;
	
	/**PN��*/
	private static final int COL_PN=1;
	
	/**Storage Location��*/
	private static final int COL_SLOC=1;
	
	/**Plant��*/
	private static final int COL_PLANT=10;
	
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
	
	/**��������*/
	private static final int COL_ORDER=10;
	
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
		String datevalue;
		int lastrow=0;		//��¼��һ������
		DateFormat dformat=new SimpleDateFormat("yyyy.MM.dd");	//���ڸ�ʽ
		try {
			sheet=wb.getSheetAt(0);
			if(!validateSheet(sheet)) return null;
			doclist=new ArrayList<MatOperDocContent>();
			for(Row row:sheet) {			//��ʼ����sheet��
				if(row.getRowNum()<ROW_BEGIN) continue;		//�������С����ʼ�У������
				if(row.getCell(COL_PN)==null) {		//���Cellû������,��˵��Ҫ��ȡ��һ������,������pn����Ϊ��
					matpn=null;
					lastrow=row.getRowNum();
					continue;
				} else {
					row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
					//�����Ԫ����û��ֵ����pn����Ϊ�գ���������һ��
					if(row.getCell(COL_PN).getStringCellValue()==null||row.getCell(COL_PN).getStringCellValue().equals("")) {		//���pn��Ϊ��
						matpn=null;
						lastrow=row.getRowNum();
						continue;
					}
				}
				if(matpn==null||row.getRowNum()-lastrow>1) {			//���matpnΪnull,��������������һ�в�����1,��˵����һ��Ϊ���У�����һ����ֵ���ҵ�һ����ֵ��һ��Ϊpnֵ
					row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
					matpn=row.getCell(COL_PN).getStringCellValue();		//��ȡpnֵ
					System.out.println(row.getRowNum()+"/"+lastrow);
					plant=row.getCell(COL_PLANT).getStringCellValue();	//��ȡ����ֵ
					lastrow=row.getRowNum();
					continue;
				}
				System.out.println("Now Processing Row["+row.getRowNum()+"]");
				//���matpnΪ�գ���˵�����λ��Ϊ��λ
				doctemp=new MatOperDocContent();
				doctemp.setPn(new String(matpn));
				doctemp.setPlant(new String(plant));
				row.getCell(COL_SLOC).setCellType(Cell.CELL_TYPE_STRING);
				doctemp.setSloc(row.getCell(COL_SLOC).getStringCellValue());
				row.getCell(COL_MVMTYPE).setCellType(Cell.CELL_TYPE_STRING);
				doctemp.setMvtype(row.getCell(COL_MVMTYPE).getStringCellValue());
				row.getCell(COL_DOCNUM).setCellType(Cell.CELL_TYPE_STRING);
				doctemp.setDocnum(row.getCell(COL_DOCNUM).getStringCellValue());
				datevalue=row.getCell(COL_PSTDATE).getStringCellValue();
				doctemp.setPostdate(dformat.parse(datevalue));
				doctemp.setQty(row.getCell(COL_QTY).getNumericCellValue());
				doctemp.setUom(row.getCell(COL_UOM).getStringCellValue());
				if(row.getCell(COL_ORDER)!=null) {
					row.getCell(COL_ORDER).setCellType(Cell.CELL_TYPE_STRING);
					doctemp.setOrder(row.getCell(COL_ORDER).getStringCellValue());
				}
				if(row.getCell(COL_CUSTOMER)!=null) {
					row.getCell(COL_CUSTOMER).setCellType(Cell.CELL_TYPE_STRING);
					doctemp.setCustomer(row.getCell(COL_CUSTOMER).getStringCellValue());
				}
				if(row.getCell(COL_VENDOR)!=null) {
					row.getCell(COL_VENDOR).setCellType(Cell.CELL_TYPE_STRING);
					doctemp.setVendor(row.getCell(COL_VENDOR).getStringCellValue());
				}
				if(row.getCell(COL_HEADER)!=null)
					doctemp.setHeader(row.getCell(COL_HEADER).getStringCellValue());
				//System.out.println(doctemp);
				doclist.add(doctemp);
				lastrow=row.getRowNum();
			}
			return doclist;
		} catch(Throwable ex) {
			logger.error("��ȡSAP����ȡ��Excel������¼�ĵ����ݳ��ִ���:",ex);
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

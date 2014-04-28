package com.logsys.production;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.logsys.production.bwi.ProductionDataReaderExcel_BWI;

/**
 * ����ģ������
 * @author lx8sn6
 */
public class ProductionProcess {

	private static final Logger logger=Logger.getLogger(ProductionProcess.class);
	
	/**
	 * �����ݿ�����ȡ�������ݲ�д�����ݿ�
	 * @param filepath �ļ�·��
	 * @param dayofmonth ��Ҫд������������<=0,��ñ���������������Ҫд��
	 * @return д��ļ�¼����/-1ʧ��
	 */
	public static int extractOutputDataFromPdExcelFileToDB(String filepath, int dayofmonth) {
		List<ProductionContent> prodlist=ProductionDataReaderExcel_BWI.readDataFromFile(filepath, dayofmonth);	//��ȡ�б�
		if(prodlist==null) return -1;
		String statInfo=ProductionDataReaderExcel_BWI.getStastisticsInfo(prodlist);		//��ȡͳ����Ϣ
		if(JOptionPane.showConfirmDialog(null,statInfo,"�Ƿ����д�����ݿ�?",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) {		//ȷ��д��Ի�
			logger.info("ֹͣ����ȡ������������д�����ݿ⣬�û�ȡ����");
			return -1;
		}
		int qtywrited=ProductionDataWriterDB.writeDataToDB(prodlist);					//������д�����ݿ�
		if(qtywrited>=0) {
			logger.info("�ɹ����ļ�["+filepath+"]����ȡ��������["+qtywrited+"]����д�����ݿ⡣");
			return qtywrited;
		} else {
			logger.info("��Ȼ��ȡ�����������ݣ���д��ʧ�ܡ�");
			return -1;
		}
	}
	
	/**
	 * ��һ���ļ��������е�������Excel������<<����>>��������ȡ����������ȡ���ļ��С�
	 * @param folderpath �ļ���·��
	 * @return д���¼����/-1ʧ��
	 */
	public static int extractOutputDataFromPdExcelFolderToDB_PreviousDay(String folderpath) {
		if(folderpath==null) {
			logger.error("ֹͣ��ȡ������������ݡ�����Ϊ�ա�");
			return -1;
		}
		File pdfolder=new File(folderpath);
		if(!pdfolder.exists()) {
			logger.error("ֹͣ���ļ���["+folderpath+"]����ȡ����Ĳ������ݡ��ļ��в����ڡ�");
			return -1;
		}
		if(!pdfolder.isDirectory()) {
			logger.error("ֹͣ���ļ���["+folderpath+"]����ȡ����Ĳ������ݡ�·�������ļ��С�");
			return -1;
		}
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		int dayofmonth=cal.get(Calendar.DAY_OF_MONTH);
		List<ProductionContent> prodlist;
		for(File file:pdfolder.listFiles()) {
			if(!file.isFile()) {
				logger.warn("�ļ���["+folderpath+"]����·��["+file.getAbsolutePath()+"]���ļ��У�������");
				continue;
			}
			prodlist=ProductionDataReaderExcel_BWI.readDataFromFile(file.getAbsolutePath(), dayofmonth);
			if(prodlist==null) {
				logger.warn("������ȡ�ļ�["+file.getAbsolutePath()+"]�е�����.�������ļ���");
				continue;
			} else {
				logger.info("�ɹ���ȡ�ļ�["+file.getAbsolutePath()+"]�еĲ�����¼:["+prodlist.size()+"]����");
			}
		}
		return -1;
	}
	
}

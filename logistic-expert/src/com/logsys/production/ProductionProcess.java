package com.logsys.production;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.logsys.production.bwi.ProductionDataReaderExcel_BWI;

/**
 * 生产模块流程
 * @author lx8sn6
 */
public class ProductionProcess {

	private static final Logger logger=Logger.getLogger(ProductionProcess.class);
	
	/**
	 * 从数据库中提取产出数据并写入数据库
	 * @param filepath 文件路径
	 * @param dayofmonth 将要写入的天数，如果<=0,则该报告中所有天数都要写入
	 * @return 写入的记录条数/-1失败
	 */
	public static int extractOutputDataFromPdExcelFileToDB(String filepath, int dayofmonth) {
		List<ProductionContent> prodlist=ProductionDataReaderExcel_BWI.readDataFromFile(filepath, dayofmonth);	//提取列表
		if(prodlist==null) return -1;
		String statInfo=ProductionDataReaderExcel_BWI.getStastisticsInfo(prodlist);		//获取统计信息
		if(JOptionPane.showConfirmDialog(null,statInfo,"是否继续写入数据库?",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) {		//确认写入对话
			logger.info("停止将提取出的生产数据写入数据库，用户取消。");
			return -1;
		}
		int qtywrited=ProductionDataWriterDB.writeDataToDB(prodlist);					//将数据写入数据库
		if(qtywrited>=0) {
			logger.info("成功从文件["+filepath+"]中提取生产数据["+qtywrited+"]条并写入数据库。");
			return qtywrited;
		} else {
			logger.info("虽然提取出的生产数据，但写入失败。");
			return -1;
		}
	}
	
	/**
	 * 将一个文件夹下所有的生产用Excel报表中<<昨天>>的数据提取出来【不提取子文件夹】
	 * @param folderpath 文件夹路径
	 * @return 写入记录数量/-1失败
	 */
	public static int extractOutputDataFromPdExcelFolderToDB_PreviousDay(String folderpath) {
		if(folderpath==null) {
			logger.error("停止提取昨天的生产数据。参数为空。");
			return -1;
		}
		File pdfolder=new File(folderpath);
		if(!pdfolder.exists()) {
			logger.error("停止从文件夹["+folderpath+"]中提取昨天的产出数据。文件夹不存在。");
			return -1;
		}
		if(!pdfolder.isDirectory()) {
			logger.error("停止从文件夹["+folderpath+"]中提取昨天的产出数据。路径不是文件夹。");
			return -1;
		}
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		int dayofmonth=cal.get(Calendar.DAY_OF_MONTH);
		List<ProductionContent> prodlist;
		for(File file:pdfolder.listFiles()) {
			if(!file.isFile()) {
				logger.warn("文件夹["+folderpath+"]的子路径["+file.getAbsolutePath()+"]是文件夹，跳过。");
				continue;
			}
			prodlist=ProductionDataReaderExcel_BWI.readDataFromFile(file.getAbsolutePath(), dayofmonth);
			if(prodlist==null) {
				logger.warn("不能提取文件["+file.getAbsolutePath()+"]中的内容.跳过此文件。");
				continue;
			} else {
				logger.info("成功提取文件["+file.getAbsolutePath()+"]中的产出记录:["+prodlist.size()+"]条。");
			}
		}
		return -1;
	}
	
}

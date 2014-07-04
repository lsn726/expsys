package com.logsys.demand;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.model.ModelContent;
import com.logsys.model.ModelService;
import com.logsys.util.DateInterval;

/**
 * Demand导入时Excel表格的封装服务
 * 运行工厂函数getDemandExcelContainer()获取对象
 * @author lx8sn6
 */
public class DemandExcelService {

	private static final Logger logger=Logger.getLogger(DemandExcelService.class);
	
	/**需求日期列*/
	private static final int COL_DATE=0;
	
	/**需求型号列*/
	private static final int COL_PN=1;
	
	/**需求数量列*/
	private static final int COL_QTY=2;
	
	/**需求Sheet的名称*/
	private static final String DEMAND_SHEET_NAME="UploadDemand";
	
	/**需求Excel表格的对象*/
	private Workbook demwb;
	
	/**从Excel中提取的需求数据*/
	private List<DemandContent> demandlist;
	
	/**
	 * 时间区间映射图：PartNumber->时间区间对象，mindate为需求pn最小值，maxdate为需求pn最大值
	 * 主要用于更新需求数据库的前序步骤，即删除旧有需求数据时，确定删除需求的日期范围。
	 **/
	private Map<String,DateInterval> demandInterval;
	
	/**禁止从外部创建对象*/
	private DemandExcelService() {}
	
	/**
	 * 需求Excel表格容器类的工厂函数
	 * @param filepath 文件路径
	 * @return 容器对象/null
	 */
	public static DemandExcelService getDemandExcelService(String filepath) {
		if(filepath==null) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象:文件路径为空");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象:文件不存在");
			return null;
		}
		DemandExcelService container=new DemandExcelService();
		try {
			container.demwb=WorkbookFactory.create(file);
		} catch(Throwable ex) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象,生成Workbook对象时出现错误。:",ex);
			return null;
		}
		if(!container.processWorkBook()) {
			logger.error("不能处理Excel工作簿["+filepath+"]文件。");
			return null;
		}
		return container;
	}
	
	/**
	 * 与数据库进行同步:
	 * 第一步：备份提取的需求数据
	 * 第二步：删除以前的需求数据
	 * 第三步：写入新的需求数据
	 * @return 成功true/失败false
	 */
	public boolean syncDatabase() {
		int backupedRecordQty=backupDemandData();			//第一步：备份提取的需求数据
		if(backupedRecordQty<0) {
			logger.error("不能与数据库进行同步，数据库备份失败。");
		}
		int removedRecordQty=removeObsoleteDemandData();	//第二步：删除以前的需求数据
		if(removedRecordQty<0) {
			logger.error("不能与数据库进行同步，旧需求数据不能被删除。");
			return false;
		}
		int writedRecordQty=writeDemandData();				//第三步：写入新的需求数据
		if(writedRecordQty<0) {
			logger.error("不能与数据库进行同步，新数据写入错误。");
			logger.fatal("注意！旧需求数据已经删除，但新需求数据没有写入！");
			return false;
		}
		logger.info("需求数据成功写入数据库：备份数据["+backupedRecordQty+"]条,删除旧需求数据["+removedRecordQty+"]条，新需求数据写入["+writedRecordQty+"]条.");
		return true;
	}
	
	/**
	 * 处理工作簿函数
	 * 第一步：验证表格准确性
	 * 第二步：提取表格数据
	 * 第三步：分析提取的数据
	 * 第四步：后期数据加工
	 * @return 处理成功/处理失败
	 */
	private boolean processWorkBook() {
		if(!verifyDataSheet()) {		//第一步：验证表格准确性
			logger.error("不能处理需求工作簿，验证表格未通过。");
			return false;
		}
		if(!extractData()) {			//第二步：提取表格数据
			logger.error("不能处理需求工作簿，未能成功提取Excel数据。");
			return false;
		}
		if(!analyzeData()) {			//第三步：分析提取的数据
			logger.error("不能处理需求工作簿，未能成功分析提取出的需求数据。");
			return false;
		}
		if(!reviseData()) {				//第四步：后期数据加工
			logger.error("不能处理需求工作簿，未能成功进行后期数据加工。");
		}
		return true;
	}
	
	/**
	 * 核查数据Sheet的准确性
	 * @return 验证成功true/验证失败false
	 */
	private boolean verifyDataSheet() {
		Sheet sheet=demwb.getSheet(DEMAND_SHEET_NAME);
		if(sheet==null) {
			logger.error("无法核查数据sheet的准确性。没有名为["+DEMAND_SHEET_NAME+"]的sheet，请将包含需求数据的sheet重命名。");
			return false;
		}
		return true;
	}
	
	/**
	 * 提取文件中的数据
	 * @return true提取成功/false提取失败
	 */
	private boolean extractData() {
		Sheet datasheet=demwb.getSheet(DEMAND_SHEET_NAME);
		List<DemandContent> demlist=new ArrayList<DemandContent>(); 
		DemandContent node;
		for(Row row:datasheet) {
			node=new DemandContent();
			try {
				node.setDate(row.getCell(COL_DATE).getDateCellValue());
				row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
				node.setPn(row.getCell(COL_PN).getStringCellValue());
				row.getCell(COL_QTY).setCellType(Cell.CELL_TYPE_NUMERIC);
				node.setQty(row.getCell(COL_QTY).getNumericCellValue());
			} catch(Throwable ex) {
				logger.error("不能从工作簿中提取需求数据，提取时出现错误。",ex);
				return false;
			}
			demlist.add(node);
		}
		demandlist=demlist;
		return true;
	}
	
	/**
	 * 分析表格中的数据，并根据原数据生成必要数据，本步骤不改变原有数据或者其衍生数据
	 * 1、将每个型号的最大时间和最小时间确定
	 * @return 分析成功true/分析失败false
	 */
	private boolean analyzeData() {
		if(demandlist==null) {
			logger.error("不能分析需求列表，需求列表尚未被初始化。");
			return false;
		}
		Map<String,DateInterval> intervalmap=new HashMap<String,DateInterval>();
		String pn;
		Date date;
		DateInterval itval;
		for(DemandContent demcont:demandlist) {		//遍历需求列表，求出每个pn的需求最小时间和最大时间
			pn=demcont.getPn();
			date=demcont.getDate();
			itval=intervalmap.get(pn);
			if(itval==null) {						//如果没有区间对象，则创建对象，将初始值设置为当前date
				itval=new DateInterval();
				itval.begindate=date;
				itval.enddate=date;
				intervalmap.put(pn, itval);			//加入新节点后继续
				continue;
			}
			if(date.before(itval.begindate))		//如果时间早于区间最小值，则更新最小值
				itval.begindate=date;
			if(date.after(itval.enddate))			//如果时间晚于区间最大值，则更新最大值
				itval.enddate=date;
		}
		demandInterval=intervalmap;					//设置区间对象
		return true;
	}
	
	/**
	 * 对于数据的后期修订。本步骤会根据业务需要而更改原有数据或者其衍生数据。
	 * 第一步：合并重复的需求节点
	 * 第二步：更新发货天数修正数据
	 * @return 成功true/失败false
	 */
	private boolean reviseData() {
		if(demandlist==null) {
			logger.error("不能修正需求列表，列表尚未被初始化。");
			return false;
		}
		int recordMerged=mergeDuplicatedPnDateRecord();		//第一步：合并重复的需求节点
		if(recordMerged<0) {
			logger.error("不能修正需求列表，合并重复节点时出现错误。");
			return false;
		} else if(recordMerged>0)
			logger.info("需求数据中的pn-date重复值已合并，条数["+recordMerged+"]条.");
		if(!updateDlvFixDays()) {
			logger.error("更新发货天术修正失败!");
			return false;
		}
		return true;
	}
	
	/**
	 * 合并同一型号同一日期的多条记录
	 * @return 合并的条目数量
	 */
	private int mergeDuplicatedPnDateRecord() {
		if(demandlist==null) {
			logger.error("不能合并重复日期/pn的项，列表尚未被初始化。");
			return -1;
		}
		Map<String,Map<Date,DemandContent>> demandmap=new HashMap<String,Map<Date,DemandContent>>();	//用于辅助筛查重复的Map<pn-Map<date,content>>对象
		List<DemandContent> removelist=new ArrayList<DemandContent>();		//需要删除的重复节点
		Date demdate;
		String pn;
		int counter=0;
		for(DemandContent demand:demandlist) {					//遍历查重
			pn=demand.getPn();
			if(!demandmap.containsKey(pn))						//如果型号不存在，则新建Map并写入
				demandmap.put(pn, new HashMap<Date,DemandContent>());
			demdate=demand.getDate();
			if(demandmap.get(pn).containsKey(demdate)) {		//如果已经有该型号下该日期的dem，则合并需求
				demandmap.get(pn).get(demdate).setQty(demandmap.get(pn).get(demdate).getQty()+demand.getQty());
				removelist.add(demand);							//将不需要的节点加入删除列表
				counter++;
			} else
				demandmap.get(pn).put((Date)demand.getDate().clone(), demand);	//写入需求数据
		}
		for(DemandContent demand:removelist)					//遍历删除需要删除的节点
			demandlist.remove(demand);
		return counter;
	}
	
	/**
	 * 在需求对象列表中更新发货天数修正数据
	 * @return true更新成功/false更新失败
	 */
	private boolean updateDlvFixDays() {
		if(demandlist==null) {
			logger.error("不能更新发货天数修正数据，列表尚未被初始化。");
			return false;
		}
		String pn;
		ModelContent mcont;
		for(DemandContent demcont:demandlist) {
			pn=demcont.getPn();
			mcont=ModelService.getModelContentByPn(pn); //将该Model的在途天数*-1后记为发货天术向前修正
			if(mcont==null) {
				logger.error("不能更新发货天术修正数据，出现不能识别的需求型号["+pn+"]");
				return false;
			}
			demcont.setDlvfix(-mcont.getIntranday());
		}
		return true;
	}
	
	/**
	 * 移除以前的需求数据，依据的是demandInterval记录的每个型号新需求的日期区间
	 * @return 移除需求数据的条数
	 */
	private int removeObsoleteDemandData() {
		if(demandInterval==null) {
			logger.error("不能移除过期需求数据，需求区间对象为空。");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("session创建出现错误:"+ex);
			return -1;
		}
		String hql;
		Query delquery;
		int counter=0;
		for(String pn:demandInterval.keySet()) {			//遍历需求区间对象，删除旧有数据
			hql="delete from DemandContent where pn=:pn and date>=:mindate and date<=:maxdate";
			delquery=session.createQuery(hql);
			delquery.setString("pn", pn);
			delquery.setDate("mindate", demandInterval.get(pn).begindate);
			delquery.setDate("maxdate", demandInterval.get(pn).enddate);
			counter+=delquery.executeUpdate();
		}
		session.getTransaction().commit();
		session.close();
		return counter;
	}
	
	/**
	 * 备份需求新提取的数据，版本为当前时间
	 * @return 备份的需求数据条数
	 */
	private int backupDemandData() {
		if(demandlist==null) {
			logger.error("不能备份需求数据，因为需求列表对象为空。");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("session创建出现错误:"+ex);
			return -1;
		}
		int counter=0;
		Date version=new Date();		//取版本为当前日期
		for(DemandContent demcont:demandlist) {
			session.save(DemandBackupContent.createDemBkupContFromDemCont(demcont, version));
			if(counter%50==0) {
				session.flush();
				session.clear();
			}
			counter++;
		}
		session.getTransaction().commit();
		session.close();
		return counter;
	}
	
	/**
	 * 写入需求数据到数据库
	 * @return 写入的需求记录条数
	 */
	private int writeDemandData() {
		if(demandlist==null) {
			logger.error("不能写入需求数据，因为需求列表对象为空。");
			return 0;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("session创建出现错误:"+ex);
			return -1;
		}
		int counter=0;
		for(DemandContent dcont:demandlist) {
			session.save(dcont);
			if(counter%100==0) {
				session.flush();
				session.clear();
			}
			counter++;
		}
		session.getTransaction().commit();
		session.close();
		return counter;
	}
	
}

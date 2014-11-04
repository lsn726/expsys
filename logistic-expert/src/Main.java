import com.logsys.bom.BOMService;



public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\dem.xlsx");							//从Excel文件导入新需求
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\pp.xlsx", new Date("2014/10/30"), new Date("2014/11/23"),true));	//上传从下周一开始的计划
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\fa2.xls", -1);	//将生产日报导入数据库。《《将导入所有数据》》
		//MatOperDocContentProcess.importSAPMb51ExcelFileIntoDB("e:\\201410.xlsx");		//将SAP的mb51命令倒出的物料操作数据写入数据库
		//StockProcess.importStockDataFromExcel_SAP_MB52("e:\\stock.xlsx",null);		//导入SAP的MB52库存数据,时间默认null为当天。
		
		//ReportProcess.genMRPMatrixToExcel("e:\\MRPMatrix.xlsx", 35);					//生成MRP矩阵报表，矩阵长度(周数)为第二参数
		//ReportProcess.genSAPMrpUploadReportToExcel("e:\\SAPMrpUploadReport.xlsx", 35);//生成SAP的Mrp上传报告。
		//ReportProcess.genDemandMatrixToExcel("e:\\DemandReport.xlsx",true,false);		//生成需求矩阵报表
		
		//BWIWarehouseExcelReport report=BWIWarehouseExcelReport.createReportObject("e:\\rec.xlsx");
		//report.analyzeWorkbook();
		
		//TODO:StockDataReaderExcel继续完善
		//TODO:为所有Process加入SystemUtils.getUniqueMachineID()
		//System.out.println(SystemUtils.getUniqueMachineID());
		BOMService.test();
	}

}
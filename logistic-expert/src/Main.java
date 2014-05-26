import com.logsys.report.DemandReportForExcel;





public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\20140520D.xlsx",null);		//导入新需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\22283596.xlsx");		//上传BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/5/26"), new Date("2014/6/8"),true));	//上传从下周一开始的计划
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\组装线FA1生产线日报表2014.5.xls", -1);	//将生产日报导入数据库。《《将导入所有数据》》
		//MatOperDocContentProcess.importSAPMb51ExcelFileIntoDB("e:\\201404.xlsx");	//将SAP的mb51命令倒出的物料操作数据写入数据库	
		
		//ReportProcess.genMRPMatrixToExcel("e:\\MRPMatrix.xlsx", 32);	//生成MRP矩阵报表，矩阵长度(周数)为第二参数
		//ReportProcess.genDemandMatrixToExcel("D:\\Demand.xlsx");		//生成需求矩阵报表

		try {
			new DemandReportForExcel().writeReportToFile("d:\\123.xlsx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO:StockDataReaderExcel继续完善
	}

}
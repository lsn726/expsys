

public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\dem.xlsx");							//从Excel文件导入新需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\22283596.xlsx");					//上传BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/7/25"), new Date("2014/8/31"),true));	//上传从下周一开始的计划
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\MOD.xls", -1);	//将生产日报导入数据库。《《将导入所有数据》》
		//MatOperDocContentProcess.importSAPMb51ExcelFileIntoDB("e:\\MOL201406.xlsx");	//将SAP的mb51命令倒出的物料操作数据写入数据库	
		
		//ReportProcess.genMRPMatrixToExcel("e:\\MRPMatrix.xlsx", 35);					//生成MRP矩阵报表，矩阵长度(周数)为第二参数
		//ReportProcess.genDemandMatrixToExcel("e:\\DemandReport.xlsx",false,false);	//生成需求矩阵报表,默认不生成回溯需求矩阵
		
		//TODO:StockDataReaderExcel继续完善
		//TODO:为所有Process加入SystemUtils.getUniqueMachineID()
		//System.out.println(SystemUtils.getUniqueMachineID());
	}

}
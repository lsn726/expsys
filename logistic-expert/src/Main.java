import com.logsys.report.DemandReportForExcel;



public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\20140514D.xlsx",null);		//导入新需求
		//DemandProcess.exportDemandToExcel("e:\\ExportedDemand.xlsx", null, new Date(), null);	//导出需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\22283596.xlsx");		//上传BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/5/19"), new Date("2014/6/1"),true));	//上传从下周一开始的计划
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\组装线FA1生产线日报表2014.5.xls", 12);	//将生产日报导入数据库。《《将导入所有数据》》
		//MatOperDocContentProcess.importSAPMb51ExcelFileIntoDB("e:\\201404.xlsx");	//将SAP的mb51命令倒出的物料操作数据写入数据库	
		
		//ReportProcess.genDemandMatrixToExcel("d:\\DemandMatrix.xlsx", 33);	//生成需求矩阵报表，矩阵长度(周数)25
		try {
			new DemandReportForExcel().writeReportToFile("e:\\test123.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
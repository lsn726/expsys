import com.logsys.production.ProductionProcess;



public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\DemandCQAC.xlsx",null);		//导入新需求
		//DemandProcess.exportDemandToExcel("e:\\Export20140415D.xlsx", null, new Date(), null);	//导出需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//上传BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/4/21"), new Date("2014/5/4"),true));	//上传从下周一开始的计划
		//new MrpReportForExcel().generate("123");								//生成MRP报告
		ProductionProcess.extractOutputDataFromPdExcelFileToDB("e:\\FA2.xls", -1);
	}

}
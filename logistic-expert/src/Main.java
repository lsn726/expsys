import com.logsys.setting.Settings;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\Demand20140404.xlsx",null);	//导入新需求
		//DemandProcess.exportDemandToExcel("e:\\Export20140404.xlsx", null, new Date(), null);	//导出需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//上传BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\pp.xlsx", null, new Date("2014/4/27"),true));	//上传从下周一开始的计划
		//new MrpReportForExcel().generate("123");								//生成MRP报告
		System.out.println(Settings.BWISettings.ppExcelInfo.getVersion());
	}

}
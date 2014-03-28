import com.logsys.report.MrpReportForExcel;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\22271789.xlsx",null);		//录入新需求
		//DemandProcess.exportDemandToExcel("e:\\CS12_22271370.xlsx", null, new Date(), null);	//导出需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//上传BOM
		new MrpReportForExcel().generate("123");								//生成MRP报告
		//BOMNode topnode=BOMUtil.bomListToBomNodeStructure(bomlist);
		//System.out.print(BOMDataReaderDB.getNodesByLevel(0).size());
	}

}
import java.util.Date;

import com.logsys.prodplan.ProdplanProcess;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\CQACRR.xlsx",null);	//导入新需求
		//DemandProcess.exportDemandToExcel("e:\\Export20140331.xlsx", null, new Date(), null);	//导出需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//上传BOM
		//new MrpReportForExcel().generate("123");								//生成MRP报告
		ProdplanProcess.importProdplanFromExcel("e:\\pp.xlsx", null, new Date("2014/4/27"));	//导入从下周一开始的计划
		
	}

}
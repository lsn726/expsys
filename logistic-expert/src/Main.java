import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.logsys.production.ProductionContent;
import com.logsys.production.bwi.ProductionDataReaderExcel_BWI;

public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\22271789.xlsx",null);		//导入新需求
		//DemandProcess.exportDemandToExcel("e:\\Export20140415D.xlsx", null, new Date(), null);	//导出需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//上传BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", null, new Date("2014/4/20"),true));	//上传从下周一开始的计划
		//new MrpReportForExcel().generate("123");								//生成MRP报告
		List<ProductionContent> prodlist=ProductionDataReaderExcel_BWI.readDataFromFile("d:\\WASH.xls",0);
		ProductionDataReaderExcel_BWI.debugInfoStastistics(prodlist);
	}

}
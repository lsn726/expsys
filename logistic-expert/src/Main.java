import java.util.Date;

import com.logsys.demand.DemandProcess;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\Demand.xlsx",null);		//导入新需求
		//DemandProcess.exportDemandToExcel("e:\\ExportDemand.xlsx", null, new Date(), null);	//导出需求
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//上传BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/4/28"), new Date("2014/5/4"),true));	//上传从下周一开始的计划
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("e:\\PC.xls", -1);	//将生产日报导入数据库。《《将导入所有数据》》
		/**
		MrpReportForExcel mrpreport=new MrpReportForExcel();
		Matrixable demmatrix=mrpreport.getDemandMatrix(60);
		
		Workbook wb=new XSSFWorkbook();				//创建工作簿
		Sheet demsheet=wb.createSheet("Demand");	//创建sheet
		demmatrix.writeToExcelSheet(demsheet, new Location(0,0));
		try {
			FileOutputStream fileOut=new FileOutputStream("d:\\text.xlsx");
			wb.write(fileOut);
			fileOut.close();
		} catch(Throwable ex) {
			ex.printStackTrace();
		}
		*/
	}

}
import com.logsys.bom.BOMService;



public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\dem.xlsx");							//��Excel�ļ�����������
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\pp.xlsx", new Date("2014/10/30"), new Date("2014/11/23"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\fa2.xls", -1);	//�������ձ��������ݿ⡣�����������������ݡ���
		//MatOperDocContentProcess.importSAPMb51ExcelFileIntoDB("e:\\201410.xlsx");		//��SAP��mb51����������ϲ�������д�����ݿ�
		//StockProcess.importStockDataFromExcel_SAP_MB52("e:\\stock.xlsx",null);		//����SAP��MB52�������,ʱ��Ĭ��nullΪ���졣
		
		//ReportProcess.genMRPMatrixToExcel("e:\\MRPMatrix.xlsx", 35);					//����MRP���󱨱����󳤶�(����)Ϊ�ڶ�����
		//ReportProcess.genSAPMrpUploadReportToExcel("e:\\SAPMrpUploadReport.xlsx", 35);//����SAP��Mrp�ϴ����档
		//ReportProcess.genDemandMatrixToExcel("e:\\DemandReport.xlsx",true,false);		//����������󱨱�
		
		//BWIWarehouseExcelReport report=BWIWarehouseExcelReport.createReportObject("e:\\rec.xlsx");
		//report.analyzeWorkbook();
		
		//TODO:StockDataReaderExcel��������
		//TODO:Ϊ����Process����SystemUtils.getUniqueMachineID()
		//System.out.println(SystemUtils.getUniqueMachineID());
		BOMService.test();
	}

}
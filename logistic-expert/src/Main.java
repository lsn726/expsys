import com.logsys.report.DemandReportForExcel;





public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\20140520D.xlsx",null);		//����������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\22283596.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/5/26"), new Date("2014/6/8"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\��װ��FA1�������ձ���2014.5.xls", -1);	//�������ձ��������ݿ⡣�����������������ݡ���
		//MatOperDocContentProcess.importSAPMb51ExcelFileIntoDB("e:\\201404.xlsx");	//��SAP��mb51����������ϲ�������д�����ݿ�	
		
		//ReportProcess.genMRPMatrixToExcel("e:\\MRPMatrix.xlsx", 32);	//����MRP���󱨱����󳤶�(����)Ϊ�ڶ�����
		//ReportProcess.genDemandMatrixToExcel("D:\\Demand.xlsx");		//����������󱨱�

		try {
			new DemandReportForExcel().writeReportToFile("d:\\123.xlsx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO:StockDataReaderExcel��������
	}

}
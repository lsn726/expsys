import com.logsys.report.DemandReportForExcel;



public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\20140514D.xlsx",null);		//����������
		//DemandProcess.exportDemandToExcel("e:\\ExportedDemand.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\22283596.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/5/19"), new Date("2014/6/1"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\��װ��FA1�������ձ���2014.5.xls", 12);	//�������ձ��������ݿ⡣�����������������ݡ���
		//MatOperDocContentProcess.importSAPMb51ExcelFileIntoDB("e:\\201404.xlsx");	//��SAP��mb51����������ϲ�������д�����ݿ�	
		
		//ReportProcess.genDemandMatrixToExcel("d:\\DemandMatrix.xlsx", 33);	//����������󱨱����󳤶�(����)25
		try {
			new DemandReportForExcel().writeReportToFile("e:\\test123.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
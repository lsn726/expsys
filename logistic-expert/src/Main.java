import com.logsys.production.ProductionProcess;




public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\NewDemand.xlsx");					//��Excel�ļ�����������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\22283596.xlsx");					//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\pp.xlsx", new Date("2014/6/9"), new Date("2014/6/29"),true));	//�ϴ�������һ��ʼ�ļƻ�
		ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\RS23.xls", -1);		//�������ձ��������ݿ⡣�����������������ݡ���
		//MatOperDocContentProcess.importSAPMb51ExcelFileIntoDB("e:\\201404.xlsx");		//��SAP��mb51����������ϲ�������д�����ݿ�	
		
		//ReportProcess.genMRPMatrixToExcel("e:\\MRPMatrix.xlsx", 31);			//����MRP���󱨱����󳤶�(����)Ϊ�ڶ�����
		//ReportProcess.genDemandMatrixToExcel("e:\\DemandReport.xlsx",false);	//����������󱨱�,Ĭ�ϲ����ɻ����������
		
		//TODO:StockDataReaderExcel��������
		//TODO:��������DemandExcelContainer���
		//TODO:��дDemandImport����
		//TODO:Ϊ����Process����SystemUtils.getUniqueMachineID()
	}

}
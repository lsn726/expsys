import com.logsys.matoperdoc.MatOperDocContentSAPExcelReader;



public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\20140505D.xlsx",null);		//����������
		//DemandProcess.exportDemandToExcel("e:\\ExportedDemand.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/5/12"), new Date("2014/5/18"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("E:\\��װ��FA2�������ձ���2014.5.xls", 7);	//�������ձ��������ݿ⡣�����������������ݡ���
		//ReportProcess.genDemandMatrixToExcel("e:\\DemandMatrix.xlsx", 25);	//����������󱨱����󳤶�(����)25
		MatOperDocContentSAPExcelReader.readDataFromSAPExcel("e:\\201401.xlsx");
	}

}
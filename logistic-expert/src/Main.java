import com.logsys.production.ProductionProcess;



public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\DemandCQAC.xlsx",null);		//����������
		//DemandProcess.exportDemandToExcel("e:\\Export20140415D.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/4/21"), new Date("2014/5/4"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//new MrpReportForExcel().generate("123");								//����MRP����
		ProductionProcess.extractOutputDataFromPdExcelFileToDB("e:\\FA2.xls", -1);
	}

}
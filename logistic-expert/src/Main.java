import com.logsys.report.MrpReportForExcel;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\22271789.xlsx",null);		//¼��������
		//DemandProcess.exportDemandToExcel("e:\\CS12_22271370.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		new MrpReportForExcel().generate("123");								//����MRP����
		//BOMNode topnode=BOMUtil.bomListToBomNodeStructure(bomlist);
		//System.out.print(BOMDataReaderDB.getNodesByLevel(0).size());
	}

}
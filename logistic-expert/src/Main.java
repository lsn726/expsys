import com.logsys.setting.Settings;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\Demand20140404.xlsx",null);	//����������
		//DemandProcess.exportDemandToExcel("e:\\Export20140404.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\pp.xlsx", null, new Date("2014/4/27"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//new MrpReportForExcel().generate("123");								//����MRP����
		System.out.println(Settings.BWISettings.ppExcelInfo.getVersion());
	}

}
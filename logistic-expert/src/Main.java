import java.util.Date;

import com.logsys.prodplan.ProdplanProcess;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\CQACRR.xlsx",null);	//����������
		//DemandProcess.exportDemandToExcel("e:\\Export20140331.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		//new MrpReportForExcel().generate("123");								//����MRP����
		ProdplanProcess.importProdplanFromExcel("e:\\pp.xlsx", null, new Date("2014/4/27"));	//���������һ��ʼ�ļƻ�
		
	}

}
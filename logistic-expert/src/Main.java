import java.util.List;

import org.apache.log4j.Logger;

import com.logsys.production.ProductionContent;
import com.logsys.production.ProductionDataWriterDB;
import com.logsys.production.bwi.ProductionDataReaderExcel_BWI;

public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\20140415D.xlsx",null);		//����������
		//DemandProcess.exportDemandToExcel("e:\\Export20140415D.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", null, new Date("2014/4/20"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//new MrpReportForExcel().generate("123");								//����MRP����
		try {
			List<ProductionContent> prodlist=ProductionDataReaderExcel_BWI.readDataFromFile("E:\\RTA201403\\CW.xls");
			System.out.println(prodlist.size());
			//System.out.println(ProductionDataWriterDB.writeDataToDB(prodlist));
		} catch (Throwable e) {
			Logger.getLogger(Main.class).error("����",e);
		}
	}

}
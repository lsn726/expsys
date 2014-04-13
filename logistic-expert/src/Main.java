import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.production.ProductionContent;
import com.logsys.production.bwi.BWIProductionExcelInfoExtractor;
import com.logsys.production.bwi.BWIProductionExcelInfoExtractor_STDRTA20140413;
import com.logsys.setting.pd.bwi.BWIPLInfo;

public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\22271789.xlsx",null);		//����������
		//DemandProcess.exportDemandToExcel("e:\\Export20140409.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", null, new Date("2014/4/20"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//new MrpReportForExcel().generate("123");								//����MRP����
		//TODO:!!!! �� prodplan��prodplan_backup���Final Assembly *->Damper Final Assembly *  <--------------------!!!!
		//TODO:!!!! ���½���production���ݿ�  <--------------------!!!!
		try {
			InputStream readstream = new FileInputStream("d:\\HBF0031.xls");
			Workbook wb=WorkbookFactory.create(readstream);
			Sheet sheet=wb.getSheet("13");
			Row row=sheet.getRow(17);
			Cell cell=row.getCell(11);
			//System.out.println(cell.getStringCellValue().equals("F T Q"));
			//System.out.println(cell.getStringCellValue());
			BWIProductionExcelInfoExtractor ppExcelInfoExactor=new BWIProductionExcelInfoExtractor_STDRTA20140413();
			List<ProductionContent> prodlist=ppExcelInfoExactor.extractOutputData(sheet, BWIPLInfo.STDNAME_DAMPER_RTA_HBF0031);
			for(ProductionContent pcont:prodlist)
				System.out.println(pcont);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
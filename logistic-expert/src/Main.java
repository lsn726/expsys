import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.production.ProductionContent;
import com.logsys.production.bwi.BWIPdExcelDataExtractor;
import com.logsys.production.bwi.BWIPdExcelDataExtractor_DamperHBF;
import com.logsys.setting.pd.bwi.BWIPLInfo;

public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\22271789.xlsx",null);		//����������
		//DemandProcess.exportDemandToExcel("e:\\Export20140414.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", null, new Date("2014/4/20"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//new MrpReportForExcel().generate("123");								//����MRP����
		try {
			InputStream readstream = new FileInputStream("e:\\HBF0835.xls");
			Workbook wb=WorkbookFactory.create(readstream);
			Sheet sheet=wb.getSheet("3");
			//Row row=sheet.getRow(17);
			//Cell cell=row.getCell(11);
			//System.out.println(cell.getStringCellValue().equals("F T Q"));
			//System.out.println(cell.getStringCellValue());
			BWIPdExcelDataExtractor ppExcelInfoExactor=new BWIPdExcelDataExtractor_DamperHBF();
			List<ProductionContent> prodlist=ppExcelInfoExactor.extractOutputData(sheet, BWIPLInfo.STDNAME_DAMPER_RTA_LEAKTESTWASHING);
			for(ProductionContent pcont:prodlist)
				System.out.println(pcont);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			Logger.getLogger(Main.class).error("����",e);
		}
	}

}
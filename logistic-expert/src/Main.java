import java.util.Date;

import com.logsys.demand.DemandProcess;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\Demand.xlsx",null);		//����������
		//DemandProcess.exportDemandToExcel("e:\\ExportDemand.xlsx", null, new Date(), null);	//��������
		//BOMProcess.uploadBOMFromExcel_SAPCS12("e:\\CS12_22271372.xlsx");		//�ϴ�BOM
		//System.out.println(ProdplanProcess.importProdplanFromExcel("e:\\PP.xlsx", new Date("2014/4/28"), new Date("2014/5/4"),true));	//�ϴ�������һ��ʼ�ļƻ�
		//ProductionProcess.extractOutputDataFromPdExcelFileToDB("e:\\PC.xls", -1);	//�������ձ��������ݿ⡣�����������������ݡ���
		/**
		MrpReportForExcel mrpreport=new MrpReportForExcel();
		Matrixable demmatrix=mrpreport.getDemandMatrix(60);
		
		Workbook wb=new XSSFWorkbook();				//����������
		Sheet demsheet=wb.createSheet("Demand");	//����sheet
		demmatrix.writeToExcelSheet(demsheet, new Location(0,0));
		try {
			FileOutputStream fileOut=new FileOutputStream("d:\\text.xlsx");
			wb.write(fileOut);
			fileOut.close();
		} catch(Throwable ex) {
			ex.printStackTrace();
		}
		*/
	}

}
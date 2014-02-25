

import java.util.List;

import com.logsys.demand.DemandDataFileReader;

public class Main {

	public static void main(String[] args) {
		List list=DemandDataFileReader.getDataFromFile("E:\\DataWarehouse\\Demand\\Email_Audi20140225.xlsx");
		System.out.println(list);
	}
	
}

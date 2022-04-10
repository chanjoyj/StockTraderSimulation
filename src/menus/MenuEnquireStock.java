package menus;

import main.Screen;
import menuController.ConstantFlags;
import menuController.Menu;
import menuController.NavigationData;

public class MenuEnquireStock extends Menu{
	public void printMenu() {
		//TODO print records from my records
		
	int optionNum = 0;
	
	Screen.printEnquiryTradingRecordsInPeriodOption(optionNum);
	Screen.printBackOption(optionNum);
	
	}

	public NavigationData performAction(int optionIndex) {
		int stockStart = 0;
		int stockEnd=0;
		//have cases based on the number of shares holding

		switch (optionIndex) {
		case stockStart: {
			//TODO stock selection
			return new NavigationData(ConstantFlags.NAV_MYRECORDS);
		}
		case stockEnd+1:{
			//TODO T4
			return new NavigationData(ConstantFlags.NAV_MYRECORDS);
		}
		case stockEnd+2:{
			return new NavigationData(ConstantFlags.NAV_BACK);
		}
		}
		return null;
	}
	
}

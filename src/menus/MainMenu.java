package menus;

import main.Screen;
import menuController.ConstantFlags;
import menuController.Menu;
import menuController.NavigationData;
import objects.SetToday;

public class MainMenu extends Menu{
	
	public void printMenu() {
		String today= SetToday.getDate();
		Screen.printMainMenu(today);
	}
	
	public NavigationData performAction(int optionIndex) {
		switch (optionIndex) {
			case 1: {
				return new NavigationData(ConstantFlags.NAV_MYRECORDS);
			}
			case 2:{
				return new NavigationData(ConstantFlags.NAV_ENQUIRE);
			}
			case 3:{
				return new NavigationData(ConstantFlags.NAV_TRADE);
			}
			case 4:{
				autoTrade();
				return new NavigationData(ConstantFlags.NAV_MAIN);
			}
			case 5:{
				pass();
				return new NavigationData(ConstantFlags.NAV_MAIN);
			}
			case 6:{
				System.exit(0);
			}
		}
		return null;
	}
	
	private void autoTrade() {
		//TDO Auto trade T8
	}
	
	private void pass() {
		//TODO increment date
		String today= SetToday.getDate();
		SetToday.incrementDate(today);
	}
}

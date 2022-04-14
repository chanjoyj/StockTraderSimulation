package menuController;
import java.util.Scanner;

import main.Screen;
import menus.MainMenu;
import menus.MenuEnquireStock;
import menus.MenuMyRecords;
import menus.MenuTrade;

public class MenuController {
	private static Menu currMenu;
		
	public static void startMenu() {
		currMenu = new MainMenu();
		askMenuOption();
	}
	private static void askMenuOption() {
		NavigationData data= null;
		do {
			//print the menu
			currMenu.printMenu();//runs the menu
			//get menu selections
			int optionIndex=currMenu.getSelection();
			//run the next menu
			data = currMenu.performAction(optionIndex);
			if (data != null) {
				navigate(data);
			}
		}while(data!=null);
	}
	
	public static void navigate(NavigationData nd) {
		switch (nd.getNavTo()) {
			case ConstantFlags.NAV_BACK: {
				Menu parentMenu = currMenu.getParentMenu();
				currMenu.setParentMenu(null);
				currMenu= parentMenu;
				break;
			}
			case ConstantFlags.NAV_MAIN: {
				Menu mainMenu = new MainMenu();
				mainMenu.setParentMenu(currMenu);
				currMenu= mainMenu;
				break;
			}
			case ConstantFlags.NAV_MYRECORDS: {
				Menu myRecords = new MenuMyRecords();
				myRecords.setParentMenu(currMenu);
				currMenu= myRecords;
				break;
			}
			case ConstantFlags.NAV_ENQUIRE: {
				Menu Enquire = new MenuEnquireStock(nd.getStockID());
				Enquire.setParentMenu(currMenu);
				currMenu= Enquire;
				break;
			}
			case ConstantFlags.NAV_TRADE: {
				Menu trade;
				if (nd.getStockID()==null) trade = new MenuTrade();
				else trade = new MenuTrade(nd.getStockID());
				trade.setParentMenu(currMenu);
				currMenu= trade;
				break;
			}
		}
	}
}

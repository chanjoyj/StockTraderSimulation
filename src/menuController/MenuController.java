package menuController;
import java.util.Scanner;

import menus.MainMenu;
import menus.MenuEnquireStock;
import menus.MenuMyRecords;
import menus.MenuTrade;

public class MenuController {
	private static Menu currMenu;
	static Scanner keyboard = new Scanner(System.in);
	
	public static void startMenu() {
		currMenu = new MainMenu();
		askMenuOption();
	}
	private static void askMenuOption() {
		NavigationData data= null;
		do {
			currMenu.printMenu();
			//todo run menu rather than straight to print
			System.out.println("Select an option:");
			//select to next menu is here
			int optionIndex= Integer.parseInt(keyboard.nextLine());
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
				Menu Enquire = new MenuEnquireStock();
				Enquire.setParentMenu(currMenu);
				currMenu= Enquire;
				break;
			}
			case ConstantFlags.NAV_TRADE: {
				Menu trade = new MenuTrade();
				trade.setParentMenu(currMenu);
				currMenu= trade;
				break;
			}
		}
	}
}

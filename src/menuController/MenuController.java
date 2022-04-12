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
			currMenu.printMenu();
			//TODO run menu rather than straight to print	
			int options = currMenu.getNumOptions();
			Screen.printSelectOption();
			 //TODO make this in all menus
			
			boolean valid;
			do {
				try {
					valid = true;
					System.out.println("Select an option: ");
					int select = input.nextInt();
					if (select>options) {
						valid = false;
						Screen.printInvalidMainMenuOption();
					}
				} catch (Exception e) {
					valid = false;
					Screen.printInvalidMainMenuOption();
				}

			} while (!valid);
			
			

			try {
				int optionIndex= Integer.parseInt(Screen.keyboard.nextLine());
				
			} catch (Exception e) {
				// TODO: handle exception
				
				
			}
			
			//
			
			//
			
			
			//select to next menu is here
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

package menus;

import main.Screen;
import main.SetToday;
import main.StockData;
import main.TraderRecords;
import menuController.ConstantFlags;
import menuController.Menu;
import menuController.NavigationData;
import objects.DailyPrice;
import objects.Stock;

public class MainMenu extends Menu{
	
	public void printMenu() {
		String today= SetToday.getDate();
		Screen.printMainMenu(today);
	}
	
	public int getSelection() {
		Screen.printSelectOption();
		boolean valid;
		int optionIndex=6;
		do {
			try {
				valid = true;
				optionIndex = Integer.parseInt(Screen.keyboard.nextLine());
				if (optionIndex>6 || optionIndex<1) {
					valid = false;
					Screen.printInvalidMainMenuOption();
				}
			} catch (Exception e) {
				valid = false;
				Screen.printInvalidMainMenuOption();
			}

		} while (!valid);
		return optionIndex;
	}
	
	public NavigationData performAction(int optionIndex) {
		switch (optionIndex) {
			case 1: {
				return new NavigationData(ConstantFlags.NAV_MYRECORDS);
			}
			case 2:{//enquire a stock
				//enter stock ID
				Screen.printEnterStockId();
				String stockID = Screen.keyboard.nextLine();
				Stock[] stockList= StockData.getStockList();
				//check valid stock id
				boolean valid=false;
				do {
					for (int i = 0; i < stockList.length; i++) {
						if (stockID.equals(stockList[i].getStockID())) {
							valid=true;
							break;
						}
					}
					if (!valid) {
						Screen.printInvalidStockId();
						stockID = Screen.keyboard.nextLine();
					}
				} while (!valid);
				return new NavigationData(ConstantFlags.NAV_ENQUIRE,stockID);
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
		//TODO Auto trade T8
		String endDate;
		do {
			Screen.printEndDatePrompt();
			endDate = Screen.keyboard.nextLine();
			endDate= SetToday.changeDate(endDate);
		} while (endDate<SetToday.getDate());
		String stockID = "00700";
		
		//TODO add a for each date in records from today to end date:
		String[] dateList;
		for (int i = 0; i < array.length; i++) {
			dateList[i];
			DailyPrice dailyPrice = StockData.findDailyPrice(stockID, date);
			TradeRecord lastTrade = TraderRecords.findLastTradeRecord(stockID);
			if (condition) {
				//TODO 
			}
		}
		
	}
	
	private void pass() {
		//increments the date
		String today= SetToday.getDate();
		String date = SetToday.incrementDate(today);
		SetToday.setDate(date);
	}
}

package menus;

import main.Screen;
import main.SetToday;
import main.TraderRecords;
import menuController.ConstantFlags;
import menuController.Menu;
import menuController.NavigationData;
import objects.SharesHolding;
import objects.TradeRecord;

public class MenuMyRecords extends Menu{
	public void printMenu() {
		Double accCash = TraderRecords.getCash();
		Double overallProfit =TraderRecords.getOverallProfit();
		Screen.printAccInfo(accCash,overallProfit);
		
		SharesHolding[] holdingList= TraderRecords.getHoldingList();
		for (int i = holdingList.length-1; i <0; i--) {
			Screen.printShareHoldingOption(holdingList.length-i, holdingList[i].getStockID(),holdingList[i].getNumShares(),holdingList[i].getAveragePrice(),holdingList[i].getTotalProfit());
			
		}		
		int optionNum = holdingList.length;//number of options in stocks
	
	Screen.printEnquiryTradingRecordsInPeriodOption(optionNum+1);
	Screen.printBackOption(optionNum+2);
	}
	
	public int getSelection() {
		Screen.printSelectOption();
		int optionIndex = Integer.parseInt(Screen.keyboard.nextLine());
		return optionIndex;
	}
	
	public NavigationData performAction(int optionIndex) {
		SharesHolding[] holdingList= TraderRecords.getHoldingList();
		//shares menu
		for (int i = holdingList.length-1; i <0; i--) {
			if (optionIndex==holdingList.length-i) {
				tradeRecords(holdingList[i].getStockID());
				
				//print
				Screen.printOrderMenu();
				String selection= Screen.keyboard.nextLine();
				switch (selection) {//TODO Sorting
				case "1": {
					tradeRecords(holdingList[i].getStockID());
				}case "2": {
					//Order by trade price
				}case "3": {
					//Order by trade shares
				}case "4": {
					//Order by total trade amount
				}case "5": {
					//Order by stock ID
				}case "6": {//back
					return new NavigationData(ConstantFlags.NAV_MYRECORDS);
				}
				}				
				break;
			}
		}
		//other menu
		if (optionIndex==holdingList.length+1) {
			//TODO T4
			String startDate;
			String endDate;
			do {
				Screen.printStartDatePrompt();
				startDate = Screen.keyboard.nextLine();
				startDate= SetToday.changeDate(startDate);
				
				Screen.printEndDatePrompt();
				endDate = Screen.keyboard.nextLine();
				endDate= SetToday.changeDate(endDate);
				
				if (endDate<startDate) {
					Screen.printInvalidDatePrompt();
				}
			} while (endDate<startDate);
			
			tradeRecords(startDate,endDate);
			//print
			Screen.printOrderMenu();
			String selection= Screen.keyboard.nextLine();
			switch (selection) {//TODO Sorting
			case "1": {
				tradeRecords(startDate,endDate);
			}case "2": {
				//Order by trade price
			}case "3": {
				//Order by trade shares
			}case "4": {
				//Order by total trade amount
			}case "5": {
				//Order by stock ID
			}case "6": {//back
				return new NavigationData(ConstantFlags.NAV_MYRECORDS);
			}
			}

		}else return new NavigationData(ConstantFlags.NAV_BACK);//TODO Check if this is meant to be back or null
	}
	
	private void tradeRecords(String stockID) {
		TradeRecord[] tradeRecords= TraderRecords.getTradeList();
		for (int i = tradeRecords.length-1; i <0; i--) {
			if (stockID.equals(tradeRecords[i].getStockID())) {
				Screen.printTradeRecord(tradeRecords[i].getDate(),tradeRecords[i].getStockID(),tradeRecords[i].getDirection(),tradeRecords[i].getPrice(),tradeRecords[i].getNumShares(),tradeRecords[i].getPrice()*tradeRecords[i].getNumShares());
			}
		}
	}
	
	private void tradeRecords(String startDate, String endDate) {
		TradeRecord[] tradeRecords= TraderRecords.getTradeList();
		for (int i = tradeRecords.length-1; i <0; i--) {
			if (tradeRecords[i].getDate()<endDate && tradeRecords[i].getDate()>startDate) {
				Screen.printTradeRecord(tradeRecords[i].getDate(),tradeRecords[i].getStockID(),tradeRecords[i].getDirection(),tradeRecords[i].getPrice(),tradeRecords[i].getNumShares(),tradeRecords[i].getPrice()*tradeRecords[i].getNumShares());
			}
		}
	}
	
}

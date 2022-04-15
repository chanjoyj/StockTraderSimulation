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
		int optionNum;
		SharesHolding[] holdingList= TraderRecords.getHoldingList();
		if (!(holdingList==null)) {
			for (int i = holdingList.length-1; i <0; i--) {
				Screen.printShareHoldingOption(holdingList.length-i, holdingList[i].getStockID(),holdingList[i].getNumShares(),holdingList[i].getAveragePrice(),holdingList[i].getTotalProfit());
				
			}		
			optionNum = holdingList.length;//number of options in stocks
		}else optionNum= 0;
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
		int option;
		//shares menu
		if (!(holdingList==null)) {
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
			option=holdingList.length;
		}else option=0;
		
		if (optionIndex==option+1) {//display enquiries menu
			int startDate;
			int endDate;
			do {
				Screen.printStartDatePrompt();
				String start = Screen.keyboard.nextLine();
				startDate= SetToday.changeDate(start);
				
				Screen.printEndDatePrompt();
				String end = Screen.keyboard.nextLine();
				endDate= SetToday.changeDate(end);
				
				if (endDate<startDate) {
					Screen.printInvalidDatePrompt();
				}
			} while (endDate<startDate);
			//print
			tradeRecords(startDate,endDate);
			Screen.printOrderMenu();
			Screen.printSelectOption();
			int selection=Integer.parseInt(Screen.keyboard.nextLine());
			do {
				switch (selection) {//TODO Sorting
					case 1: {//default no change
						tradeRecords(startDate,endDate);
						Screen.printOrderMenu();
						Screen.printSelectOption();
						selection= Integer.parseInt(Screen.keyboard.nextLine());
					}case 2: {
						//Order by trade price
						tradeRecords(startDate,endDate);//TODO add a sorting here for all below
						Screen.printOrderMenu();
						Screen.printSelectOption();
						selection= Integer.parseInt(Screen.keyboard.nextLine());
					}case 3: {
						//Order by trade shares
						tradeRecords(startDate,endDate);
						Screen.printOrderMenu();
						Screen.printSelectOption();
						selection= Integer.parseInt(Screen.keyboard.nextLine());
					}case 4: {
						//Order by total trade amount
						tradeRecords(startDate,endDate);
						Screen.printOrderMenu();
						Screen.printSelectOption();
						selection= Integer.parseInt(Screen.keyboard.nextLine());
					}case 5: {
						//Order by stock ID
						tradeRecords(startDate,endDate);
						Screen.printOrderMenu();
						Screen.printSelectOption();
						selection= Integer.parseInt(Screen.keyboard.nextLine());
					}
				}
			} while (selection!=6);//TODO debug mode why keep running 6
			if (selection ==6) {
				printMenu();
				int op = getSelection();
				performAction(op);
			}

		}else if (optionIndex==option+2) {
			return new NavigationData(ConstantFlags.NAV_BACK);
		}
		return null;
	}
	
	private void tradeRecords(String stockID) {
		TradeRecord[] tradeRecords= TraderRecords.getTradeList();
		for (int i = tradeRecords.length-1; i <0; i--) {
			if (stockID.equals(tradeRecords[i].getStockID())) {
				Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),tradeRecords[i].getStockID(),tradeRecords[i].getDirection(),tradeRecords[i].getPrice(),tradeRecords[i].getNumShares(),tradeRecords[i].getPrice()*tradeRecords[i].getNumShares());
			}
		}
	}
	
	private void tradeRecords(int startDate, int endDate) {
		TradeRecord[] tradeRecords= TraderRecords.getTradeList();
		if (!(tradeRecords==null)) {
			for (int i = tradeRecords.length-1; i <0; i--) {
				if (tradeRecords[i].getDate()<endDate && tradeRecords[i].getDate()>startDate) {
					Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),tradeRecords[i].getStockID(),tradeRecords[i].getDirection(),tradeRecords[i].getPrice(),tradeRecords[i].getNumShares(),tradeRecords[i].getPrice()*tradeRecords[i].getNumShares());
				}
			}
		}
	}
	
}

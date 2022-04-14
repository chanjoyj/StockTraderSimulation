package menus;

import main.Screen;
import main.SetToday;
import main.StockData;
import main.TraderRecords;
import menuController.ConstantFlags;
import menuController.Menu;
import menuController.NavigationData;
import objects.DailyPrice;
import objects.SharesHolding;
import objects.Stock;
import objects.StockPerformance;
import objects.TradeRecord;

public class MenuEnquireStock extends Menu{
	private String stockID;
	
	public MenuEnquireStock(String string){
		setStockID(string);
	}

	public void printMenu() {
		Stock stock= StockData.findStockbyID(getStockID());
		//print stock and current price 
		DailyPrice stockPrice = StockData.findDailyPrice(stockID,SetToday.getDate());
		Screen.printStockPriceInfo(stockID,stockPrice.getClose());
		//prints sub menu
		if (stock.getType()==1) {//listed print Latest net profit, net asset value and last dividend.
			StockPerformance stockPerformance= StockData.findPerformance(stockID,SetToday.getDate());
			Screen.printListedCompanyInfo(stockID,stockPrice.getClose(),stockPerformance.getProfit(),stockPerformance.getNAV(),stockPerformance.getDividend());
		}else if (stock.getType()==2) {//ETF
			for (int i = 0; i < stock.getETFstocks().length; i++) {
				Screen.printStockOption(i+1,stock.getETFstocks()[i].getStockID());
			}
		}
		//print other menu options
		int optionNum = stock.getETFstocks().length;
		Screen.printTradeOption(optionNum+1);
		Screen.printPriceInDateRangeOption(optionNum+2);
		Screen.printBackOption(optionNum+3);
	}
	
	public int getSelection() {
		Screen.printSelectOption();
		int optionIndex = Integer.parseInt(Screen.keyboard.nextLine());
		return optionIndex;
	}

	public NavigationData performAction(int optionIndex) {
		Stock stock= StockData.findStockbyID(getStockID());
		if (optionIndex<stock.getETFstocks().length+1) {
			for (int i = 0; i < stock.getETFstocks().length; i++) {
				if (optionIndex==i+1) {
					return new NavigationData(ConstantFlags.NAV_ENQUIRE,stock.getETFstocks()[i].getStockID());
				}
			}
		
		}else if (optionIndex==stock.getETFstocks().length+1) {
			//TODO initialize trade here
			return new NavigationData(ConstantFlags.NAV_TRADE);
			
		}else if (optionIndex==stock.getETFstocks().length+2) {
			//Enquire price within date range
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
			//prints stock info
			printRecords(startDate,endDate);
			//redisplays menu and selection part until navigation is called.
			printMenu();
			int option= getSelection();
			performAction(option);
		}else if (optionIndex==stock.getETFstocks().length+3) {//back
			return new NavigationData(ConstantFlags.NAV_BACK);
		}
		return null;
	}
	
	private void printRecords(String startDate, String endDate) {
		DailyPrice[] priceList= StockData.getPriceList();
		int count=0;
		for (int i =0; i <priceList.length; i++) {
			if (getStockID().equals(priceList[i].getStockID()) && priceList[i].getDate()<=endDate && priceList[i].getDate()=>startDate && priceList[i].getDate()<=SetToday.getDate()) {
				Screen.printPrice(priceList[i].getDate(),priceList[i].getClose());
				count++;
			}
		}
		if (count==0) {
			Screen.printNoPriceWithinRange();
		}
	}

	public String getStockID() {
		return stockID;
	}

	public void setStockID(String e) {
		stockID = e;
	}
	
}

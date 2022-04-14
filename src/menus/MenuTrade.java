package menus;

import java.io.IOException;

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

public class MenuTrade extends Menu {
private String stockID;
	
	public MenuTrade(String stockID){
		setStockID(stockID);
	}

	public MenuTrade() {
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
		setStockID(stockID);
	}

	public void printMenu() {
		DailyPrice stockPrice= StockData.findDailyPrice(getStockID(),SetToday.getDate());
		Screen.printStockPriceInfo(getStockID(),stockPrice.getClose());
		Screen.printTradeMenu();
	}
	
	public int getSelection() {
		Screen.printSelectOption();
		int optionIndex = Integer.parseInt(Screen.keyboard.nextLine());
		return optionIndex;
	}

	public NavigationData performAction(int optionIndex) {
		DailyPrice stockPrice= StockData.findDailyPrice(getStockID(),SetToday.getDate());
		switch (optionIndex) {
			case 1: {//buy
				if (TraderRecords.getCash()<stockPrice.getClose()) {
					Screen.printInsufficientCash();
					redisplayTradeMenu();
					
				}else {
					double numPossible= TraderRecords.getCash()/stockPrice.getClose();
					int max= Integer.parseInt((""+numPossible).substring(0,(""+numPossible).indexOf(".")));
					Boolean valid= true;
					int amountbuy;
					do {
						Screen.printAmountOfTradeSharePrompt(max);
						amountbuy = Integer.parseInt(Screen.keyboard.nextLine());
						if (amountbuy>max) {
							Screen.printInsufficientCash();
							valid=false;
						}else valid=true;
					} while (!valid);
					
					//do the buy trade
					buyTrade(amountbuy, stockPrice.getClose());
					redisplayTradeMenu();
				}
			}
			case 2: {//sell
				SharesHolding stockShares=TraderRecords.findSharesHolding(getStockID());
				if (stockShares==null) {
					Screen.printInsufficientShare();
					redisplayTradeMenu();
				}else {
					Boolean valid= true;
					int amountsell;
					do {
						Screen.printAmountOfTradeSharePrompt(stockShares.getNumShares());
						amountsell = Integer.parseInt(Screen.keyboard.nextLine());
						if (amountsell>stockShares.getNumShares()) {
							Screen.printInsufficientShare();
							valid=false;
						}else valid=true;
					} while (!valid);
					//do sell trade
					sellTrade(amountsell,stockShares,stockPrice.getClose());
					redisplayTradeMenu();
				}
			}
			case 3: {//back
				return new NavigationData(ConstantFlags.NAV_BACK);
			}
		}
		return null;
	}

	private void sellTrade(int amountsell, SharesHolding stockShares, double stockPrice) {
		//shares holding
				//TODO CHECK how average price and profit are calculated
				stockShares.updateSharesHolding(stockID, stockShares.getNumShares()-amountsell, stockShares.getAveragePrice(), stockShares.getTotalProfit()+((amountsell-stockShares.getAveragePrice())*stockPrice));
				try {
					TraderRecords.updateSharesHolding();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
		//TradeRecord
		TradeRecord buyTradeRecord = new TradeRecord(SetToday.getDate(), getStockID(), stockPrice, amountsell, 2);
		try {
			TraderRecords.addTradeRecord(buyTradeRecord);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buyTrade(int amountbuy, Double stockPrice) {
		//shares holding
		// get the shares holding if have and update it
		SharesHolding existingSharesHolding =TraderRecords.findSharesHolding(getStockID());
		if (existingSharesHolding==null) {//if no create new and add 
			SharesHolding newSharesHolding = new SharesHolding(stockID, amountbuy, stockPrice, 0);
			try {
				TraderRecords.addSharesHolding(newSharesHolding);
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}else {
			existingSharesHolding.updateSharesHolding(stockID, existingSharesHolding.getNumShares()+amountbuy, (existingSharesHolding.getAveragePrice()+stockPrice)/2, existingSharesHolding.getTotalProfit());
			try {
				TraderRecords.updateSharesHolding();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		//trade record
		// make a new trade record and add into file and reset array
		TradeRecord buyTradeRecord = new TradeRecord(SetToday.getDate(), getStockID(), stockPrice, amountbuy, 1);
		try {
			TraderRecords.addTradeRecord(buyTradeRecord);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	private void redisplayTradeMenu() {
		printMenu();
		int optionIndex= getSelection();
		performAction(optionIndex);
	}

	public String getStockID() {
		return stockID;
	}

	public void setStockID(String e) {
		stockID = e;
	}
	
	
}

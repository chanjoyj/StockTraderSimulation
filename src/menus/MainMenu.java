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
import objects.TradeRecord;

public class MainMenu extends Menu {

	public void printMenu() {
		int today = SetToday.getDate();
		Screen.printMainMenu(SetToday.revertDate(today));
	}

	public int getSelection() {
		Screen.printSelectOption();
		boolean valid;
		int optionIndex = 6;
		do {
			try {
				valid = true;
				optionIndex = Integer.parseInt(Screen.keyboard.nextLine());
				if (optionIndex > 6 || optionIndex < 1) {
					valid = false;
					Screen.printInvalidMainMenuOption();
				}
			} catch (Exception e) {
				valid = false;
				Screen.printInvalidMainMenuOption();
				Screen.printSelectOption();
			}

		} while (!valid);
		return optionIndex;
	}

	public NavigationData performAction(int optionIndex) {
		switch (optionIndex) {
		case 1: {
			return new NavigationData(ConstantFlags.NAV_MYRECORDS);
		}
		case 2: {// enquire a stock
			// enter stock ID
			Screen.printEnterStockId();
			String stockID = Screen.keyboard.nextLine();
			Stock[] stockList = StockData.getStockList();
			// check valid stock id
			boolean valid = false;
			do {
				for (int i = 0; i < stockList.length; i++) {
					if (stockID.equals(stockList[i].getStockID())) {
						valid = true;
						break;
					}
				}
				if (!valid) {
					Screen.printInvalidStockId();
					Screen.printEnterStockId();
					stockID = Screen.keyboard.nextLine();
				}
			} while (!valid);
			return new NavigationData(ConstantFlags.NAV_ENQUIRE, stockID);
		}
		case 3: {
			return new NavigationData(ConstantFlags.NAV_TRADE);
		}
		case 4: {
			autoTrade();
			return new NavigationData(ConstantFlags.NAV_MAIN);
		}
		case 5: {
			SetToday.pass();
			return new NavigationData(ConstantFlags.NAV_MAIN);
		}
		case 6: {
			System.exit(0);
		}
		}
		return null;
	}

	private void autoTrade() {
		int endDate;
		do {
			Screen.printEndDatePrompt();
			String end = Screen.keyboard.nextLine();
			endDate = SetToday.changeDate(end);
			if (endDate < SetToday.getDate()) {
				Screen.printInvalidAutoTradeEndDatePrompt();
			}
		} while (endDate < SetToday.getDate());
		String stockID = "00700";
		// gets list of dates sorted from today to date before end date
		int count = 0;
		for (int i = 0; i < SetToday.getDateList().length; i++) {
			if (SetToday.getDate() <= SetToday.getDateList()[i] && SetToday.getDateList()[i] < endDate) {
				count++;
			}
		}
		int[] autoDateList = new int[count];
		count = 0;
		for (int i = 0; i < SetToday.getDateList().length; i++) {
			if (SetToday.getDate() <= SetToday.getDateList()[i] && SetToday.getDateList()[i] < endDate) {
				autoDateList[count] = SetToday.getDateList()[i];
				count++;
			}
		}

		for (int i = 0; i < autoDateList.length; i++) {
			SetToday.setDate(autoDateList[i]);
			DailyPrice dailyPrice = StockData.findDailyPrice(stockID, SetToday.getDate());
			TradeRecord lastTrade = TraderRecords.findLastTradeRecord(stockID);
			SharesHolding stockShares = TraderRecords.findSharesHolding(stockID);
			if (!(lastTrade == null)) {
				if (dailyPrice.getClose() < lastTrade.getPrice() && stockShares != null
						&& stockShares.getNumShares() != 0) {
					sellTrade(stockShares.getNumShares(), stockShares, dailyPrice.getClose());
					Screen.printAutoTradeMsg(SetToday.revertDate(SetToday.getDate()), stockID, 2, dailyPrice.getClose(),
							stockShares.getNumShares(), dailyPrice.getClose() * stockShares.getNumShares());
					// to see
					stockShares.getNumShares();
					TraderRecords.getCash();

				} else if (dailyPrice.getClose() > lastTrade.getPrice()
						&& TraderRecords.getCash() > dailyPrice.getClose()) {
					double numPossible = TraderRecords.getCash() / dailyPrice.getClose();
					int max = Integer.parseInt(("" + numPossible).substring(0, ("" + numPossible).indexOf(".")));
					buyTrade(max, dailyPrice.getClose());
					Screen.printAutoTradeMsg(SetToday.revertDate(SetToday.getDate()), stockID, 1, dailyPrice.getClose(),
							max, dailyPrice.getClose() * max);
					// to see
					TraderRecords.getCash();
				}
			}
		}
		SetToday.setDate(endDate);
	}

	private void sellTrade(int amountsell, SharesHolding stockShares, double stockPrice) {
		// shares holding
		String stockID = "00700";

		stockShares.updateSharesHolding(stockID, stockShares.getNumShares() - amountsell, stockShares.getAveragePrice(),
				stockShares.getTotalProfit() + (amountsell * stockPrice));
		try {
			TraderRecords.updateSharesHolding();
		} catch (IOException e) {

		}

		// TradeRecord
		TradeRecord buyTradeRecord = new TradeRecord(SetToday.getDate(), stockID, stockPrice, amountsell, 2,
				amountsell);
		try {
			TraderRecords.addTradeRecord(buyTradeRecord);
		} catch (IOException e) {

		}
	}

	private void buyTrade(int amountbuy, Double stockPrice) {
		String stockID = "00700";
		// shares holding
		// get the shares holding if have and update it
		SharesHolding existingSharesHolding = TraderRecords.findSharesHolding(stockID);
		if (existingSharesHolding == null) {// if no create new and add
			SharesHolding newSharesHolding = new SharesHolding(stockID, amountbuy, stockPrice, 0);
			try {
				TraderRecords.addSharesHolding(newSharesHolding);
			} catch (IOException e) {

			}
		} else {
			existingSharesHolding.updateSharesHolding(stockID, existingSharesHolding.getNumShares() + amountbuy,
					(existingSharesHolding.getAveragePrice() + stockPrice) / 2, existingSharesHolding.getTotalProfit());
			try {
				TraderRecords.updateSharesHolding();
			} catch (IOException e) {

			}
		}
		// trade record
		// make a new trade record and add into file and reset array
		TradeRecord buyTradeRecord = new TradeRecord(SetToday.getDate(), stockID, stockPrice, amountbuy, 1, amountbuy);
		try {
			TraderRecords.addTradeRecord(buyTradeRecord);
		} catch (IOException e) {

		}
	}
}

package menus;

import main.Screen;
import main.SetToday;
import main.Sorting;
import main.TraderRecords;
import menuController.ConstantFlags;
import menuController.Menu;
import menuController.NavigationData;
import objects.SharesHolding;
import objects.TradeRecord;

public class MenuMyRecords extends Menu {
	public void printMenu() {
		Double accCash = TraderRecords.getCash();
		Double overallProfit = TraderRecords.getOverallProfit();
		Screen.printAccInfo(accCash, overallProfit);
		int optionNum;
		SharesHolding[] holdingList = TraderRecords.getHoldingList();
		if (holdingList != null) {
			for (int i = holdingList.length-1; i > -1; i--) {
				Screen.printShareHoldingOption(holdingList.length - i, holdingList[i].getStockID(),
						holdingList[i].getNumShares(), holdingList[i].getAveragePrice(),
						holdingList[i].getTotalProfit());

			}
			optionNum = holdingList.length;// number of options in stocks
		} else
			optionNum = 0;
		Screen.printEnquiryTradingRecordsInPeriodOption(optionNum + 1);
		Screen.printBackOption(optionNum + 2);
	}

	public int getSelection() {
		Screen.printSelectOption();
		int optionIndex = Integer.parseInt(Screen.keyboard.nextLine());
		return optionIndex;
	}

	public NavigationData performAction(int optionIndex) {
		SharesHolding[] holdingList = TraderRecords.getHoldingList();
		int option;
		// shares menu
		if (!(holdingList == null)) {// Choosing a stock ID
			String selectedID = "";

			// get stock ID of selected stock
			for (int i = holdingList.length - 1; i > -1; i--) {
				if (optionIndex == holdingList.length - i) {
					selectedID = holdingList[i].getStockID();
					break;
				}
			}
			// print all stocks in holding list = to the stockID selected
			TradeRecord[] tradeRecords = tradeRecords(selectedID);
			if (tradeRecords!=null) {
				for (int i = 0; i < tradeRecords.length; i++) {
					Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()), tradeRecords[i].getStockID(),
							tradeRecords[i].getDirection(), tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
							tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
				}
			}
			// print sorting menu
			Screen.printOrderMenu();
			Screen.printSelectOption();
			String selection = Screen.keyboard.nextLine();
			int[] sortBy = new int[tradeRecords.length];
			do {
				switch (selection) {
				case "1": {// trade sequence by date
					// change sort by to dates and sort it
					for (int i = 0; i < tradeRecords.length; i++) {
						sortBy[i] = tradeRecords[i].getDate();
					}
					Sorting.quickSort(sortBy, 0, sortBy.length - 1);
					// print trade record by trader records
					tradeRecords = tradeRecords(selectedID);
					for (int j = 0; j < sortBy.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (sortBy[j] == tradeRecords[i].getDate()) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setDate(0);
								break;
							}
						}
					}
					tradeRecords = tradeRecords(selectedID);
					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
					break;
				}
				case "2": {
					// Order by trade price
					double[] priceSort = new double[tradeRecords.length];
					for (int i = 0; i < tradeRecords.length; i++) {
						priceSort[i] = tradeRecords[i].getPrice();
					}
					Sorting.dquickSort(priceSort, 0, priceSort.length - 1);

					tradeRecords = tradeRecords(selectedID);
					for (int j = 0; j < priceSort.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (priceSort[j] == tradeRecords[i].getPrice()) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setPrice(0);
								break;
							}
						}
					}
					tradeRecords = tradeRecords(selectedID);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
					break;
				}
				case "3": {
					// Order by trade shares
					for (int i = 0; i < tradeRecords.length; i++) {
						sortBy[i] = tradeRecords[i].getNumShares();
					}
					Sorting.quickSort(sortBy, 0, sortBy.length - 1);

					tradeRecords = tradeRecords(selectedID);
					for (int j = 0; j < sortBy.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (sortBy[j] == tradeRecords[i].getNumShares()) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setNumShares(0);
								break;
							}
						}
					}
					tradeRecords = tradeRecords(selectedID);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
				}
				case "4": {
					// order by total num shares
					for (int i = 0; i < tradeRecords.length; i++) {
						sortBy[i] = tradeRecords[i].getTotalNumShares();
					}
					Sorting.quickSort(sortBy, 0, sortBy.length - 1);

					tradeRecords = tradeRecords(selectedID);
					for (int j = 0; j < sortBy.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (sortBy[j] == tradeRecords[i].getTotalNumShares()) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setTotalNumShares(0);
								break;
							}
						}
					}
					tradeRecords = tradeRecords(selectedID);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();

				}
				case "5": {
					// Order by stock ID
					for (int i = 0; i < tradeRecords.length; i++) {
						sortBy[i] = Integer.parseInt(tradeRecords[i].getStockID());
					}
					Sorting.quickSort(sortBy, 0, sortBy.length - 1);

					tradeRecords = tradeRecords(selectedID);
					for (int j = 0; j < sortBy.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (sortBy[j] == Integer.parseInt(tradeRecords[i].getStockID())) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setStockID("");
								break;
							}
						}
					}
					tradeRecords = tradeRecords(selectedID);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
				}
				case "6":{
					printMenu();
					int op = getSelection();
					return performAction(op);
				}
				}
				option = holdingList.length;
			} while (selection!="6");
			if (selection == "6") {
				printMenu();
				int op = getSelection();
				return performAction(op);
			}
		} else
			option = 0;

		if (optionIndex == option + 1) {// display enquire menu
			int startDate;
			int endDate;
			do {
				Screen.printStartDatePrompt();
				String start = Screen.keyboard.nextLine();
				startDate = SetToday.changeDate(start);

				Screen.printEndDatePrompt();
				String end = Screen.keyboard.nextLine();
				endDate = SetToday.changeDate(end);

				if (endDate < startDate) {
					Screen.printInvalidDatePrompt();
				}
			} while (endDate < startDate);
			// print
			TradeRecord[] tradeRecords = tradeRecords(startDate, endDate);
			if (tradeRecords!=null) {
				for (int i = 0; i < tradeRecords.length; i++) {
					Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()), tradeRecords[i].getStockID(),
							tradeRecords[i].getDirection(), tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
							tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
				}
			}else tradeRecords= new TradeRecord[0];
			Screen.printOrderMenu();
			Screen.printSelectOption();
			String selection = Screen.keyboard.nextLine();
			int[] sortBy = new int[tradeRecords.length];
			do {
				switch (selection) {
				case "1": {// trade sequence by date
					// change sort by to dates and sort it
					for (int i = 0; i < tradeRecords.length; i++) {
						sortBy[i] = tradeRecords[i].getDate();
					}
					Sorting.quickSort(sortBy, 0, sortBy.length - 1);
					// print trade record by trader records
					tradeRecords = tradeRecords(startDate, endDate);
					for (int j = 0; j < sortBy.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (sortBy[j] == tradeRecords[i].getDate()) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setDate(0);
								break;
							}
						}
					}
					tradeRecords = tradeRecords(startDate, endDate);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
					break;
				}
				case "2": {
					// Order by trade price
					double[] priceSort = new double[tradeRecords.length];
					for (int i = 0; i < tradeRecords.length; i++) {
						priceSort[i] = tradeRecords[i].getPrice();
					}
					Sorting.dquickSort(priceSort, 0, priceSort.length - 1);

					tradeRecords = tradeRecords(startDate, endDate);
					for (int j = 0; j < priceSort.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (priceSort[j] == tradeRecords[i].getPrice()) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setPrice(0);
								break;
							}
						}
					}
					tradeRecords = tradeRecords(startDate, endDate);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
					break;
				}
				case "3": {
					// Order by trade shares
					for (int i = 0; i < tradeRecords.length; i++) {
						sortBy[i] = tradeRecords[i].getNumShares();
					}
					Sorting.quickSort(sortBy, 0, sortBy.length - 1);

					tradeRecords = tradeRecords(startDate, endDate);
					for (int j = 0; j < sortBy.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (sortBy[j] == tradeRecords[i].getNumShares()) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setNumShares(0);
								break;
							}
						}
					}
					tradeRecords = tradeRecords(startDate, endDate);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
					break;
				}
				case "4": {
					// order by total num shares
					for (int i = 0; i < tradeRecords.length; i++) {
						sortBy[i] = tradeRecords[i].getTotalNumShares();
					}
					Sorting.quickSort(sortBy, 0, sortBy.length - 1);

					tradeRecords = tradeRecords(startDate, endDate);
					for (int j = 0; j < sortBy.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (sortBy[j] == tradeRecords[i].getTotalNumShares()) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setTotalNumShares(0);
								break;
							}
						}
					}
					tradeRecords = tradeRecords(startDate, endDate);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
					break;
				}
				case "5": {
					// Order by stock ID
					for (int i = 0; i < tradeRecords.length; i++) {
						sortBy[i] = Integer.parseInt(tradeRecords[i].getStockID());
					}
					Sorting.quickSort(sortBy, 0, sortBy.length - 1);

					tradeRecords = tradeRecords(startDate, endDate);
					for (int j = 0; j < sortBy.length; j++) {
						for (int i = 0; i < tradeRecords.length; i++) {
							if (sortBy[j] == Integer.parseInt(tradeRecords[i].getStockID())) {
								Screen.printTradeRecord(SetToday.revertDate(tradeRecords[i].getDate()),
										tradeRecords[i].getStockID(), tradeRecords[i].getDirection(),
										tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(),
										tradeRecords[i].getPrice() * tradeRecords[i].getNumShares());
								tradeRecords[i].setStockID("");
								break;
							}
						}
					}
					tradeRecords = tradeRecords(startDate, endDate);

					Screen.printOrderMenu();
					Screen.printSelectOption();
					selection = Screen.keyboard.nextLine();
					break;
				}case "6": {
					printMenu();
					int op = getSelection();
					return performAction(op);
				}
				}
			} while (selection!="6");
			if (selection == "6") {
				printMenu();
				int op = getSelection();
				return performAction(op);
			}

		} else if (optionIndex == option + 2) {
			return new NavigationData(ConstantFlags.NAV_BACK);
		}
		return null;
	}

	private TradeRecord[] tradeRecords(String stockID) {
		TradeRecord[] tradeRecords = TraderRecords.getTradeList();
		if (tradeRecords!=null) {
			int count = 0;
			for (int i = tradeRecords.length - 1; i > -1; i--) {
				if (stockID.equals(tradeRecords[i].getStockID())) {
					count++;
				}
			}
			TradeRecord[] selectedRecords = new TradeRecord[count];
			count=0;
			for (int i = tradeRecords.length - 1; i > -1; i--) {
				if (stockID.equals(tradeRecords[i].getStockID())) {
					selectedRecords[count] = new TradeRecord(tradeRecords[i].getDate(), tradeRecords[i].getStockID(),
							tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(), tradeRecords[i].getDirection(),
							tradeRecords[i].getTotalNumShares());
					count++;
				}
			}
			return selectedRecords;
		}else return tradeRecords= new TradeRecord[0];
	}

	private TradeRecord[] tradeRecords(int startDate, int endDate) {

		TradeRecord[] tradeRecords = TraderRecords.getTradeList();
		if (tradeRecords!=null) {
			int count = 0;
			for (int i = tradeRecords.length - 1; i > -1; i--) {
				if (tradeRecords[i].getDate() < endDate && tradeRecords[i].getDate() > startDate) {
					count++;
				}
			}
			TradeRecord[] selectedRecords = new TradeRecord[count];
			for (int i = tradeRecords.length - 1; i > -1; i--) {
				if (tradeRecords[i].getDate() < endDate && tradeRecords[i].getDate() > startDate) {
					selectedRecords[count] = new TradeRecord(tradeRecords[i].getDate(), tradeRecords[i].getStockID(),
							tradeRecords[i].getPrice(), tradeRecords[i].getNumShares(), tradeRecords[i].getDirection(),
							tradeRecords[i].getTotalNumShares());
					count++;
				}
			}
			return selectedRecords;
		}else return tradeRecords= new TradeRecord[0];
	}

}

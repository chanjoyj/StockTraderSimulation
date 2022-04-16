package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import objects.SharesHolding;
import objects.StockPerformance;
import objects.TradeRecord;

public class TraderRecords {
	private static TradeRecord[] tradeList;
	private static SharesHolding[] holdingList;
	private static Double cash;
	private static Double overallProfit;

	public static void loadRecords() {
		File tradeRecord = new File("trade-record.csv");
		File sharesHolding = new File("shares-holding.csv");

		if (tradeRecord.exists()) {
			int numTrades;
			try {
				numTrades = StockData.countrecords(tradeRecord);
				tradeList = new TradeRecord[numTrades];
				for (int i = 0; i < numTrades; i++) {
					tradeList[i] = new TradeRecord();
				}
				loadTradeData(tradeRecord);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block for if file is not found
			}

			// get unique and set the total num shares
			// get list of unique stockIDs
			int[] stockIDList = new int[tradeList.length];
			int[][] stockShares = new int[tradeList.length][2];
			for (int i = 0; i < tradeList.length; i++) {
				stockIDList[i] = Integer.parseInt(tradeList[i].getStockID());
				stockShares[i][0] = Integer.parseInt(tradeList[i].getStockID());
				stockShares[i][1] = tradeList[i].getNumShares();
			}
			Sorting.quickSort(stockIDList, 0, stockIDList.length - 1);

			int prev = 0;
			int count = 0;
			for (int i = 0; i < stockIDList.length; i++) {
				if (stockIDList[i] != prev) {
					prev = stockIDList[i];
					count++;
				}
			}

			// create 2d array of unique ID and their total share count
			int[][] uniqueStockShares = new int[count][2];
			int[] totalShares = new int[count];
			prev = 0;
			count = 0;
			int sharenum = 0;
			for (int i = 0; i < stockIDList.length; i++) {
				if (stockIDList[i] != prev) {
					if (count != 0) {
						uniqueStockShares[count - 1][1] = sharenum;
						totalShares[count - 1] = sharenum;
					}
					prev = stockIDList[i];
					uniqueStockShares[count][0] = stockIDList[i];
					count++;
					sharenum = 0;
				}
				for (int j = 0; j < stockIDList.length; j++) {
					if (stockShares[j][0] == stockIDList[i]) {
						sharenum += stockShares[j][1];
						stockShares[j][0] = 0;
					}
				}
			}

			for (int j = 0; j < totalShares.length; j++) {
				for (int i = 0; i < tradeList.length; i++) {
					if (uniqueStockShares[j][0] == Integer.parseInt(tradeList[i].getStockID())) {
						tradeList[i].setTotalNumShares(uniqueStockShares[j][1]);
					}
				}
			}
		}

		if (sharesHolding.exists()) {
			int numHolding;
			try {
				numHolding = StockData.countrecords(sharesHolding);
				holdingList = new SharesHolding[numHolding];
				for (int i = 0; i < numHolding; i++) {
					holdingList[i] = new SharesHolding();
				}
				loadHoldingData(sharesHolding);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block for if file is not found
			}
		}
		setOverallProfit();
		setCash();
	}

	public static void resetRecords() {
		if (tradeList!=null) {
			setDividends(tradeList[tradeList.length-1].getDate(),SetToday.getDate());
		}
		setOverallProfit();
		setCash();
		
	}

	public static void setDividends(int fromDate, int toDate) {
		// adds earned dividends into holding list profits.
		StockPerformance[] performanceList = StockData.getPerformanceList();
		TradeRecord[] traderDividends = getTradeList();
		int[][] dividendScore = new int[traderDividends.length][3];// for stockID, date bought, amount shares

		for (int i = 0; i < traderDividends.length; i++) {// for each item in trader record
			if (traderDividends[i].getDate()>=fromDate && traderDividends[i].getDate()<toDate) {
				if (traderDividends[i].getDirection() == 1) {// if direction is buy
					for (int j = 0; j < dividendScore.length; j++) {// if stock was bought before, change that record's date
																	// bought and add to amount shares
						if (dividendScore[j][0] == Integer.parseInt(traderDividends[i].getStockID())) {
							dividendScore[j][1] = traderDividends[i].getDate();
							dividendScore[j][2] += traderDividends[i].getNumShares();
							break;
						} else if (dividendScore[j][0] == 0) {// no stock record found, save the stock id, date bought and
																// add to amount shares held into new row in array
							dividendScore[j][0] = Integer.parseInt(traderDividends[i].getStockID());
							dividendScore[j][1] = traderDividends[i].getDate();
							dividendScore[j][2] += traderDividends[i].getNumShares();
							break;
						}
					}
					for (int j = 0; j < traderDividends.length; j++) {// for loop over trader record for same stock ID and
																		// direction is sell
						if (traderDividends[i].getStockID() == traderDividends[j].getStockID()
								&& traderDividends[j].getDirection() == 2) {
							for (int k = 0; k < dividendScore.length; k++) {// if stock was bought before, change that
																			// record's date bought and add to amount shares
								// loop through dividends to get the current one
								if (dividendScore[k][0] == Integer.parseInt(traderDividends[j].getStockID())) {
									if (dividendScore[k][2] == 0) {
										break;
									} else if (traderDividends[j].getNumShares() <= dividendScore[k][2]) {// if amount
																											// sold<= amount
																											// held
										// subtract amount sold from amount shares held, keeping amountSold
										dividendScore[k][2] = dividendScore[k][2] - traderDividends[j].getNumShares();
										// loop over performance if stockID equal && payable date within (excluding)
										// date bought and date sold,
										for (int p = 0; p < performanceList.length; p++) {
											if (dividendScore[k][0] == Integer.parseInt(performanceList[p].getStockID())
													&& performanceList[p].getPayableDate() > dividendScore[k][1]
													&& performanceList[p].getPayableDate() < traderDividends[j].getDate()) {
												// then add amount shares held * dividends as new dividends received, into
												// total profit for that shares holding
												double dividendsProfit = traderDividends[j].getNumShares()
														* performanceList[p].getDividend();
												for (int l = 0; l < holdingList.length; l++) {
													if (holdingList[l].getStockID() == performanceList[p].getStockID()) {
														holdingList[l].setTotalProfit(
																holdingList[l].getTotalProfit() + dividendsProfit);
													}
												}
											}
										}
										traderDividends[j].setDirection(0);// also change the direction to 0 meaning sold
																			// off previously already

									} else if (traderDividends[j].getNumShares() > dividendScore[k][2]
											&& dividendScore[k][2] != 0) {// if amount sold> amount held
										// subtract amount shares held from amount sold , keeping amount shares held
										traderDividends[j]
												.setNumShares(traderDividends[j].getNumShares() - dividendScore[k][2]);

										// loop over performance if stockID equal && payable date within (excluding)
										// date bought and date sold,
										for (int p = 0; p < performanceList.length; p++) {
											if (dividendScore[k][0] == Integer.parseInt(performanceList[p].getStockID())
													&& performanceList[p].getPayableDate() > dividendScore[k][1]
													&& performanceList[p].getPayableDate() < traderDividends[j].getDate()) {
												// then add amount shares held * dividends as new dividends received, into
												// total profit for that shares holding
												double dividendsProfit = dividendScore[k][2]
														* performanceList[p].getDividend();
												for (int l = 0; l < holdingList.length; l++) {
													if (holdingList[l].getStockID() == performanceList[p].getStockID()) {
														holdingList[l].setTotalProfit(
																holdingList[l].getTotalProfit() + dividendsProfit);
													}
												}
												dividendScore[k][2] = 0;
											}
										}
									}
								}
							}
						}
					}
				}
				// goes to next item in trader record until another buy appears
			} // end reading trade record
		}
		for (int j = 0; j < dividendScore.length; j++) { // stockID, date bought, amount shares
			if (dividendScore[j][2] > 0) {// for loop in array for all stocks where amount held is > 0 (shares still holidng)
				// for loop over performance if stockID equal && payable date within (excluding) date bought and today,
				for (int p = 0; p < performanceList.length; p++) {
					if (dividendScore[j][0] == Integer.parseInt(performanceList[p].getStockID())
							&& performanceList[p].getPayableDate() > dividendScore[j][1]
							&& performanceList[p].getPayableDate() < toDate) {
						// then add amount shares held * dividends as new dividends received, +into
						// cash, + into profit for that shares holding
						double dividendsProfit = dividendScore[j][2] * performanceList[p].getDividend();
						for (int l = 0; l < holdingList.length; l++) {
							if (holdingList[l].getStockID() == performanceList[p].getStockID()) {
								holdingList[l].setTotalProfit(holdingList[l].getTotalProfit() + dividendsProfit);
							}
						}
						dividendScore[j][2] = 0;
					}
				}
			}
		}
	}

	private static void loadTradeData(File Data) throws FileNotFoundException {
		Scanner FileReader = new Scanner(Data);
		int index = 0;
		while (FileReader.hasNext()) {
			String line = FileReader.nextLine();
			String[] Arr = line.split(",");
			tradeList[index].setDate(SetToday.changeDate(Arr[0]));
			tradeList[index].setStockID(Arr[1]);
			tradeList[index].setPrice(Double.parseDouble(Arr[2]));
			tradeList[index].setNumShares(Integer.parseInt(Arr[3]));
			tradeList[index].setDirection(Integer.parseInt(Arr[4]));
			index++;
		}
		FileReader.close();

	}

	private static void loadHoldingData(File Data) throws FileNotFoundException {
		Scanner FileReader = new Scanner(Data);
		int index = 0;
		while (FileReader.hasNext()) {
			String line = FileReader.nextLine();
			String[] Arr = line.split(",");
			holdingList[index].setStockID(Arr[0]);
			holdingList[index].setNumShares(Integer.parseInt(Arr[1]));
			holdingList[index].setAveragePrice(Double.parseDouble(Arr[2]));
			holdingList[index].setTotalProfit(Double.parseDouble(Arr[3]));
			index++;
		}
		FileReader.close();
	}

	public static Double getCash() {
		return cash;
	}

	public static void setCash() {
		double cash = 1000000.00;// TODO Try one with 0 cash
		if (!(tradeList == null)) {
			for (int i = 0; i < tradeList.length; i++) {
				if (tradeList[i].getDirection() == 1) {
					cash = cash - tradeList[i].getPrice();
				} else if (tradeList[i].getDirection() == 2) {
					cash = cash + tradeList[i].getPrice();
				}
			}
			for (int i = 0; i < holdingList.length; i++) {
				cash = cash + holdingList[i].getTotalProfit();
			}
		}
		TraderRecords.cash = cash;
	}

	public static Double getOverallProfit() {
		return overallProfit;
	}

	public static void setOverallProfit() {
		Double profit = 0.0;
		if (!(holdingList == null)) {
			for (int i = 0; i < holdingList.length; i++) {
				profit = profit + holdingList[i].getTotalProfit();
			}
		}
		TraderRecords.overallProfit = profit;
	}

	public static TradeRecord[] getTradeList() {
		return tradeList;
	}

	public static SharesHolding[] getHoldingList() {
		return holdingList;
	}

	public static TradeRecord findTradeRecord(String ID, int date) {
		if (!(tradeList == null)) {
			for (int i = 0; i < tradeList.length; i++) {
				if (ID.equals(tradeList[i].getStockID()) && date == tradeList[i].getDate()) {
					return tradeList[i];
				}
			}
		}
		return null;
	}

	public static TradeRecord findLastTradeRecord(String ID) {
		if (!(tradeList == null)) {
			for (int i = tradeList.length - 1; i < 0; i--) {
				if (ID.equals(tradeList[i].getStockID())) {
					return tradeList[i];
				}
			}
		}
		return null;
	}

	public static SharesHolding findSharesHolding(String ID) {
		if (!(holdingList == null)) {
			for (int i = 0; i < holdingList.length; i++) {
				if (ID.equals(holdingList[i].getStockID())) {
					return holdingList[i];
				}
			}
		}
		return null;
	}

	public static void addTradeRecord(TradeRecord record) throws IOException {// TODO do we need to deal with this exception?
		File tradeRecord = new File("trade-record.csv");
		FileWriter outputFile = new FileWriter(tradeRecord, true);// appends
		outputFile.write(SetToday.revertDate(record.getDate()) + "," + record.getStockID() + "," + record.getPrice()
				+ "," + record.getNumShares() + "," + record.getDirection() + "\n");
		outputFile.close();
		loadRecords(); // resets the array tradeList
	}

	public static void addSharesHolding(SharesHolding record) throws IOException {
		File sharesHolding = new File("shares-holding.csv");
		FileWriter outputFile = new FileWriter(sharesHolding, true);// appends
		outputFile.write(record.getStockID() + "," + record.getNumShares() + "," + record.getAveragePrice() + ","
				+ record.getTotalProfit() + "\n");
		outputFile.close();
		loadRecords(); // resets the array holdingList
	}

	public static void updateSharesHolding() throws IOException {
		File sharesHolding = new File("shares-holding.csv");
		FileWriter outputFile = new FileWriter(sharesHolding, false);
		for (int i = 0; i < holdingList.length; i++) {
			outputFile.write(holdingList[i].getStockID() + "," + holdingList[i].getNumShares() + ","
					+ holdingList[i].getAveragePrice() + "," + holdingList[i].getTotalProfit() + "\n");
		}
		outputFile.close();
		loadRecords();
	}

}

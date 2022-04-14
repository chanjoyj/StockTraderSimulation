package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import objects.DailyPrice;
import objects.SharesHolding;
import objects.TradeRecord;
//TODO FIND THE TO STRING METHOD DO AN INTEGER+"" EMPTY STRING TO BECOME A STRING
public class TraderRecords {
	private static TradeRecord[] tradeList;
	private static SharesHolding[] holdingList;
	private static Double cash;
	private static Double overallProfit;

	public static void loadRecords() throws FileNotFoundException {
		File tradeRecord = new File("trade-record.csv");
		File sharesHolding= new File("shares-holding.csv");
		
		if (tradeRecord.exists()) {
			int numTrades= StockData.countrecords(tradeRecord);
			tradeList = new TradeRecord[numTrades];
			for (int i = 0; i < numTrades; i++) {
				tradeList[i]= new TradeRecord();
			}
			loadTradeData(tradeRecord);
		}
		
		if (sharesHolding.exists()) {
			int numHolding= StockData.countrecords(tradeRecord);
			holdingList = new SharesHolding[numHolding];
			for (int i = 0; i < numHolding; i++) {
				holdingList[i]= new SharesHolding();
			}
			loadHoldingData(tradeRecord);
		}
		
		setOverallProfit();
		setCash();
	}

	private static void loadTradeData(File Data) throws FileNotFoundException {
		Scanner FileReader = new Scanner(Data);
		int index = 0;
		while (FileReader.hasNext()){
			String line = FileReader.nextLine();
			String[] Arr= line.split(",");
			tradeList[index].setDate(Arr[0]);
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
		while (FileReader.hasNext()){
			String line = FileReader.nextLine();
			String[] Arr= line.split(",");
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

	public static void setCash() {//TODO DIVIDENDS
		double cash= 1000000.00;
		if (tradeList.length>0) {
			for (int i = 0; i < tradeList.length; i++) {
				if (tradeList[i].getDirection() ==1)
				{
					cash= cash- tradeList[i].getPrice();
				}else if (tradeList[i].getDirection() ==2) {
					cash= cash+ tradeList[i].getPrice();
				}
			}
			for (int i = 0; i < holdingList.length; i++) {
				cash= cash + holdingList[i].getTotalProfit();
			}
		}
		TraderRecords.cash= cash;
	}

	public static Double getOverallProfit() {
		return overallProfit;
	}
	
	public static void setOverallProfit() {//TODO DIVIDENDS
		Double profit=0.0;
		for (int i = 0; i < holdingList.length; i++) {
			profit= profit + holdingList[i].getTotalProfit();
		}
		TraderRecords.overallProfit= profit;
	}
	
	public static TradeRecord[] getTradeList() {
		return tradeList;
	}

	public static SharesHolding[] getHoldingList() {
		return holdingList;
	}
	
	public static TradeRecord findTradeRecord(String ID,String date) {
		for (int i = 0; i < tradeList.length; i++) {
			if (ID.equals(tradeList[i].getStockID()) && date.equals(tradeList[i].getDate())){
				return tradeList[i];
			}
		}
		return null;
	}
	
	public static TradeRecord findLastTradeRecord(String ID) {//TODO see if this works without day list
		for (int i = tradeList.length-1; i<0; i--) {
			if (ID.equals(tradeList[i].getStockID())){
				return tradeList[i];
			}
		}
		return null;
	}
	
	public static SharesHolding findSharesHolding(String ID) {
		for (int i = 0; i < holdingList.length; i++) {
			if (ID.equals(holdingList[i].getStockID())){
				return holdingList[i];
			}
		}
		return null;
	}
	
	public static void addTradeRecord(TradeRecord record) throws IOException{
		//TODO add a trade record to the tradeList. and writes it into the csv file
		File tradeRecord = new File("trade-record.csv");
		FileWriter outputFile = new FileWriter(tradeRecord,true);
		outputFile.write(record.getDate() + ","+
						record.getStockID() + ","+
						String.valueOf(record.getPrice()) + ","+
						String.valueOf(record.getNumShares()) + ","+
						String.valueOf(record.getDirection()) + ",");		
		outputFile.close();
		loadRecords();	//resets the array tradeList
	}
	
	public static void addSharesHolding(SharesHolding record) throws IOException{
		//TODO add a shares holding record to the holdingList. and writes it into the csv file
		File sharesHolding= new File("shares-holding.csv");
		FileWriter outputFile = new FileWriter(sharesHolding,true);
		outputFile.write(record.getStockID() + ","+
						String.valueOf(record.getNumShares()) + ","+
						String.valueOf(record.getAveragePrice()) + ","+
						String.valueOf(record.getTotalProfit()) + ",");		
		outputFile.close();
		loadRecords();	//resets the array holdingList
	}
	
	public static void updateSharesHolding() throws IOException {
		File sharesHolding= new File("shares-holding.csv");
		FileWriter outputFile = new FileWriter(sharesHolding,false);
		for (int i = 0; i < holdingList.length; i++) {
			outputFile.write(holdingList[i].getStockID() + ","+
					String.valueOf(holdingList[i].getNumShares()) + ","+
					String.valueOf(holdingList[i].getAveragePrice()) + ","+
					String.valueOf(holdingList[i].getTotalProfit()) + ",");	
		}
		outputFile.close();
		loadRecords();//TODO SEE IF NEED
	}
	
}

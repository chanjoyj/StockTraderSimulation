package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import objects.SharesHolding;
import objects.TradeRecord;

public class TraderRecords {
	private static TradeRecord[] tradeList;
	private static SharesHolding[] holdingList;
	private static Double cash;
	private static Double overallProfit;

	public static void loadRecords() {//TODO check for File not found 
		File tradeRecord = new File("trade-record.csv");
		File sharesHolding= new File("shares-holding.csv");
		
		if (tradeRecord.exists()) {
			int numTrades;
			try {
				numTrades = StockData.countrecords(tradeRecord);
				tradeList = new TradeRecord[numTrades];
				for (int i = 0; i < numTrades; i++) {
					tradeList[i]= new TradeRecord();
				}
				loadTradeData(tradeRecord);
			} catch (FileNotFoundException e) {
				//TODO Auto-generated catch block for if file is not found
			}
		}
		
		if (sharesHolding.exists()) {
			int numHolding;
			try {
				numHolding = StockData.countrecords(sharesHolding);
				holdingList = new SharesHolding[numHolding];
				for (int i = 0; i < numHolding; i++) {
					holdingList[i]= new SharesHolding();
				}
				loadHoldingData(tradeRecord);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block for if file is not found
			}
		}
		//add dividends
		setOverallProfit();
		setCash();
	}

	private static void loadTradeData(File Data) throws FileNotFoundException {
		Scanner FileReader = new Scanner(Data);
		int index = 0;
		while (FileReader.hasNext()){
			String line = FileReader.nextLine();
			String[] Arr= line.split(",");
			tradeList[index].setDate(SetToday.changeDate(Arr[0]));
			tradeList[index].setStockID(Arr[1]);
			tradeList[index].setPrice(Double.parseDouble(Arr[2]));
			tradeList[index].setNumShares(Integer.parseInt(Arr[3]));
			tradeList[index].setDirection(Integer.parseInt(Arr[4]));
			index++;
		}
		FileReader.close();
	}
	
	private static void loadHoldingData(File Data) throws FileNotFoundException{
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
		if (!(tradeList==null)) {
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
		if (!(holdingList==null)) {
			for (int i = 0; i < holdingList.length; i++) {
				profit= profit + holdingList[i].getTotalProfit();
			}
		}
		TraderRecords.overallProfit= profit;
	}
	
	public static TradeRecord[] getTradeList() {
		return tradeList;
	}

	public static SharesHolding[] getHoldingList() {
		return holdingList;
	}
	
	public static TradeRecord findTradeRecord(String ID,int date) {
		for (int i = 0; i < tradeList.length; i++) {
			if (ID.equals(tradeList[i].getStockID()) && date==tradeList[i].getDate()){
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
	
	public static void addTradeRecord(TradeRecord record) throws IOException{//TODO do we need to deal with this exception?
		File tradeRecord = new File("trade-record.csv");
		FileWriter outputFile = new FileWriter(tradeRecord,true);//appends
		outputFile.write(SetToday.revertDate(record.getDate()) + ","+
						record.getStockID() + ","+
						record.getPrice()+
						record.getNumShares() + ","+
						record.getDirection() + ",");		
		outputFile.close();
		loadRecords();	//resets the array tradeList
	}
	
	public static void addSharesHolding(SharesHolding record) throws IOException{
		File sharesHolding= new File("shares-holding.csv");
		FileWriter outputFile = new FileWriter(sharesHolding,true);//appends
		outputFile.write(record.getStockID() + ","+
						record.getNumShares() + ","+
						record.getAveragePrice() + ","+
						record.getTotalProfit() + ",");		
		outputFile.close();
		loadRecords();	//resets the array holdingList
	}
	
	public static void updateSharesHolding() throws IOException {
		File sharesHolding= new File("shares-holding.csv");
		FileWriter outputFile = new FileWriter(sharesHolding,false);
		for (int i = 0; i < holdingList.length; i++) {
			outputFile.write(holdingList[i].getStockID() + ","+
					holdingList[i].getNumShares() + ","+
					holdingList[i].getAveragePrice() + ","+
					holdingList[i].getTotalProfit() + ",");	
		}
		outputFile.close();
		loadRecords();//TODO SEE IF NEED
	}
	
}

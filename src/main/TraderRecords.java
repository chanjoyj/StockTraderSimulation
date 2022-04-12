package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import objects.DailyPrice;
import objects.SharesHolding;
import objects.TradeRecord;

public class TraderRecords {
	private static TradeRecord[] tradeList;
	private static SharesHolding[] holdingList;
	
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
	
	public static TradeRecord findTradeRecord(String ID,String date) {
		for (int i = 0; i < tradeList.length; i++) {
			if (ID.equals(tradeList[i].getStockID()) && date.equals(tradeList[i].getDate())){
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
	
}

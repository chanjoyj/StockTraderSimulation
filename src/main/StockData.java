package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import objects.DailyPrice;
import objects.Stock;
import objects.StockPerformance;
import objects.TradeRecord;
//holds the stock data information as an array
//array of stock information
public class StockData {
		private static Stock[] stockList;
		private static StockPerformance[] performanceList;
		private static DailyPrice[] priceList;
		private static String dateStart;
		private static String dateEnd;
		

		public static String getDateEnd() {
			return dateEnd;
		}

		public static void setDateEnd(String dateEnd) {
			StockData.dateEnd = dateEnd;
		}
		
		public static String getDateStart() {
			return dateStart;
		}

		public static void setDateStart(String dateStart) {
			StockData.dateStart = dateStart;
		}
		
		public static DailyPrice[] getPriceList() {
			return priceList;
		}
		
		public static Stock[] getStockList() {
			return stockList;
		}

		public static StockPerformance[] getPerformanceList() {
			return performanceList;
		}

	//assumes all 3 files exist
	public static void loadData() throws FileNotFoundException {
		File stockData = new File ("stock-meta");
		File dailyPrice = new File("daily-price.csv");
		File stockPerformance = new File("performance.csv");
		
		int numStocks= countrecords(stockData);
		stockList = new Stock[numStocks];
		for (int i = 0; i < numStocks; i++) {
			stockList[i]= new Stock();
		}
		loadStockData(stockData);
		
		int numDays= countrecords(dailyPrice);
		priceList = new DailyPrice[numDays];
		for (int i = 0; i < numDays; i++) {
			priceList[i]= new DailyPrice();
		}
		loadPriceData(dailyPrice);
		
		int numPerform= countrecords(stockPerformance);
		performanceList = new StockPerformance[numPerform];
		for (int i = 0; i < numPerform; i++) {
			performanceList[i]= new StockPerformance();
		}
		loadPerformanceeData(stockPerformance);
		
		
		
		
		//TODO change all dates to yyyy/mm/dd
		//set the max and min dates
	}
	
	public static int countrecords(File Data) throws FileNotFoundException {
		Scanner FileReader = new Scanner(Data);
		int count= 0;
		while (FileReader.hasNext()) {
			FileReader.nextLine();
			count++;
		}
		FileReader.close();
		return count;
	}
	
	private static void loadStockData(File Data) throws FileNotFoundException{
		Scanner FileReader = new Scanner(Data);
		int index = 0;
		while (FileReader.hasNext()){
			String line = FileReader.nextLine();
			String[] Arr= line.split(",");
			stockList[index].setStockID(Arr[0]);
			stockList[index].setType(Integer.parseInt(Arr[1]));
			if (Arr[1]=="2") {
				Stock[] ETF= new Stock[Arr.length-2];
				for (int i = 2; i < Arr.length; i++) {
					ETF[i-2].setStockID(Arr[i]);
				}
				stockList[index].setETFstocks(ETF);
			}
			index++;
		}
		FileReader.close();
	}
	
	private static void loadPriceData(File Data) throws FileNotFoundException{
		Scanner FileReader = new Scanner(Data);
		int index = 0;
		while (FileReader.hasNext()){
			String line = FileReader.nextLine();
			String[] Arr= line.split(",");
			priceList[index].setStockID(Arr[0]);
			priceList[index].setDate(SetToday.changeDate(Arr[1]));
			priceList[index].setClose(Double.parseDouble(Arr[2]));
			index++;
		}
		FileReader.close();
	}
	
	private static void loadPerformanceeData(File Data) throws FileNotFoundException{
		Scanner FileReader = new Scanner(Data);
		int index = 0;
		while (FileReader.hasNext()){
			String line = FileReader.nextLine();
			String[] Arr= line.split(",");
			performanceList[index].setStockID(Arr[0]);
			performanceList[index].setDate(SetToday.changeDate(Arr[1]));
			performanceList[index].setProfit(Double.parseDouble(Arr[2]));
			performanceList[index].setNAV(Double.parseDouble(Arr[3]));
			performanceList[index].setDividend(Double.parseDouble(Arr[4]));
			performanceList[index].setPayableDate(SetToday.changeDate(Arr[5]));
			index++;
		}
		FileReader.close();
	}
	
	public static Stock findStockbyID(String ID) {
		for (int i = 0; i < stockList.length; i++) {
			if (ID.equals(stockList[i].getStockID())) {
				return stockList[i];
			}
		}
		return null;
	}
	
	public static StockPerformance findPerformance(String ID,String date) {//TODO Get latest date rather than equals
		for (int i = 0; i < performanceList.length; i++) {
			if (ID.equals(performanceList[i].getStockID()) && date.equals(performanceList[i].getDate())){
				return performanceList[i];
			}
		}
		return null;
	}
	
	public static DailyPrice findDailyPrice(String ID,String date) {
		for (int i = 0; i < priceList.length; i++) {
			if (ID.equals(priceList[i].getStockID()) && date.equals(priceList[i].getDate())){
				return priceList[i];
			}
		}
		return null;
	}
	
}

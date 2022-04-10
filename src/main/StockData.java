package main;
import java.io.File;
import java.util.Scanner;

public class StockData {
	public static void loadStockData() {
		File stockData = new File ("stock-meta");
		File dailyPrice = new File("daily-price.csv");
		File stockPerformance = new File("performance.csv");
		
		countStock(stockData);
		loadStockData(stockData);
	}
	
	private static void countStock(File stockData) {
		Scanner FileReader = new Scanner(stockData);
		int count= 0;
		while (FileReader.hasNext()) {
			FileReader.nextLine();
			count++;
		}
		
	}
	
	private static void loadStockData(File stockData) {
		
	}
	
}

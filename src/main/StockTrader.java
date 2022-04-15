package main;

import java.io.FileNotFoundException;
import menuController.MenuController;

//Main runner
public class StockTrader {
	public static void main(String[] args) throws FileNotFoundException {
		StockData.loadData();
		TraderRecords.loadRecords();
		System.out.println();
		SetToday.inputDate();
		MenuController.startMenu();
	}
}

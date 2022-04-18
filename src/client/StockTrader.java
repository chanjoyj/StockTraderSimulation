package client;

import java.io.FileNotFoundException;

import main.SetToday;
import main.StockData;
import main.TraderRecords;
import menuController.MenuController;

//Main runner
public class StockTrader {
	public static void main(String[] args) throws FileNotFoundException {
		StockData.loadData();
		TraderRecords.loadRecords();
		SetToday.inputDate();
		MenuController.startMenu();
	}
}

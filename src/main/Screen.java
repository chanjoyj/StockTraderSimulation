package main;
/**
	A Class that provides all the printings for the project
*/

import java.util.Scanner;

public class Screen {
	public static Scanner keyboard = new Scanner(System.in);
	private static String[] tradeType = { "Buy", "Sell" };

	public static void printDateInsertion() {
		System.out.print("Enter a date as today (DD/MM/YYYY): ");
	}

	public static void printMainMenu(String today) {
		System.out.println("Hello! Today is " + today + "\n");
		System.out.println("1. My records");
		System.out.println("2. Enquire a stock");
		System.out.println("3. Trade");
		System.out.println("4. Perform auto Trade");
		System.out.println("5. Pass");
		System.out.println("6. Exit\n");
	}
	
	public static void printInvalidMainMenuOption() {
		System.out.println("Invalid main menu option. Please enter again.");
	}

	public static void printSelectOption() {
		System.out.print("Select an option: ");
	}

	public static void printAccInfo(double accCash, double overallProfit) {
		System.out.printf("Cash: $%-15.2f Overall Profit: $%-15.2f\n", accCash, overallProfit);
	}
	
	public static void printShareHoldingOption(int optionNum, String id, int amountOfShare, double avgPrice, double totalProfit) {
		System.out.printf("%d. [%s]  Shares: %-15d Average Price: %-15.2f Total Profit: $%-15.2f\n", optionNum, id, amountOfShare, avgPrice, totalProfit);
	}
	
	public static void printEnquiryTradingRecordsInPeriodOption(int optionNum) {
		System.out.printf("%d. Enquiry trading records in period\n", optionNum);
	}
	
	public static void printBackOption(int optionNum) {
		System.out.printf("%d. Back\n\n", optionNum);
	}
	
	public static void printStartDatePrompt() {
		System.out.print("Enter the start date: ");
	}

	public static void printEndDatePrompt() {
		System.out.print("Enter the end date: ");
	}
	
	public static void printInvalidDatePrompt() {
		System.out.println("The end date cannot before the start date. Please enter again.");
	}
	
	public static void printOrderMenu() {
		System.out.println("1. Order by trade sequence (default)");
		System.out.println("2. Order by trade price");
		System.out.println("3. Order by trade shares");
		System.out.println("4. Order by total trade amount");
		System.out.println("5. Order by stock ID");
		System.out.println("6. Back\n");
	}
	
	public static void printTradeRecord(String date, String id, int type, double price, int shares, double amount) {
		System.out.printf("%s %s %-4s Price: %-15.2f Shares: %-15d Amount: $%-15.2f\n", date, id, tradeType[type-1], price, shares, amount);
	}
	
	public static void printTradeOption(int optionNum) {
		System.out.printf("%d. Trade\n", optionNum);
	}

	public static void printPriceInDateRangeOption(int optionNum) {
		System.out.printf("%d. Enquiry price within date range\n", optionNum);
	}
	
	public static void printEnterStockId() {
		System.out.print("Enter a stock id: ");
	}
	
	public static void printInvalidStockId() {
		System.out.println("Invalid stock id. Please input again.");
	}
	
	public static void printListedCompanyInfo(String id, double price, double earnings, double nav, double dividend) {
		System.out.printf("[%s] Price: %-15.2f Net Profit: %-15.2f NAV: %-15.2f Dividend: %-15.2f\n", id, price, earnings, nav, dividend);
	}
	
	public static void printStockPriceInfo(String id, double price) {
		System.out.printf("[%s] Price: %-15.2f\n", id, price);
	}
	
	public static void printPrice(String date, double price) {
		System.out.printf("%s: %-15.2f\n", date, price);
	}
	
	public static void printNoPriceWithinRange() {
		System.out.println("No price within the given date range.");
	}
	
	public static void printStockOption(int optionNum, String id) {
		System.out.printf("%d. %s\n", optionNum, id);
	}
	
	public static void printTradeMenu() {
		System.out.println("1. Buy");
		System.out.println("2. Sell");
		System.out.println("3. Back\n");
	}
	
	public static void printInsufficientCash() {
		System.out.println("Insufficient cash.");
	}

	public static void printInsufficientShare() {
		System.out.println("Insufficient share.");
	}
	
	public static void printAmountOfTradeSharePrompt(int max) {
		System.out.printf("Enter the amount of shares [1-%d]: ", max);
	}
	
	public static void printAutoTradeMsg(String date, String id, int type, double price, int numShare, double amount) {
		System.out.printf("Auto Trade: %s %s %-4s Price: %-15.2f Shares: %-15d Amount: $%-15.2f\n", date, id, tradeType[type-1], price, numShare, amount);
	}
	
	public static void printInvalidAutoTradeEndDatePrompt() {
		System.out.println("The end date cannot earlier than today. Please enter again.");
	}
}
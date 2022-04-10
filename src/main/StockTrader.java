package main;
import java.io.File;
import java.sql.Date;
import java.util.Scanner;

import com.example.services.ScriptManager;
//Main runner
public class StockTrader {
	private static Scanner input = new Scanner(System.in);
	File stockData = new File("stock-meta.csv");
	File dailyPrice = new File("daily-price.csv");
	File performance = new File("performance.csv");
	File tradeRecord = new File("trade-record.csv");
	File sharesHolding= new File("shares-holding.csv");
	
	private Screen script = new Screen();

	public static void main(String[] args) {
		
	}

	public void dateInsertion() {
		// TODO insert date and check if valid
		System.out.println("Enter a date as today (DD/MM/YYYY): ");
		String date = input.nextLine();
		// check in DD/MM/YYYY format
		// split check lengths and if DD<30,MM<13
		// within date range of daily price data file dailyPrice
		// use and from year
		// no earlier than the date of the last trade record
		// use and from year
		// check if date is in daily price data file if not move to next trade date

	}

	public void printMainMenu() {
		System.out.println("Hello! Today is " + date); //TODO date
		System.out.println("1. My records\n"
				+ "2. Enquire a stock\n"
				+ "3. Trade\n"
				+ "4. Perform auto trade 5. Pass\n"
				+ "6. Exit\n");
		
		//Check input
		boolean valid;
		do {
			try {
				valid = true;
				System.out.println("Select an option: ");
				int select = input.nextInt();
				if (!(select==1|| select==2|| select==3|| select==4|| select==5|| select==6)) {
					valid = false;
					System.out.println("Invalid main menu option, please enter again.");
				}
			} catch (Exception e) {
				valid = false;
				System.out.println("Invalid main menu option, please enter again.");
			}

		} while (!valid);
		
		//TODO menu selection
		switch (select) {
		case 1: {
			//my records
			
		}
		case 2: {
			// enquire a stock
			
		}
		case 3: {
			//trade
			
		}
		case 4: {
			//perform auto trade
			
		}
		case 5: {
			//pass
			
		}
		case 6: {
			//exit
			System.exit(0);
		}
		}
	}

	public void myRecords1() {
		//TODO T3 have class 
	}
	
	public void enquire2() {
		boolean match = false;
		do {
			System.out.println("Enter a stock id: ");
			String stockID = input.nextLine();
			//check if exists
			while (txtReader.hasNext()) {
				String stock = txtReader.nextLine();
				if (stock.equals(stockID)) {
					//TODO T6
					match = true;
					break;
				} else {
					match = false;
				}
			}
			if (!match) {
				System.out.println("Invalid stock id. Please input again.");
			}
		} while (!match);

	}
	
	public void trade3() {
		//TODO T7
	}
	
	public void option4() {
		//TODO T8
	}
	
	public void option5() {
		//TODO move date
		//validation by if month 12, if month has 30 or 31 days
		//check if its in the dates and go to next if not
		//re run menu 
	}
	
	
	
}

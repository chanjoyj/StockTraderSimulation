package main;
import java.io.File;
import java.sql.Date;
import java.util.Scanner;

import com.example.services.ScriptManager;
//Main runner
public class StockTrader {
	
	
	
	
	
	
	
	

	public void printMainMenu() {
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

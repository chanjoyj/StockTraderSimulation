package main;

public class SetToday {
	public static int today;
	public static int lastTradeDate;

	// Change date to integer, then add a method of return to string dd/mm/yyyy
	public static void inputDate() {
		boolean valid = true;
		String date;
		int today;
		do {
			Screen.printDateInsertion();
			date = Screen.keyboard.nextLine();
			valid = checkValidDate(date);
		} while (!valid);
		today = changeDate(date);
		today = checkDateinFile(today);
		setDate(today);
	}

	public static int checkDateinFile(int date) {
		boolean inFile = false;
		do {
			for (int i = 0; i < StockData.getPriceList().length; i++) {
				if (date == StockData.getPriceList()[i].getDate()) {
					inFile = true;
				}
			}
			if (!inFile) {
				date = incrementDate(date);
			}
		} while (!inFile);
		return date;
	}

	public static boolean checkValidDate(String date) {
		String[] dateArr;
		int today;
		if (date.length() == 10 && !(date.indexOf("/") == -1)) {
			dateArr = date.split("/");
		} else
			return false;

		dateArr[0].length();
		if (dateArr[0].length() == 2 && dateArr[1].length() == 2 && dateArr[2].length() == 4 &&
				Integer.parseInt(dateArr[0]) < 32 && Integer.parseInt(dateArr[1]) < 13) {// TODO add more date
																							// requirements
			today = changeDate(date);
		} else
			return false;
		StockData.setDateEnd(20220301);// TODO Delete later
		StockData.setDateStart(20171201);// TODO Delete later
		if (today > StockData.getDateEnd() || today < StockData.getDateStart() || today < lastTradeDate) {
			return false;
		} else
			return true;
	}

	public static int incrementDate(int date) {
		// TODO also check for dividends and update the other trader menus accordingly
		// or just reload trader menu
		// TODO change increment date to be using the date list
		for (int i = 0; i < StockData.getPriceList().length; i++) {
			if (StockData.getPriceList()[i].getDate() > date) { // assuming dates are in ascending order
				date = StockData.getPriceList()[i].getDate();
				break;
			}
		}
		return date;
	}

	public static int getDate() {
		return today;
	}

	public static void setDate(int today2) {
		SetToday.today = today2;
	}

	public static int changeDate(String date) {
		return Integer.parseInt(date.substring(6) + date.substring(3, 5) + date.substring(0, 2));
	}

	public static String revertDate(int date) {
		String dates = date + "";
		return dates.substring(6) + "/" + dates.substring(4, 6) + "/" + dates.substring(0, 4);
	}

}

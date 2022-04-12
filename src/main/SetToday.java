package main;

public class SetToday {
	public static String today;
	public static String lastTradeDate= "";
	
	public void inputDate() {
		boolean valid=true;
		String date;
		//TODO: get date from input and check and set the date
		do {
			Screen.printDateInsertion();
			date = Screen.keyboard.nextLine();
			valid= checkValidDate(date);
		} while (!valid);
		date= changeDate(date);
		date= checkDateinFile(date);
		today = date;
	}
	
	public static String checkDateinFile(String date) {
		boolean inFile =false;
		do {
			for (int i = 0; i < StockData.getPriceList().length; i++) {
				if (date.equals(StockData.getPriceList()[i].getDate())){
					inFile= true; 
				}
			}
			if (!inFile) {
				date= incrementDate(date);
			}
		} while (!inFile);
		return date;
	}
	
	public boolean checkValidDate(String date) {
		String[] dateArr;
			if (date.length()== 10) {
				dateArr= date.split("/");
			} else return false;

			dateArr[0].length();
			if (dateArr[0].length()==2 && dateArr[1].length()==2 && dateArr[2].length()==4 &&
					Integer.parseInt(dateArr[0])<32 && Integer.parseInt(dateArr[1])<13) {
				date= changeDate(date);
			}else return false;
			
			if (date > StockData.getDateEnd() || date < StockData.getDateStart() || date < lastTradeDate) {
				return false;
			}else return true;
	}
	
	public static String incrementDate(String date) {
		for (int i = 0; i < StockData.getPriceList().length; i++) {
			if (StockData.getPriceList()[i].getDate() > date){ //assuming dates are in ascending order
				date= StockData.getPriceList()[i].getDate();
				break;
			}
		}
		return date;
	}

	public static String getDate() {
		return today;
	}
	
	public static String changeDate(String date) {
		return date.substring(6)+"/"+date.substring(3,5)+"/"+ date.substring(0,2);
	}
	
}

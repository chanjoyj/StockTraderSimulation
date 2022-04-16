package main;

import objects.TradeRecord;

public class SetToday {
	public static int today;
	public static int lastTradeDate;
	public static int[] dateList;

	// Change date to integer, then add a method of return to string dd/mm/yyyy
	public static void inputDate() {
		setDateList();
		boolean valid = true;
		boolean valid2 = true;
		String date;
		int today = 0;
		do {
			Screen.printDateInsertion();
			date = Screen.keyboard.nextLine();
			valid = checkValidDate(date);
			if (valid) {
				today = changeDate(date);
				valid2 = checkDatebefore(today);
			}
		} while (!valid||!valid2||today == 0);
		today = checkDateinFile(today);
		setDate(today);
		TraderRecords.resetRecords();
	}

	public static void setDateList() {
		//get min and max and list of all dates
				int max=0;
				int min=999999999;
				int[] allDateList= new int[StockData.getPriceList().length];
				for (int i = 0; i < StockData.getPriceList().length; i++) {
					int thisDate = StockData.getPriceList()[i].getDate();
					//get max and min date
					if (thisDate>max) {
						max = thisDate;
					}else if (thisDate<min) {
						min = thisDate;
					}
					allDateList[i]= thisDate;
				}
				StockData.setDateEnd(max);
				StockData.setDateStart(min);
				//sort the list
				Sorting.quickSort(allDateList,0,allDateList.length-1);
				
				//find number of unique
				int prev=0;
				int count=0;
				for (int i = 0; i < allDateList.length; i++) {
					if (allDateList[i]!=prev) {
						prev=allDateList[i];
						count++;
					}
				}
				
				//create unique sorted list
				SetToday.dateList= new int[count];
				prev=0;
				count=0;
				for (int i = 0; i < allDateList.length; i++) {
					if (allDateList[i]!=prev) {
						prev=allDateList[i];
						dateList[count]=allDateList[i];
						count++;
					}
				}
	}
	
	public static boolean checkValidDate(String date) {
		String[] dateArr;
		int today;
		if (date.length() == 10 && !(date.indexOf("/") == -1)) {//check for length and /
			dateArr = date.split("/");
		} else
			return false;

		dateArr[0].length();//check for if is a month and day valid
		if (dateArr[0].length() == 2 && dateArr[1].length() == 2 && dateArr[2].length() == 4 &&
				Integer.parseInt(dateArr[0]) < 32 && Integer.parseInt(dateArr[1]) < 13) {// TODO add more date requirements
			today = changeDate(date);
		} else
			return false;
		if (today > StockData.getDateEnd() || today < StockData.getDateStart() || today < lastTradeDate) {
			return false;
		} else
			return true;
	}


	public static int checkDateinFile(int date) {
		boolean inFile = false;
		do {
			for (int i = 0; i < dateList.length; i++) {
				if (date == dateList[i]) {
					inFile = true;
				}
			}
			if (!inFile) {
				date = incrementDate(date);
			}
		} while (!inFile);
		return date;
	}

	private static boolean checkDatebefore(int today2) {
		TradeRecord[] tradelist = TraderRecords.getTradeList();
		if (tradelist!=null) {
			if (today2 < tradelist[tradelist.length-1].getDate()) {
				return false;
			}
		}
		return true;
	}

	public static int incrementDate(int date) {
		for (int i = 0; i < dateList.length; i++) {
			if (dateList[i]>date) {
				date= dateList[i];
				break;
			}
		}
		return date;
	}
	
	public static void pass() {
		int prevDay=getDate();
		for (int i = 0; i < dateList.length; i++) {
			if (dateList[i]==getDate()) {
				setDate(dateList[i+1]);
				break;
			}
		}
		TradeRecord[] tradelist = TraderRecords.getTradeList();
		if (tradelist!=null) {
			TraderRecords.setDividends(prevDay,getDate());
		}
		TraderRecords.setOverallProfit();
		TraderRecords.setCash();
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
	
	public static int[] getDateList() {
		return dateList;
	}

	public static String revertDate(int date) {
		String dates = date + "";
		return dates.substring(6) + "/" + dates.substring(4, 6) + "/" + dates.substring(0, 4);


	}

}

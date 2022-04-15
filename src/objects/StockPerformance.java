package objects;

public class StockPerformance {
	private String stockID;
	private int date;
	private double profit;
	private double NAV;
	private double dividend;
	private int payableDate;

	public StockPerformance(String stockID, int date, double profit, double nAV, double dividend,
			int payableDate) {
		this.stockID = stockID;
		this.date = date;
		this.profit = profit;
		this.NAV = nAV;
		this.dividend = dividend;
		this.payableDate = payableDate;
	}

	public StockPerformance() {
	}

	public String getStockID() {
		return stockID;
	}

	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getNAV() {
		return NAV;
	}

	public void setNAV(double nAV) {
		NAV = nAV;
	}

	public double getDividend() {
		return dividend;
	}

	public void setDividend(double dividend) {
		this.dividend = dividend;
	}

	public int getPayableDate() {
		return payableDate;
	}

	public void setPayableDate(int payableDate) {
		this.payableDate = payableDate;
	}

}

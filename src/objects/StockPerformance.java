package objects;

public class StockPerformance {
	private String stockID;
	private String date;
	private double profit;
	private double NAV;
	private double dividend;
	private String payableDate;

	public StockPerformance(String stockID, String date, double profit, double nAV, double dividend,
			String payableDate) {
		this.stockID = stockID;
		this.date = date;
		this.profit = profit;
		this.NAV = nAV;
		this.dividend = dividend;
		this.payableDate = payableDate;
	}

	public String getStockID() {
		return stockID;
	}

	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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

	public String getPayableDate() {
		return payableDate;
	}

	public void setPayableDate(String payableDate) {
		this.payableDate = payableDate;
	}

}

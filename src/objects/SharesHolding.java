package objects;

public class SharesHolding {
	private String stockID;
	private int numShares;
	private double averagePrice;
	private double totalProfit;

	public SharesHolding(String stockID, int numShares, double averagePrice, double totalProfit) {
		this.stockID = stockID;
		this.numShares = numShares;
		this.averagePrice = averagePrice;
		this.totalProfit = totalProfit;
	}

	public SharesHolding() {
	}

	public void updateSharesHolding(String stockID, int numShares, double averagePrice, double totalProfit) {
		this.stockID = stockID;
		this.numShares = numShares;
		this.averagePrice = averagePrice;
		this.totalProfit = totalProfit;
	}

	public String getStockID() {
		return stockID;
	}

	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	public int getNumShares() {
		return numShares;
	}

	public void setNumShares(int numShares) {
		this.numShares = numShares;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}

}

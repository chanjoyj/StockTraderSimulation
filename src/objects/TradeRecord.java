package objects;

public class TradeRecord {
	private int date;
	private String stockID;
	private double price;
	private int numShares;
	private int direction;
	private int totalNumShares;

	public TradeRecord(int date, String stockID, double price, int numShares, int direction, int totalNumShares) {
		this.date = date;
		this.stockID = stockID;
		this.price = price;
		this.numShares = numShares;
		this.direction = direction;
	}

	public TradeRecord() {
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getStockID() {
		return stockID;
	}

	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumShares() {
		return numShares;
	}

	public void setNumShares(int numShares) {
		this.numShares = numShares;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getTotalNumShares() {
		return totalNumShares;
	}

	public void setTotalNumShares(int totalNumShares) {
		this.totalNumShares = totalNumShares;
	}

}
